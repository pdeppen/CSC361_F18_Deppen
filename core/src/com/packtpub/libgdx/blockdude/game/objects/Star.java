package com.packtpub.libgdx.blockdude.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.packtpub.libgdx.blockdude.game.Assets;

/**
 * Created by Philip Deppen (Milestone 3, 11/26/18, issue 49)
 */
public class Star extends AbstractGameObject
{
	private TextureRegion regStar;
	
	public boolean collected;
	
	/**
	 * Created by Philip Deppen (Milestone 3, 11/26/18, issue 49)
	 */
	public Star ()
	{
		init();
	}
	
	/**
	 * Created by Philip Deppen (Milestone 3, 11/26/18, issue 49)
	 */
	private void init ()
	{
		dimension.set(0.5f, 0.5f);
		
		regStar = Assets.instance.star.star;
		
		// set bounding box for collision detection
		bounds.set(0, 0, dimension.x, dimension.y);
		
		collected = false;
	}
	
	/**
	 * Created by Philip Deppen (Milestone 3, 11/26/18, issue 49)
	 */
	@Override
	public void render(SpriteBatch batch) 
	{
		if (collected)
			return;
		
		TextureRegion reg = null;
		reg = regStar;
		
		batch.draw(reg.getTexture(), position.x, position.y,
				   origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y,
				   rotation, reg.getRegionX(), reg.getRegionY(),
				   reg.getRegionWidth(), reg.getRegionHeight(), false, false);
	}
	
	/**
	 * Created by Philip Deppen (Milestone 3, 11/26/18, issue 49)
	 */
	public int getScore()
	{
		return 250;
	}
}
