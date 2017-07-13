package edu.gatech.project3for6310.dao;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.excludeId;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import edu.gatech.project3for6310.dbconnection.MongoConnection;
import edu.gatech.project3for6310.entity.Course;
import edu.gatech.project3for6310.entity.Professor;
import edu.gatech.project3for6310.utils.ObjectConversion;

public class ProfessorDAOImpl implements ProfessorDAO {
	private static  MongoClient mongoClient =MongoConnection.getInstance().getClient();
	private static MongoDatabase db =mongoClient.getDatabase("6310Project3");
	private static MongoCollection<Document> collection=db.getCollection("professor");
	public List<Document> getAllProfessors() {
		List<Document> list= new ArrayList<Document>();
	    FindIterable<Document> docs =collection.find().projection(excludeId());
	    Iterator<Document> idocs= docs.iterator();
	    while (idocs.hasNext())
	    {
	    	list.add(idocs.next());
	    }
		return list;
		
	}

	public Document getOneProfessor(String id) {
		return collection.find(eq("id", id)).projection(excludeId()).first();
	}

	public boolean updateProfessor(String id, Professor professor) {
		try{
		Document doc = ObjectConversion.professorToDocument(professor);
		collection.replaceOne(eq("id",id), doc);
		return true;
		} catch(Exception e)
		{
			return false;
		}
	}

	public void updateProfessor(String id, Document doc) {
		try{
		
			collection.replaceOne(eq("id",id), doc);
	
			} catch(Exception e)
			{

			}
		
	}

	@Override
	public boolean createProfessor(String id, Professor professor) {
		try{
			Document doc = ObjectConversion.professorToDocument(professor);
			collection.insertOne(doc);
			return true;
			} catch (Exception e)
			{
				return false;
			}
	}
	
	@Override
	public boolean deleteProfessor(String id) {
		try{
			collection.deleteOne(eq("id", id));
		return true;
		} catch (Exception e)
		{
			return false;
		}
	}

}
