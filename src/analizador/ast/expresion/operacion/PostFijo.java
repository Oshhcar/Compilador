/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizador.ast.expresion.operacion;

import analizador.ast.entorno.Entorno;
import analizador.ast.entorno.Simbolo;
import analizador.ast.entorno.Tipo;
import analizador.ast.expresion.Expresion;
import analizador.ast.expresion.Identificador;

/**
 *
 * @author oscar
 */
public class PostFijo extends Operacion {

    public PostFijo(Expresion op1, Operador operador, int linea, int columna) {
        super(op1, null, operador, linea, columna);
    }

    @Override
    public Tipo getTipo(Entorno e, Object salida) {
        if (this.getOp1() instanceof Identificador) {
            Tipo tip = this.getOp1().getTipo(e, salida);
            if (tip != null) {
                if (tip.isNumero() || tip == Tipo.CHAR) {
                    return tip;
                }
            }
        }
        return null;
    }

    @Override
    public Object getValor(Entorno e, Object salida) {
        if (this.getOp1() instanceof Identificador) {
            Tipo tip = this.getOp1().getTipo(e, salida);
            if (tip != null) {
                if (tip.isNumero() || tip == Tipo.CHAR) {
                    Object valor = this.getOp1().getValor(e, salida);
                    if (valor != null) {
                        Simbolo tmp = e.get(((Identificador) this.getOp1()).getId());
                        switch (tip) {
                            case INT:
                                if (this.getOperador() == Operacion.Operador.AUMENTO) {
                                    tmp.setValor(Integer.valueOf(valor.toString()) + 1);
                                } else {
                                    tmp.setValor(Integer.valueOf(valor.toString()) - 1);
                                }
                                break;
                            case DOUBLE:
                                if (this.getOperador() == Operacion.Operador.AUMENTO) {
                                    tmp.setValor(Double.valueOf(valor.toString()) + 1);
                                } else {
                                    tmp.setValor(Double.valueOf(valor.toString()) - 1);
                                }
                                break;
                            case CHAR:
                                if (this.getOperador() == Operacion.Operador.AUMENTO) {
                                    int a = Integer.valueOf(valor.toString().charAt(0)) + 1;
                                    tmp.setValor((char) a);
                                } else {
                                    int a = Integer.valueOf(valor.toString().charAt(0)) - 1;
                                    tmp.setValor((char) a);
                                }
                        }
                        return valor;
                    }
                }
            }
        }
        return null;
    }
}
