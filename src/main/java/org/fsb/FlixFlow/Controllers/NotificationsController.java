package org.fsb.FlixFlow.Controllers;

import org.fsb.FlixFlow.Models.Notification;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class NotificationsController {

	private final ObservableList<String> notifications;

	@FXML
	private ListView<String> notificationsList;

	public NotificationsController() {
		notifications = FXCollections.observableArrayList();
	}

	public void addNotification(Notification notification) {
		String notificationText = String.format("New episode released: %s, S%dE%d", notification.getSerieNom(),
				notification.getNum_saison(), notification.getNumEpisode());
		notifications.add(notificationText);
	}

	@FXML
	private void initialize() {
		notificationsList.setItems(notifications);
	}
}
