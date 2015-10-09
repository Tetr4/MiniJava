# Name resolution
Everytime an identifier is used in the program, try to link it to its declaration. There are variables (local or class fields), methods and classes.

In [NameResolution.jrag](/minijava/NameResolution.jrag) a `decl()` method is added to:
+ IdentUse
+ Assign
+ ObjAlloc
+ ClassT
+ ArrayAssign
+ This
+ MethodCall

A `superClass()` method is also added to ClassDecl which returns the ClassDecl of the super class.

These methods return the declaration of the object as `TypedVar`, `MethodDecl` or `ClassDecl` in our AST, which the object refers to. For example `MethodCall.decl()` returns the MethodDecl from the ClassDecl of the object on which the method is called, e.g. to check if the parameters of MethodCall and MethodDecl match.

This is done by traversing the tree in reverse order starting from the AST node in question and looking for matching declarations.

Based on this information we add [type analysis](/docs/TypeAnalysis.md) to the AST.