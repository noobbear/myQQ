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
	List<Friend> friends = null;//所有好友
	ArrayList<Integer> frids=null;//某分组的好友id
	
	public List<Friend> findBymyid(Integer myid) {// 帐号，密码
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

	//////////////////////////////////返回 所有在指定分组的好友的id
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
//	//测试
//	public static void main(String[] args) {
//		Frienddao dao=new Frienddao();
//		ArrayList<Integer> arrayList=dao.findFriendid(9, "默认好友");
//		System.out.println("默认好友");
//		for(int i=0;i<arrayList.size();i++)
//			System.out.print(arrayList.get(i));
//	}

	/// ****************************删除好友,从我的好友列表中删掉好友即可返回true,然后试着从好友列表中删除自己
	public boolean DelFriend(Integer myid, Integer fid) {
		conn = getConn();
		boolean bool = false;
		String sql1 = "delete from friend where myid = ? and fid = ?";
		try {
			ps = conn.prepareStatement(sql1);// 从我的好友列表中删掉好友
			ps.setInt(1, myid);
			ps.setInt(2, fid);
			int n = ps.executeUpdate();
			if (n > 0)
				bool = true;
			ps = conn.prepareStatement(sql1);// 交换id再删除一次
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

	// **************************重载函数 从我的好友列表中删掉好友即可返回true,然后试着从好友列表中删除自己
	public boolean DelFriend(Integer myid, String num) {
		conn = getConn();
		boolean bool1 = false, bool2 = false;
		Integer fid = -1;
		String sql = "select * from myqquser where num = ?";
	
		String sql1 = "delete from friend where myid = ? and fid = ?";
	
		try {
			ps = conn.prepareStatement(sql);// 先查询帐号为num的用户id
			ps.setString(1, num);
			rs = ps.executeQuery();
			while (rs.next()) {
				fid = rs.getInt("id");
				bool2 = true;
				break;
			}
			if (fid == -1)// 用户不存在时中断，返回false
				return false;
	
			ps = conn.prepareStatement(sql1);// 从我的好友列表中删掉好友
			ps.setInt(1, myid);
			ps.setInt(2, fid);
			int n = ps.executeUpdate();
			if (n > 0)
				bool1 = true;
			ps = conn.prepareStatement(sql1);// 交换id再删除一次
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

	/// ***************************凭用户ID添加好友,先判断fid是否存在
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
			ps.setString(3, "默认好友");
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

	/////////////////// 重载 通过帐号添加好友
	public boolean AddFriend(Integer myid, String num, String group) {
		conn = getConn();
		boolean bool1 = false, bool2 = false;
		Integer fid = -1;
		String sql1 = "select * from myqquser where num = ?";
		String sql2 = "insert into friend(myid,fid,inwhichgroup) values(?,?,?)";
		String sql3 = "insert into friend(myid,fid,inwhichgroup) values(?,?,?)";
		try {
			ps = conn.prepareStatement(sql1);// 先查询帐号为num的用户id
			ps.setString(1, num);
			rs = ps.executeQuery();
			while (rs.next()) {
				fid = rs.getInt("id");
				break;
			}
			if (fid == -1)// 用户不存在时中断，返回false
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
			ps.setString(3, "默认好友");//添加到默认好友
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
	//***************************移动好友到分组
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
	//***************************修改分组名,先修改usergroup中的名字，再修改friend中的名字
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
