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
@XmlRootElement(name="vocabulario")
@XmlAccessorType(XmlAccessType.FIELD)
public class Vocabulario implements Serializable {
    
    @XmlAttribute
    private int id;
    @XmlElement
    private String english;
    @XmlElement
    private String spanish;
    @XmlElement
    private int tipo;
    
    public Vocabulario (){}
    
    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getEnglish() { return english; }

    public void setEnglish(String english) { this.english = english; }

    public String getSpanish() { return spanish; }

    public void setSpanish(String spanish) { this.spanish = spanish; }

    public int getTipo() { return tipo; }

    public void setTipo(int tipo) { this.tipo = tipo; }
    
}
