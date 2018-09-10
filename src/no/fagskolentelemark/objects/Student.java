package no.fagskolentelemark.objects;

public class Student {

	private String firstName;
	private String lastName;
	private int phone;

	public Student(String firstName, String lastName, int phone) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public int getPhoneNumber() {
		return phone;
	}
}