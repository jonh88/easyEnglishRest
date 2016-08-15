/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package webService.rest;

import dao.AuthenticationImpl;
import dao.VocabularioDAOImpl;
import dao.UsuarioDAOImpl;
import domain.Test;
import domain.Token;
import domain.Usuario;
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
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;

/**
 * REST Web Service
 *
 * @author Jonh
 */
@Path("/vocabulario")
public class VocabularioResource {

    @Context
    private UriInfo context;
    private VocabularioDAOImpl vocabularioManager;
    private AuthenticationImpl authz;
    private UsuarioDAOImpl userManager;
    
    public VocabularioResource() {
        this.vocabularioManager = new VocabularioDAOImpl();
        this.authz = new AuthenticationImpl();
        this.userManager = new UsuarioDAOImpl();
    }

    
    @GET
    @Path("{idVoc}")
    @Produces("application/json")
    public Response getVocabulary(@QueryParam("id") int id, @HeaderParam("token")String token, @PathParam("idVoc") int idVoc) {
    	
    	try{
			 this.authz = new AuthenticationImpl();			  
			 int validado = this.authz.validaToken(token, id);
			  
			  switch (validado){
			  	case 1:			  		
			  		//buscamos el vocabulario en la BD, lo instanciamos y devolvemos
			        Vocabulario vocab = vocabularioManager.findVocabularyById(idVoc);
			        
			        if (vocab == null){
			            return Response.status(Status.NOT_FOUND).build();
			        }
			       
			        return Response.status(Status.OK).entity(vocab).build();
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
    @Path("{idVoc}")
    @Consumes(MediaType.APPLICATION_JSON)    
    public Response updateVocabulary(@PathParam("idVoc") int idVoc, @QueryParam("id")int id, Vocabulario updatedVocab, @HeaderParam("token")String token) {
    	try{
			 this.authz = new AuthenticationImpl();			  
			 int validado = this.authz.validaToken(token, id);
			  
			  switch (validado){
			  	case 1:		
			  		
			  		if ((vocabularioManager.findVocabularyById(idVoc) != null)&&(vocabularioManager.updateVocabulario(updatedVocab)== true)){
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
   
}
