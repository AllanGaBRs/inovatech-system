package model.entities;

import java.io.Serializable;
import java.util.Objects;

public class Student implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String ra;
	private String name;
	private String email;
	private String pass;
	private String course;
	private Double hours;
	private Boolean adm;
	
	public Student() {
	}
	
	public Student(Integer id, String ra, String name, String email, String pass, String course, Double hours, Boolean adm) {
		this.id = id;
		this.ra = ra;
		this.name = name;
		this.email = email;
		this.pass = pass;
		this.course = course;
		this.hours = hours;
		this.adm = adm;
	}
	

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public Boolean getAdm() {
		return adm;
	}

	public void setAdm(Boolean adm) {
		this.adm = adm;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRa() {
		return ra;
	}

	public void setRa(String ra) {
		this.ra = ra;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}
	public Double getHours() {
		return hours;
	}
	
	public void setHours(Double hours) {
		this.hours = hours;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", ra=" + ra + ", name=" + name + ", email=" + email + ", course=" + course + "]";
	}
}
