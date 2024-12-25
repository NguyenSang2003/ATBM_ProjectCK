package SecurityModel;

import java.time.LocalDateTime;

public class UserPublicKey {
    private int keyId;              // Khóa chính
    private int userId;             // ID người dùng
    private String publicKey;       // Public Key
    private LocalDateTime createTime; // Thời gian tạo
    private LocalDateTime endTime;    // Thời gian hết hiệu lực

    // Constructor không tham số
    public UserPublicKey() {
    }

    // Constructor đầy đủ tham số
    public UserPublicKey(int keyId, int userId, String publicKey, LocalDateTime createTime, LocalDateTime endTime) {
        this.keyId = keyId;
        this.userId = userId;
        this.publicKey = publicKey;
        this.createTime = createTime;
        this.endTime = endTime;
    }

    // Getters và Setters
    public int getKeyId() {
        return keyId;
    }

    public void setKeyId(int keyId) {
        this.keyId = keyId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "UserPublicKey{" +
                "keyId=" + keyId +
                ", userId=" + userId +
                ", publicKey='" + publicKey + '\'' +
                ", createTime=" + createTime +
                ", endTime=" + endTime +
                '}';
    }
}