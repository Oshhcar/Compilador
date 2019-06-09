/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import java.awt.Rectangle;

/**
 *
 * @author oscar
 */
public interface LineNumberModel {
    public int getNumberLines();
    
    public Rectangle getLineRect(int line);
}
