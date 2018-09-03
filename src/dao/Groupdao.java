package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import jdbc.JDBCTest;
import pojo.UserGroup;

public class Groupdao extends JDBCTest {
	ResultSet rs = null;
	PreparedStatement ps = null;
	Connection conn = null;

	public UserGroup getGroup(Integer id) {//ƾ�ҵ�id�����ҵķ���
		UserGroup group = new UserGroup();
		conn = getConn();
		String sql = "select * from usergroup where id = ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					group.addGroup(rs.getString("groupname"));
				}

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			close(rs, ps, conn);
		}
		return group;
	}
	//****************���һ�����飺
	public boolean addGroup(int id,String name){
		boolean bl=false;
		conn=getConn();
		String sql="insert into usergroup(id,groupname) values(?,?)";
		try {
			ps=conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setString(2, name);
			int n=ps.executeUpdate();
			if(n>0){
				bl=true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bl;
	}
	//****************ɾ��һ���շ��飺
		public boolean DelGroup(int id,String name){
			boolean bl=false;
			conn=getConn();
			String sql="delete from usergroup where id=? and groupname=?";
			try {
				ps=conn.prepareStatement(sql);
				ps.setInt(1, id);
				ps.setString(2, name);
				int n=ps.executeUpdate();
				if(n>0){
					bl=true;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return bl;
		}
///////////////////////���Ժ���
//	public static void main(String[] args) {
//		boolean n = new Groupdao().DelGroup(9, "û����");
//		System.out.println(n);
//		
//	}
//		
}
