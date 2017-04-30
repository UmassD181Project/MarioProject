package com.brackeen.javagamebook.tilegame.sprites;

import com.brackeen.javagamebook.codereflection.CodeReflection;
import com.brackeen.javagamebook.graphics.Animation;
import java.lang.Math;

/**
 * @author Clinton Rogers
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Ghost extends Creature {
	
	//Constructor, calls super constructor
	public Ghost(Animation left, Animation right,
	        Animation deadLeft, Animation deadRight)
	    {
	        super(left, right, deadLeft, deadRight);
	    	
	        //Code Tracing
	        if(CodeReflection.isTracing() && SpritesPackageTracingEnabled.getSpritesPackageTracingEnabledInstance().isEnabled()) {
	        	if(CodeReflection.getAbstactionLevel()>=1)
	        	{//check to make sure it's this level of abstraction
	        		e.fillInStackTrace();		
	        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
	        								e.getStackTrace()[0].getMethodName());
	        	}
	    	}
	    	//cannot be damaged
	        canTakeDamage=false;
	    	//We don't apply gravity to the Ghost
	        flying = true;
	    }
	
    public float getMaxSpeed() 
    {//Return the max speed of this Ghost
    	
    	//Code Tracing
    	if(CodeReflection.isTracing() && SpritesPackageTracingEnabled.getSpritesPackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=2)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        return 0.2f * enemySpeedMultiplier;
    }

    public boolean isGhosting() 
    {//Returns if the Sinuous Ghost is "Ghosting"
    	
    	//Code Tracing
    	if(CodeReflection.isTracing() && SpritesPackageTracingEnabled.getSpritesPackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=4)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
   	
    	//make sure the Ghost is alive
        return isAlive() && super.isFlying();
    }
    
    public void update(long elapsedTime) 
    {//Override the call to update
    	
		// Call super to maintain animation behavior
		super.update(elapsedTime);
		
		//Code Tracing
    	if(CodeReflection.isTracing() && SpritesPackageTracingEnabled.getSpritesPackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=4)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
    	
        if (!onGround && isAlive()) 
        {
            //onGround = false;
            setVelocityY((float)Math.sin(this.getX()/100)*this.getVelocityX() * enemySpeedMultiplier);
        }
	}
}