package Model;

public class Topic {
    private  int idTopic;
    private  String imageInterface;
    private  String name;
    private  int product;
    private boolean isShow;
    public Topic() {

    }

    public int getIdTopic() {
        return idTopic;
    }

    public void setIdTopic(int idTopic) {
        this.idTopic = idTopic;
    }

    public String getImageInterface() {
        return imageInterface;
    }

    public void setImageInterface(String imageInterface) {
        this.imageInterface = imageInterface;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProduct() {
        return product;
    }

    public void setProduct(int product) {
        this.product = product;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }
}
