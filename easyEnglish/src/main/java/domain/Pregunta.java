package domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Jonh
 */

public class Pregunta implements Serializable {
    
    private int id;    
    private String pregunta;    
    private String respA;
    private String respB;
    private String respC;
    private String respD;
    private String respOK;
    //private Set<Cuestionario> cuestionarios = new HashSet();
    
    
    public Pregunta (){}
    
    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getPregunta() { return this.pregunta; }

    public void setPregunta(String pregunta) { this.pregunta = pregunta; }

    public String getRespA() { return this.respA; }

    public void setRespA(String res) { this.respA = res; }

    public String getRespB() { return this.respB; }

    public void setRespB(String res) { this.respB = res; }
    
    public String getRespC() { return this.respC; }

    public void setRespC(String res) { this.respC = res; }
    
    public String getRespD() { return this.respD; }

    public void setRespD(String res) { this.respD = res; }
    
    public String getRespOK() { return this.respOK; }

    public void setRespOK(String res) { this.respOK = res; }
    /*
    public Set<Cuestionario> getCuestionarios(){ return this.cuestionarios; }
    
    public void setCuestionarios (Set<Cuestionario> cuestionarios) {this.cuestionarios= cuestionarios;}
    */
    
}