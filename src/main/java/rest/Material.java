/*
Author: Lukas
Ansvar: Denne klasse bruges til at konvertere input fra hjemmesiden til databasen, og vice versa, når det kommer til råvarerbatches.
 */

package rest;

import database.dal.*;
import database.dto.*;
import rest.jsonObjects.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Path("/material")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Material {

    private static DecimalFormat df = new DecimalFormat("0.00");

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public Response addMaterialJson(JSONmaterial jmaterial) {
        IMaterialDTO material = null;
        if(jmaterial.getSupplier().length() > 35 || jmaterial.getSupplier().length() < 2) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Leverandørnavn ikke gyldigt.").build();
        }
        try {
            material = jsonToMaterial(jmaterial);
        } catch (IDALException.DALException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        IMaterialDAO materialDAO = MaterialDAO.getInstance();

        try {
            materialDAO.createMaterial(material);
        } catch (IDALException.DALException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        return Response.ok("Råvarebatch oprettet").build();
    }

    @GET
    public ArrayList<JSONmaterial> getMaterialList() throws IDALException.DALException {
        IMaterialDAO materialDAO = MaterialDAO.getInstance();
        List<IMaterialDTO> materials = materialDAO.getMaterialList();
        return materialsToJSON(materials);
    }


    @Path("single/{value}")
    @GET
    public JSONmaterial getSingleMaterial(@PathParam("value") String id) throws IDALException.DALException {
        IMaterialDAO materialDAO = MaterialDAO.getInstance();
        IMaterialDTO material = materialDAO.getMaterial(Integer.parseInt(id));
        List<IMaterialDTO> materials = new ArrayList<>();
        materials.add(material);
        return materialsToJSON(materials).get(0);
    }

    @Path("update")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateMaterialJson(JSONmaterial jmaterial) {
        IMaterialDTO material = null;
        try {
            jmaterial.setIngredientid("" + 1);
            material = jsonToMaterial(jmaterial);
        } catch (IDALException.DALException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        IMaterialDAO materialDAO = MaterialDAO.getInstance();

        try {
            materialDAO.updateMaterial(material);
        } catch (IDALException.DALException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        return Response.ok("Råvarebatch oprettet").build();
    }

    private static IMaterialDTO jsonToMaterial(JSONmaterial jmaterial) throws IDALException.DALException {
        IMaterialDTO material = new MaterialDTO();

        try {
            material.setMaterialBatchId(Integer.parseInt(jmaterial.getId()));
        } catch (NumberFormatException e){
            throw new IDALException.DALException("ID skal være et tal.");
        }
        if (Integer.parseInt(jmaterial.getId()) == 0){
            throw new IDALException.DALException("ID må ikke være 0.");
        }

        java.util.Date utilDate = new java.util.Date();
        material.setDate(new java.sql.Date(utilDate.getTime()));
        if (Integer.parseInt(jmaterial.getIngredientid()) ==0 ){
            throw new IDALException.DALException("Vælg venligst en ingrediens.");
        }
        material.setIngredientId(Integer.parseInt(jmaterial.getIngredientid()));
        try {
            material.setAmount(Double.parseDouble(jmaterial.getAmount().replace(",",".")));
        } catch (NumberFormatException e){
            throw new IDALException.DALException("Mængde skal være et tal.");
        }
        material.setOrder(false);
        material.setUserId(User.getCurrentUser());
        material.setSupplier(jmaterial.getSupplier());

        return material;
    }

    private static ArrayList<JSONmaterial> materialsToJSON(List<IMaterialDTO> materials){
        JSONmaterial jmaterial;
        ArrayList<JSONmaterial> jmaterials = new ArrayList<>();
        IIngredientDAO ingredientDAO = IngredientDAO.getInstance();
        String date;
        for (IMaterialDTO material: materials){
            date = new SimpleDateFormat("dd-MM-yyyy").format(material.getDate());
            jmaterial = new JSONmaterial();
            jmaterial.setId("" + material.getMaterialBatchId());
            jmaterial.setIngredientid("" + material.getIngredientId());
            jmaterial.setAmount("" + df.format(material.getAmount()));
            jmaterial.setDate(date);
            jmaterial.setSupplier(material.getSupplier());
            try {
                jmaterial.setIngredientname(ingredientDAO.getIngredient(material.getIngredientId()).getIngredientName());
            } catch (IDALException.DALException e) {
                e.printStackTrace();
            }
            jmaterials.add(jmaterial);
        }
        return jmaterials;
    }

}
