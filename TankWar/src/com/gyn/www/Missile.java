package com.gyn.www;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * �ڵ���
 * @author ��Զ�� 
 *
 */
public class Missile {
	/**
	 * �ӵ���x����
	 */
	int x;
	/**
	 * �ӵ���y����
	 */
	int y;
	/**
	 * �ӵ��ĺ����ٶ�
	 */
	public static final int X_SPEED = 5;
	/**
	 * �ӵ��������ٶ�
	 */
	public static final int Y_SPEED = 5;
	public static final int WIDTH = 10;
	public static final int HEIGHT = 10;
	
	/**
	 * �ӵ��ķ���
	 */
	Direction direction;
	
	boolean live = true;
	
	/**
	 * ��ʾ�з�/�ҷ��ӵ�
	 */
	private boolean good;
	
	TankClient tankClient;
	
	/**
	 * ����ToolKit�����ȡӲ�̵�ͼƬ
	 */
	private static Toolkit toolkit = Toolkit.getDefaultToolkit();
	
	/**
	 * ͼƬ����
	 */
	private static Image[] missileImages = null;
	private static Map<String, Image> images = new HashMap<String, Image>();
	
	static {
		missileImages = new Image[] {
			toolkit.getImage(Missile.class.getClassLoader().getResource("images/MissileD.gif")),
			toolkit.getImage(Missile.class.getClassLoader().getResource("images/missileRU.gif")),
			toolkit.getImage(Missile.class.getClassLoader().getResource("images/missileR.gif")),
			toolkit.getImage(Missile.class.getClassLoader().getResource("images/missileRD.gif")),
			toolkit.getImage(Missile.class.getClassLoader().getResource("images/missileU.gif")),
			toolkit.getImage(Missile.class.getClassLoader().getResource("images/missileLD.gif")),
			toolkit.getImage(Missile.class.getClassLoader().getResource("images/missileL.gif")),
			toolkit.getImage(Missile.class.getClassLoader().getResource("images/missileLU.gif")),
		};
		
		images.put("U", missileImages[0]);
		images.put("RU", missileImages[1]);
		images.put("R", missileImages[2]);
		images.put("RD", missileImages[3]);
		images.put("D", missileImages[4]);
		images.put("LD", missileImages[5]);
		images.put("L", missileImages[6]);
		images.put("LU", missileImages[7]);
	}
	
	public Missile(int x, int y, Direction direction) {
		this.x = x;
		this.y = y;
		this.direction = direction;
	}
	
	public Missile(int x, int y, boolean good, Direction direction, TankClient tankClient) {
		this(x, y, direction);
		
		this.good = good;
		this.tankClient = tankClient;
	}
	
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}
	/**
	 * �����ӵ�
	 * @param ����
	 */
	public void draw(Graphics g) {
		if(!live) {
			tankClient.missiles.remove(this);
			return;
		}
		switch (direction) {
		case U:
			g.drawImage(images.get("U"), x, y, null);
			break;
		case RU:
			g.drawImage(images.get("RU"), x, y, null);
			break;
		case R:
			g.drawImage(images.get("R"), x, y, null);
			break;
		case RD:
			g.drawImage(images.get("RD"), x, y, null);
			break;
		case D:
			g.drawImage(images.get("D"), x, y, null);
			break;
		case LD:
			g.drawImage(images.get("LD"), x, y, null);
			break;
		case L:
			g.drawImage(images.get("L"), x, y, null);
			break;
		case LU:
			g.drawImage(images.get("LU"), x, y, null);
			break;
		default:
			break;
		}
		
		move();
	}
	
	/**
	 * �ӵ��ƶ�����
	 */
	public void move() {
		switch (direction) {
		case U:
			y -= Y_SPEED;
			break;
		case RU:
			y -= Y_SPEED;
			x += X_SPEED;
			break;
		case R:
			x += X_SPEED;
			break;
		case RD:
			y += Y_SPEED;
			x += X_SPEED;
			break;
		case D:
			y += Y_SPEED;
			break;
		case LD:
			y += Y_SPEED;
			x -= X_SPEED;
			break;
		case L:
			x -= X_SPEED;
			break;
		case LU:
			y -= Y_SPEED;
			x -= X_SPEED;
			break;
		case STOP:
			break;

		default:
			break;
		}
		
		if (x < 0 || y < 0 || x > TankClient.GAME_WIDTH || y > TankClient.GAME_HEIGHT) {
			live = false;
		}
	}
	
	/**
	 * �õ��ڵ��ľ��η�Χ������ײ���ʱʹ��
	 * @return �����ڵ��ķ�Χ����
	 */
	public Rectangle getRec() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}
	
	/**
	 * ���̹�˺���
	 * @param tank 
	 * @return ���з���true������Ϊfalse
	 */
	public boolean hitTank(Tank tank) {
		if(this.live && this.getRec().intersects(tank.getRec()) && 
				tank.isLive() && this.good != tank.isGood()) {
			if (tank.isGood()) {
				tank.setLife(tank.getLife() - 20);
				if (tank.getLife() < 0)
					tank.setLive(false);
			}else {
				tank.setLive(false);
			}	
			this.live = false;
			tankClient.explodes.add(new Explode(x, y, tankClient));
			return true;
		}
		
		return false;
	}
	
	public boolean hitTianks(List<Tank> tanks) {
		for (int i = 0; i < tanks.size(); i++) {
			Tank tank = tanks.get(i);
			if (this.hitTank(tank))
				return true;
		}
		
		return false;
	}
	
	/**
	 * ����ǽ�Ĵ���
	 * @param w ǽ
	 * @return ���з���true������Ϊfalse
	 */
	public boolean hitWall(Wall w) {
		if(this.live && this.getRec().intersects(w.getRec())) {
			this.live = false;
			
			return true;
		}
		
		return false;
	}
	
}

























