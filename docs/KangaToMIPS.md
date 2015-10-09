# Transform Kanga AST to MIPS AST
The final step is to create the stack layout and take care of recursive calls. The main challenge of this exercise is to implement the stack layout of the method frames. We have to store and re-construct the stack pointer and the return address for each procedure call as well as allocate enough stack space for each frame. 

In [MipsASTBuilder.jrag](/kanga/MipsASTBuilder.jrag) a `toMips()` method is added to most kanga AST nodes. <br/>


#### Prologue & Epilogue for procedures
1. Allocate stack space using the required stack slot information in Kanga plus another slot for the return address.
1. Save return address on stack.
1. *Procedure body...*
1. Restore return address from stack.
1. Free stack space.
1. Jump to return address.

#### Helper procedures and data
Some statements (e.g. `Print`) do not exist in MIPS and have to be generate by a `Syscall`:
+ \_halloc: syscall to allocate memory on the heap
+ \_print: syscall to print integer + "\\n"
+ \_error: syscall to print "ERROR\\n" and then syscall to exit
+ newl: "\\n" as string data
+ str_er: "ERROR\\n" as string data
