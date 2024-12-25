package SecurityController;

import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;
import SecurityModel.KeyPairGeneratorUtil;

public class SignatureVerifier {

    public boolean verifySignature(String dataToVerify, String signatureBase64, String publicKeyBase64) {
        try {
            // Chuyển đổi public key từ Base64
            byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyBase64);
            PublicKey publicKey = KeyPairGeneratorUtil.getPublicKeyFromBytes(publicKeyBytes);

            // Tạo đối tượng Signature để xác minh
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initVerify(publicKey);

            // Cập nhật dữ liệu cần xác minh
            signature.update(dataToVerify.getBytes());

            // Chuyển đổi chữ ký từ Base64
            byte[] signatureBytes = Base64.getDecoder().decode(signatureBase64);

            // Kiểm tra chữ ký
            return signature.verify(signatureBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
