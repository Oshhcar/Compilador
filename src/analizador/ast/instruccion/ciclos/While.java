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
import analizador.ast.instruccion.Break;
import analizador.ast.instruccion.Instruccion;
import java.util.ArrayList;

/**
 *
 * @author oscar
 */
public class While extends Instruccion {

    private final Expresion condicion;
    private final ArrayList<NodoAst> bloques;

    public While(Expresion condicion, ArrayList<NodoAst> bloques, int linea, int columna) {
        super(linea, columna);
        this.condicion = condicion;
        this.bloques = bloques;
    }

    @Override
    public Object ejecutar(Entorno e, Object salida) {
        if (this.bloques != null) {
            while (true) {
                Entorno local = new Entorno(e);
                Tipo tipCond = this.condicion.getTipo(e, salida);
                if (tipCond != null) {
                    if (tipCond == Tipo.BOOLEAN) {
                        Object valCond = this.condicion.getValor(e, salida);
                        if (valCond != null) {
                            if (Boolean.valueOf(valCond.toString())) {
                                for (NodoAst bloque : this.bloques) {
                                    if (bloque instanceof Instruccion) {
                                        if (bloque instanceof Break) {
                                            return null;
                                        } else {
                                            Object obj = ((Instruccion) bloque).ejecutar(local, salida);

                                            if (obj != null) {
                                                if (obj instanceof Break) {
                                                    return null;
                                                }/*return*/
                                            }
                                        }
                                    } else {
                                        Object obj = ((Expresion) bloque).getValor(local, salida);
                                        /*return*/
                                    }
                                }
                                continue;
                            } else {
                                break;
                            }
                        }
                    }
                }
                return null;
            }
        }
        return null;
    }

}
