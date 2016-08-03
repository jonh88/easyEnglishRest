package domain;
import java.io.Serializable;


/**
 *
 * @author Jonh
 */

public class Cuestionario implements Serializable {
  
    private int id;    
    private int id_user;
    private int fecha;    
    private int num_preguntas;    
    private int num_fallos;
    
    public Cuestionario(){}
    
    public Cuestionario (int usr, int date, int preguntas, int fallos){
        this.id_user = usr;
        this.fecha = date;
        this.num_preguntas = preguntas;
        this.num_fallos = fallos;
    }

    public int getIdCuestionario() { return this.id; }

    public void setIdCuestionario(int idCuestionario) { this.id = idCuestionario; }

    public int getIdUser() { return id_user; }

    public void setIdUser(int idUser) { this.id_user = idUser; }

    public int getFecha() { return fecha; }

    public void setFecha(int fecha) { this.fecha = fecha; }

    public int getNumPreguntas() { return this.num_preguntas; }

    public void setNumPreguntas(int numPreguntas) { this.num_preguntas = numPreguntas; }

    public int getNumFallos() { return this.num_fallos; }

    public void setNumFallos(int numFallos) { this.num_fallos = numFallos; }
    
}
