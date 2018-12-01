package com.packtpub.libgdx.blockdude.util;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by Philip Deppen (Milestone 4, 12/1/18, issue 63)
 */
public enum CharacterSkin 
{
	WHITE("White", 1.0f, 1.0f, 1.0f),
	GRAY("Gray", 0.7f, 0.7f, 0.7f),
	BROWN("Brown", 0.7f, 0.5f, 0.3f);
	
	private String name;
	private Color color = new Color();
	private CharacterSkin (String name, float r, float g, float b) 
	{
		this.name = name;
		color.set(r, g, b, 1.0f);
	}
	
	/**
	 * Created by Philip Deppen (Milestone 4, 12/1/18, issue 63)
	 * All character skins are defined using a name
	 * that is used for display and RGB color values
	 */
	@Override
	public String toString () 
	{
		return name;
	}
	
	/**
	 * Created by Philip Deppen (Milestone 4, 12/1/18, issue 63)
	 * @return the color value used to describe a color
	 */
	public Color getColor () 
	{
		return color;
	}
}
