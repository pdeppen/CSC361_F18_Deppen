package com.packtpub.libgdx.blockdude.game;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.ObjectMap;
import com.packtpub.libgdx.blockdude.game.objects.AbstractGameObject;
import com.packtpub.libgdx.blockdude.game.objects.Coins;
import com.packtpub.libgdx.blockdude.game.objects.Dude;
import com.packtpub.libgdx.blockdude.game.objects.Dude.JUMP_STATE;
import com.packtpub.libgdx.blockdude.game.objects.Ground;
import com.packtpub.libgdx.blockdude.game.objects.Star;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;

/**
 * Created by Philip Deppen (Milestone 3, 11/18/18, issue 47)
 */
public class CollisionHandler implements ContactListener 
{
	private WorldController worldController;
    private ObjectMap<Short, ObjectMap<Short, ContactListener>> listeners;
    
    public CollisionHandler(WorldController worldController)
    {
    		this.worldController = worldController;
        listeners = new ObjectMap<Short, ObjectMap<Short, ContactListener>>();
    }
    
    /**
     * Created by Philip Deppen (Milestone 3, 11/18/18, issue 47)
     * Edited by Philip Deppen (Milestone 3, 11/26/18, issue 51)
     * Edited by Philip Deppen (Milestone 3, 11/26/18, issue 49)
     * @param contact
     */
    private void processContact(Contact contact)
    {
    		Fixture fixtureA = contact.getFixtureA();
    		Fixture fixtureB = contact.getFixtureB();
    		AbstractGameObject objA = (AbstractGameObject)fixtureA.getBody().getUserData();
    		AbstractGameObject objB = (AbstractGameObject)fixtureB.getBody().getUserData();
    		
    		if (((objA instanceof Dude) && (objB instanceof Ground)) || ((objA instanceof Ground) && (objB instanceof Dude)))
        {
            processGroundContact(fixtureA,fixtureB);
        }
//    		if ((objA instanceof Ground) && (objB instanceof Dude))
//    		{
//    			processGroundContact(fixtureB, fixtureA);
//    		}
        if (((objA instanceof Dude) && (objB instanceof Coins)) || ((objA instanceof Coins) && (objB instanceof Dude)))
        {
        		processCoinContact(fixtureA, fixtureB);
        }
     
        if (((objA instanceof Dude) && (objB instanceof Star)) || ((objA instanceof Star) && (objB instanceof Dude)))
        {
        		processStarContact (fixtureA, fixtureB);
        }
    }
    
    /**
     * Created by Philip Deppen (Milestone 3, 11/26/18, issue 49)
     */
    private void processStarContact(Fixture dudeFixture, Fixture starFixture)
    {
    		Dude dude = (Dude) starFixture.getBody().getUserData();
    		Star star = (Star) dudeFixture.getBody().getUserData();
    		
    		star.collected = true;
    		worldController.level.dude.setStarPowerup(true);
    		Gdx.app.log(this.getClass().getName(), "star collected");
    }
    
    /**
     * Created by Philip Deppen (Milestone 3, 11/26/18, issue 51)
     */
    private void processCoinContact(Fixture dudeFixture, Fixture coinFixture)
    {
    		Dude dude = (Dude) coinFixture.getBody().getUserData();
    		Coins coins = (Coins) dudeFixture.getBody().getUserData();
    		    		
    		coins.collected = true;
    		
    		if (coins.badBlock)
    		{
        		Gdx.app.log("CollisionHandler", "Bad block hit. Life lost");
        		worldController.lives--;
        		
        		/* ends game if player hits a bad block and has no lives left */
        		if (worldController.lives == 0)
        			worldController.badBlockHit = true;
    		}
    		
    		else if (coins.goodBlock) {
        		Gdx.app.log("CollisionHandler", "Bad block hit. Life lost");
        		worldController.score += coins.getScore();
    		}
    }
    
    /**
     * Created by Philip Deppen (Milestone 3, 11/18/18, issue 47)
     * @param dudeFixture
     * @param groundFixture
     */
    private void processGroundContact(Fixture dudeFixture, Fixture groundFixture)
    {
    		//System.out.println("Ground contact");
    	    Dude dude = (Dude) groundFixture.getBody().getUserData();
        Ground ground = (Ground) dudeFixture.getBody().getUserData();
        // static ?
        dude = Level.dude;
        float heightDifference = Math.abs(dude.position.y - (  ground.position.y + ground.bounds.height));
         
         if (heightDifference > 0.25f) 
         {
             boolean hitRightEdge = dude.position.x > (ground.position.x + ground.bounds.width / 2.0f);
             if (hitRightEdge) 
             {
            	 	dude.position.x = ground.position.x + ground.bounds.width;
             }
             else 
             {
            	 	dude.position.x = ground.position.x - dude.bounds.width;
             }
             return;
         }
         
         switch (dude.jumpState) 
         {
             case GROUNDED:
                 break;
             case FALLING:
             case JUMP_FALLING:
            	 	dude.position.y = ground.position.y + dude.bounds.height  + dude.origin.y;
            	 	dude.jumpState = JUMP_STATE.GROUNDED;
            	 	break;
             case JUMP_RISING:
            	 	dude.position.y = ground.position.y + dude.bounds.height + dude.origin.y;
                 break;
         }
    }
    
    /**
     * Created by Philip Deppen (Milestone 3, 11/26/18, issue 50)
     * @param contact
     */
	@Override
	public void beginContact(Contact contact) {
		Fixture fixtureA = contact.getFixtureA();
		Fixture fixtureB = contact.getFixtureB();
		processContact(contact);

		Gdx.app.log("CollisionHandler-begin A", "begin");

		ContactListener listener = getListener(fixtureA.getFilterData().categoryBits, fixtureB.getFilterData().categoryBits);
		if (listener != null)
		{
		    listener.beginContact(contact);
	    }	
	}

	/**
	 * Created by Philip Deppen (Milestone 3, 11/26/18, issue 50)
	 * @param contact
	 */
	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		Fixture fixtureA = contact.getFixtureA();
		Fixture fixtureB = contact.getFixtureB();

		Gdx.app.log("CollisionHandler-end A", "end");

		 Gdx.app.log("CollisionHandler-end A", fixtureA.getBody().getLinearVelocity().x+" : "+fixtureA.getBody().getLinearVelocity().y);
		 Gdx.app.log("CollisionHandler-end B", fixtureB.getBody().getLinearVelocity().x+" : "+fixtureB.getBody().getLinearVelocity().y);
		ContactListener listener = getListener(fixtureA.getFilterData().categoryBits, fixtureB.getFilterData().categoryBits);
	    if (listener != null)
		{
		    listener.endContact(contact);
		}
	}
	
	/**
	 * Created by Philip Deppen (Milestone 3, 11/26/18)
	 * @param categoryA
	 * @param categoryB
	 * @return
	 */
	private ContactListener getListener(short categoryA, short categoryB)
	{
		ObjectMap<Short, ContactListener> listenerCollection = listeners.get(categoryA);
		if (listenerCollection == null)
		{
		    return null;
		}
		return listenerCollection.get(categoryB);
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) 
	{
		this.processContact(contact);
	}
    
}
