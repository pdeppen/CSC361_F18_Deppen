package com.packtpub.libgdx.blockdude.game.objects;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.packtpub.libgdx.blockdude.game.Assets;

/**
 * Created by Philip Deppen (Milestone 2, 11/6/18, issue 24)
 */

public class Ufos extends AbstractGameObject
{
	private float length;
	
	private Array<TextureRegion> regUfos;
	private Array<Ufo> ufos;
	
	public Ufos (float length)
	{
		this.length = length;
		init();
	}
	
	private void init ()
	{
		dimension.set(3.0f, 1.5f);
		regUfos = new Array<TextureRegion>();
		regUfos.add(Assets.instance.levelDecoration.ufo01);
		regUfos.add(Assets.instance.levelDecoration.ufo02);
		
		int distFac = 5;
		int numUfos = (int) (length / distFac);
		ufos = new Array<Ufo> (2 * numUfos);
		
		for (int i = 0; i < numUfos; i++)
		{
			Ufo ufo = spawnUfo();
			ufo.position.x = i * distFac;
			ufos.add(ufo);
		}
	}
	
	private Ufo spawnUfo ()
	{
		Ufo ufo = new Ufo();
		ufo.dimension.set(dimension);
		
		// select random ufo image
		ufo.setRegion(regUfos.random());
		
		// position
		Vector2 pos = new Vector2();
		pos.x = length + 10; // position after end of level
		pos.y += 4; // base position
		pos.y += MathUtils.random(0.0f, 0.2f) * (MathUtils.randomBoolean() ? 1 : -1); // random additional position
		ufo.position.set(pos);
		return ufo;
		
	}
	
	@Override
	public void render (SpriteBatch batch)
	{
		for (Ufo ufo : ufos)
			ufo.render(batch);
	}
	
	private class Ufo extends AbstractGameObject 
	{
		private TextureRegion regUfo;
		
		public Ufo ()
		{
			
		}
		
		public void setRegion (TextureRegion region)
		{
			regUfo = region;
		}
		
		@Override
		public void render (SpriteBatch batch)
		{
			TextureRegion reg = regUfo;
			
			 batch.draw(reg.getTexture(), position.x + origin.x, position.y + origin.y, origin.x, origin.y, dimension.x, dimension.y, scale.x, 
					   scale.y, rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(), false, false);
		}
	}
}
