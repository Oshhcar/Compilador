/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizador.ast.instruccion;

import analizador.ast.entorno.Arreglo;
import analizador.ast.entorno.Entorno;
import analizador.ast.entorno.Tipo;
import analizador.ast.expresion.Expresion;
import analizador.ast.expresion.Identificador;
import java.util.ArrayList;

/**
 *
 * @author oscar
 */
public class AsignacionArreglo extends Instruccion {

    private final Identificador id;
    private final ArrayList<Expresion> dimensiones;
    private final Expresion valor;

    public AsignacionArreglo(Identificador id, ArrayList<Expresion> dimensiones, Expresion valor, int linea, int columna) {
        super(linea, columna);
        this.id = id;
        this.dimensiones = dimensiones;
        this.valor = valor;
    }

    @Override
    public Object ejecutar(Entorno e, Object salida) {
        Object arreglo = this.id.getValor(e, salida);
        if (arreglo != null) {
            if (arreglo instanceof Arreglo) {
                Arreglo aux = (Arreglo) arreglo;

                int i = 0;
                while (i < this.dimensiones.size()) {
                    Expresion exp = this.dimensiones.get(i++);
                    Tipo tipExp = exp.getTipo(e, salida);
                    if (tipExp != null) {
                       if(tipExp == Tipo.INT){
                           Object valExp = exp.getValor(e, salida);
                           if(valExp != null){
                               int dim = Integer.valueOf(valExp.toString());
                               
                               if(i != this.dimensiones.size()){
                                   Object posDim = aux.get(dim);
                                   if(posDim instanceof Arreglo){
                                       aux = (Arreglo) posDim;
                                       continue;
                                   }
                                   System.err.println("Error, ya no hay mas dimensiones");
                                   return null;
                               } else {
                                   Tipo tipValor = this.valor.getTipo(e, salida);
                                   if(tipValor != null){
                                       if(tipValor == aux.getTipo()){
                                           Object valor = this.valor.getValor(e, salida);
                                           if(valor != null){
                                               aux.setValor(dim, valor);
                                               return null;
                                           }
                                           System.err.println("Error en valor");
                                           return null;
                                       }
                                       System.err.println("No son del mismo tipo");
                                       return null;
                                   }
                                   System.err.println("Error en valor");
                                   return null;
                               }
                           }
                       } 
                    }
                    return null;
                }
            } else {
                System.err.println("No es arreglo");
            }
        }
        return null;
    }

}
