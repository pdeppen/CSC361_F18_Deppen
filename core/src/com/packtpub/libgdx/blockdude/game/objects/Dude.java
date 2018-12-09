package com.packtpub.libgdx.blockdude.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.packtpub.libgdx.blockdude.game.Assets;
import com.packtpub.libgdx.blockdude.game.Level;
import com.packtpub.libgdx.blockdude.util.Constants;
import com.packtpub.libgdx.blockdude.util.AudioManager;
import com.packtpub.libgdx.blockdude.util.CharacterSkin;
import com.packtpub.libgdx.blockdude.util.GamePreferences;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
/**
 * Created by Philip Deppen (Milestone 3, 11/12/18, issue 41)
 * Edited by Philip Deppen (Milestone 3, 11/13/18, issue 43)
 * Edited by Philip Deppen (Milestone 4, 12/1/18, issue 63)
 * Edited by Philip Deppen (Milestone 5, 12/9/18, issue 71)
 */
public class Dude extends AbstractGameObject
{

	public static final String TAG = Dude.class.getName();
	
	private final float JUMP_TIME_MAX = 0.3f;
	private final float JUMP_TIME_MIN = 0.1f;
	private final float JUMP_TIME_OFFSET_FLYING = JUMP_TIME_MAX - 0.018f;
	
	public ParticleEffect dustParticles = new ParticleEffect();
	
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
		
		// particles
		dustParticles.load(Gdx.files.internal("particles/dustParticle.pfx"), Gdx.files.internal("particles"));
		
		
	}
	
	/**
	 * Created by Philip Deppen (Milestone 3, 11/12/18, issue 43)
	 * @param jumpKeyPressed
	 */
	public void setJumping (boolean jumpKeyPressed)
	{
		dustParticles.setPosition(position.x + dimension.x / 2, position.y);
		dustParticles.start();
		switch (jumpState)
		{
			case GROUNDED: 
				if (velocity.x == 0)
				{
					dustParticles.allowCompletion();
				}
				if (jumpKeyPressed)
				{
					AudioManager.instance.play(Assets.instance.sounds.jump);
					timeJumping = 0;
					jumpState = JUMP_STATE.JUMP_RISING;
					/* edit second argument to change jump height */
					body.setLinearVelocity(new Vector2(0, 6));
				}
				break;
			case JUMP_RISING: 
				if (!jumpKeyPressed)
					jumpState = JUMP_STATE.JUMP_FALLING;
				break;
			case FALLING:
			case JUMP_FALLING:
				if (jumpKeyPressed && this.hasStarPowerup)
				{	
					//timeJumping = JUMP_TIME_OFFEST_FLYING;
					jumpState = JUMP_STATE.JUMP_RISING;
				}
			break;
		}
	}
	
	/**
	 * Created by Philip Deppen (Milestone 3, 11/12/18, issue 41)
	 * Edited by Philip Deppen (Milestone 3, 11/26/18, issue 49)
	 * @param pickedUp
	 */
	public void setStarPowerup (boolean pickedUp)
	{
		hasStarPowerup = pickedUp;
		if (pickedUp)
		{
			timeLeftStarPowerup = Constants.ITEM_STAR_POWERUP_DURATION;
		}
	}
	
	/**
	 * Created by Philip Deppen (Milestone 3, 11/12/18, issue 41)
	 */
	public boolean hasStarPowerup()
	{
		return hasStarPowerup && timeLeftStarPowerup > 0;
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
		dustParticles.update(deltaTime);
	}
	
	/**
	 * Created by Philip Deppen (Milestone 3, 11/13/18, issue 43)
	 * Edited by Philip Deppen (Milestone 3, 11/26/18, issue 50) - method is no longer called in AbstractGameObject
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
		if (jumpState != JUMP_STATE.GROUNDED) {
			dustParticles.allowCompletion();
			super.updateMotionY(deltaTime);
		}
	}
	
	/**
	 * Created by Philip Deppen (Milestone 3, 11/12/18, issue 41)
	 * Edited by Philip Deppen (Milestone 3, 11/26/18, issue 49)
	 * Edited by Philip Deppen (Milestone 4, 12/1/18, issue 63)
	 */
	@Override
	public void render(SpriteBatch batch) 
	{
		TextureRegion reg = null;
		
		dustParticles.draw(batch);
		
		// apply skin color
		batch.setColor(CharacterSkin.values() [GamePreferences.instance.charSkin].getColor());
		
		if (hasStarPowerup)
		{
			batch.setColor(1.0f, 0.8f, 0.0f, 1.0f);
		}
		
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
