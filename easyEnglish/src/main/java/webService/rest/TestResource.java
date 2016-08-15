package webService.rest;

import dao.AuthenticationImpl;
import dao.TestDAOImpl;
import dao.UsuarioDAOImpl;
import domain.Test;
import domain.Usuario;
import domain.Vocabulario;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Set;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;


/**
 * REST Web Service
 *
 * @author Jonh
 */
@Path("test")
public class TestResource {

    @Context
    private UriInfo context;
    private TestDAOImpl testManager;
    private AuthenticationImpl authz;
    private UsuarioDAOImpl userManager;
    
    /**
     * Creates a new instance of TestResource
     */
    public TestResource() {
        this.testManager = new TestDAOImpl();
        this.authz = new AuthenticationImpl();
        this.userManager = new UsuarioDAOImpl();
    }

   
    @GET
    @Path("{idTest}")
    @Produces("application/json")
    public Response getTest (@QueryParam("id") int idUser, @PathParam("idTest") int idTest, @HeaderParam("token")String token) {
    	try{
			 this.authz = new AuthenticationImpl();			  
			 int validado = this.authz.validaToken(token, idUser);
			  
			  switch (validado){
			  	case 1:
			  		//buscamos el usuario en la BD, lo instanciamos y devolvemos
			        Test t = testManager.getTestObject(idTest);
			        
			        if (t == null){
			            return Response.status(Response.Status.NOT_FOUND).build();
			        }
			        
			        return Response.status(Response.Status.OK).entity(t).type(MediaType.APPLICATION_JSON).build();

			  	case -1:
			  		return Response.status(Status.UNAUTHORIZED).entity("Token missmatch with database's token.").build();
			  	case -2:
			  		return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Problems checking token.").build();
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
    @Path("{idTest}/questions")
    @Produces("application/json")
    public Response getTestQuestions (@QueryParam("id") int id, @PathParam("idTest") int idTest, @HeaderParam("token")String token) {
    	try{
			 this.authz = new AuthenticationImpl();			  
			 int validado = this.authz.validaToken(token, id);
			  
			  switch (validado){
			  	case 1:
			  		//buscamos el usuario en la BD, lo instanciamos y devolvemos   
			        Set<Vocabulario> result = testManager.getVocabularioTest(idTest);
			        
			        if ((result == null)||(result.isEmpty())){
			        	return Response.status(Response.Status.NOT_FOUND).entity("Empty test.").build();
			        }

			        return Response.status(Response.Status.OK).entity(result).type(MediaType.APPLICATION_JSON).build();

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
      
    @PUT
    @Path("{idTest}")
    @Consumes ("application/json")
    public Response updateTest(@PathParam("idTest")int idTest, @QueryParam("id") int idUser, @HeaderParam("token")String token, InputStream test){
        
    	try{
			 this.authz = new AuthenticationImpl();			  
			 int validado = this.authz.validaToken(token, idUser);
			  
			  switch (validado){
			  	case 1:			  		
			  		StringBuilder sB = new StringBuilder();
			    	
					try {
						BufferedReader in = new BufferedReader(new InputStreamReader(test));
						String line = null;
						while ((line = in.readLine()) != null) {
							sB.append(line);
						}
					} catch (Exception e) {
						return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Error parsing."+e.getMessage()).build();
					}
					String result = sB.toString();

					Gson gson = new Gson();
			        Test t = gson.fromJson(result,Test.class);			        		       
			        
			    	boolean res = testManager.update(t);
			    	
			        if (res){
			            return Response.ok().build();
			        }else{
			        	return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Error updating test").build();
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
    @Path("{idTest}")
    public Response removeTest(@QueryParam("id")int idUser, @PathParam("idTest") int idTest, @HeaderParam("token")String token) {        
    	try{
			 this.authz = new AuthenticationImpl();			  
			 int validado = this.authz.validaToken(token, idUser);
			  
			  switch (validado){
			  	case 1:			  		
			  		 boolean r = testManager.delete(idTest);			         
			         if (r == false)
			             return Response.status(Status.NOT_MODIFIED).entity("Test with id: "+idTest+ " has not been deleted.").build();
			         else
			         	return Response.ok().build();			        
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
