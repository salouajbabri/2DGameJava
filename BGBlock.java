import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class BGBlock extends GameObject {
	protected BGType BGT;
	private LoadedTextures LT;
	
	public BGBlock(int x, int y, BufferedImage img, ID ID, BGType BGT, LoadedTextures LT) {
		super(x, y, img, ID, LT);
		this.BGT = BGT;
		this.LT = LT;
	}
	
	public BGBlock(int x, int y, int width, int height, ID ID, BGType BGT, LoadedTextures LT) {
		super(x, y, width, height, ID, LT);
		this.BGT = BGT;
		this.LT = LT;
	}
	
	public void draw(Graphics2D g) {
		// draws image corresponding to block ID
		if(BGT == BGType.castle)
			g.drawImage(LT.getCastleBG(), x, y, null);
		else if(BGT == BGType.outside)
			g.drawImage(LT.getOutsideBG(), x, y, null);
		// draws base model
		/*g.setColor(Color.BLACK);
		g.fillRect(x, y, width, height);
		
		// draws hit box
		g.drawRect((int) b.getX(), (int) b.getY(), (int) b.getWidth(), (int) b.getHeight());
		g.drawRect((int) bottom.getX(), (int) bottom.getY(), (int) bottom.getWidth(), (int) bottom.getHeight());
		g.drawRect((int) right.getX(), (int) right.getY(), (int) right.getWidth(), (int) right.getHeight());
		g.drawRect((int) left.getX(), (int) left.getY(), (int) left.getWidth(), (int) left.getHeight());
		g.drawRect((int) top.getX(), (int) top.getY(), (int) top.getWidth(), (int) top.getHeight());*/
	}

	public void update() {}
}
