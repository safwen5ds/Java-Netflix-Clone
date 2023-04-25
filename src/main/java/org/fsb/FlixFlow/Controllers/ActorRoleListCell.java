package org.fsb.FlixFlow.Controllers;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import org.fsb.FlixFlow.Utilities.DatabaseUtil;

import java.sql.SQLException;

public class ActorRoleListCell extends ListCell<ActorRoleDisplay> {
	private final HBox content;
	private final Button favoriteButton;
	private final ImageView imageView;
	private final Label nameLabel;
	private final Label roleLabel;

	public ActorRoleListCell() {
		super();
		imageView = new ImageView();
		imageView.setPreserveRatio(true);
		imageView.setFitWidth(50);

		nameLabel = new Label();
		roleLabel = new Label();

		Font bebasNeueFont = Font.loadFont(getClass().getResourceAsStream("/FXML/fonts/BebasNeue-Regular.ttf"), 20);
		nameLabel.setFont(bebasNeueFont);
		roleLabel.setFont(bebasNeueFont);
		nameLabel.setStyle("-fx-text-fill: white;");
		roleLabel.setStyle("-fx-text-fill: white;");

		favoriteButton = new Button("Add to Favorite");
		favoriteButton.setFont(bebasNeueFont);

		favoriteButton.setOnAction(event -> {
			ActorRoleDisplay currentItem = getItem();
			int userId = DatabaseUtil.readUserFromFile().getId_utilisateur();
			int actorId = currentItem.getActorid();

			try {
				DatabaseUtil.addPreference(userId, actorId);
				showAlert(AlertType.INFORMATION, "Success", "Actor added to favorites.");
			} catch (SQLException e) {
				showAlert(AlertType.ERROR, "Error", "The actor is already in your favorites.");
			}
		});

		content = new HBox(imageView, nameLabel, roleLabel, favoriteButton);
		content.setSpacing(10);
	}

	private void showAlert(AlertType alertType, String title, String content) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(content);
		alert.showAndWait();
	}

	@Override
	protected void updateItem(ActorRoleDisplay item, boolean empty) {
		super.updateItem(item, empty);
		if (item != null && !empty) {
			imageView.setImage(new Image(item.getUrlImage()));
			nameLabel.setText(item.getActorName());
			roleLabel.setText(item.getRoleType());

			favoriteButton.setVisible(true);

			setGraphic(content);
		} else {
			setGraphic(null);
		}
	}

}
