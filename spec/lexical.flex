package compiler.generated;
import java_cup.runtime.*;
import compiler.core.*;

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
	  return new JavaSymbol(type, yyline+1, yycolumn+1);
  }

  private Symbol symbol(int type, Object value) {
	  return new JavaSymbol(type, yyline+1, yycolumn+1, value);
  }
%}

/* White spaces*/
LineTerminator = \r|\n|\r\n
WhiteSpace     = {LineTerminator} | [ \t\f]

/* Comments */
Comment = "(*" ( [^*] | \*+ [^/*] )* "*)"

/* Identifiers */
Identifier = [:jletter:][:jletterdigit:]*

/* Integer literals */
DecimalLiteral = 0 | [1-9][0-9]*

/* String and Character literals */
StringCharacter = [^\r\n\'\\]

/* Float literals */
FloatLiteral  = ({Float1}|{Float2}|{Float3}) {Exponent}? [fF]?
DoubleLiteral = ({Float1}|{Float2}|{Float3}) {Exponent}? [dD]

Float1    = [0-9]+ \. [0-9]*
Float2    = \. [0-9]+
Float3    = [0-9]+
Exponent  = [eE] [+-]? [0-9]+


%state STRING

%%

<YYINITIAL> {

    /* Keywords Pascal*/
    "program"                      	{ return symbol(sym.PROGRAM); }
    "array"                      	  { return symbol(sym.ARRAY); }
    "begin"                      	  { return symbol(sym.BEGIN); }
    "end" 							            { return symbol(sym.END); }
    "label"                       	{ return symbol(sym.LABEL); }
    "const"						             	{ return symbol(sym.CONST); }
    "type"							            { return symbol(sym.TYPE); }
    "var"							              { return symbol(sym.VAR); }
   	"forward"					            	{ return symbol(sym.FORWARD); }
   	"procedure"					          	{ return symbol(sym.PROCEDURE); }
   	"function"				          		{ return symbol(sym.FUNCTION); }
    "uses"                	     		{ return symbol(sym.USES);}
    "goto"                	     		{ return symbol(sym.GOTO);}
    "while"               	     		{ return symbol(sym.WHILE);}
    "with"               		       	{ return symbol(sym.WITH);}
    "do"                  	     		{ return symbol(sym.DO);}
    "until"                      		{ return symbol(sym.UNTIL);}
    "repeat"             		        { return symbol(sym.REPEAT);}
    "for"                  		     	{ return symbol(sym.FOR);}
    "to"                   			    { return symbol(sym.TO);}
    "downto"                   	    { return symbol(sym.DOWNTO);}
    "if"                   			    { return symbol(sym.IF);}
    "then"                   		    { return symbol(sym.THEN);}
    "else"                   		    { return symbol(sym.ELSE);}
    "case"                   		    { return symbol(sym.CASE);}
    "of"                   			    { return symbol(sym.OF);}
    "packed"                        { return symbol(sym.PACKED);}
    "not"                           { return symbol(sym.NOT);}
    "nil"                           { return symbol(sym.NIL);}
    "record"                        { return symbol(sym.RECORD);}
    "set"                           { return symbol(sym.SET);}
    "file"                          { return symbol(sym.FILE);}

    /* Separators */
    "("                             { return symbol(sym.LPAREN); }
    ")"                             { return symbol(sym.RPAREN); }
    "["                             { return symbol(sym.LBRACK); }
    "]"                             { return symbol(sym.RBRACK); }
    ";"                             { return symbol(sym.SEMICOLON); }
    ","                             { return symbol(sym.COMMA); }
    "."   		  				            { return symbol(sym.DOT); }
    ".."   		  				            { return symbol(sym.DOTDOT); }

    /* String literal */
    \'                              { yybegin(STRING); string.setLength(0); }

    /* Operators */
    ":"                             { return symbol(sym.COLON); }
    "+"							              	{ return symbol(sym.PLUS); }
    "-"								              { return symbol(sym.MINUS); }
    "="								              { return symbol(sym.EQUALS); }
    ":="              				      { return symbol(sym.COLONEQUALS); }
    "<>"                            { return symbol(sym.DIFFERENT); }
    "<"                             { return symbol(sym.LESST); }
    ">"                             { return symbol(sym.GREATERT); }
    "<="                            { return symbol(sym.LESSTOE); }
    ">="                            { return symbol(sym.GREATERTOE); }
    "in"                            { return symbol(sym.IN); }
    "or"                            { return symbol(sym.OR); }
    "*"								              { return symbol(sym.MULT); }
    "/"							              	{ return symbol(sym.DIV); }
    "mod"								            { return symbol(sym.MOD); }
    "div"								            { return symbol(sym.DIVWORD); }
    "and"               		        { return symbol(sym.AND); }
    "^"								              { return symbol(sym.XOR); }

    /* Comments*/
    {Comment}                       { /* just ignore it */ }

    /* White spaces */
    {WhiteSpace}				            { /*just ignore it*/ }

    /* Identifier*/
    {Identifier} 					          { System.out.println("ID: " + yytext());
                                      return symbol(sym.IDENTIFIER,yytext());}

    {DecimalLiteral}                { return symbol(sym.INTEGER_LITERAL, new Integer(yytext()));}

    {FloatLiteral}                  { return symbol(sym.FLOATING_POINT_LITERAL, new Float(yytext().substring(0,yylength()-1))); }
    {DoubleLiteral}                 { return symbol(sym.FLOATING_POINT_LITERAL, new Double(yytext().substring(0,yylength()-1))); }

}

<STRING> {
  \'                                { yybegin(YYINITIAL); return symbol(sym.STRING_LITERAL, string.toString()); }

  {StringCharacter}+                { string.append( yytext() ); }

  /* Escape sequences */
  "\\b"                             { string.append( '\b' ); }
  "\\t"                             { string.append( '\t' ); }
  "\\n"                             { string.append( '\n' ); }
  "\\f"                             { string.append( '\f' ); }
  "\\r"                             { string.append( '\r' ); }
  "\\\""                            { string.append( '\"' ); }
  "\\'"                             { string.append( '\'' ); }
  "\\\\"                            { string.append( '\\' ); }

  /* Error cases */
  \\.                               { throw new RuntimeException("ERROR: Illegal escape sequence \""+yytext()+"\""); }
  {LineTerminator}                  { throw new RuntimeException("ERROR: Unterminated string at end of line"); }

}
