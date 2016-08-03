package dao;

import java.util.List;

import domain.Cuestionario;
import domain.Pregunta;

public interface ICuestionarioDAO {

	public int addCuestionario (int client, int numPreguntas);
    
    public int modify (Cuestionario c);
    
    public Cuestionario getCuestionarioObject(Integer id);
    
    public List<Pregunta> listCuestionario (int idUser);
    
    public boolean delete (int id);
}
