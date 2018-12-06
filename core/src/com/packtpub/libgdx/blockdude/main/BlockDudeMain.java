package com.packtpub.libgdx.blockdude.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.packtpub.libgdx.blockdude.game.WorldController;
import com.packtpub.libgdx.blockdude.game.WorldRenderer;
import com.badlogic.gdx.assets.AssetManager;
import com.packtpub.libgdx.blockdude.game.Assets;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.packtpub.libgdx.blockdude.game.Assets;
import com.packtpub.libgdx.blockdude.screens.MenuScreen;
import com.packtpub.libgdx.blockdude.util.AudioManager;
import com.packtpub.libgdx.blockdude.util.GamePreferences;

/**
 * Created by Philip Deppen (Milestone 1, 10/29/18)
 * Edited by Philip Deppen (Milestone 4, 11/30/18, issue 59)
 * Edited by Philip Deppen (Milestone 5, 12/5/18, issue 72)
 * Contains game loop and handles major aspects of game
 */
public class BlockDudeMain extends Game {
    
	/**
	 * Created by Philip Deppen (Milestone 1, 10/29/18)
	 * Edited by Philip Deppen (Milestone 4, 11/30/18, issue 59)
	 * Edited by Philip Deppen (Milestone 5, 12/5/18, issue 72)
	 */
	@Override
	public void create() {
		// Set Libgdx log level to DEBUG
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		
		// load assets
		Assets.instance.init(new AssetManager());
		
		// load preferences for audio settings and start playing music
		GamePreferences.instance.load();
		AudioManager.instance.play(Assets.instance.music.song01);
		
		// Start game at menu screen
		setScreen(new MenuScreen(this));
	}

}
