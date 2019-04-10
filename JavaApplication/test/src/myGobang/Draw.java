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


/**
 * ������������
 * @author ����ѧԺ��ΰ
 *
 */
public class Draw extends JPanel{

	private int py_x=75;//xƫ��
	private int py_y=50;
	private User user;//���
	private boolean gameStart;//true�����У�false����
	private int whichColor;//��˭���� 1���壬2����
	private int[][] qizi;//������� 0û���ӣ� 1���壬2����
	private ImageIcon black,white,empty;//���壬���壬���
	private int gameState;//1���˶��ģ�2�˻�����
	private Socket s;//�ͻ���
	private Ruler ruler;//����
	private int move_x,move_y;
	
	public Draw(){
		
		qizi=new int[15][15];
		ruler=new Ruler(qizi);
		String basePath=this.getClass().getResource("/").getPath();
		black=new ImageIcon(basePath+"black.jpg");
		white=new ImageIcon(basePath+"white.jpg");
		empty=new ImageIcon(basePath+"empty.png");
		this.setSize(500, 500);
		this.setBackground(new Color(220,191,157));
		user=new User();
		addListener();
	}
	
	
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		
		paintQipan(g);
		paintQizi(g);
		paintCursor(g);
		
		ruler.setQizi(qizi);
		
		if(ruler.isOver()==1){
			Map.gameInfo.setText("����ʤ");
			gameStart=false;
		}else if(ruler.isOver()==2){
			Map.gameInfo.setText("����ʤ");
			gameStart=false;
		}
	}
	
	/**
	 * ���ƹ��
	 * @param g
	 */
	public void paintCursor(Graphics g){
		g.drawImage(empty.getImage(),30*move_x+py_x-10,30*move_y+py_y-10,20,20,this);
	}
	/**
	 * �����������������
	 * @param g
	 */
	public void paintQipan(Graphics g){
		
		Graphics2D g2d=(Graphics2D)g;
		
		//�����߼Ӵ�
		g2d.setStroke(new BasicStroke(3f));
		g2d.drawLine(py_x+0,py_y+0,py_x+420,py_y+0);
		g2d.drawLine(py_x+0,py_y+420,py_x+420,py_y+420);
		g2d.drawLine(py_x+0,py_y+0,py_x+0,py_y+420);
		g2d.drawLine(py_x+420,py_y+0,py_x+420,py_y+420);
		g2d.setStroke(new BasicStroke());
		
		//����������Χ������
		for(int i=0;i<15;i++){
			g2d.drawString((i+1)+"",30*i+py_x-3,py_y-10);
			g2d.drawString((i+1)+"",py_x-25,30*i+py_y+3);
		}
		
		for(int i=0;i<15;i++){
			//������
			//���(0,0) (0,1) (0,2) �յ�(14,0) (14,1)
			g2d.drawLine(py_x+0,py_y+i*30,py_x+14*30,py_y+i*30);
			//������
			g2d.drawLine(py_x+i*30,py_y+0,py_x+i*30,py_y+14*30);
			
		}
		//(3,3) (3,11) (11,3) (11,11) (7,7)����С�ڵ�
		g2d.fillOval(py_x+30*3-3,py_y+30*3-3,6,6);
		g2d.fillOval(py_x+30*3-3,py_y+30*11-3,6,6);
		g2d.fillOval(py_x+30*11-3,py_y+30*3-3,6,6);
		g2d.fillOval(py_x+30*11-3,py_y+30*11-3,6,6);
		g2d.fillOval(py_x+30*7-3,py_y+30*7-3,6,6);
	}
	
	
	/**
	 * ��������
	 * @param g
	 */
	public void paintQizi(Graphics g){
		for(int i=0;i<15;i++){
			for(int j=0;j<15;j++){
				if(qizi[i][j]==1){
					//��һ������
					g.drawImage(black.getImage(),30*i+py_x-10,30*j+py_y-10,20,20,this);
				}else if(qizi[i][j]==2){
					//��һ������
					g.drawImage(white.getImage(),30*i+py_x-10,30*j+py_y-10,20,20,this);
				}
			}
		}
	}

	/**
	 * ���Ӽ���
	 */
	public void addListener(){
		this.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {

				//��Ϸδ��ʼ
				if(!gameStart)return;
				//û���ֵ��Լ�����
				if(user.getQiziColor()!=whichColor)return;
				
				int x=e.getX();
				int y=e.getY();
			
				if(x>=0+py_x-10&&x<=420+py_x+10&&y>=0+py_y-10&&y<=420+py_y+10){
					
					x=(int)Math.round((x-py_x)/30.0);
					y=(int)Math.round((y-py_y)/30.0);
					
					if(qizi[x][y]!=0)return;
					qizi[x][y]=user.getQiziColor();
					repaint();
					changeQiziColor();
					showGameInfo();
					
					//���˶���
					if(gameState==1){
						//֪ͨ����������ǰ�û��������Ϣ
						GameData data=new GameData();
						data.setUser(user);
						data.setP(new Point(x,y));
						sendInfo2Server(data);
					}else if(gameState==2){
						//�˻�����
						computerCalNextStep();
					}
					
				}
				
			}
		});
		
		this.addMouseMotionListener(new MouseMotionAdapter() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				
				//��Ϸδ��ʼ
				if(!gameStart)return;
				
				int x=e.getX();
				int y=e.getY();
			
				if(x>=0+py_x-10&&x<=420+py_x+10&&y>=0+py_y-10&&y<=420+py_y+10){
					
					move_x=(int)Math.round((x-py_x)/30.0);
					move_y=(int)Math.round((y-py_y)/30.0);
					repaint();
				}
			}
			 
		});
	}
	
	/**
	 * ������������һ����
	 */
	public void computerCalNextStep(){
		//���õ����������[0,1)
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
	
	//���ӷ�����
	public void connectServer(){
		try {
			s=new Socket("127.0.0.1",8888);
			GameData d=new GameData();
			d.setUser(user);
			IOUtil.writeObject(d,s.getOutputStream());
			//���������ݵ��߳�
			new Thread(new GetInfoFromServer()).start();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//������������Ϣ
	public void sendInfo2Server(GameData data){
		try {
			IOUtil.writeObject(data,s.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * �ı�������ɫ
	 */
	public void changeQiziColor(){
		if(whichColor==1)whichColor=2;
		else if(whichColor==2)whichColor=1;
	}
	
	public void showGameInfo(){
		if(whichColor==1){
			Map.gameInfo.setText("�ֵ��ڷ�����");
		}else if(whichColor==2){
			Map.gameInfo.setText("�ֵ��׷�����");
		}
	}
	/**
	 * �¿�һ����
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
	
	//�ӷ����������ݵ��߳�
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