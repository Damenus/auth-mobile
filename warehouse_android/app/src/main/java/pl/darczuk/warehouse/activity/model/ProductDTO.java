package pl.darczuk.warehouse.activity.model;

public class ProductDTO {

    private Long id;
    private String modelName;
    private String manufacturerName;
    private Double price;
    private int quantity;
    private Long lastTimeUpdate;

    public ProductDTO(){}

    public ProductDTO(Long id, String modelName, String manufacturerName, Double price, int quantity, Long lastTimeUpdate) {
        this.id = id;
        this.modelName = modelName;
        this.manufacturerName = manufacturerName;
        this.price = price;
        this.quantity = quantity;
        this.lastTimeUpdate = lastTimeUpdate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getLastTimeUpdate() {
        return lastTimeUpdate;
    }

    public void setLastTimeUpdate(Long lastTimeUpdate) {
        this.lastTimeUpdate = lastTimeUpdate;
    }
}
