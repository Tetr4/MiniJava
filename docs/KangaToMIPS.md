# Transform Kanga AST to MIPS AST
In [MipsASTBuilder.jrag](/kanga/MipsASTBuilder.jrag) a `toMips()` method is added to most kanga AST nodes. <br/>


#### Prologue & Epilogue for Procedures
1. Allocate stack space
1. Save return address on stack
1. *Procedure body...*
1. Restore return address from stack
1. Free stack space
1. Jump to return address


#### Helper procedures and data
Some statements (e.g. `Print`) do not exist in MIPS and have to be generate by a `Syscall`:
+ \_halloc: syscall to allocate
+ \_print: sysvall to print integer + "\\n"
+ \_error: syscall to print "ERROR\\n" and then syscall to exit
+ newl: "\\n" as string data
+ str_er: "ERROR\\n" as string data
