package no.fagskolentelemark.utils;

import java.io.DataOutputStream;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

import no.fagskolentelemark.GitIgnored;
import no.fagskolentelemark.objects.Group;
import no.fagskolentelemark.objects.Student;

public class SendSMS {

	public static int sendMessage(String groupName, String txt) throws Exception {
		int i = 0;

		// Get class
		Group group = null;
		for (Group g : DatabaseReader.groups) {
			if (g.getName().equalsIgnoreCase(groupName)) {
				group = g;
				break;
			}
		}

		// Send messages
		for (Student student : group.getStudents()) {
			int phone = student.getPhoneNumber();

			URL url = new URL("https://gatewayapi.com/rest/mtsms");
			HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
			con.setDoOutput(true);

			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(
					"token=" + GitIgnored.sms_token
							+ "&sender=" + URLEncoder.encode("Fagskolen", "UTF-8")
							+ "&message=" + URLEncoder.encode(txt, "UTF-8")
							+ "&class=premium&priority=VERY_URGENT&recipients.0.msisdn=0047" + phone
					);
			wr.close();

			int responseCode = con.getResponseCode();
			System.out.println("Respons: " + responseCode + " for +47 " + phone);
			if (responseCode == 200) i++;
		}

		return i;
	}
}