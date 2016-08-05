package domain;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


/**
 *
 * @author Jonh
 */

public class Cuestionario implements Serializable {
  
    private int id;    
    private int user;
    private int fecha;    
    private int preguntas;    
    private int fallos;
   // private Set<Pregunta> preguntas = new HashSet<Pregunta>();
    
    public Cuestionario (){}
    
    public Cuestionario (int usr, int date, int preguntas, int fallos){
        this.user = usr;
        this.fecha = date;
        this.preguntas = preguntas;
        this.fallos = fallos;
    }

    public int getId() { return this.id; }

    public void setId(int idCuestionario) { this.id = idCuestionario; }

    public int getUser() { return user; }

    public void setUser(int idUser) { this.user = idUser; }

    public int getFecha() { return fecha; }

    public void setFecha(int fecha) { this.fecha = fecha; }

    public int getPreguntas() { return this.preguntas; }

    public void setPreguntas(int numPreguntas) { this.preguntas = numPreguntas; }

    public int getFallos() { return this.fallos; }

    public void setFallos(int numFallos) { this.fallos = numFallos; }
    
   // public Set<Pregunta> getPreguntas(){ return this.preguntas; }
    
   // public void setPreguntas(Set<Pregunta> preguntas){ this.preguntas = preguntas;}
    
    
    
}
