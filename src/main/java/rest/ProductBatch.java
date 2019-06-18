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

@Path("/productBatch")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProductBatch {

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public Response addProdBatchJson(JSONproductbatchSimple jprodbatch) {
		IProdBatchDTO prodbatch;
		try {
			prodbatch = jsonToProdbatch(jprodbatch);
		} catch (IDALException.DALException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
		IProdBatchDAO prodBatchDAO = ProdBatchDAO.getInstance();

		try {
			prodBatchDAO.createProdBatch(prodbatch);
		} catch (IDALException.DALException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
		return Response.ok("Produktbatch oprettet").build();
	}

	@GET
	public ArrayList<JSONproductbatch> getProdBatchList() throws IDALException.DALException {
		IProdBatchDAO prodBatchDAO = ProdBatchDAO.getInstance();
		List<IProdBatchDTO> prodbatches = prodBatchDAO.getProdBatchList();
		return prodbatchToJSON(prodbatches);
	}


	@Path("single/{value}")
	@GET
	public JSONproductbatch getSingleProdBatch(@PathParam("value") String id) throws IDALException.DALException {
		IProdBatchDAO prodBatchDAO = ProdBatchDAO.getInstance();
		IProdBatchDTO prodbatch = prodBatchDAO.getProdBatch(Integer.parseInt(id));
		IMaterialDAO materialDAO = MaterialDAO.getInstance();
		IIngredientDAO ingredientDAO = IngredientDAO.getInstance();
		IUserDAO userDAO = UserDAO.getInstance();

		List<IIngredientDTO> ings = ingredientDAO.getIngredientList();
		List<IMaterialDTO> materials = materialDAO.getMaterialList();
		List<IUserDTO> users = userDAO.getUserList();

		JSONproductbatch jprodbatch = new JSONproductbatch();

		jprodbatch.setId("" + prodbatch.getProdBatchId());
		jprodbatch.setRecipeid("" + prodbatch.getRecipeId());
		jprodbatch.setUser_id("" + prodbatch.getUserId());

		jprodbatch.setDate(new SimpleDateFormat("dd-MM-yyyy").format(prodbatch.getDate()));

		String[] materialids = new String[prodbatch.getMatList().size()];
		String[] ingnames = new String[prodbatch.getMatList().size()];
		String[] amounts = new String[prodbatch.getMatList().size()];
		String[] suppliers = new String[prodbatch.getMatList().size()];
		String[] labos = new String[prodbatch.getMatList().size()];
		String[] dates = new String[prodbatch.getMatList().size()];

		for (int i = 0; i < prodbatch.getMatList().size(); i++){
			materialids[i] = "" + prodbatch.getMatList().get(i);
			amounts[i] = "" + prodbatch.getNettoList().get(i);
			dates[i] = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(prodbatch.getDateList().get(i));
			for (int j = 0; j < materials.size(); j++) {
				if (materials.get(j).getMaterialBatchId() == prodbatch.getMatList().get(i)) {
					for (int k = 0; k < ings.size(); k++) {
						if (ings.get(k).getIngredientId() == materials.get(j).getIngredientId()){
							ingnames[i] = ings.get(k).getIngredientName();
						}
					}
					suppliers[i] = materials.get(j).getSupplier();
				}
			}
			System.out.printf(prodbatch.getLabList().size() + "");
			for (int j = 0; j < users.size(); j++) {
				if (users.get(j).getUserId() == prodbatch.getLabList().get(i)){
					labos[i] = users.get(j).getIni();
				}

			}
		}
		jprodbatch.setMaterials(materialids);
		jprodbatch.setIngnames(ingnames);
		jprodbatch.setAmounts(amounts);
		jprodbatch.setSuppliers(suppliers);
		jprodbatch.setLabos(labos);
		jprodbatch.setDates(dates);

		return jprodbatch;
	}

	private static IProdBatchDTO jsonToProdbatch(JSONproductbatchSimple jprodbatch) throws IDALException.DALException {
		IProdBatchDTO prodbatch = new ProdBatchDTO();

		try {
			prodbatch.setProdBatchId(Integer.parseInt(jprodbatch.getId()));
		} catch (NumberFormatException e){
			throw new IDALException.DALException("ID skal være et tal.");
		}
		if (Integer.parseInt(jprodbatch.getRecipeid()) == 0) {
			throw new IDALException.DALException("Vælg venligst en opskrift");
		}
		prodbatch.setRecipeId(Integer.parseInt(jprodbatch.getRecipeid()));
		java.util.Date utilDate = new java.util.Date();
		prodbatch.setUserId(User.getCurrentUser());
		prodbatch.setDate(new java.sql.Date(utilDate.getTime()));

		return prodbatch;
	}

	private static ArrayList<JSONproductbatch> prodbatchToJSON(List<IProdBatchDTO> prodbatches) {
		JSONproductbatch jprodbatch;
		ArrayList<JSONproductbatch> jprodbatches = new ArrayList<>();
		IRecipeDAO recipeDAO = RecipeDAO.getInstance();
		IProductDAO productDAO = ProductDAO.getInstance();
		String date;
		for (IProdBatchDTO prodbatch : prodbatches) {
			date = new SimpleDateFormat("dd-MM-yyyy").format(prodbatch.getDate());
			jprodbatch = new JSONproductbatch();
			jprodbatch.setId("" + prodbatch.getProdBatchId());
			jprodbatch.setRecipeid("" + prodbatch.getRecipeId());
			try {
				jprodbatch.setProductName(productDAO.getProduct(recipeDAO.getRecipe(prodbatch.getRecipeId()).getProductId()).getProductName());
			} catch (IDALException.DALException e) {
				e.printStackTrace();
			}
			jprodbatch.setDate(date);

			jprodbatches.add(jprodbatch);
		}
		return jprodbatches;
	}

}
