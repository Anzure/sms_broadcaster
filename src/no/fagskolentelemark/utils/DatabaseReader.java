package no.fagskolentelemark.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import no.fagskolentelemark.GitIgnored;
import no.fagskolentelemark.objects.Group;
import no.fagskolentelemark.objects.Student;

public class DatabaseReader {

	public static List<Group> groups = new ArrayList<Group>();

	public static void loadDatabase() throws Exception {
		// Input
		InputStream inputStream = new URL(GitIgnored.excel).openStream();
		BufferedReader inputReader = new BufferedReader(new InputStreamReader(inputStream, "UTF8"));

		// Reader
		String line;
		while ((line = inputReader.readLine()) != null) {
			String[] row = line.replace("\"", "").split(";");
			String firstName = row[0];
			String lastName = row[1];

			try {
				// Load student

				int phone = Integer.parseInt(row[2].replace(" ", ""));
				String groupName = row[3].toUpperCase();

				Student student = new Student(firstName, lastName, phone);

				// Find class
				Group group = new Group(groupName);
				for (Group g : groups) {
					if (g.getName().equalsIgnoreCase(groupName)) {
						group = g;
						break;
					}
				}

				// Save student
				if (groups.contains(group)) groups.remove(group);
				group.addStudent(student);
				groups.add(group);

			} catch (Exception ex) {
				System.out.println(firstName + " " + lastName + " mangler telefon nummer!");
			}
		}

	}
}