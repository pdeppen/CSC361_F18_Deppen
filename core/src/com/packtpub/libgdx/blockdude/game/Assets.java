package com.packtpub.libgdx.blockdude.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;
import com.packtpub.libgdx.blockdude.util.Constants;
import com.packtpub.libgdx.blockdude.game.Assets.AssetLevelDecoration;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Philip Deppen (Milestone 1, 10/30/18)
 * Edited by Philip Deppen (Milestone 2, 11/12/18, issue 40)
 * Edited by Philip Deppen (Milestone n/a, 12/3/18, issue 67)
 * Edited by Philip Deppen (Milestone 5, 12/5/18, issue 72)
 */
public class Assets implements Disposable, AssetErrorListener
{

	public static final String TAG = Assets.class.getName();
	public static final Assets instance = new Assets();
	private AssetManager assetManager;
	
	public AssetBlockDude dude;
	public AssetDoor door;
	public AssetStar star;
	public AssetBrokenBlock brokenBlock;
	public AssetMovingBlock movingBlock;
	public AssetGround ground;
	public AssetLevelDecoration levelDecoration;
	public AssetFonts fonts;	
	
	/* coins and block share the same class (Coins) */
	public AssetCoin coin;
	public AssetBlock block;
	
	public AssetSounds sounds;
	public AssetMusic music;
	
	// singleton: prevent instantiation from other classes
	private Assets() {}
	
	/**
	 * Created by Philip Deppen (Milestone 5, 12/5/18, issue 73)
	 */
	public class AssetSounds {
		public final Sound jump;
		public final Sound pickupCoin;
		public final Sound pickupStar;
		public final Sound liveLost;
		
		public AssetSounds(AssetManager am) 
		{
			jump = am.get("sounds/jump.wav");
			pickupCoin = am.get("sounds/coin.wav");
			pickupStar = am.get("sounds/star.wav");
			liveLost = am.get("sounds/livelost.wav");
		}
	}
	
	/**
	 * Created by Philip Deppen (Milestone 5, 12/5/18, issue 72)
	 */
	public class AssetMusic {
		public final Music song01;
		
		public AssetMusic (AssetManager am)
		{
			song01 = am.get("music/music.mp3", Music.class);
		}
	}
	
	/**
	 * Created by Philip Deppen (Milestone 1, 10/30/18)
	 * Edited by Philip Deppen (Milestone 2, 11/12/18, issue 40)
	 * This method is called when the game starts
	 * @param assetManager
	 */
	public void init (AssetManager assetManager)
	{
		this.assetManager = assetManager;
		
		// set asset manager error handler
		assetManager.setErrorListener(this);
		
		// load texture atlas
		assetManager.load(Constants.TEXTURE_ATLAS_OBJECTS, TextureAtlas.class);
		
		// load music
	    assetManager.load("music/music.mp3", Music.class);
		
	    // load sounds
	    assetManager.load("sounds/jump.wav", Sound.class);
		assetManager.load("sounds/coin.wav", Sound.class);
		assetManager.load("sounds/star.wav", Sound.class);
		assetManager.load("sounds/livelost.wav", Sound.class);
		
	    // start loading assets and wait until finished
		assetManager.finishLoading();
		
		Gdx.app.debug(TAG, "# of assets loaded: " + assetManager.getAssetNames().size);
		
		for (String a : assetManager.getAssetNames())
			Gdx.app.debug(TAG, "asset: " + a);
		
		TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS_OBJECTS);
		
		// enable texture filtering for pixel smoothing
		for (Texture t: atlas.getTextures()) {
			t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
		
		// create game resource objects
		fonts = new AssetFonts();
		dude = new AssetBlockDude(atlas);
		block = new AssetBlock(atlas);
		door = new AssetDoor(atlas);
		star = new AssetStar(atlas);
		brokenBlock = new AssetBrokenBlock(atlas);
		movingBlock = new AssetMovingBlock(atlas);
		ground = new AssetGround(atlas);
		coin = new AssetCoin(atlas);
		levelDecoration = new AssetLevelDecoration(atlas);
		
		music = new AssetMusic(assetManager);
		sounds = new AssetSounds(assetManager);
	}
	
	/**
	 * Created by Philip Deppen (Milestone 2, 11/5/18)
	 * decorates level
	 */
	public class AssetLevelDecoration
	{
		public final AtlasRegion ufo01;
		public final AtlasRegion ufo02;
		public final AtlasRegion tree;
		public final AtlasRegion sun;
		public final AtlasRegion background;
		
		public AssetLevelDecoration(TextureAtlas atlas)
		{
			ufo01 = atlas.findRegion("ufo1");
			ufo02 = atlas.findRegion("ufo2");
			tree = atlas.findRegion("tree");
			sun = atlas.findRegion("sun");
			background = atlas.findRegion("background");
		}
	}
	
	/**
	 * Created by Philip Deppen (Milestone 1, 10/30/18)
	 * inner class
	 */
	public class AssetBlockDude 
	{
		public final AtlasRegion dude;
		
		public AssetBlockDude (TextureAtlas atlas)
		{
			dude = atlas.findRegion("blockdude");
		}
	}
	
	/**
	 * Created by Philip Deppen (Milestone 1, 10/30/18)
	 * Edited by Philip Deppen (Milestone 5, 12/6/18, issue 74)
	 * inner class
	 */
	public class AssetBlock
	{
		public final AtlasRegion block;
		public final Animation animBlock;
		
		public AssetBlock (TextureAtlas atlas)
		{
			block = atlas.findRegion("block");
			
			// Animation: Block
			Array<AtlasRegion> regions = atlas.findRegions("anim_block");
			AtlasRegion region = regions.first();
			
			for (int i = 0; i < 10; i++)
				regions.insert(0, region);
			
			animBlock = new Animation(1.0f / 4.0f, regions, Animation.PlayMode.LOOP);
		}
	}
	
	/**
	 * Created by Philip Deppen (Milestone 2, 11/5/18)
	 * inner class
	 */
	public class AssetBrokenBlock
	{
		public final AtlasRegion brokenBlock;
		
		public AssetBrokenBlock (TextureAtlas atlas)
		{
			brokenBlock = atlas.findRegion("brokenblock");
		}
	}
	
	/**
	 * Created by Philip Deppen (Milestone 1, 11/5/18)
	 * inner class
	 */
	public class AssetMovingBlock
	{
		public final AtlasRegion movingBlock;
		
		public AssetMovingBlock (TextureAtlas atlas)
		{
			movingBlock = atlas.findRegion("movingblock");
		}
	}
	
	/**
	 * Created by Philip Deppen (Milestone 1, 10/30/18)
	 * inner class
	 */
	public class AssetDoor
	{
		public final AtlasRegion door;
		
		public AssetDoor (TextureAtlas atlas)
		{
			door = atlas.findRegion("door");
		}
	}
	
	
	/**
	 * Created by Philip Deppen (Milestone 1, 10/30/18)
	 * inner class
	 */
	public class AssetStar
	{
		public final AtlasRegion star;
		
		public AssetStar (TextureAtlas atlas)
		{
			star = atlas.findRegion("star");
		}
	}
	
	/**
	 * Created by Philip Deppen (Milestone 2, 11/5/18, issue 22)
	 * inner class
	 */
	public class AssetGround
	{
		public final AtlasRegion ground;
		
		public AssetGround (TextureAtlas atlas)
		{
			ground = atlas.findRegion("ground");
		}
	}

	/**
	 * Created by Philip Deppen (Milestone 2, 11/12/18, issue 39)
	 * inner class
	 */
	public class AssetCoin
	{
		public final AtlasRegion coin;
		
		public AssetCoin (TextureAtlas atlas)
		{
			coin = atlas.findRegion("coin");
		}
	}
	/**
	 * Created by Philip Deppen (Milestone 2, 11/12/18, issue 40)
	 * inner class for fonts
	 */
	public class AssetFonts 
	{
		public final BitmapFont defaultSmall;
		public final BitmapFont defaultNormal;
		public final BitmapFont defaultBig;
		
		public AssetFonts()
		{
			// create three fonts using Libgdx's 15px bitmap font
			// create three fonts using Libgdx's 15px bitmap font
			defaultSmall = new BitmapFont(Gdx.files.internal("../core/assets/font/arial-15.fnt"), true);
			defaultNormal = new BitmapFont(Gdx.files.internal("../core/assets/font/arial-15.fnt"), true);
			defaultBig = new BitmapFont(Gdx.files.internal("../core/assets/font/arial-15.fnt"), true);
			
			// set font sizes
			defaultSmall.getData().setScale(0.75f);
			defaultNormal.getData().setScale(1.0f);
			defaultBig.getData().setScale(2.0f);
			
			// enable linear texture filtering for smooth fonts
			defaultSmall.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
			defaultNormal.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
			defaultBig.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
	}
	
	/**
	 * Created by Philip Deppen (Milestone 1, 10/30/18)
	 * Handles an error
	 * @param filename
	 * @param type
	 * @param throwable
	 */
	public void error (String filename, Class type, Throwable throwable) {
		Gdx.app.error(TAG, "Couldn't load asset: '" + filename + "'", (Exception)throwable);
	}
	
	/**
	 * Created by Philip Deppen (Milestone 1, 10/30/18)
	 * handles an error
	 */
	@Override
	public void error (AssetDescriptor asset, Throwable throwable) {
		Gdx.app.error(TAG, "Couldn't load asset '" + asset.fileName + "'", (Exception)throwable);
	}
	
	/**
	 * Created by Philip Deppen (Milestone 1, 10/30/18)
	 * Edited by Philip Deppen (Milestone 2, 11/12/18, issue 40)
	 */
	@Override
	public void dispose() 
	{
		assetManager.dispose();
		fonts.defaultSmall.dispose();
		fonts.defaultNormal.dispose();
		fonts.defaultBig.dispose();
	}
	
	
	
}
