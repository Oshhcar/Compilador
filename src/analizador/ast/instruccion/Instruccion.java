/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizador.ast.instruccion;

import analizador.ast.NodoAst;
import analizador.ast.entorno.Entorno;

/**
 *
 * @author oscar
 */
public abstract class Instruccion extends NodoAst{
    
    public Instruccion(int linea, int columna) {
        super(linea, columna);
    }
    
    public abstract Object ejecutar(Entorno e, Object salida);
}
