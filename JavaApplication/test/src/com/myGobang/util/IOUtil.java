package com.myGobang.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class IOUtil {

	
	/**
	 * 写对象
	 * @param obj
	 * @param os
	 */
	public static void writeObject(Object obj,OutputStream os){
		try {
			ObjectOutputStream oos=new ObjectOutputStream(os);
			oos.writeObject(obj);
			oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 读对象
	 * @param is
	 * @return
	 */
	public static Object readObject(InputStream is){
		Object obj=null;
		try {
			ObjectInputStream ois=new ObjectInputStream(is);
			obj=ois.readObject();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}
}