# Type analysis

#### TypeInfo
In [TypeAnalysis.jrag](/minijava/TypeAnalysis.jrag) an abstract class `TypeInfo` is added. Subclasses are:
+ `TInt` for integers
+ `TIntArray` for integer arrays
+ `TBool` for booleans
+ `TClass` (with `ClassDecl` member) for objects (see [TClass](#tclass) section below)
+ `TUnknown` for cases where the type is not known or most likely invalid

For convenience and to reuse the same type object everywhere instead of creating lots of objects, the following cached shortcuts are added to all AST nodes:
+ `booleanType()`
+ `intType()`
+ `intArrayType()`
+ `unknownType()`


#### type()
The method type `type()` returns the TypeInfo for most AST nodes. It was added to:
+ `TypeUse`
    + `IntArray`
    + `Bool`
    + `Int`
    + `ClassT`
+ `Exp`
    + `And`
    + `Cmp`
    + `Add`
    + `Sub`
    + `Mult`
    + `Not`
    + `Neg`
+ `ClassDecl`
+ `MethodDecl`
+ `TypedVar`
+ `IdentUse`
+ `ArrayAlloc`
+ `ObjAlloc`
+ `ArrayLookup`
+ `ArrayLength`
+ `MethodCall`
+ `IntLiteral`
+ `True`
+ `False`
+ `This`

This allows for [error checking](/docs/ErrorChecking.md). For example to check if a `MethodCall` returns an int use:
```java
boolean returnsInt = call.type().isSubtypeOf(intType());
```

#### TClass
`TClass` is used to represent class types and contains a reference to the `ClassDecl` of the class it belongs to, which allows for these methods:
+ `isSameType()` which checks for exactly the same class.
+ `isSubtypeOf()` equivalent to Java's `instanceof` and checks for super classes as well.

It checks the super class chain with information from the [name resolution](/docs/NameResolution.md) for matching ClassDecls and allows to verify inheritance.

