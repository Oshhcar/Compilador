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
public class Logica extends Operacion {

    public Logica(Expresion op1, Expresion op2, Operador operador, int linea, int columna) {
        super(op1, op2, operador, linea, columna);
    }

    public Logica(Expresion op1, Operador operador, int linea, int columna) {
        super(op1, null, operador, linea, columna);
    }

    @Override
    public Tipo getTipo(Entorno e, Object salida) {
        if (this.getOp2() != null) {
            Tipo tipOp1 = this.getOp1().getTipo(e, salida);
            Tipo tipOp2 = this.getOp2().getTipo(e, salida);

            if (tipOp1 != null && tipOp2 != null) {
                if (tipOp1 == Tipo.BOOLEAN && tipOp2 == Tipo.BOOLEAN) {
                    return Tipo.BOOLEAN;
                }
            }
        } else {
            Tipo tipOp1 = this.getOp1().getTipo(e, salida);
            if (tipOp1 != null) {
                if (tipOp1 == Tipo.BOOLEAN) {
                    return Tipo.BOOLEAN;
                }
            }
        }
        return null;
    }

    @Override
    public Object getValor(Entorno e, Object salida) {
        if (this.getTipo(e, salida) == Tipo.BOOLEAN) {
            if (this.getOp2() != null) {
                Object val1 = this.getOp1().getValor(e, salida);
                Object val2 = this.getOp2().getValor(e, salida);

                if (val1 != null && val2 != null) {
                    boolean valOp1 = Boolean.valueOf(val1.toString());
                    boolean valOp2 = Boolean.valueOf(val2.toString());
                    
                    switch(this.getOperador()){
                        case AND:
                            return valOp1 && valOp2;
                        case OR:
                            return valOp1 || valOp2;
                        case XOR:
                            return valOp1 ^ valOp2;
                    }
                    
                }
            } else {
                Object val1 = this.getOp1().getValor(e, salida);
                
                if(val1 != null){
                    boolean valOp1 = Boolean.valueOf(val1.toString());
                    if(this.getOperador() == Operacion.Operador.NOT){
                        return !valOp1;
                    }
                }
            }
        }
        return null;
    }

}
