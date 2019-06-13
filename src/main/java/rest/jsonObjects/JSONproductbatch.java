package rest.jsonObjects;

public class JSONproductbatch {
    String id;
    String productName;
    String recipeid;
    String status;
    String date;

    public JSONproductbatch(String id, String productName, String recipeid, String status, String date) {
        this.id = id;
        this.productName = productName;
        this.recipeid = recipeid;
        this.status = status;
        this.date = date;
    }

    public JSONproductbatch() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getRecipeid() {
        return recipeid;
    }

    public void setRecipeid(String recipeid) {
        this.recipeid = recipeid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
