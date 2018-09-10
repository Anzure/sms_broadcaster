package no.fagskolentelemark;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import no.fagskolentelemark.objects.Group;
import no.fagskolentelemark.utils.DatabaseReader;
import no.fagskolentelemark.utils.SendSMS;

public class BroadcastMain extends Application {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void start(Stage primaryWindow) throws Exception {
		primaryWindow.getIcons().add(new Image("icon.png"));

		// Load scene
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/BroadcastScene.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);

		// Load classes
		ChoiceBox<String> groupChoice = (ChoiceBox)loader.getNamespace().get("chooseGroup");
		ObservableList<String> groupList = FXCollections.observableArrayList();
		for (Group group : DatabaseReader.groups) {
			groupList.add(group.getName());
		}
		groupChoice.setItems(groupList);

		// Load button
		TextArea msgField = (TextArea)loader.getNamespace().get("msgField");
		Button sendButton = (Button)loader.getNamespace().get("sendButton");
		sendButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					int i = SendSMS.sendMessage(groupChoice.getValue(), msgField.getText());
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Fagskolen Telemark - Popup");
					alert.setHeaderText("SMS ble utsendt!");
					alert.setContentText(i + " meldinger sendt.");
					alert.showAndWait();
					
				} catch (Exception e) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Fagskolen Telemark - Popup");
					alert.setHeaderText("Utsending feilet");
					alert.setContentText("Vennligst velg en klasse.");
					alert.showAndWait();
				}
			}
		});

		// Start scene
		primaryWindow.setScene(scene);
		primaryWindow.setTitle("Fagskolen Telemark - SMS utsender");
		primaryWindow.show();
	}

	public static void main(String[] args) {
		try {
			DatabaseReader.loadDatabase();
		} catch (Exception e) {
			e.printStackTrace();
		}
		launch(args);
	}
}