# Transform Simplified Piglet to Kanga

In this intermediate language temporaries are mapped to registers or spilled to the stack frame. Kanga is already fairly close to MIPS assembler. The `spiglet.Call` expression is now a statement with only the target method's address as parameter. The actual arguments for the call have to be put in place through previous statements before the call. The first four arguments are passed in special argument registers `a0`-`a3`, all the other arguments are passed on the stack using `kanga.PassArg`.

The return value of a procedures' `spiglet.StmtExp` is put in register `v0`. 

Every temp is mapped to a stack slot. Like in the previous transformation, the same stack slot is used for the same temp index. 

Currently every temp is always stored on the stack when it's written to and loaded from the stack when it's read. There are no means to use temporary registers to speed up calculations yet. This goes along with the task's specification.

In [KangaASTBuilder.jrag](/spiglet/KangaASTBuilder.jrag) a `toKanga()` method is added to most spiglet AST nodes. <br />

Except for `spiglet.Call`s all transformations are straight forward. Depending on whether the Statement or Expression uses a Temp for reading or writing the value is loaded from the stack using `kanga.ALoad` or saved to the stack using `kanga.AStore`. This is easy, because every Temp belongs to a stack index. Statements collect the additional `ALoad` or `AStore` statements which are generated by their children and add them before or after themselves accordingly.

#### Method calls
The caller's arguments have to be preserved, because the arguments for the callee have to be passed in the same registers. There is extra space reserved at the beginning of the caller's stack frame for that. This is against MIPS' calling convention, but Kanga doesn't allow interprocedural stack access.
1. Save all argument registers `a0`-`a3` in special slots on the stack.
1. Load all arguments for the call.
1. Call the method's address.
1. Restore the argument registers from the stack.
1. Move the returned value from register `v0` into the target register.

Last thing to do is [construct the actual stack frame in MIPS assembler](/docs/KangaToMIPS.md).