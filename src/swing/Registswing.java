package swing;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import dao.Userdao;
import pojo.QQUser;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpinnerListModel;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.ImageIcon;

public class Registswing {
	private JFrame lgJframe = new JFrame("myQQ 注册");
	private JTextField jtfnum;
	private JPasswordField jpword1;
	private JPasswordField jpword2;
	private JTextField jtfschool;
	private JTextField jtfadd;
	private ButtonGroup grp;
	private JRadioButton radioButtonman;
	private JRadioButton radioButtonwoman;
	private JTextField jtfage;
	private JSpinner spinneryear;
	private JSpinner spinnermon;
	private JSpinner spinnerday;
	private JSpinner spinnerxingzuo;
	private JSpinner spinnerblood;
	private QQUser user = new QQUser();;
	private Userdao userdao = new Userdao();
	private JLabel jlname;
	private JTextField jtfname;
	private JLabel lbl_bg;


	public Registswing() {
		lgJframe.setIconImage(Toolkit.getDefaultToolkit().getImage("imag\\tx.png"));

		lgJframe.setSize(621, 460);
		lgJframe.getContentPane().setLayout(null);

		JLabel jlnum = new JLabel("\u7528\u6237\u540D\uFF08\u5FC5\u586B\uFF09\uFF1A");
		jlnum.setBounds(10, 10, 172, 27);
		lgJframe.getContentPane().add(jlnum);

		JLabel jlpword1 = new JLabel("\u7528\u6237\u5BC6\u7801\uFF08\u5FC5\u586B\uFF09\uFF1A");
		jlpword1.setBounds(10, 47, 132, 27);
		lgJframe.getContentPane().add(jlpword1);

		JLabel jlpword2 = new JLabel("\u91CD\u590D\u5BC6\u7801\uFF1A");
		jlpword2.setBounds(351, 47, 73, 27);
		lgJframe.getContentPane().add(jlpword2);

		JLabel label_2 = new JLabel("\u6027\u522B\uFF1A");
		label_2.setBounds(10, 122, 73, 27);
		lgJframe.getContentPane().add(label_2);

		JLabel jlbirth = new JLabel("\u51FA\u751F\u5E74\u6708\u65E5\uFF1A");
		jlbirth.setBounds(10, 189, 73, 27);
		lgJframe.getContentPane().add(jlbirth);

		JLabel jlbld = new JLabel("\u8840\u578B\uFF1A");
		jlbld.setBounds(10, 226, 73, 27);
		lgJframe.getContentPane().add(jlbld);

		JLabel jlxingzuo = new JLabel("\u661F\u5EA7\uFF1A");
		jlxingzuo.setBounds(10, 263, 73, 27);
		lgJframe.getContentPane().add(jlxingzuo);

		JLabel jlschool = new JLabel("\u6BD5\u4E1A\u5B66\u6821\uFF1A");
		jlschool.setBounds(10, 300, 73, 27);
		lgJframe.getContentPane().add(jlschool);

		JLabel jladd = new JLabel("\u5730\u5740\uFF1A");
		jladd.setBounds(10, 337, 73, 27);
		lgJframe.getContentPane().add(jladd);

		JButton jbyes = new JButton("\u786E\u8BA4\u6CE8\u518C");
		jbyes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(setUser(user))
					Register(user);
			}
		});
		jbyes.setBounds(152, 385, 127, 37);
		lgJframe.getContentPane().add(jbyes);

		JButton jbno = new JButton("\u53D6\u6D88");
		jbno.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lgJframe.dispose();
			}
		});
		jbno.setBounds(289, 385, 151, 37);
		lgJframe.getContentPane().add(jbno);

		jtfnum = new JTextField();
		jtfnum.setToolTipText("\u8BF7\u8F93\u51658\u523012\u4F4D\u6574\u6570");
		jtfnum.setBounds(152, 10, 189, 24);
		lgJframe.getContentPane().add(jtfnum);
		jtfnum.setColumns(10);

		jpword1 = new JPasswordField();
		jpword1.setToolTipText("6\u523018\u4F4D\u5B57\u7B26");
		jpword1.setBounds(152, 49, 189, 24);
		lgJframe.getContentPane().add(jpword1);
		jpword1.setColumns(10);

		jpword2 = new JPasswordField();
		jpword2.setToolTipText("\u8BF7\u91CD\u590D\u4F60\u7684\u5BC6\u7801");
		jpword2.setBounds(416, 47, 189, 27);
		lgJframe.getContentPane().add(jpword2);
		jpword2.setColumns(10);

		grp = new ButtonGroup();

		radioButtonman = new JRadioButton("\u7537");
		radioButtonman.setSelected(true);
		radioButtonman.setBounds(152, 124, 66, 23);
		radioButtonman.setContentAreaFilled(false);// 去边框
		radioButtonman.setBorderPainted(false);// 去背景色
		lgJframe.getContentPane().add(radioButtonman);

		radioButtonwoman = new JRadioButton("\u5973");
		radioButtonwoman.setBounds(233, 124, 63, 23);
		radioButtonwoman.setContentAreaFilled(false);// 去边框
		radioButtonwoman.setBorderPainted(false);// 去背景色
		lgJframe.getContentPane().add(radioButtonwoman);
		grp.add(radioButtonwoman);
		grp.add(radioButtonman);

		jtfschool = new JTextField();
		jtfschool.setBounds(152, 297, 288, 27);
		lgJframe.getContentPane().add(jtfschool);
		jtfschool.setColumns(10);

		jtfadd = new JTextField();
		jtfadd.setBounds(152, 334, 288, 27);
		lgJframe.getContentPane().add(jtfadd);
		jtfadd.setColumns(10);

		JLabel jlage = new JLabel("\u5E74\u9F84\uFF1A");
		jlage.setBounds(10, 159, 73, 20);
		lgJframe.getContentPane().add(jlage);

		jtfage = new JTextField();
		jtfage.setText("1");
		jtfage.setBounds(152, 156, 66, 27);
		lgJframe.getContentPane().add(jtfage);
		jtfage.setColumns(10);

		jlname = new JLabel("\u6635\u79F0\uFF1A");
		jlname.setBounds(10, 84, 73, 28);
		lgJframe.getContentPane().add(jlname);

		jtfname = new JTextField();
		jtfname.setBounds(152, 84, 189, 24);
		lgJframe.getContentPane().add(jtfname);
		jtfname.setColumns(10);
		
		spinneryear = new JSpinner();
		spinneryear.setFont(new Font("新宋体", Font.PLAIN, 12));
		spinneryear.setModel(new SpinnerNumberModel(2017, 1800, 2500, 1));
		spinneryear.setBounds(152, 189, 68, 27);
		lgJframe.getContentPane().add(spinneryear);
		
		spinnermon = new JSpinner();
		spinnermon.setFont(new Font("新宋体", Font.PLAIN, 12));
		spinnermon.setModel(new SpinnerNumberModel(1, 1, 12, 1));
		spinnermon.setBounds(232, 189, 58, 27);
		lgJframe.getContentPane().add(spinnermon);
		
		spinnerday = new JSpinner();
		spinnerday.setFont(new Font("新宋体", Font.PLAIN, 12));
		spinnerday.setModel(new SpinnerNumberModel(1, 1, 31, 1));
		spinnerday.setBounds(300, 189, 58, 27);
		lgJframe.getContentPane().add(spinnerday);
		
		spinnerblood = new JSpinner();
		spinnerblood.setFont(new Font("新宋体", Font.PLAIN, 12));
		spinnerblood.setModel(new SpinnerListModel(new String[] {"A", "B", "AB", "O", "\u5176\u4ED6"}));
		spinnerblood.setBounds(152, 226, 66, 27);
		lgJframe.getContentPane().add(spinnerblood);
		
		spinnerxingzuo = new JSpinner();
		spinnerxingzuo.setModel(new SpinnerListModel(new String[] {"\u767D\u7F8A\u5EA7", "\u91D1\u725B\u5EA7", "\u53CC\u5B50\u5EA7", "\u5DE8\u87F9\u5EA7", "\u72EE\u5B50\u5EA7", "\u5904\u5973\u5EA7", "\u5929\u79E4\u5EA7", "\u5929\u874E\u5EA7", "\u5C04\u624B\u5EA7", "\u6469\u7FAF\u5EA7", "\u6C34\u74F6\u5EA7", "\u53CC\u9C7C\u5EA7"}));
		spinnerxingzuo.setBounds(152, 263, 66, 26);
		lgJframe.getContentPane().add(spinnerxingzuo);
		
		lbl_bg = new JLabel();
		lbl_bg.setIcon(new ImageIcon("imag\\04.jpg"));
		lbl_bg.setBounds(0, 0, 615, 432);
		lgJframe.getContentPane().add(lbl_bg);
		lgJframe.setLocationRelativeTo(null);
		lgJframe.setVisible(true);
		lgJframe.setResizable(false);
		lgJframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public boolean setUser(QQUser user) {
		int sex;
		if (radioButtonman.isSelected()) {// mam
			sex = 0;
		} else
			sex = 1;// woman
		user.setSex(sex);
		user.setNum(jtfnum.getText());
		user.setPassword(new String(jpword1.getPassword()));
		user.setName(jtfname.getText());
		user.setAge(new Integer(jtfage.getText()));
		user.setY((Integer)spinneryear.getValue());
		user.setM((Integer)spinnermon.getValue());
		user.setD((Integer)spinnerday.getValue());
		user.setBlood((String)spinnerblood.getValue());
		user.setAddress(jtfadd.getText());
		user.setSchool(jtfschool.getText());
		user.setXingzuo((String)spinnerxingzuo.getValue());
		if (user.getNum().equals("")) {
			JOptionPane.showMessageDialog(null, "用户名不能为空", "tips", JOptionPane.OK_OPTION);
			return false;
		} else if (user.getPassword().equals("")) {
			JOptionPane.showMessageDialog(null, "密码不能为空", "tips", JOptionPane.OK_OPTION);
			return false;
		} else if (user.getName().equals("")) {
			JOptionPane.showMessageDialog(null, "昵称不能为空", "tips", JOptionPane.OK_OPTION);
			return false;
		} else if (!(new String(jpword1.getPassword())).equals(new String(jpword2.getPassword()))) {
			JOptionPane.showMessageDialog(null, "密码不相同", "tips", JOptionPane.OK_OPTION);
			return false;
		}
		else return true;
	}

	public void Register(QQUser user) {
		boolean b = userdao.Regist(user);
		if (b) {
			JOptionPane.showMessageDialog(null, "注册成功,您的帐号是：" + user.getNum() + "\n昵称是：" + user.getName() + "！\n请前往登录",
					"tips", JOptionPane.OK_OPTION);
			lgJframe.dispose();
		} else {
			JOptionPane.showMessageDialog(null, "该账号不可用！", "tips", JOptionPane.WARNING_MESSAGE);
		}
	}
}
