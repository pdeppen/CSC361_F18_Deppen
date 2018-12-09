package com.packtpub.libgdx.blockdude.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.badlogic.gdx.utils.Array;
import com.packtpub.libgdx.blockdude.game.objects.AbstractGameObject;
import com.packtpub.libgdx.blockdude.game.objects.Background;
import com.packtpub.libgdx.blockdude.game.objects.Coins;
import com.packtpub.libgdx.blockdude.game.objects.Dude;
import com.packtpub.libgdx.blockdude.game.objects.Ground;
import com.packtpub.libgdx.blockdude.game.objects.Star;
import com.packtpub.libgdx.blockdude.game.objects.Sun;
import com.packtpub.libgdx.blockdude.game.objects.Trees;
import com.packtpub.libgdx.blockdude.game.objects.Ufos;
import com.packtpub.libgdx.blockdude.game.Level.BLOCK_TYPE;
import com.packtpub.libgdx.blockdude.game.objects.Ground;

/**
 * Created by Philip Deppen (Milestone 2, 11/6/18, issue 26)
 * Edited by Philip Deppen (Milestone 3, 11/12/18, issue 41)
 * Edited by Philip Deppen (Milestone 3, 11/13/18, issue 43)
 * Edited by Philip Deppen (Milestone 3, 11/26/18, issue 49)
 */
public class Level 
{
	public static final String TAG = Level.class.getName();
	
	/**
	 * Created by Philip Deppen (Milestone 2, 11/6/18, issue 26)
	 * this enum represents entire game world objects
	 */
	public enum BLOCK_TYPE 
	{
		EMPTY (0, 0, 0), // black
		GROUND (0, 255, 0), // green
		PLAYER_SPAWNPOINT (255, 255, 255), // white
		ITEM_STAR (255, 0, 255),
		GOAL(236, 56, 56), // red
		ITEM_COIN (255, 255, 0); // purple
	
		private int color;
		
		private BLOCK_TYPE (int r, int g, int b)
		{
			color = r << 24 | g << 16 | b << 8 | 0xff;
		}
		
		public boolean sameColor (int color)
		{
			return this.color == color;
		}
		
		public int getColor ()
		{
			return color;
		}
	}
	
	// objects 
	public Array<Ground> ground;
	public static Dude dude;
	public Array<Coins> coins;
	public Array<Star> stars;
	
	// decoration
	public Ufos ufos;
	public Sun sun;
	public Background background;
	public static Trees goal;
	
	/**
	 * Created by Philip Deppen (Milestone 2, 11/6/18, issue 26)
	 * @param filename
	 */
	public Level (String filename)
	{
		init(filename);
	}
	
	/**
	 * Created by Philip Deppen (Milestone 2, 11/6/18, issue 26)
	 * Edited by Philip Deppen (Milestone 3, 11/26/18, issue 49)
	 * @param filename
	 */
	private void init (String filename) 
	{
		// objects
		ground = new Array<Ground>();
		coins = new Array<Coins>();
		stars = new Array<Star>();
		
		// player character
		dude = null;
		
		// load image file that represents the level data
		Pixmap pixmap = new Pixmap (Gdx.files.internal(filename));
		
		// scan pixels from top-left to bottom-right
		int lastPixel = -1;
		
		for (int pixelY = 0; pixelY < pixmap.getHeight(); pixelY++)
		{
			for (int pixelX = 0; pixelX < pixmap.getWidth(); pixelX++)
			{
				AbstractGameObject obj = null;
				float offsetHeight = 0;
				// height grows from bottom to top
				float baseHeight = pixmap.getHeight() - pixelY;
				// get color of current pixel as 32-bit RGBA value
				int currentPixel = pixmap.getPixel(pixelX, pixelY);
				
				// find matching color value to identify block type at (x,y)
				// point and create the corresponding game object if there is
				// a match empty space
				if (BLOCK_TYPE.EMPTY.sameColor(currentPixel)) 
				{
					// do nothing
				}
				// rock
				else if (BLOCK_TYPE.GROUND.sameColor(currentPixel)) 
				{
					if (lastPixel != currentPixel) 
					{
						obj = new Ground();
						float heightIncreaseFactor = 0.25f;
						offsetHeight = -2.5f;
						obj.position.set(pixelX, baseHeight * obj.dimension.y * heightIncreaseFactor + offsetHeight);
						ground.add((Ground)obj);
					} 
					else
					{
						ground.get(ground.size - 1).increaseLength(1);
					}
				}
				// player spawn point
				else if (BLOCK_TYPE.PLAYER_SPAWNPOINT.sameColor(currentPixel)) 
				{
					obj = new Dude();
					offsetHeight = -8.0f;
					obj.position.set(pixelX, baseHeight * obj.dimension.y + offsetHeight);
					dude = (Dude) obj;
				}
				// item star
				else if (BLOCK_TYPE.ITEM_STAR.sameColor(currentPixel)) 
				{
					obj = new Star();
					offsetHeight = -1.5f;
					obj.position.set(pixelX, baseHeight * obj.dimension.y + offsetHeight);
					stars.add((Star) obj);
				}
				// item block
				else if (BLOCK_TYPE.ITEM_COIN.sameColor(currentPixel))
				{
					obj = new Coins(true, baseHeight);
			        offsetHeight = -1.5f;
			        obj.position.set(pixelX,baseHeight * obj.dimension.y + offsetHeight);
			        coins.add((Coins) obj);
				}
				// goal
				else if (BLOCK_TYPE.GOAL.sameColor(currentPixel))
				{
					obj = new Trees();
					offsetHeight = -7.0f;
					obj.position.set(pixelX - 5.5f, baseHeight + offsetHeight);
					goal = (Trees) obj;
					
				}
				// unknown object/pixel color
				else {
					int r = 0xff & (currentPixel >>> 24); //red color channel
					int g = 0xff & (currentPixel >>> 16); //green color channel
					int b = 0xff & (currentPixel >>> 8); //blue color channel
					int a = 0xff & currentPixel; //alpha channel
					Gdx.app.error(TAG, "Unknown object at x<" + pixelX + "> y<" + pixelY + ">: r<" + r+ "> g<" + g + "> b<" + b + "> a<" + a + ">");
				}
				lastPixel = currentPixel;
			}
		}
		
		// decorations
		ufos = new Ufos(pixmap.getWidth());
		ufos.position.set(0, 4);
		
		sun = new Sun(pixmap.getWidth());
		sun.position.set(0, -3);
		
		background = new Background(pixmap.getWidth());
		background.position.set(-1, -1);
		
		// free memory
		pixmap.dispose();
		Gdx.app.debug(TAG, "level '" + filename + "' loaded");
	}
	
	/**
	 * Created by Philip Deppen (Milestone 2, 11/6/18, issue 26)
	 * Edited by Philip Deppen (Milestone 3, 11/26/18, issue 49)
	 * @param batch
	 */
	public void render (SpriteBatch batch) 
	{
		
		// draw sun
		//sun.render(batch);
		
		// draw background
		background.render(batch);
		
		goal.render(batch);
		
		// draw coins
		for (Coins coin : coins)
			coin.render(batch);
		
		// draw stars
		for (Star star : stars)
			star.render(batch);
		
		// draw ground
		for (Ground grnd : ground)
			grnd.render(batch);
		
		// draw character
		dude.render(batch);
		
		// draw ufos
		ufos.render(batch);
		
	}
	
	/**
	 * Created by Philip Deppen (Milestone 3, 11/13/18, issue 43)
	 * Edited by Philip Deppen (Milestone 3, 11/26/18, issue 49)
	 */
	public void update (float deltaTime)
	{
		dude.update(deltaTime);
				
		for (Ground grnd : ground)
			grnd.update(deltaTime);
		
		for (Coins coin : coins)
			coin.update(deltaTime);
		
		for (Star star : stars)
			star.update(deltaTime);
		
		ufos.update(deltaTime);

	}
}
