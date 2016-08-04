package dao;

import java.util.List;

import domain.Cuestionario;
import domain.Pregunta;

public interface ICuestionarioDAO {

	public Cuestionario insertCuestionario (int client, int numPreguntas);
    
    public boolean update (Cuestionario c);
    
    public Cuestionario getCuestionarioObject(Integer id);
    
    public List<Pregunta> listCuestionario (int idUser);
    
    public boolean delete (int id);
}
