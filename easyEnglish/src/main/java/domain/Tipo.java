/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domain;

import java.io.Serializable;
import java.util.Set;

import javax.xml.bind.annotation.*;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonManagedReference;

//@XmlRootElement(name="tipo")
//@XmlAccessorType(XmlAccessType.FIELD)
public class Tipo implements Serializable {
    //@XmlAttribute
    private int id;
    //@XmlElement
    private String type;
    private Set<Vocabulario> vocabularios;
    
    public Tipo(){} 
    
    public Tipo (String t){
        this.type = t;
    }    

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }
    
    @JsonIgnore
    public Set<Vocabulario> getVocabularios(){ return this.vocabularios; }
    
    public void setVocabularios (Set<Vocabulario> vocabularios) {this.vocabularios= vocabularios;}
    
}
