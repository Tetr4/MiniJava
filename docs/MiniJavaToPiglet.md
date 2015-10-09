# MiniJava to Piglet

#### toPiglet()
In [PigletASTBuilder.jrag](/minijava/PigletASTBuilder.jrag) a `toPiglet()` method is added to `Program`, `MethodDecl`, statements and expressions. <br/>
This method returns a piglet equivalent of the AST node on which it is used, e.g. a `MethodDecl` becomes a `piglet.Procedure` and an `Add` becomes a `piglet.Plus`. To do this, it will also transform its children by calling `toPiglet()` on them. Therefore a call to `toPiglet()` of `Program` is enough to transform the MiniJava AST to a Piglet AST.

A MiniJava statement may generate multiple piglet statements, e.g. a `minijava.If` has to create multiple `piglet.Jump` and `piglet.CJump` statements to *else* and *exit* targets. Therefore every `toPiglet()` method returns a list of statements.


#### Helper Procedures
Often used functions are added as helper procedures to the `piglet.Program`:
+ `ArrayAlloc(size)`: Allocates an array of given size in the heap, initialises it with zeros and returns the address. The array length is saved at the first index.
+ `ArrayLookup(address, index)`: Checks address for null and for index out of bounds and returns the value at address + index + 1 (+ 1 as the length is saved at the first index and needs to be skipped).
+ `ArrayLength(address)`: Return value at first index of array, as the length is saved there.
+ `ArrayBoundsCheck(address, index)`: Makes sure that 0 <= index < ArrayLength(address). Jumps to `piglet.ERR` if not to terminate execution.
+ `ObjNullCheck(address)`: Makes sure that the given array/object address is not 0. Jumps to `piglet.ERR` if not to terminate execution.
+ `ObjectAlloc(vtableList, classIndex , size)`: Allocates a block of given size + 1, initialises it with zeros, saves the virtual function table (*vtableList[classIndex]*) address at the beginning of the block and then returns the blocks address.
+ `ObjectFieldAccess(address, offset)`: Returns the fields value, which is saved at address + offset.
+ `MethodAddress(address, vtableIndex)`: Checks address for null, gets the virtual function table (address is stored at beginning of object) and returns the method address from *vtable[vtableIndex]*.


#### Virtual function tables and fields
+ The virtual function table list (*vtableList*) contains all virtual function tables (*vtable*). It is always stored at `TEMP 0`.
+ Every class has an index inside the vtableList for its vtable.
+ Every method of a class (including superclasses) has an index so it can be looked up in its classes vtable. A methods that overrides another method, take its index (polymorphy).
+ Every field of a class (including superclasses) has an index, so it can be stored and found in objects.
+ Method calls receive the vtable list and the object ("this") as 1. and 2. parameter, so they can access fields and other methods and can create new instances of different classes using the vtable list. Due to the vtable list always being the first parameter it's guaranteed to have it in `TEMP 0`.


#### Temporaries
When transforming the `MainClass` to piglet statements, every local variable is assigned a temp with an incrementing index, starting at 0. <br/>
In `MethodDecl` `TEMP 0` (vtablelist) and `TEMP 1` ("this") are reserved, therefore every parameter is assigned a temp with and incrementing index starting at 2. Then every local Variable is assigned a temp, again incrementing the same index. Finally if the return expressions is a "logic and chain" we need to generate additional code, so one additional temp is generated from the same index.


#### Labels
Labels for generated procedures are made up of classname and method name, so two methods with the same name can exist in seperate classes.
```java
class + "__" + methodname`
```
Generated labels have at least two underscore in their name to prevent name collision with internal labels (e.g. "vtable_list"), as internal labels use at most one underscore.

#### Boolean encoding
As piglet doesn't have boolean literals and we have already validated type correctness in [ErrorChecking](/docs/ErrorChecking.md), we simply use integers for booleans:
+ True = 1
+ False = 0

The next step is to [flatten the tree using SPiglet](/docs/PigletToSPiglet.md).
