/*
Forfatter: Lukas
Ansvar: Klassen er oprettet som interface og beskriver hvilke metoder og atributter der skal være i et "data transfer object" for produkter.
*/


package database.dto;


public interface IProductDTO {
    int getProductId();

    void setProductId(int productId);

    String getProductName();

    void setProductName(String productName);


}
