
package com.brackeen.javagamebook.tilegame.sprites;
import com.brackeen.javagamebook.graphics.Animation;
import com.brackeen.javagamebook.util.*;
import com.brackeen.javagamebook.codereflection.*;
/**
A Turtle is a Creature uses jumps to move but not track player.
*/
public class Turtle extends Creature {

	private static final int MILI_PER_SECOND = 1000; 
	private long initJumpInterval = 2 * MILI_PER_SECOND; 
	private long jumpInterval = initJumpInterval;

   // private Throwable e = new Throwable();
	/**
	 * @param left
	 * @param right
	 * @param deadLeft
	 * @param deadRight
	 */
	public Turtle(Animation left, Animation right, Animation deadLeft, Animation deadRight) 
	{
		super(left, right, deadLeft, deadRight);
    	if(CodeReflection.isTracing() && SpritesPackageTracingEnabled.getSpritesPackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=1)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
		health=2;
		this.jumpSpeed = -0.65f;
		this.trackPlayer=false;
		helper=false;
		
		
	}
	
    public float getMaxSpeed() {
        return 0.1f * enemySpeedMultiplier;
    }
    
    public void update(long elapsedTime) {
		// Call super to maintain animation behavior
		super.update(elapsedTime);
		
		if((totalElapsedTime % jumpInterval)-elapsedTime<0){
			//Jump is on the ground
			jump();
			jumpInterval = initJumpInterval + RandomUtil.getRandomInt(200);
		}
	}
}