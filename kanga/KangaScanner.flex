package kanga; // The generated parser will belong to this package 

import kanga.KangaParser.Terminals; 
// The terminals are implicitly defined in the parser
%%

// define the signature for the generated scanner
%public
%final
%class KangaScanner 
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
InputCharacter = [^\r\n]
WhiteSpace     = {LineTerminator} | [ \t\f]
Identifier = [:jletter:][:jletterdigit:]*
IntLiteral = [1-9][0-9]* | "0"

/* comments */
InputCharacter = [^\r\n]
TraditionalComment   = "/*" [^*] ~"*/" | "/*" "*"+ "/"
EndOfLineComment     = "//" {InputCharacter}* {LineTerminator}?
DocumentationComment = "/**" {CommentContent} "*"+ "/"
CommentContent       = ( [^*] | \*+ [^/*] )*

Comment = {TraditionalComment} | {EndOfLineComment} | {DocumentationComment}
%%

// discard whitespace information and comments
{WhiteSpace}  { }
{Comment}  { }

// token definitions
"["          { return sym(Terminals.LSQPAREN); }
"]"          { return sym(Terminals.RSQPAREN); }

"MAIN"		 { return sym(Terminals.MAIN); }
"NOOP"    	 { return sym(Terminals.NOOP); }
"ERROR"      { return sym(Terminals.ERROR); }
"CJUMP"      { return sym(Terminals.CJUMP); }
"JUMP"    	 { return sym(Terminals.JUMP); }
"HSTORE"     { return sym(Terminals.HSTORE); }
"HLOAD"      { return sym(Terminals.HLOAD); }
"MOVE"     	 { return sym(Terminals.MOVE); }
"PRINT"      { return sym(Terminals.PRINT); }
"ALOAD"      { return sym(Terminals.ALOAD); }
"ASTORE"     { return sym(Terminals.ASTORE); }
"PASSARG"    { return sym(Terminals.PASSARG); }
"CALL"     	 { return sym(Terminals.CALL); }
"END"     	 { return sym(Terminals.END); }
"HALLOCATE"  { return sym(Terminals.HALLOCATE); }
"SPILLEDARG"  { return sym(Terminals.SPILLEDARG); }
"LT"     	 { return sym(Terminals.LT); }
"PLUS"       { return sym(Terminals.PLUS); }
"MINUS"		 { return sym(Terminals.MINUS); }
"TIMES"		 { return sym(Terminals.TIMES); }
"a0"         { return sym(Terminals.A0); }
"a1"         { return sym(Terminals.A1); }
"a2"         { return sym(Terminals.A2); }
"a3"         { return sym(Terminals.A3); }
"t0"         { return sym(Terminals.T0); }
"t1"         { return sym(Terminals.T1); }
"t2"         { return sym(Terminals.T2); }
"t3"         { return sym(Terminals.T3); }
"t4"         { return sym(Terminals.T4); }
"t5"         { return sym(Terminals.T5); }
"t6"         { return sym(Terminals.T6); }
"t7"         { return sym(Terminals.T7); }
"t8"         { return sym(Terminals.T8); }
"t9"         { return sym(Terminals.T9); }
"s0"         { return sym(Terminals.S0); }
"s1"         { return sym(Terminals.S1); }
"s2"         { return sym(Terminals.S2); }
"s3"         { return sym(Terminals.S3); }
"s4"         { return sym(Terminals.S4); }
"s5"         { return sym(Terminals.S5); }
"s6"         { return sym(Terminals.S6); }
"s7"         { return sym(Terminals.S7); }
"v0"         { return sym(Terminals.V0); }
"v1"         { return sym(Terminals.V1); }

{Identifier} { return sym(Terminals.IDENTIFIER); }
{IntLiteral} { return sym(Terminals.INTEGER_LITERAL); }

// fall through errors
.            { throw new beaver.Scanner.Exception("illegal character \"" + yytext() + "\" at line " + yyline + "," + yycolumn); }
