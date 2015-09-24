package minijava; // The generated parser will belong to this package 

import minijava.MJParser.Terminals; 
// The terminals are implicitly defined in the parser
%%

// define the signature for the generated scanner
%public
%final
%class MJScanner 
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
  private beaver.Symbol sym(short id, Object value) {
    return new beaver.Symbol(id, yyline + 1, yycolumn + 1, yylength(), value);
  }
%}

LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
WhiteSpace     = {LineTerminator} | [ \t\f]
Integer        = [1-9][0-9]* | 0
Identifier     = [a-zA-Z][a-zA-Z0-9_]*
LineComment    = "//" {InputCharacter}*
MultiComment   = "/*" [^*] ~"*/" | "/*" "*"+ "/"

%%

// discard whitespace information and comments
{WhiteSpace}          { }
{LineComment}         { }
{MultiComment}        { }

"class"               { return sym(Terminals.CLASS); }
"extends"             { return sym(Terminals.EXTENDS); }
"public"              { return sym(Terminals.PUBLIC); }
"static"              { return sym(Terminals.STATIC); }
"void"                { return sym(Terminals.VOID); }
"main"                { return sym(Terminals.MAIN); }
"String"              { return sym(Terminals.STRINGTYPE); }
"System.out.println"  { return sym(Terminals.SYSOUT); }
"length"              { return sym(Terminals.LENGTH); }

"if"                  { return sym(Terminals.IF); }
"else"                { return sym(Terminals.ELSE); }
"while"               { return sym(Terminals.WHILE); }
"return"              { return sym(Terminals.RETURN); }

"int"                 { return sym(Terminals.INTTYPE); }
"boolean"             { return sym(Terminals.BOOLEAN); }
"false"               { return sym(Terminals.FALSE); }
"true"                { return sym(Terminals.TRUE); }
"this"                { return sym(Terminals.THIS); }
"new"                 { return sym(Terminals.NEW); }

"("                   { return sym(Terminals.LBRACE); }
")"                   { return sym(Terminals.RBRACE); }
"{"                   { return sym(Terminals.LCURLYBRACE); }
"}"                   { return sym(Terminals.RCURLYBRACE); }
"["                   { return sym(Terminals.LBRACKET); }
"]"                   { return sym(Terminals.RBRACKET); }

","                   { return sym(Terminals.COMMA); }
"."                   { return sym(Terminals.DOT); }
"+"                   { return sym(Terminals.PLUS); }
"-"                   { return sym(Terminals.MINUS); }
"*"                   { return sym(Terminals.TIMES); }
"<"                   { return sym(Terminals.LESSTHAN); }
"&&"                  { return sym(Terminals.AND); }
"!"                   { return sym(Terminals.BANG); }
";"                   { return sym(Terminals.SEMICOLON); }
"="                   { return sym(Terminals.EQUALS); }

{Integer}             { return sym(Terminals.INTEGER); }
{Identifier}          { return sym(Terminals.IDENTIFIER); }

// fall through errors
.                     { throw new beaver.Scanner.Exception("illegal character \"" + yytext() + "\" at line " + yyline + "," + yycolumn); }
