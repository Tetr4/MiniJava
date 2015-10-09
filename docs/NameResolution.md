# Name resolution
In [NameResolution.jrag](/minijava/NameResolution.jrag) a `decl()` method is added to:
+ IdentUse
+ Assign
+ ObjAlloc
+ ClassT
+ ArrayAssing
+ This
+ MethodCall

A a `superClass()` method is also added to ClassDecl.

These methods return the TypedVar, MethodDecl or ClassDecl in our AST, which the object refers to. For example `MethodCall.decl()` returns the MethodDecl from the ClassDecl of the object on which the call is called, e.g. to check if the parameters of MethodCall and MethodDecl match.

This is done by traversing the tree in reverse order starting from ast node to resolve and looking for matching declarations.
