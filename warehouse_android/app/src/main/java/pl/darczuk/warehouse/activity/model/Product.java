package pl.darczuk.warehouse.activity.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Objects;

@Entity
public class Product implements Serializable {
    @PrimaryKey
    @NonNull
    private Long id;
    private String modelName;
    private String manufacturerName;
    private Double price;
    private int quantity;

    public void setId(Long id) {
        this.id = id;
    }

    public Product() {}

    public Product(String modelName, String manufacturerName, Double price) {
        this.modelName = modelName;
        this.manufacturerName = manufacturerName;
        this.price = price;
        this.quantity = 0;
    }

    public Product(Long id, String modelName, String manufacturerName, Double price, int quantity) {
        this.id = id;
        this.modelName = modelName;
        this.manufacturerName = manufacturerName;
        this.price = price;
        this.quantity = quantity;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void increaseQuantity(int quantity) {
        this.quantity += quantity;
    }

    public void decreaseQuantity(int quantity) {
        this.quantity -= quantity;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", modelName='" + modelName + '\'' +
                ", manufacturerName='" + manufacturerName + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return quantity == product.quantity &&
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
        quantity = in.readInt();
    }

}
