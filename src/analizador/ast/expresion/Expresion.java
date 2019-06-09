/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizador.ast.expresion;

import analizador.ast.NodoAst;
import analizador.ast.entorno.Entorno;
import analizador.ast.entorno.Tipo;

/**
 *
 * @author oscar
 */
public abstract class Expresion extends NodoAst{
    
    public Expresion(int linea, int columna) {
        super(linea, columna);
    }

    public abstract Tipo getTipo(Entorno e, Object salida);
    public abstract Object getValor(Entorno e, Object salida);
}
