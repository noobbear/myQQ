package swing;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JTextField;

import dao.Userdao;
import pojo.QQUser;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class Like_Searchdswing {

	
	private JFrame frame=new JFrame("");
	private JPanel panel_1;
	private JTextField textField_num;
	private JTextField textField_Name;
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public Like_Searchdswing(QQUser user){
		frame.getContentPane().setLayout(null);
		frame.setTitle("\u6A21\u7CCA\u67E5\u627E");
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("imag\\tx.png"));
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(500, 150);
		
		panel_1 = new JPanel();
		panel_1.setOpaque(false);
		panel_1.setBounds(10, 10, 465, 92);
		frame.getContentPane().add(panel_1, new GridLayout(5,5));
		panel_1.setLayout(null);
		
		textField_num = new JTextField();
		textField_num.setToolTipText("\u6BD4\u5982\u8F93\u5165\u201C1\u201D\uFF0C\u4E3A\u7A7A\u65F6\u8F93\u51FA\u6240\u6709\u7528\u6237");
		textField_num.setBounds(44, 10, 222, 32);
		panel_1.add(textField_num);
		textField_num.setColumns(10);
		
		textField_Name = new JTextField();
		textField_Name.setToolTipText("\u6BD4\u5982\u8F93\u5165\u201C\u5C0F\u201D\uFF0C\u4E3A\u7A7A\u65F6\u8F93\u51FA\u6240\u6709\u7528\u6237");
		textField_Name.setBounds(44, 45, 222, 32);
		panel_1.add(textField_Name);
		textField_Name.setColumns(10);
		
		JButton btn_num = new JButton("\u6309\u5E10\u53F7\u6A21\u7CCA\u67E5\u627E");
		btn_num.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Userdao userdao=new Userdao();
				List<QQUser> users=userdao.findBynum_like("%"+textField_num.getText()+"%");
				new Search_resultswing(users,user);
				textField_num.setText("");
			}
		});
		btn_num.setBounds(276, 10, 130, 32);
		panel_1.add(btn_num);
		
		JButton btn_Name = new JButton("\u6309\u6635\u79F0\u6A21\u7CCA\u67E5\u627E");
		btn_Name.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Userdao userdao=new Userdao();
				List<QQUser> users=userdao.findByname_like("%"+textField_Name.getText()+"%");
				new Search_resultswing(users,user);
				textField_Name.setText("");
			}
		});
		btn_Name.setBounds(276, 45, 130, 32);
		panel_1.add(btn_Name);
		
		JLabel lbl_bg = new JLabel();
		lbl_bg.setBounds(0, 0, 494, 122);
		frame.getContentPane().add(lbl_bg);
		lbl_bg.setIcon(new ImageIcon("imag\\likeswingbg.png"));
		
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
