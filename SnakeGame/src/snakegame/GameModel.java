/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakegame;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Dhriti
 */
class GameModel {
    
    static final int TOTAL_GAME_AREA = 20;
    
    private Cell[][] cellgrid = new Cell[TOTAL_GAME_AREA][TOTAL_GAME_AREA];
    private int currentScore = 0;
    private int currentLive = 3;
    //false means game is paused
    private boolean isPlaying = false;
    private int timeinteval = 1500;
    //this is a redundant variabele because it is SnakeCordinates.size()
    //but it is good to have this as a seperate variable
    private int snakeParts;
    //used Point as it is easy to store x and y cordinates than a 2D array
    private ArrayList<Point> SnakeCordinates;
    private Point fruitPosition;
    private Arrow currentArrow = Arrow.RIGHT;
    private Arrow previousArrow = Arrow.LEFT;
    //boolean to see if user wants to reset game
    private boolean reset = false;
    
    public GameModel(){
        for (Cell[] cellgrid1 : cellgrid) {
            for (int j = 0; j < cellgrid1.length; j++) {
                cellgrid1[j] = new Cell();
            }
        }
        
        changeFruitPosition();
        
        SnakeCordinates = new ArrayList<>();
        
        updateSnakeParts();
    }
    
    //changes the Position of the fruit by randomly assigning cordinates
    public void changeFruitPosition(){
        int x, y;
        x = new Random().nextInt(TOTAL_GAME_AREA-1);
        y = new Random().nextInt(TOTAL_GAME_AREA-1);
        
        //to prevent overlapping of snake's coordinates and the fruits position
        if(snakeParts>0){
        while (SnakeCordinates.contains(new Point(x, y))) {            
            x = new Random().nextInt(TOTAL_GAME_AREA-1);
            y = new Random().nextInt(TOTAL_GAME_AREA-1);
        }}
        
        this.fruitPosition = new Point(x, y);
    }
    
    //remove Fruit from the grid
    public void removeFruit(){
        //technically supposed to be eaqual to null but that won't work in my code
        //assigned negative coordinates
        this.fruitPosition = new Point(-1, -1);
    }
    
    public void updateSnakeParts(){
        this.snakeParts = SnakeCordinates.size();
    }

    //add new snakePart, x and y are the cordinates of the new part
    public void addNewSnakePart(int x, int y){
        this.SnakeCordinates.add(new Point(x, y));
    }
    
    //alter the coordinates of the snake parts at the necessary index
    public void alterSnakeCordinates(int position, int newX, int newY){
        this.SnakeCordinates.remove(position);
        this.SnakeCordinates.add(position, new Point(newX, newY));
    }
    
    public Cell[][] getCellGrid(){
        return cellgrid;
    }
    
    public int getCellType(int x, int y){
        return cellgrid[x][y].getCellType();
    }
    
    void setCellType(int x, int y, int type){
        cellgrid[x][y].setCellType(y);
    }
    
    public void setReset(boolean reset){this.reset = reset;}
    
    public void setScore(int score){ this.currentScore = score;}
    public int getCurrentScore() {return currentScore;}
    
    public void setLive(int lives){ this.currentLive = lives;  }
    public int getCurrentLive() {return currentLive; }
    
    public void setArrowKey(String key){
        //other part of the if check to make sure snake only moves forward and not backward
        
        if (key.toLowerCase().equals("up") && !previousArrow.equals(Arrow.DOWN)) {
            this.currentArrow = Arrow.UP;
        }
        else if (key.toLowerCase().equals("down") && !previousArrow.equals(Arrow.UP)) {
            this.currentArrow = Arrow.DOWN;
        }
        else if (key.toLowerCase().equals("right") && !previousArrow.equals(Arrow.LEFT)){
            this.currentArrow = Arrow.RIGHT;
        }
        else if (key.toLowerCase().equals("left") && !previousArrow.equals(Arrow.RIGHT)){
            this.currentArrow = Arrow.LEFT;
        }  
    }
    //gets the user input
    public String getArrowKey(){
        return this.currentArrow.toString();
    }
    
    public int incrementScore(){
        currentScore +=1;
        return currentScore;
    }
    
    public int decrementLive(){
        currentLive -=1;
        return currentLive;
    }
    
    public int getGridHeight(){return cellgrid.length;}
    public int getGridWidth(){return cellgrid[0].length;}
    
    public void setPlayingMode(boolean isPlaying) {this.isPlaying = isPlaying;} 
    public boolean getPlayingMode() {return isPlaying;}
    
    public int getTimeInterval(){return timeinteval;}
    public void setTimeInterval(int time){this.timeinteval = time;}
    
    public void snakeDies(){
    //lives is decremeted and snakecordinates are reset to intial
        decrementLive();
        this.snakeParts = 0;
        SnakeCordinates.removeAll(SnakeCordinates);
        this.previousArrow = Arrow.LEFT;
        this.currentArrow = Arrow.RIGHT;
    }
    
    //reseting all the variables
    public void ResetGame(){
        snakeDies();
        setScore(0);
        setLive(3);
        removeFruit();
        this.previousArrow = Arrow.LEFT;
        this.currentArrow = Arrow.RIGHT;
    }
    
    public void NextStep(){
        Cell nextVersion[][] = new Cell[TOTAL_GAME_AREA][TOTAL_GAME_AREA];
        
        if(reset){
            ResetGame();
        }
        
        else{
        if (snakeParts == 0) {
            addNewSnakePart(0, 2);
            updateSnakeParts();
        }
        else if (snakeParts < 4) {
            addNewSnakePart(0, 2);
            updateSnakeParts();
            updateSnakePosition();
            
        }
        else{updateSnakePosition();}
        
        //if snake eats fruit
        if(SnakeCordinates.get(0).x == fruitPosition.x && SnakeCordinates.get(0).y == fruitPosition.y)
        { 
            eat();
        }
   
        //if snake touches the boundary
        if(SnakeCordinates.get(0).x < 0 || SnakeCordinates.get(0).x > TOTAL_GAME_AREA-1 ||SnakeCordinates.get(0).y <0 ||SnakeCordinates.get(0).y > TOTAL_GAME_AREA-1)
        {
            snakeDies();
            isPlaying = false;
        }
        
        //if snake eats itself
        for (int i = 1; i < SnakeCordinates.size()-1; i++) {
                if (SnakeCordinates.get(0).equals(SnakeCordinates.get(i))) {
                    snakeDies();
                    isPlaying = false;
                    break;
                }
            }
                }
        
        for (int y = 0; y <cellgrid.length; y++) {
            for (int x = 0; x < cellgrid[y].length; x++) {
                if (SnakeCordinates.contains(new Point(x, y))) {
                    nextVersion[y][x] = new Cell(1);
                }
                else if (fruitPosition.equals(new Point(x, y))) {
                    nextVersion[y][x] = new Cell(2);
                }
                else {nextVersion[y][x] = new Cell(0);}
            }
        }
        
        cellgrid = nextVersion;
    }
    
    public void updateSnakePosition(){
        
        for (int i = snakeParts-1; i > 0; i--) {
            alterSnakeCordinates(i, SnakeCordinates.get(i-1).x, SnakeCordinates.get(i-1).y);
        }
        switch (currentArrow) {
            case DOWN:
                alterSnakeCordinates(0, SnakeCordinates.get(0).x, (SnakeCordinates.get(0).y)+1);
                break;
            case UP:
                alterSnakeCordinates(0, SnakeCordinates.get(0).x, (SnakeCordinates.get(0).y)-1);
                break;
            case RIGHT:
                alterSnakeCordinates(0, (SnakeCordinates.get(0).x)+1, (SnakeCordinates.get(0).y));
                break;
            case LEFT:
                alterSnakeCordinates(0, (SnakeCordinates.get(0).x)-1, (SnakeCordinates.get(0).y));
                break;
            default:
                break;
        }
       
        previousArrow = currentArrow;
//        System.out.println(snakeParts);
//        System.out.println(currentArrow.toString());
//        for (int i = 0; i < SnakeCordinates.size(); i++) {
//            System.out.println(SnakeCordinates.get(i).x +" "+SnakeCordinates.get(i).y+" ,");
//        }
        
    }
    
    public void eat(){
        addNewSnakePart(SnakeCordinates.get(snakeParts-1).x, SnakeCordinates.get(snakeParts-1).y);
        changeFruitPosition();
        incrementScore();
        updateSnakeParts();
    }
    
}
//Arrow controls
enum Arrow {
    UP, DOWN, RIGHT, LEFT
}
