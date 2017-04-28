package com.brackeen.javagamebook.tilegame.sprites;

import com.brackeen.javagamebook.codereflection.CodeReflection;
import com.brackeen.javagamebook.graphics.Animation;
import com.brackeen.javagamebook.util.RandomUtil;

/**
 * @author Clinton Rogers
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Crusher extends Creature {
	private static final int MILI_PER_SECOND = 1000; 
	//TODO set a jump interval to only jump after a certain number of seconds
	// have elapsed.
	private long initJumpInterval = 4 * MILI_PER_SECOND; 
	private long jumpInterval = initJumpInterval;
	/**
	 * @param left
	 * @param right
	 * @param deadLeft
	 * @param deadRight
	 */
	public Crusher(Animation left, Animation right, Animation deadLeft,
			Animation deadRight) 
	{
		//Call Creature constructor
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
		
		//Define as a helper; cannot do/take damage
		//Set as flying
    	//this.flying=true;
    	this.jumpSpeed = -1f;
	}
	
    public float getMaxSpeed() {
    	if(CodeReflection.isTracing() && SpritesPackageTracingEnabled.getSpritesPackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=2)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        return 0.0f;
    }
    public void update(long elapsedTime) {
		// Call super to maintain animation behavior
		super.update(elapsedTime);
    	if(CodeReflection.isTracing() && SpritesPackageTracingEnabled.getSpritesPackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=4)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
		//See if its time for the monkey to jump
		//Because time comes in at intervals we have to make sure that
		//we jump in whichever elapsedTime interval that the timer has gone off in.
    	if(!onGround)
    	{
    		this.setVelocityY(0.1f);
    	}
    	else if(onGround)
    	{
    		this.setVelocityY(-0.15f);
    	}
		if((totalElapsedTime % jumpInterval)-elapsedTime<0){
			//Make the monkey jump if he's not already in the air
			if(onGround)
				jump();
			jumpInterval = initJumpInterval;
		
		}
    }
}
