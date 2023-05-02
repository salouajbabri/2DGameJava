import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class BackGroundBlock extends Scene {
	protected BackGroundType BGT;
	private LoadedTextures LT;
	
	public BackGroundBlock(int x, int y, BufferedImage img, IDE ID, BackGroundType BGT, LoadedTextures LT) {
		super(x, y, img, ID, LT);
		this.BGT = BGT;
		this.LT = LT;
	}
	
	public BackGroundBlock(int x, int y, int width, int height, IDE ID, BackGroundType BGT, LoadedTextures LT) {
		super(x, y, width, height, ID, LT);
		this.BGT = BGT;
		this.LT = LT;
	}
	
	public void draw(Graphics2D g) {
		// draws image corresponding to block ID
		if(BGT == BackGroundType.castle)
			g.drawImage(LT.getCastleBG(), x, y, null);
		else if(BGT == BackGroundType.outside)
			g.drawImage(LT.getOutsideBG(), x, y, null);
		
	}

	public void update() {}
}
