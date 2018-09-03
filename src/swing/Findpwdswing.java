package swing;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Font;
import javax.swing.JTextField;

import dao.Userdao;
import javax.swing.ImageIcon;

import java.awt.Button;
import java.awt.Color;
import javax.swing.UIManager;

public class Findpwdswing extends JFrame{
	/**
	 * long swing.Findpwdswing.serialVersionUID : 1 [0x1]
	 */
	private static final long serialVersionUID = 1L;
	private JLabel labname,labage,labblood,labnum,labtips;
	private JButton btn_findpwd, btn_reset;
	private JPanel pan1,pan2;
	private JTextField jtfname;
	private JTextField jtfnum;
	private JTextField jtfage;
	private JTextField jtfblood;
	private JTextField jtfnewpwd;
	private JButton button_return;
	private JLabel lbl_bg;
	public Findpwdswing() {
		setTitle("’˝‘⁄’“ªÿ√‹¬Î");
		setIconImage(Toolkit.getDefaultToolkit().getImage("imag\\tx.png"));
		setSize(600, 400);
		getContentPane().setLayout(null);

		pan1 = new JPanel();
		pan1.setBounds(10, 10, 568, 294);
		pan1.setLayout(null);
		pan1.setOpaque(false);
		pan2 = new JPanel();
		pan2.setBounds(10, 310, 568, 42);
		pan2.setLayout(null);
		
		labname = new JLabel("\u6635\u79F0\uFF1A");
		labname.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 15));
		labname.setBounds(113, 49, 67, 29);
		
		labage = new JLabel("\u5E74\u9F84\uFF1A");
		labage.setToolTipText("\u6CE8\u518C\u65F6\u586B\u5199\u7684\u5E74\u9F84");
		labage.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 15));
		labage.setBounds(113, 88, 67, 29);
		
		labnum = new JLabel("\u5E10\u53F7\uFF1A");
		labnum.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 15));
		labnum.setBounds(113, 10, 67, 29);
	
		labblood = new JLabel("\u8840\u578B\uFF1A");
		labblood.setToolTipText("\u6CE8\u518C\u65F6\u586B\u5199\u7684\u8840\u578B");
		labblood.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 15));
		labblood.setBounds(113, 127, 67, 29);	
		
		labtips = new JLabel("\u4EC5\u5728\u9700\u8981\u91CD\u8BBE\u5BC6\u7801\u65F6\u5728\u4E0B\u65B9\u8F93\u5165\u65B0\u5BC6\u7801:");
		labtips.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 15));
		labtips.setBounds(113, 166, 273, 33);
		jtfnewpwd=new JTextField();
		jtfnewpwd.setBounds(190, 209, 114, 29);
		
		jtfname = new JTextField();
		jtfname.setBounds(190, 49, 114, 29);
		pan1.add(jtfname);
		jtfname.setColumns(10);
		
		jtfnum = new JTextField();
		jtfnum.setBounds(190, 10, 114, 29);
		jtfnum.setColumns(10);
		
		jtfblood = new JTextField();
		jtfblood.setColumns(10);
		jtfblood.setBounds(190, 128, 114, 29);
		
		jtfage = new JTextField();
		jtfage.setColumns(10);
		jtfage.setBounds(190, 88, 114, 29);
		
		
		pan1.add(labname);
		pan1.add(labnum);
		pan1.add(labblood);
		pan1.add(labage);
		pan1.add(labtips);
		pan1.add(jtfblood);
		pan1.add(jtfnum);
		pan1.add(jtfage);
		pan1.add(jtfage);
		pan1.add(jtfnewpwd);
		
		btn_findpwd=new JButton();
		btn_findpwd.setBackground(UIManager.getColor("Button.background"));
		btn_findpwd.setText("\u627E\u56DE\u5BC6\u7801");
		btn_findpwd.setBounds(193, 1, 115, 42);
		btn_findpwd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.out.println(jtfnum.getText()+jtfname.getText()+new Integer(jtfage.getText())+jtfblood.getText()+jtfnewpwd.getText());
				Userdao user=new Userdao();
				String pwd=null;
				try {
					pwd=user.reSetpwd(1, jtfnum.getText(), jtfname.getText(), new Integer(jtfage.getText()), jtfblood.getText(), jtfnewpwd.getText());
					if(pwd!=null)
						JOptionPane.showMessageDialog(null, "ƒ„µƒ√‹¬Î «£∫"+pwd,"tips",JOptionPane.INFORMATION_MESSAGE);
						dispose();
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, " ˝◊÷∏Ò ΩªØ ß∞‹\nƒÍ¡‰≤ªƒ‹Œ™ø’£°","tips",JOptionPane.WARNING_MESSAGE);
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "∆•≈‰ ß∞‹","tips",JOptionPane.WARNING_MESSAGE);
				}
				
			}
		});
		btn_reset=new JButton("÷ÿ…Ë√‹¬Î");	
		btn_reset.setBounds(318,1,115,42);
		btn_reset.setBackground(UIManager.getColor("Button.background"));
		btn_reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Userdao user=new Userdao();
				String pwd=null;
				try {
					pwd=user.reSetpwd(2, jtfnum.getText(), jtfname.getText(), new Integer(jtfage.getText()), jtfblood.getText(), jtfnewpwd.getText());
					if (pwd.equals("succeed")) {
						JOptionPane.showMessageDialog(null, "πßœ≤ƒ„√‹¬Î…Ë÷√≥…π¶","tips",JOptionPane.INFORMATION_MESSAGE);
						dispose();
					} else if(pwd.equals("failed")){
						JOptionPane.showMessageDialog(null, "÷ÿ÷√√‹¬Î ß∞‹","tips",JOptionPane.WARNING_MESSAGE);
					}
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, " ˝◊÷∏Ò ΩªØ ß∞‹\nƒÍ¡‰≤ªƒ‹Œ™ø’£°","tips",JOptionPane.WARNING_MESSAGE);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "÷ÿ÷√√‹¬Î ß∞‹","tips",JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		
		getContentPane().add(pan1);
		getContentPane().add(pan2);
		
		button_return = new JButton("\u8FD4\u56DE");
		button_return.setBounds(443, 0, 115, 42);
		button_return.setBackground(UIManager.getColor("Button.background"));
		button_return.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		button_return.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 15));
		pan2.add(btn_findpwd);
		pan2.add(btn_reset);
		pan2.add(button_return);
		pan2.setOpaque(false);
		lbl_bg = new JLabel();
		lbl_bg.setIcon(new ImageIcon("imag\\grass.jpg"));
		lbl_bg.setBounds(0, 0, 584, 362);
		getContentPane().add(lbl_bg);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

	}

}
