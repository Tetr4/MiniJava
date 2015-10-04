# Liveness Analysis
## Definitions
+ **pos(statement)**: Assign a position 0, ..., n for each statement in a procedure with n statements. <br/>
Example: `[CJUMP TEMP 9 label]⁰ [MOVE TEMP 10 0]¹ [label NOOP]²`

+ **def(pos)**: set of variables that are defined at this position<br/>
Example: def(3) = {`TEMP 6`} for `[MOVE TEMP 6 4]³`

+ **use(pos)**: set of variables that are read at this position<br/>
Example: use(7) = {`TEMP 5`, `TEMP 2`} for `[MOVE TEMP 5 PLUS TEMP 5 TEMP 2]⁷`

+ **succ(pos)**: set of possible successor positions<br/>
Example: succ(0) = {1,2} for `[CJUMP TEMP 9 label]⁰ [MOVE TEMP 10 0]¹ [label NOOP]²`

+ **out(pos)**: set of variables, which are alive after this position<br/>
out(pos) = in(i) for each i in succ(position)

+ **in(pos)**: set of variables, which are alive before this position<br/>
in(pos) = use(position) + (out(position) - def(position))

+ **flow(statement)**: defines set of edges between positions of statements<br/>
Example: flow(`[CJUMP TEMP 9 [label]]ⁿ`) = { (n, n+1), (n, flow(label)) }

## Specification
*TODO: specify use/def sets of temporaries*
### flow
- flow(`[l:NOOP]ⁿ`) = { (n, n+1) }
- flow(`ERROR`) = {∅}
- flow(`[CJUMP TEMP 9 [label]]ⁿ`) = { (n, n+1), (n, flow(label)) }
- flow(`[JUMP [label]]ⁿ`) = { (n, flow(label)) }
- flow(`[HSTORE TEMP 5 0 TEMP 8]ⁿ`) = { (n, n+1) }
- flow(`[HLOAD TEMP 2 TEMP 3 0]ⁿ`) = { (n, n+1) }
- flow(`[MOVE TEMP 5 4]ⁿ`) = { (n, n+1) }
- flow(`[PRINT TEMP 9]ⁿ`) = { (n, n+1) }

#### use
- Noop:Stmt;
- Err:Stmt;
- CJump:Stmt ::= Cond:Temp Label:Label;
- Jump:Stmt ::= Label:Label;
- HStore:Stmt ::= Addr:Temp <Offset:Integer> Value:Temp;
- HLoad:Stmt ::= Dest:Temp Addr:Temp <Offset:Integer>;
- Move:Stmt ::= Dest:Temp Source:Exp;
- Print:Stmt ::= Value:SExp;

#### def
- Noop:Stmt;
- Err:Stmt;
- CJump:Stmt ::= Cond:Temp Label:Label;
- Jump:Stmt ::= Label:Label;
- HStore:Stmt ::= Addr:Temp <Offset:Integer> Value:Temp;
- HLoad:Stmt ::= Dest:Temp Addr:Temp <Offset:Integer>;
- Move:Stmt ::= Dest:Temp Source:Exp;
- Print:Stmt ::= Value:SExp;
