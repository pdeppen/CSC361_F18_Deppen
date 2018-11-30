package com.packtpub.libgdx.blockdude.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.packtpub.libgdx.blockdude.game.Assets;

/**
 * Created by Philip Deppen (Milestone 2, 11/5/18, issue 22)
 * Edited by Philip Deppen (Milestone 3, 11/13/18, issue 43)
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
		 dimension.set(4.5f, 1.5f);
		 
		 ground = Assets.instance.ground.ground;
		 
		 // start length of this ground block
		 setLength(1);
	 }
	 
	 /**
	  * Created by Philip Deppen (Milestone 2, 11/5/18, issue 22)
	  * Edited by Philip Deppen (Milestone 3, 11/13/18, issue 43)
	  * @param length
	  */
	 public void setLength(int length)
	 {
		 this.length = length;
		 
		 // update bounding box for collision detection
		 bounds.set(0,0,4.5f,1.5f);//bounds.set(0, 0, dimension.x * length, dimension.y);
		 System.out.println("Ground: "+length);
	 }
	 
	 /**
	  * Created by Philip Deppen (Milestone 2, 11/5/18, issue 22)
	  * @param amount
	  */
	 public void increaseLength (int amount)
	 {
		 //setLength(length + amount);
	 }
     
	/**
	 * Created by Philip Deppen (Milestone 2, 11/5/18, issue 22)
	 * Edited by Philip Deppen (Milestone 3, 11/26/18, issue 50) - fixed initial Dude position
	 */
	@Override
	public void render(SpriteBatch batch) 
	{
		TextureRegion reg = null;
		
		float relX = 0;
		float relY = 0;
		
		reg = ground;
		//relX -= dimension.x / 4;
		
		// draw ground object
		batch.draw(reg.getTexture(), position.x + relX, position.y + relY, origin.x, origin.y, dimension.x , dimension.y, 
				   scale.x, scale.y, rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(), false, false);
		
		
		
	}
	
}
