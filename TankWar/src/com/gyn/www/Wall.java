package com.gyn.www;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * ǽ��
 * @author ��Զ��
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
	 * ��ǽ����
	 * @param g ����
	 */
	public void draw(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.DARK_GRAY);
		g.fillRect(x, y, w, h);
		g.setColor(c);
	}
		/**
	 * �õ�ǽ�ľ��η�Χ������ײ���ʱʹ��
	 * @return ����ǽ�ķ�Χ����
	 */
	public Rectangle getRec() {
		return new Rectangle(x, y, w, h);
	}
}
