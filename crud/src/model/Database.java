package model;

import java.sql.*;

public class Database 
{
    private Statement stmt;
    private ResultSet rs;
    private Connection conn;
    private final String db_url = "jdbc:mysql://localhost:3306/javafx_project";
    private final String db_username = "root";
    private final String db_password = "";

    public void connect() throws SQLException
    {
        conn = DriverManager.getConnection(db_url, db_username, db_password);
        stmt = conn.createStatement();
    }

    public void disconnect() throws SQLException
    {
        if (conn != null && !conn.isClosed()) 
        {
            conn.close();
        }
        if (stmt != null && !stmt.isClosed()) 
        {
            stmt.close();
        }
        if (rs != null && !rs.isClosed()) 
        {
            rs.close();
        }
    }

    public ResultSet getResultSet(String query) throws SQLException
    {
        connect();
        rs = stmt.executeQuery(query);
        return rs;
    }

    public void executeQuery(String query) throws SQLException
    {
        connect();
        stmt.executeUpdate(query);
        disconnect();
    }
}
