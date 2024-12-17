package Model;

public class Size {
    private int idSize;
    private String nameSize;
    private int width;
    private int height;
    private int priceSize; // Giá tiền

    public Size() {
    }

    public Size(int idSize, String nameSize, int width, int height, int priceSize) {
        this.idSize = idSize;
        this.nameSize = nameSize;
        this.width = width;
        this.height = height;
        this.priceSize = priceSize;
    }

    public int getIdSize() {
        return idSize;
    }

    public void setIdSize(int idSize) {
        this.idSize = idSize;
    }

    public String getNameSize() {
        return nameSize;
    }

    public void setNameSize(String nameSize) {
        this.nameSize = nameSize;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getPriceSize() {
        return priceSize;
    }

    public void setPriceSize(int priceSize) {
        this.priceSize = priceSize;
    }
}