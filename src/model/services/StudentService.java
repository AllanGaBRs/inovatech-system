package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.StudentDao;
import model.entities.Student;

public class StudentService {

	private StudentDao dao = DaoFactory.createStudentDao();
	
	public List<Student> findAll(){
		return dao.findAll();
	}
}
