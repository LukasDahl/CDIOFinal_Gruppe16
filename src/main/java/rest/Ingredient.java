package rest;

import database.dal.*;
import database.dto.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/ingredient")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Ingredient {

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public Response addIngJson(JSONingredient ing) {

        IIngredientDTO ingDTO = jsonToIng(ing);
        IIngredientDAO ingDAO = IngredientDAO.getInstance();
        try {
            ingDAO.createIngredient(ingDTO);
        } catch (IDALException.DALException e) {
            e.printStackTrace();
        }
        //return Response.status(Response.Status.BAD_REQUEST).entity("Brugernavn ikke gyldigt").build();
        return Response.ok("Ingrediens oprettet").build();
    }

    @GET
    public ArrayList<JSONingredient> getIngList() throws IDALException.DALException {
        IIngredientDAO ingDAO = IngredientDAO.getInstance();
        List<IIngredientDTO> ings = ingDAO.getIngredientList();
        return ingToJSON(ings);
    }


    public static IIngredientDTO jsonToIng(JSONingredient jing){
        IIngredientDTO ing = new IngredientDTO();
        ing.setIngredientId(Integer.parseInt(jing.getId()));
        ing.setIngredientName(jing.getName());
        ing.setActive(false);
        return ing;
    }

    public static ArrayList<JSONingredient> ingToJSON(List<IIngredientDTO> ings){
        JSONingredient jing;
        ArrayList<JSONingredient> jings = new ArrayList<>();
        String role;
        for (IIngredientDTO ing: ings){
            jing = new JSONingredient(""+ing.getIngredientId(), ing.getIngredientName());
            jings.add(jing);
        }
        return jings;
    }

}
