package com.packtpub.libgdx.blockdude.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by Philip Deppen (Milestone 2, 11/5/18, issue 21)
 * Edited by Philip Deppen (Milestone 3, 11/12/18, issue 41)
 * Edited by Philip Deppen (Milestone 3, 11/18/18, issue 47)
 * base class for game objects
 */
public abstract class AbstractGameObject 
{
	public Vector2 position;
    public Vector2 dimension;
    public Vector2 origin;
    public Vector2 scale;
    public float rotation;
    
    public Vector2 velocity;
    public Vector2 terminalVelocity;
    public Vector2 friction;
    
    public Vector2 acceleration;
    public Rectangle bounds;
    
    public Body body;
    
    /**
     * Created by Philip Deppen (Milestone 2, 11/5/18, issue 21)
     * Edited by Philip Deppen (Milestone 3, 11/12/18, issue 41)
     */
    public AbstractGameObject()
    	{
    		position = new Vector2();
        dimension = new Vector2(1, 1);
        origin = new Vector2();
        scale = new Vector2(1, 1);
        rotation = 0;
        velocity = new Vector2();
        terminalVelocity = new Vector2(1,1);
        friction = new Vector2();
        acceleration = new Vector2();
        bounds = new Rectangle();
    }
    
    /**
     * Created by Philip Deppen (Milestone 2, 11/5/18, issue 21)
     * Edited by Philip Deppen (Milestone 3, 11/13/18, issue 43)
     * Edited by Philip Deppen (Milestone 3, 11/26/18, issue 50)
     * @param deltaTime
     */
    public void update (float deltaTime)
    {
		if (body == null)
		{	
			//updateMotionX(deltaTime);
			//updateMotionY(deltaTime);
			// Move to new position
			//position.x += velocity.x * deltaTime;
			//System.out.println("position x: " + position.x);
			//position.y += velocity.y * deltaTime;
			//System.out.println("position y: " + position.y);
		}
		else
		{
			position.set(body.getPosition());
			rotation = body.getAngle() * MathUtils.radiansToDegrees;
		}
    		//position.set(body.getPosition());
    		//rotation = body.getAngle() * MathUtils.radiansToDegrees;
    }
    
    /**
     * Created by Philip Deppen (Milestone 3, 11/13/18, issue 43)
     * @param updateMotionX
     */
	protected void updateMotionX (float deltaTime) 
	{
		if (velocity.x != 0)
		{
			// Apply friction
			if (velocity.x > 0)
			{
				velocity.x = Math.max(velocity.x - friction.x * deltaTime, 0);
			}
			else 
			{
				velocity.x = Math.min(velocity.x + friction.x * deltaTime, 0);
			}
		}
			// Apply acceleration
			velocity.x += acceleration.x * deltaTime;
			// Make sure the object's velocity does not exceed the
			// positive or negative terminal velocity
			velocity.x = MathUtils.clamp(velocity.x, -terminalVelocity.x, terminalVelocity.x);
	}
	
	/**
	 * Created by Philip Deppen (Milestone 3, 11/13/18, issue 43)
	 * @param deltaTime
	 */
	protected void updateMotionY (float deltaTime)
	{
		if (velocity.y != 0)
		{
			// Apply friction
			if (velocity.y > 0)
			{
				velocity.y = Math.max(velocity.y - friction.y * deltaTime, 0);
			}
			else 
			{
				velocity.y = Math.min(velocity.y + friction.y * deltaTime, 0);
			}
		}
			// Apply acceleration
			velocity.y += acceleration.y * deltaTime;
			// Make sure the object's velocity does not exceed the
			// positive or negative terminal velocity
			velocity.y = MathUtils.clamp(velocity.y, -terminalVelocity.y, terminalVelocity.y);
	}
    
    /**
     * Created by Philip Deppen (Milestone 2, 11/5/18, issue 21)
     * @param batch
     */
    public abstract void render (SpriteBatch batch);
}
