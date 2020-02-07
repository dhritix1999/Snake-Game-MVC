/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakegame;

import java.awt.geom.Ellipse2D;

/**
 *
 * @author Dhriti
 */

//A cell can be of three types; either it is part of the snake or it is a fruit or it is nothing
 enum CellType{
    NONE, SNAKE, FRUIT}

class Cell {
    private CellType cellType;
    private Ellipse2D.Double circle = new Ellipse2D.Double();
    
    //default constructor
    public Cell() {this.cellType = CellType.NONE;}
    
    //non default constructor
    //user specifies what the cell type is by passing an int which represents the following
    // 0 = none
    // 1 = snake
    // 2 = fruit
    public Cell(int type){
        
        setCellType(type);
    }
    
    
    public void setCellType(int i){
        switch (i) {
            case 0:
                this.cellType = CellType.NONE;
                break;
            case 1:
                this.cellType = CellType.SNAKE;
                break;
            case 2:
                this.cellType = CellType.FRUIT;
                break;
        }
    }
    
    public int getCellType(){
        switch(cellType){
            default: //if NONE
                return 0; 
            case SNAKE: 
                return 1;
            case FRUIT: 
                return 2;
        }
    }
    
    //boolean checkers to make it easier to understand
    public boolean isSnake(){
        return cellType.equals(CellType.SNAKE);
    }
    
    public boolean isFruit(){
        return cellType.equals(CellType.FRUIT);
    }
    public boolean isNone(){
        return cellType.equals(CellType.NONE);
    }
    
    //each cell is represented as a circle
    public void setCircle(double x, double y, double w, double h){
        circle.setFrame(x, y, w, h);
    }
    
    public Ellipse2D getCircle(){return circle;}

}
