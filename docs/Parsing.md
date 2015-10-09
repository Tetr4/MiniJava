# Parse token stream via Beaver
We use [Beaver](http://beaver.sourceforge.net/) to generate a parser from [MJParser.beaver](/minijava/MJParser.beaver).

The Parser is generated as java files into the *gen* folder.<br/>

Parsing rules are described in Extended Backus–Naur Form (EBNF) and use the terminals from our [scanner](/docs/Scanning.md). Most rules return a java object (whose class was generated with [JastAdd](http://jastadd.org/) from [minijava.ast](/minijava/minijava.ast)), which contains the objects generated from other rules. Starting from the goal rule an abstract syntax tree (AST) is generated made up of these java objects.


The generated parser is a deterministic finite automaton (DFA) which parses the token stream from our [scanner](/docs/Scanning.md) into an Abstract Syntax Tree (AST).
The generated file /gen/minijava/MJParser.stat allows you to take a look at the generated DFA.

The AST is further analyzed starting with [name resolution](/docs/NameResolution.md).