import java.awt.image.BufferedImage;

public class LoadedTextures {
	private BufferedImageLoader BIL = new BufferedImageLoader();
	private String blockPath = "data/blocks/";
	private String playerPath = "data/player/";
	private String bgBlockPath = "data/bgBlocks/";
	private String killBlockPath = "data/killBlocks/";
	private BufferedImage castle = BIL.loadImage(blockPath + "castle.png"),
			dirt = BIL.loadImage(blockPath + "dirt.png"),
			dirtGrass = BIL.loadImage(blockPath + "dirtGrass.png"),
			empty = BIL.loadImage(blockPath + "empty.png"),
			lavaTop = BIL.loadImage(killBlockPath + "lavaTop.png"),
			playerRight = BIL.loadImage(playerPath + "playerRight.png"),
			playerLeft = BIL.loadImage(playerPath + "playerLeft.png"),
			outsideBG = BIL.loadImage(bgBlockPath + "outside.png"),
			castleBG = BIL.loadImage(bgBlockPath + "castle.png"),
			pauseImg = BIL.loadImage("data/pause.png"),
			win = BIL.loadImage("data/blocks/flag.png"),
			lava = BIL.loadImage(killBlockPath + "lava.png");
	
	
	public BufferedImage getDirtGrass() {
		return dirtGrass;
	}
	public BufferedImage getDirt() {
		return dirt;
	}
	public BufferedImage getCastle() {
		return castle;
	}
	public BufferedImage getEmpty() {
		return empty;
	}
	public BufferedImage getLavaTop() {
		return lavaTop;
	}
	public BufferedImage getPlayerRight() {
		return playerRight;
	}
	public BufferedImage getPlayerLeft() {
		return playerLeft;
	}
	public BufferedImage getOutsideBG() {
		return outsideBG;
	}
	public BufferedImage getCastleBG() {
		return castleBG;
	}
	public BufferedImage getPauseImg() {
		return pauseImg;
	}
	public BufferedImage getWin() {
		return win;
	}
	public BufferedImage getLava() {
		return lava;
	}
}