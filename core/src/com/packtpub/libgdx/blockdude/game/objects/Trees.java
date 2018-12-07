package com.packtpub.libgdx.blockdude.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.packtpub.libgdx.blockdude.game.Assets;

/**
 * Created by Philip Deppen (Milestone 2, 11/6/18, issue 23)
 * This is the goal object
 */
public class Trees extends AbstractGameObject
{
	private TextureRegion regTree;
		
	/**
	 * Created by Philip Deppen (Milestone 2, 11/6/18, issue 23)
	 * @param length
	 */
	public Trees () 
	{
		init();
	}
	
	/**
	 * Created by Philip Deppen (Milestone 2, 11/6/18, issue 23)
	 */
	public void init()
	{
		dimension.set(3.0f, 3.0f);
		
		regTree = Assets.instance.levelDecoration.tree;
		
		// Set bounding box for collision detection
		bounds.set(1, Float.MIN_VALUE, 10, Float.MAX_VALUE);
		origin.set(dimension.x / 2.0f, 0.0f);		
	}
	
	/**
	 * Created by Philip Deppen (Milestone 2, 11/6/18, issue 23)
	 */
	public void render(SpriteBatch batch) 
	{
		TextureRegion reg = null;
		
		reg = regTree;
		batch.draw(reg.getTexture(), position.x - origin.x,
				   position.y - origin.y, origin.x, origin.y, dimension.x,
				   dimension.y, scale.x, scale.y, rotation,
				   reg.getRegionX(), reg.getRegionY(),
				   reg.getRegionWidth(), reg.getRegionHeight(),
				   false, false);
	}
	
}
