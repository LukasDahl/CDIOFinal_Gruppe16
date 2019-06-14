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

@Path("/recipe")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Recipe {

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public Response addRecipeJson(JSONrecipe jrecipe) {
        IRecipeDTO recipe = null;

        try {
            recipe = jsonToRecipe(jrecipe);
        } catch (IDALException.DALException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }

        for (int i: recipe.getIngList()){
            if (i == 0){
                return Response.status(Response.Status.BAD_REQUEST).entity("Vælg venligst en ingrediens.").build();
            }
        }

        if (recipe.getProductId() == 0) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Vælg venligst et produkt.").build();
        }


        IRecipeDAO recipeDAO = RecipeDAO.getInstance();

        try {
            recipeDAO.createRecipe(recipe);
        } catch (IDALException.DALException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }

        return Response.ok("Opskrift oprettet").build();
    }

    @GET
    public ArrayList<JSONrecipe> getRecipeList() throws IDALException.DALException {
        IRecipeDAO recipeDAO = RecipeDAO.getInstance();
        List<IRecipeDTO> recipes = recipeDAO.getRecipeList();
        return recipesToJSON(recipes);
    }




    private static IRecipeDTO jsonToRecipe(JSONrecipe jrecipe) throws IDALException.DALException {
        IRecipeDTO recipe = new RecipeDTO();

        try {
            recipe.setRecipeId(Integer.parseInt(jrecipe.getId()));
        } catch (NumberFormatException e){
            throw new IDALException.DALException("ID skal være et tal");
        }

        recipe.setProductId(Integer.parseInt(jrecipe.getProduct()));

        java.util.Date utilDate = new java.util.Date();
        recipe.setDate(new java.sql.Date(utilDate.getTime()));

        List<Integer> pharmaList = new ArrayList<>();
        pharmaList.add(2);
        recipe.setPharmaList(pharmaList);

        List<Integer> ingredient = new ArrayList<>();
        for (String i: jrecipe.getIngrediens()){
                ingredient.add(Integer.parseInt(i));

        }
        recipe.setIngList(ingredient);

        List<Double> amount = new ArrayList<>();
        for (int i = 0; i < jrecipe.getMængde().length; i++){
            try {
                amount.add(Double.parseDouble(jrecipe.getMængde()[i].replace(",",".")));
            } catch (NumberFormatException e){
                throw new IDALException.DALException("Fejl i " + (i + 1) + ". ingrediensmængde.");
            }
        }
        recipe.setAmount(amount);

        List<Double> margin = new ArrayList<>();
        for (int i = 0; i < jrecipe.getAfvigelse().length; i++){
            try {
                amount.add(Double.parseDouble(jrecipe.getAfvigelse()[i].replace(",",".")));
            } catch (NumberFormatException e){
                throw new IDALException.DALException("Fejl i " + (i + 1) + ". ingrediensafvigelse.");
            }
        }
        recipe.setMargin(margin);

        return recipe;
    }

    private static ArrayList<JSONrecipe> recipesToJSON(List<IRecipeDTO> recipes){
        JSONrecipe jrecipe;
        IProductDAO productDAO = new ProductDAO();
        ArrayList<JSONrecipe> jrecipes = new ArrayList<>();
        String[] ings;
        String[] amounts;
        String[] margins;
        String date;
        for(IRecipeDTO recipe: recipes){
            jrecipe = new JSONrecipe();
            try {
                date = new SimpleDateFormat("dd-MM-yyyy").format(recipe.getDate());
                jrecipe.setId("" + recipe.getRecipeId());
                jrecipe.setAntal("" + recipe.getIngList().size());
                jrecipe.setProduct(productDAO.getProduct(recipe.getProductId()).getProductName());
                jrecipe.setDato(date);
                ings = new String[recipe.getIngList().size()];
                amounts = new String[recipe.getIngList().size()];
                margins = new String[recipe.getIngList().size()];

                for (int i = 0; i < recipe.getIngList().size(); i++){
                    ings[i] = "" + recipe.getIngList().get(i);
                    amounts[i] = "" + recipe.getAmount().get(i);
                    margins[i] = "" + recipe.getMargin().get(i);
                }
                jrecipe.setIngrediens(ings);
                jrecipe.setMængde(amounts);
                jrecipe.setAfvigelse(margins);

            } catch (IDALException.DALException e) {
                e.printStackTrace();
            }
            jrecipes.add(jrecipe);
        }
        return jrecipes;
    }

    @Path("single/{value}")
    @GET
    public JSONrecipe getRecipe(@PathParam("value") String id) throws IDALException.DALException {
        IRecipeDAO recipeDAO = RecipeDAO.getInstance();
        List<IRecipeDTO> recipes = new ArrayList<>();
        recipes.add(recipeDAO.getRecipe(Integer.parseInt(id)));
        return recipesToJSON(recipes).get(0);
    }

}
