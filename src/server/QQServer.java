package server;
import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Font;

public class QQServer {
	ServerSocket serv = null;
	Socket Ssoc = null;
	List<Client> clients = new ArrayList<Client>();
	private JFrame frame;
	private JLabel label;
	
	boolean started = false;
	public QQServer(){
		frame=new JFrame("服务器正在运行");
		frame.setSize(275, 206);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		label = new JLabel("\u8FD0\u884C\u4E2D");
		label.setText("正在运行");
		label.setFont(new Font("宋体", Font.BOLD, 40));
		frame.getContentPane().add(label, BorderLayout.CENTER);
		frame.setVisible(true);
	}
	public static void main(String[] args) {
		new QQServer().start();
	}
	public void start() {
		try {
			serv = new ServerSocket(6688);// 
			started = true;
			while (started) {
				Ssoc = serv.accept();
				Client c = new Client(Ssoc);
				clients.add(c);
				label.setText("峰值："+clients.size());
				new Thread(c).start();
			}
		} catch (BindException e) {
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private class Client implements Runnable {

		int num = -1;
		Socket Csoc = null;
		DataInputStream Input = null;
		DataOutputStream Output = null;
		boolean bConnected = false;

		Client(Socket s) {
			Csoc = s;
			num ++;
			bConnected = true;
			try {
				Input = new DataInputStream(s.getInputStream());
				Output = new DataOutputStream(s.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void send(String str) {
			if(Csoc!=null)
				try {
					Output.writeUTF(str);
				} catch (IOException e) {
					//e.printStackTrace();
					clients.remove(this);
				}
		}
		
		public void run() {
			try {
				while (bConnected) {//、、、、、、、、、
					//String str = Csoc.getPort() + ": " + Input.readUTF();
					String str=Input.readUTF();
					for(int i=0; i<clients.size(); i++) {
						clients.get(i).send(str);
					}
				}
			} catch (EOFException e) {
			} catch (IOException e) {
				//e.printStackTrace();
			} finally {
				try {
					if (Input!=null) Input.close();
					if(Output!=null) Output.close();
					if (Csoc!=null) Csoc.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	
	}
}
