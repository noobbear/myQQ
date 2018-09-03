package jdbc;

import java.sql.*;

import javax.swing.JOptionPane;

public class JDBCTest {
	// Oracle JDBC驱动
	String driver = "com.mysql.jdbc.Driver";
	// Oracle数据库的连接url，需指定主机和端口及数据库名
	String url = "jdbc:mysql://localhost:3306/myqq?useUnicode=true&characterEncoding=UTF-8&useSSL=false";
	String user = "root"; // 用户账号
	String password = "123456"; // 用户密码
	Connection conn = null; // 数据库连接

	public Connection getConn() {
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "数据库加载失败", "tips", JOptionPane.OK_OPTION);
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "数据库连接失败", "tips", JOptionPane.OK_OPTION);
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
