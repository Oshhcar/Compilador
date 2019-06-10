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
public class DoWhile extends Instruccion {

    private final ArrayList<NodoAst> bloques;
    private final Expresion condicion;

    public DoWhile(ArrayList<NodoAst> bloques, Expresion condicion, int linea, int columna) {
        super(linea, columna);
        this.bloques = bloques;
        this.condicion = condicion;
    }

    @Override
    public Object ejecutar(Entorno e, Object salida) {
        if (this.bloques != null) {
            
            while (true) {
                Entorno local = new Entorno(e);
                for (NodoAst bloque : this.bloques) {
                    if (bloque instanceof Instruccion) {
                        if (bloque instanceof Break) {
                            return null;
                        } else {
                            Object obj = ((Instruccion) bloque).ejecutar(local, salida);
                            if (obj != null) {
                                if (obj instanceof Break) {
                                    return null;
                                }
                                /*return*/
                            }
                        }
                    } else {
                        Object obj = ((Expresion) bloque).getValor(local, salida);
                        /*RETURN*/
                    }
                }
                Tipo tipCond = this.condicion.getTipo(e, salida);
                if (tipCond != null) {
                    if (tipCond == Tipo.BOOLEAN) {
                        Object valor = this.condicion.getValor(e, salida);
                        if (valor != null) {
                            if (Boolean.valueOf(valor.toString())) {
                                continue;
                            }
                        }
                    }
                }
                break;
            }
        }
        return null;
    }

}
