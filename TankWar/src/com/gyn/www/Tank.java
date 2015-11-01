package com.gyn.www;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 坦克类
 * @author 高远宁
 *
 */
public class Tank {
	//位置
	/**
	 * 坦克的x坐标
	 */
	int x;
	/**
	 * 坦克的y坐标
	 */
	int y;
	TankClient tankClient;	//持有对方的引用，便于访问成员变量
	
	int oldX;
	int oldY;
	/**
	 * 标示敌方/我方坦克
	 */
	private boolean good; 
	/**
	 * 生命值
	 */
	private int life = 100;
	/**
	 * 生命值显示
	 */
	private BloodBar bloodBar = new BloodBar();
	
	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public boolean isGood() {
		return good;
	}

	boolean live = true;
	/**
	 * 控制方向的随机数
	 */
	private static Random random = new Random();	//控制方向
	private int step = random.nextInt(15) + 3; 
	
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	
	public static final int X_SPEED = 3;
	public static final int Y_SPEED = 3;
	
	private boolean bU = false,  bR = false, bD = false, bL = false;
	/**
	 * 坦克方向
	 */
	Direction direction = Direction.STOP;
	/**
	 * 炮筒方向
	 */
	Direction gunBarrelDirection = Direction.R;
	/**
	 * 坦克宽度
	 */
	public static final int TANK_WIDTH = 30;
	/**
	 * 坦克高度
	 */
	public static final int TANK_HEIGHT = 30;
	
	private static Toolkit toolkit = Toolkit.getDefaultToolkit();
	/**
	 * 坦克的图片
	 */
	private static Image[] tankImages = null;
	private static Map<String, Image> images = new HashMap<String, Image>();
	
	static {
		tankImages = new Image[] {
			toolkit.getImage(Tank.class.getClassLoader().getResource("images/tankU.gif")),
			toolkit.getImage(Tank.class.getClassLoader().getResource("images/tankRU.gif")),
			toolkit.getImage(Tank.class.getClassLoader().getResource("images/tankR.gif")),
			toolkit.getImage(Tank.class.getClassLoader().getResource("images/tankRD.gif")),
			toolkit.getImage(Tank.class.getClassLoader().getResource("images/tankD.gif")),
			toolkit.getImage(Tank.class.getClassLoader().getResource("images/tankLD.gif")),
			toolkit.getImage(Tank.class.getClassLoader().getResource("images/tankL.gif")),
			toolkit.getImage(Tank.class.getClassLoader().getResource("images/tankLU.gif")),
		};
		
		images.put("U", tankImages[0]);
		images.put("RU", tankImages[1]);
		images.put("R", tankImages[2]);
		images.put("RD", tankImages[3]);
		images.put("D", tankImages[4]);
		images.put("LD", tankImages[5]);
		images.put("L", tankImages[6]);
		images.put("LU", tankImages[7]);
	}
	
	public Tank(int x, int y, boolean good) {
		this.x = x;
		this.y = y;
		this.good = good;
	}
	
	public Tank(int x, int y, boolean good, TankClient tankClient) {
		this(x, y, good);
		this.tankClient = tankClient;
		this.oldX = x;
		this.oldY = y;
	}
	/**
	 * 画出坦克
	 * @param g 画笔
	 */
	public void draw(Graphics g) {
		if(!live) {
			if(!good)
				tankClient.tanks.remove(this);
			
			return;
		}
		
		if (this.good) {
			bloodBar.draw(g);
		}
		
		drawTank(g);
		
		move();
	}
	/**
	 * 具体的画法
	 * @param g 画笔
	 */
	public void drawTank(Graphics g) {
		switch (gunBarrelDirection) {
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
	}
	/**
	 * 移动函数
	 */
	public void move() {
		this.oldX = x;
		this.oldY = y;
		
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
		
		if (direction != Direction.STOP)
			gunBarrelDirection = direction;
		if(x < 0) x = 0;
		if(y < 25) y = 25;
		if(x + TANK_WIDTH > TankClient.GAME_WIDTH) x = TankClient.GAME_WIDTH - TANK_WIDTH;
		if(y + TANK_HEIGHT > TankClient.GAME_HEIGHT) y = TankClient.GAME_HEIGHT - TANK_HEIGHT;
 	
		if(!good) {
			Direction[] directions = Direction.values();
			if (step == 0) {
				step = random.nextInt(15) + 3;
				int r = random.nextInt(directions.length);
				this.direction = directions[r];
			}
			step--;
			
			if (random.nextInt(60) > 58)
				this.fire();
		}	
	}
	/**
	 * 处理键盘被按下事件
	 * @param e 键盘事件
	 */
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		switch (key) {
			case KeyEvent.VK_UP:
				bU = true;
				break;
			case KeyEvent.VK_RIGHT:
				bR = true;
				break;
			case KeyEvent.VK_DOWN:
				bD = true;
				break;
			case KeyEvent.VK_LEFT:
				bL = true;
				break;
			case KeyEvent.VK_W:
				superFire();
				break;
			case KeyEvent.VK_A:
				superMissilefire(gunBarrelDirection);
				break;
			default:
				break;
		}
		
		setDirection();
	}
	/**
	 * 根据按键设定方向
	 */
	public void setDirection() {
		if (bU && !bR && !bD && !bL) direction = Direction.U;
		else if (bU && bR && !bD && !bL) direction = Direction.RU;
		else if (!bU && bR && !bD && !bL) direction = Direction.R;
		else if (!bU && bR && bD && !bL) direction = Direction.RD;
		else if (!bU && !bR && bD && !bL) direction = Direction.D;
		else if (!bU && !bR && bD && bL) direction = Direction.LD;
		else if (!bU && !bR && !bD && bL) direction = Direction.L;
		else if (bU && !bR && !bD && bL) direction = Direction.LU;
		else if(!bU && !bR && !bD && !bL) direction = Direction.STOP;
	}
	/**
	 * 处理键盘释放事件
	 * @param e 键盘事件
	 */
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
	
			switch (key) {
			    case KeyEvent.VK_R:
			    	if (good) {
			    		if (!live)
			    			live = true;
			    			life = 100;
			    	}
			    	break;
				case KeyEvent.VK_S:
					fire();
					break;
				case KeyEvent.VK_UP:
					bU = false;
					break;
				case KeyEvent.VK_RIGHT:
					bR = false;
					break;
				case KeyEvent.VK_DOWN:
					bD = false;
					break;
				case KeyEvent.VK_LEFT:
					bL = false;
					break;
				case KeyEvent.VK_W:
					superFire();
					break;
				default:
					break;
			}
			
			setDirection();
		
	}
	/**
	 * 开火函数
	 * @return 返回一个炮弹类型
	 */
	public Missile fire() {
		return fire(gunBarrelDirection);
	}
	/**
	 * 开火函数的具体实现
	 * @param direction 方向
	 * @return 返回一个炮弹
	 */
	public Missile fire(Direction direction) {
		if(!live) {
			return null;
		}
		Missile missile = new Missile(x + TANK_HEIGHT/2, y + TANK_HEIGHT/2, this.good,
					direction, this.tankClient);
		tankClient.missiles.add(missile);
		return missile;
	}
	/**
	 * 超级开火，一次性8枚炮弹
	 */
	public void  superFire() {
		Direction[] directions = Direction.values();
		for(int i = 0; i < directions.length - 1; i++) {
			fire(directions[i]);
		}
	}
	/**
	 * 超级炮弹函数
	 * @param direction 方向
	 * @return 超级炮弹类型
	 */
	public SuperMissile superMissilefire(Direction direction) {
		if(!live) {
			return null;
		}
		SuperMissile missile = new SuperMissile(x + TANK_HEIGHT/2, y + TANK_HEIGHT/2, this.good,
					direction, this.tankClient);
		tankClient.missiles.add(missile);
		return missile;
	}
	/**
	 * 得到坦克的矩形范围，在碰撞检测时使用
	 * @return 返回墙的范围矩形
	 */
	public Rectangle getRec() {
		return new Rectangle(x, y, TANK_WIDTH, TANK_HEIGHT);
	}
	
	public void stay() {
		x = this.oldX;
		y = this.oldY;
	}
	
	public boolean collidesWithWall(Wall w) {
		/**
		 * 碰撞检测
		 */
		if(this.live && this.getRec().intersects(w.getRec())) {
			stay();
			return true;
		}
		
		return false;
	}
	/**
	 * 坦克与墙相撞时的处理函数
	 * @param tanks 坦克
	 * @return 布尔类型值
	 */
	public boolean collidesWithTanks(java.util.List<Tank> tanks) {
		for (int i = 0; i < tanks.size(); i++) {
			Tank tank = tanks.get(i);
			if (this != tank) {
				if(this.live && tank.isLive() &&this.getRec().intersects(tank.getRec())) {
					stay();
					return true;
				}
			}
		}
		
		return false;
	}
	/**
	 * 生命值，内部类
	 * @author Administrator
	 *
	 */
	class BloodBar {
		public void draw(Graphics g) {
			Color color = g.getColor();
			g.setColor(Color.BLUE);
			g.drawRect(x + 10, y - 10, TANK_WIDTH + 5, 10);
			int w = (TANK_WIDTH + 15) * life / 100;
			g.fillRect(x, y - 10, w, 10);
			g.setColor(color);
		}
	}
	/**
	 * 吃血块函数
	 * @param blood 血块
	 * @return 布尔类型值
	 */
	public boolean eatBlood(Blood blood) {
		if (this.live && blood.live && this.getRec().intersects(blood.getRec())) {
			this.life = 100;
			blood.setLive(false);
			return true;
		}
		
		return false;
	}
}





















