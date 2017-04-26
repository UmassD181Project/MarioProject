package com.brackeen.javagamebook.tilegame.sprites;

import com.brackeen.javagamebook.codereflection.CodeReflection;
import com.brackeen.javagamebook.graphics.Animation;


public class BalloonCold extends Balloon//Creature
{
	private Animation leftH;
	private Animation rightH;
	private Animation deadLeftH;
	private Animation deadRightH; 
	
	public BalloonCold(Animation left, Animation right, Animation deadLeft,
			Animation deadRight, Animation leftHin, Animation rightHin, Animation deadLeftHin, Animation deadRightHin) 
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
    	leftH=leftHin;
    	rightH=rightHin;
    	deadLeftH=deadLeftHin;
    	deadRightH=deadRightHin;
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

	public void update(long elapsedTime) 
	{
		Animation newAnim = anim;
    	super.update(elapsedTime);
    	if(state == STATE_HURT )
    	{
    		left=leftH;
    		right=rightH;
    		deadLeft=deadLeftH;
    		deadRight=deadRightH;
    	}
    }
    
}
