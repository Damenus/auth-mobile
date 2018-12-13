package pl.darczuk.warehouse.activity.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

//import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

@Entity
public class Product implements Serializable {
    @PrimaryKey
    @NonNull
  //  @JsonProperty("id")
    private Long id;
  //  @JsonProperty("modelName")
    private String modelName;
 //   @JsonProperty("manufacturerName")
    private String manufacturerName;
  //  @JsonProperty("price")
    private Double price;
    private int serverQuantity;
  //  @JsonProperty("localDeltaChangeQuantity")
    private int localDeltaChangeQuantity;

    public Product() {}

    public Product(String modelName, String manufacturerName, Double price) {
        this.modelName = modelName;
        this.manufacturerName = manufacturerName;
        this.price = price;
        this.serverQuantity = 0;
        this.localDeltaChangeQuantity = 0;
    }

    public Product(Long id, String modelName, String manufacturerName, Double price, int serverQuantity) {
        this.id = id;
        this.modelName = modelName;
        this.manufacturerName = manufacturerName;
        this.price = price;
        this.serverQuantity = serverQuantity;
        this.localDeltaChangeQuantity = 0;
    }

    public Product(Long id, String modelName, String manufacturerName, Double price, int serverQuantity, int localDeltaChangeQuantity) {
        this.id = id;
        this.modelName = modelName;
        this.manufacturerName = manufacturerName;
        this.price = price;
        this.serverQuantity = serverQuantity;
        this.localDeltaChangeQuantity = localDeltaChangeQuantity;
    }

    public Product(ProductDTO productDTO) {
        this.id = productDTO.getId();
        this.modelName = productDTO.getModelName();
        this.manufacturerName = productDTO.getManufacturerName();
        this.price = productDTO.getPrice();
        this.serverQuantity = productDTO.getQuantity();
        this.localDeltaChangeQuantity = 0;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
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

    public int getServerQuantity() {
        return serverQuantity;
    }

    public void setServerQuantity(int serverQuantity) {
        this.serverQuantity = serverQuantity;
    }

    public int getLocalDeltaChangeQuantity() {
        return localDeltaChangeQuantity;
    }

    public void setLocalDeltaChangeQuantity(int localDeltaChangeQuantity) {
        this.localDeltaChangeQuantity = localDeltaChangeQuantity;
    }

    public int getQuantity() {
        return serverQuantity + localDeltaChangeQuantity;
    }

    public void setQuantity(int quantity) {
        this.localDeltaChangeQuantity = quantity;
    }

    public void increaseQuantity(int quantity) {
        this.localDeltaChangeQuantity += quantity;
    }

    public void decreaseQuantity(int quantity) {
        this.localDeltaChangeQuantity -= quantity;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", modelName='" + modelName + '\'' +
                ", manufacturerName='" + manufacturerName + '\'' +
                ", price=" + price +
                ", serverQuantity=" + serverQuantity +
                ", localDeltaChangeQuantity=" + localDeltaChangeQuantity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return serverQuantity == product.serverQuantity &&
                localDeltaChangeQuantity == product.localDeltaChangeQuantity &&
                id == product.id &&
                modelName == product.modelName &&
                manufacturerName == product.manufacturerName &&
                price == product.price;
    }

    @Override
    public int hashCode() {

        return id.hashCode() + modelName.hashCode() + manufacturerName.hashCode();
    }

    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    private Product(Parcel in) {
        id = in.readLong();
        modelName = in.readString();
        manufacturerName = in.readString();
        price = in.readDouble();
        serverQuantity = in.readInt();
        localDeltaChangeQuantity = in.readInt();
    }

}
