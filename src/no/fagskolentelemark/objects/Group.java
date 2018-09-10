package no.fagskolentelemark.objects;

import java.util.ArrayList;
import java.util.List;

public class Group {

	private String name;
	private List<Student> students = new ArrayList<Student>();

	public Group (String name) {
		this.name = name;
	}

	public void addStudent(Student student) {
		students.add(student);
	}

	public List<Student> getStudents(){
		return students;
	}

	public String getName() {
		return name;
	}
}