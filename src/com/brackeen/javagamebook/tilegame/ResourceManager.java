package com.brackeen.javagamebook.tilegame;


import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.*;
import java.util.ArrayList;
import javax.swing.ImageIcon;

import com.brackeen.javagamebook.graphics.*;
import com.brackeen.javagamebook.tilegame.sprites.*;
import com.brackeen.javagamebook.codereflection.*;


/**
    The ResourceManager class loads and manages tile Images and
    "host" Sprites used in the game. Game Sprites are cloned from
    "host" Sprites.
*/
public class ResourceManager {

    private ArrayList tiles;
    private int currentMap;
    private GraphicsConfiguration gc;

    // host sprites used for cloning
    private Sprite playerSprite;
    private Sprite musicSprite;
    private Sprite coinSprite;
    private Sprite goalSprite;
    private Sprite warpSprite;
    private Sprite healthSprite;
    
    private Sprite[] enemySprites;
    private ScriptManager s;
    private Throwable e = new Throwable();

    /**
        Creates a new ResourceManager with the specified
        GraphicsConfiguration.
    */
    public ResourceManager(GraphicsConfiguration gc) {
        this.gc = gc;
        
        s = ScriptManager.getScriptManagerInstance();
                
        currentMap = -1;	//flag value
        
        loadTileImages();
        loadCreatureSprites();
        loadPowerUpSprites(); 
    }


    /**
        Gets an image from the images/ directory.
    */
    public Image loadImage(String name) {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        String filename = "images/" + name;
        return new ImageIcon(filename).getImage();
    }


    public Image getMirrorImage(Image image) {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        return getScaledImage(image, -1, 1);
    }


    public Image getFlippedImage(Image image) {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        return getScaledImage(image, 1, -1);
    }


    private Image getScaledImage(Image image, float x, float y) {

    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        // set up the transform
        AffineTransform transform = new AffineTransform();
        transform.scale(x, y);
        transform.translate(
            (x-1) * image.getWidth(null) / 2,
            (y-1) * image.getHeight(null) / 2);

        // create a transparent (not translucent) image
        Image newImage = gc.createCompatibleImage(
            image.getWidth(null),
            image.getHeight(null),
            Transparency.TRANSLUCENT);

        // draw the transformed image
        Graphics2D g = (Graphics2D)newImage.getGraphics();
        g.drawImage(image, transform, null);
        g.dispose();

        return newImage;
    }


    public TileMap loadNextMap() {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        TileMap map = null;
        
        while (map == null) {
        	currentMap++;
            try {
                map = loadMap(
                    "maps/"+s.getMap(currentMap));
            }
            catch (IOException ex) {
                if (currentMap == 1) {
                    // no maps to load!
                    return null;
                }
                currentMap = 0;
                map = null;
            }
        }

        return map;
    }
    public TileMap loadWarpMap(int x) {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        TileMap map = null;
        currentMap=x;
        
        while (map == null) {
            try {
            	if(s.getMap(currentMap)!=null)
            		map = loadMap("maps/"+s.getMap(currentMap));
            	else{ 
            		map = null;
            		currentMap = 1;
            	}
            }
            catch (IOException ex) {
                if (currentMap == 1) {
                    // no maps to load!
                    return null;
                }
                currentMap = 1;
                map = null;
            }
        }
        return map;
    }


    public TileMap reloadMap() {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        try {
            return loadMap(
            		 "maps/"+s.getMap(currentMap));
        }
        catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }


    private TileMap loadMap(String filename)
        throws IOException
    {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        ArrayList lines = new ArrayList();
        int width = 0;
        int height = 0;

        // read every line in the text file into the list
        BufferedReader reader = new BufferedReader(
            new FileReader(filename));
        while (true) {
            String line = reader.readLine();
            // no more lines to read
            if (line == null) {
                reader.close();
                break;
            }

            // add every line except for comments
            if (!line.startsWith("#")) {
                lines.add(line);
                width = Math.max(width, line.length());
            }
        }

        // parse the lines to create a TileEngine
        height = lines.size();
        TileMap newMap = new TileMap(width, height);
        for (int y=0; y<height; y++) {
            String line = (String)lines.get(y);
            for (int x=0; x<line.length(); x++) {
                char ch = line.charAt(x);

                // check if the char represents tile A, B, C etc.
                int tile = ch - 'A';
                if (tile >= 0 && tile < tiles.size()) {
                    newMap.setTile(x, y, (Image)tiles.get(tile));
                }

                // check if the char represents a sprite
                else if (ch == '&') {
                    addSprite(newMap, coinSprite, x, y);
                }
                else if (ch == '!') {
                    addSprite(newMap, musicSprite, x, y);
                }
                else if (ch == '*') {
                    addSprite(newMap, goalSprite, x, y);
                }
                else if (ch == '^') {
                    addSprite(newMap, healthSprite, x, y);
                }
                else if ((ch>='0')&&(ch<='9')){
                	((PowerUp.Warp)warpSprite).setWarpValue((ch-'0')+2+currentMap);
                	addSprite(newMap, warpSprite, x, y);
                }
                else	
                	for(char i='a'; i<'a'+s.getNumberOfEnemies();i++)
                		if (ch == i) {
                			addSprite(newMap, enemySprites[(int)(i-'a')], x, y);
                		}
            }
        }

        // add the player to the map
        Sprite player = (Sprite)playerSprite.clone();
        player.setX(TileMapRenderer.tilesToPixels(3));
        player.setY(0);
        newMap.setPlayer(player);

        return newMap;
    }


    private void addSprite(TileMap map,
        Sprite hostSprite, int tileX, int tileY)
    {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=3)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        if (hostSprite != null) {
            // clone the sprite from the "host"
            Sprite sprite = (Sprite)hostSprite.clone();
            
            if(hostSprite instanceof PowerUp.Warp)	//if it's a warp, add warp value
            	((PowerUp.Warp)sprite).setWarpValue(((PowerUp.Warp)hostSprite).getWarpValue());
          
            if(hostSprite instanceof Boss)
            	((Boss)sprite).setHealth(10);
            
            // center the sprite
            sprite.setX(
                TileMapRenderer.tilesToPixels(tileX) +
                (TileMapRenderer.tilesToPixels(1) -
                sprite.getWidth()) / 2);

            // bottom-justify the sprite
            sprite.setY(
                TileMapRenderer.tilesToPixels(tileY + 1) -
                sprite.getHeight());

            // add it to the map
            map.addSprite(sprite);
        }
    }


    // -----------------------------------------------------------
    // code for loading sprites and images
    // -----------------------------------------------------------


    public void loadTileImages() {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        // keep looking for tile A,B,C, etc. this makes it
        // easy to drop new tiles in the images/ directory
        tiles = new ArrayList();
        char ch = 'A';
        while (true) {
            String name = "tile_" + ch + ".png";
            File file = new File("images/" + name);
            if (!file.exists()) {
                break;
            }
            tiles.add(loadImage(name));
            ch++;
        }
    }

    public void loadCreatureSprites() {
    	
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
    	int sprites, enemies, imageIndex=0;
    	
    	sprites=s.getNumberOfSprites();		//get total number of sprites
    	Image[][] images = new Image[6][];

        // load left-facing images
        images[0] = new Image[sprites] ;
        for(int x=0;x<sprites;x++)
        	images[0][x]= loadImage(s.getSpriteImage(x));

        images[1] = new Image[images[0].length];
        images[2] = new Image[images[0].length];
        images[3] = new Image[images[0].length];
        for (int i=0; i<images[0].length; i++) {
            // right-facing images
            images[1][i] = getMirrorImage(images[0][i]);
            // left-facing "dead" images
            images[2][i] = getFlippedImage(images[0][i]);
            // right-facing "dead" images
            images[3][i] = getFlippedImage(images[1][i]);
        }

        // create creature animations
        Animation[] playerAnim = new Animation[8];
        /*Animation[] flyAnim = new Animation[4];
        Animation[] grubAnim = new Animation[4];
        Animation[] monkeyAnim = new Animation[4];*/
        enemies= s.getNumberOfEnemies();
        
        Animation[][] enemyAnim =new Animation[enemies][4]; 
        
        //Create Animation
        int x=0;       
        for (int i=0; i<4; i++){
        	imageIndex=13;
        	for(x=0; x<enemies;x++)
            {
            	if(s.getArchType(x).compareTo("grub")==0)
                    enemyAnim[x][i] = createGrubAnim(
                            images[i][imageIndex++], images[i][imageIndex++]);	
            	else
            	if(s.getArchType(x).compareTo("fly")==0)
            		enemyAnim[x][i] = createFlyAnim(
            			images[i][imageIndex++], images[i][imageIndex++], images[i][imageIndex++]);
            	else
            	if(s.getArchType(x).compareTo("slime")==0)
            		enemyAnim[x][i]=createMonkeyAnim(
            				images[i][imageIndex++], images[i][imageIndex++], images[i][imageIndex++]);
            	else
            	if(s.getArchType(x).compareTo("boss")==0)
            		enemyAnim[x][i]=createBossAnim(
            				images[i][imageIndex++], images[i][imageIndex++], images[i][imageIndex++]);
            	else
            	if(s.getArchType(x).compareTo("sinuousfly")==0)
            		enemyAnim[x][i]=createFlyAnim(
            				images[i][imageIndex++], images[i][imageIndex++], images[i][imageIndex++]);
            	else
            	if(s.getArchType(x).compareTo("homingfly")==0)
            		enemyAnim[x][i]=createFlyAnim(
            				images[i][imageIndex++], images[i][imageIndex++], images[i][imageIndex++]);
            	else
            	if(s.getArchType(x).compareTo("randomfly")==0)
            		enemyAnim[x][i]=createFlyAnim(
            				images[i][imageIndex++], images[i][imageIndex++], images[i][imageIndex++]);
            	else
                	if(s.getArchType(x).compareTo("balloon")==0)
                		enemyAnim[x][i]=createFlyAnim(
                				images[i][imageIndex++], images[i][imageIndex++], images[i][imageIndex++]);
                else
                   	if(s.getArchType(x).compareTo("frog")==0)
                   		enemyAnim[x][i]=createFrogAnim(
                   				images[i][imageIndex++], images[i][imageIndex++], images[i][imageIndex++], images[i][imageIndex++]);
                else
            	if(s.getArchType(x).compareTo("crusher")==0)
                    enemyAnim[x][i] = createCrusherAnim(
                            images[i][imageIndex++], images[i][imageIndex++]);	
            	else
                	if(s.getArchType(x).compareTo("balloonCold")==0)
                        enemyAnim[x][i] = createColdAnim(images[i][imageIndex++]);
                else
                   	if(s.getArchType(x).compareTo("ghost")==0)
                        enemyAnim[x][i] = createGhostAnim(images[i][imageIndex++], images[i][imageIndex++], images[i][imageIndex++], images[i][imageIndex++]);
                else
                    if(s.getArchType(x).compareTo("bush")==0)
                        enemyAnim[x][i] = createDecoAnim(images[i][imageIndex++], images[i][imageIndex++]);
                else
                    if(s.getArchType(x).compareTo("saw")==0)
                        enemyAnim[x][i] = createDecoAnim(images[i][imageIndex++], images[i][imageIndex++]);
                else
                    if(s.getArchType(x).compareTo("mage")==0)
                        enemyAnim[x][i] = createMageAnim(images[i][imageIndex++], images[i][imageIndex++], images[i][imageIndex++], images[i][imageIndex++], images[i][imageIndex++],
                        		 images[i][imageIndex++], images[i][imageIndex++]);
                else
                  	if(s.getArchType(x).compareTo("spider")==0)
                  		enemyAnim[x][i]=createMonkeyAnim(
                    		images[i][imageIndex++], images[i][imageIndex++], images[i][imageIndex++]);	

                  	else
                       	if(s.getArchType(x).compareTo("rocket")==0)
                      		enemyAnim[x][i]=createDecoAnim(
                      			images[i][imageIndex++], images[i][imageIndex++]);
                    else
                        if(s.getArchType(x).compareTo("turtle")==0)
                          	enemyAnim[x][i]=createColdAnim(
                          		images[i][imageIndex++]);
                    else
                        if(s.getArchType(x).compareTo("bird")==0)
                          	enemyAnim[x][i]=createFlyAnim(
                          		images[i][imageIndex++], images[i][imageIndex++], images[i][imageIndex++]);
                    else
                        if(s.getArchType(x).compareTo("spinner")==0)
                            enemyAnim[x][i] = createDecoAnim(images[i][imageIndex++], images[i][imageIndex++]);
                        else
                           	if(s.getArchType(x).compareTo("hidingWall")==0)
                          		enemyAnim[x][i]=createDecoAnim(
                          			images[i][imageIndex++], images[i][imageIndex++]);
                           	else
                                if(s.getArchType(x).compareTo("lava")==0)
                                    enemyAnim[x][i] = createDecoAnim(images[i][imageIndex++], images[i][imageIndex++]);
                }
                            
            
        }
        /*//Editfield
        for(x=x; x<enemies; x++){
        	for(int i=0; i<8; i++)
        	{
        		if(s.getArchType(x).compareTo("balloonCold")==0)
        			enemyAnim[x][i]=createColdAnim(
        					images, i, imageIndex++);
        	}
        }
    	//Editfield*/      
        for (int i=0; i<4; i++)
        {
        	imageIndex=0;
        	playerAnim[i] = createPlayerAnim(i,
            images);
        }
        playerAnim[4] = createPlayerJumpAnim(0,images);
        playerAnim[5] = createPlayerJumpAnim(1,images);


        playerAnim[6] = createPlayerStandAnim(0,images);
        playerAnim[7] = createPlayerStandAnim(1,images);
        // create creature sprites
        playerSprite = new Player(playerAnim[0], playerAnim[1],
            playerAnim[2], playerAnim[3], playerAnim[4],playerAnim[5],playerAnim[6],playerAnim[7]);
       
        enemies = s.getNumberOfEnemies();      
        enemySprites= new Creature[enemies]; 	//initialize space

        //Create Enemies
        for(int y=0; y<enemies;y++)
        	if(s.getArchType(y).compareTo("grub")==0)
        		enemySprites[y]=new Grub(enemyAnim[y][0], enemyAnim[y][1],
        				enemyAnim[y][2], enemyAnim[y][3]);
        	else
        	if(s.getArchType(y).compareTo("fly")==0)
        		enemySprites[y] = new Fly(enemyAnim[y][0], enemyAnim[y][1],
        				enemyAnim[y][2], enemyAnim[y][3]);
        	else
        	if(s.getArchType(y).compareTo("slime")==0)
        		enemySprites[y]=new Slime(enemyAnim[y][0], enemyAnim[y][1],
        				enemyAnim[y][2], enemyAnim[y][3]);   
        	else
            if(s.getArchType(y).compareTo("boss")==0)
            	enemySprites[y]=new Boss(enemyAnim[y][0], enemyAnim[y][1],
            			enemyAnim[y][2], enemyAnim[y][3]); 
            else
            if(s.getArchType(y).compareTo("sinuousfly")==0)
            	enemySprites[y]=new SinuousFly(enemyAnim[y][0], enemyAnim[y][1],
            			enemyAnim[y][2], enemyAnim[y][3]);

            else
            if(s.getArchType(y).compareTo("homingfly")==0)
            	enemySprites[y]=new HomingFly(enemyAnim[y][0], enemyAnim[y][1],
            			enemyAnim[y][2], enemyAnim[y][3]);
            else
            if(s.getArchType(y).compareTo("randomfly")==0)
            	enemySprites[y]=new RandomFly(enemyAnim[y][0], enemyAnim[y][1],
            			enemyAnim[y][2], enemyAnim[y][3]);
            else
                if(s.getArchType(y).compareTo("balloon")==0)
                	enemySprites[y]=new Balloon(enemyAnim[y][0], enemyAnim[y][1],
                			enemyAnim[y][2], enemyAnim[y][3]);
              else
                  if(s.getArchType(y).compareTo("frog")==0)
                 	enemySprites[y]=new Frog(enemyAnim[y][0], enemyAnim[y][1],
                  			enemyAnim[y][2], enemyAnim[y][3]);
            else
        	if(s.getArchType(y).compareTo("crusher")==0)
        		enemySprites[y]=new Crusher(enemyAnim[y][0], enemyAnim[y][1],
        				enemyAnim[y][2], enemyAnim[y][3]);
        	else if(s.getArchType(y).compareTo("balloonCold")==0)
        		enemySprites[y]=new BalloonCold(enemyAnim[y][0], enemyAnim[y][1],
        				enemyAnim[y][2], enemyAnim[y][3]);
        	else if(s.getArchType(y).compareTo("ghost")==0)
        		enemySprites[y]=new Ghost(enemyAnim[y][0], enemyAnim[y][1],
        				enemyAnim[y][2], enemyAnim[y][3]);
        	else if(s.getArchType(y).compareTo("bush")==0)
        		enemySprites[y]=new Decoration(enemyAnim[y][0], enemyAnim[y][1],
        				enemyAnim[y][2], enemyAnim[y][3]);
        	else if(s.getArchType(y).compareTo("saw")==0)
        		enemySprites[y]=new Hazard(enemyAnim[y][0], enemyAnim[y][1],
        				enemyAnim[y][2], enemyAnim[y][3]);
        	else if(s.getArchType(y).compareTo("mage")==0)
        		enemySprites[y]=new Mage(enemyAnim[y][0], enemyAnim[y][1],
        				enemyAnim[y][2], enemyAnim[y][3]);
        	else if(s.getArchType(y).compareTo("spider")==0)
        		enemySprites[y]=new Spider(enemyAnim[y][0], enemyAnim[y][1],
        				enemyAnim[y][2], enemyAnim[y][3]);
        	else if(s.getArchType(y).compareTo("rocket")==0)
        		enemySprites[y]=new Decoration(enemyAnim[y][0], enemyAnim[y][1],
        				enemyAnim[y][2], enemyAnim[y][3]);
        	else if(s.getArchType(y).compareTo("turtle")==0)
        		enemySprites[y]=new Turtle(enemyAnim[y][0], enemyAnim[y][1],
        				enemyAnim[y][2], enemyAnim[y][3]);
        	else if(s.getArchType(y).compareTo("bird")==0)
        		enemySprites[y]=new Bird(enemyAnim[y][0], enemyAnim[y][1],
        				enemyAnim[y][2], enemyAnim[y][3]);
        	else if(s.getArchType(y).compareTo("spinner")==0)
        		enemySprites[y]=new MobileHazard(enemyAnim[y][0], enemyAnim[y][1],
        				enemyAnim[y][2], enemyAnim[y][3]);
        	else if(s.getArchType(y).compareTo("hidingWall")==0)
        		enemySprites[y]=new Decoration(enemyAnim[y][0], enemyAnim[y][1],
        				enemyAnim[y][2], enemyAnim[y][3]);
        	else if(s.getArchType(y).compareTo("lava")==0)
        		enemySprites[y]=new Hazard(enemyAnim[y][0], enemyAnim[y][1],
        				enemyAnim[y][2], enemyAnim[y][3]);
      //Editfield*/
    }
    
    public String levelBackground()
    {	//return the image name of the background for the current map
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
    	if(currentMap==-1) //first instance load
    		return(s.getLevelImage(1));
    	return(s.getLevelImage(currentMap));
    }
    
    public String levelMusic()
    {	//return the music name for the current map
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
    	if(currentMap==-1) //first instance load
    		return(s.getlevelMusic(1));
    	return(s.getlevelMusic(currentMap));
    }
    
    public int getLevel()
    {	//return the current level the player is on
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=4)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
    	return(currentMap);
    }
    
    public String getBoopSound()
    {	//return the sound for jumping on an enemy
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
    	return(s.getSoundByReference("boop"));
    }
    
    public String getStarSound()
    {	//return the sound for the star
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
    	return(s.getSoundByReference("star"));
    }
    
    public String getNoteSound()
    {	//return the sound for the note
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
    	return(s.getSoundByReference("note"));
    }
    
    public String getWarpSound()
    {	//return the sound for the warp
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
    	return(s.getSoundByReference("warp"));
    }
    
    public String getEndOfLevelSound()
    {	//return the sound for the warp
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
    	return(s.getSoundByReference("endoflevel"));
    }
    
    public String getDieSound()
    {	//return the sound for the player dying
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
    	return(s.getSoundByReference("die"));
    }
    
    public String getHealthSound()
    {	//return the sound for receiving a health powerup
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
    	return(s.getSoundByReference("health"));
    }
    
    public String getHurtSound()
    {	//return the sound for receiving damage, but not dying
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
    	return(s.getSoundByReference("hurt"));
    }
    
    //credit sound entry 
    public String getCreditSound() 
    {	//return the sound for the end credits
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()){
    		if(CodeReflection.getAbstactionLevel()>=0)
    		{//check to make sure it's this level of abstraction
    			e.fillInStackTrace();
    			CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
						e.getStackTrace()[0].getMethodName());
    			
    		}
    	}
    	return (s.getSoundByReference("credits"));
	}
    
    public ScriptManager getScriptClass()
    {	//return your instance of s
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=4)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
    	return(s);
    }
    
    //TODO finish player anim
    private Animation createPlayerAnim(int typeIndex,Image images[][])
    {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        Animation anim = new Animation();
        for(int I =0; I < 11 ;I++)
        {
        	anim.addFrame(images[typeIndex][I], 85);
        }
        return anim;
    }
    private Animation createPlayerJumpAnim(int typeIndex,Image images[][])
    {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        Animation anim = new Animation();
        
        	anim.addFrame(images[typeIndex][11], 85);
       
        return anim;
    }

    private Animation createPlayerStandAnim(int typeIndex,Image images[][])
    {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        Animation anim = new Animation();
        
        	anim.addFrame(images[typeIndex][12], 85);
       
        return anim;
    }
    private Animation createFlyAnim(Image img1, Image img2,
        Image img3)
    {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        Animation anim = new Animation();
        anim.addFrame(img1, 50);
        anim.addFrame(img2, 50);
        anim.addFrame(img3, 50);
        anim.addFrame(img2, 50);
        return anim;
    }
    private Animation createColdAnim(Image img1)
        {
        	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
            	if(CodeReflection.getAbstactionLevel()>=0)
            	{//check to make sure it's this level of abstraction
            		e.fillInStackTrace();		
            		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
            								e.getStackTrace()[0].getMethodName());
            	}
        	}
            Animation anim = new Animation();
            anim.addFrame(img1, 100);            
            return anim;
        }
    //TODO: frog anim; stand still, jump, toungue animations
    private Animation createFrogAnim(Image img1, Image img2,
            Image img3, Image img4)
    {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        Animation anim = new Animation();
        anim.addFrame(img1, 300);
        anim.addFrame(img2, 100);
        anim.addFrame(img3, 100);
        anim.addFrame(img4, 100);
        anim.addFrame(img3, 100);
        anim.addFrame(img2, 100);
        anim.addFrame(img1, 300);
        return anim;
    }
    private Animation createGhostAnim(Image img1, Image img2,
            Image img3, Image img4)
    {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        Animation anim = new Animation();
        anim.addFrame(img1, 100);
        anim.addFrame(img2, 200);
        anim.addFrame(img3, 200);
        anim.addFrame(img4, 200);
        anim.addFrame(img3, 200);
        anim.addFrame(img2, 200);
        anim.addFrame(img1, 100);
        return anim;
    }
    private Animation createMageAnim(Image img1, Image img2, Image img3, Image img4, Image img5, Image img6, Image img7) {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        Animation anim = new Animation();
        anim.addFrame(img1, 200);
        anim.addFrame(img2, 100);
        anim.addFrame(img3, 100);
        anim.addFrame(img4, 100);
        anim.addFrame(img5, 100);
        anim.addFrame(img6, 100);
        anim.addFrame(img7, 100);
        return anim;
    }
    private Animation createDecoAnim(Image img1, Image img2) {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        Animation anim = new Animation();
        anim.addFrame(img1, 100);
        anim.addFrame(img2, 100);
        return anim;
    }
    //TODO: Crusher/umbrella
    private Animation createCrusherAnim(Image img1, Image img2) {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        Animation anim = new Animation();
        anim.addFrame(img2, 4000);
        anim.addFrame(img1, 4000);
        return anim;
    }

    private Animation createGrubAnim(Image img1, Image img2) {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        Animation anim = new Animation();
        anim.addFrame(img1, 250);
        anim.addFrame(img2, 250);
        return anim;
    }

    //TODO Create animation sequence for creature
    private Animation createMonkeyAnim(Image img1, Image img2,
            Image img3)
        {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
            Animation anim = new Animation();
            //Normal state
            anim.addFrame(img1, 100);
            //Up anim state
            anim.addFrame(img2, 100);
            //Normal state
            anim.addFrame(img3, 100);
          //down anim state
            anim.addFrame(img1, 100);
            
            
            return anim;
        }
    
    private Animation createBossAnim(Image img1, Image img2,
            Image img3)
        {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
            Animation anim = new Animation();
            //Normal state
            anim.addFrame(img1, 50);
            //Up anim state
            anim.addFrame(img2, 50);
            //Normal state
            anim.addFrame(img1, 50);
            //down anim state
            anim.addFrame(img3, 50);
         
            return anim;
        }
    
    private void loadPowerUpSprites() {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        // create "goal" sprite
        Animation anim = new Animation();
        anim.addFrame(loadImage(s.getItemImage("eol",1)), 150);
        anim.addFrame(loadImage(s.getItemImage("eol",2)), 150);
        anim.addFrame(loadImage(s.getItemImage("eol",3)), 150);
        anim.addFrame(loadImage(s.getItemImage("eol",4)), 150);
        goalSprite = new PowerUp.Goal(anim);

        // create "star" sprite
        anim = new Animation();
        anim.addFrame(loadImage(s.getItemImage("coin",1)), 100);
        anim.addFrame(loadImage(s.getItemImage("coin",2)), 100);
        anim.addFrame(loadImage(s.getItemImage("coin",3)), 100);
        anim.addFrame(loadImage(s.getItemImage("coin",4)), 100);
        coinSprite = new PowerUp.Star(anim);

        // create "music" sprite
        anim = new Animation();
        anim.addFrame(loadImage(s.getItemImage("note",1)), 150);
        anim.addFrame(loadImage(s.getItemImage("note",2)), 150);
        anim.addFrame(loadImage(s.getItemImage("note",3)), 150);
        anim.addFrame(loadImage(s.getItemImage("note",4)), 150);
        musicSprite = new PowerUp.Music(anim);
        
        // create warp sprite
        anim = new Animation();
        anim.addFrame(loadImage(s.getItemImage("warp",1)), 55);
        anim.addFrame(loadImage(s.getItemImage("warp",2)), 55);
        anim.addFrame(loadImage(s.getItemImage("warp",3)), 55);
        anim.addFrame(loadImage(s.getItemImage("warp",4)), 55);
        warpSprite = new PowerUp.Warp(anim);
        
        // create health sprite
        anim = new Animation();
        anim.addFrame(loadImage(s.getItemImage("health",1)),100);
        anim.addFrame(loadImage(s.getItemImage("health",2)),100);
        anim.addFrame(loadImage(s.getItemImage("health",3)),100);
        anim.addFrame(loadImage(s.getItemImage("health",4)),100);
        healthSprite = new PowerUp.Health(anim);
        
    }
	 public void setLevelMappingFile(String scriptFile)
	 {	//This tells what script file to use for level loading
	    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
	        	if(CodeReflection.getAbstactionLevel()>=0)
	        	{//check to make sure it's this level of abstraction
	        		e.fillInStackTrace();		
	        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
	        								e.getStackTrace()[0].getMethodName());
	        	}
	    	}
	 	s.setLevelMappingFile(scriptFile);
	 }
	 
	 public String getLevelMappingFile()
	 {	//Tell what script file is being used to load levels
	    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
	        	if(CodeReflection.getAbstactionLevel()>=0)
	        	{//check to make sure it's this level of abstraction
	        		e.fillInStackTrace();		
	        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
	        								e.getStackTrace()[0].getMethodName());
	        	}
	    	}
	 	return(s.getLevelMappingFile());
	 }


	

}
