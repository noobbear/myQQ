package jdbc;

import java.sql.*;

import javax.swing.JOptionPane;

public class JDBCTest {
	// Oracle JDBC����
	String driver = "com.mysql.jdbc.Driver";
	// Oracle���ݿ������url����ָ�������Ͷ˿ڼ����ݿ���
	String url = "jdbc:mysql://localhost:3306/myqq?useUnicode=true&characterEncoding=UTF-8&useSSL=false";
	String user = "root"; // �û��˺�
	String password = "123456"; // �û�����
	Connection conn = null; // ���ݿ�����

	public Connection getConn() {
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "���ݿ����ʧ��", "tips", JOptionPane.OK_OPTION);
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "���ݿ�����ʧ��", "tips", JOptionPane.OK_OPTION);
		}
		return conn;
	}

	public void close(ResultSet rs, PreparedStatement ps, Connection conn) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
