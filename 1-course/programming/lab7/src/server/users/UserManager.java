package server.users;

import java.sql.*;

public class UserManager {
    private final Connection connection;

    public UserManager(Connection connection) {
        this.connection = connection;
    }

    public boolean registerUser(String username, String passwordHash) throws SQLException {
        String sql = "INSERT INTO users (username, password_md5) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, passwordHash);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                return false;
            }
            throw e;
        }
    }

    public boolean authenticateUser(String username, String passwordHash) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ? AND password_md5 = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, passwordHash);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }
}

