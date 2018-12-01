package com.packtpub.libgdx.blockdude.util;

/**
 * Created by Philip Deppen (Milestone 1, 10/29/18)
 * Edited by Philip Deppen (Milestone 2, 11/6/18, issue 27)
 * Edited by Philip Deppen (Milestone 3, 11/26/18, issue 49)
 * holds values that are fixed throughout project
 */
public class Constants 
{
	// Visible game world is 5 meters wide
	public static final float VIEWPORT_WIDTH = 5.0f;
	
	// Visible game world is 5 meters tall
	public static final float VIEWPORT_HEIGHT = 5.0f;
	
	// Location of description file for texture atlas
	public static final String TEXTURE_ATLAS_OBJECTS = "images/blockdude.pack.atlas";
	
	// GUI Width
	public static final float VIEWPORT_GUI_WIDTH = 800.0f;
	
	// GUI Height
	public static final float VIEWPORT_GUI_HEIGHT = 480.0f;
	
	// Location of image file for level 01
	public static final String LEVEL_01 = "levels/level-01.png";
	
	// Amount of extra lives at level start
	public static final int LIVES_START = 3;
	
	// star powerup time (seconds)
	public static final float ITEM_STAR_POWERUP_DURATION = 9;
	
	//main menu
	public static final String TEXTURE_ATLAS_UI = "images-ui/blockdude-ui.pack.atlas"; 
	public static final String TEXTURE_ATLAS_LIBGDX_UI = "images-ui/uiskin.atlas"; 
	
	// Location of description file for skins
	public static final String SKIN_LIBGDX_UI = "images-ui/uiskin.json"; 
	public static final String SKIN_CANYONBUNNY_UI ="images-ui/blockdude-ui.json"; 

	public static final String PREFERENCES = "Blockdude.prefs";
}
