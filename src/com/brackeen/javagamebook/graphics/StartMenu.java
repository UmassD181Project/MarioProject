package com.brackeen.javagamebook.graphics;

import javax.swing.*;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;

import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Dimension;
import com.brackeen.javagamebook.eventlisteners.*;
import com.brackeen.javagamebook.graphics.OptionsMenu;
//This is a commit Test

public class StartMenu extends JFrame{
	
	 private JButton startButton;
	 private JButton exitButton;
	 private JButton optionsButton;
	 private JPanel  buttonPanel;
	 private JPanel  screenContainer;
	 public  JPanel cards;
	 JScrollPane scrollPane;
		ImageIcon icon;
		Image image;
	 private OptionsMenu optionsMenu;
	 final static String BUTTONPANEL = "Card with JButtons";
	 final static public String TEXTPANEL = "Card with JTextField";
	 public static CardLayout card;
	 public static Container c;
	 String a= "ab";
	 String b= "b";
	 String d= "d";
	public StartMenu()
	{
		
		
		
		optionsMenu = new OptionsMenu();
    	startButton = new JButton("Start Game");
    	
    	optionsButton = new JButton("Options");
    	
    	exitButton = new JButton("Exit Game");
    	
    	buttonPanel = new JPanel();
    	buttonPanel.setLayout(new GridLayout(3,0));
    	buttonPanel.add(startButton);
    	buttonPanel.add(optionsButton);
    	buttonPanel.add(exitButton);
    	screenContainer = new JPanel(new BorderLayout());
    	screenContainer.add(buttonPanel, BorderLayout.SOUTH);
    	this.setSize(800,600);
    	this.add(screenContainer);
    	//c=screenContainer;
		card=new CardLayout(40,30);  
		//create CardLayout object with 40 hor space and 30 ver space  
		//c.setLayout(card);  
    	//c.add(scrollPane,"Main");

    	optionsButton.addActionListener(new OptionsButtonListener(optionsMenu));
    	startButton.addActionListener(new StartButtonListener());
    	exitButton.addActionListener(new ExitButtonListener());
    	//Release all Resources on Close
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    	
    	icon = new ImageIcon("images/newbanner.png");
    	 
		JPanel panel = new JPanel()
		{
			protected void paintComponent(Graphics g)
			{
				g.drawImage(icon.getImage(), 0, 0, null);
 				super.paintComponent(g);
			}
		};
		panel.setOpaque( false );
		panel.setPreferredSize( new Dimension(500, 400) );
		
		scrollPane = new JScrollPane( panel );
		getContentPane().add( scrollPane );
 
		 panel.add(buttonPanel);
		 //c.add(panel,null);
		 //getContentPane().add( c );
		 //c.setPreferredSize(new Dimension(500,400));
		 //c.add(scrollPane, "card 1");
		
		
		
	}
}
