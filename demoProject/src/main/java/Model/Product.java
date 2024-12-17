package Model;

public class Product {
    private int idProduct;
    private int idOddImage;
    private int idMaterial;
    private int idSize;
    private int price;        // Giá cơ bản từ OddImage
    private int discount;     // Phần trăm giảm giá
    private boolean isShow;

    private Material material; // Thông tin chi tiết về chất liệu
    private Size size;         // Thông tin chi tiết về kích thước

    public int calculateTotalPrice() {
        int totalPrice = (int) (price + material.getPriceMaterial() + size.getPriceSize());
        if (discount > 0) {
            totalPrice -= totalPrice * discount / 100;
        }
        return totalPrice;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public int getIdOddImage() {
        return idOddImage;
    }

    public void setIdOddImage(int idOddImage) {
        this.idOddImage = idOddImage;
    }

    public int getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(int idMaterial) {
        this.idMaterial = idMaterial;
    }

    public int getIdSize() {
        return idSize;
    }

    public void setIdSize(int idSize) {
        this.idSize = idSize;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }
}