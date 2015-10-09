# Error checking
In [ErrorCheck.jrag](/minijava/ErrorCheck.jrag) a `SemanticError` class
and an `errors()` method is added to the `Program`. Usage:
```java
for (SemanticError e : program.errors()) {
    System.err.println(e.getMessage());
}
```

This is done by adding a `collectErrors()` method to every AST node, which recursively calls `collectErrors()` for its children. In this method an AST node checks for errors in its children and if there are any adds a new error, for example:
+ The type of the condition in an `If` is not a `TBool`.
+ The `TypedVar` belonging to an `IdentUse` can not be found.
+ Usage if `This` in MainClass.
+ There is a cycle on the superclass chain of a `ClassDecl`.
+ There are multiple `ClassDecl` with the same name.
+ Overridden `MethodDecl` has different return type or parameters.
