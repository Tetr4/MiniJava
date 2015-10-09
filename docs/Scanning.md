# Scan input via JFlex
We use [JFlex](http://jflex.de/) to generate a scanner from [MJScanner.flex](/minijava/MJScanner.flex).

The Scanner is generated as java files into the *gen* folder.

It defines terminals (e.g. IF, CLASS, INTEGER, COMMA or WHITESPACE) which are described in RegEx. <br/>
With these it can turn a stream of chars into a stream of terminals (also called token stream), which can then be parsed by our [parser](/docs/Parsing.md).
