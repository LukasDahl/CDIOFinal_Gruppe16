package rest;

import database.dal.*;
import database.dto.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class User {
	static ArrayList<JSONuser> users = new ArrayList<JSONuser>();
	static int ID = findNextID(0);






	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public Response addUserJson(JSONuser user) {
		if(user.getUsername().length() > 20 || user.getUsername().length() < 2) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Brugernavn ikke gyldigt").build();
		}
		else if(!user.getCpr().matches("[0-9]{6}-[0-9]{4}")){
			return Response.status(Response.Status.BAD_REQUEST).entity("Opgiv venligst gyldigt CPR").build();
		}
		else{
			IUserDTO userDTO = jsonToUser(user);
			IUserDAO userDAO = UserDAO.getInstance();
			try {
				userDAO.createUser(userDTO);
			} catch (IUserDAO.DALException e) {
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

	public static ArrayList<JSONuser> userToJSON(List<IUserDTO> users){
		JSONuser juser;
		ArrayList<JSONuser> jusers = new ArrayList<>();
		String role;
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

			juser = new JSONuser("" + user.getUserId(), user.getUserName(), user.getIni(), user.getCpr(), "", role);
			jusers.add(juser);
		}
		return jusers;
	}

	public static IUserDTO jsonToUser(JSONuser juser){
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

	public static int findNextID(int id){
		IUserDAO userDAO = UserDAO.getInstance();
		try {
			while (!((UserDAO) userDAO).idAvailable(++id)){
			}
		} catch (IUserDAO.DALException e) {
			e.printStackTrace();
		}
		return id;
	}

}
