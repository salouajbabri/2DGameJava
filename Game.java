import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends Canvas implements Runnable, KeyListener {
	// basic setup
	private static final long serialVersionUID = 1L;
	protected final static int GWIDTH = 900,
							   GHEIGHT =  800,
							   SCALE = 72;
	protected final static double GRAVITY = 0.62, // higher = more gravity, lower = less gravity ; was .62
								  FRICTION = 0.5; // higher = less friction, lower = more friction
	protected static Thread thread;
	protected boolean running, updateRun, drawRun, loadDrawRun = false;
	private Camera camera;
	private LoadedTextures LT = new LoadedTextures();
	// others
	protected Handler handler = new Handler();
	protected Player player = null;
	private BufferedImage level = null;
	protected static int dying = 0;

	public static void main(String[] args) {
		new Game();
	}
	
	public Game() {
		new GameFrame(this, GWIDTH, GHEIGHT);
		
		handler.game = this;
		handler.LT = LT;
		
		player = new Player(0, 0, LT.getPlayerRight(), IDE.player, handler, LT);
		
		// loads the map
		BufferedImageLoader loader = new BufferedImageLoader();
		level = loader.loadImage("data/maps/map" + handler.map + ".png");
		loadLevel(level);
		
		camera = new Camera(0, 0, GWIDTH, GHEIGHT, level);
		handler.setCamera(camera);
		
		start();

		this.addKeyListener(this);
		this.addMouseListener(new MouseInput());
	}	
	
	// starting method/constructor called
	public void start() {
		running = true;
		updateRun = true;
		drawRun = true;
		thread = new Thread(this);
		thread.start();
	}

	// calls the update method of every game object
	public void update() {
		if(updateRun) {
			for(int i = 0; i < handler.objects.size(); i++) {
				if(handler.objects.get(handler.objects.size() - 1).ID == IDE.player) {
					camera.update(handler.objects.get(handler.objects.size()-1));
				}
			}
			handler.update();
		}
		
		// this makes it so that you have a second before dying, fall into lava or spikes smth
		if(dying >= 15) {
			player.kill(); // 60fps so 60 means 1 second
			AudioGame.playSound("/Audio/gameOver.wav");
			dying = 0;
			
		}
	}
	
	// paints every object 
	public void paint() {
		if(drawRun) {
			BufferStrategy bs = this.getBufferStrategy();
			if (bs == null) {
				this.createBufferStrategy(3);
				return;
			}
			Graphics2D g = (Graphics2D) bs.getDrawGraphics();
			////////////////////////////////////////////////
			
			if(loadDrawRun)
				paintLoad(g);
			else {
				g.setColor(new Color(125, 255, 255));
				g.fillRect(0, 0, GWIDTH, GHEIGHT);
				
				g.translate(-camera.x, -camera.y);
				
				handler.draw(g);
		
				g.translate(camera.x, camera.y);
				
				if(!updateRun)
					g.drawImage(LT.getPauseImg(), GWIDTH/2 - LT.getPauseImg().getWidth()/2, GHEIGHT/2 - LT.getPauseImg().getHeight()/2, null);
			}
			
			////////////////////////////////////////////////
			g.dispose();
			bs.show();
		}
	}
	
	// loading the level
	public void loadLevel(BufferedImage image) {
		int w = image.getWidth();
		int h = image.getHeight();
		int width = (int) (1.63 * SCALE); // makes 94.54 down to 94 b/c it goes to int
		
		// add bg first
		for(int x = 0; x < w; x++) {
			for(int y = 0; y < h; y++) {
				int pixel = image.getRGB(x, y);
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;

				if((red == 125 && green == 255 && blue == 255) || (red == 0 && green == 0 && blue == 255))
					handler.addObject(new BackGroundBlock(x * SCALE, y * SCALE, LT.getOutsideBG(), IDE.bgBlock, BackGroundType.outside, LT));
				if((red == 0 && green == 0 && blue == 0) || (red == 255 && green == 255 && blue == 0))
					handler.addObject(new BackGroundBlock(x * SCALE, y * SCALE, LT.getCastleBG(), IDE.bgBlock, BackGroundType.castle, LT));
				else if(red == 255 && green == 0 && blue == 255)
					handler.addObject(new BackGroundBlock(x * SCALE, y * SCALE, LT.getOutsideBG(), IDE.bgBlock, BackGroundType.outside, LT));
			}
		}
		int px = 0;
		int py = 0;
		for(int x = 0; x < w; x++) {
			for(int y = 0; y < h; y++) {
				int pixel = image.getRGB(x, y);
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;

				if(red == 255 && green == 0 && blue == 0) // castle block
					handler.addObject(new Block(x * SCALE, y * SCALE, LT.getCastle(), IDE.block, BlockType.castle, LT, handler));
				else if(red == 125 && green == 50 && blue == 0) // normal dirt block
					handler.addObject(new Block(x * SCALE, y * SCALE, LT.getDirt(), IDE.block, BlockType.dirt, LT, handler));
				else if(red == 0 && green == 200 && blue == 0) // dirt grass block
					handler.addObject(new Block(x * SCALE, y * SCALE, LT.getDirtGrass(), IDE.block, BlockType.dirtGrass, LT, handler));
				else if(red == 200 && green == 200 && blue == 255) // transparent block
					handler.addObject(new Block(x * SCALE, y * SCALE, LT.getEmpty(), IDE.block, BlockType.outsidebg, LT, handler));
				else if(red == 0 && green == 0 && blue == 255) { // player
					px = x;
					py = y;
				}
				else if(red == 255 && green == 200 && blue == 0) // lava top block
					handler.addObject(new Block(x * SCALE, y * SCALE, LT.getLavaTop(), IDE.lava, BlockType.lavaTop, LT, handler));
				else if(red == 255 && green == 150 && blue == 0) // lava block
					handler.addObject(new Block(x * SCALE, y * SCALE, LT.getLava(), IDE.lava, BlockType.lava, LT, handler));
				else if((red == 255 && green == 255 && blue == 0) || (red == 255 && green == 0 && blue == 255)) // win block
					handler.addObject(new Block(x * SCALE, y * SCALE, SCALE, SCALE, IDE.win, BlockType.win, LT, handler));
			}
		}
		
		player.initSpawnX = px * SCALE;
		player.initSpawnY = py * SCALE;
		player.setLocation(px * SCALE, py * SCALE);
		handler.addObject(player);
		handler.setPlayer(handler.objects.get(handler.objects.size() - 1));
		
		loadDrawRun = false;
	}
	
	public void adjustCamera(BufferedImage img) {
		camera.image = img;
	}
	
	// paints loading screen
	public void paintLoad(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, GWIDTH, GHEIGHT);
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.PLAIN, 100));
		g.drawString("Loading...", (int) (GWIDTH/2 - g.getFontMetrics().getStringBounds("Loading...", g).getWidth()/2),
				(int) (GHEIGHT/2 - g.getFontMetrics().getStringBounds("Loading...", g).getHeight()/2));
	}
		
	private class MouseInput extends MouseAdapter {
		
		public void mousePressed(MouseEvent e) {
			
		}
	}
	
	public void keyPressed(KeyEvent evt) {
		int key = evt.getKeyCode();
		for(int i = 0; i < handler.objects.size(); i++) {
			Scene tempObject = handler.objects.get(i);
			if(tempObject.ID == IDE.player) {
				if(key == KeyEvent.VK_UP) handler.up = true;
				else if(key == KeyEvent.VK_LEFT) handler.left = true;
				else if(key == KeyEvent.VK_DOWN) handler.down = true;
				else if(key == KeyEvent.VK_RIGHT) handler.right = true;
			}
		}
		if (key == KeyEvent.VK_C) {
			stop();
		} else if(key == KeyEvent.VK_P) {
			if(updateRun) updateRun = false;
			else if(!updateRun) updateRun = true;
		}
	}

	public void keyReleased(KeyEvent evt) {
		int key = evt.getKeyCode();
		
		for(int i = 0; i < handler.objects.size(); i++) {
			Scene tempObject = handler.objects.get(i);
			if(tempObject.ID == IDE.player) {
				if(key == KeyEvent.VK_UP) handler.up = false;
				else if(key == KeyEvent.VK_LEFT) handler.left = false;
				else if(key == KeyEvent.VK_DOWN) handler.down = false;
				else if(key == KeyEvent.VK_RIGHT) handler.right = false;
			}
		}
	}

	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				update();
				delta--;
			}
			paint();
			frames++;
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frames = 0;
			}
		}
		stop();
	}

	public void stop() {
		running = false;
		try {
			thread.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.exit(0);
	}
	
	public void keyTyped(KeyEvent arg0) {}
}