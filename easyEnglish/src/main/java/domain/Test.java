/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domain;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.*;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author Jonh
 */

public class Test implements Serializable {
    private int id;
    private Usuario user;
    private int fecha;    
    private int numPreguntas;
    private int numFallos;
    Set<Vocabulario> vocabularios = new HashSet<Vocabulario>();
    
    public Test(){}
    
    public Test (Usuario user, int date, int preguntas, int fallos){
        this.user = user;
        this.fecha = date;
        this.numPreguntas = preguntas;
        this.numFallos = fallos;
    }

    public int getId() { return this.id; }

    public void setId(int idTest) { this.id = idTest; }

    @JsonIgnore
    public Usuario getUser() { return this.user; }

    public void setUser(Usuario user) { this.user = user; }

    public int getFecha() { return fecha; }

    public void setFecha(int fecha) { this.fecha = fecha; }

    public int getNumPreguntas() { return numPreguntas; }

    public void setNumPreguntas(int numPreguntas) { this.numPreguntas = numPreguntas; }

    public int getNumFallos() { return numFallos; }

    public void setNumFallos(int numFallos) { this.numFallos = numFallos; }
    
    @JsonIgnore
    public Set<Vocabulario> getVocabularios() {return this.vocabularios; }
    
    public void setVocabularios(Set<Vocabulario> vocs) {this.vocabularios = vocs;}
    
    @Override
    public int hashCode(){
    	return new HashCodeBuilder().append(id).build();
    }
    
    @Override
    public boolean equals(Object o){
    	if (o instanceof Cuestionario) {
    		Test other = (Test) o;
            if (this.id == other.getId()) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String toString(){
    	return ReflectionToStringBuilder.toStringExclude(this, "user", "vocabularios");
    }
}
