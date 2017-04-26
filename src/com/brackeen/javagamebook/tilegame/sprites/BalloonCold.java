package com.brackeen.javagamebook.tilegame.sprites;

import com.brackeen.javagamebook.codereflection.CodeReflection;
import com.brackeen.javagamebook.graphics.Animation;

public class BalloonCold extends Balloon//Creature
{
	public BalloonCold(Animation left, Animation right, Animation deadLeft,
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
		//Give 2 Health
    	health=2;
		//Define as helper; can take but not deal damage.
		helper = true;
		canDoDamage = false;
		canTakeDamage=true;
		//Set as flying
		flying = true;
		//give 2 health
		setHealth(2);
	}
	public float getMaxSpeed() 
	{
    	if(CodeReflection.isTracing() && SpritesPackageTracingEnabled.getSpritesPackageTracingEnabledInstance().isEnabled()) 
    	{
        	if(CodeReflection.getAbstactionLevel()>=2)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        return 0.2f;
    }
}
