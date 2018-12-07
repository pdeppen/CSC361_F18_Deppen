package com.packtpub.libgdx.blockdude.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.packtpub.libgdx.blockdude.util.AudioManager;
import com.packtpub.libgdx.blockdude.util.CameraHelper;
import com.packtpub.libgdx.blockdude.game.Assets;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input.Keys;
import com.packtpub.libgdx.blockdude.game.objects.Ground;
import com.packtpub.libgdx.blockdude.game.objects.Star;
import com.packtpub.libgdx.blockdude.game.objects.Trees;
import com.packtpub.libgdx.blockdude.screens.MenuScreen;
import com.packtpub.libgdx.blockdude.util.Constants;
import com.packtpub.libgdx.blockdude.util.GamePreferences;
import com.packtpub.libgdx.blockdude.game.objects.Ground;
import com.packtpub.libgdx.blockdude.game.objects.Coins;
import com.packtpub.libgdx.blockdude.game.objects.Dude;
import com.packtpub.libgdx.blockdude.game.objects.Ground;
import com.packtpub.libgdx.blockdude.game.objects.Dude.JUMP_STATE;

/**
 * Created by Philip Deppen (Milestone 1, 10/29/18)
 * Edited by Philip Deppen (Milestone 2, 11/6/18, issue 29)
 * Edited by Philip Deppen (Milestone 3, 11/13/18, issue 43)
 * Edited by Philip Deppen (Milestone 3, 11/18/18, issue 47)
 * Edited by Philip Deppen (Milestone 3, 11/19/18, issue 46)
 * Edited by Philip Deppen (Milestone 4, 11/30/18)
 * Edited by Philip Deppen (Milestone N/A, 12/3/18, issue 57)
 * Edited by Philp Deppen (Milestone 5, 12/6/18, issue 73)
 * contains game logic
 */
public class WorldController extends InputAdapter implements Disposable
{
	private static final String TAG = WorldController.class.getName();
	
	public CameraHelper cameraHelper;

	public Level level;
	public int lives;
	public int score;
	
	// Rectangles for collision detection
	private Rectangle r1 = new Rectangle();
	private Rectangle r2 = new Rectangle();
	
	public World b2world;
	
	private Game game;
	
	private float timeLeftGameOverDelay;
	
	/* variable that ends game if last life is lost on block hit */
	public boolean badBlockHit = false;
	
	public boolean winner = false;
	
	public boolean resetLives = false;
	
	/**
	 * Created by Philip Deppen (Milestone 1, 10/29/18)
	 * Edited by Philip Deppen (Milestone 4, 11/30/18)
	 * constructor
	 */
	public WorldController(Game game) 
	{
		this.game = game;
		init();
	}
		
	/**
	 * Created by Philip Deppen (Milestone 1, 10/29/18)
	 * Edited by Philip Deppen (Milestone 2, 11/6/18, issue 29)
	 * Edited by Philip Deppen (Milestone N/A, 12/3/18, issue 57)
	 * used for reseting game objects instead of making new ones
	 */
	private void init()
	{
		Gdx.input.setInputProcessor(this);
		cameraHelper = new CameraHelper();
		lives = Constants.LIVES_START;
		timeLeftGameOverDelay = 0;
		initLevel();
	}
	
	/**
	 * Created by Philip Deppen (Milestone 2, 11/6/18, issue 29)
	 * Edited by Philip Deppen (Milestone 3, 11/19/18, issue 46)
	 */
	private void initLevel()
	{
		score = 0;
		level = new Level (Constants.LEVEL_01);
		cameraHelper.setTarget(level.dude);
		initPhysics();
	}
	
	/**
	 * Created by Philip Deppen (Milestone 1, 10/29/18)
	 * Edited by Philip Deppen (Milestone 3, 11/19/18, issue 46)
	 * Edited by Philip Deppen (Milestone N/A, 12/3/18, issue 57)
	 * Edited by Philp Deppen (Milestone 5, 12/6/18, issue 73)
	 * contains game logic is called several hundred times per second
	 * @param deltaTime
	 */
	public void update (float deltaTime)
	{
		handleDebugInput(deltaTime);
				
		/* need to add code from page 234 */
		if (isGameOver() || winner)
		{
			timeLeftGameOverDelay -= deltaTime;
			if (timeLeftGameOverDelay < 0)
				backToMenu();				
		}
		else
		{
			handleInputGame(deltaTime);
		}
		
		level.update(deltaTime);
		b2world.step(deltaTime, 8, 3);
		cameraHelper.update(deltaTime);
		
		if (!isGameOver() && (isPlayerInWater() || badBlockHit))
		{
			AudioManager.instance.play(Assets.instance.sounds.liveLost);
			lives--;
			if (isGameOver())
			{
				timeLeftGameOverDelay = Constants.TIME_LEFT_GAME_OVER;
			}
			else
			{
				Coins.drawBadBlock = 0;
				initLevel();
			}
		}
		if (winner)
		{
			timeLeftGameOverDelay = Constants.TIME_LEFT_GAME_OVER;
		}
		if (this.resetLives)
		{
			this.lives = Constants.LIVES_START;
		}
		
	}
	
	/**
	 * Created by Philip Deppen (Milestone N/A, 12/3/18, issue 57)
	 */
	public boolean isGameOver()
	{
		return lives < 0;
	}
	
	/**
	 * Created by Philip Deppen (Milestone N/A, 12/3/18, issue 57)
	 */
	public boolean isPlayerInWater()
	{
		return Level.dude.position.y < -5;
	}
	
	
	
	/**
	 * Created by Philip Deppen (Milestone 1, 10/30/18, issue 13)
	 * Edited by Philip Deppen (Milestone 3, 11/19/18, issue 46)
	 * moves sprites when keys are pressed
	 * @param deltaTime
	 */
	private void handleDebugInput (float deltaTime) {
		if (Gdx.app.getType() != ApplicationType.Desktop) 
			return;
		
		if (!cameraHelper.hasTarget(level.dude))
		{
			// Camera Controls (move)
			float camMoveSpeed = 5 * deltaTime;
			float camMoveSpeedAccelerationFactor = 5;
			if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) 
				camMoveSpeed *= camMoveSpeedAccelerationFactor;
			if (Gdx.input.isKeyPressed(Keys.LEFT))
				moveCamera(-camMoveSpeed, 0);
			if (Gdx.input.isKeyPressed(Keys.RIGHT)) 
				moveCamera(camMoveSpeed, 0);
			if (Gdx.input.isKeyPressed(Keys.UP))
				moveCamera(0, camMoveSpeed);
			if (Gdx.input.isKeyPressed(Keys.DOWN))
				moveCamera(0, -camMoveSpeed);
			if (Gdx.input.isKeyPressed(Keys.BACKSPACE))
				cameraHelper.setPosition(0, 0);
		}
		
		// Camera Controls (zoom)
		float camZoomSpeed = 1 * deltaTime;
		float camZoomSpeedAccelerationFactor = 5;
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT))
			camZoomSpeed *= camZoomSpeedAccelerationFactor;
		if (Gdx.input.isKeyPressed(Keys.COMMA))
			cameraHelper.addZoom(camZoomSpeed);
		if (Gdx.input.isKeyPressed(Keys.PERIOD)) 
			cameraHelper.addZoom(-camZoomSpeed);
		if (Gdx.input.isKeyPressed(Keys.SLASH))
			cameraHelper.setZoom(1);
	}
	
	/**
	 * Created by Philip Deppen (Milestone 1, 10/30/18, issue 13)
	 * @param x
	 * @param y
	 */
	private void moveCamera (float x, float y) 
	{
		x += cameraHelper.getPosition().x;
		y += cameraHelper.getPosition().y;
		cameraHelper.setPosition(x, y);
	}
	
	
	/**
	 * Created by Philip Deppen (Milestone 1, 10/30/18, issue 13)
	 * Edited by Philip Deppen (Milestone 3, 11/19/18, issue 46)
	 */
	@Override
	public boolean keyUp (int keycode) 
	{
		// Reset game world
		if (keycode == Keys.R) {
			Coins.drawBadBlock = 0;
			init();
			Gdx.app.debug(TAG, "Game world resetted");
		}
		// Toggle camera follow
		else if (keycode == Keys.ENTER) {
			cameraHelper.setTarget(cameraHelper.hasTarget() ? null: level.dude);
		    Gdx.app.debug(TAG, "Camera follow enabled: " + cameraHelper.hasTarget());
		}
		// back to menu
		else if (keycode == Keys.ESCAPE || keycode == Keys.BACK)
		{
			backToMenu();
		}
		
		return false;
	}
	
	/**
	 * Created by Philip Deppen (Milestone 3, 11/18/18, issue 47)
	 * Edited by Philip Deppen (Milestone 3, 11/26/18, issue 51)
	 * Edited by Philip Deppen (Milestone 3, 11/26/18, issue 49)
	 * Adds Box2D physics for game objects. 
	 */
	public void initPhysics()
	{
		if (b2world != null)
			b2world.dispose();
		
	    b2world = new World(new Vector2(0, -9.81f), true);
	    
        b2world.setContactListener(new CollisionHandler(this));
        
        Vector2 origin = new Vector2();
        
        /* ground body */
		for (Ground grnd : level.ground)
		{
			BodyDef bodyDef = new BodyDef();
			bodyDef.type = BodyType.KinematicBody;
			bodyDef.position.set(grnd.position);
			Body body = b2world.createBody(bodyDef);
			// set user data
			body.setUserData(grnd);
			grnd.body = body;
			PolygonShape polygonShape = new PolygonShape();
			origin.x = grnd.bounds.width / 2.0f;
			origin.y = grnd.bounds.height / 2.0f;
			polygonShape.setAsBox(grnd.bounds.width / 2.0f, grnd.bounds.height / 2.0f,origin, 0);
			FixtureDef fixtureDef = new FixtureDef();
			fixtureDef.friction = 0.5f;
			fixtureDef.shape = polygonShape;
			body.createFixture(fixtureDef);
			polygonShape.dispose();
		}
		
		/* coin body */
		for (Coins coin : level.coins) 
		{
	    		BodyDef bodyDef = new BodyDef();
	    		bodyDef.type = BodyType.KinematicBody;
	    		bodyDef.position.set(coin.position);
	    		Body body = b2world.createBody(bodyDef);
	    		body.setUserData(coin);
	    		
	    		coin.body = body;
	    		PolygonShape polygonShape = new PolygonShape();
	    		origin.x = coin.bounds.width / 2.0f;
	    		origin.y = coin.bounds.height / 2.0f;
	    		polygonShape.setAsBox(coin.bounds.width / 2.0f, coin.bounds.height / 2.0f,origin, 0);
	    		FixtureDef fixtureDef = new FixtureDef();
	    		fixtureDef.shape = polygonShape;
	    		fixtureDef.isSensor = true;
	    		body.createFixture(fixtureDef);
	    		polygonShape.dispose();		
    		}
		
		/* star body */
		for (Star star : level.stars)
		{
	    		BodyDef bodyDef = new BodyDef();
	    		bodyDef.type = BodyType.KinematicBody;
	    		bodyDef.position.set(star.position);
	    		Body body = b2world.createBody(bodyDef);
	    		// set user data
	    		body.setUserData(star);
	    		
	    		star.body = body;
	    		PolygonShape polygonShape = new PolygonShape();
	    		origin.x = star.bounds.width / 2.0f;
	    		origin.y = star.bounds.height / 2.0f;
	    		polygonShape.setAsBox(star.bounds.width / 2.0f,
	    		star.bounds.height / 2.0f,origin, 0);
	    		FixtureDef fixtureDef = new FixtureDef();
	    		fixtureDef.shape = polygonShape;
	    		fixtureDef.isSensor = true;
	    		body.createFixture(fixtureDef);
	    		polygonShape.dispose();
		}
		
		/* dude body */
		Dude dude = Level.dude;
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(dude.position);
		Body body = b2world.createBody(bodyDef);
		body.setUserData(dude);
		dude.body = body;
		PolygonShape polygonShape = new PolygonShape();
		origin.x = dude.bounds.width / 2.0f;
		origin.y = dude.bounds.height / 2.0f;
		polygonShape.setAsBox(dude.bounds.width / 2.0f,
		dude.bounds.height / 2.0f,origin, 0);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = polygonShape;
		body.createFixture(fixtureDef);
		polygonShape.dispose();
		
		/* goal (tree) body */
		Trees goal = Level.goal;
		bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(goal.position);
		body = b2world.createBody(bodyDef);
		body.setUserData(goal);	
		goal.body = body;
		polygonShape = new PolygonShape();
		origin.x = goal.bounds.width / 2.0f;
		origin.y = goal.bounds.height / 2.0f;
		polygonShape.setAsBox(goal.bounds.width / 2.0f, goal.bounds.height / 2.0f,origin, 0);
		fixtureDef = new FixtureDef();
		fixtureDef.shape = polygonShape;
		fixtureDef.isSensor = true;
		body.createFixture(fixtureDef);
		polygonShape.dispose();	
        		
	}
	
	/**
	 * Created by Philip Deppen (Milestone 3, 11/19/18, issue 46)
	 * @param deltaTime
	 */
	private void handleInputGame (float deltaTime) 
	{
	   if (cameraHelper.hasTarget(Level.dude)) 
	   {
		   // Player Movement
		   if (Gdx.input.isKeyPressed(Keys.LEFT)) 
		   {
			   Level.dude.body.setLinearVelocity(new Vector2(-3, 0));
			   Level.dude.velocity.x = -3.0f;
		   } 
		   else if (Gdx.input.isKeyPressed(Keys.RIGHT))
		   {
			   Level.dude.body.setLinearVelocity(new Vector2(3,0));
			   Level.dude.velocity.x = 3.0f;
		   } 
		   else 
		   {
			   // Execute auto-forward movement on non-desktop platform
			   if (Gdx.app.getType() != ApplicationType.Desktop) 
			   {
				   Level.dude.velocity.x = Level.dude.terminalVelocity.x;
			   }
		   }
		   
		   // dude Jump
		   if (Gdx.input.isTouched() || Gdx.input.isKeyPressed(Keys.SPACE)) 
		   {
			   Level.dude.setJumping(true);
	       } 
		   else 
		   {
			   Level.dude.setJumping(false);
	       }
	   }
   	}
	
	/**
	 * Created by Philip Deppen (Milestone 4, 11/30/18)
	 */
	private void backToMenu()
	{
		Coins.drawBadBlock = 0;
		
		// load preferences for audio settings and start playing music
		GamePreferences.instance.load();
		AudioManager.instance.play(Assets.instance.music.song01);
		
		// switch to menu screen
		game.setScreen(new MenuScreen(game));
	}

	@Override
	public void dispose() 
	{
		if (b2world != null)
			b2world.dispose();
	}
	
}
