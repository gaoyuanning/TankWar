package com.gyn.www;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

/**
 * ��ը��
 * @author ��Զ��
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
	 * ����ToolKit�����ȡӲ�̵�ͼƬ
	 */
	private static Toolkit toolkit = Toolkit.getDefaultToolkit();
	/**
	 * ��ըʱ��10�Ų�ͬ״̬ͼƬ
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
	 * 	������ը��
	 * @param g ����
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
