package com.brackeen.javagamebook.eventlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import com.brackeen.javagamebook.graphics.OptionsMenu;
import com.brackeen.javagamebook.graphics.StartMenu;

public class OptionsButtonListener implements ActionListener {

	private OptionsMenu optionsMenuInstance;
	
	public OptionsButtonListener(OptionsMenu om)
	{
		optionsMenuInstance = om;
		//StartMenu.card.next(StartMenu.c);
		
	}
	
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		optionsMenuInstance.setVisible(true);
	}

}
