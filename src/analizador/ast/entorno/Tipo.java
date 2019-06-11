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
public enum Tipo {
    
    INT{
        @Override
        public boolean isNumero() {
            return true;
        }
    },
    DOUBLE{
        @Override
        public boolean isNumero() {
            return true;
        }
    
    },
    CHAR{
        @Override
        public boolean isNumero() {
            return false;
        }
    
    },
    STRING{
        @Override
        public boolean isNumero() {
            return false;
        }
        
    },
    BOOLEAN{
        @Override
        public boolean isNumero() {
            return false;
        }
    
    }, 
    ARRAY{
        @Override
        public boolean isNumero() {
            return false;
        }
    
    };
    
    public abstract boolean isNumero();
}
