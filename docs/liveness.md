# Liveness Analysis
## Definitions
+ **pos(statement)**: Assign a position 0, ..., n for each statement in a procedure with n statements. <br/>
Example: `[CJUMP TEMP 9 label]⁰ [MOVE TEMP 10 0]¹ [label NOOP]²`

+ **def(pos)**: set of temps that are defined at this position<br/>
Example: def(3) = {`TEMP 6`} for `[MOVE TEMP 6 4]³`

+ **use(pos)**: set of temps that are read at this position<br/>
Example: use(7) = {`TEMP 5`, `TEMP 2`} for `[MOVE TEMP 5 PLUS TEMP 5 TEMP 2]⁷`

+ **succ(pos)**: set of possible successor positions<br/>
Example: succ(0) = {1,2} for `[CJUMP TEMP 9 label]⁰ [MOVE TEMP 10 0]¹ [label NOOP]²`

+ **out(pos)**: set of temps which are alive after this position<br/>
out(pos) = in(i) for each i in succ(position)

+ **in(pos)**: set of temps which are alive before this position<br/>
in(pos) = use(position) + (out(position) - def(position))

+ **flow(statement)**: defines set of edges between positions of statements<br/>
Example: flow(`[CJUMP TEMP 9 [label]]ⁿ`) = { (n, n+1), (n, flow(label)) }

## Specification
### flow
- flow(`[NOOP]ⁿ`) = { (n, n+1) }
- flow(`[ERROR]ⁿ`) = {∅}
- flow(`[CJUMP TEMP i [label]]ⁿ`) = { (n, n+1), (n, flow(label)) }
- flow(`[JUMP [label]]ⁿ`) = { (n, flow(label)) }
- flow(`[HSTORE TEMP i <int> TEMP j]ⁿ`) = { (n, n+1) }
- flow(`[HLOAD TEMP i TEMP j <int>]ⁿ`) = { (n, n+1) }
- flow(`[MOVE TEMP i <Exp>]ⁿ`) = { (n, n+1) }
- flow(`[PRINT <SExp>]ⁿ`) = { (n, n+1) }

#### def
- def(n) = {∅} for `[NOOP]ⁿ`
- def(n) = {∅} for `[ERROR]ⁿ`
- def(n) = {∅} for `[CJUMP TEMP i [label]]ⁿ`
- def(n) = {∅} for `[JUMP [label]]ⁿ`}
- def(n) = {`TEMP i`} for `[HSTORE TEMP i <int> TEMP j]ⁿ`
- def(n) = {`TEMP i`} for `[HLOAD TEMP i TEMP j <int>]ⁿ`
- def(n) = {`TEMP i`} for `[MOVE TEMP i <Exp>]ⁿ`
- def(n) = {∅} for `[PRINT <SExp>]ⁿ`

#### use
- use(n) = {∅} for `[NOOP]ⁿ`
- use(n) = {∅} for `[ERROR]ⁿ`
- use(n) = {`TEMP i`} for `[CJUMP TEMP i [label]]ⁿ`
- use(n) = {∅} for `[JUMP [label]]ⁿ`}
- use(n) = {`TEMP j`} for `[HSTORE TEMP i <int> TEMP j]ⁿ`
- use(n) = {`TEMP j`} for `[HLOAD TEMP i TEMP j <int>]ⁿ`
- use(n) = {useExp(`<Exp>`)} for `[MOVE TEMP i <Exp>]ⁿ`
- use(n) = {useSExp(`<SExp>`)} for `[PRINT <SExp>]ⁿ`
- useExp(`HALLOC <SExp>`) = {useSExp(`<SExp>`)}
- useExp(`CALL <SExp> TEMP i ... TEMP j`) = {useSExp(`<SExp>`), `TEMP i`, ..., `TEMP j`}
- useExp(`TEMP i LT <SExp>`) =  {`TEMP i`, useSExp(`<SExp>`)}
- useExp(`TEMP i PLUS <SExp>`) =  {`TEMP i`, useSExp(`<SExp>`)}
- useExp(`TEMP i MINUS <SExp>`) =  {`TEMP i`, useSExp(`<SExp>`)}
- useExp(`TEMP i TIMES <SExp>`) =  {`TEMP i`, useSExp(`<SExp>`)}
- useSExp(`TEMP i`) = {`TEMP i`}
- useSExp(`label`) = {∅}
- useSExp(`<int>`) = {∅}
