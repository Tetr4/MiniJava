package mips; // The generated parser will belong to this package 

import mips.MipsParser.Terminals; 
// The terminals are implicitly defined in the parser
%%

// define the signature for the generated scanner
%public
%final
%class MipsScanner 
%extends beaver.Scanner

// the interface between the scanner and the parser is the nextToken() method
%type beaver.Symbol 
%function nextToken 
%yylexthrow beaver.Scanner.Exception
%eofval{
	return new beaver.Symbol(Terminals.EOF, "end-of-file");
%eofval}

// store line and column information in the tokens
%line
%column

// this code will be inlined in the body of the generated scanner class
%{
  private beaver.Symbol sym(short id) {
    return new beaver.Symbol(id, yyline + 1, yycolumn + 1, yylength(), yytext());
  }
%}

LineTerminator = \r|\n|\r\n
WhiteSpace     = {LineTerminator} | [ \t\f]
Identifier = [:jletter:][:jletterdigit:]*
IntLiteral = [1-9][0-9]* | "0"

/* comments */
InputCharacter = [^\r\n]
Comment     = "#" {InputCharacter}* {LineTerminator}?
%%

// discard whitespace information and comments
{WhiteSpace} { }
{Comment}    { }

// token definitions
"("          { return sym(Terminals.LPAREN); }
")"          { return sym(Terminals.RPAREN); }
//";"          { return sym(Terminals.SEMI); }
//"."          { return sym(Terminals.DOT); }
","          { return sym(Terminals.COMMA); }
":"          { return sym(Terminals.COLON); }
//"+"          { return sym(Terminals.PLUS); }
"-"          { return sym(Terminals.MINUS); }



"add"		 { return sym(Terminals.ADD); }
"addu"    	 { return sym(Terminals.ADDU); }
"b"          { return sym(Terminals.B); }
"bne"        { return sym(Terminals.BNE); }
"j"      	 { return sym(Terminals.J); }
"jal"        { return sym(Terminals.JAL); }
"jalr"       { return sym(Terminals.JALR); }
"la"         { return sym(Terminals.LA); }
"li"         { return sym(Terminals.LI); }
"lw"         { return sym(Terminals.LW); }
"move"     	 { return sym(Terminals.MOVE); }
"nop"        { return sym(Terminals.NOP); }
"sw"         { return sym(Terminals.SW); }
"subu"     	 { return sym(Terminals.SUBU); }


"syscall"    { return sym(Terminals.SYSCALL); }

".word"      { return sym(Terminals.WORD); }
".text"      { return sym(Terminals.TEXT); }
".data"      { return sym(Terminals.DATA); }
".globl"     { return sym(Terminals.GLOBL); }
//".extern"	 { return sym(Terminals.EXTERN); }

"a0"		 { return sym(Terminals.A0); }
"a1"		 { return sym(Terminals.A1); }
"a2"		 { return sym(Terminals.A2); }
"a3"		 { return sym(Terminals.A3); }
"t0"		 { return sym(Terminals.T0); }
"t1"		 { return sym(Terminals.T1); }
"t2"		 { return sym(Terminals.T2); }
"t3"		 { return sym(Terminals.T3); }
"t4"		 { return sym(Terminals.T4); }
"t5"		 { return sym(Terminals.T5); }
"t6"		 { return sym(Terminals.T6); }
"t7"		 { return sym(Terminals.T7); }
"t8"		 { return sym(Terminals.T8); }
"t9"		 { return sym(Terminals.T9); }
"s0"		 { return sym(Terminals.S0); }
"s1"		 { return sym(Terminals.S1); }
"s2"		 { return sym(Terminals.S2); }
"s3"		 { return sym(Terminals.S3); }
"s4"		 { return sym(Terminals.S4); }
"s5"		 { return sym(Terminals.S5); }
"s6"		 { return sym(Terminals.S6); }
"s7"		 { return sym(Terminals.S7); }
"v0"		 { return sym(Terminals.V0); }
"v1"		 { return sym(Terminals.V1); }
"sp"		 { return sym(Terminals.SP); }
"fp"		 { return sym(Terminals.FP); }
"ra"		 { return sym(Terminals.RA); }

{Identifier} { return sym(Terminals.IDENTIFIER); }
{IntLiteral} { return sym(Terminals.INTEGER_LITERAL); }

// fall through errors
.            { throw new beaver.Scanner.Exception("illegal character \"" + yytext() + "\" at line " + yyline + "," + yycolumn); }
