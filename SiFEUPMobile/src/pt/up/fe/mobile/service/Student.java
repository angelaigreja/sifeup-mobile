package pt.up.fe.mobile.service;

public class Student {
	private String code;
	private String name;
	private String courseAcronym;
	private String courseName;
	private String registrationYear;
	private String state;
	private String academicYear;
	private String email;

	public Student() {
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCourseAcronym() {
		return courseAcronym;
	}

	public void setCourseAcronym(String courseAcronym) {
		this.courseAcronym = courseAcronym;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getRegistrationYear() {
		return registrationYear;
	}

	public void setRegistrationYear(String registrationYear) {
		this.registrationYear = registrationYear;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	private void clearAll() {
		name = courseAcronym = 
			courseName = registrationYear = 
				state = academicYear = email = "";
	}
}