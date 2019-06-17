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
	private static int currentUser = 0;


	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public Response addUserJson(JSONuser user) {
		if(user.getUsername().length() > 35 || user.getUsername().length() < 2) {
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
 		return usersToJSON(users);
	}

	@Path("update")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public Response updateUserJson(JSONuser user) {
		if(user.getUsername().length() > 35 || user.getUsername().length() < 2) {
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
				userDAO.updateUser(userDTO);
			} catch (IDALException.DALException e) {
				e.printStackTrace();
			}
			return Response.ok("Bruger opdateret").build();
		}
	}




	@Path("id")
	@GET
	public int getID(){
		return ID;
	}

	private static ArrayList<JSONuser> usersToJSON(List<IUserDTO> users){
		JSONuser juser;
		ArrayList<JSONuser> jusers = new ArrayList<>();
		String role;
		String admin;
		String aktiv;
		for (IUserDTO user: users){
			if (user.isPharma()){
				role = "Farmaceut";
				aktiv = "Aktiv";
			}
			else if (user.isPLeader()){
				role = "Produktionsleder";
				aktiv = "Aktiv";
			}
			else if (user.isLabo()){
				role = "Laborant";
				aktiv = "Aktiv";
			}
			else{
				role = "Ingen";
				aktiv = "Inaktiv";
			}

			if (user.isAdmin()){
				admin = "Ja";
				aktiv = "Aktiv";
			}
			else{
				admin = "Nej";
			}
			juser = new JSONuser("" + user.getUserId(), user.getUserName(), user.getIni(), user.getCpr(), admin, role, aktiv);
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
		else if (juser.getRole().equals("Laborant")){
			user.setPharma(false);
			user.setPLeader(false);
			user.setLabo(true);
		}
		else {
			user.setPharma(false);
			user.setPLeader(false);
			user.setLabo(false);
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

	@Path("login")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public Response userPriv(JSONlogin login) {

		IUserDAO userDAO = UserDAO.getInstance();
		IUserDTO user;
		int loginint = 0;
		try {
			loginint = Integer.parseInt(login.getLogin_ID());
			currentUser = loginint;
		}catch (NumberFormatException e){
			return Response.status(Response.Status.BAD_REQUEST).entity("Giv mig et tal.").build();
		}

		try {
			user = userDAO.getUser(loginint);
		} catch (IDALException.DALException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Bruger eksisterer ikke.").build();
		}

		if (user.isAdmin()){
			if (user.isPharma()){
				return Response.ok("5").build();
			}
			else if (user.isPLeader()){
				return Response.ok("3").build();
			}
			else{
				return Response.ok("1").build();
			}
		}
		else{
			if (user.isPharma()){
				return Response.ok("6").build();
			}
			else if (user.isPLeader()){
				return Response.ok("4").build();
			}
			else{
				return Response.ok("2").build();
			}
		}
	}

	@Path("single")
	@GET
	public JSONuser getSingleUser(){
		IUserDAO userDAO = UserDAO.getInstance();
		IUserDTO user = new UserDTO();
		List<IUserDTO> users = new ArrayList<>();
		try {
			user = userDAO.getUser(currentUser);
		} catch (IDALException.DALException e) {
			e.printStackTrace();
		}
		users.add(user);
		return usersToJSON(users).get(0);
	}

	@Path("single/{value}")
	@GET
	public JSONuser getSingleUserByID(@PathParam("value") String id){
		IUserDAO userDAO = UserDAO.getInstance();
		IUserDTO user = new UserDTO();
		List<IUserDTO> users = new ArrayList<>();
		try {
			user = userDAO.getUser(Integer.parseInt(id));
		} catch (IDALException.DALException e) {
			e.printStackTrace();
		}
		users.add(user);
		return usersToJSON(users).get(0);
	}


	public static int getCurrentUser() {
		return currentUser;
	}
}
