/*
Forfatter: Lukas
Ansvar: Denne klasse får information om opskrifter fra hjemmesiden, og sender dem videre til databasen.
 */

package rest.jsonObjects;

public class JSONrecipe {

    String id;
    String product;
    String antal;
    String[] ingrediens;
    String[] mængde;
    String[] afvigelse;
    String dato;

    public JSONrecipe(String id, String product, String antal, String[] ingrediens, String[] mængde, String[] afvigelse, String dato) {
        this.id = id;
        this.product = product;
        this.antal = antal;
        this.ingrediens = ingrediens;
        this.mængde = mængde;
        this.afvigelse = afvigelse;
        this.dato = dato;
    }

    public JSONrecipe() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getAntal() {
        return antal;
    }

    public void setAntal(String antal) {
        this.antal = antal;
    }

    public String[] getIngrediens() {
        return ingrediens;
    }

    public void setIngrediens(String[] ingrediens) {
        this.ingrediens = ingrediens;
    }

    public String[] getMængde() {
        return mængde;
    }

    public void setMængde(String[] mængde) {
        this.mængde = mængde;
    }

    public String[] getAfvigelse() {
        return afvigelse;
    }

    public void setAfvigelse(String[] afvigelse) {
        this.afvigelse = afvigelse;
    }

    public String getDato() {
        return dato;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }
}

