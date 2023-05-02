import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Player extends Scene {

	protected Handler handler;
	protected int initSpawnX, initSpawnY;
	private boolean jumping = true, firstJump = true;
	private double inAirMoveSpeed = 0.85, onGroundMoveSpeed = 2.5;
	private boolean right = true, left = false;
	protected int maxGroundSpeed = 10, jumpSpeed = 16, maxAirXSpeed = 10, maxFallSpeed = 40; // first y used in jumping
	protected double jumpXResistance = 0.9; // like friction in the air, same value rules apply

	public Player(int x, int y, BufferedImage img, IDE ID, Handler handler, LoadedTextures LT) {
		super(x, y, img, ID, LT);
		start(handler);
	}
	public Player(int x, int y, BufferedImage img, double boundpos, IDE ID, Handler handler, LoadedTextures LT) {
		super(x, y, img, boundpos, ID, LT);
		start(handler);
	}
	public Player(int x, int y, int width, int height, IDE ID, Handler handler, LoadedTextures LT) {
		super(x, y, width, height, ID, LT);
		start(handler);
	}	
	public void start(Handler handler) {
		b.setBounds((int)(x + (width * boundpos)), (int)(y + (width * boundpos)),
				    (int)(width - (width * boundpos) * 2), (int)(width - (width * boundpos) * 2));
		initSpawnX = x;
		initSpawnY = y;
		this.handler = handler;
	}

	public void update() {
		if((velX > 0 && velX < 0.005) || (velX < 0 && velX > -0.7))
			velX = 0;
		
		x += velX;
		y += velY;
	// M O V E M E N T
		
		if(firstJump) firstJump = false;
		
		// determines direction of person
		if(handler.right && !handler.left) {
			right = true;
			left = false;
		} else if(!handler.right && handler.left) {
			right = false;
			left = true;
		}
		
		// UP/JUMPING
		if (handler.up) {
			if(!jumping) {
				velY = -jumpSpeed;
				jumping = true;
				firstJump = true;
				if(handler.right || handler.left) {
					velX = (int)(velX * jumpXResistance);
				}
			}
		} else if (!handler.down) {
			if(jumping && velY + Game.GRAVITY <= maxFallSpeed)
				velY += Game.GRAVITY;
		}
	
		// RIGHT
		if (handler.right && !jumping) { 
			if(velX + onGroundMoveSpeed <= maxGroundSpeed)
				velX += onGroundMoveSpeed;
			
		} else if(handler.right && jumping) {
			if(firstJump && velX + jumpSpeed/3 < maxAirXSpeed)
				velX += jumpSpeed/3;
			else if(velX + inAirMoveSpeed < maxAirXSpeed)
				velX += inAirMoveSpeed;
		} 
		
		// LEFT
		if (handler.left && !jumping) {
			if(velX - onGroundMoveSpeed >= -maxGroundSpeed)
				velX -= onGroundMoveSpeed;
			
		} else if(handler.left && jumping) {
			if(firstJump && velX - jumpSpeed/3 > -maxAirXSpeed) 
				velX -= jumpSpeed/3;
			else if(velX - inAirMoveSpeed > -maxAirXSpeed)
				velX -= inAirMoveSpeed;
		}
		
		// CONSTANT GRAVITATIONAL FORCE DOWN
		if(y - Game.SCALE >= 0 && velY + Game.GRAVITY <= maxFallSpeed) {
			velY += Game.GRAVITY;
		}
		
		// check collisions
		collision();

		// adds friction or air resistance after determining whether hit ground or not
		if(jumping && ((velX * jumpXResistance > 1 && velX > 0) || (velX * jumpXResistance < -1 && velX < 0)))
			velX *= jumpXResistance;
		
		// re-adjust boundaries accordingly
		b.setBounds((int)(x + (width * boundpos)), (int)(y + (width * boundpos)),
				    (int)(width - (width * boundpos) * 2), (int)(width - (width * boundpos) * 2));
	}
	
	private void collision() {
		for(int i = 0; i < handler.objects.size(); i++) {
			Scene tempObject = handler.objects.get(i);
			if(tempObject.ID == ID.block) {
				if(getBounds().intersects(tempObject.b)) {
					// checking to see if player hit top of block
					
					if(getBounds().intersects(tempObject.top)) {
						y = (int) (Math.abs(tempObject.top.getY() - height));
						jumping = false;
						velY = 0;
					} 
					if(getBounds().intersects(tempObject.bottom)) { // checking to see if player hit bottom of block
						y = (int) (tempObject.bottom.getY() + tempObject.bottom.getHeight() + 1);
						if(velY > 0) velY -= velY/1.5;
						if(velY < 0) velY += Math.abs(velY/1.5);
						velY *= -1;
					} 
					if(getBounds().intersects(tempObject.right)) { // checking to see if player hit right of block
						x = (int) (tempObject.right.getX() + tempObject.right.getWidth() + 1);
						velX = 0;
					} 
					if(getBounds().intersects(tempObject.left)) { // checking to see if player hit left of block
						x = (int) (Math.abs(tempObject.left.getX() - width - 1));
						velX = 0;
					}
					
					if(!jumping && (!handler.right && !handler.left)) {
						velX *= Game.FRICTION;
					}
				}
			} else if(tempObject.ID == ID.lava) {
				if(getBounds().intersects(tempObject.b)) {
					Game.dying++;
				}
			} else if(tempObject.ID == ID.win) {
				if(getBounds().intersects(tempObject.getBounds())) {
					Block winObj = (Block)tempObject;
					winObj.loadNext();
				}
			}
		}
	}
	
	public void draw(Graphics2D g) {
		// draws player image
		if(right) g.drawImage(LT.getPlayerRight(), x, y, null);
		else if(left) g.drawImage(LT.getPlayerLeft(), x, y, null);
		
	}

	public void kill() {
		x = initSpawnX;
		y = initSpawnY;
		velX = 0;
		velY = 0;
	}
	
	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
