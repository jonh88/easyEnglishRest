/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domain;

import java.io.Serializable;
import java.util.Set;

import javax.xml.bind.annotation.*;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author Jonh
 */

public class Vocabulario implements Serializable {
    
    private int id;
    private String english;    
    private String spanish;    
    private Tipo tipo;
    private Set<Usuario> usuarios;
    private Set<Test> tests;
    
    public Vocabulario (){}
    
    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getEnglish() { return english; }

    public void setEnglish(String english) { this.english = english; }

    public String getSpanish() { return spanish; }

    public void setSpanish(String spanish) { this.spanish = spanish; }

    public Tipo getTipo() { return tipo; }

    public void setTipo(Tipo tipo) { this.tipo = tipo; }
    
    @JsonIgnore
    public Set<Usuario> getUsuarios(){ return this.usuarios; }
    
    public void setUsuarios (Set<Usuario> users) {this.usuarios = users;}
    
    @JsonIgnore
    public Set<Test> getTests(){ return this.tests; }
    
    public void setTests (Set<Test> tests) {this.tests = tests;}
    
    @Override
    public int hashCode(){
    	return new HashCodeBuilder().append(id).build();
    }
    
    @Override
    public boolean equals(Object o){
    	if (o instanceof Cuestionario) {
    		Tipo other = (Tipo) o;
            if (this.id == other.getId()) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String toString(){
    	return ReflectionToStringBuilder.toStringExclude(this, "usuarios", "tests");
    }
}
