package com.packtpub.libgdx.blockdude.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.packtpub.libgdx.blockdude.game.Assets;

/**
 * Created by Philip Deppen (Milestone 2, 11/7/18, issue 37)
 */
public class Background extends AbstractGameObject
{
	private TextureRegion regBackground1;
	private TextureRegion regBackground2;

	
	private int length;

	/**
	 * Created by Philip Deppen (Milestone 2, 11/7/18, issue 37)
	 * @param length
	 */
	public Background (int length)
	{
		this.length = length;
		init();
	}
	
	/**
	 * Created by Philip Deppen (Milestone 2, 11/7/18, issue 37)
	 */
	private void init()
	{
		dimension.set(10, 2);
		
		regBackground1 = Assets.instance.levelDecoration.background;
		regBackground2 = Assets.instance.levelDecoration.background;
		
		// shift background and extend length
		origin.x = -dimension.x * 2;
		length += dimension.x * 2;
	}
	
	/**
	 * Created by Philip Deppen (Milestone 2, 11/7/18, issue 37)
	 * Edited by Philip Deppen (Milestone 2, 11/12/18, issue 38)
	 * @param batch
	 */
	private void drawBackground (SpriteBatch batch, float offsetX, float offsetY)
	{
		TextureRegion reg = null;

		float xRel = dimension.x * offsetX;
		float yRel = dimension.y * offsetY;

		// mountains span the whole level
		int backgroundLength = 0;
		backgroundLength += MathUtils.ceil(length / (2 * dimension.x));
		backgroundLength += MathUtils.ceil(0.5f + offsetX);
		
		for (int i = 0; i < backgroundLength; i++)
		{
			// draw background
			reg = regBackground1;
			batch.draw(reg.getTexture(), origin.x + xRel, position.y +
					   origin.y + yRel - 1.5f, origin.x, origin.y, dimension.x + 5, dimension.y + 10,
					   scale.x, scale.y, rotation, reg.getRegionX(), reg.getRegionY(),
					   reg.getRegionWidth(), reg.getRegionHeight(), false, false);

			xRel += dimension.x + 5;

			reg = regBackground2;
			batch.draw(reg.getTexture(), origin.x + xRel, position.y +
					   origin.y + yRel - 1.5f, origin.x, origin.y, dimension.x + 5, dimension.y + 10,
					   scale.x, scale.y, rotation, reg.getRegionX(), reg.getRegionY(),
					   reg.getRegionWidth(), reg.getRegionHeight(), false, false);			
			
			xRel += dimension.x + 5;

	         
		}

		
		//batch.draw(regBackground1, 0, 0);
		//reg = regBackground2;
		//batch.draw(reg.getTexture(), position.x + origin.x, position.y + origin.y, origin.x, origin.y, dimension.x, dimension.y, scale.x, 
		//		   scale.y, rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(), false, false);
		
	}
	
	/**
	 * Created by Philip Deppen (Milestone 2, 11/7/18, issue 37)
	 */
	@Override
	public void render(SpriteBatch batch) 
	{
		this.drawBackground(batch, 0.5f, 0.5f);
	}
	
}
