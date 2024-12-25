package testClass;

import DAO.PublicKeyDAO;

import java.util.List;

public class TestPublickeyDAO {
    public static void main(String[] args) {
        PublicKeyDAO dao = new PublicKeyDAO();

        int testUserId = 123;
        String testPublicKey1 = "testPublicKey1_ABCDEF123456";
        String testPublicKey2 = "testPublicKey2_XYZ789101112";

        try {
//            // Test thêm Public Key mới
//            System.out.println("Adding first public key...");
//            boolean isAdded1 = dao.addPublicKey(testUserId, testPublicKey1);
//            System.out.println("First public key added: " + isAdded1);
//
//            System.out.println("Adding second public key...");
//            boolean isAdded2 = dao.addPublicKey(testUserId, testPublicKey2);
//            System.out.println("Second public key added: " + isAdded2);
//
//            // Test lấy tất cả Public Key của người dùng
//            System.out.println("Fetching all public keys for user ID: " + testUserId);
//            List<String> publicKeys = dao.getPublicKeysByUserId(testUserId);
//            System.out.println("Public keys:");
//            for (String key : publicKeys) {
//                System.out.println(key);
//            }

            // test lấy public key với iduser
            System.out.println(dao.getPublicKeyByUserId(123));
//            // Test lấy Public Key hiện tại (còn hiệu lực)
//            System.out.println("Fetching active public key for user ID: " + testUserId);
//            String activePublicKey = dao.getActivePublicKeyByUserId(testUserId);
//            System.out.println("Active public key: " + activePublicKey);
//
//            // Test kiểm tra tính hợp lệ của Public Key
//            System.out.println("Checking if active public key is valid...");
//            boolean isValid = dao.isPublicKeyValid(activePublicKey);
//            System.out.println("Is valid: " + isValid);
//
//            // Test vô hiệu hóa Public Key hiện tại
//            System.out.println("Deactivating active public key...");
//            boolean isDeactivated = dao.deactivatePublicKey(testUserId);
//            System.out.println("Public key deactivated: " + isDeactivated);
//
//            // Test kiểm tra lại tính hợp lệ sau khi vô hiệu hóa
//            System.out.println("Rechecking if deactivated public key is valid...");
//            boolean isValidAfterDeactivation = dao.isPublicKeyValid(activePublicKey);
//            System.out.println("Is valid after deactivation: " + isValidAfterDeactivation);
//
//            // Test lấy Public Key hiện tại sau khi vô hiệu hóa
//            System.out.println("Fetching active public key for user ID: " + testUserId + " after deactivation");
//            String activePublicKeyAfterDeactivation = dao.getActivePublicKeyByUserId(testUserId);
//            System.out.println("Active public key after deactivation: " + activePublicKeyAfterDeactivation);
//
//            // Test xóa tất cả Public Keys của người dùng
//            System.out.println("Deleting all public keys for user ID: " + testUserId);
//            boolean isDeleted = dao.deleteAllPublicKeysByUserId(testUserId);
//            System.out.println("All public keys deleted: " + isDeleted);
//
//            // Test xác nhận không còn Public Key nào sau khi xóa
//            System.out.println("Fetching all public keys for user ID: " + testUserId + " after deletion");
//            List<String> publicKeysAfterDeletion = dao.getPublicKeysByUserId(testUserId);
//            System.out.println("Public keys after deletion:");
//            for (String key : publicKeysAfterDeletion) {
//                System.out.println(key);
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
