/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizador.ast.expresion;

import analizador.ast.entorno.Arreglo;
import analizador.ast.entorno.Entorno;
import analizador.ast.entorno.Tipo;
import java.util.ArrayList;

/**
 *
 * @author oscar
 */
public class AccesoArreglo extends Expresion{
    private final Identificador id;
    private final ArrayList<Expresion> dimensiones;
    
    public AccesoArreglo(Identificador id, ArrayList<Expresion> dimensiones, int linea, int columna) {
        super(linea, columna);
        this.id = id;
        this.dimensiones = dimensiones;
        
    }

    @Override
    public Tipo getTipo(Entorno e, Object salida) {
        return this.id.getTipo(e, salida);
    }

    @Override
    public Object getValor(Entorno e, Object salida) {
        Object val = this.id.getValor(e, salida);
        if(val instanceof Arreglo){
            Object ret = null;
            Arreglo aux = (Arreglo) val;
            
            int i = 0;
            while(i < this.dimensiones.size()){
                Expresion exp = this.dimensiones.get(i++);
                Tipo tipExp = exp.getTipo(e, salida);
                if(tipExp == Tipo.INT){
                    Object valExp = exp.getValor(e, salida);
                    if(valExp != null){
                        try{
                            int pos = Integer.valueOf(valExp.toString());
                            ret = aux.get(pos);
                            if(ret instanceof Arreglo){
                                aux = (Arreglo) ret;
                            }
                            if(i == this.dimensiones.size())
                                return ret;
                            else
                                continue;
                        } catch(Exception ex){
                            System.err.println(""+ex);
                        }
                    }
                }
                return null;
            }
        }
        return null;
    }
    
}
