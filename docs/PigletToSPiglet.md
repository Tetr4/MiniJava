# Piglet to SPiglet

#### toSpiglet()
In [SpigletASTBuilder.jrag](/minijava/SpigletASTBuilder.jrag) a `toSpiglet()` method is added to most piglet AST nodes. <br/>

A piglet statement may generate multiple spiglet statements, e.g. a `spiglet.Plus` needs to store its left expression in a `Temp`, which needs an additional statement. But other than that piglets AST nodes map neatly to spiglet AST nodes.
Therefore `Stmt.toSpiglet()` returns a single `spiglet.Stmt`, but has a list of statements as a parameter, into which additionally generated statements can be dumped. This list is generated once per `spiglet.Procedure`/`spiglet.Program`.

#### Temps
As spiglet uses more temps, we need to remember which piglet `Temp` belongs to which `spiglet.Temp` and reuse accordingly. Therefore we generate a new `spiglet.Temp`, if this is the first time we encounter this `Temp` in piglet, otherwise we reuse the old one.

#### Expressions
Spiglet uses `spiglet.Exp`, `spiglet.SExp`, and `spiglet.Temp` where piglet uses just `Exp`. Therefore a method is required for each transformation:
+ `Exp.toSpigletExp()`
+ `Exp.toSpigletSExp()`
+ `Exp.toSpigletTemp()`
