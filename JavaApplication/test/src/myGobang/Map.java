package myGobang;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import myGobang.bean.User;





/**
 * ����
 * @author ����ѧԺ��ΰ
 *
 */
public class Map extends JFrame {

	private JMenuBar bar;//�˵���
	private JMenu xitong;//ϵͳ����
	private JMenuItem login;//��¼
	private JMenu renrenduiyi;//���˶���
	private JMenuItem rr_renzhihei;//���˶���ʱ��ִ��
	private JMenuItem rr_renzhibai;//���˶���ʱ��ִ��
	private JMenu renjiduiyi;//�˻�����
	private JMenuItem rj_renzhihei;//��ִ��
	private JMenuItem rj_renzhibai;//��ִ��
	private Draw draw;//������������
	public static JLabel userInfo;//��ʾ�û���Ϣ
	public static JLabel gameInfo;//��ʾ��Ϸ��Ϣ
	
	
	public Map(){
		super("��Ѷ����CodingѧԺ-�������ս V1.0.1");
		
		init();
		
		this.setSize(600, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
		int width=(int)d.getWidth();
		int height=(int)d.getHeight();
		
		this.setLocation((width-600)/2,(height-600)/2);
		this.setVisible(true);
		
		addListener();
	}
	
	/**
	 * ��������г�ʼ��
	 */
	public void init(){
		
		bar=new JMenuBar();
		xitong=new JMenu("ϵͳ����");
		login=new JMenuItem("��¼");
		renrenduiyi=new JMenu("���˶���");
		rr_renzhihei=new JMenuItem("��ִ��");
		rr_renzhibai=new JMenuItem("��ִ��");
		renjiduiyi=new JMenu("�˻�����");
		rj_renzhihei=new JMenuItem("��ִ��");
		rj_renzhibai=new JMenuItem("��ִ��");
		draw=new Draw();
		userInfo=new JLabel("δ��¼");
		userInfo.setHorizontalAlignment(JLabel.CENTER);
		gameInfo=new JLabel("��Ϸδ��ʼ");
		//gameInfo.setText("")
		gameInfo.setHorizontalAlignment(JLabel.CENTER);
		
		bar.add(xitong);
		xitong.add(login);
		xitong.add(renrenduiyi);
		xitong.add(renjiduiyi);
		
		renrenduiyi.add(rr_renzhihei);
		renrenduiyi.add(rr_renzhibai);
		renjiduiyi.add(rj_renzhihei);
		renjiduiyi.add(rj_renzhibai);
		
		this.setLayout(new BorderLayout());
		
		this.setJMenuBar(bar);
		this.add(userInfo,BorderLayout.NORTH);
		this.add(draw,BorderLayout.CENTER);
		this.add(gameInfo,BorderLayout.SOUTH);
		
		
	}

	/**
	 * ���������������
	 */
	public void addListener(){
		//���˶���ʱ��ִ�����Ӽ�������
		rr_renzhihei.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				draw.getUser().setQiziColor(1);
				//�¿�һ����Ϸ
				draw.setGameState(1);//���˶���
				draw.playNewGame();
				gameInfo.setText("��Ϸ��ʼ���ֵ��ڷ�����");
				//���ӷ�����
				draw.connectServer();
			}
		});
		
		//���˶�������ִ�����Ӽ�������
		rr_renzhibai.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				draw.getUser().setQiziColor(2);
				//�¿�һ����Ϸ
				draw.setGameState(1);//���˶���
				draw.playNewGame();
				gameInfo.setText("��Ϸ��ʼ���ֵ��ڷ�����");
				//���ӷ�����
				draw.connectServer();
			}
		});
		//��¼����
		login.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String userName=JOptionPane.showInputDialog("�������û���");
				if(userName==null||userName.trim().equals("")){
					JOptionPane.showConfirmDialog(null,"�û�������Ϊ��");
					return;
				}
				draw.getUser().setUserName(userName);
				userInfo.setText("��ӭ�㣬"+userName);
			}
		});
		
		//�˻�������ִ��
		rj_renzhihei.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				draw.getUser().setQiziColor(1);
				//�¿�һ����Ϸ
				draw.setGameState(2);//�˻�����
				draw.playNewGame();
				gameInfo.setText("��Ϸ��ʼ���ֵ��ڷ�����");
				
			}
		});
		
		//�˻�������ִ��
		rj_renzhibai.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				draw.getUser().setQiziColor(2);
				//�¿�һ����Ϸ
				draw.setGameState(2);//�˻�����
				draw.playNewGame();
				gameInfo.setText("��Ϸ��ʼ���ֵ��ڷ�����");
			}
		});
	}
	
	
	public Draw getDraw() {
		return draw;
	}

	public void setDraw(Draw draw) {
		this.draw = draw;
	}

	public static void main(String[] args) {
		
		new Map();
	}
}