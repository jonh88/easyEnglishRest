package webService.rest;

import dao.AuthenticationImpl;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;




@Path("/hello")
public class Hello {
	
	private AuthenticationImpl auth;
	
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response sayHtmlHello(@HeaderParam("token") String token, @QueryParam("id") int id) {
	  this.auth = new AuthenticationImpl();	
	  
	  int validado = this.auth.validaToken(token, id);
	  
	  return Response.ok(validado).build();
  	
  }

} 