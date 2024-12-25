package SecurityModel;

import DAO.PublicKeyDAO;

import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class KeyPairGeneratorUtil {
    // Tạo cặp khóa RSA, lưu public key vào DB, và trả về private key
    public static synchronized String generateAndSaveKeyPair(int userId) throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        KeyPair pair = keyGen.generateKeyPair();

        String publicKey = Base64.getEncoder().encodeToString(pair.getPublic().getEncoded());
        String privateKey = Base64.getEncoder().encodeToString(pair.getPrivate().getEncoded());

        // Save the new public key
        PublicKeyDAO keyDAO = new PublicKeyDAO();
        keyDAO.savePublicKey(userId, publicKey);

        return privateKey + ";" + publicKey;
    }

    // Phương thức để chuyển đổi mảng byte thành PublicKey
    public static PublicKey getPublicKeyFromBytes(byte[] publicKeyBytes) throws Exception {
        // Tạo đối tượng KeyFactory
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        // Chuyển mảng byte thành đối tượng RSAPublicKey
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
        RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);

        return publicKey;
    }
}