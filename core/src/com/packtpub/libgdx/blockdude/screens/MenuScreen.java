package com.packtpub.libgdx.blockdude.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.packtpub.libgdx.blockdude.game.Assets;
import com.packtpub.libgdx.blockdude.game.WorldController;
import com.packtpub.libgdx.blockdude.util.CharacterSkin;
import com.packtpub.libgdx.blockdude.util.Constants;
import com.packtpub.libgdx.blockdude.util.GamePreferences;
import com.packtpub.libgdx.blockdude.screens.GameScreen;
import com.packtpub.libgdx.blockdude.util.AudioManager;

/**
 * Created by Philip Deppen (Milestone 4, 11/30/18, issue 55)
 * Edited by Philip Deppen (Milestone 4, 12/1/18, issue 62)
 * Edited by Philip Deppen (Milestone 5, 12/5/18, issue 72)
 */
public class MenuScreen extends AbstractGameScreen
{
	private static final String TAG = MenuScreen.class.getName();
	
	private Stage stage;
    private Skin skinBlockDude;
	private Skin skinLibgdx;
    
    // menu
    private Image imgBackground;
    private Image imgLogo;
    private Image imgDude;
    private Button btnMenuPlay;
    private Button btnMenuOptions;
    
    // options
    private Window winOptions;
    private TextButton btnWinOptSave;
    private TextButton btnWinOptCancel;
    private TextButton btnWinOptHighScores;
    private CheckBox chkSound;
    private Slider sldSound;
    private CheckBox chkMusic;
    private Slider sldMusic;
    private SelectBox<CharacterSkin> selCharSkin;
    private Image imgCharSkin;
    private CheckBox chkShowFpsCounter;
	
    // debug
    private final float DEBUG_REBUILD_INTERVAL = 5.0f;
    private boolean debugEnabled = false;
    private float debugRebuildStage;
    
	public MenuScreen(Game game) 
	{
		super(game);
	}

	@Override
	public void render(float deltaTime) 
	{
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if (debugEnabled) {
			debugRebuildStage -= deltaTime;
			if (debugRebuildStage <=  0) {
				debugRebuildStage = DEBUG_REBUILD_INTERVAL;
				rebuildStage();
			}
		}
		stage.act(deltaTime);
		stage.draw();
		stage.setDebugAll(true);
	}

	/**
	 * Created by Philip Deppen (Milestone 4, 12/1/18, issue 62)
	 */
    private void rebuildStage() 
    {
    		skinBlockDude = new Skin (Gdx.files.internal(Constants.SKIN_BLOCKDUDE_UI), new TextureAtlas(Constants.TEXTURE_ATLAS_UI));
    		
    		skinLibgdx = new Skin (Gdx.files.internal(Constants.SKIN_LIBGDX_UI), new TextureAtlas(Constants.TEXTURE_ATLAS_LIBGDX_UI));
    		
    		//build all layers
    		Table layerBackground = buildBackgroundLayer();

    		Table layerObjects = buildObjectsLayer();
    		Table layerLogos = buildLogosLayer();
    		Table layerControls = buildControlsLayer();
    		Table layerOptionsWindow = buildOptionsWindowLayer();
    		
    		// assemble stage for menu screen
    		stage.clear();
    		Stack stack = new Stack();
    		stage.addActor(stack);
    		stack.setSize(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT);
    		stack.add(layerBackground);
    		stack.add(layerObjects);
    		stack.add(layerLogos);
    		stack.add(layerControls);
    		stage.addActor(layerOptionsWindow);
    }
    
    /**
     * Created by Philip Deppen (Milestone 4, 12/1/18, issue 62)
     * draws background image
     * @return Table
     */
    private Table buildBackgroundLayer() 
    {
    		Table layer = new Table();
	    	//layer.left().top();
    		// + Background
    		imgBackground = new Image(skinBlockDude, "background");
    		layer.add(imgBackground);
    		return layer;
    }
    
    /**
     * Created by Philip Deppen (Milestone 4, 12/1/18, issue 62)
     * @return Table
     */
    private Table buildObjectsLayer() 
    {
    		Table layer = new Table();
    		// + Dude
    		imgDude = new Image(skinBlockDude, "blockdude");
    		layer.addActor(imgDude);
    		imgDude.setPosition(355, 40);
    		return layer;
    }
    
    /**
     * Created by Philip Deppen (Milestone 4, 12/1/18, issue 62)
     * anchored in the top-left corner
     * @return Table
     * This resizes and moves the logos on the home screen
     */
    private Table buildLogosLayer () 
    {
	    	Table layer = new Table();
	    	layer.left().top();
	    	// + Game Logo
	    	imgLogo = new Image(skinBlockDude, "logo");
	    	layer.add(imgLogo);
	    	layer.row().expandY();
	    	// + Info Logos
	    	if (debugEnabled) layer.debug();
	    	return layer;
    	}
    
    /**
     * Created by Philip Deppen (Milestone 4, 12/1/18, issue 62)
     * anchored to the bottom-right corner
     * @return Table
     * anchors play button
     */
    private Table buildControlsLayer () 
    {
    		Table layer = new Table();
    		layer.right().bottom();
    		
    		// + Play Button
    		btnMenuPlay = new Button(skinBlockDude, "play");
    		layer.add(btnMenuPlay);
    		btnMenuPlay.addListener(new ChangeListener() {
    			@Override public void changed (ChangeEvent event, Actor actor) {
    				onPlayClicked();
    			}
    		});
    		layer.row();
    		// + Options Button
    		btnMenuOptions = new Button (skinBlockDude, "options");
    		layer.add(btnMenuOptions);
    		btnMenuOptions.addListener(new ChangeListener() {
    			@Override
    			public void changed (ChangeEvent event, Actor actor) {
    				onOptionsClicked();
    			}
    		});
    		if (debugEnabled) {
    			layer.debug();
    		}
        return layer;
    }
    
    //Tyler Major added from page 248
    private void onPlayClicked() 
    {
		// load preferences for audio settings and start playing music
		//GamePreferences.instance.load();
		AudioManager.instance.stopMusic();
    		game.setScreen(new GameScreen(game));
    }
    
    /**
     * Tyler Major (Assignment 6, page 248)
     * Edited by: Philip Deppen (Assignment 6, 259)
     * allows the options window to be opened
     */
    private void onOptionsClicked() 
    {
    		loadSettings();
    		btnMenuPlay.setVisible(false);
    		btnMenuOptions.setVisible(false);
    		winOptions.setVisible(true);
    }
    
    /**
     * Made by Philip Deppen (Assignment 6, p. 259)
     * initializes the options window
     * @return Table
     */
    private Table buildOptionsWindowLayer () 
    {
		winOptions = new Window("Options", skinLibgdx);
		// + Audio Settings: Sound/Music CheckBox and Volume Slider
		winOptions.add(buildOptWinAudioSettings()).row();
		// + Character Skin: Selection Box (White, Gray, Brown)    
		
		winOptions.add(buildOptWinSkinSelection()).row();
		// + Debug: Show FPS Counter
		winOptions.add(buildOptWinDebug()).row();
		// + Separator and Buttons (Save, Cancel)
		winOptions.add(buildOptWinButtons()).pad(10, 0, 10, 0);
    
		// Make options window slightly transparent
		winOptions.setColor(1, 1, 1, 0.8f);
		// Hide options window by default
		winOptions.setVisible(false);
		if (debugEnabled) 
			winOptions.debug();
		// Let TableLayout recalculate widget sizes and positions
		winOptions.pack();
		
		// Move options window to bottom right corner 
		winOptions.setPosition (Constants.VIEWPORT_GUI_WIDTH - winOptions.getWidth() - 50, 50);
		
		return winOptions;
    }
    
    /**
     * Made by Philip Deppen (Assignment 6)
     */
    private void loadSettings() 
    {
    		GamePreferences prefs = GamePreferences.instance;
        prefs.load();
        chkSound.setChecked(prefs.sound);
        sldSound.setValue(prefs.volSound);
        chkMusic.setChecked(prefs.music);
        sldMusic.setValue(prefs.volMusic);
        selCharSkin.setSelectedIndex(prefs.charSkin);
        onCharSkinSelected(prefs.charSkin);
        chkShowFpsCounter.setChecked(prefs.showFpsCounter);
    }
    
    /**
     * Made by Philip Deppen (Assignment 6)
     */
    private void saveSettings() 
    {
    		GamePreferences prefs = GamePreferences.instance;
    		
    		prefs.sound = chkSound.isChecked();
    		prefs.volSound = sldSound.getValue();
    		prefs.music = chkMusic.isChecked();
    		prefs.volMusic = sldMusic.getValue();
    		prefs.charSkin = selCharSkin.getSelectedIndex();
    		prefs.showFpsCounter = chkShowFpsCounter.isChecked();
    		prefs.save();
    }
    
    /**
     * Made by Philip Deppen (Assignment 6)
     */
    private void onCharSkinSelected(int index) 
    {
    		CharacterSkin skin = CharacterSkin.values() [index];
    		imgCharSkin.setColor(skin.getColor());
    }
    
    /**
     * Made by Philip Deppen (Assignment 6)
     * Edited by Philip Deppen (Milestone 5, 12/5/18, issue 72)
     */
    private void onSaveClicked() 
    {
    		saveSettings();
    		onCancelClicked();
    		AudioManager.instance.onSettingsUpdated();
    }
    
    /**
     * Made by Philip Deppen (Assignment 6)
     * Created by Philip Deppen (Milestone 5, 12/5/18, issue 72)
     */
    private void onCancelClicked() 
    {
    		btnMenuPlay.setVisible(true);
    		btnMenuOptions.setVisible(true);
    		winOptions.setVisible(false);
    		AudioManager.instance.onSettingsUpdated();
    }
    
	/**
	 * Made by Philip Deppen (Assignment 6)
	 * use and add widgets defined in the LibGDX ski
	 */
	private Table buildOptWinAudioSettings () 
	{
	    Table tbl = new Table();
	    // + Title: "Audio"
	    tbl.pad(10, 10, 0, 10);
	    tbl.add(new Label("Audio", skinLibgdx, "default-font", Color.ORANGE)).colspan(3);
	    tbl.row();
	    tbl.columnDefaults(0).padRight(10);
	    tbl.columnDefaults(1).padRight(10);
	    // + Checkbox, "Sound" label, sound volume slider
	    chkSound = new CheckBox("", skinLibgdx);
	    tbl.add(chkSound);
	    tbl.add(new Label("Sound", skinLibgdx));
	    sldSound = new Slider(0.0f, 1.0f, 0.1f, false, skinLibgdx);
	    tbl.add(sldSound);
	    tbl.row();
	    // + Checkbox, "Music" label, music volume slider
	    chkMusic = new CheckBox("", skinLibgdx);
	    tbl.add(chkMusic);
	    tbl.add(new Label("Music", skinLibgdx));
	        sldMusic = new Slider(0.0f, 1.0f, 0.1f, false, skinLibgdx);
	        tbl.add(sldMusic);
	        tbl.row();
	        return tbl;
   }
    
	/**
	 * Made by Philip Deppen (Assignment 6, p. 256)
	 * creates a table that contains the skin selection via drop down box
	 * @return Table
	 */
    private Table buildOptWinSkinSelection () 
    {
		Table tbl = new Table();
		
		// + Title: "Character Skin"
		tbl.pad(10, 10, 0, 10);
		tbl.add(new Label("Character Skin", skinLibgdx, "default-font", Color.ORANGE)).colspan(2);
		tbl.row();
		
		// + Drop down box filled with skin items
		selCharSkin = new SelectBox<CharacterSkin>(skinLibgdx);
		selCharSkin.setItems(CharacterSkin.values());
		selCharSkin.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) 
			{
				onCharSkinSelected(((SelectBox<CharacterSkin>)actor).getSelectedIndex());
			}
	     });
		tbl.add(selCharSkin).width(120).padRight(20);
		
		// + Skin preview image
		imgCharSkin = new Image(Assets.instance.dude.dude);
		tbl.add(imgCharSkin).width(50).height(50);
		return tbl;
    	}
    
    /**
     * Made by Philip Deppen (Assignment 6, p. 257)
     * builds a table that contains the debug settings
     */
    private Table buildOptWinDebug()
    {
    		Table tbl = new Table();
    		
    		// + Title: "Debug"
    		tbl.pad(10, 10, 0, 10);
    	    tbl.add(new Label("Debug", skinLibgdx, "default-font", Color.RED)).colspan(3);
    	    tbl.row();
    	    tbl.columnDefaults(0).padRight(10);
    	    tbl.columnDefaults(1).padRight(10);
    	   
    	    // + Checkbox, "Show FPS Counter" label
    	    chkShowFpsCounter = new CheckBox("", skinLibgdx);
    	    tbl.add(new Label("Show FPS Counter", skinLibgdx));
    	    tbl.add(chkShowFpsCounter);
    	    tbl.row();
    	    return tbl;
    }
    
    /**
     * Made by Philip Deppen (Assignment 6, p. 257)
     */
    private Table buildOptWinButtons() 
    {
    
   		Table tbl = new Table();
    		
    		// + Separator 
    		Label lbl = null;
		lbl = new Label ("", skinLibgdx);
		lbl.setColor(0.75f, 0.75f, 0.75f, 1);
		lbl.setStyle(new LabelStyle(lbl.getStyle()));
		lbl.getStyle().background = skinLibgdx.newDrawable("white");
		tbl.add(lbl).colspan(2).height(1).width(220).pad(0, 0, 0, 1);
		tbl.row();
		lbl = new Label("", skinLibgdx);
		lbl.setColor(0.5f, 0.5f, 0.5f, 1);
		lbl.setStyle(new LabelStyle(lbl.getStyle()));
		lbl.getStyle().background = skinLibgdx.newDrawable("white");
		tbl.add(lbl).colspan(2).height(1).width(220).pad(0, 1, 5, 0);
		tbl.row();
		
		// + Save Button with event handler
	    btnWinOptSave = new TextButton("Save", skinLibgdx);
	    tbl.add(btnWinOptSave).padRight(30);
	    btnWinOptSave.addListener(new ChangeListener() {
	    		@Override
	    		public void changed (ChangeEvent event, Actor actor) {
	    			onSaveClicked();
	    		}
	    });
    		
	    // + Cancel Button with event handler
	    btnWinOptCancel = new TextButton("Cancel", skinLibgdx);
	    tbl.add(btnWinOptCancel);
	    btnWinOptCancel.addListener(new ChangeListener() {
	    		@Override
	    		public void changed (ChangeEvent event, Actor actor) {
	    			onCancelClicked();
	    		}
	    });
	    
	    btnWinOptHighScores = new TextButton("High Scores", skinLibgdx);
	    tbl.add(btnWinOptHighScores);
	    btnWinOptHighScores.addListener(new ChangeListener() {
	    		@Override
	    		public void changed(ChangeEvent event, Actor actor) {
	    			WorldController.displayHighScores();
	    		}
	    });
	    
	    return tbl;
    }
    
	@Override
	public void resize(int width, int height) 
	{
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void show() 
	{
		stage = new Stage(new StretchViewport(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT));
		Gdx.input.setInputProcessor(stage);
		rebuildStage();
	}

	@Override
	public void pause() 
	{
		
	}

	@Override
	public void hide() 
	{
		stage.dispose();
		skinBlockDude.dispose();
		skinLibgdx.dispose();
	}


}
