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
@XmlRootElement(name="test")
@XmlAccessorType(XmlAccessType.FIELD)
public class Test implements Serializable {
    @XmlAttribute
    private int idTest;
    @XmlElement
    private int idUsr;
    @XmlElement
    private int fecha;
    @XmlElement
    private int numPreguntas;
    @XmlElement
    private int numFallos;
    
    public Test(){}
    
    public Test (int usr, int date, int preguntas, int fallos){
        this.idUsr = usr;
        this.fecha = date;
        this.numPreguntas = preguntas;
        this.numFallos = fallos;
    }

    public int getIdTest() { return idTest; }

    public void setIdTest(int idTest) { this.idTest = idTest; }

    public int getIdUsr() { return idUsr; }

    public void setIdUsr(int idUsr) { this.idUsr = idUsr; }

    public int getFecha() { return fecha; }

    public void setFecha(int fecha) { this.fecha = fecha; }

    public int getNumPreguntas() { return numPreguntas; }

    public void setNumPreguntas(int numPreguntas) { this.numPreguntas = numPreguntas; }

    public int getNumFallos() { return numFallos; }

    public void setNumFallos(int numFallos) { this.numFallos = numFallos; }
    
}
