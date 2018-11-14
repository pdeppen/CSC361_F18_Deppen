package com.packtpub.libgdx.blockdude.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.packtpub.libgdx.blockdude.game.Assets;

/**
 * Created by Philip Deppen (Milestone 3, 11/13/18, issue 44)
 */
public class Coins extends AbstractGameObject
{

	private TextureRegion regCoin;
	
	public boolean collected;
	
	/**
	 * Created by Philip Deppen (Milestone 3, 11/13/18, issue 44)
	 */
	public Coins() 
	{
		init();
	}
	
	/**
	 * Created by Philip Deppen (Milestone 3, 11/13/18, issue 44)
	 */
	private void init() 
	{
		dimension.set(0.5f, 0.5f);
		
		regCoin = Assets.instance.coin.coin;
		
		// set bounding box for collision detection
		bounds.set(0, 0, dimension.x, dimension.y);
		
		collected = false;
	}
	
	/**
	 * Created by Philip Deppen (Milestone 3, 11/13/18, issue 44)
	 */
	public void render (SpriteBatch batch) 
	{
		if (collected) return;
		
		TextureRegion reg = null;
		reg = regCoin;
		
		batch.draw(reg.getTexture(), position.x, position.y,
				   origin.x, origin.y, dimension.x + 0.5f, dimension.y + 0.5f, scale.x, scale.y,
				   rotation, reg.getRegionX(), reg.getRegionY(),
				   reg.getRegionWidth(), reg.getRegionHeight(), false, false);
		
	}
	
	/**
	 * Created by Philip Deppen (Milestone 3, 11/13/18, issue 44)
	 */
	public int getScore() 
	{
		return 100;
	}
	
	
}
