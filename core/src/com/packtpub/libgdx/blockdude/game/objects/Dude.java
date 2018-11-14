package com.packtpub.libgdx.blockdude.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.packtpub.libgdx.blockdude.game.Assets;
import com.packtpub.libgdx.blockdude.util.Constants;

/**
 * Created by Philip Deppen (Milestone 3, 11/12/18, issue 41)
 * Edited by Philip Deppen (Milestone 3, 11/13/18, issue 43)
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
	 * Created by Philip Deppen (Milestone 3, 11/12/18, issue 43)
	 * @param jumpKeyPressed
	 */
	public void setJumping (boolean jumpKeyPressed)
	{
		switch (jumpState)
		{
			case GROUNDED: // character is standing on a platform
				if (jumpKeyPressed)
				{
					// start counting jump time from its beginning
					timeJumping = 0;
					jumpState = JUMP_STATE.JUMP_RISING;		
				}
				break;
			case JUMP_RISING: // rising in the air
				if (!jumpKeyPressed)
				{
					jumpState = JUMP_STATE.JUMP_FALLING;
				}
				break;
			case FALLING: // falling down
			case JUMP_FALLING: // falling down after jump
				if (jumpKeyPressed && hasStarPowerup)
				{
					timeJumping = JUMP_TIME_OFFSET_FLYING;
					jumpState = JUMP_STATE.JUMP_RISING;
				}
				break;
				
		}
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
	 * Created by Philip Deppen (Milestone 3, 11/13/18, issue 43)
	 * @param deltaTime
	 */
	@Override
	public void update (float deltaTime)
	{
		super.update(deltaTime);
		if (velocity.x != 0)
		{
			viewDirection = velocity.x < 0 ? VIEW_DIRECTION.LEFT :
				VIEW_DIRECTION.RIGHT;
		}
		if (timeLeftStarPowerup > 0)
		{
			timeLeftStarPowerup -= deltaTime;
			if (timeLeftStarPowerup < 0)
			{
				// disable power-up
				timeLeftStarPowerup = 0;
				setStarPowerup(false);
			}
		}
	}
	
	/**
	 * Created by Philip Deppen (Milestone 3, 11/13/18, issue 43)
	 */
	@Override
	protected void updateMotionY (float deltaTime)
	{
		switch (jumpState) 
		{
		case GROUNDED:
			jumpState = JUMP_STATE.FALLING;
			break;
		case JUMP_RISING:
			// Keep track of jump time
			timeJumping += deltaTime;
			// Jump time left?
			if (timeJumping <= JUMP_TIME_MAX)
			{
				// Still jumping
				velocity.y = terminalVelocity.y;
			}
			break;
		case FALLING:
			break;
		case JUMP_FALLING:
			// Add delta times to track jump time
			timeJumping += deltaTime;
			// Jump to minimal height if jump key was pressed to short
			if (timeJumping > 0 && timeJumping <= JUMP_TIME_MIN)
			{
				// Still Jumping
				velocity.y = terminalVelocity.y;
			}
		}
		if (jumpState != JUMP_STATE.GROUNDED)
			super.updateMotionY(deltaTime);
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