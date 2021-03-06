package edu.gatech.project3for6310.services;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.bson.Document;
import org.json.JSONObject;
import org.json.JSONArray;

import com.sun.jersey.spi.inject.Inject;

import edu.gatech.project3for6310.dao.ProfessorDAO;
import edu.gatech.project3for6310.dao.ProfessorDAOImpl;
import edu.gatech.project3for6310.entity.Professor;
import edu.gatech.project3for6310.entity.TeachingAssistant;

@Path("/professors")
public class ProfessorService {

	
	private static ProfessorDAO professorDAO = new ProfessorDAOImpl();
	
	@Path("/all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllProfessors(){
		
		List<Document> professors =professorDAO.getAllProfessors();
		JSONArray sb = new JSONArray();
		for(Document d:professors)
		{
			sb.put(d);
		}
		return Response.status(200).entity(sb.toString()).build();
	}
	
	@Path("/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProfessor(@PathParam("id") String id){
		
		Document professor =professorDAO.getOneProfessor(id);
		if(professor ==null)
		{
			return Response.status(404).build();
		}
		return Response.status(200).entity(professor.toJson()).build();
	}
	
	@Path("/{id}")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createProfessor(@PathParam("id") String id, Professor professor){
		boolean success=professorDAO.createProfessor(id, professor);
	    String res = null;
	    JSONObject sb = new JSONObject();
	    if (success)
	    {
	    	res="created successfully";
	    	sb.put("result", res);
	    	return Response.status(200).entity(sb.toString()).header("isCreated",success).build();
	    } else {
	    	res="not created";
	    	return Response.status(400).entity(sb.toString()).header("isCreated", success).build();
	    }
		
	}
	
	@Path("/{id}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateProfessor(@PathParam("id") String id, Professor professor){
		boolean success=professorDAO.updateProfessor(id, professor);
		JSONObject sb = new JSONObject();
	    String res = null;
	    if (success)
	    {
	    	res="updated successfully";
	    	sb.put("result", res);
	    	return Response.status(200).entity(sb.toString()).header("isUpdated",success).build();
	    } else {
	    	res="not updated";
	    	sb.put("result", res);
	    	return Response.status(400).entity(sb.toString()).header("isUpdated", success).build();
	    }
		
	}
	
	
	@Path("/{id}")
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteProfessor(@PathParam("id") String id){
		boolean success=professorDAO.deleteProfessor(id);
	    String res = null;
	    JSONObject sb = new JSONObject();
	    if (success)
	    {
	    	res="deleted successfully";
	    	sb.put("result", res);
	    	return Response.status(200).entity(sb.toString()).header("isDeleted",success).build();
	    } else {
	    	res="not deleted";
	    	return Response.status(400).entity(sb.toString()).header("isDeleted", success).build();
	    }
		
	}
}
