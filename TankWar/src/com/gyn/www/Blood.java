package com.gyn.www;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

/**
 * 血块，吃完可以提高生命值
 * @author 高远宁
 *
 */
public class Blood {
	int x, y, w, h;
	TankClient tankClient;
	
	/**
	 * 血块的位置坐标
	 */
	int[][]pos = {
			{360, 360}, {400, 360}, {440, 360},
			{500,400}, {440, 440}, {440, 500},
			{400, 420}, {360, 380}
	};
	
	int index = 0;
	/**
	 * 检测血块是否被吃掉
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
	 * 画出血块
	 * @param g 画笔
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
	 * 得到血块的矩形范围，在碰撞检测时使用
	 * @return 返回血块的范围矩形
	 */
	public Rectangle getRec() {
		return new Rectangle(x, y, w, h);
	}
	
	
}














