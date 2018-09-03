package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jdbc.JDBCTest;
import pojo.Friend;
import pojo.QQUser;
import pojo.UserGroup;

public class Frienddao extends JDBCTest {
	ResultSet rs = null;
	PreparedStatement ps = null;
	Connection conn = null;
	QQUser user = null;
	Friend friend = null;
	List<Friend> friends = null;//���к���
	ArrayList<Integer> frids=null;//ĳ����ĺ���id
	
	public List<Friend> findBymyid(Integer myid) {// �ʺţ�����
		conn = getConn();
		String sql = "select * from friend where myid = ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, myid);
			rs = ps.executeQuery();
			if (rs != null) {
				friends = new ArrayList<Friend>();
				while (rs.next()) {
					friend = new Friend();
					friend.setId(rs.getInt("id"));
					friend.setMyid(rs.getInt("myid"));
					friend.setFid(rs.getInt("fid"));
					friend.setInwhichGroup(rs.getString("inwhichgroup"));
					friends.add(friend);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close(rs, ps, conn);
		}
		return friends;
	}

	//////////////////////////////////���� ������ָ������ĺ��ѵ�id
	public ArrayList<Integer> findFriendid(int myid,String groupname) {
		conn = getConn();
		String sql = "select * from friend where myid = ? and inwhichgroup = ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, myid);
			ps.setString(2, groupname);
			rs = ps.executeQuery();
			if (rs != null) {
				frids=new ArrayList<Integer>();
				while (rs.next()) {
					frids.add(rs.getInt("fid"));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close(rs, ps, conn);
		}
		return frids;
	}
//	//����
//	public static void main(String[] args) {
//		Frienddao dao=new Frienddao();
//		ArrayList<Integer> arrayList=dao.findFriendid(9, "Ĭ�Ϻ���");
//		System.out.println("Ĭ�Ϻ���");
//		for(int i=0;i<arrayList.size();i++)
//			System.out.print(arrayList.get(i));
//	}

	/// ****************************ɾ������,���ҵĺ����б���ɾ�����Ѽ��ɷ���true,Ȼ�����ŴӺ����б���ɾ���Լ�
	public boolean DelFriend(Integer myid, Integer fid) {
		conn = getConn();
		boolean bool = false;
		String sql1 = "delete from friend where myid = ? and fid = ?";
		try {
			ps = conn.prepareStatement(sql1);// ���ҵĺ����б���ɾ������
			ps.setInt(1, myid);
			ps.setInt(2, fid);
			int n = ps.executeUpdate();
			if (n > 0)
				bool = true;
			ps = conn.prepareStatement(sql1);// ����id��ɾ��һ��
			ps.setInt(1, fid);
			ps.setInt(2, myid);
			int result = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close(rs, ps, conn);
		}
		return bool;
	}

	// **************************���غ��� ���ҵĺ����б���ɾ�����Ѽ��ɷ���true,Ȼ�����ŴӺ����б���ɾ���Լ�
	public boolean DelFriend(Integer myid, String num) {
		conn = getConn();
		boolean bool1 = false, bool2 = false;
		Integer fid = -1;
		String sql = "select * from myqquser where num = ?";
	
		String sql1 = "delete from friend where myid = ? and fid = ?";
	
		try {
			ps = conn.prepareStatement(sql);// �Ȳ�ѯ�ʺ�Ϊnum���û�id
			ps.setString(1, num);
			rs = ps.executeQuery();
			while (rs.next()) {
				fid = rs.getInt("id");
				bool2 = true;
				break;
			}
			if (fid == -1)// �û�������ʱ�жϣ�����false
				return false;
	
			ps = conn.prepareStatement(sql1);// ���ҵĺ����б���ɾ������
			ps.setInt(1, myid);
			ps.setInt(2, fid);
			int n = ps.executeUpdate();
			if (n > 0)
				bool1 = true;
			ps = conn.prepareStatement(sql1);// ����id��ɾ��һ��
			ps.setInt(1, fid);
			ps.setInt(2, myid);
			int result = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close(rs, ps, conn);
		}
		return bool1 && bool2;
	}

	/// ***************************ƾ�û�ID��Ӻ���,���ж�fid�Ƿ����
	public boolean AddFriend(Integer myid, Integer fid, String group) {
		conn = getConn();
		boolean bool1 = false, bool2 = false;
		String s = "select * from myqquser where id=?";
		String sql = "insert into friend(myid,fid,inwhichgroup) values(?,?,?)";
		String sql2 = "insert into friend(myid,fid,inwhichgroup) values(?,?,?)";
		try {
			ps = conn.prepareStatement(s);
			ps.setInt(1, fid);
			rs = ps.executeQuery();
			int i=0;
			if (rs != null) {
				while (rs.next()) {
					i++;
					System.out.println(" "+i);
				}
				if(i==0)
					return false;
			}else 
				return false;
			ps = conn.prepareStatement(sql);
			ps.setInt(1, myid);
			ps.setInt(2, fid);
			ps.setString(3, group);
			int n = ps.executeUpdate();
			if (n > 0)
				bool1 = true;
			ps = conn.prepareStatement(sql2);
			ps.setInt(1, fid);
			ps.setInt(2, myid);
			ps.setString(3, "Ĭ�Ϻ���");
			int m = ps.executeUpdate();
			if (m > 0)
				bool2 = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close(rs, ps, conn);
		}
		return bool1 && bool2;
	}

	/////////////////// ���� ͨ���ʺ���Ӻ���
	public boolean AddFriend(Integer myid, String num, String group) {
		conn = getConn();
		boolean bool1 = false, bool2 = false;
		Integer fid = -1;
		String sql1 = "select * from myqquser where num = ?";
		String sql2 = "insert into friend(myid,fid,inwhichgroup) values(?,?,?)";
		String sql3 = "insert into friend(myid,fid,inwhichgroup) values(?,?,?)";
		try {
			ps = conn.prepareStatement(sql1);// �Ȳ�ѯ�ʺ�Ϊnum���û�id
			ps.setString(1, num);
			rs = ps.executeQuery();
			while (rs.next()) {
				fid = rs.getInt("id");
				break;
			}
			if (fid == -1)// �û�������ʱ�жϣ�����false
				return false;
			ps = conn.prepareStatement(sql2);
			ps.setInt(1, myid);
			ps.setInt(2, fid);
			ps.setString(3, group);
			int n = ps.executeUpdate();
			if (n > 0)
				bool1 = true;
			ps = conn.prepareStatement(sql3);
			ps.setInt(1, fid);
			ps.setInt(2, myid);
			ps.setString(3, "Ĭ�Ϻ���");//��ӵ�Ĭ�Ϻ���
			int m = ps.executeUpdate();
			if (m > 0)
				bool2 = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close(rs, ps, conn);
		}
		return bool1 && bool2;
	}
	//***************************�ƶ����ѵ�����
	public boolean MoveTO(int myid,int fid,String name) {
		boolean bl=false;
		conn=getConn();
		String sql="update friend set inwhichgroup = ? where myid = ? and fid =?";
		try {
			ps=conn.prepareStatement(sql);
			ps.setString(1, name);
			ps.setInt(2, myid);
			ps.setInt(3, fid);
			int n=ps.executeUpdate();
			if (n>0) {
				bl=true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bl;
	}
	//***************************�޸ķ�����,���޸�usergroup�е����֣����޸�friend�е�����
	public boolean Rename(int myid,int fid,String name){
		boolean bl=false;
//		conn=getConn();
//		String sql="update friend set inwhichgroup = ? where myid = ? and fid =?";
//		try {
//			ps=conn.prepareStatement(sql);
//			ps.setString(1, name);
//			ps.setInt(2, myid);
//			ps.setInt(3, fid);
//			
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return bl;
	}	
}
