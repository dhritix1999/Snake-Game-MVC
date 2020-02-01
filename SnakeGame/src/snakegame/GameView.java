/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakegame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

/**
 *
 * @author Dhriti
 */
class GameView extends JComponent {
    
    private Cell[][] snakeGame;
    
    public GameView(){ }

    public void addKeyArrowListener(int keyCode, String Name, Action action){
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(keyCode, 0), Name);
        getActionMap().put(Name, action);
    }

    @Override
    protected void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        
        for (int i = 0; i<snakeGame.length; i++){
            for (int j = 0; j < snakeGame[i].length; j++) {
                if (snakeGame[i][j].isSnake()) {
                    g2d.setColor(Color.GREEN);
                } else if (snakeGame[i][j].isFruit()) {
                    g2d.setColor(Color.RED);
                }
                else{
                    g2d.setColor(Color.BLACK);
                }
                g2d.fill(snakeGame[i][j].getCircle());
            }
        }
        
        g2d.setColor(Color.RED);
        //+20 is just to put some space from the border
        g2d.drawRect(0+20, 0+20, 400, 400);
    }
    
    public void setCells(Cell[][] allCells){
        this.snakeGame = allCells;
    }
    
    public Cell[][] getCells(){return snakeGame;}
            
}
