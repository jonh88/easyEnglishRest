package webService.rest;

import java.io.File;
import java.util.ArrayList;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import dao.AuthenticationImpl;

@Path("media")
public class AudioResource {
	
	@Context
    private UriInfo context;
	
	private AuthenticationImpl authz;

	
	public AudioResource(){
		this.authz = new AuthenticationImpl();
	}

	  @GET	  
	  @Produces(MediaType.APPLICATION_OCTET_STREAM)
	  public Response getFile(@QueryParam("id") int idUser, @HeaderParam("token")String token, @QueryParam("name")String path) {
		  try{
			 this.authz = new AuthenticationImpl();			  
			 int validado = this.authz.validaToken(token, idUser);
			  
			  switch (validado){
			  	case 1:
			  		File aux = new File (".");				  
					String rutaTomcat7 = aux.getCanonicalPath()+"/webapps/easyEnglish/resources";
					  
					File file = new File (rutaTomcat7 +"/"+ path);
					return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
					        .header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"" ) //optional
					        .build();
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
	  @Path("/resources")
	  @Produces("application/json")	  
	  public Response getResources (@QueryParam("id") int idUser, @HeaderParam("token") String token){
		  try{
				 this.authz = new AuthenticationImpl();			  
				 int validado = this.authz.validaToken(token, idUser);
				  
				  switch (validado){
				  	case 1:
				  		ArrayList<String> nFicheros = new ArrayList<String>();
				  		File aux = new File (".");				  
						  String rutaTomcat7 = aux.getCanonicalPath()+"/webapps/easyEnglish/resources";
						  File f = new File(rutaTomcat7);
						  if (f.exists()){
							  File [] ficheros = f.listFiles();
							  for(int i = 0; i< ficheros.length; i++){
								  nFicheros.add(ficheros[i].getName());
							  }				 		  							  
						  }else{
							 return Response.status(Status.NOT_FOUND).entity("Directory /webapps/easyEnglish/resources doesn't exist.").build();
						  }
						  return Response.ok(nFicheros).build();
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
