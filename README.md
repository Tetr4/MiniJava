# MIPS Compiler for MiniJava
### Setup
+ Building: Run [build.xml](build.xml) with ant.
+ Testing: Run the [AllTests.java](juni/tests/AllTests.java) suite with junit.

### Compilation order
1. [Scan the input](/docs/Scanning.md) via JFlex
2. [Parse the token stream](/docs/Parsing.md) via Beaver
3. Annotate the [Abstract Syntax Tree (AST)](/minijava/minijava.ast) via JastAdd
    + [Name resolution](/docs/NameResolution.md)
    + [Type analysis](/docs/TypeAnalysis.md)
    + [Error checking](/docs/ErrorChecking.md)
4. Transform [MiniJava AST to Piglet AST](/docs/MiniJavaToPiglet.md)
5. Transform [Piglet AST to SPiglet AST](/docs/PigletToSPiglet.md)
    + Liveness analysis
6. Transform [SPiglet AST to Kanga AST](/docs/SPigletToKanga.md)
7. Transform [Kanga AST to MIPS AST](/docs/KangaToMIPS.md)
8. Prettyprint MIPS AST


### Usage examples
####  Check for Syntax/Semantic errors:
```java
MJFile file = new MJFile("tests/Factorial.java");
minijava.Program program = file.parse();
for (SemanticError e : program.errors()) {
    System.err.println(e.getMessage());
}
```

#### Compilation:
```java
MJFile file = new MJFile("tests/Factorial.java");
mips.Program mips = file.parse().toPiglet().toSpiglet().toKanga().toMips();
String mipsCode = mips.print().getString();
```

#### Interpreting:
It is possible to interpret Piglet, Spiglet, Kanga and Mips.
```java
MJFile file = new MJFile("tests/Factorial.java");
spiglet.Program spiglet = file.parse().toPiglet().toSpiglet();
System.out.println(spiglet.interpret());
```
