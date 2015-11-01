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
 * 炮弹类
 * @author 高远宁 
 *
 */
public class Missile {
	/**
	 * 子弹的x坐标
	 */
	int x;
	/**
	 * 子弹的y坐标
	 */
	int y;
	/**
	 * 子弹的横向速度
	 */
	public static final int X_SPEED = 5;
	/**
	 * 子弹的纵向速度
	 */
	public static final int Y_SPEED = 5;
	public static final int WIDTH = 10;
	public static final int HEIGHT = 10;
	
	/**
	 * 子弹的方向
	 */
	Direction direction;
	
	boolean live = true;
	
	/**
	 * 标示敌方/我方子弹
	 */
	private boolean good;
	
	TankClient tankClient;
	
	/**
	 * 利用ToolKit方便读取硬盘的图片
	 */
	private static Toolkit toolkit = Toolkit.getDefaultToolkit();
	
	/**
	 * 图片数组
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
	 * 画出子弹
	 * @param 画笔
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
	 * 子弹移动函数
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
	 * 得到炮弹的矩形范围，在碰撞检测时使用
	 * @return 返回炮弹的范围矩形
	 */
	public Rectangle getRec() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}
	
	/**
	 * 打击坦克函数
	 * @param tank 
	 * @return 打中返回true，否则为false
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
	 * 击中墙的处理
	 * @param w 墙
	 * @return 打中返回true，否则为false
	 */
	public boolean hitWall(Wall w) {
		if(this.live && this.getRec().intersects(w.getRec())) {
			this.live = false;
			
			return true;
		}
		
		return false;
	}
	
}

























