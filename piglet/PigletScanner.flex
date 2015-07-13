package piglet; // The generated parser will belong to this package 

import piglet.PigletParser.Terminals; 
// The terminals are implicitly defined in the parser
%%

// define the signature for the generated scanner
%public
%final
%class PigletScanner 
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
"("          { return sym(Terminals.LPAREN); }
")"          { return sym(Terminals.RPAREN); }
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
"BEGIN"      { return sym(Terminals.BEGIN); }
"RETURN"   	 { return sym(Terminals.RETURN); }
"END"     	 { return sym(Terminals.END); }
"CALL"     	 { return sym(Terminals.CALL); }
"HALLOCATE"  { return sym(Terminals.HALLOCATE); }
"LT"     	 { return sym(Terminals.LT); }
"PLUS"       { return sym(Terminals.PLUS); }
"MINUS"		 { return sym(Terminals.MINUS); }
"TIMES"		 { return sym(Terminals.TIMES); }
"TEMP"		 { return sym(Terminals.TEMP); }

{Identifier} { return sym(Terminals.IDENTIFIER); }
{IntLiteral} { return sym(Terminals.INTEGER_LITERAL); }

// fall through errors
.            { throw new beaver.Scanner.Exception("illegal character \"" + yytext() + "\" at line " + yyline + "," + yycolumn); }
