/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakegame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author Dhriti
 */
class SouthGamePanel extends JPanel {
         
    private final JLabel scorelabel;
    private final JLabel lives;

    public SouthGamePanel() {
        setBackground(Color.BLACK);
        setLayout(new GridLayout(0, 2));
        
        scorelabel = new JLabel();
        scorelabel.setForeground(Color.white);
        setScoreLabel(0);
        scorelabel.setFont(new Font("Monospaced", Font.PLAIN, 18));
        scorelabel.setPreferredSize(new Dimension(100, 30));
        add(scorelabel);
        
        lives = new JLabel("", SwingConstants.RIGHT);
        lives.setForeground(Color.white);
        setLivesLabel(3);
        lives.setFont(new Font("Monospaced", Font.PLAIN, 18));
        lives.setPreferredSize(new Dimension(100, 30));
        add(lives);
        
    }
    
    public void setScoreLabel(int score){
    scorelabel.setText(" Score: "+ score);
    }
    
    public void setLivesLabel(int live){
    lives.setText("Lives: "+ live+" ");
    }
}
