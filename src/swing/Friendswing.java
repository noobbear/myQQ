package swing;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PrimitiveIterator.OfDouble;

import javax.swing.*;
import dao.Frienddao;
import dao.Groupdao;
import dao.Userdao;
import pojo.QQUser;
import pojo.UserGroup;
import javax.swing.tree.*;

import java.awt.event.*;

public class Friendswing {
	private JFrame frame = new JFrame();
	private JScrollPane jsp;
	private JPanel pan1;
	private JMenuItem menu_Inform;
	private JMenuItem menu_Quicksearch;
	private JButton btn_tx;

	static HashMap<DefaultMutableTreeNode, Integer> map_id;// ���������Ա� ͨ���ڵ��ȡid
	static HashMap<DefaultMutableTreeNode, String> map_num;// ���������Ա� ͨ���ڵ��ȡnum
	static HashMap<String, DefaultMutableTreeNode> map_group;// ���������Ա�
																// ͨ��������string��ȡ�ڵ�

	static JTree tree;
	static DefaultMutableTreeNode root;
	static DefaultMutableTreeNode Tem_node = null;
	static String name = null, num = null;

	private QQUser user = null;
	private JLabel lbl_num;
	private JLabel lbl_name;
	private JMenuItem menu_del;
	private JMenuItem menu_movefri;
	private JMenuItem menu_delnullgroup;
	private JLabel lbl_bg;

	public Friendswing(QQUser user) {
		this.user = user;

		frame.setResizable(false);
		frame.setSize(292, 550);
		pan1 = new JPanel();
		pan1.setBounds(0, 63, 284, 558);
		pan1.setLayout(null);
		pan1.setSize(frame.getWidth(), frame.getHeight());
		// *********************���͹�����������
		root = new DefaultMutableTreeNode("�ҵĺ���");
		createTree(root, user);
		tree = new JTree(root);
		tree.setOpaque(false);

		tree.setSelectionRow(1);
		tree.setBackground(Color.WHITE);
		tree.setFont(new Font("������", Font.BOLD, 20));
		tree.setRootVisible(false);
		tree.putClientProperty("JTree.lineStyle", "None");// ȥ�����ڵ����

		jsp = new JScrollPane(tree);
		jsp.setSize(pan1.getWidth(), pan1.getHeight());
		jsp.setViewportView(tree);
		jsp.setBounds(0, 0, 284, 523);
		//jsp.setOpaque(false);
		jsp.getViewport().setOpaque(false);
		pan1.add(jsp);

		tree.addMouseListener(new MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				if (evt.getModifiers() == InputEvent.BUTTON1_MASK && evt.getClickCount() == 2) {
					Tem_node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
					if (Tem_node.isLeaf()) {
						String string = Tem_node.toString();
						name = string.substring(0, string.indexOf(":"));// �����û���

						Userdao dao = new Userdao();
						QQUser friend = dao.findFriend(map_id.get(Tem_node));
						num = map_num.get(Tem_node);

						new Thread(new Chatswing(friend, user)).start();//
						Tem_node = null;
					}
				}
			}
		});

		// ********************* �˵�������
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(tree, popupMenu);

		// ***********************************ˢ�º����б�
		JMenuItem menu_refresh = new JMenuItem("\u5237\u65B0\u597D\u53CB\u5217\u8868");
		menu_refresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				UpdateTree();
			}
		});
		popupMenu.add(menu_refresh);
		/// ********��ӷ���
		JMenuItem menu_addgroup = new JMenuItem("\u6DFB\u52A0\u4E00\u4E2A\u5206\u7EC4");
		menu_addgroup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String group = null;
				Groupdao dao = new Groupdao();
				group = JOptionPane.showInputDialog(null, "�����������", "��ӷ���", JOptionPane.OK_CANCEL_OPTION);
				if (group != null) {
					if (dao.addGroup(user.getId(), group)) {
						DefaultMutableTreeNode node = new DefaultMutableTreeNode(group);
						map_group.put(group, node);
						root.add(node);
						tree.updateUI();
					}
				}
			}
		});
		popupMenu.add(menu_addgroup);
		// *********�ƶ�����
		menu_movefri = new JMenuItem("\u79FB\u52A8\u8BE5\u597D\u53CB\u81F3");
		menu_movefri.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Justmove();
			}
		});
		// **********ɾ���շ���
		menu_delnullgroup = new JMenuItem("\u5220\u9664\u4E00\u4E2A\u7A7A\u5206\u7EC4");
		menu_delnullgroup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				Groupdao dao = new Groupdao();
				if (node.isLeaf() && map_id.get(node) == null && map_num.get(node) == null) {
					if (dao.DelGroup(user.getId(), node.toString())) {
						map_group.remove(node);
						node.removeFromParent();
						tree.updateUI();
					}
				}
			}
		});
		popupMenu.add(menu_delnullgroup);
		popupMenu.add(menu_movefri);
		// *****************��Ӻ���
		JMenuItem menu_addf = new JMenuItem("\u6DFB\u52A0\u597D\u53CB");
		menu_addf.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new AddFriend_swing(user);
			}
		});
		popupMenu.add(menu_addf);
		// *********************�鿴��Ϣ
		menu_Inform = new JMenuItem("\u67E5\u770B\u4E2A\u4EBA\u4FE1\u606F");
		menu_Inform.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) { // �鿴������Ϣ
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				Userdao dao = new Userdao();
				if (node.isLeaf() && map_id.get(node) != null) {
					QQUser friend = dao.findFriend(map_id.get(node));
					new UserInformationswing(friend, frame, null, null, false);
				}
			}
		});
		// **************ɾ������
		menu_del = new JMenuItem("\u5220\u9664\u8BE5\u597D\u53CB");
		menu_del.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				deleteFriend();
			}
		});
		popupMenu.add(menu_del);
		popupMenu.add(menu_Inform);
		// **********************************ģ������
		menu_Quicksearch = new JMenuItem("\u6A21\u7CCA\u67E5\u627E");
		menu_Quicksearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Like_Searchdswing(user);
			}
		});
		popupMenu.add(menu_Quicksearch);

		// ********************���ڻ�������
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("imag\\tx.png"));
		frame.getContentPane().setLayout(null);
		frame.setTitle(user.getName() + " " + user.getNum());
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {// ���߸���
			@Override
			public void windowClosing(WindowEvent arg0) {
				Userdao dao = new Userdao();
				dao.UpdateOnline(user.getId(), false);
				System.exit(0);
			}
		});

		jsp.setOpaque(false);// �����Ϊ͸��
		pan1.setOpaque(false);

		// ���ڵ��еı���ɫ����Ϊ͸��
		DefaultTreeCellRenderer cellRenderer = new DefaultTreeCellRenderer();
		cellRenderer.setBackgroundNonSelectionColor(new Color(0, 0, 0, 0));
		cellRenderer.setBackgroundSelectionColor(new Color(0, 0, 0, 0));
		tree.setCellRenderer(cellRenderer);
		frame.getContentPane().add(pan1);

		jsp.setBorder(null);

		lbl_num = new JLabel("�ʺţ�" + user.getNum());
		lbl_num.setBounds(0, 38, 152, 37);
		frame.getContentPane().add(lbl_num);
		lbl_num.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_num.setBackground(Color.WHITE);
		lbl_num.setToolTipText(lbl_num.getText());
		lbl_num.setFont(new Font("������", Font.PLAIN, 16));
		lbl_num.setBorder(null);

		lbl_name = new JLabel("�ǳƣ�" + user.getName());
		lbl_name.setBounds(0, 0, 152, 37);
		frame.getContentPane().add(lbl_name);
		lbl_name.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_name.setBackground(Color.WHITE);
		lbl_name.setToolTipText(lbl_name.getText());
		lbl_name.setFont(new Font("������", Font.PLAIN, 16));
		lbl_name.setBorder(null);

		// �򿪸�����Ϣ
		btn_tx = new JButton();
		btn_tx.setBounds(153, 0, 133, 75);
		frame.getContentPane().add(btn_tx);
		btn_tx.setBackground(Color.WHITE);
		btn_tx.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new UserInformationswing(user, frame, lbl_num, lbl_name, true);
			}
		});
		btn_tx.setIcon(new ImageIcon("imag\\dog.jpg"));
		btn_tx.setContentAreaFilled(false);// ȥ�߿�
		btn_tx.setBorderPainted(false);

		lbl_bg = new JLabel();
		lbl_bg.setBounds(0, 0, 286, 522);
		frame.getContentPane().add(lbl_bg);
		lbl_bg.setIcon(new ImageIcon("imag\\bg.png"));
		frame.setVisible(true);
	}

	// *****************************************************************************************************
	/////// �ƶ����ѵ�ĳ����
	public void Justmove() {
		String name = null;
		Frienddao dao = new Frienddao();
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		if (node.isLeaf() && map_id.get(node) != null) {// ѡ����һ���û�
			name = JOptionPane.showInputDialog(null, "�ƶ�����", "�����ƶ�����", JOptionPane.OK_CANCEL_OPTION);
			if (name != null && map_group.get(name) != null) {// ������һ����Ч�ķ�����
				if (dao.MoveTO(user.getId(), map_id.get(node), name)) {// �ƶ��ɹ�
					DefaultMutableTreeNode newnode = new DefaultMutableTreeNode(node);
					int id = map_id.get(node);
					String num = map_num.get(node);
					map_id.remove(node);
					map_num.remove(node);
					node.removeFromParent();

					map_id.put(newnode, id);
					map_num.put(newnode, num);
					map_group.get(name).add(newnode);
					tree.updateUI();
				}
			}

		}
	}

	// *******************���������б�
	private void createTree(DefaultMutableTreeNode root2, QQUser user) {
		// TODO Auto-generated method stub
		Frienddao frienddao = new Frienddao();
		Userdao userDao = new Userdao();
		Groupdao groupdao = new Groupdao();
		UserGroup userGroup = groupdao.getGroup(user.getId());// �����ҵķ��飬���浽usergroup��

		map_id = new HashMap<>();
		map_num = new HashMap<>();

		map_group = new HashMap<>();

		DefaultMutableTreeNode frieds = new DefaultMutableTreeNode("Ĭ�Ϻ���");// ������ڵ�һ��
		DefaultMutableTreeNode frinode = null;
		root2.add(frieds);
		map_group.put("Ĭ�Ϻ���", frieds);

		DefaultMutableTreeNode noderoot = null;
		DefaultMutableTreeNode node = null;
		for (int i = 0; i < userGroup.getGroup_num(); i++) {// ����ÿ�����飬ÿ�����齨��һ��noderoot
			if (!userGroup.Getagroup(i).equals("Ĭ�Ϻ���")) {
				noderoot = new DefaultMutableTreeNode(userGroup.Getagroup(i));// noderoot����node�ڵ㣬����ڸ÷����ڵĺ���
				map_group.put(userGroup.Getagroup(i), noderoot);
			}
			ArrayList<Integer> list_id = frienddao.findFriendid(user.getId(), userGroup.Getagroup(i));// ��friend��ƾ�ҵ�id�ͺ������ڵķ��鷵�غ��ѵ�id
			// �����÷����ڵĺ���
			for (int j = 0; j < list_id.size(); j++) {
				QQUser frienduser = userDao.findFriend(list_id.get(j));// ƾmy���ѵ�id��ѯ������һ��user����nuul
				String online = "������";
				if (frienduser.isOnline()) {
					online = "����";
				}
				if (userGroup.Getagroup(i).equals("Ĭ�Ϻ���")) {
					frinode = new DefaultMutableTreeNode(
							frienduser.getName() + ":" + frienduser.getNum() + "  " + online);
					frieds.add(frinode);
					map_id.put(frinode, frienduser.getId());
					map_num.put(frinode, frienduser.getNum());
				} else {
					node = new DefaultMutableTreeNode(frienduser.getName() + ":" + frienduser.getNum() + "  " + online);
					noderoot.add(node);
					map_id.put(node, frienduser.getId());
					map_num.put(node, frienduser.getNum());
				}
			}
			// ��ӷ�Ĭ�Ϻ��ѷ���ڵ�
			if (userGroup.Getagroup(i).equals("Ĭ�Ϻ���"))
				continue;
			root2.add(noderoot);

		}
	}

	// ɾ������
	public void deleteFriend() {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		if (node.isLeaf() && map_id.get(node) != null) {
			Frienddao frienddao = new Frienddao();
			// System.out.println("ɾ������" + map_id.get(node) + " " +
			// map_num.get(node));
			if (frienddao.DelFriend(user.getId(), map_id.get(node))) {
				node.removeFromParent();
				node = null;
				tree.updateUI();
			}

		}
	}

	// ˢ����
	public void UpdateTree() {
		root.removeAllChildren();// ɾ���ӽڵ�
		createTree(root, user);// �����µ���
		tree.updateUI();// ˢ��
	}

	// �жϽڵ��Ƿ�User
	public boolean isQQUser() {
		boolean bl = false;
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		if (node.isLeaf() && map_id.get(node) != null)
			bl = true;
		return bl;

	}

	// �ж��Ƿ�շ���
	public boolean isnullgroup() {
		boolean bl = false;
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		if (node.isLeaf() && map_id.get(node) == null && map_num.get(node) == null)
			bl = true;
		return bl;

	}

	// ************��ӵ���ʽ�˵�
	private void addPopup(Component component, final JPopupMenu popup) {

		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					if (!isQQUser()) {
						menu_del.setVisible(false);
						menu_Inform.setVisible(false);
						menu_movefri.setVisible(false);
					} else {
						menu_del.setVisible(true);
						menu_Inform.setVisible(true);
						menu_movefri.setVisible(true);
					}
					if (!isnullgroup()) {
						menu_delnullgroup.setVisible(false);
					} else
						menu_delnullgroup.setVisible(true);
					showMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					if (!isQQUser()) {
						menu_del.setVisible(false);
						menu_Inform.setVisible(false);
						menu_movefri.setVisible(false);
					} else {
						menu_del.setVisible(true);
						menu_Inform.setVisible(true);
						menu_movefri.setVisible(true);
					}
					if (!isnullgroup()) {
						menu_delnullgroup.setVisible(false);
					} else
						menu_delnullgroup.setVisible(true);
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				if (!isQQUser()) {
					menu_del.setEnabled(false);
					menu_Inform.setEnabled(false);
					menu_movefri.setEnabled(false);
				} else {
					menu_del.setEnabled(true);
					menu_Inform.setEnabled(true);
					menu_movefri.setEnabled(true);
				}
				if (!isnullgroup()) {
					menu_delnullgroup.setVisible(false);
				} else
					menu_delnullgroup.setVisible(true);
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}

}
