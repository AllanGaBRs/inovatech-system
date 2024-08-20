package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import banco.DB;
import banco.DbException;
import model.dao.StudentDao;
import model.entities.Student;

public class StudentDaoJDBC implements StudentDao {

	private Connection conn;
	private PreparedStatement st;
	private ResultSet rs;

	public StudentDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Student obj) {
		try {
			st = conn.prepareStatement("INSERT INTO student " + "(Ra, Namee, Email, pass, course, hours, adm) "
					+ "VALUES " + "(?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			st.setString(1, obj.getRa());
			st.setString(2, obj.getName());
			st.setString(3, obj.getEmail());
			st.setString(4, obj.getPass());
			st.setString(5, obj.getCourse());
			st.setDouble(6, 0.0);
			st.setBoolean(7, false);

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			} else {
				throw new DbException("Unexpected error! No rows affected!");
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public void updateHours(Student obj, Double hours) {
		try {
			st = conn.prepareStatement("UPDATE student SET hours = hours + ? WHERE Id = ?",
					Statement.RETURN_GENERATED_KEYS);
			st.setDouble(1, hours);
			st.setInt(2, obj.getId());

			st.executeUpdate();

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Student findById(Integer id) {
		try {
			st = conn.prepareStatement("SELECT * FROM STUDENT WHERE ID = ?");
			st.setInt(1, id);

			rs = st.executeQuery();

			if (rs.next()) {
				return instantiateStudent(rs);
			}
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

	@Override
	public List<Student> findAll() {
		try {
			st = conn.prepareStatement("SELECT * FROM STUDENT");

			rs = st.executeQuery();

			Map<Integer, Student> map = new HashMap<>();
			List<Student> list = new ArrayList<>();

			while (rs.next()) {
				Student student = map.get(rs.getInt("id"));

				if (student == null) {
					student = instantiateStudent(rs);
					map.put(student.getId(), student);
				}
				if (student.getId() == 1) {
					continue;
				}
				list.add(student);
			}
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

	@Override
	public Student findByRa(String ra) {
		try {
			st = conn.prepareStatement("SELECT * FROM STUDENT WHERE RA = ?");
			st.setString(1, ra);

			rs = st.executeQuery();

			if (rs.next()) {
				return instantiateStudent(rs);
			}
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

	@Override
	public Student findByEmail(String email) {
		try {
			st = conn.prepareStatement("SELECT * FROM STUDENT WHERE EMAIL = ?");
			st.setString(1, email);

			rs = st.executeQuery();

			if (rs.next()) {
				return instantiateStudent(rs);
			}
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

	@Override
	public Student findByEmailPass(String email, String pass) {
		try {
			st = conn.prepareStatement("SELECT * FROM STUDENT WHERE EMAIL = ? and PASS = ?");
			st.setString(1, email);
			st.setString(2, pass);

			rs = st.executeQuery();

			if (rs.next()) {
				return instantiateStudent(rs);
			}
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}
	
	@Override
	public void updateDate(Student obj, java.sql.Date date) {
		try {
			st = conn.prepareStatement("UPDATE student SET dayOf = ? WHERE Id = ?",
					Statement.RETURN_GENERATED_KEYS);
			st.setDate(1, date);
			st.setInt(2, obj.getId());

			st.executeUpdate();

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
		
	}
	
	@Override
	public Student selectDate(Integer id) {
		try {
			st = conn.prepareStatement("SELECT * FROM STUDENT WHERE ID = ?");
			st.setInt(1, id);

			rs = st.executeQuery();

			if (rs.next()) {
				return instantiateStudent(rs);
			}
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
		
	}
	@Override
	public void update(Student obj) {
		// TODO Auto-generated method stub
		
	}

	private Student instantiateStudent(ResultSet rs) throws SQLException {
		Student obj = new Student();
		obj.setId(rs.getInt("Id"));
		obj.setRa(rs.getString("Ra"));
		obj.setName(rs.getString("Namee"));
		obj.setEmail(rs.getString("Email"));
		obj.setCourse(rs.getString("Course"));
		obj.setHours(rs.getDouble("Hours"));
		obj.setAdm(rs.getBoolean("Adm"));
		obj.setDate(rs.getDate("DayOf"));
		return obj;
	}



}
