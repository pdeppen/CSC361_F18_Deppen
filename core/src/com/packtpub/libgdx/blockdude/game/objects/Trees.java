package com.packtpub.libgdx.blockdude.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.packtpub.libgdx.blockdude.game.Assets;

/**
 * Created by Philip Deppen (Milestone 2, 11/6/18, issue 23)
 */
public class Trees extends AbstractGameObject
{
	private TextureRegion regTree;
	
	private int length;
	
	/**
	 * Created by Philip Deppen (Milestone 2, 11/6/18, issue 23)
	 * @param length
	 */
	public Trees (int length) 
	{
		this.length = length;
		init();
	}
	
	/**
	 * Created by Philip Deppen (Milestone 2, 11/6/18, issue 23)
	 */
	public void init()
	{
		dimension.set(10, 2);
		
		regTree = Assets.instance.levelDecoration.tree;
	}
	
	/**
	 * Created by Philip Deppen (Milestone 2, 11/6/18, issue 23)
	 * @param batch
	 * @param offsetX
	 * @param offsetY
	 * @param tintColor
	 */
	private void drawTree (SpriteBatch batch, float offsetX, float offsetY, float tintColor)
	{
		TextureRegion reg = null;
		
		
	}
	
	/**
	 * Created by Philip Deppen (Milestone 2, 11/6/18, issue 23)
	 */
	@Override
	public void render(SpriteBatch batch) 
	{
		
	}
	
}
