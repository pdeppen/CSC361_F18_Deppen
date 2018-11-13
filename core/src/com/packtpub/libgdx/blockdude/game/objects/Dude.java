package com.packtpub.libgdx.blockdude.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.packtpub.libgdx.blockdude.game.Assets;
import com.packtpub.libgdx.blockdude.util.Constants;

/**
 * Created by Philip Deppen (Milestone 3, 11/12/18, issue 41)
 */
public class Dude extends AbstractGameObject
{

	public static final String TAG = Dude.class.getName();
	
	private final float JUMP_TIME_MAX = 0.3f;
	private final float JUMP_TIME_MIN = 0.1f;
	private final float JUMP_TIME_OFFSET_FLYING = JUMP_TIME_MAX - 0.018f;
	
	public enum VIEW_DIRECTION 
	{ 
		LEFT, RIGHT 
	}
	
	public enum JUMP_STATE 
	{
		GROUNDED, FALLING, JUMP_RISING, JUMP_FALLING
	}
	
	private TextureRegion regDude;
	
	public VIEW_DIRECTION viewDirection;
	public float timeJumping;
	public JUMP_STATE jumpState;
	public boolean hasStarPowerup;
	public float timeLeftStarPowerup;
	
	/**
	 * Created by Philip Deppen (Milestone 3, 11/12/18, issue 41)
	 */
	public Dude()
	{
		init();
	}
	
	/**
	 * Created by Philip Deppen (Milestone 3, 11/12/18, issue 41)
	 */
	private void init() 
	{
		dimension.set(1, 2);
		regDude = Assets.instance.dude.dude;
		
		// center image on game object
		origin.set(dimension.x / 2, dimension.y / 2);
		// boundig box for collision detection
		bounds.set(0, 0, dimension.x, dimension.y);
		// set physics values
		terminalVelocity.set(3.0f, 4.0f);
		friction.set(12.0f, 0.0f);
		acceleration.set(0.0f, -25.0f);
		// view direction
		viewDirection = VIEW_DIRECTION.RIGHT;
		// jump state
		jumpState = JUMP_STATE.FALLING;
		timeJumping = 0;
		// power-ups
		hasStarPowerup = false;
		timeLeftStarPowerup = 0;				
	}
	
	/**
	 * Created by Philip Deppen (Milestone 3, 11/12/18, issue 41)
	 * @param jumpKeyPressed
	 */
	public void setJumping (boolean jumpKeyPressed)
	{
		
	}
	
	/**
	 * Created by Philip Deppen (Milestone 3, 11/12/18, issue 41)
	 * @param pickedUp
	 */
	public void setStarPowerup (boolean pickedUp)
	{
		
	}
	
	/**
	 * Created by Philip Deppen (Milestone 3, 11/12/18, issue 41)
	 */
	public boolean hasStarPowerup()
	{
		return hasStarPowerup;
	}
	
	
	/**
	 * Created by Philip Deppen (Milestone 3, 11/12/18, issue 41)
	 */
	@Override
	public void render(SpriteBatch batch) 
	{
		TextureRegion reg = null;
		
		// draw image
		reg = regDude;
		batch.draw(reg.getTexture(), position.x, position.y, origin.x,
				   origin.y, dimension.x, dimension.y, scale.x, scale.y, rotation,
				   reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(),
				   reg.getRegionHeight(), viewDirection == VIEW_DIRECTION.LEFT,
				   false);
		
		// reset color to white
		batch.setColor(1, 1, 1, 1);
	}
	
}
