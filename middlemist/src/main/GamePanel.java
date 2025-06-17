package main;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import entity.Entity;
import entity.EntityLoader;
import entity.ItemEntity;
import entity.Npc;
import entity.Player;
import entity.SpriteHandler;
import interaction.DialogPanel;
import item.Item;
import world.World;

public class GamePanel extends JPanel implements Runnable {

	//Variables
	public static Player player;
	public static World world;
	public static GamePanel gamePanel;

	private static final long serialVersionUID = 1L;
	private final int ogTileSize = 64;
	public final int scale = 1;
	public final int tileSize = ogTileSize * scale;

	public final int maxCols = 12;
	public final int maxRows = 8;
	public final int screenW = maxCols * tileSize;
	public final int screenH = maxRows * tileSize;

	public final int fps = 60;

	public Thread gameThread;
	public KeyHandler keyH;
	public SpriteHandler spriteH;
	public ArrayList<Entity> entities;
	public ArrayList<Entity> dontDraw;
	public static EntityLoader entLoader;

	private volatile boolean running = true;
    public volatile boolean paused = false;

	//Constructors

	public GamePanel() {
		gamePanel = this;
		this.setPreferredSize(new Dimension(screenW, screenH));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		keyH = new KeyHandler();
		this.addKeyListener(keyH);
		this.setFocusable(true);
		this.requestFocusInWindow();
		spriteH = new SpriteHandler(this);
		entities = new ArrayList<>();
		dontDraw = new ArrayList<>();
		entLoader = new EntityLoader();
		world = new World(this);
		innitWorld(1);
		player = new Player("Player", (screenW / 2) - (tileSize / 2), (screenH / 2) - (tileSize / 2), this);
		player.innitSheet("player.png");
		entLoader.loadEntity(player);
		ItemEntity test = new ItemEntity("test", 100, 100, new Item("test"));
		test.itemPickup.setIcon("/res/sprites/player.png");
		entLoader.loadEntity(test);
		ItemEntity test2 = new ItemEntity("test2", 200, 100, new Item("test2"));
		test2.itemPickup.setIcon("/res/sprites/player.png");
		entLoader.loadEntity(test2);
		Npc testNpc = new Npc("TestNpc", 300, 100, true);
		testNpc.newDialog("Hello, I am a test NPC! Press space to continue, or click the dialog panel!NEXT_DIALOGLmao", 100);
		entLoader.loadEntity(testNpc);
	}

	//Methods
	
	private void innitWorld(int i) {
		try {
			world.mapImage = ImageIO.read(getClass().getResourceAsStream("/res/maps/map" + i + ".png"));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		world.worldW = world.mapImage.getWidth();
		world.worldH = world.mapImage.getHeight();
		world.x = 0 - (world.worldW - screenW)/2;
		world.y = 0 - (world.worldH - screenH)/2;
	}
	
	public void createVoidEntity(int x, int y) {
		Entity voidEnt = new Entity("Void", x, y);
		entLoader.loadEntity(voidEnt);
	}
	
	public void setInvisibleEntity(Entity ent) {
		dontDraw.add(ent);
	}
	
	public void togglePause() {
	    paused = !paused;
	    if (paused) {
	        pause();
	    } else {
	        resume();
	    }
	}

	public void pause() {
		paused = true;
	}
	public void resume() {
		synchronized (gameThread) {
			paused = false;
			gameThread.notifyAll();
		}
	}
	public void stop() {
		running = false;
		resume();
	}

	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	private void appendEntities() {
		if (entities.equals(entLoader.loadedEnts)) {
			return;
		} else {
			entities.clear();
			entities.addAll(entLoader.loadedEnts);
		}
		if (entities.contains(player)) {
			entities.remove(player);
		}
	}

	private void update() {
		player.pauseUpdate();
		appendEntities();
		world.verifyPos();
		for (Entity ent : entities) {
			ent.entCollide.collisionUpdate();
			if (ent instanceof ItemEntity) {
				((ItemEntity) ent).itemUpdate();
			}
		}
		player.playUpdate();
		world.worldUpdate();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(world.mapImage, world.x, world.y, world.worldW, world.worldH, null);
		for (Entity ent : entities) {
			if (!(dontDraw.contains(ent))) {
				g2.drawImage(spriteH.getSprite(ent), ent.x, ent.y, ent.width, ent.height, null);
			}
		}
		g2.drawImage(spriteH.getSprite(player), player.x, player.y, player.width, player.height, null);
	}

	@Override
	public void run() {
		DialogPanel.toggleVisibility();
		DialogPanel.loadDialog("Welcome to middlemist! This is a test dialog. Press space to continue, or click the dialog panel!NEXT_DIALOGI hope you enjoy!");
	    double drawInterval = 1000000000.0 / fps;
	    double delta = 0;
	    long lastTime = System.nanoTime();
	    long curTime;

	    while (running) {
	        synchronized (gameThread) {
	            if (paused) {
	            	player.pauseUpdate();
	                try {
	                	Thread.sleep(10);
	                } catch (InterruptedException ex) {
	                    break;
	                }
	                if (!running) {
	                    break;
	                }
	                lastTime = System.nanoTime(); // Reset timing after pause
	            }
	        }

	        curTime = System.nanoTime();
	        delta += (curTime - lastTime) / drawInterval;
	        lastTime = curTime;

	        if (delta >= 1) {
	            update();
	            repaint();
	            delta--;
	        }
	    }
	}
	
	public ArrayList<Entity> sortEnts(Class <? extends Entity> type) {
		ArrayList<Entity> sortedEnts = new ArrayList<>();
		for (Entity ent : entities) {
			if (type.isInstance(ent)) {
				sortedEnts.add(ent);
			}
		}
		return sortedEnts;
	}
	
	public void hideCursor() {
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
            cursorImg, new Point(0, 0), "blank cursor");
        setCursor(blankCursor);
    }

    public void showCursor() {
    	Cursor defaultCursor = Cursor.getDefaultCursor();
		if (defaultCursor == null) {
			defaultCursor = Toolkit.getDefaultToolkit().createCustomCursor(
				new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), "default cursor");
		}
    	setCursor(defaultCursor);
    }

}
