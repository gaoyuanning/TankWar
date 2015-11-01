package com.gyn.www;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

/**
 * Ѫ�飬��������������ֵ
 * @author ��Զ��
 *
 */
public class Blood {
	int x, y, w, h;
	TankClient tankClient;
	
	/**
	 * Ѫ���λ������
	 */
	int[][]pos = {
			{360, 360}, {400, 360}, {440, 360},
			{500,400}, {440, 440}, {440, 500},
			{400, 420}, {360, 380}
	};
	
	int index = 0;
	/**
	 * ���Ѫ���Ƿ񱻳Ե�
	 */
	boolean live = true;
	
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public Blood() {
		x = pos[0][0];
		y = pos[0][1];
		w = 15; 
		h = 15;
	}
	/**
	 * ����Ѫ��
	 * @param g ����
	 */
	public void draw(Graphics g) {
		if(!live) {
			return;
		}
		
		index++;
		if (index == pos.length) {
			index = 0;
		}
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillRect(x, y, w, h);
		g.setColor(c);
		x = pos[index][0];
		y = pos[index][1];
	}
	
	/**
	 * �õ�Ѫ��ľ��η�Χ������ײ���ʱʹ��
	 * @return ����Ѫ��ķ�Χ����
	 */
	public Rectangle getRec() {
		return new Rectangle(x, y, w, h);
	}
	
	
}














