package com.packtpub.libgdx.blockdude.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.packtpub.libgdx.blockdude.game.Assets;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by Philip Deppen (Milestone 3, 11/13/18, issue 44)
 * Edited by Philip Deppen (Milestone n/a, 12/3/18, issue 67)
 */
public class Coins extends AbstractGameObject
{

	private TextureRegion regCoin;
	
	public static int drawBadBlock = 0;
	
	public boolean collected;
	
	public boolean badBlock = false;
	public boolean goodBlock = false;
	public boolean coin = false;
	
	/**
	 * Created by Philip Deppen (Milestone 3, 11/13/18, issue 44)
	 */
	public Coins(boolean drawBlock, float height) 
	{
		init(drawBlock, height);
	}
	
	/**
	 * Created by Philip Deppen (Milestone 3, 11/13/18, issue 44)
	 * Edited by Philip Deppen (Milestone n/a, 12/3/18, issue 67)
	 */
	private void init(boolean drawBlock, float height) 
	{
		setAnimation(Assets.instance.block.animBlock);
		stateTime = MathUtils.random(0.0f, 1.0f);
				
		drawBadBlock++;
		dimension.set(0.5f, 0.5f);
		
		if (drawBadBlock == 8 && height >= 10.0) {
			badBlock = true;
			regCoin = Assets.instance.movingBlock.movingBlock;
			drawBadBlock = 0;
		}
		else if (drawBlock) {
			goodBlock = true;
			regCoin = Assets.instance.block.block;
		}
		else {
			coin = true;
			regCoin = Assets.instance.coin.coin;
		}
		// set bounding box for collision detection
		bounds.set(0, 0, dimension.x, dimension.y);
		
		collected = false;
	}
	
	/**
	 * Created by Philip Deppen (Milestone 3, 11/13/18, issue 44)
	 */
	public void render (SpriteBatch batch) 
	{
		TextureRegion reg = null;

		reg = regCoin;

		if (collected) return; //reg = (TextureRegion) animation.getKeyFrame(stateTime, true);
		
		else if (this.badBlock)
			reg = (TextureRegion) animation.getKeyFrame(stateTime, true);
		
		//reg = (TextureRegion) animation.getKeyFrame(stateTime, true);
		
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
