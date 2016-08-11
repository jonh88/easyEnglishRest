package domain;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonManagedReference;


/**
 *
 * @author Jonh
 */

public class Cuestionario implements Serializable {
  
    private int id;  
    private Usuario user;
    private int fecha;    
    private int numPreguntas;    
    private int numFallos;    
    private Set<Pregunta> preguntas = new HashSet<Pregunta>();
    
    public Cuestionario (){}
    
    public Cuestionario (Usuario usr, int date, int preguntas, int fallos){
        this.user = usr;
        this.fecha = date;
        this.numPreguntas = preguntas;
        this.numFallos = fallos;
    }

    public int getId() { return this.id; }

    public void setId(int idCuestionario) { this.id = idCuestionario; }

    public Usuario getUser() { return user; }

    public void setUser(Usuario user) { this.user = user; }

    public int getFecha() { return fecha; }

    public void setFecha(int fecha) { this.fecha = fecha; }

    public int getNumPreguntas() { return this.numPreguntas; }

    public void setNumPreguntas(int numPreguntas) { this.numPreguntas = numPreguntas; }

    public int getNumFallos() { return this.numFallos; }

    public void setNumFallos(int numFallos) { this.numFallos = numFallos; }
    
    public Set<Pregunta> getPreguntas(){ return this.preguntas; }
    
    public void setPreguntas(Set<Pregunta> preguntas){ this.preguntas = preguntas;}
    
    
    
}
