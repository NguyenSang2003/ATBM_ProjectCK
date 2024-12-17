package Model;

public class Material {
    private int idMaterial;
    private String nameMaterial;
    private String description;
    private int priceMaterial;

    public Material(int idMaterial, String nameMaterial, String description, int price) {
        this.idMaterial = idMaterial;
        this.nameMaterial = nameMaterial;
        this.description = description;
        this.priceMaterial = price;
    }

    public int getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(int idMaterial) {
        this.idMaterial = idMaterial;
    }

    public String getNameMaterial() {
        return nameMaterial;
    }

    public void setNameMaterial(String nameMaterial) {
        this.nameMaterial = nameMaterial;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriceMaterial() {
        return priceMaterial;
    }

    public void setPriceMaterial(int priceMaterial) {
        this.priceMaterial = priceMaterial;
    }
}