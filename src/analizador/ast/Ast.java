/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizador.ast;

import analizador.ast.entorno.Entorno;
import analizador.ast.expresion.Expresion;
import analizador.ast.instruccion.Instruccion;
import java.util.ArrayList;

/**
 *
 * @author oscar
 */
public class Ast {
    private final ArrayList<NodoAst> nodos;

    public Ast(ArrayList<NodoAst> nodos) {
        this.nodos = nodos;
    }
    
    public void ejecutar(Object salida){
        Entorno global = new Entorno(null);
        
        for(NodoAst nodo: nodos){
            if(nodo instanceof Instruccion){
                ((Instruccion) nodo).ejecutar(global, salida);
            } else {
                ((Expresion) nodo).getValor(global, salida);
            }
        }
    }
}
