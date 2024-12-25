package SecurityModel;

import DAO.PublicKeyDAO;

import java.security.*;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.io.FileWriter;
import java.io.IOException;

public class KeyPairGeneratorUtil {
    private static final String KEY_STORE_PATH = "G:\\KeyDSA";

    // Tạo cặp khóa DSA, lưu public key vào DB, và trả về private key
    public static synchronized String generateAndSaveKeyPair(int userId) throws NoSuchAlgorithmException, IOException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA");
        keyGen.initialize(1024);  // DSA mặc định với độ dài khóa 1024 bit
        KeyPair pair = keyGen.generateKeyPair();

        String publicKey = Base64.getEncoder().encodeToString(pair.getPublic().getEncoded());
        String privateKey = Base64.getEncoder().encodeToString(pair.getPrivate().getEncoded());

        // Save the new public key to database
        PublicKeyDAO keyDAO = new PublicKeyDAO();
        keyDAO.savePublicKey(userId, publicKey);

        // Save keys to files in PEM format
        saveDSAKeyToFile(pair.getPublic(), pair.getPrivate());

        return privateKey + ";" + publicKey;
    }

    // Lưu khóa DSA vào file với định dạng PEM
    public static void saveDSAKeyToFile(PublicKey publicKey, PrivateKey privateKey) throws IOException {
        // Lưu Public Key
        try (FileWriter pubKeyWriter = new FileWriter(KEY_STORE_PATH + "DSA_publicKey.txt")) {
            String publicKeyPEM = "-----BEGIN PUBLIC KEY-----\n"
                    + Base64.getEncoder().encodeToString(publicKey.getEncoded()) + "\n-----END PUBLIC KEY-----\n";
            pubKeyWriter.write(publicKeyPEM);
        }

        // Lưu Private Key
        try (FileWriter privKeyWriter = new FileWriter(KEY_STORE_PATH + "DSA_privateKey.txt")) {
            String privateKeyPEM = "-----BEGIN PRIVATE KEY-----\n"
                    + Base64.getEncoder().encodeToString(privateKey.getEncoded()) + "\n-----END PRIVATE KEY-----\n";
            privKeyWriter.write(privateKeyPEM);
        }
    }

    // Phương thức để chuyển đổi mảng byte thành PublicKey
    public static PublicKey getPublicKeyFromBytes(byte[] publicKeyBytes) throws Exception {
        // Tạo đối tượng KeyFactory
        KeyFactory keyFactory = KeyFactory.getInstance("DSA");

        // Chuyển mảng byte thành đối tượng DSAPublicKey
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
        DSAPublicKey publicKey = (DSAPublicKey) keyFactory.generatePublic(keySpec);

        return publicKey;
    }
}