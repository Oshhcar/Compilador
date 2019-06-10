/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizador.ast.instruccion;

import analizador.ast.entorno.Entorno;
import analizador.ast.entorno.Simbolo;
import analizador.ast.entorno.Tipo;
import analizador.ast.expresion.Expresion;
import java.util.ArrayList;
import javax.swing.JTextArea;

/**
 *
 * @author oscar
 */
public class Declaracion extends Instruccion {

    private final Tipo tipo;
    private final ArrayList<Asignacion> asignaciones;

    public Declaracion(Tipo tipo, ArrayList<Asignacion> asignaciones, int linea, int columna) {
        super(linea, columna);
        this.tipo = tipo;
        this.asignaciones = asignaciones;
    }

    @Override
    public Object ejecutar(Entorno e, Object salida) {

        for (Asignacion asigna : this.asignaciones) {
            String id = asigna.getId().getId();
            Simbolo tmp = e.get(id);
            if (tmp == null) {
                Expresion valor = asigna.getValor();
                if (valor != null) {
                    Tipo tipValor = valor.getTipo(e, salida);
                    if (tipValor != null) {
                        if (this.tipo == tipValor) {
                            Object valValor = valor.getValor(e, salida);
                            if (valValor != null) {
                                tmp = new Simbolo(this.tipo, id, valValor);
                                e.add(id, tmp);
                                continue;
                            }
                        }
                    }

                    ((JTextArea) salida).append("*Error Semántico, no se puede asignar el valor. ");
                    ((JTextArea) salida).append("Línea: " + asigna.getLinea() + " Columna: " + asigna.getColumna() + ". \n");

                } else {
                    tmp = new Simbolo(this.tipo, id);
                    e.add(id, tmp);
                }
            } else {
                ((JTextArea) salida).append("*Error Semántico, Ya se ha declarado la variable: " + asigna.getId().getId() + ". ");
                ((JTextArea) salida).append("Línea: " + asigna.getLinea() + " Columna: " + asigna.getColumna() + ". \n");
            }
        }

        return null;
    }

}
