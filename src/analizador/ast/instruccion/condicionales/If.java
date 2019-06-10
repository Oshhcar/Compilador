/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizador.ast.instruccion.condicionales;

import analizador.ast.NodoAst;
import analizador.ast.entorno.Entorno;
import analizador.ast.expresion.Expresion;
import analizador.ast.instruccion.Instruccion;
import java.util.ArrayList;

/**
 *
 * @author oscar
 */
public class If extends Instruccion {

    private final ArrayList<SubIf> subIfs;

    public If(Expresion condicion, ArrayList<NodoAst> bloques, int linea, int columna) {
        super(linea, columna);
        this.subIfs = new ArrayList<>();
        this.subIfs.add(new SubIf(condicion, bloques, linea, columna));
    }

    public If(Expresion condicion, ArrayList<NodoAst> bloques, int linea, int columna, SubIf sino) {
        super(linea, columna);
        this.subIfs = new ArrayList<>();
        this.subIfs.add(new SubIf(condicion, bloques, linea, columna));
        this.subIfs.add(sino);
    }

    public If(Expresion condicion, ArrayList<NodoAst> bloques, int linea, int columna, ArrayList<SubIf> subIfs) {
        super(linea, columna);
        this.subIfs = new ArrayList<>();
        this.subIfs.add(new SubIf(condicion, bloques, linea, columna));
        this.subIfs.addAll(subIfs);
    }

    public If(Expresion condicion, ArrayList<NodoAst> bloques, int linea, int columna, ArrayList<SubIf> subIfs, SubIf sino) {
        super(linea, columna);
        this.subIfs = new ArrayList<>();
        this.subIfs.add(new SubIf(condicion, bloques, linea, columna));
        this.subIfs.addAll(subIfs);
        this.subIfs.add(sino);
    }

    @Override
    public Object ejecutar(Entorno e, Object salida) {
        for (SubIf if_ : this.subIfs) {
            Object r = if_.ejecutar(e, salida);
            if (r != null) {
                if (r instanceof Boolean) {
                    if ((boolean) r) {
                        return null;
                    }
                } else {
                    return r;
                }
            }
        }
        return null;
    }

}
