/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizador.ast.instruccion.ciclos;

import analizador.ast.NodoAst;
import analizador.ast.entorno.Entorno;
import analizador.ast.entorno.Tipo;
import analizador.ast.expresion.Expresion;
import analizador.ast.instruccion.Instruccion;
import java.util.ArrayList;

/**
 *
 * @author oscar
 */
public class For extends Instruccion {

    private final Instruccion forInit;
    private final Expresion condicion;
    private final Expresion update;
    private final ArrayList<NodoAst> bloques;

    public For(Instruccion forInit, Expresion condicion, Expresion update, ArrayList<NodoAst> bloques, int linea, int columna) {
        super(linea, columna);
        this.forInit = forInit;
        this.condicion = condicion;
        this.update = update;
        this.bloques = bloques;
    }

    @Override
    public Object ejecutar(Entorno e, Object salida) {
        Entorno local = new Entorno(e);

        forInit.ejecutar(local, salida);

        while (true) {
            Tipo tipCondicion = this.condicion.getTipo(local, salida);
            if (tipCondicion != null) {
                if (tipCondicion == Tipo.BOOLEAN) {
                    Object valCondicion = this.condicion.getValor(local, salida);
                    if (valCondicion != null) {
                        if (Boolean.valueOf(valCondicion.toString())){
                            if(this.bloques == null){
                                break;
                            } else {
                                for(NodoAst bloque: this.bloques){
                                    if(bloque instanceof Instruccion){
                                        ((Instruccion) bloque).ejecutar(local, salida);
                                    } else {
                                        ((Expresion) bloque).getValor(local, salida);
                                    }
                                }
                                this.update.getValor(local, salida);
                                continue;
                            }
                        } else {
                            break;
                        }
                    }
                }
            }
            break;
        }

        return null;
    }

}
