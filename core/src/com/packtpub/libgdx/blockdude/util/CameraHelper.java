package com.packtpub.libgdx.blockdude.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.packtpub.libgdx.blockdude.game.objects.AbstractGameObject;

/**
 * Created by Philip Deppen (Milestone 1, 10/30/18)
 * Edited by Philip Deppen (Milestone 2, 11/6/18, issue 30)
 * manages and manipulates certain parameters of the camera used to render the game world
 */
public class CameraHelper 
{
	private static final String TAG = CameraHelper.class.getName();
	
	private final float MAX_ZOOM_IN = 0.25f;
	private final float MAX_ZOOM_OUT = 10.0f;
	
	private Vector2 position;
	private float zoom;
	
	private AbstractGameObject target;
	
	/**
	 * Created by Philip Deppen (Milestone 1, 10/30/18)
	 * constructor
	 */
	public CameraHelper()
	{
		position = new Vector2();
		//zoom = 1.0f;
		zoom = 2.270987f;
	}
	
	/**
	 * Created by Philip Deppen (Milestone 1, 10/30/18)
	 * Edited by Philip Deppen (Milestone 2, 11/6/18, issue 30)
	 * @param deltatime
	 */
	public void update (float deltatime)
	{
		if (!hasTarget())
			return;
		
		position.x = target.position.x + target.origin.x;
		position.y = target.position.y + target.origin.y;
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
	 * Edited by Philip Deppen (Milestone 2, 11/6/18, issue 30)
	 */
	public void setTarget(AbstractGameObject target)
	{
		this.target = target;
	}
	
	/**
	 * Created by Philip Deppen (Milestone 1, 10/30/18)
	 * Edited by Philip Deppen (Milestone 2, 11/6/18, issue 30)
	 */
	public AbstractGameObject getTarget()
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
	 * Edited by Philip Deppen (Milestone 2, 11/6/18, issue 30)
	 * @param target
	 * @return
	 */
	public boolean hasTarget (AbstractGameObject target)
	{
		return hasTarget() && this.target.equals(target);
	}
	
	/**
	 * Created by Philip Deppen (Milestone 1, 10/30/18)
	 * Edited by Philip Deppen (Milestone 2, 11/12/18, issue 38)
	 * @param camera
	 */
	public void applyTo (OrthographicCamera camera)
	{
		camera.position.x = position.x + 6.6024f;
		camera.position.y = position.y + 2.7722f;
		camera.zoom = zoom;
		//Gdx.app.debug(TAG, "Position x: " + position.x);
		//Gdx.app.debug(TAG, "Position y: " + position.y);
		camera.update();
	}
	
	
	
}
