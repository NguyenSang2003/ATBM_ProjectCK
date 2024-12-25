package DAO;

import Services.Connect;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PublicKeyDAO {

    // Lấy public key của người dùng theo id
    public String getPublicKeyByUserId(int userId) {
        Connection connection = null;
        String publicKey = null;

        try {
            connection = Connect.getConnection();
            String sql = "SELECT public_key FROM user_public_keys WHERE user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                publicKey = resultSet.getString("public_key");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching public key", e);
        } finally {
            Connect.closeConnection(connection);
        }

        return publicKey;
    }

    // Lưu hoặc cập nhật public key của người dùng
//    public void savePublicKey(int userId, String publicKey) {
//        String updateQuery = "UPDATE user_public_keys SET endTime = NOW() WHERE user_id = ? AND endTime IS NULL";
//        String insertQuery = "INSERT INTO user_public_keys (user_id, public_key, createTime, endTime, key_id) " +
//                "VALUES (?, ?, NOW(), NULL, ?)";
//
//        String newKeyId = UUID.randomUUID().toString(); // Tạo UUID
//
//        try (Connection connection = Connect.getConnection()) {
//            // Bắt đầu giao dịch
//            connection.setAutoCommit(false);
//
//            // Cập nhật các khóa hiện tại
//            try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
//                updateStatement.setInt(1, userId);
//                updateStatement.executeUpdate();
//            }
//
//            // Chèn khóa mới
//            try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
//                insertStatement.setInt(1, userId);
//                insertStatement.setString(2, publicKey);
//                insertStatement.setString(3, newKeyId); // Sử dụng UUID mới
//                insertStatement.executeUpdate();
//            }
//
//            // Commit giao dịch
//            connection.commit();
//        } catch (SQLException e) {
//            throw new RuntimeException("Error saving public key: " + e.getMessage(), e);
//        }
//    }

    public void savePublicKey(int userId, String publicKey) {
        String checkExistingKeyQuery = "SELECT COUNT(*) FROM user_public_keys WHERE user_id = ? AND endTime IS NULL";
        String updateQuery = "UPDATE user_public_keys SET endTime = NOW() WHERE user_id = ? AND endTime IS NULL";
        String insertQuery = "INSERT INTO user_public_keys (user_id, public_key, createTime, endTime, key_id) " +
                "VALUES (?, ?, NOW(), NULL, ?)";

        String newKeyId = UUID.randomUUID().toString();

        try (Connection connection = Connect.getConnection()) {
            connection.setAutoCommit(false);

            // Kiểm tra số lượng khóa hiện tại
            int existingKeyCount = 0;
            try (PreparedStatement checkStatement = connection.prepareStatement(checkExistingKeyQuery)) {
                checkStatement.setInt(1, userId);
                try (ResultSet rs = checkStatement.executeQuery()) {
                    if (rs.next()) {
                        existingKeyCount = rs.getInt(1);
                    }
                }
            }

            if (existingKeyCount > 0) {
                // Cập nhật khóa cũ
                try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                    updateStatement.setInt(1, userId);
                    updateStatement.executeUpdate();
                }
            }

            // Thêm khóa mới
            try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                insertStatement.setInt(1, userId);
                insertStatement.setString(2, publicKey);
                insertStatement.setString(3, newKeyId);
                insertStatement.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException("Error saving public key: " + e.getMessage(), e);
        }
    }


    // Thêm Public Key mới
    public boolean addPublicKey(int userId, String publicKey) {
        Connection connection = null;
        try {
            connection = Connect.getConnection();
            String sql = "INSERT INTO user_public_keys (user_id, public_key, createTime) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, publicKey);
            preparedStatement.setObject(3, LocalDateTime.now());
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);  // Log số dòng bị ảnh hưởng
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error adding public key", e);
        } finally {
            Connect.closeConnection(connection);
        }
    }


    // Lấy tất cả Public Key của một người dùng
    public List<String> getPublicKeysByUserId(int userId) {
        Connection connection = null;
        List<String> publicKeys = new ArrayList<>();
        try {
            connection = Connect.getConnection();
            String sql = "SELECT public_key FROM user_public_keys WHERE user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                publicKeys.add(resultSet.getString("public_key"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching public keys by user ID", e);
        } finally {
            Connect.closeConnection(connection);
        }
        return publicKeys;
    }

    // Lấy Public Key hiện tại (còn hiệu lực) của người dùng
    public String getActivePublicKeyByUserId(int userId) {
        Connection connection = null;
        String publicKey = null;
        try {
            connection = Connect.getConnection();
            String sql = "SELECT public_key FROM user_public_keys WHERE user_id = ? AND endTime IS NULL";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                publicKey = resultSet.getString("public_key");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching active public key", e);
        } finally {
            Connect.closeConnection(connection);
        }
        return publicKey;
    }

    // Vô hiệu hóa Public Key hiện tại của một người dùng
    public boolean deactivatePublicKey(int userId) {
        Connection connection = null;
        try {
            connection = Connect.getConnection();
            String sql = "UPDATE user_public_keys SET endTime = ? WHERE user_id = ? AND endTime IS NULL";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, LocalDateTime.now());
            preparedStatement.setInt(2, userId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deactivating public key", e);
        } finally {
            Connect.closeConnection(connection);
        }
    }

    // Kiểm tra xem Public Key có hợp lệ không (dùng để xác minh chữ ký)
    public boolean isPublicKeyValid(String publicKey) {
        Connection connection = null;
        try {
            connection = Connect.getConnection();
            String sql = "SELECT * FROM user_public_keys WHERE public_key = ? AND endTime IS NULL";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, publicKey);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException("Error checking public key validity", e);
        } finally {
            Connect.closeConnection(connection);
        }
    }

    // Lấy danh sách tất cả Public Keys
    public List<String> getAllPublicKeys() {
        Connection connection = null;
        List<String> publicKeys = new ArrayList<>();
        try {
            connection = Connect.getConnection();
            String sql = "SELECT public_key FROM user_public_keys";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                publicKeys.add(resultSet.getString("public_key"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching all public keys", e);
        } finally {
            Connect.closeConnection(connection);
        }
        return publicKeys;
    }

    // Xóa tất cả Public Key của một người dùng
    public boolean deleteAllPublicKeysByUserId(int userId) {
        Connection connection = null;
        try {
            connection = Connect.getConnection();
            String sql = "DELETE FROM user_public_keys WHERE user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting public keys", e);
        } finally {
            Connect.closeConnection(connection);
        }
    }


}