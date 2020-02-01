/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakegame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author Dhriti
 */
class GameController {
    
    private final GameModel snakeGameModel;
    private final GameView snakeGameView;
    private final NorthGamePanel northpanel;
    private final SouthGamePanel southpanel;
    
    private Timer gameTimer = null;
    
    public GameController(GameModel snakeGameModel, GameView snakeView, NorthGamePanel npanel, SouthGamePanel spanel){
        this.snakeGameModel = snakeGameModel;
        this.snakeGameView = snakeView;
        this.northpanel = npanel;
        this.southpanel = spanel;
        
        updateGameViewDisplay();
        
        initialpanelViewListeners();
    }
    
    private void updateGameViewDisplay(){  
        int startx = 20;
        int starty = 20;
        double CellWidth = 20;
        
        int numOfRows = snakeGameModel.getGridHeight();
        int numOfColumn = snakeGameModel.getGridWidth();
        
        Cell[][] Cells = new Cell[numOfRows][numOfColumn];
        
        for (int i = 0; i < Cells.length; i++) {
                double curretYPos = (i * CellWidth)+starty;
            for (int j = 0; j < Cells[i].length; j++) {
                double curentXPos = (j * CellWidth)+startx;
                
                int currentCellType = snakeGameModel.getCellType(i, j);
                
                Cells[i][j] = new Cell(currentCellType);
                Cells[i][j].setCircle(curentXPos, curretYPos, CellWidth, CellWidth);
            }
        }
        snakeGameView.setCells(Cells);
        snakeGameView.repaint();
        }
    
    private void performOneStep(){
        
        snakeGameModel.NextStep();
        southpanel.setScoreLabel(snakeGameModel.getCurrentScore());
        southpanel.setLivesLabel(snakeGameModel.getCurrentLive());
        
        if (!snakeGameModel.getPlayingMode()) {
            stopTime();
            northpanel.updatePanelForPlayMode(false);
        }
        
        if(snakeGameModel.getCurrentLive() == 0)
        {
            northpanel.updatePanelForGameOverMode();
            javax.swing.JLabel label = new javax.swing.JLabel("Game Over");
            label.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 18));
            JOptionPane.showMessageDialog(null, label, null, JOptionPane.WARNING_MESSAGE);
        }
            

    }
    
    
    private void initialpanelViewListeners(){
        northpanel.addPlayButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                snakeGameModel.setPlayingMode(!snakeGameModel.getPlayingMode());
                stopTime();
                
                if (snakeGameModel.getPlayingMode()) {
                    snakeGameModel.setArrowKey("right");
                    startGame();
                    northpanel.updatePanelForPlayMode(true);
                }
                else{northpanel.updatePanelForPlayMode(false);}            
            }
        });
        
        northpanel.addNewGameButoonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
            javax.swing.JLabel label = new javax.swing.JLabel("New Game");
            label.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 18));
            JOptionPane.showMessageDialog(null, label,"Are you sure?", JOptionPane.INFORMATION_MESSAGE);
            
                    snakeGameModel.setReset(true);
                    snakeGameModel.NextStep();
                    southpanel.setScoreLabel(snakeGameModel.getCurrentScore());
                    southpanel.setLivesLabel(snakeGameModel.getCurrentLive());
                    updateGameViewDisplay();
                   snakeGameModel.setReset(false);
                   snakeGameModel.setPlayingMode(false);
                    snakeGameModel.changeFruitPosition();
                    northpanel.updatePanelforGameStartMode();
            }
        });
        
        
        snakeGameView.addKeyArrowListener(KeyEvent.VK_UP, "up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                snakeGameModel.setArrowKey("up");   
            }
        });
        
        snakeGameView.addKeyArrowListener(KeyEvent.VK_DOWN, "down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                snakeGameModel.setArrowKey("down");   
            }
        });
        
        snakeGameView.addKeyArrowListener(KeyEvent.VK_RIGHT, "right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                snakeGameModel.setArrowKey("right");   
            }
        });
             
        snakeGameView.addKeyArrowListener(KeyEvent.VK_LEFT, "left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                snakeGameModel.setArrowKey("left");   
            }
        });
        
    }
    
    private void stopTime(){
        if(gameTimer != null){gameTimer.stop();}}
    
    private void startGame(){
        gameTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                startOneStepThread();
            }
        });
        gameTimer.start();
    }
    
    private void startOneStepThread(){
        new Thread(new Runnable() {
            @Override
            public void run() {
            synchronized(snakeGameModel){
                
                if(snakeGameModel.getCurrentLive() == 0){
                    snakeGameModel.removeFruit();
                }
                else{
                performOneStep();}
                
                updateGameViewDisplay();}   
            }
        }).start();
    }
    
}
    
