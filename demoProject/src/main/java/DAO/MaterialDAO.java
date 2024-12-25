package DAO;

import Model.Material;
import Services.Connect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MaterialDAO {
    // Lấy tên vật liệu từ ID
    public String getMaterialNameById(int idMaterial) {
        String materialName = null;
        Connection connection = null;

        try {
            connection = Connect.getConnection();
            String sql = "SELECT nameMaterial FROM Material WHERE idMaterial = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idMaterial);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                materialName = resultSet.getString("nameMaterial");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Connect.closeConnection(connection);
        }

        return materialName;
    }

    // Lấy tất cả các vật liệu
    public List<Material> getAllMaterials() {
        List<Material> materials = new ArrayList<>();
        Connection connection = null;

        try {
            connection = Connect.getConnection();
            String sql = "SELECT idMaterial, nameMaterial, description, priceMaterial FROM Material";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Material material = new Material(
                        resultSet.getInt("idMaterial"),
                        resultSet.getString("nameMaterial"),
                        resultSet.getString("description"),
                        resultSet.getInt("priceMaterial") // Lấy giá tiền từ database
                );
                materials.add(material);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Connect.closeConnection(connection);
        }

        return materials;
    }

    // Lấy vật liệu theo id
    public static Material getMaterialById(int idMaterial) {
        Material material = null;
        Connection connection = null;

        try {
            connection = Connect.getConnection();
            String sql = "SELECT idMaterial, nameMaterial, description, priceMaterial FROM Material WHERE idMaterial = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idMaterial);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                material = new Material(
                        resultSet.getInt("idMaterial"),
                        resultSet.getString("nameMaterial"),
                        resultSet.getString("description"),
                        resultSet.getInt("priceMaterial") // Lấy giá tiền
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Connect.closeConnection(connection);
        }

        return material;
    }

    // Thêm vật liệu mới
    public boolean addMaterial(Material material) {
        Connection connection = null;

        try {
            connection = Connect.getConnection();
            String sql = "INSERT INTO Material (nameMaterial, description, priceMaterial) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, material.getNameMaterial());
            preparedStatement.setString(2, material.getDescription());
            preparedStatement.setInt(3, material.getPriceMaterial()); // Chèn giá tiền

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Connect.closeConnection(connection);
        }
    }

    // Cập nhật vật liệu
    public boolean updateMaterial(int idMaterial, String nameMaterial, String description, int priceMaterial) {
        Connection connection = null;

        try {
            connection = Connect.getConnection();
            String sql = "UPDATE Material SET nameMaterial = ?, description = ?, priceMaterial = ? WHERE idMaterial = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, nameMaterial);
            preparedStatement.setString(2, description);
            preparedStatement.setInt(3, priceMaterial); // Cập nhật giá tiền
            preparedStatement.setInt(4, idMaterial);

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Connect.closeConnection(connection);
        }
    }

    // Xóa vật liệu
    public boolean deleteMaterial(int idMaterial) {
        Connection connection = null;

        try {
            connection = Connect.getConnection();
            String sql = "DELETE FROM Material WHERE idMaterial = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idMaterial);

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Connect.closeConnection(connection);
        }
    }

    // Kiểm tra xem materialId có hợp lệ hay không
    public static boolean isValidMaterial(int materialId) {
        Connection connection = null;
        boolean isValid = false;

        try {
            connection = Connect.getConnection();
            String sql = "SELECT 1 FROM Material WHERE idMaterial = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, materialId);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Nếu tìm thấy một bản ghi thì materialId hợp lệ
            isValid = resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Connect.closeConnection(connection);
        }

        return isValid;
    }

//    // Hàm main để kiểm tra
//    public static void main(String[] args) {
//        MaterialDAO materialDAO = new MaterialDAO();
//        materialDAO.isValidMaterial(122);
//        System.out.println(materialDAO.isValidMaterial(122));
//
//        // Thêm vật liệu mới
//        Material newMaterial = new Material(0, "Wood", "Strong wood material", 500);
//        if (materialDAO.addMaterial(newMaterial)) {
//            System.out.println("Thêm vật liệu thành công!");
//        }
//
//        // Lấy tất cả vật liệu
//        List<Material> materials = materialDAO.getAllMaterials();
//        for (Material material : materials) {
//            System.out.println("ID: " + material.getIdMaterial()
//                    + ", Tên: " + material.getNameMaterial()
//                    + ", Mô tả: " + material.getDescription()
//                    + ", Giá: " + material.getPrice());
//        }
//
//        // Cập nhật vật liệu
//        if (materialDAO.updateMaterial(1, "Steel", "Updated strong steel", 700)) {
//            System.out.println("Cập nhật vật liệu thành công!");
//        }
//
//        // Xóa vật liệu
//        if (materialDAO.deleteMaterial(1)) {
//            System.out.println("Xóa vật liệu thành công!");
//        }
//    }
}