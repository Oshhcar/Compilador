/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizador.ast.instruccion;

import analizador.ast.entorno.Entorno;

/**
 *
 * @author oscar
 */
public class PrintTabla extends Instruccion{

    public PrintTabla(int linea, int columna) {
        super(linea, columna);
    }

    @Override
    public Object ejecutar(Entorno e, Object salida) {
        e.recorrer();
        return null;
    }
    
}
