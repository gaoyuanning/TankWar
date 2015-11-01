package com.gyn.www;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
/**
 * 整个游戏的主函数，显示窗口
 * @author 高远宁
 *
 */
public class TankClient extends Frame{
	/**
	 * 窗口的宽度
	 */
	public static final int GAME_WIDTH = 800;
	/**
	 * 窗口的高度
	 */
	public static final int GAME_HEIGHT = 600;
	/**
	 * 画布，用于双缓冲
	 */
	Image offScreenImage = null;	//背后的画布
	/**
	 * 我方坦克
	 */
	Tank myTank = new Tank(50, 550, true, this);
	/**
	 * 墙
	 */
	Wall w1 = new Wall(100, 200, 20, 150, this);
	/*
	 * 墙
	 */
	Wall w2 = new Wall(300, 100, 300, 20, this);
	/**
	 * 血块
	 */
	Blood blood = new Blood();
	/**
	 * 承载爆炸的集合
	 */
	List<Explode> explodes = new ArrayList<Explode>();
	/**
	 * 承载炮弹的集合
	 */
	List<Missile> missiles = new ArrayList<Missile>();
	/**
	 * 承载坦克的集合
	 */
	List<Tank> tanks = new ArrayList<Tank>();
	
	/**
	 * 程序入口
	 * @param args
	 */
	public static void main(String[] args) {
		TankClient tankClient = new TankClient();
		tankClient.launchFrame();
	}
	
	/**
	 * 绘制函数，分别调用其他类的绘制函数
	 */
	public void paint(Graphics g) {
		for (int i = 0; i < missiles.size(); i++) {
			Missile missile = missiles.get(i);
			missile.draw(g);
			missile.hitTianks(tanks);
			missile.hitTank(myTank);
			missile.hitWall(w1);
			missile.hitWall(w2);
		}
		for (int i = 0; i < explodes.size(); i++) {
			Explode explode = explodes.get(i);
			explode.draw(g);
		}
		
		if (tanks.size() == 0) {
			addTanks();
		}
		for (int i = 0; i < tanks.size(); i++) {
			Tank tank = tanks.get(i);
			tank.draw(g);
			tank.collidesWithWall(w1);
			tank.collidesWithWall(w2);
			tank.collidesWithTanks(tanks);
		}
		
		myTank.draw(g);
		myTank.eatBlood(blood);
		//外挂，自己的可以穿墙
//		myTank.collidesWithWall(w1);
//		myTank.collidesWithWall(w2);
		Color c = g.getColor();
		g.setColor(Color.WHITE);
		g.drawString("missile count: " + missiles.size(), 8, 35);
		g.drawString("explpdes count: " + explodes.size(), 8, 50);
		g.drawString("tanks count: " + tanks.size(), 8, 65);
		g.setColor(c);
		w1.draw(g);
		w2.draw(g);
		blood.draw(g);
	}
	
	/**
	 * 双缓冲技术，解决闪屏问题
	 */
	public void update(Graphics g) {
		if(offScreenImage == null) {
			offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);	//画布
		}
		
		Graphics graphicsOffScreen = offScreenImage.getGraphics();	//该画笔负责往画布上画画，而g是往屏幕上画画
		
		Color c = graphicsOffScreen.getColor();
		graphicsOffScreen.setColor(Color.BLACK);
		graphicsOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		graphicsOffScreen.setColor(c);
//		missile.draw(graphicsOffScreen);
//		myTank.draw(graphicsOffScreen);
		paint(graphicsOffScreen);
		
		g.drawImage(offScreenImage, 0, 0, null);	//在把画布上的画到屏幕上
	}

	/**
	 * 初始化函数
	 */
	public void launchFrame() {
		this.setLocation(300, 100);
		this.setSize(GAME_WIDTH, GAME_HEIGHT);
		this.setResizable(false);
		this.setTitle("坦克大战");
		this.setBackground(Color.black);
		this.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent g) {
				setVisible(false);
				System.exit(0);
			}
			
		});
		this.addKeyListener(new KeyMonitor());
		this.addTanks();
		this.setVisible(true);
		
		new Thread(new PaintThread()).start();
	}
	/**
	 * 线程，用于控制绘制函数
	 * @author 高远宁
	 *
	 */
	private class PaintThread implements Runnable {

		public void run() {
			
			while(true) {
				repaint();
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 往游戏里加坦克函数
	 */
	public void addTanks() {
		
		
		int initTankCount = Integer.parseInt(PropertyManager.getProperty("initTankCount"));
		
		for (int i = 0; i < initTankCount; i++) {
			tanks.add(new Tank(10 + i*40, 50, false, this));
		}
	}
	/**
	 * 键盘事件监听
	 * @author Administrator
	 *
	 */
	private class KeyMonitor extends KeyAdapter {

		@Override
		public void keyReleased(KeyEvent e) {
			myTank.keyReleased(e);
		}

		public void keyPressed(KeyEvent e) {
			
			myTank.keyPressed(e);
		}
		
	}
}

















