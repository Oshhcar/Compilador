/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizador.ast.instruccion;

import analizador.ast.entorno.Arreglo;
import analizador.ast.entorno.Entorno;
import analizador.ast.entorno.Rol;
import analizador.ast.entorno.Simbolo;
import analizador.ast.entorno.Tipo;
import analizador.ast.expresion.Expresion;
import analizador.ast.expresion.Identificador;
import javax.swing.JTextArea;

/**
 *
 * @author oscar
 */
public class Asignacion extends Instruccion {

    private final Identificador id;
    private final Expresion valor;

    public Asignacion(Identificador id, int linea, int columna) {
        super(linea, columna);
        this.id = id;
        this.valor = null;
    }

    public Asignacion(Identificador id, Expresion valor, int linea, int columna) {
        super(linea, columna);
        this.id = id;
        this.valor = valor;
    }

    @Override
    public Object ejecutar(Entorno e, Object salida) {
        Simbolo tmp = e.get(this.id.getId());
        if (tmp != null) {
            Tipo tipValor = this.valor.getTipo(e, salida);
            if (tipValor != null) {
                if (tmp.getTipo() == tipValor) {
                    Object valValor = this.valor.getValor(e, salida);
                    if (valValor != null) {
                        if (tmp.getRol() == Rol.ARREGLO) {
                            if(valValor instanceof Arreglo){
                                if(tmp.getTamaño() == ((Arreglo) valValor).getDimensiones()){
                                    tmp.setValor(valValor);
                                }else {
                                    System.err.println("No son de las mismas dimensiones");
                                }
                            }else {
                                System.err.println("No se esta asignano arreglo");
                            }
                        } else {
                            tmp.setValor(valValor);
                        }
                        return null;
                    }
                }
            }
            ((JTextArea) salida).append("*Error Semántico, no se puede asignar el valor. ");
            ((JTextArea) salida).append("Línea: " + this.getLinea() + " Columna: " + this.getColumna() + ". \n");

        } else {
            ((JTextArea) salida).append("*Error Semántico, no se ha declarado la variable: " + this.id.getId() + ". ");
            ((JTextArea) salida).append("Línea: " + this.getLinea() + " Columna: " + this.getColumna() + ". \n");
        }
        return null;
    }

    /**
     * @return the id
     */
    public Identificador getId() {
        return id;
    }

    /**
     * @return the valor
     */
    public Expresion getValor() {
        return valor;
    }

}
