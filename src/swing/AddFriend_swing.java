package swing;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.tree.DefaultMutableTreeNode;

import dao.Frienddao;
import dao.Groupdao;
import dao.Userdao;
import pojo.QQUser;
import pojo.UserGroup;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import javax.swing.JSpinner;

public class AddFriend_swing {
	
	private JFrame jframe=new JFrame();
	private JPanel pan1,pan2;
	private JTextField textId;
	private JTextField textNum;
	private JLabel labId,labNum;
	private JSpinner spinner_group;
	public AddFriend_swing(QQUser user){
		
		jframe.getContentPane().setBackground(SystemColor.menu);
		jframe.setResizable(false);
		jframe.setTitle("\u7CBE\u786E\u6DFB\u52A0\u597D\u53CB");
		jframe.setBackground(Color.WHITE);
		jframe.setSize(451, 319);
		jframe.setIconImage(Toolkit.getDefaultToolkit().getImage("imag\\tx.png"));
		jframe.setVisible(true);
		jframe.setLocationRelativeTo(null);
		jframe.getContentPane().setLayout(null);
		jframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		pan1 = new JPanel();
		pan1.setBackground(SystemColor.menu);
		pan1.setLayout(null);
		pan1.setBounds(10, 10, 415, 225);
		jframe.getContentPane().add(pan1);
		
		textId = new JTextField();
		textId.setFont(new Font("新宋体", Font.PLAIN, 16));
		textId.setToolTipText("\u4EC5\u9700\u4E8C\u9009\u4E00");
		textId.setBounds(134, 55, 198, 38);
		pan1.add(textId); 
		textId.setColumns(10);
		
		textNum = new JTextField();
		textNum.setFont(new Font("新宋体", Font.PLAIN, 16));
		textNum.setToolTipText("\u4EC5\u9700\u4E8C\u9009\u4E00");
		textNum.setBounds(134, 105, 198, 38);
		pan1.add(textNum);
		textNum.setColumns(10);
		
		labId = new JLabel("\u901A\u8FC7ID\u6DFB\u52A0\uFF1A");
		labId.setForeground(Color.GREEN);
		labId.setFont(new Font("宋体", Font.BOLD, 12));
		labId.setToolTipText("\u4EC5\u9700\u4E8C\u9009\u4E00");
		labId.setBounds(10, 54, 114, 38);
		pan1.add(labId);
		
		labNum = new JLabel("\u901A\u8FC7\u5E10\u53F7\u6DFB\u52A0\uFF1A");
		labNum.setForeground(Color.GREEN);
		labNum.setFont(new Font("宋体", Font.BOLD, 12));
		labNum.setToolTipText("\u4EC5\u9700\u4E8C\u9009\u4E00");
		labNum.setBounds(10, 103, 114, 40);
		pan1.add(labNum);
		
		JLabel lblNewLabel = new JLabel("\u4E8C\u9009\u4E00\u5373\u53EF");
		lblNewLabel.setFont(new Font("新宋体", Font.PLAIN, 15));
		lblNewLabel.setToolTipText("\u90FD\u586B\u65F6\u9ED8\u8BA4ID");
		lblNewLabel.setBounds(187, 10, 86, 35);
		pan1.add(lblNewLabel);
		
		spinner_group = new JSpinner();
		spinner_group.setToolTipText("\u8BF7\u52FF\u5728\u6B64\u4FEE\u6539\u5206\u7EC4\u540D");
		spinner_group.setBounds(134, 154, 198, 35);
		Groupdao dao=new Groupdao();
		UserGroup uGroup=new UserGroup();
		uGroup=dao.getGroup(user.getId());
		spinner_group.setModel(new SpinnerListModel(uGroup.getUserGroup()));
		pan1.add(spinner_group);
		
		JLabel label = new JLabel("\u6DFB\u52A0\u5230\u5206\u7EC4\uFF1A");
		label.setForeground(Color.GREEN);
		label.setFont(new Font("宋体", Font.BOLD, 15));
		label.setBounds(10, 153, 114, 28);
		pan1.add(label);
		pan1.setOpaque(false);
		
		pan2 = new JPanel();
		pan2.setBackground(SystemColor.menu);
		pan2.setLayout(new FlowLayout());
		pan2.setBounds(84, 239, 304, 42);
		jframe.getContentPane().add(pan2);
		pan2.setOpaque(false);
		
		JButton btnadd = new JButton("\u6DFB\u52A0");
		btnadd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addfriend(user);
				textId.setText("");
				textNum.setText("");
			}
		});
		btnadd.setToolTipText("\u4F18\u5148\u901A\u8FC7\u5E10\u53F7\u67E5\u627E");
		btnadd.setFont(new Font("新宋体", Font.BOLD, 16));
		pan2.add(btnadd);
		
		JButton btnReturn = new JButton("\u8FD4\u56DE");
		btnReturn.setFont(new Font("新宋体", Font.BOLD, 16));
		pan2.add(btnReturn);
		
		JLabel lbl_bg = new JLabel();
		lbl_bg.setBounds(0, 0, 445, 291);
		lbl_bg.setIcon(new ImageIcon("imag\\03.jpg"));
		jframe.getContentPane().add(lbl_bg);
		
	}
	public void addfriend(QQUser user){
		Frienddao dao=new Frienddao();
		boolean	bl=false;
		if(textId.getText().equals("")&&user.getNum().equals(textNum.getText())//Id为空且帐号等于自己帐号
				||!textId.getText().equals("")&&user.getId()==(new Integer( textId.getText()) )){//id不为空且等于自己ID
			JOptionPane.showMessageDialog(null, "你不能添加自己", "tips", JOptionPane.WARNING_MESSAGE);
		}else {		

			Userdao userdao=new Userdao();
			QQUser friend=new QQUser();
			String groupname=new String((String)spinner_group.getValue());
			if(!textId.getText().equals(""))
				{
					bl=dao.AddFriend(user.getId(),new Integer( textId.getText()),groupname);//通过ID添加
					friend=userdao.findFriend(new Integer( textId.getText()));//返回一个user
				}
			else
				{
					bl=dao.AddFriend(user.getId(),textNum.getText(),groupname);//通过帐号添加
					friend=userdao.findFriend(textNum.getText());
				}
			if(bl)
				{	//添加到好友列表
					String online="不在线";
					if(friend.isOnline())
						online="在线";
					DefaultMutableTreeNode node=new DefaultMutableTreeNode(friend.getName()+": "+friend.getNum()+"  "+online);
					Friendswing.map_id.put(node, friend.getId());
					Friendswing.map_num.put(node, friend.getNum());
					Friendswing.map_group.get(groupname).add(node);
					Friendswing.tree.updateUI();
					JOptionPane.showMessageDialog(null, "添加好友成功", "tips", JOptionPane.INFORMATION_MESSAGE);					
				}
			else 
				JOptionPane.showMessageDialog(null, "添加失败\n可能该用户已经是您的好友\n或者该用户不存在", "tips", JOptionPane.WARNING_MESSAGE);
		}
	}
}
