package com.packtpub.libgdx.blockdude.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.packtpub.libgdx.blockdude.game.WorldController;
import com.packtpub.libgdx.blockdude.game.WorldRenderer;
import com.badlogic.gdx.assets.AssetManager;
import com.packtpub.libgdx.blockdude.game.Assets;

/**
 * Created by Philip Deppen (Milestone 1, 10/29/18)
 * Contains game loop and handles major aspects of game
 */
public class BlockDudeMain implements ApplicationListener {

	private static final String TAG = BlockDudeMain.class.getName();
	
	private WorldController worldController;
    private WorldRenderer worldRenderer;
    private boolean paused;
    
    SpriteBatch batch;
    Texture img; 
    
	/**
	 * Created by Philip Deppen (Milestone 1, 10/29/18)
	 */
	@Override
	public void create() {
		// Set Libgdx log level to DEBUG
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		
		// load assets
		Assets.instance.init(new AssetManager());
		
		// Initialize controller and renderer
		worldController = new WorldController();
		worldRenderer = new WorldRenderer(worldController);	
	}

	/**
	 * Created by Philip Deppen (Milestone 1, 10/29/18)
	 */
	@Override
	public void resize(int width, int height) 
	{
		worldRenderer.resize(width, height);
	}

	/**
	 * Created by Philip Deppen (Milestone 1, 10/29/18)
	 */
	@Override
	public void render() 
	{
		// Do not update game when paused
		if (!paused) 
		{
			// Update game world by the time that has passed
			// since last rendered frame.
			worldController.update(Gdx.graphics.getDeltaTime());
		}
		
		// Sets the clear screen color 
		//#5DADE2
		//#85C1E9
		Gdx.gl.glClearColor(0x85/255.0f, 0xC1/255.0f, 0xE9/255.0f, 0xff/255.0f);
		
//		Gdx.gl.glClearColor(1, 1, 1, 1);
//		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//		
//		batch = new SpriteBatch();
//		img = new Texture("images/background.png");
//		
//		batch.begin();
//		batch.draw(img, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());	
//		batch.end();
		
		// Clears the screen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// Render game world to screen
		worldRenderer.render();
		
	}

	/**
	 * Created by Philip Deppen (Milestone 1, 10/29/18)
	 */
	@Override
	public void pause() 
	{
		paused = true;
	}

	/**
	 * Created by Philip Deppen (Milestone 1, 10/29/18)
	 */
	@Override
	public void resume() 
	{
		Assets.instance.init(new AssetManager());
		paused = false;
	}

	/**
	 * Created by Philip Deppen (Milestone 1, 10/29/18)
	 */
	@Override
	public void dispose() 
	{
		worldRenderer.dispose();
		Assets.instance.dispose();
		//img.dispose();
		//batch.dispose();
	}

}
