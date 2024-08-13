package model.dao;

import java.util.List;

import model.entities.Student;

public interface StudentDao {
	
	void insert(Student obj);
	void update(Student obj);
	void deleteById(Integer id);
	Student findById(Integer id);
	Student findByRa(String ra);
	Student findByEmail(String email);
	Student findByEmailPass(String email, String pass);
	List<Student> findAll();
	
}
