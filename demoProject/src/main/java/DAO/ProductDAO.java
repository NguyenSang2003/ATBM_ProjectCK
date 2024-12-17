package DAO;

import Properties.URL;
import Services.Connect;
import Model.OddImage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    BelongDAO belongDAO = new BelongDAO();
    DescriptionDAO descriptionDAO = new DescriptionDAO();
    TopicDAO topicDAO = new TopicDAO();
    ImageDAO imageDAO = new ImageDAO();
    java.util.Date utilDate = new java.util.Date();
    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

    public boolean insertOddImage(String nameTopic, String nameOddImage, String source, String description, int price, int discount, String isShow) {
        Connection connection = null;
        try {
            connection = Connect.getConnection();
            String sqlInsert = "insert into oddImage(name, source, price, discount,isShow, createdAt) values (?,?,?,?,?,?)";
            PreparedStatement preparedStatementInsert = connection.prepareStatement(sqlInsert);
            preparedStatementInsert.setString(1, nameOddImage);
            preparedStatementInsert.setString(2, source);
            preparedStatementInsert.setInt(3, price);
            preparedStatementInsert.setInt(4, discount);
            preparedStatementInsert.setString(5, isShow);
            preparedStatementInsert.setDate(6, sqlDate);
            int checkOddInsert = preparedStatementInsert.executeUpdate();
            if (checkOddInsert >= 0 &&
                    belongDAO.insertOddImageBelongTopic(topicDAO.getIdTopicByName(nameTopic), getIdOddImageByName(nameOddImage))
                    && descriptionDAO.insertDescriptionDAO(getIdOddImageByName(nameOddImage), description)) {
                return true;
            } else {
                return false;
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            Connect.closeConnection(connection);
        }
    }

    public boolean updateOddImage(int idTopic, String idOddImage, String nameOddImage, String description, int price, int discount) {
        Connection connection = null;
        System.out.println(" before insert " + price);
        try {
            connection = Connect.getConnection();
            String sql = "update oddImage set  name = ? , price = ? ,discount = ? where  idOddImage = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, nameOddImage);
            preparedStatement.setInt(2, price);
            preparedStatement.setInt(3, discount);
//            preparedStatement.setDate(4, sqlDate);
            preparedStatement.setString(4, idOddImage);
            int check = preparedStatement.executeUpdate();
            if (check > 0 && descriptionDAO.updateDescriptionOddImage(idOddImage, description) && belongDAO.updateOddImage(idTopic, Integer.parseInt(idOddImage))) {
                return true;
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            Connect.closeConnection(connection);
        }
        return false;
    }

    public boolean checkOddNameExist(String name) {
        Connection connection = null;

        try {
            connection = Connect.getConnection();
            String sql = "select name from oddImage where name = ? ";
            PreparedStatement preparedStatementCheckEmail = connection.prepareStatement(sql);
            preparedStatementCheckEmail.setString(1, name);
            ResultSet check = preparedStatementCheckEmail.executeQuery();
            if (check.next()) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Connect.closeConnection(connection);
        }
        return false;
    }

    public boolean checkOddNameExistForUpdate(String idOddImage, String name) {
        Connection connection = null;

        try {
            connection = Connect.getConnection();
            String sql = "select count(name) as total from oddImage where name = ? and idOddImage <> ?";
            PreparedStatement preparedStatementCheckEmail = connection.prepareStatement(sql);
            preparedStatementCheckEmail.setString(1, name);
            preparedStatementCheckEmail.setString(2, idOddImage);
            ResultSet check = preparedStatementCheckEmail.executeQuery();
            if (check.next()) {
                int count = check.getInt("total");
                return count > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Connect.closeConnection(connection);
        }
        return false;
    }

    public int getIdOddImageByName(String name) {
        Connection connection = null;
        int res = 0;
        try {
            connection = Connect.getConnection();
            String sql = "select idOddImage from oddImage where name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                res = resultSet.getInt("idOddImage");
                return res;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Connect.closeConnection(connection);
        }
        return res;
    }

    public ArrayList<OddImage> getAllOddImage(int page, int recSize) {
        Connection connection = null;
        ArrayList<OddImage> listOddImage = new ArrayList<OddImage>();
        int startIndex = (page - 1) * recSize;
        try {
            connection = Connect.getConnection();
            String sql = "Select idOddImage, name, source, price, discount, isShow from oddImage LIMIT ?  OFFSET ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, recSize);
            preparedStatement.setInt(2, startIndex);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                OddImage oddImage = new OddImage();
                oddImage.setIdOddImage(resultSet.getInt("idOddImage"));
                oddImage.setName(resultSet.getString("name"));
                oddImage.setImage(URL.URL + resultSet.getString("source"));
                oddImage.setPrice(resultSet.getInt("price"));
                oddImage.setDiscount(resultSet.getInt("discount"));
                int idTopic = belongDAO.getIdTopicFromIdOdd(resultSet.getInt("idOddImage"));
                String nameTopic = topicDAO.getNameTopicById(idTopic);
                oddImage.setBelongTopic(nameTopic);
                oddImage.setShow(resultSet.getBoolean("isShow"));
                listOddImage.add(oddImage);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Connect.closeConnection(connection);
        }
        return listOddImage;
    }

    public ArrayList<OddImage> getAllOddImageForClient(int page, int recSize) {
        Connection connection = null;
        ArrayList<OddImage> listOddImage = new ArrayList<OddImage>();
        int startIndex = (page - 1) * recSize;
        try {
            connection = Connect.getConnection();
            String sql = "Select idOddImage, name, source, price, discount, isShow from oddImage where  isShow= ? LIMIT ? OFFSET ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "true");
            preparedStatement.setInt(2, recSize);
            preparedStatement.setInt(3, startIndex);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                OddImage oddImage = new OddImage();
                oddImage.setIdOddImage(resultSet.getInt("idOddImage"));
                oddImage.setName(resultSet.getString("name"));
                oddImage.setImage(URL.URL + resultSet.getString("source"));
                oddImage.setPrice(resultSet.getInt("price"));
                oddImage.setDiscount(resultSet.getInt("discount"));
                int idTopic = belongDAO.getIdTopicFromIdOdd(resultSet.getInt("idOddImage"));
                String nameTopic = topicDAO.getNameTopicById(idTopic);
                oddImage.setBelongTopic(nameTopic);
                oddImage.setShow(resultSet.getBoolean("isShow"));
                listOddImage.add(oddImage);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Connect.closeConnection(connection);
        }
        return listOddImage;
    }


    public boolean deleteOddImage(int idOddImage) {
        Connection connection = null;
        try {
            connection = Connect.getConnection();
            String sql = "Delete from OddImage where idOddImage = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idOddImage);
            int check = preparedStatement.executeUpdate();
            if (check > 0 && belongDAO.deleteOddImage(idOddImage) && descriptionDAO.deleteDescriptionOddImage(idOddImage)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            Connect.closeConnection(connection);
        }
    }

    //    get 1 sản phẩm
//    sd trong trang chi tiết
    public OddImage getOddImageById(int idOddImage) {
        Connection connection = null;
        OddImage oddImage = new OddImage();
        try {
            connection = Connect.getConnection();
            String sql = "select idOddImage, name , price, discount, source from OddImage where idOddImage = ? and isShow = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idOddImage);
            preparedStatement.setString(2, "true");
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                oddImage.setIdOddImage(resultSet.getInt("idOddImage"));
                oddImage.setName(resultSet.getString("name"));
                oddImage.setPrice(resultSet.getInt("price"));
                oddImage.setDiscount(resultSet.getInt("discount"));
                oddImage.setImage(URL.URL + resultSet.getString("source"));
                int idTopicFromIdOdd = belongDAO.getIdTopicFromIdOdd(idOddImage);
                String topicName = topicDAO.getNameTopicById(idTopicFromIdOdd);
                oddImage.setBelongTopic(topicName);
                String description = descriptionDAO.getDescriptionByOddImage(idOddImage);
                oddImage.setDescription(description);
                return oddImage;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Connect.closeConnection(connection);
        }
        return oddImage;
    }

    public OddImage getOddImageByIdForAdminUpdate(int idOddImage) {
        Connection connection = null;
        OddImage oddImage = new OddImage();
        try {
            connection = Connect.getConnection();
            String sql = "select idOddImage, name , price, discount, source from OddImage where idOddImage = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idOddImage);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                oddImage.setIdOddImage(resultSet.getInt("idOddImage"));
                oddImage.setName(resultSet.getString("name"));
                oddImage.setPrice(resultSet.getInt("price"));
                oddImage.setDiscount(resultSet.getInt("discount"));
                oddImage.setImage(URL.URL + resultSet.getString("source"));
                int idTopicFromIdOdd = belongDAO.getIdTopicFromIdOdd(idOddImage);
                String topicName = topicDAO.getNameTopicById(idTopicFromIdOdd);
                oddImage.setBelongTopic(topicName);
                String description = descriptionDAO.getDescriptionByOddImage(idOddImage);
                oddImage.setDescription(description);
                return oddImage;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Connect.closeConnection(connection);
        }
        return oddImage;
    }

    //    cho trang detail


    //    tìm kiesm sản phẩm
    public ArrayList<OddImage> searchOddImageWithParam(String name) {
        Connection connection = null;
        ArrayList<OddImage> listOddImage = new ArrayList<>();
        try {
            connection = Connect.getConnection();
            String sql = "select idOddImage, name, price, discount, source from oddImage where name LIKE ? AND isShow = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + name + "%");
            preparedStatement.setString(2, "true");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
//                chỉ cần báy nhiêu dữ liệu thôi
                OddImage oddImage = new OddImage();
                oddImage.setIdOddImage(resultSet.getInt("idOddImage"));
                oddImage.setName(resultSet.getString("name"));
                oddImage.setPrice(resultSet.getInt("price"));
                oddImage.setDiscount(resultSet.getInt("discount"));
                oddImage.setImage(URL.URL + resultSet.getString("source"));
                listOddImage.add(oddImage);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Connect.closeConnection(connection);
        }
        return listOddImage;
    }

    public boolean updateShowOddImage(int idOddImage, String status) {
        Connection connection = null;
        try {
            connection = Connect.getConnection();
            String sql = "update oddImage set isShow = ? where idOddImage= ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, idOddImage);
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

    public boolean checkOddImageExist(int idOddImage) {
        Connection connection = null;
        try {
            connection = Connect.getConnection();
            String sql = "select idOddImage from oddImage where  idOddImage = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idOddImage);
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

    //    get String show
    public String getShowOddImage(String idOddImage) {
        Connection connection = null;
        String res = "";
        try {
            connection = Connect.getConnection();
            String sql = "select isShow from oddImage where  idOddImage = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, idOddImage);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                res = resultSet.getString("isShow");
                return res;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Connect.closeConnection(connection);
        }
        return res;
    }

    //    Lấy ra 10 sản phẩm mới nhất
    public ArrayList<OddImage> getTop8ddImageNew() {
        Connection connection = null;
        ArrayList<OddImage> list10OddImage = new ArrayList<>();
        try {
            connection = Connect.getConnection();
            String sql = "select idOddImage, name, price, discount , source from oddImage where isShow = ? order by createdAt desc LIMIT 8 ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "true");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                OddImage oddImage = new OddImage();
                oddImage.setIdOddImage(resultSet.getInt("idOddImage"));
                oddImage.setName(resultSet.getString("name"));
                oddImage.setPrice(resultSet.getInt("price"));
                oddImage.setDiscount(resultSet.getInt("discount"));
                oddImage.setImage(URL.URL + resultSet.getString("source"));
                list10OddImage.add(oddImage);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Connect.closeConnection(connection);
        }
        return list10OddImage;
    }

    //    toatl product;
    public int totalOdd() {
        Connection connection = null;
        try {
            connection = Connect.getConnection();
            String sql = "select count(idOddImage) as total from oddImage";
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

    //Lấy ra danh sách OddImage sau khi lọc theo giá tiền
    public List<OddImage> getFilteredOddImages(int page, int recSize, int minPrice, int maxPrice) {
        Connection connection = null;
        List<OddImage> listOddImage = new ArrayList<>();
        int start = (page - 1) * recSize;
        try {
            connection = Connect.getConnection();
            String sql = "SELECT * FROM oddImage WHERE isShow = ? AND price BETWEEN ? AND ? LIMIT ?, ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "true");
            preparedStatement.setInt(2, minPrice);
            preparedStatement.setInt(3, maxPrice);
            preparedStatement.setInt(4, start);
            preparedStatement.setInt(5, recSize);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                OddImage oddImage = new OddImage();
                oddImage.setIdOddImage(resultSet.getInt("idOddImage"));
                oddImage.setName(resultSet.getString("name"));
                oddImage.setPrice(resultSet.getInt("price"));
                oddImage.setDiscount(resultSet.getInt("discount"));
                oddImage.setImage(URL.URL + resultSet.getString("source"));
                oddImage.setShow(resultSet.getBoolean("isShow"));
                listOddImage.add(oddImage);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Connect.closeConnection(connection);
        }
        return listOddImage;
    }

    //Tính tổng số item để phân trang
    public int totalFilteredItems(int minPrice, int maxPrice) {
        String sql = "(SELECT COUNT(*) FROM OddImage WHERE price BETWEEN ? AND ?) AS total";

        try (Connection connection = Connect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, minPrice);
            preparedStatement.setInt(2, maxPrice);
            preparedStatement.setInt(3, minPrice);
            preparedStatement.setInt(4, maxPrice);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("total");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}