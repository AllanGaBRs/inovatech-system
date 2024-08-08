package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import banco.DbException;
import model.dao.StudentDao;
import model.entities.Student;

public class StudentDaoJDBC implements StudentDao{

	private Connection conn;
	private PreparedStatement st;
	private ResultSet rs;
	
	public StudentDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Student obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Student obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Student findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Student> findAll() {
		try {
			st = conn.prepareStatement("SELECT * FROM STUDENT");
			
			rs = st.executeQuery();
			
			Map<Integer, Student> map = new HashMap<>();
			List<Student> list = new ArrayList<>();
			
			while(rs.next()) {
				Student student = map.get(rs.getInt("id"));
				
				if(student == null) {
					student = instantiateStudent(rs);
					map.put(student.getId(), student);
				}
				
				list.add(student);
			}
			return list;
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
	}
	
	private Student instantiateStudent(ResultSet rs) throws SQLException {
		Student obj = new Student();
		obj.setId(rs.getInt("Id"));
		obj.setRa(rs.getString("Ra"));
		obj.setName(rs.getString("Namee"));
		obj.setEmail(rs.getString("Email"));
		obj.setCourse(rs.getString("Course"));
		obj.setHours(rs.getDouble("Hours"));
		return obj;
	}

}
