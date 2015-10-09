# Type analysis

#### TypeInfo
In [TypeAnalysis.jrag](/minijava/TypeAnalysis.jrag) an abstract class `TypeInfo` is added. Subclasses are:
+ `TInt` for integers
+ `TIntArray` for integer arrays
+ `TBool` for booleans
+ `TClass` (with `ClassDecl` member) for objects (see TClass section below)
+ `TUnknown` for cases where the type is not know or most likely invalid

For convenience and to resuse the same type object following shortcuts are added to all AST nodes:
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
`TClass` contains a `ClassDecl` member, which allows for these methods:
+ `isSameType()`
+ `isSubtypeOf()` equivalent to javas `instanceof`
