/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domain;

import java.io.Serializable;
import javax.xml.bind.annotation.*;

/**
 *
 * @author Jonh
 */

@XmlRootElement(name="usuario")
@XmlAccessorType(XmlAccessType.FIELD)
public class Usuario implements Serializable {
    
    @XmlAttribute
    private int id;
    @XmlElement
    private String name;
    @XmlElement
    private String apellidos;
    @XmlElement
    private String email;
    @XmlElement
    private String pwd;
    
    public Usuario (){}    
    
    
    public Usuario (String nombre, String apellidos, String email, String pwd){
        this.email = email;
        this.name = nombre;
        this.apellidos = apellidos;
        this.pwd = pwd;
    }

    public int getId() { return id; }

    public void setId(int i){ this.id = i; }
    
    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getApellidos() { return apellidos; }

    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPwd() { return pwd; }

    public void setPwd(String pwd) { this.pwd = pwd; }
    
    
}