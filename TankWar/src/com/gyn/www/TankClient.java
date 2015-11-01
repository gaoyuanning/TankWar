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
 * ������Ϸ������������ʾ����
 * @author ��Զ��
 *
 */
public class TankClient extends Frame{
	/**
	 * ���ڵĿ��
	 */
	public static final int GAME_WIDTH = 800;
	/**
	 * ���ڵĸ߶�
	 */
	public static final int GAME_HEIGHT = 600;
	/**
	 * ����������˫����
	 */
	Image offScreenImage = null;	//����Ļ���
	/**
	 * �ҷ�̹��
	 */
	Tank myTank = new Tank(50, 550, true, this);
	/**
	 * ǽ
	 */
	Wall w1 = new Wall(100, 200, 20, 150, this);
	/*
	 * ǽ
	 */
	Wall w2 = new Wall(300, 100, 300, 20, this);
	/**
	 * Ѫ��
	 */
	Blood blood = new Blood();
	/**
	 * ���ر�ը�ļ���
	 */
	List<Explode> explodes = new ArrayList<Explode>();
	/**
	 * �����ڵ��ļ���
	 */
	List<Missile> missiles = new ArrayList<Missile>();
	/**
	 * ����̹�˵ļ���
	 */
	List<Tank> tanks = new ArrayList<Tank>();
	
	/**
	 * �������
	 * @param args
	 */
	public static void main(String[] args) {
		TankClient tankClient = new TankClient();
		tankClient.launchFrame();
	}
	
	/**
	 * ���ƺ������ֱ����������Ļ��ƺ���
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
		//��ң��Լ��Ŀ��Դ�ǽ
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
	 * ˫���弼���������������
	 */
	public void update(Graphics g) {
		if(offScreenImage == null) {
			offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);	//����
		}
		
		Graphics graphicsOffScreen = offScreenImage.getGraphics();	//�û��ʸ����������ϻ�������g������Ļ�ϻ���
		
		Color c = graphicsOffScreen.getColor();
		graphicsOffScreen.setColor(Color.BLACK);
		graphicsOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		graphicsOffScreen.setColor(c);
//		missile.draw(graphicsOffScreen);
//		myTank.draw(graphicsOffScreen);
		paint(graphicsOffScreen);
		
		g.drawImage(offScreenImage, 0, 0, null);	//�ڰѻ����ϵĻ�����Ļ��
	}

	/**
	 * ��ʼ������
	 */
	public void launchFrame() {
		this.setLocation(300, 100);
		this.setSize(GAME_WIDTH, GAME_HEIGHT);
		this.setResizable(false);
		this.setTitle("̹�˴�ս");
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
	 * �̣߳����ڿ��ƻ��ƺ���
	 * @author ��Զ��
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
	 * ����Ϸ���̹�˺���
	 */
	public void addTanks() {
		
		
		int initTankCount = Integer.parseInt(PropertyManager.getProperty("initTankCount"));
		
		for (int i = 0; i < initTankCount; i++) {
			tanks.add(new Tank(10 + i*40, 50, false, this));
		}
	}
	/**
	 * �����¼�����
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

















