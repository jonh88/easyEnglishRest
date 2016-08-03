package webService.rest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import dao.AuthenticationImpl;
import domain.Token;

@Path("/authz")
public class Authz {
	
	private AuthenticationImpl auth;
	
	  @GET		  
	  @Produces("application/json")
	  public Response getToken(@QueryParam("user")String email, @QueryParam("pass") String pass)  {
		  this.auth = new AuthenticationImpl();
		  String token = this.auth.getToken(email, pass);
		  
		  if (token == null)
			  return Response.status(Response.Status.FORBIDDEN).build();
		  else
			  return Response.ok(token).build();
	  }
	
}


