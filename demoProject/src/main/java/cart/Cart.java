package cart;

import DAO.MaterialDAO;
import DAO.ProductDAO;
import DAO.SizeDAO;
import Model.Discount;
import Model.Material;
import Model.OddImage;
import Model.Size;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    Map<String, CartProduct> data = new HashMap<>();
    private Discount appliedDiscount;
    ProductDAO productDAO = new ProductDAO();

    public Map<String, CartProduct> getData() {
        return this.data;
    }

    public Discount getAppliedDiscount() {
        return appliedDiscount;
    }

    public void setAppliedDiscount(Discount discount) {
        this.appliedDiscount = discount;
    }

//    public boolean add(String type, String key, int productId, int materialId, int sizeId) {
//        ProductDAO productDAO = new ProductDAO();
//        Material material = MaterialDAO.getMaterialById(materialId);
//        Size size = SizeDAO.getSizeById(sizeId);
//
//        if (data.containsKey(key)) {
//            CartProduct cartProduct = data.get(key);
//            cartProduct.setQuantity(cartProduct.getQuantity() + 1);
//        } else {
//            CartProduct cartProduct = new CartProduct();
//            cartProduct.setObject(productDAO.getOddImageById(productId));
//            cartProduct.setQuantity(1);
//            cartProduct.setMaterialName(material.getNameMaterial());
//            cartProduct.setSizeName(size.getNameSize());
//            cartProduct.setMaterialPrice(material.getPriceMaterial()); // Set giá chất liệu
//            cartProduct.setSizePrice(size.getPriceSize()); // Set giá kích cỡ
//            data.put(key, cartProduct);
//        }
//        return true;
//    }

    public boolean add(String type, String key, int productId, int materialId, int sizeId) {
        System.out.println("Adding to cart - materialId: " + materialId + ", sizeId: " + sizeId);

        Material material = MaterialDAO.getMaterialById(materialId);
        Size size = SizeDAO.getSizeById(sizeId);

        if (material == null || size == null) {
            System.out.println("Material or Size is null, cannot add to cart.");
            return false; // Trả về false nếu dữ liệu không hợp lệ
        }

        if (data.containsKey(key)) {
            CartProduct cartProduct = data.get(key);
            cartProduct.setQuantity(cartProduct.getQuantity() + 1);
        } else {
            CartProduct cartProduct = new CartProduct();
            cartProduct.setObject(productDAO.getOddImageById(productId));
            cartProduct.setQuantity(1);
            cartProduct.setMaterialName(material.getNameMaterial());
            cartProduct.setSizeName(size.getNameSize());
            cartProduct.setMaterialPrice(material.getPriceMaterial()); // Set giá chất liệu
            cartProduct.setSizePrice(size.getPriceSize()); // Set giá kích cỡ
            cartProduct.setMaterialId(materialId);  // Quan trọng: Đảm bảo gọi setter này
            cartProduct.setSizeId(sizeId);          // Quan trọng: Đảm bảo gọi setter này
            data.put(key, cartProduct);

            System.out.println("CartProduct - materialId: " + cartProduct.getMaterialId() + ", sizeId: " + cartProduct.getSizeId());
        }
        return true;
    }

    public boolean removeCart(String idMap) {
        if (!data.containsKey(idMap)) {
            return false;
        }
        CartProduct cartProduct = data.get(idMap);
        return cartProduct.reduce() > 0;
    }

    public int total() {
        return data.size();
    }

    public boolean remove(String idMap) {
        if (!data.containsKey(idMap)) {
            return false;
        }
        data.remove(idMap);
        return true;
    }

    public boolean removeAll() {
        if (!data.isEmpty()) {
            data.clear();
            return true;
        }
        return false;
    }

    //    public int totalPrice() {
//        int totalPrice = 0;
//        for (CartProduct cartProduct : getData().values()) {
//            totalPrice += cartProduct.getQuantity() * cartProduct.price();
//        }
//
//        // Áp dụng giảm giá nếu có
//        if (appliedDiscount != null) {
//            double discountAmount = (double) totalPrice * appliedDiscount.getDiscountValue();
//            totalPrice -= discountAmount;
//        }
//
//        return totalPrice;
//    }
    public int totalPrice() {
        int totalPrice = 0;
        for (CartProduct cartProduct : getData().values()) {
            totalPrice += cartProduct.calculateTotalPrice(); // Sử dụng phương thức tính tổng giá từ CartProduct
        }

        // Áp dụng giảm giá nếu có
        if (appliedDiscount != null) {
            double discountAmount = (double) totalPrice * appliedDiscount.getDiscountValue();
            totalPrice -= discountAmount;
        }

        return totalPrice;
    }


}