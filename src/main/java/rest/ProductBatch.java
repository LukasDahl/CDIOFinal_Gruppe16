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
	public Response addProdBatchJson(JSONproductbatch jprodbatch) {
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
		List<IProdBatchDTO> prodbatches = new ArrayList<>();
		prodbatches.add(prodbatch);
		return prodbatchToJSON(prodbatches).get(0);
	}

	private static IProdBatchDTO jsonToProdbatch(JSONproductbatch jprodbatch) throws IDALException.DALException {
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
		String date;
		for (IProdBatchDTO prodbatch : prodbatches) {
			date = new SimpleDateFormat("dd-MM-yyyy").format(prodbatch.getDate());
			jprodbatch = new JSONproductbatch();
			jprodbatch.setId("" + prodbatch.getProdBatchId());
			jprodbatch.setRecipeid("" + prodbatch.getRecipeId());
			jprodbatch.setDate(date);

			jprodbatches.add(jprodbatch);
		}
		return jprodbatches;
	}

}
