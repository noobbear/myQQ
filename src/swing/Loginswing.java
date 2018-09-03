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
	private JButton login = new JButton("登录");
	private JButton register = new JButton("注册帐号");
	private JButton foget = new JButton("找回密码");
	private ImageIcon bg = new ImageIcon("imag/myQQ.png");
	private ImageIcon tx = new ImageIcon("imag/tx.png");
	private JLabel jlnum = new JLabel("帐号");
	private JLabel jlpassw = new JLabel("密码");
	private JLabel jpbg;
	private QQUser user = null;
	private Userdao userdao = new Userdao();

	public Loginswing() {

		jframe.getContentPane().setBackground(new Color(255, 250, 250));
		jframe.getContentPane().setForeground(new Color(255, 250, 250));
		jframe.getContentPane().setFont(new Font("宋体", Font.BOLD, 15));
		jframe.setTitle("myQQ--登录");
		jframe.setIconImage(tx.getImage());
		jframe.setSize(378, 293);
		jframe.getContentPane().setLayout(null);
		jframe.setLocationRelativeTo(null);
		
		jtfnum.setFont(new Font("宋体", Font.BOLD, 12));
		jtfnum.setBounds(89, 51, 171, 30);// 帐号输入框
		
		jtfpw.setFont(new Font("宋体", Font.BOLD, 12));
		jtfpw.setBounds(89, 115, 171, 30);// 密码输入框
		

		register.setContentAreaFilled(false);// 去边框
		register.setBorderPainted(false);// 去背景色
		
		foget.setContentAreaFilled(false);// 去边框
		foget.setBorderPainted(false);
		
		foget.setFont(new Font("SansSerif", Font.BOLD, 13));
		foget.setBounds(260, 113, 100, 35);// 找回密码按钮
		foget.setContentAreaFilled(false);// 去边框
		foget.setBorderPainted(false);
		
		register.setFont(new Font("SansSerif", Font.BOLD, 13));
		register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Registswing();
			}
		});
		register.setBounds(260, 49, 100, 35);// 注册帐号按钮

		foget.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Findpwdswing();
			}
		});

		
		jlnum.setFont(new Font("宋体", Font.BOLD, 12));
		jlnum.setBounds(48, 52, 42, 26);// 帐号提示标签
		jlpassw.setFont(new Font("宋体", Font.BOLD, 12));
		jlpassw.setBounds(48, 116, 42, 26);// 密码提示标签
		
		jframe.getContentPane().add(jtfnum);
		jframe.getContentPane().add(jtfpw);
		
		login.setBackground(new Color(100, 149, 237));
		login.setForeground(new Color(0, 0, 255));
		login.setFont(new Font("宋体", Font.BOLD, 16));
		login.setBounds(89, 187, 181, 30);// 登录按钮
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginCheck(jtfnum.getText(), new String(jtfpw.getPassword()));// jtfpw.getPassword().toString()错误！！！
			}
		});
		jframe.getRootPane().setDefaultButton(login);// 设置回车键默认是登录
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
			JOptionPane.showMessageDialog(null, "用户名为空", "tips", JOptionPane.WARNING_MESSAGE);
		} else if (pwd.equals("") || num == null) {
			JOptionPane.showMessageDialog(null, "密码为空", "tips", JOptionPane.WARNING_MESSAGE);
		} else {
			user = userdao.login(num, pwd);//登录成功会自动设为在线状态
			if (user != null) {
				jframe.dispose();
				userdao.UpdateOnline(user.getId(), true);
				new Friendswing(user);
				
			} else {
				JOptionPane.showMessageDialog(null, "帐号或密码错误！", "错误信息", JOptionPane.WARNING_MESSAGE);
				jtfpw.setText("");
			}
		}
	}
}