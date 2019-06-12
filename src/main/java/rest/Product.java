package rest;

import database.dal.*;
import database.dto.*;
import rest.jsonObjects.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/product")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Product {

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public Response addProductJson(JSONproduct jproduct) {

        IProductDTO product = jsonToProduct(jproduct);
        IProductDAO productDAO = ProductDAO.getInstance();
        try {
            productDAO.createProduct(product);
        } catch (IDALException.DALException e) {
            e.printStackTrace();
        }
        return Response.ok("Produkt oprettet").build();
    }

    @GET
    public ArrayList<JSONproduct> getProductList() throws IDALException.DALException {
        IProductDAO productDAO = ProductDAO.getInstance();
        List<IProductDTO> products = productDAO.getProductList();
        return productToJSON(products);
    }


    private static IProductDTO jsonToProduct(JSONproduct jproduct){
        IProductDTO product = new ProductDTO();
        product.setProductId(Integer.parseInt(jproduct.getId()));
        product.setProductName(jproduct.getName());
        return product;
    }

    private static ArrayList<JSONproduct> productToJSON(List<IProductDTO> products){
        JSONproduct jproduct;
        ArrayList<JSONproduct> jproducts = new ArrayList<>();
        for (IProductDTO product: products){
            jproduct = new JSONproduct(""+product.getProductId(), product.getProductName());
            jproducts.add(jproduct);
        }
        return jproducts;
    }

}
