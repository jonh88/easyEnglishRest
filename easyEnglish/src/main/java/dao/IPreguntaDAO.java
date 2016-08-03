package dao;

import java.util.List;

import domain.Pregunta;

public interface IPreguntaDAO {
	
	public Pregunta insertPregunta (Pregunta q);
    
    public boolean updatePregunta (Pregunta vocModified);
    
    public Pregunta getPregunta(int id);
        
    public boolean delete (int idVoc);
}
