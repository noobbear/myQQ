package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import jdbc.JDBCTest;
import pojo.Friend;
import pojo.QQUser;

public class Userdao extends JDBCTest {
	ResultSet rs = null;
	PreparedStatement ps = null;
	Connection conn = null;
	QQUser user = null;
	private List<QQUser> users= null;
	// ***************************************��¼��ȡ�û���Ϣ
	public QQUser login(String num, String password) {// �ʺţ�����
		conn = getConn();
		String sql = "select * from myqquser where num = ? and password = ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, num);
			ps.setString(2, password);
			rs = ps.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					user = new QQUser();
					user.setId(rs.getInt("id"));
					user.setNum(rs.getString("num"));
					user.setName(rs.getString("name"));
					user.setPassword(rs.getString("password"));
					user.setAge(rs.getInt("age"));
					user.setSex(rs.getInt("sex"));
					user.setY(rs.getInt("year"));
					user.setM(rs.getInt("month"));
					user.setD(rs.getInt("day"));
					user.setBlood(rs.getString("blood"));
					user.setXingzuo(rs.getString("xingzuo"));
					user.setSchool(rs.getString("school"));
					user.setAddress(rs.getString("address"));
					user.setOnline(true);// ����Ϊ����״̬
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close(rs, ps, conn);
		}
		return user;
	}
	
	/// ************************update�û�����״̬
		public void UpdateOnline(Integer id,Boolean bool) {
			conn = getConn();
			int online=0;
			if (bool) {
				online=1;
			}
			String sql = "update myqquser set online=?  where id = ?";
			try {
				ps = conn.prepareStatement(sql);
				ps.setInt(1, online);
				ps.setInt(2, id);	
				int n = ps.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	/// ************************update�û���Ϣ
	public boolean UpdateUser(QQUser user) {
		boolean update = false;
		conn = getConn();
		String sql = "update myqquser set name=? , age=? , sex = ? , year=? , month=? , day=? , xingzuo=? , blood=? , school=? , address=?  where id = ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getName());
			ps.setInt(2, user.getAge());
			ps.setInt(3, user.getSex());
			ps.setInt(4, user.getY());
			ps.setInt(5, user.getM());
			ps.setInt(6, user.getD());
			ps.setString(7, user.getXingzuo());
			ps.setString(8, user.getBlood());
			ps.setString(9, user.getSchool());
			ps.setString(10, user.getAddress());
			ps.setInt(11, user.getId());
			int n = ps.executeUpdate();
			if (n > 0) {
				update = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			update = false;
		}

		return update;

	}

	// *************************�ж��Ƿ����ע�ᣬ����ʱ����ע�ᣨ��ӽ����ݿ⣩
	public boolean Regist(QQUser user) {
		conn = getConn();
		boolean b = false;
		boolean never = true;
		String sql1 = "select * from myqquser where num = ?";// �Ȳ�ѯ���û����Ƿ����
		try {
			ps = conn.prepareStatement(sql1);
			ps.setString(1, user.getNum());
			rs = ps.executeQuery();
			while (rs.next() && never) {
				if (rs.getString("num").equals(user.getNum())) {
					never = false;
				}
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (never) {// �û�������ʱע��
			String sql2 = "insert into myqquser(num,name,password,sex,age,year,month,day,blood,xingzuo,school,address) values(?,?,?,?,?,?,?,?,?,?,?,?)";
			try {
				ps = conn.prepareStatement(sql2);
				ps.setString(1, user.getNum());
				ps.setString(2, user.getName());
				ps.setString(3, user.getPassword());
				ps.setInt(4, user.getSex());
				ps.setInt(5, user.getAge());
				ps.setInt(6, user.getY());
				ps.setInt(7, user.getM());
				ps.setInt(8, user.getD());
				ps.setString(9, user.getBlood());
				ps.setString(10, user.getXingzuo());
				ps.setString(11, user.getSchool());
				ps.setString(12, user.getAddress());
				// online Ĭ��Ϊ0����������
				int n = ps.executeUpdate();
				if (n > 0) {
					b = true;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				b = false;
			} finally {
				close(rs, ps, conn);
			}
		}
		return b;
	}

	// ***************************ƾid����һ���û�
	public QQUser findFriend(Integer id) {
		conn = getConn();
		String sql = "select * from myqquser where id = ? ";
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();

			if (rs != null) {
				while (rs.next()) {
					user = new QQUser();
					user.setId(rs.getInt("id"));
					user.setNum(rs.getString("num"));
					user.setName(rs.getString("name"));
					user.setPassword(rs.getString("password"));
					user.setSex(rs.getInt("sex"));
					user.setY(rs.getInt("year"));
					user.setM(rs.getInt("month"));
					user.setD(rs.getInt("day"));
					user.setBlood(rs.getString("blood"));
					user.setXingzuo(rs.getString("xingzuo"));
					user.setSchool(rs.getString("school"));
					user.setAddress(rs.getString("address"));
					user.setOnline(rs.getBoolean("online"));// ��ȡ����״̬
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close(rs, ps, conn);
		}
		return user;
	}
	
	// ***************************ƾ�ʺŷ���һ���û�
	public QQUser findFriend(String num) {
		conn = getConn();
		String sql = "select * from myqquser where num = ? ";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, num);
			rs = ps.executeQuery();

			if (rs != null) {
				while (rs.next()) {
					user = new QQUser();
					user.setId(rs.getInt("id"));
					user.setNum(rs.getString("num"));
					user.setName(rs.getString("name"));
					user.setPassword(rs.getString("password"));
					user.setSex(rs.getInt("sex"));
					user.setY(rs.getInt("year"));
					user.setM(rs.getInt("month"));
					user.setD(rs.getInt("day"));
					user.setBlood(rs.getString("blood"));
					user.setXingzuo(rs.getString("xingzuo"));
					user.setSchool(rs.getString("school"));
					user.setAddress(rs.getString("address"));
					user.setOnline(rs.getBoolean("online"));// ��ȡ����״̬
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close(rs, ps, conn);
		}
		return user;
	}
	
	//**********************************ģ�������ʺ�
	public List<QQUser> findBynum_like(String num) {// �ʺţ�����
		conn = getConn();
		String sql = "select *from myqquser where num like ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, num);
			rs = ps.executeQuery();
			if (rs != null) {
				users = new ArrayList<QQUser>();
				while (rs.next()) {
					user = new QQUser();
					user.setId(rs.getInt("id"));
					user.setNum(rs.getString("num"));
					user.setName(rs.getString("name"));
					user.setPassword(rs.getString("password"));
					user.setAge(rs.getInt("age"));
					user.setSex(rs.getInt("sex"));
					user.setY(rs.getInt("year"));
					user.setM(rs.getInt("month"));
					user.setD(rs.getInt("day"));
					user.setBlood(rs.getString("blood"));
					user.setXingzuo(rs.getString("xingzuo"));
					user.setSchool(rs.getString("school"));
					user.setAddress(rs.getString("address"));
					user.setOnline(rs.getBoolean("online"));// ��ȡ����״̬
					users.add(user);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close(rs, ps, conn);
		}
		return users;
	}
	//**********************************ģ�������ǳ�
		public List<QQUser> findByname_like(String name) {// �ʺţ�����
			conn = getConn();
			String sql = "select *from myqquser where name like ?";
			try {
				ps = conn.prepareStatement(sql);
				ps.setString(1, name);
				rs = ps.executeQuery();
				if (rs != null) {
					users = new ArrayList<QQUser>();
					while (rs.next()) {
						user = new QQUser();
						user.setId(rs.getInt("id"));
						user.setNum(rs.getString("num"));
						user.setName(rs.getString("name"));
						user.setPassword(rs.getString("password"));
						user.setAge(rs.getInt("age"));
						user.setSex(rs.getInt("sex"));
						user.setY(rs.getInt("year"));
						user.setM(rs.getInt("month"));
						user.setD(rs.getInt("day"));
						user.setBlood(rs.getString("blood"));
						user.setXingzuo(rs.getString("xingzuo"));
						user.setSchool(rs.getString("school"));
						user.setAddress(rs.getString("address"));
						user.setOnline(rs.getBoolean("online"));// ��ȡ����״̬
						users.add(user);
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				close(rs, ps, conn);
			}
			return users;
		}
	
	// **************************�һ����벢����ԭ���룬������������,�ɹ������ַ���succeedʧ�ܷ���failed
	public String reSetpwd(int n, String num, String name, Integer age, String blood, String newpwd)
			throws SQLException {
		conn = getConn();
		String s = null;
		if (n == 1) {// �һ�����
			String sql1 = "select * from myqquser where num = ? and name = ? and age = ? and blood = ? ";// �Ȳ�ѯ���û����Ƿ����
			ps = conn.prepareStatement(sql1);
			ps.setString(1, num);
			ps.setString(2, name);
			ps.setInt(3, age);
			ps.setString(4, blood);
			rs = ps.executeQuery();//////////////////
			if (rs != null)
				while (rs.next()) {
					s = rs.getString("password");
					System.out.println(s);
					break;
				}
			else {
				s = null;
			}
		} else {// ��������
			String sql2 = "update myqquser set password = ? where num = ? and name = ? and age = ? and blood = ? ";
			ps = conn.prepareStatement(sql2);
			ps.setString(1, newpwd);
			ps.setString(2, num);
			ps.setString(3, name);
			ps.setInt(4, age);
			ps.setString(5, blood);
			int result = ps.executeUpdate();
			if (result > 0) {
				s = "succeed";
			} else {
				s = "failed";
			}
		}
		return s;
	}
	//����
	public static void main(String[] args) {
		Userdao dao=new Userdao();
		List<QQUser> arrayList=dao.findBynum_like("%1%");
		System.out.println(""+arrayList.size());
		for (QQUser qqUser : arrayList) {
			System.out.println(qqUser.getId()+" "+qqUser.getName()+" "+qqUser.getNum());
		}
	}
}
