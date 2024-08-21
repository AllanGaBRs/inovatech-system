package model.dao;

import java.util.List;

import model.entities.Report;
import model.entities.Student;

public interface StudentDao {
	
	void insert(Student obj);
	void update(Student obj);
	void updateHours(Student obj, Double hours);
	void updateDate(Student obj, java.sql.Date date);
	Student selectDate(Integer id);
	void deleteById(Integer id);
	Student findById(Integer id);
	Student findByRa(String ra);
	Student findByEmail(String email);
	Student findByEmailPass(String email, String pass);
	List<Student> findAll();
	void insertReport(Student obj, Report rt);
	
}
