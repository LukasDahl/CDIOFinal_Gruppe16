package rest;

import database.dal.*;
import database.dto.*;
import rest.jsonObjects.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/recipe")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Recipe {

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public Response addRecipeJson(JSONrecipe jrecipe) {
        IRecipeDTO recipe = jsonToRecipe(jrecipe);
        IRecipeDAO recipeDAO = RecipeDAO.getInstance();

        try {
            recipeDAO.createRecipe(recipe);
        } catch (IDALException.DALException e) {
            e.printStackTrace();
        }

        return Response.ok("Opskrift oprettet").build();
    }

    @GET
    public ArrayList<JSONrecipe> getRecipeList() throws IDALException.DALException {
        IRecipeDAO recipeDAO = RecipeDAO.getInstance();
        List<IRecipeDTO> recipes = recipeDAO.getRecipeList();
        System.out.println(recipes);
        return recipesToJSON(recipes);
    }




    private static IRecipeDTO jsonToRecipe(JSONrecipe jrecipe){
        IRecipeDTO recipe = new RecipeDTO();


        recipe.setRecipeId(Integer.parseInt(jrecipe.getId()));
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
        for (String i: jrecipe.getMængde()){
            amount.add(Double.parseDouble(i));
        }
        recipe.setAmount(amount);

        List<Double> margin = new ArrayList<>();
        for (String i: jrecipe.getAfvigelse()){
            margin.add(Double.parseDouble(i));
        }
        recipe.setMargin(margin);

        return recipe;
    }

    private static ArrayList<JSONrecipe> recipesToJSON(List<IRecipeDTO> recipes){
        JSONrecipe jrecipe;
        IProductDAO productDAO = new ProductDAO();
        ArrayList<JSONrecipe> jrecipes = new ArrayList<>();
        for(IRecipeDTO recipe: recipes){
            jrecipe = new JSONrecipe();
            try {
                jrecipe.setId("" + recipe.getRecipeId());
                jrecipe.setAntal("" + recipe.getIngList().size());
                jrecipe.setProduct(productDAO.getProduct(recipe.getProductId()).getProductName());
            } catch (IDALException.DALException e) {
                e.printStackTrace();
            }
            jrecipes.add(jrecipe);
        }
        return jrecipes;
    }

}
