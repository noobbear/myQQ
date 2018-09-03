package swing;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import pojo.QQUser;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.SpinnerNumberModel;

import dao.Userdao;

import javax.swing.SpinnerListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class UserInformationswing {
	private JFrame jframe = new JFrame();
	private JTextField txtName;
	private JTextField textField_Addr;
	private JTextField textField_Sch;
	private JTextField text_age;
	private JButton btn_Save, button_reset;
	private JLabel labID, labnum, label_addr, label_sch, label_xz, label_age, label_blood, label_sex, labname;
	private JSpinner spinner_Xz, spinner_blood, spinner_Y, spinner_M, spinner_D;
	private JRadioButton radioButton_man, radioButton_woman;
	private ButtonGroup buttonGroup = new ButtonGroup();
	private JLabel lab_Num, lblNewLabel_bg;
	private ImageIcon bg = new ImageIcon("imag\\flower.JPG");
	private JButton button_Rspwd;

	public UserInformationswing(QQUser user,JFrame P_frame,JLabel lab_num,JLabel lab_name,boolean ifmyself) {
		jframe.getContentPane().setBackground(new Color(255, 255, 255));
		jframe.setResizable(false);

		jframe.setTitle("个人信息");
		jframe.setIconImage(Toolkit.getDefaultToolkit().getImage("imag\\tx.png"));
		jframe.getContentPane().setLayout(null);

		labID = new JLabel();
		labID.setText("ID："+user.getId());
		labID.setFont(new Font("叶根友毛笔行书2.0版", Font.BOLD, 22));
		labID.setBounds(10, 10, 124, 33);
		jframe.getContentPane().add(labID);

		labnum = new JLabel(user.getNum());
		labnum.setFont(new Font("新宋体", Font.PLAIN, 15));
		labnum.setBounds(201, 14, 120, 33);
		jframe.getContentPane().add(labnum);

		txtName = new JTextField(user.getName());
		txtName.setFont(new Font("新宋体", Font.PLAIN, 13));
		txtName.setEditable(false);
		txtName.setText(user.getName());
		txtName.setBounds(379, 10, 149, 33);
		jframe.getContentPane().add(txtName);
		txtName.setColumns(10);

		textField_Addr = new JTextField(user.getAddress());
		textField_Addr.setEditable(false);
		textField_Addr.setColumns(10);
		textField_Addr.setBounds(144, 245, 338, 27);
		jframe.getContentPane().add(textField_Addr);

		textField_Sch = new JTextField(user.getSchool());
		textField_Sch.setEditable(false);
		textField_Sch.setColumns(10);
		textField_Sch.setBounds(144, 215, 338, 27);
		jframe.getContentPane().add(textField_Sch);

		label_addr = new JLabel("\u5730\u5740\uFF1A");
		label_addr.setBounds(10, 245, 73, 27);
		jframe.getContentPane().add(label_addr);

		label_sch = new JLabel("\u6BD5\u4E1A\u5B66\u6821\uFF1A");
		label_sch.setBounds(10, 215, 73, 27);
		jframe.getContentPane().add(label_sch);

		label_xz = new JLabel("\u661F\u5EA7\uFF1A");
		label_xz.setBounds(10, 185, 73, 27);
		jframe.getContentPane().add(label_xz);

		spinner_Xz = new JSpinner();
		spinner_Xz.setFont(new Font("新宋体", Font.BOLD, 13));
		spinner_Xz.setEnabled(false);
		spinner_Xz.setModel(new SpinnerListModel(
				new String[] { "\u767D\u7F8A\u5EA7", "\u91D1\u725B\u5EA7", "\u53CC\u5B50\u5EA7", "\u5DE8\u87F9\u5EA7",
						"\u72EE\u5B50\u5EA7", "\u5904\u5973\u5EA7", "\u5929\u79E4\u5EA7", "\u5929\u874E\u5EA7",
						"\u5C04\u624B\u5EA7", "\u6469\u7FAF\u5EA7", "\u6C34\u74F6\u5EA7", "\u53CC\u9C7C\u5EA7" }));
		spinner_Xz.setBounds(144, 185, 68, 26);
		spinner_Xz.setValue(user.getXingzuo());
		jframe.getContentPane().add(spinner_Xz);

		spinner_blood = new JSpinner();
		spinner_blood.setEnabled(false);
		spinner_blood.setModel(new SpinnerListModel(new String[] { "A", "B", "AB", "O", "\u5176\u4ED6" }));
		spinner_blood.setValue(user.getBlood());// setSelection(1, true);选中1
		spinner_blood.setFont(new Font("新宋体", Font.BOLD, 13));
		spinner_blood.setBounds(144, 155, 68, 27);
		jframe.getContentPane().add(spinner_blood);

		label_blood = new JLabel("\u8840\u578B\uFF1A");
		label_blood.setBounds(10, 155, 73, 27);
		jframe.getContentPane().add(label_blood);

		JLabel label_birth = new JLabel("\u51FA\u751F\u5E74\u6708\u65E5\uFF1A");
		label_birth.setBounds(10, 125, 78, 27);
		jframe.getContentPane().add(label_birth);

		spinner_Y = new JSpinner();
		spinner_Y.setEnabled(false);
		spinner_Y.setModel(new SpinnerNumberModel(user.getY(), 1840, 2500, 1));
		spinner_Y.setFont(new Font("新宋体", Font.BOLD, 13));
		spinner_Y.setBounds(144, 125, 68, 27);
		jframe.getContentPane().add(spinner_Y);

		spinner_M = new JSpinner();
		spinner_M.setEnabled(false);
		spinner_M.setModel(new SpinnerNumberModel(user.getM(), 1, 12, 1));
		spinner_M.setFont(new Font("新宋体", Font.BOLD, 13));
		spinner_M.setBounds(222, 125, 68, 27);
		jframe.getContentPane().add(spinner_M);

		spinner_D = new JSpinner();
		spinner_D.setEnabled(false);
		spinner_D.setModel(new SpinnerNumberModel(user.getD(), 1, 31, 1));
		spinner_D.setFont(new Font("新宋体", Font.BOLD, 13));
		spinner_D.setBounds(300, 125, 68, 27);
		jframe.getContentPane().add(spinner_D);

		text_age = new JTextField();
		text_age.setEditable(false);
		text_age.setText("" + user.getAge());
		text_age.setColumns(10);
		text_age.setBounds(144, 95, 66, 27);
		jframe.getContentPane().add(text_age);

		label_age = new JLabel("\u5E74\u9F84\uFF1A");
		label_age.setBounds(10, 95, 73, 27);
		jframe.getContentPane().add(label_age);

		label_sex = new JLabel("\u6027\u522B\uFF1A");
		label_sex.setBounds(10, 65, 73, 27);
		jframe.getContentPane().add(label_sex);

		radioButton_man = new JRadioButton("\u7537");
		radioButton_man.setSelected(user.getSex() == 0);
		radioButton_man.setEnabled(false);
		buttonGroup.add(radioButton_man);
		radioButton_man.setBounds(144, 65, 66, 27);
		jframe.getContentPane().add(radioButton_man);

		radioButton_woman = new JRadioButton("\u5973");
		buttonGroup.add(radioButton_woman);
		radioButton_woman.setBounds(224, 65, 66, 27);
		radioButton_woman.setSelected(user.getSex() == 1);
		radioButton_woman.setEnabled(false);
		jframe.getContentPane().add(radioButton_woman);

		labname = new JLabel("\u6635\u79F0\uFF1A");
		labname.setFont(new Font("新宋体", Font.PLAIN, 14));
		labname.setBounds(320, 10, 49, 33);
		jframe.getContentPane().add(labname);

		button_reset = new JButton("\u4FEE\u6539");
		button_reset.setVisible(ifmyself);
		button_reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 修改属性
				Set_anti();
			}
		});
		button_reset.setBounds(435, 327, 93, 23);
		jframe.getContentPane().add(button_reset);

		btn_Save = new JButton("\u4FDD\u5B58");
		btn_Save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// 保存并 修改数据表
				if(Res_user(user))
					{
					P_frame.setTitle(user.getName()+"   "+user.getNum());//修改窗口标题
					lab_name.setText("昵称："+user.getName());		
					lab_name.setToolTipText(lab_name.getText());
					lab_num.setText("帐号："+user.getNum());
					lab_num.setToolTipText(lab_num.getText());
					}
				Set_anti();
			}
		});
		btn_Save.setEnabled(false);
		btn_Save.setVisible(false);
		btn_Save.setBounds(332, 327, 93, 23);

		jframe.getContentPane().add(btn_Save);

		lab_Num = new JLabel("帐号：");
		lab_Num.setFont(new Font("新宋体", Font.PLAIN, 13));
		lab_Num.setBounds(144, 14, 47, 33);
		jframe.getContentPane().add(lab_Num);
		
		button_Rspwd = new JButton("\u5BC6\u7801\u8BBE\u7F6E");
		button_Rspwd.setVisible(ifmyself);
		button_Rspwd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Findpwdswing();
			}
		});
		button_Rspwd.setBounds(10, 327, 93, 23);
		jframe.getContentPane().add(button_Rspwd);

		lblNewLabel_bg = new JLabel();
		lblNewLabel_bg.setIcon(bg);
		lblNewLabel_bg.setBounds(0, 0, 538, 360);
		jframe.setSize(543, 389);
		jframe.getContentPane().add(lblNewLabel_bg);

		jframe.getContentPane().add(lblNewLabel_bg);

		jframe.setLocationRelativeTo(null);
		jframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jframe.setVisible(true);
	}

	public void Set_anti() {
		if (txtName.isEditable())// 名字
			txtName.setEditable(false);
		else
			txtName.setEditable(true);

		if (text_age.isEditable())// 年龄
			text_age.setEditable(false);
		else
			text_age.setEditable(true);

		if (spinner_Y.isEnabled()) // 年
			spinner_Y.setEnabled(false);
		else
			spinner_Y.setEnabled(true);

		if (spinner_M.isEnabled()) // 月
			spinner_M.setEnabled(false);
		else
			spinner_M.setEnabled(true);

		if (spinner_D.isEnabled()) // 日
			spinner_D.setEnabled(false);
		else
			spinner_D.setEnabled(true);

		if (spinner_Xz.isEnabled())// 星座
			spinner_Xz.setEnabled(false);
		else
			spinner_Xz.setEnabled(true);

		if (spinner_blood.isEnabled())// 血型
			spinner_blood.setEnabled(false);
		else
			spinner_blood.setEnabled(true);

		if (textField_Sch.isEditable())// 学校
			textField_Sch.setEditable(false);
		else
			textField_Sch.setEditable(true);

		if (textField_Addr.isEditable())// 地址
			textField_Addr.setEditable(false);
		else
			textField_Addr.setEditable(true);

		if (radioButton_man.isEnabled() || radioButton_woman.isEnabled()) {// 性别编辑时
			radioButton_man.setEnabled(false);
			radioButton_woman.setEnabled(false);
		} else {// 都设为可见以便编辑
			radioButton_man.setEnabled(true);
			radioButton_woman.setEnabled(true);
		}

		if (btn_Save.isVisible()) {
			btn_Save.setEnabled(false);
			btn_Save.setVisible(false);
		} else {
			btn_Save.setEnabled(true);
			btn_Save.setVisible(true);
		}

		if (button_reset.isVisible())
			button_reset.setVisible(false);
		else
			button_reset.setVisible(true);

	}

	public boolean Res_user(QQUser OLDuser) {	
		QQUser	user=new QQUser();//新建一个临时user
		user=OLDuser;
		user.setName(txtName.getText());
		int sex;
		if (radioButton_man.isSelected()) {// mam
			sex = 0;
		} else
			sex = 1;// woman
		user.setSex(sex);
		user.setAge(new Integer(text_age.getText()));
		user.setY((Integer)spinner_Y.getValue());
		user.setM((Integer)spinner_M.getValue());
		user.setD((Integer)spinner_D.getValue());
		user.setBlood((String)spinner_blood.getValue());
		user.setXingzuo((String)spinner_Xz.getValue());
		user.setAddress(textField_Addr.getText());
		user.setSchool(textField_Sch.getText());
		Userdao userdao=new Userdao();
		boolean bl=userdao.UpdateUser(user);
		if(bl){
			{
				JOptionPane.showMessageDialog(null, "修改成功", "tips", JOptionPane.INFORMATION_MESSAGE);
				OLDuser=user;
			}
		}
		else 
			JOptionPane.showMessageDialog(null, "修改失败", "tips", JOptionPane.WARNING_MESSAGE);
		return bl;
	}
	
	
}
