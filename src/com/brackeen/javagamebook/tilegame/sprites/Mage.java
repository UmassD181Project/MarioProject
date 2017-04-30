/*
 * Created on Mar 2, 2005
 * by Daniels.Douglas@gmail.com
 */
package com.brackeen.javagamebook.tilegame.sprites;

import com.brackeen.javagamebook.graphics.Animation;

import com.brackeen.javagamebook.util.*;
import com.brackeen.javagamebook.codereflection.*;

/**
 * @author danielsd
 * Daniels.Douglas@gmail.com
 */
public class Mage extends Creature {
	//TODO set a jump interval to only jump after a certain number of seconds
	// have elapsed.
//    private Throwable e = new Throwable();
	
	/**
	 * @param left
	 * @param right
	 * @param deadLeft
	 * @param deadRight
	 */
	public Mage(Animation left, Animation right, Animation deadLeft, Animation deadRight) {
		super(left, right, deadLeft, deadRight);
    	if(CodeReflection.isTracing() && SpritesPackageTracingEnabled.getSpritesPackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=1)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
		health=5;
		
	}
	
	
	//TODO Set how fast you want the frog to move.
    public float getMaxSpeed() {
    	if(CodeReflection.isTracing() && SpritesPackageTracingEnabled.getSpritesPackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=2)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
    	this.trackPlayer=true;
        return 0.00001f * enemySpeedMultiplier;
        
    }
}