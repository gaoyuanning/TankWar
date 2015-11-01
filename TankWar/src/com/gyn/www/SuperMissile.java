package com.gyn.www;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * 超级炮弹类，继承自炮弹类
 * @author 高远宁
 *
 */
public class SuperMissile extends Missile{

	public SuperMissile(int x, int y, boolean good, Direction direction,
			TankClient tankClient) {
		super(x, y, good, direction, tankClient);
	}
	
	public void draw(Graphics g) {
		if(!live) {
			tankClient.missiles.remove(this);
			return;
		}
		Color c = g.getColor();
		g.setColor(Color.CYAN);
		g.fillOval(x, y, 30, 30);
		g.setColor(c);
		
		move();
	}
	
	/**
	 * 得到超级炮弹的矩形范围，在碰撞检测时使用
	 * @return 返回超级炮弹的范围矩形
	 */
	public Rectangle getRec() {
		return new Rectangle(x, y, 30, 30);
	}
	
	public static final int X_SPEED = 10;
	public static final int Y_SPEED = 10;
	
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
}
