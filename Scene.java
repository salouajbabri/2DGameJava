import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Scene {
	protected int x, y, width, height; // x & y the position of the gameobject on the screen
	protected LoadedTextures LT;
	protected float velX, velY; // velocity of the game object on x and y
	protected Rectangle b = new Rectangle(); // represents the bounding box
	protected BufferedImage img;
	protected double boundpos = 0.03; // goes 3% into the sprite of the image
	protected IDE ID;
	public Color B = new Color(0, 0, 0);
	// mainly used for box
	protected Rectangle top = new Rectangle(), bottom = new Rectangle(), right = new Rectangle(), left = new Rectangle();
	
	// pour les 4% dans le box
	public Scene(int x, int y, BufferedImage img, IDE ID, LoadedTextures LT) {
		this.LT = LT;
		setStart(x, y, img.getWidth(), img.getHeight(), ID);
		b.setBounds((int)(x + (width * boundpos)), (int)(y + (height * boundpos)),
			        (int)(width * (boundpos * 2)),(int)(height * (boundpos * 2)));
	}
	
	//les limites
	public Scene(int x, int y, BufferedImage img, double boundpos, IDE ID, LoadedTextures LT) {//to specify how far into the sprite image the collision bounds should extend.
		this.LT = LT;// that's why we add the frequence boundpos
		setStart(x, y, img.getWidth(), img.getHeight(), ID);
		this.boundpos = boundpos;
		b.setBounds((int)(x + (width * boundpos)), (int)(y + (height * boundpos)),
			        (int)(width * (boundpos * 2)),(int)(height * (boundpos * 2)));
	}
	
	// used for development when wanting to draw box and not image
	public Scene(int x, int y, int width, int height, IDE ID, LoadedTextures LT) {
		this.LT = LT;
		setStart(x, y, width, height, ID);
		b.setBounds((int)(x * boundpos), (int)(y * boundpos), (int)(width * (boundpos * 2)),(int)(height * (boundpos * 2)));
	}
	
	// used for less repetition
	private void setStart(int x, int y, int width, int height, IDE ID) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.ID = ID;
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
	
	public abstract void update();
	
	public abstract void draw(Graphics2D g);
}
