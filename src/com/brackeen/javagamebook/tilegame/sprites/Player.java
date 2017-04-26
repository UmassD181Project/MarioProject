package com.brackeen.javagamebook.tilegame.sprites;

import com.brackeen.javagamebook.graphics.Animation;

import java.lang.reflect.Constructor;

import com.brackeen.javagamebook.codereflection.*;

/**
    The Player.
*/

public class Player extends Creature {
    public static Animation jumpLeft;
    public Animation jumpRight;
//    private Throwable e = new Throwable();
    
	public int consecutiveHits=0;
	public boolean grounded = true;
	public static float playerJumpSpeedMultiplier = 1.0f;
	
    public Player(Animation left, Animation right,
        Animation deadLeft, Animation deadRight,Animation jumpLeft,Animation jumpRight)
    {
    	 super(left, right, deadLeft,  deadRight);
    	 this.jumpLeft=jumpLeft;
    	 this.jumpRight=jumpRight;
    	 
    	
    	 
    	if(CodeReflection.isTracing() && SpritesPackageTracingEnabled.getSpritesPackageTracingEnabledInstance().isEnabled()) 
    		{
        		if(CodeReflection.getAbstactionLevel()>=1)
        			{//check to make sure it's this level of abstraction
        			e.fillInStackTrace();		
        			CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),e.getStackTrace()[0].getMethodName());
        	}
    	}
    }
    //@Overide
    public void update(long elapsedTime) 
    {
    	// select the correct Animation
        Animation newAnim = anim;
        
        if (getVelocityX() < 0 && onGround && !(getVelocityY() > 0 ||getVelocityY() < 0))  {
            newAnim = left;
        }
        else if (getVelocityX() > 0 && onGround && !(getVelocityY() > 0 ||getVelocityY() < 0)) {
            newAnim = right;
        }
        if (getVelocityX() < 0 &&!onGround&& (getVelocityY() > 0 ||getVelocityY() < 0)) {
            newAnim = jumpRight;
        }
        if (getVelocityX() > 0 &&!onGround&& (getVelocityY() > 0 ||getVelocityY() < 0) ) 
        {
            newAnim = jumpLeft;
        }
        if ((state == STATE_DYING || state == STATE_HURT) && newAnim == left) {
            newAnim = deadLeft;
        }
        else if ((state == STATE_DYING || state == STATE_HURT) && newAnim == right) {
            newAnim = deadRight;
        }
        
        // update the Animation
        if (anim != newAnim) {
            anim = newAnim;
            anim.start();
        }
        else {
            anim.update(elapsedTime);
        }

        // update to "dead" state
        stateTime += elapsedTime;
        if (state == STATE_DYING && stateTime >= DIE_TIME)
        {
        		setState(STATE_DEAD);
        }
        else if (state == STATE_HURT && stateTime >= HURT_TIME)
        {
        	setState(STATE_NORMAL);
        }
        //update total time
        totalElapsedTime += elapsedTime;
    	
    }
    public Object clone() 
    {
    	Constructor constructor = getClass().getConstructors()[0];
        try {
            return constructor.newInstance(new Object[] {
                (Animation)left.clone(),
                (Animation)right.clone(),
                (Animation)deadLeft.clone(),
                (Animation)deadRight.clone(),
                (Animation)jumpLeft.clone(),
                (Animation)jumpRight.clone()
            });
        }
        catch (Exception ex) {
            // should never happen
            ex.printStackTrace();
            return null;
        }
    }
    
    

    public void jump(boolean forceJump) {
    	if(CodeReflection.isTracing() && SpritesPackageTracingEnabled.getSpritesPackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=3)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        if ((onGround || forceJump) && isAlive()) {
            onGround = false;
            
            setVelocityY(jumpSpeed * playerJumpSpeedMultiplier);
        }
    }

    public void collideHorizontal() {
    	if(CodeReflection.isTracing() && SpritesPackageTracingEnabled.getSpritesPackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=3)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        setVelocityX(0);
    }




    public void wakeUp() {
    	if(CodeReflection.isTracing() && SpritesPackageTracingEnabled.getSpritesPackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=3)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        // do nothing
    }




    public float getMaxSpeed() {
        return 0.5f;
    }

}
