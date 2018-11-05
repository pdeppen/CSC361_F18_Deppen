package com.packtpub.libgdx.blockdude.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Philip Deppen (Milestone 2, 11/5/18, issue 21)
 * base class for game objects
 */
public abstract class AbstractGameObject 
{
	public Vector2 position;
    public Vector2 dimension;
    public Vector2 origin;
    public Vector2 scale;
    public float rotation;
    
    /**
     * Created by Philip Deppen (Milestone 2, 11/5/18, issue 21)
     */
    public AbstractGameObject()
    	{
    		position = new Vector2();
        dimension = new Vector2(1, 1);
        origin = new Vector2();
        scale = new Vector2(1, 1);
        rotation = 0;
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
