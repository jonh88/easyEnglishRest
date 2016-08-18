package webService.rest;

import dao.AuthenticationImpl;
import dao.CuestionarioDAOImpl;
import domain.Cuestionario;
import domain.Pregunta;
import java.util.Set;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

//import com.google.gson.Gson;


/**
 * REST Web Service
 *
 * @author Jonh
 */
@Path("cuestionario")
public class CuestionarioResource {

    @Context
    private UriInfo context;
    private CuestionarioDAOImpl cuestionarioManager;
    private AuthenticationImpl authz;
    
    /**
     * Creates a new instance of CuestionarioResource
     */
    public CuestionarioResource() {
        this.cuestionarioManager = new CuestionarioDAOImpl();
        this.authz = new AuthenticationImpl();        
    }

   
    @GET
    @Path("{idCuest}")
    @Produces("application/json")
    public Response getCuestionario (@QueryParam("id") int idUser, @PathParam("idCuest") int idCuest, @HeaderParam("token")String token) {
    	try{
			 this.authz = new AuthenticationImpl();			  
			 int validado = this.authz.validaToken(token, idUser);
			  
			  switch (validado){
			  	case 1:
			  		
			        Cuestionario c = cuestionarioManager.getCuestionarioObject(idCuest);
			        
			        if (c == null){
			            return Response.status(Response.Status.NOT_FOUND).build();
			        }
			        
			        return Response.status(Response.Status.OK).entity(c).type(MediaType.APPLICATION_JSON).build();

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
    @Path("{idCues}/questions")
    @Produces("application/json")
    public Response getCuestionarioQuestions (@QueryParam("id") int id, @PathParam("idCues") int idCues, @HeaderParam("token")String token) {
    	try{
			 this.authz = new AuthenticationImpl();			  
			 int validado = this.authz.validaToken(token, id);
			  
			  switch (validado){
			  	case 1:
			  		 
			        Set<Pregunta> result = cuestionarioManager.getPreguntasCuestionario(idCues);
			        
			        if ((result == null)||(result.isEmpty())){
			        	return Response.status(Response.Status.NOT_FOUND).entity("Empty exam.").build();
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
    @Path("{idCues}")
    @Consumes ("application/json")
    public Response updateCuestionario(@PathParam("idCues")int idCues, @QueryParam("id") int idUser, @HeaderParam("token")String token, Cuestionario newCuest){
        
    	try{
			 this.authz = new AuthenticationImpl();			  
			 int validado = this.authz.validaToken(token, idUser);
			  
			  switch (validado){
			  	case 1:			  		
			  		/*StringBuilder sB = new StringBuilder();
			    	
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
			        */
			    	boolean res = cuestionarioManager.update(newCuest);
			    	
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
    @Path("{idCues}")
    public Response removeCuestionario(@QueryParam("id")int idUser, @PathParam("idCues") int idCues, @HeaderParam("token")String token) {        
    	try{
			 this.authz = new AuthenticationImpl();			  
			 int validado = this.authz.validaToken(token, idUser);
			  
			  switch (validado){
			  	case 1:			  		
			  		 boolean r = cuestionarioManager.delete(idCues);			         
			         if (r == false)
			             return Response.status(Status.NOT_MODIFIED).entity("Test with id: "+idCues+ " has not been deleted.").build();
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
