package rest.jsonObjects;

public class JSONmaterial {
    String id;
    String ingredientid;
    String ingredientname;
    String amount;
    String supplier;
    String date;

    public JSONmaterial(String id, String ingredientid, String ingredientname, String amount, String supplier, String date) {
        this.id = id;
        this.ingredientid = ingredientid;
        this.ingredientname = ingredientname;
        this.amount = amount;
        this.supplier = supplier;
        this.date = date;
    }

    public JSONmaterial() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIngredientid() {
        return ingredientid;
    }

    public void setIngredientid(String ingredientid) {
        this.ingredientid = ingredientid;
    }

    public String getIngredientname() {
        return ingredientname;
    }

    public void setIngredientname(String ingredientname) {
        this.ingredientname = ingredientname;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
