package swing;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import pojo.QQUser;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import java.awt.Toolkit;

@SuppressWarnings("serial")
public class Chatswing extends JFrame implements Runnable {

	private JPanel contentPane, pan1, pan2;
	private JTextArea textArea;
	private JTextField jtfchat;
	private JScrollPane jscrollpane;
	private JButton send = new JButton("send");
	private boolean Connected = false;
	private DataInputStream input;
	private DataOutputStream output;
	private Socket socket = null;
	private JPopupMenu popupMenu;
	private JMenuItem menu_Del;
	private JMenuItem menu_expo;
	private JMenuItem menu_Im;
	private QQUser friend,myself;
	private JFileChooser fileChooser = new JFileChooser();
	private FileNameExtensionFilter filter = new FileNameExtensionFilter("默认文件(*.dat)", "dat"); 
	private File file=null;
	public Chatswing(QQUser friend,QQUser myself) {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage("imag\\tx.png"));
		this.friend=friend;
		this.myself=myself;
		
		////////////发送按钮 监听器
		send.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (Connected) {
					if (jtfchat.getText().equals(""))
						JOptionPane.showMessageDialog(null, "发送内容不能为空，请重新输入！", "提示", JOptionPane.INFORMATION_MESSAGE);
					else {
						try {
						Date date=new Date();
						String string=null;
						DateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");  
						string = sdf.format(date); 
						textArea.append(string+"\n");
						textArea.append(myself.getName()+"："+jtfchat.getText() + "\n");
						textArea.setCaretPosition(textArea.getText().length());
						//发送的信息格式：接收对象的ID+:+发送方的昵称(发送方的帐号)+消息内容
						output.writeUTF(friend.getId()+":"+myself.getName()+"("+myself.getNum()+")："+jtfchat.getText());
							jtfchat.setText("");
						} catch (IOException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
					}
				} 
			}
		});
		getRootPane().setDefaultButton(send);// 设置回车键默认是send
		
		//重写窗口关闭函数
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				disConnect();
				dispose();
			}
		});
		
		setTitle("正在与 "+friend.getName()+" 聊天");
		setBounds(100, 100, 508, 343);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout());
		setContentPane(contentPane);

		pan1 = new JPanel();
		pan1.setBounds(10, 10, 414, 211);
		pan1.setLayout(new GridLayout());
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		jscrollpane = new JScrollPane(textArea);
		
		popupMenu = new JPopupMenu();
		addPopup(textArea, popupMenu);
		
		menu_Del = new JMenuItem("\u6E05\u7A7A\u6D88\u606F\u6846");
		menu_Del.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textArea.setText("");
			}
		});
		popupMenu.add(menu_Del);
		
		menu_expo = new JMenuItem("\u4FDD\u5B58\u5230\u672C\u5730");
		menu_expo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SaveFile();
			}
		});
		popupMenu.add(menu_expo);
		
		menu_Im = new JMenuItem("\u5BFC\u5165\u672C\u5730\u6D88\u606F");
		menu_Im.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				OpenFile();
			}
		});
		popupMenu.add(menu_Im);
		pan1.add(jscrollpane);
		contentPane.add(pan1, BorderLayout.CENTER);
		
		pan2 = new JPanel();
		pan2.setBounds(10, 223, 414, 39);
		pan2.setLayout(new BorderLayout());
		jtfchat = new JTextField();
		jtfchat.setSize(100, 20);
		send.setSize(40, 20);
		pan2.add(jtfchat, BorderLayout.CENTER);
		pan2.add(send, BorderLayout.EAST);
		contentPane.add(pan2, BorderLayout.SOUTH);


		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//关闭窗口操作默认为销毁当前窗口
		setVisible(true);

	}

	//////////////////// 自动接收信息
	public void run() {
		try {
			socket = new Socket("localhost", 6688);
			Connected = true;
			System.out.println("已自动连接");
			output = new DataOutputStream(socket.getOutputStream());
			input = new DataInputStream(socket.getInputStream());
			Date date=null;
			String string=null;
			DateFormat sdf = null;
			String myid = myself.getId()+"";//把自己的ID转为字符串格式
			String message=null;
			while ((message = input.readUTF()) != null) { 					// 循环获取消息
				if (Connected) {			// 仅当已连接服务器时
					//发送的信息格式：接收对象的ID+:+发送方的昵称(发送方的帐号)+消息内容
					//message = input.readUTF();//保存消息

					String Forwhom = message.substring(0, message.indexOf(':'));//保存信息的接收对象的ID
					//String Fromwhoname =message.substring(message.indexOf(':')+1, message.indexOf('('));//保存发送方的昵称
					String Fromwhonum = message.substring(message.indexOf('(')+1, message.indexOf(')'));//保存消息发送方的帐号

					if(myid.equals(Forwhom)){			//检验自己是否为信息的接收对象
						
						if(friend.getNum().equals(Fromwhonum)){//确定自己的聊天对象是否=信息发送方
							date=new Date();
							sdf = new SimpleDateFormat("MM-dd HH:mm"); 
							string = sdf.format(date);
							textArea.append(string+"\n");
							textArea.append(message.substring(message.indexOf(':')+1)+"\n");
							textArea.setCaretPosition(textArea.getText().length());
						}
					}
					
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Connected = false;
			textArea.append("你已断开连接\r\n");
		}
	}

	///////////////// 连接服务器
	public void tryConnect() {
		try {
			//socket = new Socket("localhost", 6688);
			socket = new Socket("localhost", 6688);
			Connected = true;
			System.out.println("tryConnect 连接");
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	} 
	//********************************保存聊天记录
	public void SaveFile(){ 
		fileChooser.setFileFilter(filter); 
		int choose = fileChooser.showSaveDialog(this);
		if (choose == JFileChooser.APPROVE_OPTION) {// 选择了一个文件，保存到文件
			try {
				file = fileChooser.getSelectedFile();
				String name=fileChooser.getName(file);
				if(name.indexOf(".dat")==-1){
					file=new File(fileChooser.getCurrentDirectory(),name+".dat");
				}
				BufferedWriter writer = new BufferedWriter(new FileWriter(file));
				writer.write(textArea.getText());
				writer.flush();
				writer.close();
				JOptionPane.showMessageDialog(null, "保存成功");
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "保存失败");
			} 
		}
	}
	//********************************打开聊天记录
		public void OpenFile(){
			fileChooser.setFileFilter(filter); 
			StringBuffer str = new StringBuffer();
			int choose = fileChooser.showOpenDialog(this);
			if (choose == JFileChooser.APPROVE_OPTION) {// 选择了一个文件，保存到文件
				try {
					file = fileChooser.getSelectedFile();
					BufferedReader reader = new BufferedReader(new FileReader(file));
					String s;
//					if ((s = reader.readLine()) != null)
//						str.append(s);
					while ((s = reader.readLine()) != null) {
						str.append(s+"\n");
					}
					textArea.setText(str.toString());
					reader.close();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "保存失败");
				} 
			}
		}
	///////////// 断开连接
	public void disConnect() {
		try {
			input.close();
			output.close();
			socket.close();
			socket = null;
			Connected=false;
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	///////////////////////
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
