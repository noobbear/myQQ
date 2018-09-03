package swing;

import dao.Userdao;
import pojo.QQUser;
import java.awt.event.*;

import javax.swing.*;
import java.awt.Font;
import java.awt.Color;

public class Loginswing {
	private JFrame jframe = new JFrame();
	private JTextField jtfnum = new JTextField();
	private JPasswordField jtfpw = new JPasswordField();
	private JButton login = new JButton("��¼");
	private JButton register = new JButton("ע���ʺ�");
	private JButton foget = new JButton("�һ�����");
	private ImageIcon bg = new ImageIcon("imag/myQQ.png");
	private ImageIcon tx = new ImageIcon("imag/tx.png");
	private JLabel jlnum = new JLabel("�ʺ�");
	private JLabel jlpassw = new JLabel("����");
	private JLabel jpbg;
	private QQUser user = null;
	private Userdao userdao = new Userdao();

	public Loginswing() {

		jframe.getContentPane().setBackground(new Color(255, 250, 250));
		jframe.getContentPane().setForeground(new Color(255, 250, 250));
		jframe.getContentPane().setFont(new Font("����", Font.BOLD, 15));
		jframe.setTitle("myQQ--��¼");
		jframe.setIconImage(tx.getImage());
		jframe.setSize(378, 293);
		jframe.getContentPane().setLayout(null);
		jframe.setLocationRelativeTo(null);
		
		jtfnum.setFont(new Font("����", Font.BOLD, 12));
		jtfnum.setBounds(89, 51, 171, 30);// �ʺ������
		
		jtfpw.setFont(new Font("����", Font.BOLD, 12));
		jtfpw.setBounds(89, 115, 171, 30);// ���������
		

		register.setContentAreaFilled(false);// ȥ�߿�
		register.setBorderPainted(false);// ȥ����ɫ
		
		foget.setContentAreaFilled(false);// ȥ�߿�
		foget.setBorderPainted(false);
		
		foget.setFont(new Font("SansSerif", Font.BOLD, 13));
		foget.setBounds(260, 113, 100, 35);// �һ����밴ť
		foget.setContentAreaFilled(false);// ȥ�߿�
		foget.setBorderPainted(false);
		
		register.setFont(new Font("SansSerif", Font.BOLD, 13));
		register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Registswing();
			}
		});
		register.setBounds(260, 49, 100, 35);// ע���ʺŰ�ť

		foget.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Findpwdswing();
			}
		});

		
		jlnum.setFont(new Font("����", Font.BOLD, 12));
		jlnum.setBounds(48, 52, 42, 26);// �ʺ���ʾ��ǩ
		jlpassw.setFont(new Font("����", Font.BOLD, 12));
		jlpassw.setBounds(48, 116, 42, 26);// ������ʾ��ǩ
		
		jframe.getContentPane().add(jtfnum);
		jframe.getContentPane().add(jtfpw);
		
		login.setBackground(new Color(100, 149, 237));
		login.setForeground(new Color(0, 0, 255));
		login.setFont(new Font("����", Font.BOLD, 16));
		login.setBounds(89, 187, 181, 30);// ��¼��ť
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginCheck(jtfnum.getText(), new String(jtfpw.getPassword()));// jtfpw.getPassword().toString()���󣡣���
			}
		});
		jframe.getRootPane().setDefaultButton(login);// ���ûس���Ĭ���ǵ�¼
		jframe.getContentPane().add(login);
		jframe.getContentPane().add(foget);
		jframe.getContentPane().add(register);
		jframe.getContentPane().add(jlnum);
		jframe.getContentPane().add(jlpassw);
		
		jpbg = new JLabel();
		jpbg.setIcon(bg);
		jpbg.setBounds(0, 0, 372, 268);
		
		jframe.getContentPane().add(jpbg);
		jtfpw.setVisible(true);
		jframe.setVisible(true);
		jframe.setResizable(false);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

	public void LoginCheck(String num, String pwd) {
		if (num.equals("") || num == null) {
			JOptionPane.showMessageDialog(null, "�û���Ϊ��", "tips", JOptionPane.WARNING_MESSAGE);
		} else if (pwd.equals("") || num == null) {
			JOptionPane.showMessageDialog(null, "����Ϊ��", "tips", JOptionPane.WARNING_MESSAGE);
		} else {
			user = userdao.login(num, pwd);//��¼�ɹ����Զ���Ϊ����״̬
			if (user != null) {
				jframe.dispose();
				userdao.UpdateOnline(user.getId(), true);
				new Friendswing(user);
				
			} else {
				JOptionPane.showMessageDialog(null, "�ʺŻ��������", "������Ϣ", JOptionPane.WARNING_MESSAGE);
				jtfpw.setText("");
			}
		}
	}
}