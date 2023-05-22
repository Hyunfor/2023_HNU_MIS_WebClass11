package util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DBManager {

	// 공공자원
	public static Connection getConnection() throws Exception {

		Context initContext = new InitialContext();
		Context envContext = (Context) initContext.lookup("java:/comp/env");
		DataSource ds = (DataSource) envContext.lookup("jdbc/myoracle");
		Connection conn = ds.getConnection();

		return conn;

	}

	// select connection close
	public static void close(Connection conn, Statement stmt, ResultSet rs) throws Exception {

		rs.close();
		stmt.close();
		conn.close();

	}

	// insert, update, delete connection close
	public static void close(Connection conn, Statement stmt) throws Exception {

		stmt.close();
		conn.close();

	}

}
