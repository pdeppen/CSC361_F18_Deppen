package com.packtpub.libgdx.blockdude.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.packtpub.libgdx.blockdude.game.Assets;

/**
 * Created by Philip Deppen (Milestone 2, 11/7/18, issue 25)
 */
public class Sun extends AbstractGameObject 
{

	private TextureRegion regSun;
	
	private int length;
	
	/**
	 * Created by Philip Deppen (Milestone 2, 11/7/18, issue 25)
	 * @param length
	 */
	public Sun (int length)
	{
		this.length = length;
		init();
	}
	
	/**
	 * Created by Philip Deppen (Milestone 2, 11/7/18, issue 25)
	 */
	private void init ()
	{
		dimension.set(10, 2);
		
		regSun = Assets.instance.levelDecoration.sun;
		
		// extend length
		length += dimension.x * 2;
	}
	
	/**
	 * Created by Philip Deppen (Milestone 2, 11/7/18, issue 25)
	 */
	private void drawSun (SpriteBatch batch)
	{
		TextureRegion reg = null;
		
		//batch.setColor(tintColor, tintColor, tintColor, 1);
		//float xRel = dimension.x * offsetX;
		//float yRel = dimension.y * offsetY;
		
		// sun spans the whole level
		dimension.set(7, 7);
		int sunLength = 0;
		sunLength += MathUtils.ceil(length / (2 * dimension.x));
		//sunLength += MathUtils.ceil(0.5f + offsetX);
		
		// draw sun
		reg = regSun;
		batch.draw(reg.getTexture(), position.x + origin.x, position.y + origin.y, origin.x, origin.y, dimension.x, dimension.y, scale.x, 
				   scale.y, rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(), false, false);
		
		//xRel += dimension.x;
		
		//batch.setColor(1, 1, 1, 1);
	}

	
	/**
	 * Created by Philip Deppen (Milestone 2, 11/7/18, issue 25)
	 */
	@Override
	public void render(SpriteBatch batch) 
	{
		// distant sun 
		this.drawSun(batch);
	}
	
}
