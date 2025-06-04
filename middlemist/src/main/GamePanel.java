package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import entity.SpriteHandler;

public class GamePanel extends JPanel implements Runnable {

	//Variables

	private static final long serialVersionUID = 1L;
	private final int ogTileSize = 64;
	public final int scale = 1;
	public final int tileSize = ogTileSize * scale;

	public final int maxCols = 16;
	public final int maxRows = 10;
	public final int screenW = maxCols * tileSize;
	public final int screenH = maxRows * tileSize;

	public final int fps = 60;

	public Thread gameThread;
	public KeyHandler keyH;
	public SpriteHandler spriteH;
	public ArrayList<Entity> entities;
	public Player player;

	private volatile boolean running = true;
    public volatile boolean paused = false;

	//Constructors

	public GamePanel() {
		this.setPreferredSize(new Dimension(screenW, screenH));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.setFocusable(true);
		this.requestFocusInWindow();
		spriteH = new SpriteHandler(this);
		keyH = new KeyHandler();
		this.addKeyListener(keyH);
		entities = new ArrayList<>();
		player = new Player("Player", 0, 0, this);
		player.innitSheet("/res/sprites/player.png");
		player.loadEntity();
	}

	//Methods
	
	public void createVoidEntity(int x, int y) {
		Entity voidEnt = new Entity("Void", x, y);
		voidEnt.innitSheet("/res/sprites/player.png");
		voidEnt.loadEntity();
	}

	public void pause() {
		paused = true;
		System.out.println("Game paused. Press Escape to resume.");
	}
	public void resume() {
		System.out.println("Game resumed. Press Escape to pause.");
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
		if (entities.equals(Entity.loadedEnts)) {
			return;
		} else {
			entities.clear();
			entities.addAll(Entity.loadedEnts);
		}
	}

	private void update() {
		appendEntities();
		player.playUpdate();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		for (Entity ent : entities) {
			g2.drawImage(spriteH.getSprite(ent), ent.x, ent.y, tileSize, tileSize, null);
		}
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
