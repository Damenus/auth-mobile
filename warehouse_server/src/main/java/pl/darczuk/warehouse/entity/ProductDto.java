package pl.darczuk.warehouse.entity;

public class ProductDto {

    private Long id;
    private String modelName;
    private String manufacturerName;
    private Double price;
    private int quantity;

    public ProductDto(){}

    public ProductDto(Long id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public ProductDto(Long id, String modelName, String manufacturerName, Double price, int quantity) {
        this.id = id;
        this.modelName = modelName;
        this.manufacturerName = manufacturerName;
        this.price = price;
        this.quantity = quantity;
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
}
