package com.packtpub.libgdx.blockdude.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.packtpub.libgdx.blockdude.util.Constants;
import com.packtpub.libgdx.blockdude.util.GamePreferences;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

/**
 * Created by Philip Deppen (Milestone 1, 10/29/18)
 * Edited by Philip Deppen (Milestone 2, 11/6/18, issue 31)
 * Edited by Philip Deppen (Milestone 2, 11/12/18, issue 39)
 * Edited by Philip Deppen (Milestone 4, 12/1/18, issue 63)
 * renders game screen
 */
public class WorldRenderer implements Disposable
{
	private OrthographicCamera camera;
    private SpriteBatch batch;
    private WorldController worldController;
    private OrthographicCamera cameraGUI;
    
    private static final boolean DEBUG_DRAW_BOX2D_WORLD = false;
    private Box2DDebugRenderer b2debugRenderer;
    
    /**
     * Created by Philip Deppen (Milestone 1, 10/29/18)
     * constructor
     * @param worldController
     */
    public WorldRenderer (WorldController worldController) 
    {
    		this.worldController = worldController;
    		init();
    }
    
    /**
     * Created by Philip Deppen (Milestone 1, 10/29/18)
     * Edited by Philip Deppen (Milestone 2, 11/12/18, issue 39)
     */
    private void init () 
    {
    		batch = new SpriteBatch();
    		camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
    		camera.position.set(0, 0, 0);
    		camera.update();
    		cameraGUI = new OrthographicCamera(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT);
    		
    		cameraGUI.position.set(0, 0, 0);
    		cameraGUI.setToOrtho(true); // flip y-axis
    		cameraGUI.update();
    		
    		b2debugRenderer = new Box2DDebugRenderer();
    }
    
    /**
     * Created by Philip Deppen (Milestone 1, 10/29/18)
     * Edited by Philip Deppen (Milestone 1, 10/30/18, issue 14)
     * Edited by Philip Deppen (Milestone 2, 11/6/18, issue 31)
     * Defines in which order the game objects are drawn over others
     */
    public void render () 
    {
    		renderWorld(batch);
    		renderGui(batch);
    }
    	
	/**
	 * Created by Philip Deppen (Milestone 2, 11/6/18, issue 31)
	 * @param batch
	 */
	private void renderWorld (SpriteBatch batch)
	{
		worldController.cameraHelper.applyTo(camera);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		worldController.level.render(batch);
		batch.end();
		
		if (DEBUG_DRAW_BOX2D_WORLD) {
			b2debugRenderer.render(worldController.b2world, camera.combined);
		}
	}
	
    /**
     * Created by Philip Deppen (Milestone 1, 10/29/18)
     * Edited by Philip Deppen (Milestone 2, 11/12/18, issue 39)
     * this method is called when the screen size is changed
     * @param width
     * @param height
     */
    public void resize (int width, int height) 
    { 
    		camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) * width;
    		camera.update();
    		
    		cameraGUI.viewportHeight = Constants.VIEWPORT_GUI_HEIGHT;
    		cameraGUI.viewportWidth = (Constants.VIEWPORT_GUI_HEIGHT / (float) height) * (float) width;
    		cameraGUI.position.set(cameraGUI.viewportWidth / 2, cameraGUI.viewportHeight / 2, 0);
    		cameraGUI.update();
    }
    
    /**
     * Created by Philip Deppen (Milestone 2, 11/12/18, issue 39)
     * @param batch
     */
    private void renderGuiScore (SpriteBatch batch)
    {
    		float x = -15;
    		float y = -15;
    		batch.draw(Assets.instance.coin.coin, x, y, 50, 50, 100, 100, 0.35f, -0.35f, 0);
    		Assets.instance.fonts.defaultBig.draw(batch, "" + worldController.score, x + 75, y + 37);
    }
    
    /**
     * Created by Philip Deppen (Milestone 2, 11/12/18, issue 39)
     * @param batch
     */
    private void renderGuiExtraLives (SpriteBatch batch)
    {
    		float x = cameraGUI.viewportWidth - 50 - Constants.LIVES_START * 50;
    		float y = -15;
    		
    		for (int i = 0; i < Constants.LIVES_START; i++)
    		{
    			if (worldController.lives <= i)
    				batch.setColor(0.5f, 0.5f, 0.5f, 0.5f);
			batch.draw(Assets.instance.dude.dude, x + i * 50, y, 50, 50, 120, 100, 0.35f, -0.35f, 0);
			batch.setColor(1, 1, 1, 1);
    		}
    }
    
    /**
     * Created by Philip Deppen (Milestone 2, 11/12/18, issue 39)
     */
    private void renderGuiFpsCounter (SpriteBatch batch)
    {
    		float x = cameraGUI.viewportWidth - 55;
    		float y = cameraGUI.viewportHeight - 15;
    		
    		int fps = Gdx.graphics.getFramesPerSecond();
    		BitmapFont fpsFont = Assets.instance.fonts.defaultNormal;
    		
    		if (fps >= 45) {
    	        // 45 or more FPS show up in green
    	        fpsFont.setColor(0, 1, 0, 1);
    		} else if (fps >= 30) {
    	        // 30 or more FPS show up in yellow
	        fpsFont.setColor(1, 1, 0, 1);
    		} else {
    	         // less than 30 FPS show up in red	
    	   		fpsFont.setColor(1, 0, 0, 1);
    		}
       
    		fpsFont.draw(batch, "FPS: " + fps, x, y);
    		fpsFont.setColor(1, 1, 1, 1); // white
    }
    
    /**
     * Created by Philip Deppen (Milestone 2, 11/12/18, issue 39)
     * @param batch
     */
    private void renderGui (SpriteBatch batch)
    {
    		batch.setProjectionMatrix(cameraGUI.combined);
    		batch.begin();
    		// draw collected gold coins icon + text
    		renderGuiScore(batch);
    		
    		// draw lives left
    		renderGuiExtraLives(batch);
    		
    		// draw fps
    		if (GamePreferences.instance.showFpsCounter)
    			renderGuiFpsCounter(batch);
    		
    		renderGuiGameOverMessage(batch);
    		
    		batch.end();
    }
    
    /**
     * Created by Philip Deppen (Milestone n/a, 12/3/18, issue 57)
     */
    private void renderGuiGameOverMessage(SpriteBatch batch)
    {
		float x = cameraGUI.viewportWidth / 2;
		float y = cameraGUI.viewportHeight / 2;
		if (worldController.isGameOver()) 
		{
			BitmapFont fontGameOver = Assets.instance.fonts.defaultBig;
			fontGameOver.setColor(1, 0.75f, 0.25f, 1);
			fontGameOver.draw(batch, "GAME OVER", x, y, 0, Align.center, false);		// need to fix alignment center
			fontGameOver.setColor(1, 1, 1, 1);
		}
    }
    
    /**
     * Created by Philip Deppen (Milestone 1, 10/29/18)
     * frees allocated memory
     */
    @Override 
    public void dispose () 
    { 
    		batch.dispose();
    }
    
    
}
