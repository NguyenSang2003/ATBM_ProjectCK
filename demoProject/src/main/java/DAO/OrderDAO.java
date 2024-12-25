package DAO;

import Services.Connect;
import Model.OddImage;
import Model.Order;
import Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class OrderDAO {
    java.util.Date utilDate = new java.util.Date();
    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
    ProductDAO productDAO = new ProductDAO();
    Calendar calendar = Calendar.getInstance();
    int currentYear = calendar.get(Calendar.YEAR);
    int currentMonth = calendar.get(Calendar.MONTH) + 1;

    // dùng để thêm đơn hàng mới
    public boolean insertOrderOdd(int idOddImage, int idUser, String receiver, String phoneNumber, int quantity, double totalPrice, String address, int idMaterial, int idSize, String signature, boolean verified, boolean isTampered) {
        Connection connection = null;
        try {
            connection = Connect.getConnection();
            String sql = "INSERT INTO OddImageOrder(idOddImage, idUser, receiver, phoneNumber, quantity, totalPrice, status, address, purchareDate, idMaterial, idSize, Signature, Verified, isTampered) " +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Kiểm tra giá trị được truyền vào
            System.out.println("idMaterial: " + idMaterial);
            preparedStatement.setInt(1, idOddImage);
            preparedStatement.setInt(2, idUser);
            preparedStatement.setString(3, receiver);
            preparedStatement.setString(4, phoneNumber);
            preparedStatement.setInt(5, quantity);
            preparedStatement.setDouble(6, totalPrice);
            preparedStatement.setString(7, "Đang chuẩn bị");
            preparedStatement.setString(8, address);
            preparedStatement.setDate(9, new java.sql.Date(System.currentTimeMillis())); // Ngày hiện tại
            preparedStatement.setInt(10, idMaterial);
            preparedStatement.setInt(11, idSize);
            preparedStatement.setString(12, signature);
            preparedStatement.setBoolean(13, verified);
            preparedStatement.setBoolean(14, isTampered);

            int check = preparedStatement.executeUpdate();
            if (check > 0) {
                System.out.println("Đơn hàng đã được lưu vào OddImageOrder");
                return true;
            } else {
                System.out.println("Không thể lưu đơn hàng vào OddImageOrder");
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi chèn dữ liệu vào OddImageOrder: " + e.getMessage());
            throw new RuntimeException("Lỗi khi chèn dữ liệu vào OddImageOrder", e);
        } finally {
            Connect.closeConnection(connection);
        }
        return false;
    }


    //mới
//    public boolean insertOddImageOrder(int idUser, String receiver, String phoneNumber, int quantity, int totalPrice, String address, String orderDetails) {
//        Connection connection = null;
//        try {
//            connection = Connect.getConnection(); // Sử dụng kết nối từ Connect giống hàm insertOrderOdd
//            String sql = "INSERT INTO oddimageorder (idUser, receiver, phoneNumber, quantity, totalPrice, address, Signature, Verified, isTampered) " +
//                    "VALUES (?, ?, ?, ?, ?, ?, NULL, 0, 0)";
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setInt(1, idUser);
//            preparedStatement.setString(2, receiver);
//            preparedStatement.setString(3, phoneNumber);
//            preparedStatement.setInt(4, quantity);
//            preparedStatement.setInt(5, totalPrice);
//            preparedStatement.setString(6, address);
//
//            int check = preparedStatement.executeUpdate();
//            if (check > 0) {
//                return true;
//            }
//        } catch (Exception e) {
//            throw new RuntimeException("Lỗi khi chèn dữ liệu vào oddimageorder: " + e.getMessage(), e);
//        } finally {
//            Connect.closeConnection(connection); // Đảm bảo đóng kết nối
//        }
//        return false;
//    }


    public boolean inserOrderCart(String name, int idUser, String receiver, String phoneNumber, int quantity, int totalPrice, String address) {
        Connection connection = null;
        ProductDAO productDAO = new ProductDAO();
        try {
            connection = Connect.getConnection();
            String sql = "insert into CartOrder(name,idUser,receiver, phoneNumber, quantity ,totalPrice, status, address, purchareDate) values(?,?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, idUser);
            preparedStatement.setString(3, receiver);
            preparedStatement.setString(4, phoneNumber);
            preparedStatement.setInt(5, quantity);
            preparedStatement.setInt(6, totalPrice);
            preparedStatement.setString(7, "Đang chuẩn bị");
            preparedStatement.setString(8, address);
            preparedStatement.setDate(9, sqlDate);
            int check = preparedStatement.executeUpdate();
            if (check > 0) {
                return true;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            Connect.closeConnection(connection);
        }
        return false;
    }

    public ArrayList<Order> getAllOrderOddImageForUser(int idUser) {
        Connection connection = null;
        ArrayList<Order> listOrder = new ArrayList<>();
        ProductDAO productDAO = new ProductDAO();
        try {
            connection = Connect.getConnection();
//            String sql = "select idOrder, idOddImage,receiver, phoneNumber, quantity, address, status ,
//            purchareDate ,totalPrice from oddImageOrder where idUser = ? and status not like  ?";
            String sql = "SELECT o.idOrder, o.idOddImage, o.receiver, o.phoneNumber, o.quantity, o.address, " +
                    "o.status, o.purchareDate, o.totalPrice, m.nameMaterial AS materialName, s.nameSize AS sizeName " + // Thêm khoảng trắng ở cuối
                    "FROM oddImageOrder o " +
                    "LEFT JOIN Material m ON o.idMaterial = m.idMaterial " +
                    "LEFT JOIN Size s ON o.idSize = s.idSize " +
                    "WHERE o.idUser = ? AND o.status NOT LIKE ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idUser);
            preparedStatement.setString(2, "%Đã hủy%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setIdOrder(resultSet.getInt("idOrder"));
                order.setNameProduct(productDAO.getOddImageByIdForAdminUpdate(resultSet.getInt("idOddImage")).getName());
                order.setQuantity(resultSet.getInt("quantity"));
                order.setAddress(resultSet.getString("address"));
                order.setStatus(resultSet.getString("status"));
                order.setPurchareDate(resultSet.getDate("purchareDate"));
                order.setTotalPrice(resultSet.getInt("totalPrice"));
                order.setReceiver(resultSet.getString("receiver"));
                order.setPhoneNumber(resultSet.getString("phoneNumber"));
                order.setType("odd");
                order.setIdProduct(resultSet.getInt("idOddImage"));

                order.setNameMaterial(resultSet.getString("materialName"));
                order.setNameSize(resultSet.getString("sizeName"));

                listOrder.add(order);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            Connect.closeConnection(connection);
        }
        return listOrder;
    }

    public ArrayList<Order> getAllCartOrderForUser(int idUser) {
        Connection connection = null;
        ArrayList<Order> listOrder = new ArrayList<>();
        ProductDAO productDAO = new ProductDAO();
        try {
            connection = Connect.getConnection();
            String sql = "select idOrder,name,receiver, phoneNumber, quantity, address, status , purchareDate , totalPrice from CartOrder where idUser = ? and status not like  ?";
//            String sql = "SELECT o.idOrder, o.idOddImage, o.receiver, o.phoneNumber, o.quantity, o.address, " +
//                    "o.status, o.purchareDate, o.totalPrice, m.nameMaterial AS materialName, s.nameSize AS sizeName " +
//                    "FROM oddImageOrder o " +
//                    "LEFT JOIN Material m ON o.idMaterial = m.idMaterial " +
//                    "LEFT JOIN Size s ON o.idSize = s.idSize " +
//                    "WHERE o.idUser = ? AND o.status NOT LIKE ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idUser);
            preparedStatement.setString(2, "%Đã hủy%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setIdOrder(resultSet.getInt("idOrder"));
                order.setNameProduct(productDAO.getOddImageByIdForAdminUpdate(resultSet.getInt("idOddImage")).getName());
                order.setQuantity(resultSet.getInt("quantity"));
                order.setAddress(resultSet.getString("address"));
                order.setStatus(resultSet.getString("status"));
                order.setPurchareDate(resultSet.getDate("purchareDate"));
                order.setTotalPrice(resultSet.getInt("totalPrice"));
                order.setReceiver(resultSet.getString("receiver"));
                order.setPhoneNumber(resultSet.getString("phoneNumber"));
                order.setType("odd");
                order.setIdProduct(resultSet.getInt("idOddImage"));

//                order.setNameMaterial(resultSet.getString("materialName"));
//                order.setNameSize(resultSet.getString("sizeName"));

                listOrder.add(order);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            Connect.closeConnection(connection);
        }
        return listOrder;
    }

    public String getStatusOddById(String idOrder) {
        Connection connection = null;
        try {
            connection = Connect.getConnection();
            String sql = "select  status from oddImageOrder where  idOrder = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, idOrder);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("status");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Connect.closeConnection(connection);
        }

        return "";
    }

    public String getStatusCartOrderById(String idOrder) {
        Connection connection = null;
        try {
            connection = Connect.getConnection();
            String sql = "select  status from CartOrder where  idOrder = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, idOrder);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("status");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Connect.closeConnection(connection);
        }

        return "";
    }

    public boolean deleteOddImageOrder(String idOrder) {
        Connection connection = null;
        try {
            connection = Connect.getConnection();
            String sql = "update  OddImageOrder  set status = ? where idOrder = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "Đã hủy");
            preparedStatement.setString(2, idOrder);
            int check = preparedStatement.executeUpdate();
            if (check > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Connect.closeConnection(connection);
        }

        return false;
    }

    public boolean deleteCartOrder(String idOrder) {
        Connection connection = null;
        try {
            connection = Connect.getConnection();
            String sql = "update  CartOrder  set status = ? where idOrder = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "Đã hủy");
            preparedStatement.setString(2, idOrder);
            int check = preparedStatement.executeUpdate();
            if (check > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Connect.closeConnection(connection);
        }

        return false;
    }

    public ArrayList<OddImage> getTop8OddImageOrder() {
        Connection connection = null;
        ArrayList<OddImage> list8OddImageOrder = new ArrayList<>();
        ProductDAO productDAO = new ProductDAO();
        try {
            connection = Connect.getConnection();
            String sql = "select idOddImage, count(idOddImage) from oddImageOrder GROUP BY idOddImage order by count(idOddImage) desc LIMIT 8";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int idOddImage = resultSet.getInt("idOddImage");
                OddImage oddImage = productDAO.getOddImageById(idOddImage);
                if (oddImage.getName() != null) {

                    list8OddImageOrder.add(oddImage);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Connect.closeConnection(connection);
        }
        return list8OddImageOrder;
    }


    //    for admin
    public ArrayList<Order> getAllOrderOddImageForAdmin(int page, int recSize) {
        Connection connection = null;
        ArrayList<Order> listOrder = new ArrayList<>();
        ProductDAO productDAO = new ProductDAO();
        int startIndex = (page - 1) * recSize;
        try {
            connection = Connect.getConnection();
            String sql = "select idOrder, idOddImage,idUser,receiver, phoneNumber, quantity, address, status , purchareDate ,totalPrice from oddImageOrder LIMIT ? OFFSET ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, recSize);
            preparedStatement.setInt(2, startIndex);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setIdOrder(resultSet.getInt("idOrder"));
                order.setNameProduct(productDAO.getOddImageByIdForAdminUpdate(resultSet.getInt("idOddImage")).getName());
                order.setQuantity(resultSet.getInt("quantity"));
                order.setAddress(resultSet.getString("address"));
                order.setStatus(resultSet.getString("status"));
                order.setPurchareDate(resultSet.getDate("purchareDate"));
                order.setTotalPrice(resultSet.getInt("totalPrice"));
                order.setReceiver(resultSet.getString("receiver"));
                order.setPhoneNumber(resultSet.getString("phoneNumber"));
                order.setIdByer(resultSet.getInt("idUser"));
                order.setType("odd");
                order.setIdProduct(resultSet.getInt("idOddImage"));
                listOrder.add(order);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            Connect.closeConnection(connection);
        }
        return listOrder;
    }

    public ArrayList<Order> getAllCartOrderForAdmin(int page, int recSize) {
        Connection connection = null;
        ArrayList<Order> listOrder = new ArrayList<>();
        ProductDAO productDAO = new ProductDAO();
        int startIndex = (page - 1) * recSize;
        try {
            connection = Connect.getConnection();
            String sql = "select idOrder, name,idUser,receiver, phoneNumber, quantity, address, status , purchareDate ,totalPrice from CartOrder  LIMIT ? OFFSET ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, recSize);
            preparedStatement.setInt(2, startIndex);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setIdOrder(resultSet.getInt("idOrder"));
                order.setNameProduct(resultSet.getString("name"));
                order.setQuantity(resultSet.getInt("quantity"));
                order.setAddress(resultSet.getString("address"));
                order.setStatus(resultSet.getString("status"));
                order.setPurchareDate(resultSet.getDate("purchareDate"));
                order.setTotalPrice(resultSet.getInt("totalPrice"));
                order.setReceiver(resultSet.getString("receiver"));
                order.setPhoneNumber(resultSet.getString("phoneNumber"));
                order.setIdByer(resultSet.getInt("idUser"));
                order.setType("cart");
                listOrder.add(order);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            Connect.closeConnection(connection);
        }
        return listOrder;
    }

    public ArrayList<Order> getAllCartForAdmin() {
        Connection connection = null;
        ArrayList<Order> listOrder = new ArrayList<>();
        ProductDAO productDAO = new ProductDAO();
        try {
            connection = Connect.getConnection();
            String sql = "select idOrder,name,idUser,receiver, phoneNumber, quantity, address, status , purchareDate ,totalPrice from CartOrder";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setIdOrder(resultSet.getInt("idOrder"));
                order.setNameProduct(resultSet.getString("name"));
                order.setQuantity(resultSet.getInt("quantity"));
                order.setAddress(resultSet.getString("address"));
                order.setStatus(resultSet.getString("status"));
                order.setPurchareDate(resultSet.getDate("purchareDate"));
                order.setTotalPrice(resultSet.getInt("totalPrice"));
                order.setReceiver(resultSet.getString("receiver"));
                order.setPhoneNumber(resultSet.getString("phoneNumber"));
                order.setIdByer(resultSet.getInt("idUser"));
                order.setType("cart");
                listOrder.add(order);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            Connect.closeConnection(connection);
        }
        return listOrder;
    }

    public ArrayList<Order> getAllOrderOddImageForByStatus(String status) {
        Connection connection = null;
        ArrayList<Order> listOrder = new ArrayList<>();
        ProductDAO productDAO = new ProductDAO();
        try {
            connection = Connect.getConnection();
            String sql = "select idOrder,idUser, idOddImage,receiver, phoneNumber, quantity, address, status , purchareDate ,totalPrice from oddImageOrder where status = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, status);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setIdOrder(resultSet.getInt("idOrder"));
                order.setNameProduct(productDAO.getOddImageById(resultSet.getInt("idOddImage")).getName());
                order.setQuantity(resultSet.getInt("quantity"));
                order.setAddress(resultSet.getString("address"));
                order.setStatus(resultSet.getString("status"));
                order.setPurchareDate(resultSet.getDate("purchareDate"));
                order.setTotalPrice(resultSet.getInt("totalPrice"));
                order.setReceiver(resultSet.getString("receiver"));
                order.setPhoneNumber(resultSet.getString("phoneNumber"));
                order.setIdByer(resultSet.getInt("idUser"));
                order.setType("odd");
                order.setIdProduct(resultSet.getInt("idOddImage"));
                listOrder.add(order);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            Connect.closeConnection(connection);
        }
        return listOrder;
    }

    public ArrayList<Order> getAllCartOrderForByStatus(String status) {
        Connection connection = null;
        ArrayList<Order> listOrder = new ArrayList<>();
        ProductDAO productDAO = new ProductDAO();
        try {
            connection = Connect.getConnection();
            String sql = "select idOrder,idUser, name,receiver, phoneNumber, quantity, address, status , purchareDate ,totalPrice from CartOrder where status = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, status);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setIdOrder(resultSet.getInt("idOrder"));
                order.setNameProduct(resultSet.getString("idOddImage"));
                order.setQuantity(resultSet.getInt("quantity"));
                order.setAddress(resultSet.getString("address"));
                order.setStatus(resultSet.getString("status"));
                order.setPurchareDate(resultSet.getDate("purchareDate"));
                order.setTotalPrice(resultSet.getInt("totalPrice"));
                order.setReceiver(resultSet.getString("receiver"));
                order.setPhoneNumber(resultSet.getString("phoneNumber"));
                order.setIdByer(resultSet.getInt("idUser"));
                order.setType("cart");
                order.setIdProduct(resultSet.getInt("idOddImage"));
                listOrder.add(order);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            Connect.closeConnection(connection);
        }
        return listOrder;
    }

    public boolean checkUserOrderOddImage(int idUser, int idOddImage) {
        Connection connection = null;
        try {
            connection = Connect.getConnection();
            String sql = "select idUser from OddImageOrder where idUser= ? and idOddImage = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idUser);
            preparedStatement.setInt(2, idOddImage);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Connect.closeConnection(connection);
        }
        return false;
    }

    //    get order
    public Order getOrderOddEdit(String idOrder) {
        Connection connection = null;
        Order order = new Order();
        try {
            connection = Connect.getConnection();
            String sql = "select idOrder,idOddImage, receiver ,totalPrice, phoneNumber, address, status from oddImageOrder where idOrder = ? and status not like ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, idOrder);
            preparedStatement.setString(2, "%Đã hủy%");
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                order.setIdOrder(resultSet.getInt("idOrder"));
                order.setReceiver(resultSet.getString("receiver"));
                order.setPhoneNumber(resultSet.getString("phoneNumber"));
                order.setAddress(resultSet.getString("address"));
                order.setStatus(resultSet.getString("status"));
                order.setTotalPrice(resultSet.getInt("totalPrice"));
                order.setType("odd");
                order.setNameProduct(productDAO.getOddImageByIdForAdminUpdate(resultSet.getInt("idOddImage")).getName());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Connect.closeConnection(connection);
        }
        return order;
    }

    public Order getOrderCartEdit(String idOrder) {
        Connection connection = null;
        Order order = new Order();
        try {
            connection = Connect.getConnection();
            String sql = "select idOrder,name, receiver ,totalPrice, phoneNumber, address, status from CartOrder where idOrder = ? and status not like ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, idOrder);
            preparedStatement.setString(2, "%Đã hủy%");
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                order.setIdOrder(resultSet.getInt("idOrder"));
                order.setReceiver(resultSet.getString("receiver"));
                order.setPhoneNumber(resultSet.getString("phoneNumber"));
                order.setAddress(resultSet.getString("address"));
                order.setStatus(resultSet.getString("status"));
                order.setTotalPrice(resultSet.getInt("totalPrice"));
                order.setType("cart");
                order.setNameProduct(resultSet.getString("name"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Connect.closeConnection(connection);
        }
        return order;
    }

    public boolean updateOddStatus(String idOrder, String status) {
        Connection connection = null;
        try {
            connection = Connect.getConnection();
            String sql = "update OddImageOrder set status= ? ,purchareDate = ? where idOrder = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, status);
            preparedStatement.setDate(2, sqlDate);
            preparedStatement.setString(3, idOrder);
            int check = preparedStatement.executeUpdate();
            if (check > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Connect.closeConnection(connection);
        }
        return false;
    }

    public boolean updateCartStatus(String idOrder, String status) {
        Connection connection = null;
        try {
            connection = Connect.getConnection();
            String sql = "update CartOrder set status= ? , purchareDate= ? where idOrder = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, status);
            preparedStatement.setDate(2, sqlDate);
            preparedStatement.setString(3, idOrder);
            int check = preparedStatement.executeUpdate();
            if (check > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Connect.closeConnection(connection);
        }
        return false;
    }

    public int totalOddOrder(String table) {
        Connection connection = null;
        try {
            connection = Connect.getConnection();
            String sql = "select count(idOrder) as total from " + table;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("total");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Connect.closeConnection(connection);
        }
        return 0;
    }

    public int getOddTotalPrice() {
        Connection connection = null;
        try {
            connection = Connect.getConnection();
            String sql = "select sum(totalPrice) as total from OddImageOrder where status like ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%Đã giao%");
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Connect.closeConnection(connection);
        }
        return 0;
    }

    public int getOddTotalPriceThisMonth() {
        Connection connection = null;
        try {
            connection = Connect.getConnection();
            String sql = "select sum(totalPrice) as total from OddImageOrder where status like ? and year(purchareDate) =? and month(purchareDate) = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%Đã giao%");
            preparedStatement.setInt(2, currentYear);
            preparedStatement.setInt(3, currentMonth);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Connect.closeConnection(connection);
        }
        return 0;
    }

    //    Đơn hàng đã hủy
    public int getOddCanceled() {
        Connection connection = null;
        try {
            connection = Connect.getConnection();
            String sql = "select count(idOrder) as total from OddImageOrder where  status like ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%Đã hủy%");
            ResultSet res = preparedStatement.executeQuery();
            if (res.next()) {
                return res.getInt("total");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Connect.closeConnection(connection);
        }
        return 0;
    }

    public int getOddCanceledThisMonth() {
        Connection connection = null;
        try {
            connection = Connect.getConnection();
            String sql = "select count(idOrder) as total from OddImageOrder where  status like ? and year(purchareDate)=? and month(purchareDate)=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%Đã hủy%");
            preparedStatement.setInt(2, currentYear);
            preparedStatement.setInt(3, currentMonth);
            ResultSet res = preparedStatement.executeQuery();
            if (res.next()) {
                return res.getInt("total");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Connect.closeConnection(connection);
        }
        return 0;
    }

    public int getCartCanceled() {
        Connection connection = null;
        try {
            connection = Connect.getConnection();
            String sql = "select count(idOrder) as total from CartOrder where  status like ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%Đã hủy%");
            ResultSet res = preparedStatement.executeQuery();
            if (res.next()) {
                return res.getInt("total");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Connect.closeConnection(connection);
        }
        return 0;
    }

    public int getCartCanceledThisMonth() {
        Connection connection = null;
        try {
            connection = Connect.getConnection();
            String sql = "select count(idOrder) as total from CartOrder where  status like ? and year(purchareDate) = ? and month(purchareDate) = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%Đã hủy%");
            preparedStatement.setInt(2, currentYear);
            preparedStatement.setInt(3, currentMonth);
            ResultSet res = preparedStatement.executeQuery();
            if (res.next()) {
                return res.getInt("total");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Connect.closeConnection(connection);
        }
        return 0;
    }

    //    Tổng đơn đã đăt;
    public int getTotalOddOrder() {
        Connection connection = null;
        try {
            connection = Connect.getConnection();
            String sql = "select COUNT(idOrder) as total from OddImageOrder";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet res = preparedStatement.executeQuery();
            if (res.next()) {
                return res.getInt("total");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Connect.closeConnection(connection);
        }
        return 0;
    }

    public int getTotalOddOrderThisMonth() {
        Connection connection = null;
        try {
            connection = Connect.getConnection();
            String sql = "select count(idOrder) as total from OddImageOrder where year(purchareDate) = ? and month(purchareDate) = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, currentYear);
            preparedStatement.setInt(2, currentMonth);
            ResultSet res = preparedStatement.executeQuery();
            if (res.next()) {
                return res.getInt("total");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Connect.closeConnection(connection);
        }
        return 0;
    }

    public int getTotalCartOrder() {
        Connection connection = null;
        try {
            connection = Connect.getConnection();
            String sql = "select COUNT(idOrder) as total from CartOrder ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet res = preparedStatement.executeQuery();
            if (res.next()) {
                return res.getInt("total");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Connect.closeConnection(connection);
        }
        return 0;
    }

    public int getTotalCartOrderThisMonth() {
        Connection connection = null;
        try {
            connection = Connect.getConnection();
            String sql = "select count(idOrder) as total from CartOrder where year(purchareDate) = ? and month(purchareDate) = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, currentYear);
            preparedStatement.setInt(2, currentMonth);
            ResultSet res = preparedStatement.executeQuery();
            if (res.next()) {
                return res.getInt("total");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Connect.closeConnection(connection);
        }
        return 0;
    }


    public int getTotalCancel() {
        return this.getOddCanceled() + this.getCartCanceled();
    }

    public int getTotalCancelThisMonth() {
        return this.getCartCanceledThisMonth() + this.getOddCanceledThisMonth();
    }

    public int getTotalOrder() {
        return this.getTotalOddOrder() + this.getTotalCartOrder();
    }

    public int getTotalOrderThisMonth() {
        return this.getTotalCartOrderThisMonth() + this.getTotalOddOrderThisMonth();
    }


    public HashMap<User, Integer> getUserCancelOddHigh() {
        Connection connection = null;
        HashMap<User, Integer> map = new HashMap<>();
        try {
            connection = Connect.getConnection();
            String sql = " select user.idUser , user.name, count(oddimageorder.idOrder) as total from user join oddimageorder on user.idUser = oddimageorder.idUser where oddimageorder.status like ? group by  user.idUser, user.name";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%Đã hủy%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("idUser"));
                user.setUsername(resultSet.getString("name"));
                map.put(user, resultSet.getInt("total"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Connect.closeConnection(connection);
        }
        return map;
    }

    public HashMap<User, Integer> getUserCancelCartHigh() {
        Connection connection = null;
        HashMap<User, Integer> map = new HashMap<>();
        try {
            connection = Connect.getConnection();
            String sql = " select user.idUser  , user.name, count(cartOrder.idOrder) as total from user join cartOrder on user.idUser = cartOrder.idUser where cartOrder.status like ? group by  user.idUser, user.name";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%Đã hủy%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("idUser"));
                user.setUsername(resultSet.getString("name"));
                map.put(user, resultSet.getInt("total"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Connect.closeConnection(connection);
        }
        return map;
    }
}


