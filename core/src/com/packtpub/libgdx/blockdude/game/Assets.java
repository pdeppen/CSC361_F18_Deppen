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

/**
 * Created by Philip Deppen (Milestone 1, 10/30/18)
 */
public class Assets implements Disposable, AssetErrorListener
{

	public static final String TAG = Assets.class.getName();
	public static final Assets instance = new Assets();
	private AssetManager assetManager;
	
	public AssetBlockDude dude;
	public AssetBlock block;
	public AssetDoor door;
	public AssetStar star;
	public AssetBrokenBlock brokenBlock;
	public AssetMovingBlock movingBlock;
	public AssetGround ground;
	public AssetLevelDecoration levelDecoration;
	
	// singleton: prevent instantiation from other classes
	private Assets() {}
	
	/**
	 * Created by Philip Deppen (Milestone 1, 10/30/18)
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
		dude = new AssetBlockDude(atlas);
		block = new AssetBlock(atlas);
		door = new AssetDoor(atlas);
		star = new AssetStar(atlas);
		brokenBlock = new AssetBrokenBlock(atlas);
		movingBlock = new AssetMovingBlock(atlas);
		ground = new AssetGround(atlas);
		levelDecoration = new AssetLevelDecoration(atlas);
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
		
		public AssetLevelDecoration(TextureAtlas atlas)
		{
			ufo01 = atlas.findRegion("ufo1");
			ufo02 = atlas.findRegion("ufo2");
			tree = atlas.findRegion("tree");
			sun = atlas.findRegion("sun");
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
	 * inner class
	 */
	public class AssetBlock
	{
		public final AtlasRegion block;
		
		public AssetBlock (TextureAtlas atlas)
		{
			block = atlas.findRegion("block");
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
	 */
	@Override
	public void dispose() 
	{
		assetManager.dispose();
	}
	
	
	
}
