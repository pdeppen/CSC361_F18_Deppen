package com.packtpub.libgdx.blockdude.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Philip Deppen (Milestone 2, 11/5/18, issue 21)
 * Edited by Philip Deppen (Milestone 3, 11/12/18, issue 41)
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
     * @param deltaTime
     */
    public void update (float deltaTime)
    {
    	
    }
    
    /**
     * Created by Philip Deppen (Milestone 2, 11/5/18, issue 21)
     * @param batch
     */
    public abstract void render (SpriteBatch batch);
}
