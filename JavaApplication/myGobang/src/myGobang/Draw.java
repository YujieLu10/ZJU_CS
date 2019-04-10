package myGobang;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.myGobang.util.IOUtil;

import myGobang.bean.GameData;
import myGobang.bean.Point;
import myGobang.bean.User;

import java.awt.BasicStroke;


public class Draw extends JPanel{

	private int x_offset = 75;//x偏移
	private int y_offset=50;
	private User user;//玩家
	private boolean gameStart;//true进行中，false结束
	private int whichColor;//该谁下棋 1黑棋，2白棋
	private int[][] qizi;//存放棋子 0没棋子， 1黑棋，2白棋
	private ImageIcon black,white,empty;//黑棋，白棋，光标
	private int gameState;//1人人对弈，2人机对弈
	private Socket s;//客户端
	private Ruler ruler;//规则
	private int move_x,move_y;
	
	public Draw(){
		
		qizi=new int[15][15];
		ruler=new Ruler(qizi);
		String basePath=this.getClass().getResource("/").getPath();
		black=new ImageIcon(basePath+"black.jpg");
		white=new ImageIcon(basePath+"white.jpg");
		empty=new ImageIcon(basePath+"empty.png");
		this.setSize(500, 500);
		this.setBackground(new Color(240,240,190));
		user=new User();
		addListener();
	}
	
	
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		
		paintQipan(g);
		paintQizi(g);
		if(gameState==1||gameState==2)
			paintCursor(g);
		
		ruler.setQizi(qizi);
		
		if(ruler.isOver()==1){
			Map.gameInfo.setText("黑棋胜");
			gameStart=false;
		}else if(ruler.isOver()==2){
			Map.gameInfo.setText("白棋胜");
			gameStart=false;
		}
	}
	
	/**
	 * 绘制光标
	 * @param g
	 */
	public void paintCursor(Graphics g){
		g.drawImage(empty.getImage(),30*move_x+x_offset-10,30*move_y+y_offset-10,20,20,this);
	}
	/**
	 * 绘制下棋区域的棋盘
	 * @param g
	 */
	public void paintQipan(Graphics g){
		
		Graphics2D g2d=(Graphics2D)g;
		
		//四条边加粗
		g2d.setStroke(new BasicStroke(3f));
		g2d.drawLine(x_offset+0,y_offset+0,x_offset+420,y_offset+0);
		g2d.drawLine(x_offset+0,y_offset+420,x_offset+420,y_offset+420);
		g2d.drawLine(x_offset+0,y_offset+0,x_offset+0,y_offset+420);
		g2d.drawLine(x_offset+420,y_offset+0,x_offset+420,y_offset+420);
		g2d.setStroke(new BasicStroke());
		
		//绘制棋盘外围的数字
		for(int i=0;i<15;i++){
			g2d.drawString((i+1)+"",30*i+x_offset-3,y_offset-10);
			g2d.drawString((i+1)+"",x_offset-25,30*i+y_offset+3);
		}
		
		for(int i=0;i<15;i++){
			//画横线
			//起点(0,0) (0,1) (0,2) 终点(14,0) (14,1)
			g2d.drawLine(x_offset+0,y_offset+i*30,x_offset+14*30,y_offset+i*30);
			//画竖线
			g2d.drawLine(x_offset+i*30,y_offset+0,x_offset+i*30,y_offset+14*30);
			
		}
		//(3,3) (3,11) (11,3) (11,11) (7,7)画上小黑点
		g2d.fillOval(x_offset+30*3-3,y_offset+30*3-3,6,6);
		g2d.fillOval(x_offset+30*3-3,y_offset+30*11-3,6,6);
		g2d.fillOval(x_offset+30*11-3,y_offset+30*3-3,6,6);
		g2d.fillOval(x_offset+30*11-3,y_offset+30*11-3,6,6);
		g2d.fillOval(x_offset+30*7-3,y_offset+30*7-3,6,6);
	}
	
	
	/**
	 * 绘制棋子
	 * @param g
	 */
	public void paintQizi(Graphics g){
		for(int i=0;i<15;i++){
			for(int j=0;j<15;j++){
				if(qizi[i][j]==1){
					//画一个黑棋
					g.drawImage(black.getImage(),30*i+x_offset-10,30*j+y_offset-10,20,20,this);
				}else if(qizi[i][j]==2){
					//画一个白棋
					g.drawImage(white.getImage(),30*i+x_offset-10,30*j+y_offset-10,20,20,this);
				}
			}
		}
	}

	/**
	 * 增加监听
	 */
	public void addListener(){
		this.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {

				//游戏未开始
				if(!gameStart)return;
				//没有轮到自己下棋
				if(user.getQiziColor()!=whichColor)return;
				
				int x=e.getX();
				int y=e.getY();
			
				if(x>=0+x_offset-10&&x<=420+x_offset+10&&y>=0+y_offset-10&&y<=420+y_offset+10){
					
					x=(int)Math.round((x-x_offset)/30.0);
					y=(int)Math.round((y-y_offset)/30.0);
					
					if(qizi[x][y]!=0)return;
					qizi[x][y]=user.getQiziColor();
					repaint();
					changeQiziColor();
					showGameInfo();
					
					//人人对弈
					if(gameState==1){
						//通知服务器，当前用户下棋的信息
						GameData data=new GameData();
						data.setUser(user);
						data.setP(new Point(x,y));
						sendInfo2Server(data);
					}else if(gameState==2){
						//人机对弈
						computerCalNextStep();
					}
					
				}
				
			}
		});
		
		this.addMouseMotionListener(new MouseMotionAdapter() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				
				//游戏未开始
				if(!gameStart)return;
				
				int x=e.getX();
				int y=e.getY();
			
				if(x>=0+x_offset-10&&x<=420+x_offset+10&&y>=0+y_offset-10&&y<=420+y_offset+10){
					
					move_x=(int)Math.round((x-x_offset)/30.0);
					move_y=(int)Math.round((y-y_offset)/30.0);
					repaint();
				}
			}
			 
		});
	}
	
	/**
	 * 电脑来计算下一步棋
	 */
	public void computerCalNextStep(){
		//先让电脑随机下棋[0,1)
		while(true){
			
			int x=(int)(Math.random()*15);
			int y=(int)(Math.random()*15);
			if(qizi[x][y]!=0)continue;
			int computerColor=0;
			if(user.getQiziColor()==1)computerColor=2;
			else if(user.getQiziColor()==2)computerColor=1;
			
			qizi[x][y]=computerColor;
			
			repaint();
			changeQiziColor();
			showGameInfo();
			
			return;
		}
		
	}
	
	//连接服务器
	public void connectServer(){
		try {
			s=new Socket("127.0.0.1",8888);
			GameData d=new GameData();
			d.setUser(user);
			IOUtil.writeObject(d,s.getOutputStream());
			//启动读数据的线程
			new Thread(new GetInfoFromServer()).start();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//给服务器发信息
	public void sendInfo2Server(GameData data){
		try {
			IOUtil.writeObject(data,s.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 改变棋子颜色
	 */
	public void changeQiziColor(){
		if(whichColor==1)whichColor=2;
		else if(whichColor==2)whichColor=1;
	}
	
	public void showGameInfo(){
		if(whichColor==1){
			Map.gameInfo.setText("轮到黑方下棋");
		}else if(whichColor==2){
			Map.gameInfo.setText("轮到白方下棋");
		}
	}
	/**
	 * 新开一盘棋
	 */
	public void playNewGame(){
		qizi=new int[15][15];
		gameStart=true;
		whichColor=1;
		
	}

	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public int getGameState() {
		return gameState;
	}


	public void setGameState(int gameState) {
		this.gameState = gameState;
	}
	
	//从服务器读数据的线程
	class GetInfoFromServer implements Runnable{

		@Override
		public void run() {
			while(true){
				try {
					GameData data=(GameData)IOUtil.readObject(s.getInputStream());
					qizi[data.getP().getX()][data.getP().getY()]
							=data.getUser().getQiziColor();
					repaint();
					changeQiziColor();
					showGameInfo();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
}
