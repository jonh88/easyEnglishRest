package webService.rest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import dao.AuthenticationImpl;
import domain.Token;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import conn.ConnectionDB;


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