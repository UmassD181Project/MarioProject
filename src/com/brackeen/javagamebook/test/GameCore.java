package com.brackeen.javagamebook.test;
import javax.swing.*;
import java.awt.*;

import com.brackeen.javagamebook.tilegame.GameManager;
import com.brackeen.javagamebook.eventlisteners.ExitButtonListener;
import com.brackeen.javagamebook.graphics.ScreenManager;
import com.brackeen.javagamebook.input.InputManager;
/**
    Simple abstract class used for testing. Subclasses should
    implement the draw() method.
 */
public abstract class GameCore extends JFrame {

	protected static final int FONT_SIZE = 24;

	private static final DisplayMode POSSIBLE_MODES[] = {
			new DisplayMode(800, 600, 16, 0),
			new DisplayMode(800, 600, 32, 0),
			new DisplayMode(800, 600, 24, 0),
			new DisplayMode(640, 480, 16, 0),
			new DisplayMode(640, 480, 32, 0),
			new DisplayMode(640, 480, 24, 0),
			//new DisplayMode(1024, 768, 16, 0),
			//new DisplayMode(1024, 768, 32, 0),
			//new DisplayMode(1024, 768, 24, 0),
	};

	private boolean isRunning;
	protected ScreenManager screen;
	protected boolean fullScreen 	= false;
	protected static boolean toolScreen 	= false;
	protected boolean pauseGame 	= false;

	private Dimension 	screenResolution = new Dimension(800, 600);
	private int			colorDepth = 16;



	/**
        Signals the game loop that it's time to quit
	 */
	public void stop() {
		isRunning = false;
	}


	/**
        Calls init() and gameLoop()
	 */
	public void run() {
		try {
			init();
			gameLoop();

		}
		finally {
			screen.restoreScreen();
			//            lazilyExit();
		}
	}

	/**
        Exits the VM from a daemon thread. The daemon thread waits
        2 seconds then calls System.exit(0). Since the VM should
        exit when only daemon threads are running, this makes sure
        System.exit(0) is only called if neccesary. It's neccesary
        if the Java Sound system is running.
	 */
	public void lazilyExit() {
		Thread thread = new Thread() {
			public void run() {
				// first, wait for the VM exit on its own.
				try {
					Thread.sleep(2000);
				}
				catch (InterruptedException ex) { }
				// system is still running, so force an exit
				System.exit(0);
			}
		};
		thread.setDaemon(true);

		screen.restoreScreen();
		thread.start();
	}


	public void setScreenResolution(int width, int height)
	{
		screenResolution.setSize(width, height);
	}

	public void setColorDepth(int depth)
	{
		colorDepth = depth;
	}

	/**
        Sets full screen mode and initiates and objects.
	 */
	public void init() {
		screen = new ScreenManager();
		//screenResolution = new Dimension();
		DisplayMode displayMode = 
				new DisplayMode(screenResolution.width, screenResolution.height, colorDepth, 0);
		//    screen.findFirstCompatibleMode(POSSIBLE_MODES);
		if(fullScreen) {
			screen.setFullScreen(displayMode);
		}
		else {
			if(toolScreen) {
				screen.setToolWindow(displayMode);
			}
			screen.setWindowedScreen(displayMode);
		}
		Window window = screen.getFullScreenWindow();
		window.setFont(new Font("Dialog", Font.PLAIN, FONT_SIZE));
		window.setBackground(Color.blue);
		window.setForeground(Color.white);

		isRunning = true;
	}


	public Image loadImage(String fileName) {
		return new ImageIcon(fileName).getImage();
	}

	protected void togglePause()
	{
		pauseGame = !pauseGame;
	}

	/**
        Runs through the game loop until stop() is called.
	 */
	double interpolation = 0;
	final int TICKS_PER_SECOND = 120;
	final int SKIP_TICKS = 1000 / TICKS_PER_SECOND;
	final int MAXIMUM_FRAMESKIP = 3;

	public void gameLoop() {
		long startTime = System.currentTimeMillis();
		double nextTickTime = System.currentTimeMillis();
		long currTime = startTime;
		boolean switchPaint = true;
		ImageIcon icon;
		icon = new ImageIcon("images/blackout.png");
		int iterator;
		int pauseLimiter=0;
		JFrame window = screen.getFullScreenWindow();
		final Cursor VISIBLE_CURSOR =Toolkit.getDefaultToolkit().createCustomCursor(Toolkit.getDefaultToolkit().getImage("images/arrowCursor.png"),
				new Point(0,0),"visible");
		Component comp;
		InputManager inputManager = new InputManager(
				screen.getFullScreenWindow());
		JPanel buttonPanel;
		JButton exitButton=new JButton("Exit");  
		exitButton.addActionListener(new ExitButtonListener());
		GridBagConstraints c = new GridBagConstraints();
		JPanel optionsMenu;
		ImageIcon background;
		background = new ImageIcon("images/banner.gif");
		buttonPanel =new JPanel()
		{
			public void paintComponent(Graphics g)
			{
				g.drawImage(background.getImage(),0,0,screen.getWidth(),screen.getHeight(),null);
				g.setColor(Color.RED);
				//this.setOpaque(false);
				super.paintComponent(g);
			}
		};
		
		while (isRunning) {
			iterator=0;
			pauseLimiter=0;
			while (System.currentTimeMillis() > nextTickTime && iterator < MAXIMUM_FRAMESKIP)
			{

				while(!screen.frame().isFocused())
				{
					try
					{
						if (pauseLimiter<1)
						{
							screen.getGraphics().drawString("Automatic Pause", screen.frame().getWidth()/2-150, screen.frame().getHeight()/2);
							screen.getGraphics().drawImage(icon.getImage(),0,0,screen.frame().getWidth(),screen.frame().getHeight(),null);
							screen.update();
							Thread.sleep(20);
							pauseLimiter++;
						}

					}
					catch(Exception e)
					{
					}
				}

				while(pauseGame)
				{
					try
					{

						if (pauseLimiter<1)
						{
							
							screen.getGraphics().drawString("Manual Pause", screen.frame().getWidth()/2-150, screen.frame().getHeight()/2);
							screen.getGraphics().drawImage(icon.getImage(),0,0,screen.frame().getWidth(),screen.frame().getHeight(),null);
							buttonPanel.setBackground(new Color(0,0,0,0));
							buttonPanel.setOpaque(false);
							
							exitButton.setBounds(screen.getWidth()/2-150,screen.getHeight()/2,95,30);
							window.setVisible(true);
							//window.setBackground(new Color(0,0,0,0.0f));
							buttonPanel.setLayout(null);
							//c.fill = GridBagConstraints.HORIZONTAL;
							//c.ipady = 20;
							//c.weightx = 0.0;
							//c.gridwidth = 3;
							//c.gridx = 0;
							//c.gridy = 1;
							

							buttonPanel.add(exitButton,c); 
							window.add(buttonPanel,BorderLayout.CENTER);
							//window.update();
							screen.update();
							Thread.sleep(20);
							inputManager.setCursor(InputManager.VISIBLE_CURSOR);
							pauseLimiter++;
							buttonPanel.repaint();
							//window.setVisible(true);
							window.validate();
						}



					}
					catch(Exception e)
					{

					}

					GameManager.getGameManagerInstance().checkInput(0);		//check to see if the pause key was pressed
				}
				try
				{
				//	window.remove(buttonPanel);
				}
				catch(NullPointerException npe)
				{

				}
				inputManager.setCursor(InputManager.INVISIBLE_CURSOR);
				long elapsedTime =
						System.currentTimeMillis() - currTime;
				currTime += elapsedTime;
				nextTickTime += SKIP_TICKS;
				iterator++;
				// update
				update(elapsedTime);

				// draw the screen
				Graphics2D g = screen.getGraphics();
				draw(g);
				g.dispose();
				screen.update();

				// don't take a nap! run as fast as possible
				/*try {
                Thread.sleep(20);
            }
            catch (InterruptedException ex) { }*/
			}
		}
	}


	/**
        Updates the state of the game/animation based on the
        amount of elapsed time that has passed.
	 */
	public void update(long elapsedTime) {
		// do nothing
	}


	/**
        Draws to the screen. Subclasses must override this
        method.
	 */
	public abstract void draw(Graphics2D g);
}
