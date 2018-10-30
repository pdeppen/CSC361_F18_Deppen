package com.packtpub.libgdx.blockdude.util;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Philip Deppen (Milestone 1, 10/30/18)
 * manages and manipulates certain parameters of the camera used to render the game world
 */
public class CameraHelper 
{
	private static final String TAG = CameraHelper.class.getName();
	
	private final float MAX_ZOOM_IN = 0.25f;
	private final float MAX_ZOOM_OUT = 10.0f;
	
	private Vector2 position;
	private float zoom;
	
	private Sprite target;
	
	/**
	 * Created by Philip Deppen (Milestone 1, 10/30/18)
	 * constructor
	 */
	public CameraHelper()
	{
		position = new Vector2();
		zoom = 1.0f;
	}
	
	/**
	 * Created by Philip Deppen (Milestone 1, 10/30/18)
	 * @param deltatime
	 */
	public void update (float deltatime)
	{
		if (!hasTarget())
			return;
		
		position.x = target.getX() + target.getOriginX();
		position.y = target.getY() + target.getOriginY();
	}
	
	/**
	 * Created by Philip Deppen (Milestone 1, 10/30/18)
	 */
	public void setPosition(float x, float y)
	{
		this.position.set(x, y);
	}
	
	/**
	 * Created by Philip Deppen (Milestone 1, 10/30/18)
	 */
	public Vector2 getPosition()
	{
		return position;
	}
	
	/**
	 * Created by Philip Deppen (Milestone 1, 10/30/18)
	 */
	public void addZoom(float amount)
	{
		setZoom(zoom + amount);
	}
	
	/**
	 * Created by Philip Deppen (Milestone 1, 10/30/18)
	 */
	public void setZoom(float zoom)
	{
		this.zoom = MathUtils.clamp(zoom, MAX_ZOOM_IN, MAX_ZOOM_OUT);
	}
	
	/**
	 * Created by Philip Deppen (Milestone 1, 10/30/18)
	 */
	public float getZoom()
	{
		return zoom;
	}
	
	/**
	 * Created by Philip Deppen (Milestone 1, 10/30/18)
	 */
	public void setTarget(Sprite target)
	{
		this.target = target;
	}
	
	/**
	 * Created by Philip Deppen (Milestone 1, 10/30/18)
	 */
	public Sprite getTarget()
	{
		return target;
	}
		
	/**
	 * Created by Philip Deppen (Milestone 1, 10/30/18)
	 */
	public boolean hasTarget()
	{
		return target != null;
	}
	
	/**
	 * Created by Philip Deppen (Milestone 1, 10/30/18)
	 * @param target
	 * @return
	 */
	public boolean hasTarget(Sprite target)
	{
		return hasTarget() && this.target.equals(target);
	}
	
	public void applyTo (OrthographicCamera camera)
	{
		camera.position.x = position.x;
		camera.position.y = position.y;
		camera.zoom = zoom;
		camera.update();
	}
	
	
	
}
