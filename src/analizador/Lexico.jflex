package analizador;

import java_cup.runtime.Symbol;
import java.util.ArrayList;
import javax.swing.JTextArea;

%%

%cupsym Sym
%class Lexico
%cup
%public
%unicode
%line
%char
%column


%{
	
	StringBuffer string = new StringBuffer();
	boolean blancos = false;
        
        private String valorError = "";
        private int columnaError = 0;
        private boolean isError = false;

        private ArrayList<ErrorC> errores = new ArrayList<>();

        public ArrayList<ErrorC> getErrores(){
            return this.errores;
        }
        
        private JTextArea salida;
        public void setSalida(JTextArea salida){
            this.salida = salida;
        }

        public void addError(){
            if(this.isError){
                ErrorC error = new ErrorC();
                error.setTipo("Léxico");
                error.setLinea(yyline+1);
                error.setColumna(this.columnaError);
                error.setValor(this.valorError);
                error.setDescripcion("Carácter no reconocido.");
                this.errores.add(error);
                
                this.salida.append("*Error léxico, carácter \"" + this.valorError + "\" no reconocido.");
                this.salida.append("Línea: " + (yyline+1) + " Columna: " + this.columnaError +". \n");

                this.isError = false;
                this.columnaError = 0;
                this.valorError = "";
            }
            
        }
        
	private Symbol symbol(int type) {
                this.addError();
		return new Symbol(type, yyline+1, yycolumn+1);
	}	
  
	private Symbol symbol(int type, Object value) {
                this.addError();
		return new Symbol(type, yyline+1, yycolumn+1, value);
	}
%}

digito = [0-9]
entero = {digito}+
decimal = {digito}+"."{digito}+
letra = [a-zA-ZñÑ]
identificador = ({letra}|"_")({letra}|{digito}|"_")*

finLinea = \r|\n|\r\n
espacioBlanco = {finLinea} | [ \t\f]

COMENT_SIMPLE ="//" [^\r\n]* {finLinea}?
COMENT_MULTI ="/*""/"*([^*/]|[^*]"/"|"*"[^/])*"*"*"*/"

%state STRING
%state CHAR

%%

{COMENT_SIMPLE} 	{/* se ignora */} 
{COMENT_MULTI} 		{/* se ignora */} 

/* Palabras Reservadas*/
<YYINITIAL> "int"			{ return symbol(Sym.int_);}
<YYINITIAL> "double"			{ return symbol(Sym.double_);}
<YYINITIAL> "char"			{ return symbol(Sym.char_);}
<YYINITIAL> "boolean"			{ return symbol(Sym.boolean_);}
<YYINITIAL> "String"			{ return symbol(Sym.string_);}

<YYINITIAL> "print"			{ return symbol(Sym.print_);}
<YYINITIAL> "printTabla"                { return symbol(Sym.printTabla_);}
<YYINITIAL> "if"                        { return symbol(Sym.if_);}
<YYINITIAL> "else"                      { return symbol(Sym.else_);}
<YYINITIAL> "break"                     { return symbol(Sym.break_);}
<YYINITIAL> "while"                     { return symbol(Sym.while_);}
<YYINITIAL> "do"                        { return symbol(Sym.do_);}
<YYINITIAL> "new"                       { return symbol(Sym.new_);}
<YYINITIAL> "for"                       { return symbol(Sym.for_);}

<YYINITIAL> "null"			{ return symbol(Sym.null_);}
<YYINITIAL> "true"			{ return symbol(Sym.true_);}
<YYINITIAL> "false"			{ return symbol(Sym.false_);}


<YYINITIAL>{

\" 					{ string.setLength(0); yybegin(STRING); }
\' 					{ string.setLength(0); yybegin(CHAR); }

"{"					{return symbol(Sym.llaveIzquierda);}
"}"					{return symbol(Sym.llaveDerecha);}
"("					{return symbol(Sym.parIzquierda);}
")"					{return symbol(Sym.parDerecha);}
"["					{return symbol(Sym.corcheteIzquierda);}
"]"					{return symbol(Sym.corcheteDerecha);}
";"					{return symbol(Sym.puntoycoma);}
","					{return symbol(Sym.coma);}
"."					{return symbol(Sym.punto);}
":"					{return symbol(Sym.dospuntos);}
"?"					{return symbol(Sym.interrogacion);}


//Operadores Aritméticos
"+"                 {return symbol(Sym.mas);}
"-"                 {return symbol(Sym.menos);}
"*"                 {return symbol(Sym.asterisco);}  
"/"                 {return symbol(Sym.diagonal);}
"%"                 {return symbol(Sym.modulo);}

"++"                {return symbol(Sym.masmas);}
"--"                {return symbol(Sym.menosmenos);}

//Operadores Relacionales 
">"                 {return symbol(Sym.mayorque);}
"<"                 {return symbol(Sym.menorque);}
">="                {return symbol(Sym.mayorigual);}
"<="                {return symbol(Sym.menorigual);}
"=="                {return symbol(Sym.igualigual);}
"!="                {return symbol(Sym.diferente);}

//Operadores Lógicos
"&&"                {return symbol(Sym.and);}
"||"                {return symbol(Sym.or);}
"!"                 {return symbol(Sym.not);}
"^"                 {return symbol(Sym.xor);}

//Operador Asignacion
"="                 {return symbol(Sym.igual);}
"+="                {return symbol(Sym.masigual);}
"-="                {return symbol(Sym.menosigual);}
"*="                {return symbol(Sym.porigual);}
"/="                {return symbol(Sym.diagonaligual);}

{entero}       		{ return symbol(Sym.entero, yytext());}
{decimal}		{ return symbol(Sym.decimal, yytext());}
{identificador}         { return symbol(Sym.id, yytext());}

/* Espacios en Blanco */
{espacioBlanco}         { if(this.isError){this.valorError += " ";} }

}

<STRING> {
\"                   { yybegin(YYINITIAL);
					   return symbol(Sym.tstring, string.toString()); }
[^\n\r\"\\]+         { string.append( yytext() ); }
\\t                  { string.append('\t'); }
\\n                  { string.append('\n'); }
\\r                  { string.append('\r'); }
\\\"                 { string.append('\"'); }
\\                   { string.append('\\'); }
}

<CHAR> {
\'                   { yybegin(YYINITIAL);
					   return symbol(Sym.tchar, string.toString()); }
[^\n\r\'\\]+         { string.append( yytext() ); }
\\t                  { string.append('\t'); }
\\n                  { string.append('\n'); }
\\r                  { string.append('\r'); }
\\\'                 { string.append('\''); }
\\                   { string.append('\\'); }
}

/* ERRORES LEXICOS */
.		{ 
                    if(!this.isError){
                        this.isError = true;
                        this.columnaError = yycolumn+1;
                    }
                    this.valorError += yytext(); 
                }











