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
 * 棋盘
 * @author 柠檬学院李伟
 *
 */
public class Map extends JFrame {

	private JMenuBar bar;//菜单栏
	private JMenu xitong;//系统功能
	private JMenuItem login;//登录
	private JMenu renrenduiyi;//人人对弈
	private JMenuItem rr_renzhihei;//人人对弈时人执黑
	private JMenuItem rr_renzhibai;//人人对弈时人执白
	private JMenu renjiduiyi;//人机对弈
	private JMenuItem rj_renzhihei;//人执黑
	private JMenuItem rj_renzhibai;//人执白
	private Draw draw;//绘制下棋区域
	public static JLabel userInfo;//显示用户信息
	public static JLabel gameInfo;//显示游戏信息
	
	
	public Map(){
		super("腾讯课堂Coding学院-五子棋大战 V1.0.1");
		
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
	 * 对组件进行初始化
	 */
	public void init(){
		
		bar=new JMenuBar();
		xitong=new JMenu("系统功能");
		login=new JMenuItem("登录");
		renrenduiyi=new JMenu("人人对弈");
		rr_renzhihei=new JMenuItem("人执黑");
		rr_renzhibai=new JMenuItem("人执白");
		renjiduiyi=new JMenu("人机对弈");
		rj_renzhihei=new JMenuItem("人执黑");
		rj_renzhibai=new JMenuItem("人执白");
		draw=new Draw();
		userInfo=new JLabel("未登录");
		userInfo.setHorizontalAlignment(JLabel.CENTER);
		gameInfo=new JLabel("游戏未开始");
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
	 * 给组件创建监听器
	 */
	public void addListener(){
		//人人对弈时人执黑增加监听方法
		rr_renzhihei.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				draw.getUser().setQiziColor(1);
				//新开一盘游戏
				draw.setGameState(1);//人人对弈
				draw.playNewGame();
				gameInfo.setText("游戏开始，轮到黑方下棋");
				//连接服务器
				draw.connectServer();
			}
		});
		
		//人人对弈是人执白增加监听方法
		rr_renzhibai.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				draw.getUser().setQiziColor(2);
				//新开一盘游戏
				draw.setGameState(1);//人人对弈
				draw.playNewGame();
				gameInfo.setText("游戏开始，轮到黑方下棋");
				//连接服务器
				draw.connectServer();
			}
		});
		//登录功能
		login.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String userName=JOptionPane.showInputDialog("请输入用户名");
				if(userName==null||userName.trim().equals("")){
					JOptionPane.showConfirmDialog(null,"用户名不能为空");
					return;
				}
				draw.getUser().setUserName(userName);
				userInfo.setText("欢迎你，"+userName);
			}
		});
		
		//人机对弈人执黑
		rj_renzhihei.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				draw.getUser().setQiziColor(1);
				//新开一盘游戏
				draw.setGameState(2);//人机对弈
				draw.playNewGame();
				gameInfo.setText("游戏开始，轮到黑方下棋");
				
			}
		});
		
		//人机对弈人执白
		rj_renzhibai.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				draw.getUser().setQiziColor(2);
				//新开一盘游戏
				draw.setGameState(2);//人机对弈
				draw.playNewGame();
				gameInfo.setText("游戏开始，轮到黑方下棋");
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