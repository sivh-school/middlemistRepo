package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import entity.Entity;
import entity.EntityCollider;
import entity.EntityLoader;
import entity.Player;
import entity.SpriteHandler;
import world.World;

public class GamePanel extends JPanel implements Runnable {

	//Variables

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
	private Player player;
	private EntityLoader entLoader;
	public World world;

	private volatile boolean running = true;
    public volatile boolean paused = false;

	//Constructors

	public GamePanel() {
		this.setPreferredSize(new Dimension(screenW, screenH));
		this.setBackground(Color.white);
		this.setDoubleBuffered(true);
		this.setFocusable(true);
		this.requestFocusInWindow();
		spriteH = new SpriteHandler(this);
		keyH = new KeyHandler();
		this.addKeyListener(keyH);
		entities = new ArrayList<>();
		entLoader = new EntityLoader();
		world = new World(this);
		innitWorld(1);
		player = new Player("Player", (screenW / 2) - (tileSize / 2), (screenH / 2) - (tileSize / 2), this);
		player.innitSheet("/res/sprites/player.png");
		entLoader.loadEntity(player);
		createVoidEntity(0, 0);
	}

	//Methods
	
	private void innitWorld(int i) {
		world.x = 0;
		world.y = 0;
		world.worldW = tileSize * 20;
		world.worldH = tileSize * 20;
		try {
			world.mapImage = ImageIO.read(getClass().getResourceAsStream("/res/maps/map" + i + ".png"));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void createVoidEntity(int x, int y) {
		Entity voidEnt = new Entity("Void", x, y);
		voidEnt.innitSheet("/res/sprites/exc.png");
		entLoader.loadEntity(voidEnt);
	}

	public void pause() {
		paused = true;
		System.out.println("Game paused. Press Escape to resume.");
		main.PausePanel.toggleVisibility();
	}
	public void resume() {
		System.out.println("Game resumed. Press Escape to pause.");
		synchronized (gameThread) {
			paused = false;
			gameThread.notifyAll();
			main.PausePanel.toggleVisibility();
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
		appendEntities();
		world.verifyPos();
		player.playUpdate();
		world.worldUpdate();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(world.mapImage, world.x, world.y, world.worldW, world.worldH, null);
		for (Entity ent : entities) {
			g2.drawImage(spriteH.getSprite(ent), ent.x, ent.y, tileSize, tileSize, null);
		}
		g2.drawImage(spriteH.getSprite(player), player.x, player.y, tileSize, tileSize, null);
	}

	@Override
	public void run() {
		while (running) {
            synchronized (gameThread) {
                if (!running) {
                    break;
                }
                if (paused) {
                    try {
                        gameThread.wait();
                    } catch (InterruptedException ex) {
                        break;
                    }
                    if (!running) {
                        break;
                    }
                }
            }
            double drawInterval = 1000000000/fps;
            double delta = 0;
            long lastTime = System.nanoTime();
            long curTime;

            while (gameThread != null) {

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

	}

}
