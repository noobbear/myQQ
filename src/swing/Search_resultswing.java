package swing;
import pojo.QQUser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.tree.DefaultMutableTreeNode;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import dao.Frienddao;
import dao.Userdao;
public class Search_resultswing {
	
	private JFrame frame=new JFrame("模糊添加好友");
	private List<QQUser> list;
	private QQUser user;
	private java.awt.List list1;
	private JScrollPane jScrollPane;
	
	
	public   Search_resultswing(List<QQUser> userslist,QQUser myself) {
		
		user = myself;
		list = userslist;
		frame.setTitle("搜索结果");
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("imag\\tx.png"));
		frame.getContentPane().setLayout(new BorderLayout());
		
		list1 = new java.awt.List();
		list1.setBounds(10, 10, 286, 303);
		
		for (QQUser qqUser : list) {
			list1.add("昵称:"+qqUser.getName()+"  帐号 : "+qqUser.getNum()+"  年龄: "+qqUser.getAge());
		}
		
		jScrollPane=new JScrollPane(list1);
		frame.getContentPane().add(jScrollPane);
		list1.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//System.out.println(list1.getSelectedIndex());
				QQUser friend = list.get(list1.getSelectedIndex());
				System.out.println(friend.getId());
				Frienddao frienddao=new Frienddao();
				
				if(friend.getId()!=user.getId()){//不添加自己
					int n=JOptionPane.showConfirmDialog(null, "是否确认添加该好友到默认好友", "tips", JOptionPane.OK_CANCEL_OPTION);
					if (n==JOptionPane.OK_OPTION) {
						if (frienddao.AddFriend(user.getId(), friend.getId(), "默认好友")) {
							
							//添加到好友列表
							String online="不在线";
							if(friend.isOnline())
								online="在线";
							DefaultMutableTreeNode node=new DefaultMutableTreeNode(friend.getName()+": "+friend.getNum()+"  "+online);
							Friendswing.map_id.put(node, friend.getId());
							Friendswing.map_num.put(node, friend.getNum());
							
							System.out.println(Friendswing.map_group.get("默认好友")!=null);
							Friendswing.map_group.get("默认好友").add(node);
							
							System.out.println(Friendswing.map_group.get("默认好友").toString());
							
							Friendswing.tree.updateUI();
							
							JOptionPane.showMessageDialog(null, "添加成功", "tips", JOptionPane.INFORMATION_MESSAGE);
						}
						else 
							JOptionPane.showMessageDialog(null, "添加失败\n原因可能是你们已经是好友了", "tips", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
			
		} );

		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setSize(329, 351);
		frame.setResizable(false);
		frame.setVisible(true);
	}
}
