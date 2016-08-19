package domain;

import java.io.Serializable;
import java.util.Set;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 *
 * @author Jonh
 */

public class Usuario implements Serializable {
    
    private int id;    
    private String name;    
    private String apellidos;   
    private String email;   
    private String pwd; 
    private Set<Cuestionario> cuestionarios;
    private Set<Vocabulario> vocabularios;
    private Set<Test> tests;
    
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

    @JsonIgnore
    public String getPwd() { return pwd; }

    public void setPwd(String pwd) { this.pwd = pwd; }
    
    @JsonIgnore
    public Set<Cuestionario> getCuestionarios(){ return this.cuestionarios; }
    
    public void setCuestionarios (Set<Cuestionario> cuestionarios) {this.cuestionarios= cuestionarios;}
    
    @JsonIgnore
    public Set<Vocabulario> getVocabularios(){ return this.vocabularios; }
    
    public void setVocabularios (Set<Vocabulario> vocabularios) {this.vocabularios = vocabularios;}
    
    @JsonIgnore
    public Set<Test> getTests(){ return this.tests; }
    
    public void setTests (Set<Test> tests) {this.tests= tests;}
    
    @Override
    public int hashCode(){
    	return new HashCodeBuilder().append(id).build();
    }
    
    @Override
    public boolean equals(Object o){
    	if (o instanceof Usuario) {
            Usuario other = (Usuario) o;
            if (this.id == other.getId()) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String toString(){
    	return ReflectionToStringBuilder.toStringExclude(this, "pwd", "vocabularios", 
    			"cuestionarios", "tests");
    }
}
