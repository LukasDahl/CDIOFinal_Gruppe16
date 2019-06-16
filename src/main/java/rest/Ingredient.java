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

        IIngredientDTO ingDTO = new IngredientDTO();
        try {
            ingDTO = jsonToIng(ing);
        } catch (IDALException.DALException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        IIngredientDAO ingDAO = IngredientDAO.getInstance();
        try {
            ingDAO.createIngredient(ingDTO);
        } catch (IDALException.DALException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
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
        return ingToJSON(ings).get(0);
    }

    @Path("update")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateIngJson(JSONingredient ing) {
        IIngredientDTO ingDTO = new IngredientDTO();
        try {
            ingDTO = jsonToIng(ing);
        } catch (IDALException.DALException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        IIngredientDAO ingDAO = IngredientDAO.getInstance();
        try {
            ingDAO.updateIngredient(ingDTO);
        } catch (IDALException.DALException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        return Response.ok("Ingrediens opdateret").build();
    }


    private static IIngredientDTO jsonToIng(JSONingredient jing) throws IDALException.DALException {
        IIngredientDTO ing = new IngredientDTO();
        try{
            ing.setIngredientId(Integer.parseInt(jing.getId()));
        } catch (NumberFormatException e){
            throw new IDALException.DALException("ID skal være et tal.");
        }
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
