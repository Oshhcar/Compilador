/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizador.ast.expresion;

import analizador.ast.entorno.Entorno;
import analizador.ast.entorno.Tipo;

/**
 *
 * @author oscar
 */
public class Literal extends Expresion{
    private final Tipo tipo;
    private final Object valor;
    
    public Literal(Tipo tipo, Object valor, int linea, int columna) {
        super(linea, columna);
        this.tipo = tipo;
        this.valor = valor;
    }

    @Override
    public Tipo getTipo(Entorno e, Object salida) {
        return this.tipo;
    }

    @Override
    public Object getValor(Entorno e, Object salida) {
        return this.valor;
    }
    
}
