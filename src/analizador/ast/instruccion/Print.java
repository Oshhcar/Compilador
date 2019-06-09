/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizador.ast.instruccion;

import analizador.ast.entorno.Entorno;
import analizador.ast.expresion.Expresion;
import javax.swing.JTextArea;

/**
 *
 * @author oscar
 */
public class Print extends Instruccion {

    private final Expresion toPrint;

    public Print(Expresion toPrint, int linea, int columna) {
        super(linea, columna);
        this.toPrint = toPrint;
    }

    @Override
    public Object ejecutar(Entorno e, Object salida) {
        Object valPrint = this.toPrint.getValor(e, salida);

        if (valPrint != null) {
            ((JTextArea) salida).append(valPrint.toString()+"\n");
        }
        return null;
    }

}
