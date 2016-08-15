/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package webService.rest;

import dao.AuthenticationImpl;
import dao.TipoDAOImpl;
import domain.Tipo;
import domain.Vocabulario;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;

import javax.ws.rs.core.UriInfo;

/**
 * REST Web Service
 *
 * @author Jonh
 */
@Path("tipoVoc")
public class TipoVocResource {

    @Context
    private UriInfo context;
    private TipoDAOImpl tipoManager;
    private AuthenticationImpl authz;
    
    /**
     * Creates a new instance of TipoVocResource
     */
    public TipoVocResource() {
        this.tipoManager = new TipoDAOImpl();
        this.authz = new AuthenticationImpl();
    }

    @GET
    @Produces("application/json")
    public Response getTipos(@QueryParam("id") int id, @HeaderParam("token")String token) {
    	try{
			 this.authz = new AuthenticationImpl();			  
			 int validado = this.authz.validaToken(token, id);
			  
			  switch (validado){
			  	case 1:			  		
			  		//todos los tipos de palabras   
			        List<Tipo> result = tipoManager.getTipos();
			        
			        if (result != null){
			            return Response.ok(result).build();
			        }else{
			        	return Response.noContent().build();
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
    @Consumes("application/json")
    public Response createTipo(@QueryParam("id") int id, @HeaderParam("token")String token, InputStream newTipo){
    	try{
			 this.authz = new AuthenticationImpl();			  
			 int validado = this.authz.validaToken(token, id);
			  
			  switch (validado){
			  	case 1:	
			  		StringBuilder sB = new StringBuilder();
			    	
					try {
						BufferedReader in = new BufferedReader(new InputStreamReader(newTipo));
						String line = null;
						while ((line = in.readLine()) != null) {
							sB.append(line);
						}
					} catch (Exception e) {
						return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Error parsing").build();
					}
					String result = sB.toString();

					Gson gson = new Gson();
			        Tipo t = gson.fromJson(result,Tipo.class);
			  					  		
			  		Tipo tipoNuevo = tipoManager.addTipo(t);
			    	
			        if (tipoNuevo != null){			            
			            return Response.status(Status.OK).entity(tipoNuevo).type(MediaType.APPLICATION_JSON).build();
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
              
    @PUT
    @Path("{idTipo}")
    @Consumes("application/json")    
    public Response modifyTipo(@PathParam("idTipo") int idTipo, @QueryParam("id") int id, @HeaderParam("token")String token, InputStream updatedTipo) {
    	try{
			 this.authz = new AuthenticationImpl();			  
			 int validado = this.authz.validaToken(token, id);
			  
			  switch (validado){
			  	case 1:	
			  		StringBuilder sB = new StringBuilder();
			    	
					try {
						BufferedReader in = new BufferedReader(new InputStreamReader(updatedTipo));
						String line = null;
						while ((line = in.readLine()) != null) {
							sB.append(line);
						}
					} catch (Exception e) {
						return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Error parsing").build();
					}
					String result = sB.toString();

					Gson gson = new Gson();
			        Tipo t = gson.fromJson(result,Tipo.class);
			  					  		
			        boolean res = tipoManager.modify(t);
			                     
			        if (res){
			            return Response.ok().build();
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

    @DELETE
    @Path("{idTipo}")
    public Response remove(@PathParam("idTipo") int idTipo, @QueryParam("id") int id, @HeaderParam("token")String token) {
    	try{
			 this.authz = new AuthenticationImpl();			  
			 int validado = this.authz.validaToken(token, id);
			  
			  switch (validado){
			  	case 1:			  		
			  	//primero obtengo el Tipo para ver que existe y sino lanzar error
			        Tipo tipo = tipoManager.getTipo(idTipo);			        
			        if (tipo == null)
			        	return Response.status(Status.NOT_FOUND).build();			        
			        if (tipoManager.delete(idTipo))
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
}
