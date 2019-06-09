/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizador.ast.entorno;

/**
 *
 * @author oscar
 */
public class Simbolo {
    private Tipo tipo;
    private String id;
    private Object valor;

    public Simbolo(Tipo tipo, String id) {
        this.tipo = tipo;
        this.id = id;
        this.valor = null;
    }

    public Simbolo(Tipo tipo, String id, Object valor) {
        this.tipo = tipo;
        this.id = id;
        this.valor = valor;
    }

    /**
     * @return the tipo
     */
    public Tipo getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the valor
     */
    public Object getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(Object valor) {
        this.valor = valor;
    }
    
    
    
}
