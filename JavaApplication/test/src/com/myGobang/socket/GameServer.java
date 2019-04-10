package com.myGobang.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.myGobang.util.IOUtil;

import myGobang.bean.GameData;
import myGobang.bean.User;



public class GameServer {

	private ServerSocket server;
	private Map<User,Socket> players;
	
	public GameServer(){
		
		players=new HashMap<User,Socket>();
		try {
			server=new ServerSocket(8888);
			System.out.println("服务器启动...");
			while(true){
				System.out.println("服务器等待客户端连接...");
				Socket s=server.accept();
				GameData data=(GameData)
						IOUtil.readObject(s.getInputStream());
				players.put(data.getUser(),s);
				System.out.println("用户："+
				data.getUser().getUserName()+"上线了...");
				new Thread(new GameServerThread(data.getUser(),s)).start();
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	class GameServerThread implements Runnable{
		
		private User user;//当前玩家
		private Socket s;
		
		public GameServerThread(User user,Socket s){
			this.user=user;
			this.s=s;
		}

		@Override
		public void run() {
			while(true){
				try {
					GameData data=(GameData)
							IOUtil.readObject(s.getInputStream());
					
					Set<User> users=players.keySet();
					for(User temp:users){
						if(temp.equals(user))continue;
						IOUtil.writeObject(data,players.get(temp).getOutputStream());
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) {
		new GameServer();
	}
}
