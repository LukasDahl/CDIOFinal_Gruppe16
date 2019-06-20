/*
Forfatter: Lukas
Ansvar: Denne klasse får information om produktbatches fra hjemmesiden, og sender dem videre til databasen.
 */

package rest.jsonObjects;

public class JSONproductbatch {
    String id;
    String productName;
    String recipeid;
    String status;
    String date;
    String user_id;
    String[] materials;
    String[] ingnames;
    String[] amounts;
    String[] suppliers;
    String[] labos;
    String[] dates;

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

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String[] getMaterials() {
        return materials;
    }

    public void setMaterials(String[] materials) {
        this.materials = materials;
    }

    public String[] getIngnames() {
        return ingnames;
    }

    public void setIngnames(String[] ingnames) {
        this.ingnames = ingnames;
    }

    public String[] getAmounts() {
        return amounts;
    }

    public void setAmounts(String[] amounts) {
        this.amounts = amounts;
    }

    public String[] getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(String[] suppliers) {
        this.suppliers = suppliers;
    }

    public String[] getLabos() {
        return labos;
    }

    public void setLabos(String[] labos) {
        this.labos = labos;
    }

    public String[] getDates() {
        return dates;
    }

    public void setDates(String[] dates) {
        this.dates = dates;
    }
}
