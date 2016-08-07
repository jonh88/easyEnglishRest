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

/**
 *
 * @author Jonh
 */
@XmlRootElement(name="test")
@XmlAccessorType(XmlAccessType.FIELD)
public class Test implements Serializable {
    @XmlAttribute
    private int id;
    @XmlElement
    private Usuario user;
    @XmlElement
    private int fecha;
    @XmlElement
    private int numPreguntas;
    @XmlElement
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

    public Usuario getUser() { return this.user; }

    public void setUser(Usuario user) { this.user = user; }

    public int getFecha() { return fecha; }

    public void setFecha(int fecha) { this.fecha = fecha; }

    public int getNumPreguntas() { return numPreguntas; }

    public void setNumPreguntas(int numPreguntas) { this.numPreguntas = numPreguntas; }

    public int getNumFallos() { return numFallos; }

    public void setNumFallos(int numFallos) { this.numFallos = numFallos; }
    
    public Set<Vocabulario> getVocabularios() {return this.vocabularios; }
    
    public void setVocabularios(Set<Vocabulario> vocs) {this.vocabularios = vocs;}
    
}
