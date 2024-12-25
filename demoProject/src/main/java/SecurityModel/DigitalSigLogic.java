package SecurityModel;

import java.io.*;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class DigitalSigLogic {
    private Signature signature;

    public DigitalSigLogic(String algorithm, String provider) throws NoSuchAlgorithmException, NoSuchProviderException {
        this.signature = Signature.getInstance(algorithm, provider);
    }

    // Ký văn bản
    public String signData(String data, PrivateKey privateKey) throws SignatureException, InvalidKeyException {
        signature.initSign(privateKey);
        signature.update(data.getBytes());
        byte[] sign = signature.sign();
        return Base64.getEncoder().encodeToString(sign);
    }

    // Xác minh chữ ký văn bản
    public boolean verifySignature(String data, String sign, PublicKey publicKey)
            throws SignatureException, InvalidKeyException {
        signature.initVerify(publicKey);
        signature.update(data.getBytes());
        byte[] decodedSign = Base64.getDecoder().decode(sign);
        return signature.verify(decodedSign);
    }

    // Ký file
    public String signFile(String filePath, PrivateKey privateKey)
            throws IOException, SignatureException, InvalidKeyException {
        signature.initSign(privateKey);
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filePath))) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = bis.read(buffer)) != -1) {
                signature.update(buffer, 0, len);
            }
        }
        byte[] sign = signature.sign();
        return Base64.getEncoder().encodeToString(sign);
    }

    // Xác minh chữ ký file
    public boolean verifyFile(String filePath, String sign, PublicKey publicKey)
            throws IOException, SignatureException, InvalidKeyException {
        signature.initVerify(publicKey);
        byte[] decodedSign = Base64.getDecoder().decode(sign);
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filePath))) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = bis.read(buffer)) != -1) {
                signature.update(buffer, 0, len);
            }
        }
        return signature.verify(decodedSign);
    }

    // Hàm hỗ trợ chuyển đổi string ra privatekey
    public static PrivateKey stringToPrivateKey(String keyStr) throws Exception {
        // Loại bỏ phần không cần thiết
        keyStr = keyStr.replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s+", ""); // Xóa dòng trống hoặc khoảng trắng
        // Giải mã Base64
        byte[] keyBytes = Base64.getDecoder().decode(keyStr);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("DSA");
        return keyFactory.generatePrivate(spec);
    }

    // Hàm hỗ trợ chuyển đổi string ra publickey
    public static PublicKey stringToPublicKey(String keyStr) throws Exception {
        // Loại bỏ phần không cần thiết
        keyStr = keyStr.replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s+", ""); // Xóa dòng trống hoặc khoảng trắng
        // Giải mã Base64
        byte[] keyBytes = Base64.getDecoder().decode(keyStr);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("DSA");
        return keyFactory.generatePublic(spec);
    }
}