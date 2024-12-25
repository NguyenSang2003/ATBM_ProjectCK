package cart;

import Model.OddImage;

public class CartProduct {
    private int quantity = 1;
    private Object object;
    private int materialId;
    private int sizeId;
    private String materialName;
    private String sizeName;
    private int materialPrice;
    private int sizePrice;

    // Setter cho materialId và sizeId
    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }

    public void setSizeId(int sizeId) {
        this.sizeId = sizeId;
    }

    // Getter cho materialId và sizeId
    public int getMaterialId() {
        return materialId;
    }

    public int getSizeId() {
        return sizeId;
    }

    public CartProduct(int materialId, int sizeId, String materialName, String sizeName, int quantity) {
        this.materialId = materialId;
        this.sizeId = sizeId;
        this.materialName = materialName;
        this.sizeName = sizeName;
        this.quantity = quantity;
    }

    public CartProduct() {

    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public int increase() {
        return ++quantity;
    }

    public int reduce() {
        return quantity > 1 ? --quantity : quantity;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }

    public int price() {
        int price = 0;
        if (object instanceof OddImage) {
            price = ((OddImage) object).getPrice() - ((OddImage) object).getDiscount();
        }
        return price;
    }

    public int getMaterialPrice() {
        return materialPrice;
    }

    public void setMaterialPrice(int materialPrice) {
        this.materialPrice = materialPrice;
    }

    public int getSizePrice() {
        return sizePrice;
    }

    public void setSizePrice(int sizePrice) {
        this.sizePrice = sizePrice;
    }

    public int calculateTotalPrice() {
        int basePrice = price(); // Giá sản phẩm (OddImage) đã trừ giảm giá
        return (basePrice + materialPrice + sizePrice) * quantity; // Tổng giá (tính cả số lượng)
    }

//    public int calculateTotalPrice() {
//        int basePrice = price() > 0 ? price() : 0; // Đảm bảo giá sản phẩm không âm
//        int materialCost = materialPrice > 0 ? materialPrice : 0;
//        int sizeCost = sizePrice > 0 ? sizePrice : 0;
//
//        return (basePrice + materialCost + sizeCost) * quantity;
//    }


}