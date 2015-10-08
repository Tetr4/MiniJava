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
out(pos) = in(i) for each i in succ(pos)

+ **in(pos)**: set of temps which are alive before this position<br/>
in(pos) = use(pos) + (out(pos) - def(pos))

+ **flow(statement)**: defines set of edges between positions of statements<br/>
Example: flow(`[CJUMP TEMP 9 [label]]ⁱ`) = { (i, i+1), (i, pos(label)) }

## Specification
### flow(statement) for i ∈ {0, ..., n-1}
- flow(`[NOOP]ⁱ`) = { (i, i+1) }
- flow(`[ERROR]ⁱ`) = ∅
- flow(`[CJUMP TEMP t [label]]ⁱ`) = { (i, i+1), (i, pos(label)) }
- flow(`[JUMP [label]]ⁱ`) = { (i, pos(label)) }
- flow(`[HSTORE TEMP t <int> TEMP u]ⁱ`) = { (i, i+1) }
- flow(`[HLOAD TEMP t TEMP u <int>]ⁱ`) = { (i, i+1) }
- flow(`[MOVE TEMP t <Exp>]ⁱ`) = { (i, i+1) }
- flow(`[PRINT <SExp>]ⁱ`) = { (i, i+1) }

### def(pos)
- def(i) = ∅ for `[NOOP]ⁱ`
- def(i) = ∅ for `[ERROR]ⁱ`
- def(i) = ∅ for `[CJUMP TEMP t [label]]ⁱ`
- def(i) = ∅ for `[JUMP [label]]ⁱ`}
- def(i) = {`TEMP t`} for `[HSTORE TEMP t <int> TEMP u]ⁱ`
- def(i) = {`TEMP t`} for `[HLOAD TEMP t TEMP u <int>]ⁱ`
- def(i) = {`TEMP t`} for `[MOVE TEMP t <Exp>]ⁱ`
- def(i) = ∅ for `[PRINT <SExp>]ⁱ`

### use(pos)
- use(i) = ∅ for `[NOOP]ⁱ`
- use(i) = ∅ for `[ERROR]ⁱ`
- use(i) = {`TEMP t`} for `[CJUMP TEMP t [label]]ⁱ`
- use(i) = ∅ for `[JUMP [label]]ⁱ`}
- use(i) = {`TEMP u`} for `[HSTORE TEMP t <int> TEMP u]ⁱ`
- use(i) = {`TEMP u`} for `[HLOAD TEMP t TEMP u <int>]ⁱ`
- use(i) = useExp(`<Exp>`) for `[MOVE TEMP t <Exp>]ⁱ`
- use(i) = useSExp(`<SExp>`) for `[PRINT <SExp>]ⁱ`
- use(expression) helper
    - useExp(`HALLOC <SExp>`) = useSExp(`<SExp>`)
    - useExp(`CALL <SExp> TEMP t ... TEMP u`) = useSExp(`<SExp>`) ∪ {`TEMP t`, ..., `TEMP u`}
    - useExp(`LT TEMP t <SExp>`) =  useSExp(`<SExp>`) ∪ {`TEMP t`}
    - useExp(`PLUS TEMP t <SExp>`) =  useSExp(`<SExp>`) ∪ {`TEMP t`}
    - useExp(`MINUS TEMP t <SExp>`) =  useSExp(`<SExp>`) ∪ {`TEMP t`}
    - useExp(`TIMES TEMP t <SExp>`) =  useSExp(`<SExp>`) ∪ {`TEMP t`}
    - useSExp(`TEMP t`) = {`TEMP t`}
    - useSExp(`label`) = ∅
    - useSExp(`<int>`) = ∅
