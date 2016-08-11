/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package webService.rest;

import dao.UsuarioDAOImpl;
import dao.VocabularioDAOImpl;
import domain.Usuario;
import domain.Vocabulario;
import domain.Cuestionario;
import domain.Test;
import domain.Tipo;
import dao.AuthenticationImpl;
import dao.TestDAOImpl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.hibernate.Hibernate;

import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;

/**
 * REST Web Service
 *
 * @author Jonh
 */
@Path("users")
public class UsersResource {

    @Context
    private UriInfo context;
    private VocabularioDAOImpl vocabularioManager;
    private TestDAOImpl testManager;
    private UsuarioDAOImpl userManager;
    private AuthenticationImpl authz;
        
    public UsersResource() {
    	this.vocabularioManager = new VocabularioDAOImpl();
    	this.testManager = new TestDAOImpl();
        this.userManager = new UsuarioDAOImpl();
        this.authz = new AuthenticationImpl();
    }

    @GET
    @Path("{idUser}")
    @Produces("application/json")
    public Response getUser(@QueryParam("id") int id, @HeaderParam("token")String token, @PathParam("idUser")int idUser) {
        
    	try{
			 this.authz = new AuthenticationImpl();			  
			 int validado = this.authz.validaToken(token, id);
			  
			  switch (validado){
			  	case 1:			  		
			  		//buscamos el usuario en la BD, lo instanciamos y devolvemos    	  
			        Usuario user = userManager.findUserById(idUser);			       
			        
			        if (user == null){
			           return Response.status(Status.NO_CONTENT).entity("User not found in database.").build();
			        }			       
			        return Response.status(Status.OK).entity(user).build();
			  	case -1:
			  		return Response.status(Status.UNAUTHORIZED).entity("Token missmatch with database's token.").build();
			  	case -2:
			  		return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Problems verifing token.").build();
			  	case -3:
			  		return Response.status(Status.UNAUTHORIZED).entity("Invalid token for user with id: "+idUser).build();
			  	case -4:
			  		return Response.status(Status.NOT_ACCEPTABLE).entity("Token expired.").build();
			  	case -5:
			  		return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Error parsing token").build();
			  	case -6:
			  		return Response.status(Status.INTERNAL_SERVER_ERROR).entity("JOSE exception").build();
				default:
					return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Unknown error.").build();
			  }

		  }catch (Exception e){
			  return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Unknown error.\n"+e.getMessage()).build();
		  }     	    	        
    }
            
    @GET
    @Path("{idUser}/tests")
    @Produces("application/json")
    public Response getTestUser(@PathParam("idUser")int idUser, @QueryParam("id") int id, @HeaderParam("token") String token) {
    	
    	try{
			 this.authz = new AuthenticationImpl();			  
			 int validado = this.authz.validaToken(token, id);
			  
			  switch (validado){
			  	case 1:			  		
			  	//obtenemos lista de los test realizados por el user  
			        Set<Test> result = userManager.getTestUser(idUser);
			        if ((result == null)||(result.isEmpty())){			        	
			           return Response.status(Status.NO_CONTENT).entity("User has no tests.").build();
			        }            			        
			        return Response.ok(result).build(); 
			  	case -1:
			  		return Response.status(Status.UNAUTHORIZED).entity("Token missmatch with database's token.").build();
			  	case -2:
			  		return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Problems verifing token.").build();
			  	case -3:
			  		return Response.status(Status.UNAUTHORIZED).entity("Invalid token for user with id: "+idUser).build();
			  	case -4:
			  		return Response.status(Status.NOT_ACCEPTABLE).entity("Token expired.").build();
			  	case -5:
			  		return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Error parsing token").build();
			  	case -6:
			  		return Response.status(Status.INTERNAL_SERVER_ERROR).entity("JOSE exception").build();
				default:
					return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Unknown error.").build();
			  }

		  }catch (Exception e){
			  return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Unknown error.\n"+e.getMessage()).build();
		  }         	    	      
    }
    
    @GET   
    @Path("{idUser}/vocabularies")
    @Produces("application/json")
    public Response getVocabulariesUser(@PathParam("idUser")int idUser, @QueryParam("id") int id, @HeaderParam("token")String token) {
    	try{
			 this.authz = new AuthenticationImpl();			  
			 int validado = this.authz.validaToken(token, id);
			  
			  switch (validado){
			  	case 1:			  		
			  		Set<Vocabulario> result = userManager.getVocabularios(idUser);
			        
			        if (result.isEmpty()){
			        	Response.status(Status.NOT_FOUND).entity("Vocabulary not found.").build();
			         }
			         
			         return Response.status(Status.OK).entity(result).build();
			        			       			        
			  	case -1:
			  		return Response.status(Status.UNAUTHORIZED).entity("Token missmatch with database's token.").build();
			  	case -2:
			  		return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Problems verifing token.").build();
			  	case -3:
			  		return Response.status(Status.UNAUTHORIZED).entity("Invalid token for user with id: "+id).build();
			  	case -4:
			  		return Response.status(Status.NOT_ACCEPTABLE).entity("Token expired.").build();
			  	case -5:
			  		return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Error parsing token").build();
			  	case -6:
			  		return Response.status(Status.INTERNAL_SERVER_ERROR).entity("JOSE exception").build();
				default:
					return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Unknown error.").build();
			  }

		  }catch (Exception e){
			  return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Unknown error.\n"+e.getMessage()).build();
		  }  
    	               
    }
        
    @POST
    @Path("{idUser}/vocabulario")
    @Consumes("application/json")
    public Response insertVocabularyUser(@PathParam("idUser")int idUser, @QueryParam("id") int id, @HeaderParam("token")String token, InputStream newVoc){
       
    	try{
			 this.authz = new AuthenticationImpl();			  
			 int validado = this.authz.validaToken(token, id);
			  
			  switch (validado){
			  	case 1:			  		
			  		StringBuilder sB = new StringBuilder();
			    	
					try {
						BufferedReader in = new BufferedReader(new InputStreamReader(newVoc));
						String line = null;
						while ((line = in.readLine()) != null) {
							sB.append(line);
						}
					} catch (Exception e) {
						return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Error parsing").build();
					}
					String result = sB.toString();

					Gson gson = new Gson();
			        Vocabulario voc = gson.fromJson(result,Vocabulario.class);
			        
			    	boolean res = userManager.insertVocabulario(idUser, voc);
			    	
			        if (res){
			            return Response.ok().build();
			        }else{
			            return Response.serverError().build();
			        } 
			  	case -1:
			  		return Response.status(Status.UNAUTHORIZED).entity("Token missmatch with database's token.").build();
			  	case -2:
			  		return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Problems verifing token.").build();
			  	case -3:
			  		return Response.status(Status.UNAUTHORIZED).entity("Invalid token for user with id: "+id).build();
			  	case -4:
			  		return Response.status(Status.NOT_ACCEPTABLE).entity("Token expired.").build();
			  	case -5:
			  		return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Error parsing token").build();
			  	case -6:
			  		return Response.status(Status.INTERNAL_SERVER_ERROR).entity("JOSE exception").build();
				default:
					return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Unknown error.").build();
			  }

		  }catch (Exception e){
			  return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Unknown error.\n"+e.getMessage()).build();
		  }      	   	    	
    	
    }

    @DELETE
    @Path("{idUser}/vocabulario/{idVoc}")
    public Response removeVocabularyUser(@PathParam("idUser")int idUser, @PathParam("idVoc") int idVoc, @QueryParam("id") int id, @HeaderParam("token")String token) {
    	try{
			 this.authz = new AuthenticationImpl();			  
			 int validado = this.authz.validaToken(token, id);
			  
			  switch (validado){
			  	case 1:			
			  		//TO-CHECK
			  		 if (vocabularioManager.delete(idVoc))
			             return Response.ok().build();
			         else          
			             return Response.notModified().build();
			  	case -1:
			  		return Response.status(Status.UNAUTHORIZED).entity("Token missmatch with database's token.").build();
			  	case -2:
			  		return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Problems verifing token.").build();
			  	case -3:
			  		return Response.status(Status.UNAUTHORIZED).entity("Invalid token for user with id: "+id).build();
			  	case -4:
			  		return Response.status(Status.NOT_ACCEPTABLE).entity("Token expired.").build();
			  	case -5:
			  		return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Error parsing token").build();
			  	case -6:
			  		return Response.status(Status.INTERNAL_SERVER_ERROR).entity("JOSE exception").build();
				default:
					return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Unknown error.").build();
			  }

		  }catch (Exception e){
			  return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Unknown error.\n"+e.getMessage()).build();
		  } 
       
    }
    
    @POST  
    @Path("{idUser}/test")
    @Produces ("application/json")
    public Response insertTestUser(@PathParam("idUser")int idUser, @QueryParam("id") int id, @HeaderParam("token")String token, @QueryParam ("num") int numPreguntas ){
        
    	try{
			 this.authz = new AuthenticationImpl();			  
			 int validado = this.authz.validaToken(token, id);
			  
			  switch (validado){
			  	case 1:			  		
			  		Test test = testManager.insertTest(idUser, numPreguntas); 
			        if (test != null){
			            return Response.status(Response.Status.OK).entity(test).build();
			        }else{
			        	return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Error creating test.").build();
			        }			        
			  	case -1:
			  		return Response.status(Status.UNAUTHORIZED).entity("Token missmatch with database's token.").build();
			  	case -2:
			  		return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Problems verifing token.").build();
			  	case -3:
			  		return Response.status(Status.UNAUTHORIZED).entity("Invalid token for user with id: "+idUser).build();
			  	case -4:
			  		return Response.status(Status.NOT_ACCEPTABLE).entity("Token expired.").build();
			  	case -5:
			  		return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Error parsing token").build();
			  	case -6:
			  		return Response.status(Status.INTERNAL_SERVER_ERROR).entity("JOSE exception").build();
				default:
					return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Unknown error.").build();
			  }

		  }catch (Exception e){
			  return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Unknown error.\n"+e.getMessage()).build();
		  }
    	
    }
    
    @POST
    @Consumes("application/json")
    public Response createUser(InputStream newUser){
    	
    	StringBuilder sB = new StringBuilder();
    	
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(newUser));
			String line = null;
			while ((line = in.readLine()) != null) {
				sB.append(line);
			}
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Error parsing.").build();
		}
		String result = sB.toString();

		Gson gson = new Gson();
        Usuario nUser = gson.fromJson(result,Usuario.class);
       
    	Usuario userInserted = userManager.addUser(nUser);
    	    	
        if (userInserted == null){
        	return Response.notModified().build();
        }else{
        	return Response.ok(userInserted).build();            
        }
    }
              
    @PUT
    @Path("{idUser}")
    @Consumes("application/json")    
    public Response modifyUser(@PathParam("idUser")int idUser, @QueryParam("id") int id, @HeaderParam("token")String token, Usuario updatedUser) {
    	
    	try{
			 this.authz = new AuthenticationImpl();			  
			 int validado = this.authz.validaToken(token, id);
			  
			  switch (validado){
			  	case 1:			  		
			  		 boolean result = userManager.updateUser(updatedUser);
			         
			         if (result){
			             return Response.ok().build();
			         }else{
			             return Response.notModified().build();
			         } 
			  	case -1:
			  		return Response.status(Status.UNAUTHORIZED).entity("Token missmatch with database's token.").build();
			  	case -2:
			  		return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Problems verifing token.").build();
			  	case -3:
			  		return Response.status(Status.UNAUTHORIZED).entity("Invalid token for user with id: "+idUser).build();
			  	case -4:
			  		return Response.status(Status.NOT_ACCEPTABLE).entity("Token expired.").build();
			  	case -5:
			  		return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Error parsing token").build();
			  	case -6:
			  		return Response.status(Status.INTERNAL_SERVER_ERROR).entity("JOSE exception").build();
				default:
					return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Unknown error.").build();
			  }

		  }catch (Exception e){
			  return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Unknown error.\n"+e.getMessage()).build();
		  }                        
                        
    }

    @DELETE
    @Path("{idUser}")
    public Response removeUser(@PathParam("idUser")int idUser, @QueryParam("id") int id, @HeaderParam("token")String token) {
    	
    	try{
			 this.authz = new AuthenticationImpl();			  
			 int validado = this.authz.validaToken(token, id);
			  
			  switch (validado){
			  	case 1:			  		
			  		if (userManager.delete(idUser))
			            return Response.ok().build();
			        else          
			            return Response.notModified().build();
			  	case -1:
			  		return Response.status(Status.UNAUTHORIZED).entity("Token missmatch with database's token.").build();
			  	case -2:
			  		return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Problems verifing token.").build();
			  	case -3:
			  		return Response.status(Status.UNAUTHORIZED).entity("Invalid token for user with id: "+idUser).build();
			  	case -4:
			  		return Response.status(Status.NOT_ACCEPTABLE).entity("Token expired.").build();
			  	case -5:
			  		return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Error parsing token").build();
			  	case -6:
			  		return Response.status(Status.INTERNAL_SERVER_ERROR).entity("JOSE exception").build();
				default:
					return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Unknown error.").build();
			  }

		  }catch (Exception e){
			  return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Unknown error.\n"+e.getMessage()).build();
		  }  
        
    }


}
