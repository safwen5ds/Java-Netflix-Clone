package org.fsb.FlixFlow.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import org.fsb.FlixFlow.Models.Notification;

public class NotificationsController {

	@FXML
	private ListView<String> notificationsList;

	private final ObservableList<String> notifications;

	public NotificationsController() {
		notifications = FXCollections.observableArrayList();
	}

	@FXML
	private void initialize() {
		notificationsList.setItems(notifications);
	}

	public void addNotification(Notification notification) {
		String notificationText = String.format("New episode released: %s, S%dE%d", notification.getSerieNom(),
				notification.getNum_saison(), notification.getNumEpisode());
		notifications.add(notificationText);
	}
}
