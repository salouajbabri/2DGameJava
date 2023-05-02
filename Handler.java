import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.LinkedList;

public class Handler {

	protected LinkedList<Scene> objects = new LinkedList<Scene>();
	protected boolean up = false, down = false, right = false, left = false;
	private Camera camera;
	private Player player;
	protected int map = 1;
	protected Game game;
	protected LoadedTextures LT;
	protected BufferedImageLoader BIL = new BufferedImageLoader();
	
	public void update() {
		for(int i = 0; i < objects.size(); i++) {
			boolean inX = camera.x - 10 <= player.x && camera.x + 10 + camera.GWIDTH >= player.x;
			boolean inY = camera.y - 10 <= player.y && camera.y + 10 + camera.GHEIGHT >= player.y;
			if(inX && inY) objects.get(i).update();
		}
	}
	
	public void draw(Graphics2D g) {
		for(int i = 0; i < objects.size(); i++) {
			boolean inX = camera.x - 10 <= player.x && camera.x + 10 + camera.GWIDTH >= player.x;
			boolean inY = camera.y - 10 <= player.y && camera.y + 10 + camera.GHEIGHT >= player.y;
			if(inX && inY) objects.get(i).draw(g);
		}
	}
	
	public void setCamera(Camera camera) {
		this.camera = camera;
	}
	
	public void setPlayer(Scene gameObject) {
		this.player = (Player)gameObject;
	}
	
	public void addObject(Scene tempObject) {
		objects.add(tempObject);
	}
	
	public void removeObject(Scene tempObject) {
		objects.remove(tempObject);
	}
}
