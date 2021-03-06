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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.bson.Document;
import org.json.JSONObject;
import org.json.JSONArray;

import com.sun.jersey.spi.inject.Inject;

import edu.gatech.project3for6310.dao.CourseDAO;
import edu.gatech.project3for6310.dao.CourseDAOImpl;
import edu.gatech.project3for6310.entity.Course;
import edu.gatech.project3for6310.utils.ObjectConversion;

@Path("/courses")
public class CourseService {

	
	private static CourseDAO courseDAO = new CourseDAOImpl();
	
	@Path("/all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllCourses(){
		
		List<Document> courses =courseDAO.getAllCourses();
		JSONArray sb = new JSONArray();
		for(Document d:courses)
		{
			sb.put(d);
		}
		return Response.status(200).entity(sb.toString()).build();
	}
	
	@Path("/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCourse(@PathParam("id") String id){
		
		Document course =courseDAO.getOneCourse(id);
		if(course ==null)
		{
			return Response.status(404).build();
		}
		return Response.status(200).entity(course.toJson()).build();
	}
	
	@Path("/{id}")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createCourse(@PathParam("id") String id, Course course){
		boolean success=courseDAO.createCourse(id, course);
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
	public Response updateCourse(@PathParam("id") String id, Course course){
		boolean success=courseDAO.updateCourse(id, course);
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
	    	return Response.status(400).entity(sb.toString()).build();
	    }
		
	}
	
	@Path("/{id}")
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteCourse(@PathParam("id") String id){
		boolean success=courseDAO.deleteCourse(id);
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
