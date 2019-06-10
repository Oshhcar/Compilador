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
public class Relacional extends Operacion {

    public Relacional(Expresion op1, Expresion op2, Operador operador, int linea, int columna) {
        super(op1, op2, operador, linea, columna);
    }

    @Override
    public Tipo getTipo(Entorno e, Object salida) {
        Tipo tipOp1 = this.getOp1().getTipo(e, salida);
        Tipo tipOp2 = this.getOp2().getTipo(e, salida);

        if (tipOp1 != null && tipOp2 != null) {
            if ((tipOp1.isNumero() || tipOp1 == Tipo.CHAR) && (tipOp2.isNumero() || tipOp2 == Tipo.CHAR)) {
                return Tipo.BOOLEAN;
            } else if (tipOp1 == Tipo.STRING && tipOp2 == Tipo.STRING) {
                return Tipo.BOOLEAN;
            } else if (tipOp1 == Tipo.BOOLEAN && tipOp2 == Tipo.BOOLEAN) {
                if (this.getOperador() == Operacion.Operador.IGUAL || this.getOperador() == Operacion.Operador.DIFERENTE) {
                    return Tipo.BOOLEAN;
                }
            }
        }
        return null;
    }

    @Override
    public Object getValor(Entorno e, Object salida) {
        Tipo tipOp1 = this.getOp1().getTipo(e, salida);
        Tipo tipOp2 = this.getOp2().getTipo(e, salida);

        if (tipOp1 != null && tipOp2 != null) {
            if ((tipOp1.isNumero() || tipOp1 == Tipo.CHAR) && (tipOp2.isNumero() || tipOp2 == Tipo.CHAR)) {
                Double valOp1 = this.getDouble(tipOp1, this.getOp1().getValor(e, salida));
                Double valOp2 = this.getDouble(tipOp2, this.getOp2().getValor(e, salida));
                if (valOp1 != null && valOp2 != null) {
                    switch (this.getOperador()) {
                        case MAYORQUE:
                            return valOp1 > valOp2;
                        case MENORQUE:
                            return valOp1 < valOp2;
                        case MAYORIGUAL:
                            return valOp1 >= valOp2;
                        case MENORIGUAL:
                            return valOp1 <= valOp2;
                        case IGUAL:
                            return valOp1.doubleValue() == valOp2.doubleValue();
                        case DIFERENTE:
                            return valOp1.doubleValue() != valOp2.doubleValue();
                    }
                }
            } else if (tipOp1 == Tipo.STRING && tipOp2 == Tipo.STRING) {
                Integer valOp1 = this.getValorCadena(this.getOp1().getValor(e, salida).toString());
                Integer valOp2 = this.getValorCadena(this.getOp2().getValor(e, salida).toString());

                if (valOp1 != null && valOp2 != null) {
                    switch (this.getOperador()) {
                        case MAYORQUE:
                            return valOp1 > valOp2;
                        case MENORQUE:
                            return valOp1 < valOp2;
                        case MAYORIGUAL:
                            return valOp1 >= valOp2;
                        case MENORIGUAL:
                            return valOp1 <= valOp2;
                        case IGUAL:
                            return valOp1.intValue() == valOp2.intValue();
                        case DIFERENTE:
                            return valOp1.intValue() != valOp2.intValue();
                    }
                }

            } else if (tipOp1 == Tipo.BOOLEAN && tipOp2 == Tipo.BOOLEAN) {
                Object val1 = this.getOp1().getValor(e, salida).toString();
                Object val2 = this.getOp2().getValor(e, salida).toString();

                if (val1 != null && val2 != null) {
                    Boolean valOp1 = Boolean.valueOf(val1.toString());
                    Boolean valOp2 = Boolean.valueOf(val2.toString());
                    if (this.getOperador() == Operacion.Operador.IGUAL) {
                        return valOp1 == valOp2;
                    } else if (this.getOperador() == Operacion.Operador.DIFERENTE) {
                        return valOp1 != valOp2;
                    }
                }

            }
        }

        return null;
    }

    public Integer getValorCadena(String cad) {
        if (cad != null) {
            int res = 0;
            for (int i = 0; i < cad.length(); i++) {
                res = res + cad.charAt(i);
            }
            return res;
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
}
