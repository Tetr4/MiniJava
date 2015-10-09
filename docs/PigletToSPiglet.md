# Piglet to Simplified Piglet

SPiglet is a subset of Piglet. It simplifies the language by "flattening" Piglet's tree structure to lists of (labeled) statements.

#### toSpiglet()
In [SpigletASTBuilder.jrag](/piglet/SpigletASTBuilder.jrag) a `toSpiglet()` method is added to most piglet AST nodes. <br/>

A piglet statement may generate multiple spiglet statements, e.g. a `spiglet.Plus` needs to store its left expression in a `Temp`, which needs an additional statement. But other than that piglets AST nodes map neatly to spiglet AST nodes.
Therefore `Stmt.toSpiglet(List<spiglet.Stmt> stmts)` returns a single `spiglet.Stmt`, but has a list of statements as a parameter, into which additionally generated statements can be dumped. This list is generated once per `spiglet.Procedure`/`spiglet.Program`.

#### Temps
As spiglet uses more temps, we need to remember which piglet `Temp` belongs to which `spiglet.Temp` and reuse accordingly. Therefore we generate a new `spiglet.Temp`, if this is the first time we encounter this `Temp` in piglet, otherwise we reuse the old one. We just keep counting the temp index up for each procedure starting at 0.

#### Expressions
Spiglet uses `spiglet.Exp`, `spiglet.SExp`, and `spiglet.Temp` where piglet uses just `Exp`. Therefore a method is required for each transformation:
+ `Exp.toSpigletExp()` Generate code to save the expression's value in normal expression. This only applies to `Call`s and `HAlloc`s in SPiglet.
+ `Exp.toSpigletSExp()` Generate code to save the expression's value in a simple expression. Usually this means to wrap the result of `Exp.toSpigletTemp()` in a `spiglet.TempExp`.
+ `Exp.toSpigletTemp()` Generate code to save the expression's value in a temp and return the temp.

The next step is to [translate procedure calls using Kanga](/docs/SPigletToKanga.md).