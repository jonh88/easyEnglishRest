package webService.rest;

import dao.AuthenticationImpl;
import dao.PreguntaDAOImpl;
import domain.Pregunta;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

/**
 * REST Web Service
 *
 * @author Jonh
 */
@Path("/pregunta")
public class PreguntaResource {

    @Context
    private UriInfo context;
    private PreguntaDAOImpl preguntaManager;
    private AuthenticationImpl authz;
    
    
    public PreguntaResource() {
        this.preguntaManager = new PreguntaDAOImpl();
        this.authz = new AuthenticationImpl();        
    }

    
    @GET
    @Path("{idPreg}")
    @Produces("application/json")
    public Response getPregunta(@QueryParam("id") int id, @HeaderParam("token")String token, @PathParam("idPreg") int idPreg) {
    	
    	try{
			 this.authz = new AuthenticationImpl();			  
			 int validado = this.authz.validaToken(token, id);
			  
			  switch (validado){
			  	case 1:			  		
			  		//buscamos el pregunta en la BD, lo instanciamos y devolvemos
			        Pregunta p = preguntaManager.getPregunta(idPreg);
			        
			        if (p == null){
			            return Response.status(Status.NOT_FOUND).build();
			        }
			       
			        return Response.status(Status.OK).entity(p).build();
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
    @Path("{idPreg}")
    @Consumes(MediaType.APPLICATION_JSON)    
    public Response updatePregunta(@PathParam("idPreg") int idPreg, @QueryParam("id")int id, Pregunta updatedPregunta, @HeaderParam("token")String token) {
    	try{
			 this.authz = new AuthenticationImpl();			  
			 int validado = this.authz.validaToken(token, id);
			  
			  switch (validado){
			  	case 1:		
			  		
			  		if ((preguntaManager.getPregunta(idPreg) != null)&&(preguntaManager.updatePregunta(updatedPregunta)== true)){
			            return Response.status(Status.OK).build();
			        }else{
			        	return Response.notModified().build();
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

    @POST
    @Consumes(MediaType.APPLICATION_JSON)  
    public Response insertarPregunta(@QueryParam("id")int id, @HeaderParam("token")String token, Pregunta newPregunta){
    	try{
			 this.authz = new AuthenticationImpl();			  
			 int validado = this.authz.validaToken(token, id);
			  
			  switch (validado){
			  	case 1:		
			  		
			  		if ((preguntaManager.insertPregunta(newPregunta) != null)){
			            return Response.status(Status.OK).entity(newPregunta).type(MediaType.APPLICATION_JSON).build();
			        }else{
			        	return Response.notModified().build();
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
}
