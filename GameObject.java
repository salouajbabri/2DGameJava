import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameObject {
	protected int x, y, width, height;
	protected LoadedTextures LT;
	protected float velX, velY;
	protected Rectangle b = new Rectangle();
	protected BufferedImage img;
	protected double boundpos = 0.03; // goes 3% into the sprite of the image
	protected Methods ID;
	public Color B = new Color(0, 0, 0);
	// mainly used for box
	protected Rectangle top = new Rectangle(), bottom = new Rectangle(), right = new Rectangle(), left = new Rectangle();
	
	// for the basic 4 percent inside boundary
	public GameObject(int x, int y, BufferedImage img, Methods ID, LoadedTextures LT) {
		this.LT = LT;
		setStart(x, y, img.getWidth(), img.getHeight(), ID);
		b.setBounds((int)(x + (width * boundpos)), (int)(y + (height * boundpos)),
			        (int)(width * (boundpos * 2)),(int)(height * (boundpos * 2)));
	}
	
	// for custom boundaries
	public GameObject(int x, int y, BufferedImage img, double boundpos, Methods ID, LoadedTextures LT) {
		this.LT = LT;
		setStart(x, y, img.getWidth(), img.getHeight(), ID);
		this.boundpos = boundpos;
		b.setBounds((int)(x + (width * boundpos)), (int)(y + (height * boundpos)),
			        (int)(width * (boundpos * 2)),(int)(height * (boundpos * 2)));
	}
	
	// used for development when wanting to draw box and not image
	public GameObject(int x, int y, int width, int height, Methods ID, LoadedTextures LT) {
		this.LT = LT;
		setStart(x, y, width, height, ID);
		b.setBounds((int)(x * boundpos), (int)(y * boundpos), (int)(width * (boundpos * 2)),(int)(height * (boundpos * 2)));
	}
	
	// used for less repetition
	private void setStart(int x, int y, int width, int height, Methods ID) {
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
