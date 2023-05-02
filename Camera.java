import java.awt.image.BufferedImage;

public class Camera {
	
	protected float x, y;
	protected int GWIDTH, GHEIGHT;
	protected BufferedImage image;
	
	public Camera(float x, float y, int GWIDTH, int GHEIGHT, BufferedImage image) {
		this.x = x;
		this.y = y;
		this.GWIDTH = GWIDTH;
		this.GHEIGHT = GHEIGHT;
		this.image = image;
	}
	
	public void update(Scene object) {
		// this makes it so the camera is not totally locked onto the character
			 // the * 0.05f is for how much the camera follows the player, higher number meaning stronger follow
		x += ((object.x - x + 150) - GWIDTH/2) * 0.05f;
		if(Math.abs(object.y - GHEIGHT) >= GHEIGHT - 1100 && Math.abs(object.y - GHEIGHT) <= GHEIGHT - 1050)
			y += ((object.y - y) - GHEIGHT/4) * 0.2f;
		else if(Math.abs(object.y - GHEIGHT) >= GHEIGHT - 1100)
			y += ((object.y - y) - GHEIGHT/4) * 0.2f;
		
		if(x <= 0) {
			x = 0;
		}
		
		//2688
		if(x >= image.getWidth() * Game.SCALE - GWIDTH) 
			x = image.getWidth() * Game.SCALE - GWIDTH;
		if(y <= 0) y = 0;
		if(y >= image.getHeight() * Game.SCALE - GHEIGHT) 
			y = image.getHeight() * Game.SCALE - GHEIGHT;
	}
}