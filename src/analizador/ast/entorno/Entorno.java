/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizador.ast.entorno;

import java.util.HashMap;

/**
 *
 * @author oscar
 */
public class Entorno {
    private final Entorno padre;
    private final HashMap<String, Simbolo> tabla;
    
    public Entorno(Entorno padre) {
        this.padre = padre;
        this.tabla = new HashMap<>();
    }
    
    public void add(String id, Simbolo sim) {
        this.tabla.put(id, sim);
    }

    public Simbolo getLocal(String id){
        if(this.tabla.containsKey(id))
            return this.tabla.get(id);
        return null;
    }
    
    public Simbolo get(String id) {
        Entorno actual = this;

        while (actual != null) {
            if (actual.tabla.containsKey(id)) {
                return actual.tabla.get(id);
            } else {
                actual = actual.padre;
            }
        }
        return null;
    }
    
    public boolean set(String id, Object valor) {
        Entorno actual = this;
        
        while(actual != null){
            if(actual.tabla.containsKey(id)){
                actual.tabla.get(id).setValor(valor);
                return true;
            }
        }
        return false;
    }
    
    public void recorrer() {
        Entorno actual = this;
        int i = 0;
        while(actual != null){
            System.out.println("Entorno " + i++);
            actual.tabla.forEach((id,s)->{
                System.out.print(id + " " + s.getRol() + ":" + s.getTipo().toString());
                if(s.getValor() != null)
                    System.out.println(" -> "+s.getValor());
                else 
                    System.out.println("");
            });
            
            actual = actual.padre;
        }
    } 
}
