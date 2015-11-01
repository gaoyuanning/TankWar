package com.gyn.www;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * 墙类
 * @author 高远宁
 *
 */
public class Wall {
	int x, y, w, h;
	TankClient tankClient;
	
	public Wall(int x, int y, int w, int h, TankClient tankClient) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.tankClient = tankClient;
	}
	
	/**
	 * 画墙函数
	 * @param g 画笔
	 */
	public void draw(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.DARK_GRAY);
		g.fillRect(x, y, w, h);
		g.setColor(c);
	}
		/**
	 * 得到墙的矩形范围，在碰撞检测时使用
	 * @return 返回墙的范围矩形
	 */
	public Rectangle getRec() {
		return new Rectangle(x, y, w, h);
	}
}
