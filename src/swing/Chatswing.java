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
	private FileNameExtensionFilter filter = new FileNameExtensionFilter("Ĭ���ļ�(*.dat)", "dat"); 
	private File file=null;
	public Chatswing(QQUser friend,QQUser myself) {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage("imag\\tx.png"));
		this.friend=friend;
		this.myself=myself;
		
		////////////���Ͱ�ť ������
		send.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (Connected) {
					if (jtfchat.getText().equals(""))
						JOptionPane.showMessageDialog(null, "�������ݲ���Ϊ�գ����������룡", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
					else {
						try {
						Date date=new Date();
						String string=null;
						DateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");  
						string = sdf.format(date); 
						textArea.append(string+"\n");
						textArea.append(myself.getName()+"��"+jtfchat.getText() + "\n");
						textArea.setCaretPosition(textArea.getText().length());
						//���͵���Ϣ��ʽ�����ն����ID+:+���ͷ����ǳ�(���ͷ����ʺ�)+��Ϣ����
						output.writeUTF(friend.getId()+":"+myself.getName()+"("+myself.getNum()+")��"+jtfchat.getText());
							jtfchat.setText("");
						} catch (IOException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
					}
				} 
			}
		});
		getRootPane().setDefaultButton(send);// ���ûس���Ĭ����send
		
		//��д���ڹرպ���
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				disConnect();
				dispose();
			}
		});
		
		setTitle("������ "+friend.getName()+" ����");
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


		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//�رմ��ڲ���Ĭ��Ϊ���ٵ�ǰ����
		setVisible(true);

	}

	//////////////////// �Զ�������Ϣ
	public void run() {
		try {
			socket = new Socket("localhost", 6688);
			Connected = true;
			System.out.println("���Զ�����");
			output = new DataOutputStream(socket.getOutputStream());
			input = new DataInputStream(socket.getInputStream());
			Date date=null;
			String string=null;
			DateFormat sdf = null;
			String myid = myself.getId()+"";//���Լ���IDתΪ�ַ�����ʽ
			String message=null;
			while ((message = input.readUTF()) != null) { 					// ѭ����ȡ��Ϣ
				if (Connected) {			// ���������ӷ�����ʱ
					//���͵���Ϣ��ʽ�����ն����ID+:+���ͷ����ǳ�(���ͷ����ʺ�)+��Ϣ����
					//message = input.readUTF();//������Ϣ

					String Forwhom = message.substring(0, message.indexOf(':'));//������Ϣ�Ľ��ն����ID
					//String Fromwhoname =message.substring(message.indexOf(':')+1, message.indexOf('('));//���淢�ͷ����ǳ�
					String Fromwhonum = message.substring(message.indexOf('(')+1, message.indexOf(')'));//������Ϣ���ͷ����ʺ�

					if(myid.equals(Forwhom)){			//�����Լ��Ƿ�Ϊ��Ϣ�Ľ��ն���
						
						if(friend.getNum().equals(Fromwhonum)){//ȷ���Լ�����������Ƿ�=��Ϣ���ͷ�
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
			textArea.append("���ѶϿ�����\r\n");
		}
	}

	///////////////// ���ӷ�����
	public void tryConnect() {
		try {
			//socket = new Socket("localhost", 6688);
			socket = new Socket("localhost", 6688);
			Connected = true;
			System.out.println("tryConnect ����");
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	} 
	//********************************���������¼
	public void SaveFile(){ 
		fileChooser.setFileFilter(filter); 
		int choose = fileChooser.showSaveDialog(this);
		if (choose == JFileChooser.APPROVE_OPTION) {// ѡ����һ���ļ������浽�ļ�
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
				JOptionPane.showMessageDialog(null, "����ɹ�");
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "����ʧ��");
			} 
		}
	}
	//********************************�������¼
		public void OpenFile(){
			fileChooser.setFileFilter(filter); 
			StringBuffer str = new StringBuffer();
			int choose = fileChooser.showOpenDialog(this);
			if (choose == JFileChooser.APPROVE_OPTION) {// ѡ����һ���ļ������浽�ļ�
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
					JOptionPane.showMessageDialog(null, "����ʧ��");
				} 
			}
		}
	///////////// �Ͽ�����
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
