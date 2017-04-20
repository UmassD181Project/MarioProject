package com.brackeen.javagamebook.graphics;

import javax.swing.*;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Dimension;
import com.brackeen.javagamebook.eventlisteners.*;
import com.brackeen.javagamebook.graphics.OptionsMenu;


public class StartMenu extends JFrame{
	
	 private JButton startButton;
	 private JButton exitButton;
	 private JButton optionsButton;
	 private JPanel  buttonPanel;
	 private JPanel screenContainer;
	 JScrollPane scrollPane;
		ImageIcon icon;
		Image image;
	 private OptionsMenu optionsMenu;
	 
	public StartMenu()
	{
		optionsMenu = new OptionsMenu();
    	startButton = new JButton("Start Game");
    	startButton.addActionListener(new StartButtonListener());
    	optionsButton = new JButton("Options");
    	optionsButton.addActionListener(new OptionsButtonListener(optionsMenu));
    	exitButton = new JButton("Exit Game");
    	exitButton.addActionListener(new ExitButtonListener());
    	buttonPanel = new JPanel();
    	buttonPanel.setLayout(new GridLayout(3,0));
    	buttonPanel.add(startButton);
    	buttonPanel.add(optionsButton);
    	buttonPanel.add(exitButton);
    	screenContainer = new JPanel(new BorderLayout());
    	screenContainer.add(buttonPanel, BorderLayout.SOUTH);
    	this.setSize(800,600);
    	this.add(screenContainer);
    	
    	//Release all Resources on Close
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    	
    	icon = new ImageIcon("images/banner.gif");
    	 
		JPanel panel = new JPanel()
		{
			protected void paintComponent(Graphics g)
			{
				g.drawImage(icon.getImage(), 0, 0, null);
 				super.paintComponent(g);
			}
		};
		panel.setOpaque( false );
		panel.setPreferredSize( new Dimension(400, 400) );
		
		scrollPane = new JScrollPane( panel );
		getContentPane().add( scrollPane );
 
		panel.add(buttonPanel);
	}
}
