package com.gyn.www;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

/**
 * 爆炸类
 * @author 高远宁
 *
 */
public class Explode {
	int x;
	int y;
	TankClient tankClient;
	
	private boolean live = true;

	int index = 0;
	
	private static boolean init = false;
	
	/**
	 * 利用ToolKit方便读取硬盘的图片
	 */
	private static Toolkit toolkit = Toolkit.getDefaultToolkit();
	/**
	 * 爆炸时的10张不同状态图片
	 */
	private static Image[] images = {
		toolkit.getImage(Explode.class.getClassLoader().getResource("images/0.gif")),
		toolkit.getImage(Explode.class.getClassLoader().getResource("images/1.gif")),
		toolkit.getImage(Explode.class.getClassLoader().getResource("images/2.gif")),
		toolkit.getImage(Explode.class.getClassLoader().getResource("images/3.gif")),
		toolkit.getImage(Explode.class.getClassLoader().getResource("images/4.gif")),
		toolkit.getImage(Explode.class.getClassLoader().getResource("images/5.gif")),
		toolkit.getImage(Explode.class.getClassLoader().getResource("images/6.gif")),
		toolkit.getImage(Explode.class.getClassLoader().getResource("images/7.gif")),
		toolkit.getImage(Explode.class.getClassLoader().getResource("images/8.gif")),
		toolkit.getImage(Explode.class.getClassLoader().getResource("images/9.gif")),
		toolkit.getImage(Explode.class.getClassLoader().getResource("images/10.gif"))
	}; 
	
	public Explode(int x, int y, TankClient tankClient) {
		this.x = x;
		this.y = y;
		this.tankClient = tankClient;
	}


	/**
	 * 	画出爆炸类
	 * @param g 画笔
	 */
	public void draw(Graphics g) {
		
		if(false == init) {
			for (int j = 0; j < images.length; j++) {
				g.drawImage(images[j], -100, -100, null);
			}
			
			init = true;
		}
		
		if (index == images.length) {
			live = false;
			tankClient.explodes.remove(this);
			return;
		}
		
		g.drawImage(images[index], x, y, null);
		index++;
		
	}
}
