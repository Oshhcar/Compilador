/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizador.ast.expresion;

import analizador.ast.entorno.Entorno;
import analizador.ast.entorno.Simbolo;
import analizador.ast.entorno.Tipo;

/**
 *
 * @author oscar
 */
public class Identificador extends Expresion{
    private final String id;
    
    public Identificador(String id, int linea, int columna) {
        super(linea, columna);
        this.id = id;
    }

    @Override
    public Tipo getTipo(Entorno e, Object salida) {
        Simbolo tmp = e.get(id);
        if(tmp != null){
            return tmp.getTipo();
        }
        return null;
    }

    @Override
    public Object getValor(Entorno e, Object salida) {
        Simbolo tmp = e.get(id);
        if(tmp != null){
            return tmp.getValor();
        }
        return null;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }
    
}
