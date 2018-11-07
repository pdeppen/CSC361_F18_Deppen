package com.packtpub.libgdx.blockdude.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.packtpub.libgdx.blockdude.game.Assets;

/**
 * Created by Philip Deppen (Milestone 2, 11/5/18, issue 22)
 */
public class Ground extends AbstractGameObject
{
	 private TextureRegion ground;
	 
	 private int length;
	 
	 /**
	  * Created by Philip Deppen (Milestone 2, 11/5/18, issue 22)
	  */
	 public Ground() 
	 {
		 init();
	 }
	 
	 /**
	  * Created by Philip Deppen (Milestone 2, 11/5/18, issue 22)
	  */
	 private void init()
	 {
		 dimension.set(1, 1.5f);
		 
		 ground = Assets.instance.ground.ground;
		 
		 // start length of this ground block
		 setLength(1);
	 }
	 
	 /**
	  * Created by Philip Deppen (Milestone 2, 11/5/18, issue 22)
	  * @param length
	  */
	 public void setLength(int length)
	 {
		 this.length = length;
	 }
	 
	 /**
	  * Created by Philip Deppen (Milestone 2, 11/5/18, issue 22)
	  * @param amount
	  */
	 public void increaseLength (int amount)
	 {
		 setLength(length + amount);
	 }
     
	/**
	 * Created by Philip Deppen (Milestone 2, 11/5/18, issue 22)
	 */
	@Override
	public void render(SpriteBatch batch) 
	{
		TextureRegion reg = null;
		
		float relX = 0;
		float relY = 0;
		
		reg = ground;
		relX -= dimension.x / 4;
		
		// draw ground object
		batch.draw(reg.getTexture(), position.x + relX, position.y + relY, origin.x, origin.y, dimension.x /4 , dimension.y, 
				   scale.x, scale.y, rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(), false, false);
		
		
		
	}
	
}
