package model.entities;

import java.sql.Date;
import java.util.Objects;

public class Report {
	
	private int id;
	private String report;
	private int idStudent;
	private java.sql.Date dayReport;
	
	public Report() {
	}

	public Report(int id, String report, int idStudent, Date dayReport) {
		this.id = id;
		this.report = report;
		this.idStudent = idStudent;
		this.dayReport = dayReport;
	}

	public Report(String report, int idStudent, Date dayReport) {
		this.report = report;
		this.idStudent = idStudent;
		this.dayReport = dayReport;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getReport() {
		return report;
	}

	public void setReport(String report) {
		this.report = report;
	}

	public java.sql.Date getDayReport() {
		return dayReport;
	}

	public void setDayReport(java.sql.Date dayReport) {
		this.dayReport = dayReport;
	}
	
	public int getIdStudent() {
		return idStudent;
	}

	public void setIdStudent(int idStudent) {
		this.idStudent = idStudent;
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
		Report other = (Report) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return "Report [id=" + id + ", report=" + report + ", dayReport=" + dayReport + "]";
	}
}
