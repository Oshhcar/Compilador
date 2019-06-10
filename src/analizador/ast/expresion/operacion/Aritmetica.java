/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizador.ast.expresion.operacion;

import analizador.ast.entorno.Entorno;
import analizador.ast.entorno.Tipo;
import analizador.ast.expresion.Expresion;

/**
 *
 * @author oscar
 */
public class Aritmetica extends Operacion {

    public Aritmetica(Expresion op1, Expresion op2, Operador operador, int linea, int columna) {
        super(op1, op2, operador, linea, columna);
    }

    @Override
    public Tipo getTipo(Entorno e, Object salida) {
        Tipo tipOp1 = this.getOp1().getTipo(e, salida);
        Tipo tipOp2 = this.getOp2().getTipo(e, salida);

        if (tipOp1 != null && tipOp2 != null) {
            if (tipOp1 == Tipo.STRING || tipOp2 == Tipo.STRING) {
                if (this.getOperador() == Operacion.Operador.SUMA) {
                    return Tipo.STRING;
                }
            } else if (tipOp1 != Tipo.BOOLEAN && tipOp2 != Tipo.BOOLEAN) {
                if (tipOp1 == Tipo.DOUBLE || tipOp2 == Tipo.DOUBLE) {
                    return Tipo.DOUBLE;
                } else if (tipOp1 == Tipo.INT || tipOp2 == Tipo.INT) {
                    return Tipo.INT;
                } else if (tipOp1 == Tipo.CHAR && tipOp2 == Tipo.CHAR) {
                    return Tipo.INT;
                }
            }
        }
        return null;
    }

    @Override
    public Object getValor(Entorno e, Object salida) {
        Tipo tipDominante = this.getTipo(e, salida);

        if (tipDominante != null) {
            switch (tipDominante) {
                case STRING:
                    return this.getOp1().getValor(e, salida).toString() + this.getOp2().getValor(e, salida).toString();
                case DOUBLE: {
                    Double valOp1 = this.getDouble(this.getOp1().getTipo(e, salida), this.getOp1().getValor(e, salida));
                    Double valOp2 = this.getDouble(this.getOp2().getTipo(e, salida), this.getOp2().getValor(e, salida));
                    if (valOp1 != null && valOp2 != null) {
                        switch (this.getOperador()) {
                            case SUMA:
                                return valOp1 + valOp2;
                            case RESTA:
                                return valOp1 - valOp2;
                            case MULTIPLICACION:
                                return valOp1 * valOp2;
                            case DIVISION:
                                return valOp1 / valOp2;
                            case MODULO:
                                return valOp1 % valOp2;
                        }
                    }
                    break;
                }
                case INT: {
                    Integer valOp1 = this.getInt(this.getOp1().getTipo(e, salida), this.getOp1().getValor(e, salida));
                    Integer valOp2 = this.getInt(this.getOp2().getTipo(e, salida), this.getOp2().getValor(e, salida));
                    switch (this.getOperador()) {
                        case SUMA:
                            return valOp1 + valOp2;
                        case RESTA:
                            return valOp1 - valOp2;
                        case MULTIPLICACION:
                            return valOp1 * valOp2;
                        case DIVISION:
                            return valOp1 / valOp2;
                        case MODULO:
                            return valOp1 % valOp2;
                    }
                    break;
                }
                default:
                    break;
            }
        }
        return null;
    }

    private Double getDouble(Tipo tipo, Object valor) {
        if (tipo != null && valor != null) {
            switch (tipo) {
                case INT:
                case DOUBLE:
                    return new Double(valor.toString());
                case CHAR:
                    return new Double(valor.toString().charAt(0));
            }
        }
        return null;
    }

    private Integer getInt(Tipo tipo, Object valor) {
        if (tipo != null && valor != null) {
            switch (tipo) {
                case INT:
                case DOUBLE:
                    return new Integer(valor.toString());
                case CHAR:
                    return new Integer(valor.toString().charAt(0));
            }
        }
        return null;
    }
}
