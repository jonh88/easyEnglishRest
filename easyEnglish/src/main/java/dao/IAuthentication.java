package dao;

public interface IAuthentication {
	
	public int validaToken (String token, int idUser);
	
	public String getToken (String email, String pwd);
	

}
