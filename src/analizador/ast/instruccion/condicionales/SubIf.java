/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizador.ast.instruccion.condicionales;

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
public class SubIf extends Instruccion {

    private final Expresion condicion;
    private final ArrayList<NodoAst> bloques;
    private final boolean isSino;

    public SubIf(Expresion condicion, ArrayList<NodoAst> bloques, int linea, int columna) {
        super(linea, columna);
        this.condicion = condicion;
        this.bloques = bloques;
        this.isSino = false;
    }

    public SubIf(ArrayList<NodoAst> bloques, int linea, int columna) {
        super(linea, columna);
        this.condicion = null;
        this.bloques = bloques;
        this.isSino = true;
    }

    @Override
    public Object ejecutar(Entorno e, Object salida) {
        if (!this.isSino) {
            Tipo tipCond = this.condicion.getTipo(e, salida);
            if (tipCond != null) {
                if (tipCond == Tipo.BOOLEAN) {
                    Object valCond = this.condicion.getValor(e, salida);
                    if (valCond != null) {
                        if (Boolean.valueOf(valCond.toString())) {
                            Entorno local = new Entorno(e);
                            if (this.bloques != null) {
                                for (NodoAst bloque : this.bloques) {
                                    if (bloque instanceof Instruccion) {
                                        if (bloque instanceof Break) {
                                            return bloque;
                                        } else {
                                            Object obj = ((Instruccion) bloque).ejecutar(local, salida);
                                            if (obj != null) {
                                                if (obj instanceof Break) {
                                                    return obj;
                                                }
                                            }
                                        }
                                    } else {
                                        Object obj = ((Expresion) bloque).getValor(local, salida);
                                        /*RETURNS*/
                                    }
                                }
                            }
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
            }
        } else {
            Entorno local = new Entorno(e);
            if (this.bloques != null) {
                for (NodoAst bloque : this.bloques) {
                    if (bloque instanceof Instruccion) {
                        if (bloque instanceof Break) {
                            return bloque;
                        } else {
                            Object obj = ((Instruccion) bloque).ejecutar(local, salida);
                            if (obj != null) {
                                if (obj instanceof Break) {
                                    return obj;
                                }
                            }
                        }
                    } else {
                        Object obj = ((Expresion) bloque).getValor(local, salida);
                        /*RETURNS*/
                    }
                }
            }
        }

        return null;
    }

}
