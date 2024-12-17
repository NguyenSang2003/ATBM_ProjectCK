package DAO;

import Model.Size;
import Services.Connect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SizeDAO {

    // Lấy tất cả các kích thước
    public List<Size> getAllSizes() {
        List<Size> sizes = new ArrayList<>();
        Connection connection = null;

        try {
            connection = Connect.getConnection();
            String sql = "SELECT idSize, nameSize, width, height, priceSize FROM Size";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Size size = new Size();
                size.setIdSize(resultSet.getInt("idSize"));
                size.setNameSize(resultSet.getString("nameSize"));
                size.setWidth(resultSet.getInt("width"));
                size.setHeight(resultSet.getInt("height"));
                size.setPriceSize(resultSet.getInt("priceSize")); // Lấy giá tiền
                sizes.add(size);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Connect.closeConnection(connection);
        }

        return sizes;
    }

    // Lấy thông tin kích thước theo id
    public static Size getSizeById(int idSize) {
        Size size = null;
        Connection connection = null;

        try {
            connection = Connect.getConnection();
            String sql = "SELECT idSize, nameSize, width, height, priceSize FROM Size WHERE idSize = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idSize);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                size = new Size();
                size.setIdSize(resultSet.getInt("idSize"));
                size.setNameSize(resultSet.getString("nameSize"));
                size.setWidth(resultSet.getInt("width"));
                size.setHeight(resultSet.getInt("height"));
                size.setPriceSize(resultSet.getInt("priceSize")); // Lấy giá tiền
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Connect.closeConnection(connection);
        }

        return size;
    }

    // Thêm một kích thước mới
    public boolean addSize(Size size) {
        Connection connection = null;

        try {
            connection = Connect.getConnection();
            String sql = "INSERT INTO Size (nameSize, width, height, priceSize) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, size.getNameSize());
            preparedStatement.setInt(2, size.getWidth());
            preparedStatement.setInt(3, size.getHeight());
            preparedStatement.setDouble(4, size.getPriceSize()); // Chèn giá tiền

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Connect.closeConnection(connection);
        }
    }

    // Cập nhật kích thước
    public boolean updateSize(int idSize, String nameSize, int width, int height, double priceSize) {
        Connection connection = null;

        try {
            connection = Connect.getConnection();
            String sql = "UPDATE Size SET nameSize = ?, width = ?, height = ?, priceSize = ? WHERE idSize = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, nameSize);
            preparedStatement.setInt(2, width);
            preparedStatement.setInt(3, height);
            preparedStatement.setDouble(4, priceSize); // Cập nhật giá tiền
            preparedStatement.setInt(5, idSize);

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Connect.closeConnection(connection);
        }
    }

    // Xóa kích thước theo id
    public boolean deleteSize(int idSize) {
        Connection connection = null;

        try {
            connection = Connect.getConnection();
            String sql = "DELETE FROM Size WHERE idSize = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idSize);

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Connect.closeConnection(connection);
        }
    }

    // Hàm main để kiểm tra
//    public static void main(String[] args) {
//        SizeDAO sizeDAO = new SizeDAO();
//
//        // Thêm một kích thước mới
//        Size newSize = new Size(0, "XL", 100, 200, 1500);
//        if (sizeDAO.addSize(newSize)) {
//            System.out.println("Thêm kích thước thành công!");
//        }
//
//        // Lấy tất cả các kích thước
//        List<Size> sizes = sizeDAO.getAllSizes();
//        for (Size size : sizes) {
//            System.out.println("ID: " + size.getIdSize()
//                    + ", Tên: " + size.getNameSize()
//                    + ", Width: " + size.getWidth()
//                    + ", Height: " + size.getHeight()
//                    + ", Giá: " + size.getPriceSize());
//        }
//
//        // Cập nhật kích thước
//        if (sizeDAO.updateSize(1, "XXL", 120, 240, 2000.0)) {
//            System.out.println("Cập nhật kích thước thành công!");
//        }
//
//        // Xóa kích thước
//        if (sizeDAO.deleteSize(1)) {
//            System.out.println("Xóa kích thước thành công!");
//        }
//    }
}