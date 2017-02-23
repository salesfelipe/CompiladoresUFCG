package compiler.generated;
import java_cup.runtime.*;
import compiler.core.Token;

%%

%public
%class Scanner
%unicode
%line
%column
%cup
%cupdebug

%{
   StringBuffer string = new StringBuffer();

  private Symbol symbol(int type) {
	return new Token(type);
  }

  private Symbol symbol(int type, Object value) {
	return new Token(type, value);
  }
%}

/* White spaces*/
LineTerminator = \r|\n|\r\n
WhiteSpace     = {LineTerminator} | [ \t\f]

/* Comments */
Comment = "/**" ( [^*] | \*+ [^/*] )* "*"+ "/"

/* Identifiers */
Identifier = [:jletter:][:jletterdigit:]*

/* Integer literals */
DecimalLiteral = 0 | [1-9][0-9]*

%%

<YYINITIAL> {

    /* Keywords Pascal*/
    "program"                      	{ return symbol(sym.PROGRAM); }
    "and"                     		{ return symbol(sym.AND); }
    "array"                      	{ return symbol(sym.ARRAY); }
    "begin"                      	{ return symbol(sym.BEGIN); }
    "label"                      	{ return symbol(sym.LABEL); }
    
    /* Separators */
    "("                             { return symbol(sym.LPAREN); }
    ")"                             { return symbol(sym.RPAREN); }
    ";"                             { return symbol(sym.SEMICOLON); }
    ","                             { return symbol(sym.COMMA); }
    "."   		  				    { return symbol(sym.DOT); }
    
    "+"								{ return symbol(sym.PLUS); }
    "-"								{ return symbol(sym.MINUS); }
    
    /* Comments*/
    {Comment}                       { /* just ignore it */ }
    
    /* White spaces */
    {WhiteSpace}				    { /*just ignore it*/ }
    
    /* Identifier*/
    {Identifier} 					{ return symbol(sym.IDENTIFIER,yytext());}
    
    {DecimalLiteral}                { return symbol(sym.INTEGER_LITERAL, new Integer(yytext())); 

}
