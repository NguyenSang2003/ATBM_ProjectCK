package cart;

import Model.OddImage;

public class CartProduct {
    private int quantity = 1;
    private Object object;
    private int materialId;
    private int sizeId;
    private String materialName;
    private String sizeName;

    private int MaterialPrice;
    private int SizePrice;

    public CartProduct(int quantity, Object object, int materialId, int sizeId) {
        this.quantity = quantity;
        this.object = object;
        this.materialId = materialId;
        this.sizeId = sizeId;
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

    public int getMaterialId() {
        return materialId;
    }

    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }

    public int getSizeId() {
        return sizeId;
    }

    public void setSizeId(int sizeId) {
        this.sizeId = sizeId;
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
        return MaterialPrice;
    }

    public void setMaterialPrice(int materialPrice) {
        MaterialPrice = materialPrice;
    }

    public int getSizePrice() {
        return SizePrice;
    }

    public void setSizePrice(int sizePrice) {
        SizePrice = sizePrice;
    }

    public int calculateTotalPrice() {
        int basePrice = price(); // Giá sản phẩm (OddImage) đã trừ giảm giá
        return (basePrice + MaterialPrice + SizePrice) * quantity; // Tổng giá (tính cả số lượng)
    }

}