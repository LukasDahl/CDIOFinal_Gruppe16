package rest;

import database.dal.*;
import database.dto.*;
import rest.jsonObjects.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Path("/ingredientHistory")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class IngredientHistory {

   @Path("{value}")
   @GET
   public ArrayList<JSONingredientHistory> getSingleIng(@PathParam("value") String id) throws IDALException.DALException {
        IIngredientHistoryDAO inghisDAO = IngredientHistoryDAO.getInstance();
        List<IIngredientHistoryDTO> ings = inghisDAO.getIngredientList(Integer.parseInt(id));
        return inghisToJSON(ings);
   }

    private static ArrayList<JSONingredientHistory> inghisToJSON(List<IIngredientHistoryDTO> ings){
        JSONingredientHistory jing;
        String date;
        ArrayList<JSONingredientHistory> jings = new ArrayList<>();
        for (IIngredientHistoryDTO ing: ings){
            date = new SimpleDateFormat("dd-MM-yyyy").format(ing.getDate());
            jing = new JSONingredientHistory();
            jing.setId("" + ing.getIngredientId());
            jing.setName(ing.getIngredientName());
            jing.setUserID("" + ing.getUserId());

            jing.setDate(date);
            jings.add(jing);
        }
        return jings;
    }




}