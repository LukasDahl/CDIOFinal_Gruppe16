package rest;

import database.dal.*;
import database.dto.*;
import rest.jsonObjects.*;

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
        return Response.ok("Ingrediens oprettet").build();
    }

    @GET
    public ArrayList<JSONingredient> getIngList() throws IDALException.DALException {
        IIngredientDAO ingDAO = IngredientDAO.getInstance();
        List<IIngredientDTO> ings = ingDAO.getIngredientList();
        return ingToJSON(ings);
    }

    @Path("single/{value}")
    @GET
    public JSONingredient getSingleIng(@PathParam("value") String id) throws IDALException.DALException {
        IIngredientDAO ingDAO = IngredientDAO.getInstance();
        IIngredientDTO ing = ingDAO.getIngredient(Integer.parseInt(id));
        List<IIngredientDTO> ings = new ArrayList<>();
        ings.add(ing);
        System.out.println("123");
        return ingToJSON(ings).get(0);
    }


    private static IIngredientDTO jsonToIng(JSONingredient jing){
        IIngredientDTO ing = new IngredientDTO();
        ing.setIngredientId(Integer.parseInt(jing.getId()));
        ing.setIngredientName(jing.getName());
        ing.setActive(false);
        return ing;
    }

    private static ArrayList<JSONingredient> ingToJSON(List<IIngredientDTO> ings){
        JSONingredient jing;
        ArrayList<JSONingredient> jings = new ArrayList<>();
        for (IIngredientDTO ing: ings){
            jing = new JSONingredient(""+ing.getIngredientId(), ing.getIngredientName());
            jings.add(jing);
        }
        return jings;
    }

}
