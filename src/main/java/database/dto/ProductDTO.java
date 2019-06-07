package database.dto;

import java.io.Serializable;

public class ProductDTO implements Serializable, IProductDTO {
    private int productId;
    private String productName;


    @Override
    public int getProductId() {
        return productId;
    }
    @Override
    public void setProductId(int productId) {
        this.productId = productId;
    }
    @Override
    public String getProductName() {
        return productName;
    }
    @Override
    public void setProductName(String productName) {
        this.productName = productName;
    }
    @Override
    public String toString(){
        return "" + productId + productName;
    }
}

