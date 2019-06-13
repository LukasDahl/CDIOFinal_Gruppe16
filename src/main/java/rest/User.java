package rest;

import database.dal.*;
import database.dto.*;
import rest.jsonObjects.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class User {

	private static int ID = findNextID(1);


	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public Response addUserJson(JSONuser user) {
		if(user.getUsername().length() > 20 || user.getUsername().length() < 2) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Brugernavn ikke gyldigt").build();
		}
		else if(!user.getCpr().matches("[0-9]{6}-[0-9]{4}")){
			return Response.status(Response.Status.BAD_REQUEST).entity("Opgiv venligst gyldigt CPR").build();
		}
		else if(user.getIni().length() > 4 || user.getIni().length() < 2){
			return Response.status(Response.Status.BAD_REQUEST).entity("Opgiv venligst gyldige initialer").build();
		}
		else{
			IUserDTO userDTO = jsonToUser(user);
			IUserDAO userDAO = UserDAO.getInstance();
			try {
				userDAO.createUser(userDTO);
			} catch (IDALException.DALException e) {
				e.printStackTrace();
			}
			ID = findNextID(ID);
			return Response.ok("Bruger oprettet").build();
		}

	}

	@GET
	public ArrayList<JSONuser> getUserList() throws IUserDAO.DALException {
		IUserDAO userDAO = UserDAO.getInstance();
		List<IUserDTO> users = userDAO.getUserList();
 		return userToJSON(users);
	}

	@Path("id")
	@GET
	public int getID(){
		return ID;
	}

	private static ArrayList<JSONuser> userToJSON(List<IUserDTO> users){
		JSONuser juser;
		ArrayList<JSONuser> jusers = new ArrayList<>();
		String role;
		String admin;
		for (IUserDTO user: users){
			if (user.isPharma()){
				role = "Farmaceut";
			}
			else if (user.isPLeader()){
				role = "Produktionsleder";
			}
			else {
				role = "Laborant";
			}

			if (user.isAdmin()){
				admin = "Ja";
			}
			else{
				admin = "Nej";
			}
			juser = new JSONuser("" + user.getUserId(), user.getUserName(), user.getIni(), user.getCpr(), admin, role);
			jusers.add(juser);
		}
		return jusers;
	}

	private static IUserDTO jsonToUser(JSONuser juser){
		IUserDTO user = new UserDTO();
		user.setUserId(Integer.parseInt(juser.getId()));
		user.setUserName(juser.getUsername());
		user.setIni(juser.getIni());
		user.setCpr(juser.getCpr());
		if (juser.getAdmin().equals("yes")){
			user.setAdmin(true);
		}
		else {
			user.setAdmin(false);
		}

		if (juser.getRole().equals("Farmaceut")){
			user.setPharma(true);
			user.setPLeader(true);
			user.setLabo(true);
		}
		else if (juser.getRole().equals("Produktionsleder")){
			user.setPharma(false);
			user.setPLeader(true);
			user.setLabo(true);
		}
		else{
			user.setPharma(false);
			user.setPLeader(false);
			user.setLabo(true);
		}

		return user;
	}

	private static int findNextID(int id){
		IUserDAO userDAO = UserDAO.getInstance();
		try {
			while (!((UserDAO) userDAO).idAvailable(id)){
				id++;
			}
		} catch (IUserDAO.DALException e) {
			e.printStackTrace();
		}
		return id;
	}

}
