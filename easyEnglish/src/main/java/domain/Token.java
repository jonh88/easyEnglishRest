package domain;

import java.io.Serializable;
import javax.xml.bind.annotation.*;


@XmlRootElement(name="token")
@XmlAccessorType(XmlAccessType.FIELD)
public class Token implements Serializable{
	@XmlAttribute
    private int idUsr;
    @XmlElement
    private String token;
  
    public Token (){}
    
    public Token (int idUser){
    	
    	
    }
    
    public int getIdUsr () { return this.idUsr; }
    
    public void setIdUsr (int id){ this.idUsr = id; }
    
    public String getToken(){ return this.token; }
    
    public void setToken(String t){ this.token = t; }
       

}
