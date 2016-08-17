package dao;


import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import conn.ConnectionDB;
import domain.Usuario;

public class AuthenticationImpl implements IAuthentication{

	private ConnectionDB cn;	
	private UsuarioDAOImpl user;
    private static final byte [] secret = "[B@70306774P@sSWord32EaSyenglisH".getBytes();
    private static final String keyPass = "_easyEnglish2016"; //128 bits key
    private static final String initVector = "RandomInitVector"; //16 bytes IV
    
    public AuthenticationImpl(){    	
        this.cn = new ConnectionDB();
        this.user = new UsuarioDAOImpl();        
    }
	
	public String getToken(String email, String pwd){
		//compruebo credenciales en bbdd
		int idUser = checkUser(email,pwd);
		if (idUser == 0)
			return null;
		
		//genero token
		String token = generateToken (idUser);
		
		//almaceno token en bbdd
		if(setToken(token, email))
			return token;
		else
			return null;
	}
	
	public int validaToken(String token, int idUser){
		
		if (!checkToken(token,idUser))
			return -1;
		
    	// On the consumer side, parse the JWS and verify its HMAC
    	SignedJWT signedJWTClient;
		try {
			signedJWTClient = SignedJWT.parse(token);
			
	    	JWSVerifier verifier = new MACVerifier(AuthenticationImpl.secret);	
	    	if(!signedJWTClient.verify(verifier)){
	    		return -2;
	    	}
	    	//comprueba usuario en token
	    	if (!signedJWTClient.getJWTClaimsSet().getSubject().equals(String.valueOf(idUser)))
	    		return -3;
	    	
	    	//comprueba token no caducado
	    	if (!new Date().before(signedJWTClient.getJWTClaimsSet().getExpirationTime()))	    		    	
	    		return -4;
	    	
	    	return 1;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -5;
		} catch (JOSEException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -6;
		}
		
	}
	
	private String generateToken (int idUser){
		/*Generate random 256-bit (32-byte) shared secret
    	SecureRandom random = new SecureRandom();
    	byte[] sharedSecret = new byte[32];
    	sharedSecret = AuthenticationImpl.secret;
    	random.nextBytes(sharedSecret);
    	*/
    	// Create HMAC signer
    	JWSSigner signer = new MACSigner(AuthenticationImpl.secret);

    	// Prepare JWT with claims set    	    	
    	JWTClaimsSet claimsSet = new JWTClaimsSet();
    	claimsSet.setSubject(String.valueOf(idUser));
    	//claimsSet.setIssuer("http://easyEnglish/rest");
    	//claimsSet.setClaim("rol", "user");
    	//validez de una hora
    	claimsSet.setExpirationTime(new Date(new Date().getTime() + 60 * 60 * 1000));

    	SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);

    	// Apply the HMAC protection
    	try {
			signedJWT.sign(signer);
		} catch (JOSEException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	// Serialize to compact form, produces something like
    	// eyJhbGciOiJIUzI1NiJ9.SGVsbG8sIHdvcmxkIQ.onO9Ihudz3WkiauDO2Uhyuz0Y18UASXlSc1eS0NkWyA
    	String token = signedJWT.serialize();
    	
    	return token;
	}
	
	private boolean setToken(String token, String email) {
		
		try {
			//fecha
			java.sql.Date fecha = new java.sql.Date(new java.util.Date().getTime());            
			//id del usuario a partir del mail
			Usuario user = this.user.findUserByEmail(email);
			int id_user = 0;
			if (user != null){
				id_user = user.getId();
			}else{
				throw new NullPointerException();
			}									        
            
			if (this.tokenExist(id_user)){
				//si existe hago un update
				String sqlU = "update tbl_authz set access_token = ?, fecha= ? where id_user = ?";
				Connection cn = this.cn.getConn();
				PreparedStatement ps = cn.prepareStatement(sqlU);
				ps.setString(1, token);
				ps.setDate(2, fecha);
				//ps.setInt(2, Integer.parseInt(fechaActual));
				ps.setInt(3, id_user);
				
				int rs = ps.executeUpdate();
				
				cn.close();
				ps.close();
				
				if (rs>0)
					return true;
				
				return false;								
			}else{
				//si no existe inserto				
				String sql = "insert into tbl_authz (id_user, access_token, fecha) values (?,?,?)";
				Connection cn = this.cn.getConn();
				PreparedStatement ps = cn.prepareStatement(sql);
				ps.setInt(1, id_user);
				ps.setString(2, token);
				ps.setDate(3, fecha);	            
				
				int rs = ps.executeUpdate();
				
				cn.close();
				ps.close();
				
				if (rs>0)
					return true;
				
				return false;
			}						
			
		}catch (NullPointerException e){
			System.out.println("NullPointerException: "+ e.getMessage().toString());
			return false;
		}catch (SQLException e){
			System.out.println("SQLException: "+ e.getMessage().toString());
			return false;
		}
	}
	
	private boolean tokenExist(int id_user){
		
		String sql = "select * from tbl_authz where id_user = ?";
		Connection cn = null;
		ResultSet rs = null;
		try{
			cn = this.cn.getConn();
			PreparedStatement ps = cn.prepareStatement(sql);
			ps.setInt(1, id_user);
			
			rs = ps.executeQuery();
						
			boolean f = false;
			if (rs.next())
				f = true;
			cn.close();
			rs.close();
			return f;			
						
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	public int checkUser(String email, String psw) {
		
		String sql = "select * from tbl_users where email= ?";
		String passEncripted = "";
		int idUsr = 0;
		try {
			Connection c = this.cn.getConn();
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, email);
						
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()){
				passEncripted = rs.getString("pwd");
				idUsr = rs.getInt("Id_usr");				
			}
			ps.close();
			rs.close();
			c.close();
			//encriptar la psw 
			String encrypted = this.encrypt(psw);
												
			if (passEncripted.equals(encrypted))
				return idUsr;
			else
				return 0;
			
		}catch (Exception e){
			e.printStackTrace();
			return 0;
		}
				
	}

	private boolean checkToken(String token, int id_user) {
		String sql = "select * from tbl_authz where id_user = ?";
		String p = "";
		int id =-1;
		try {
			Connection cn = this.cn.getConn();
			PreparedStatement ps = cn.prepareStatement(sql);
			ps.setInt(1, id_user);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()){
				p = rs.getString("access_token");
				id = rs.getInt("id_user");
			}
			
			cn.close();
			ps.close();
			
			if ((p.equals(token))&&(id == id_user))
				return true;
			
			return false;
			
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	public String encrypt (String value){
		try {
            IvParameterSpec iv = new IvParameterSpec(this.initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(this.keyPass.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());
           // System.out.println("encrypted string: "
             //       + Base64.encodeBase64String(encrypted));

            return Base64.encodeBase64String(encrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
	}

	private String decrypt(String encrypted) {
        try {
            IvParameterSpec iv = new IvParameterSpec(this.initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(this.keyPass.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));

            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
