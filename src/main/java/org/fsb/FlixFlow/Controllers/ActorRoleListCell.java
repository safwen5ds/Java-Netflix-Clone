package org.fsb.FlixFlow.Controllers;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class ActorRoleListCell extends ListCell<ActorRoleDisplay> {
	private HBox content;
    private Button favoriteButton;
    private ImageView imageView;
    private Label nameLabel;
    private Label roleLabel;
    public ActorRoleListCell() {
        super();
        imageView = new ImageView();
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(50); // Set the preferred width of the image
        // imageView.setFitHeight(50); // Alternatively, set the preferred height and let the width adjust accordingly
        
        nameLabel = new Label();
        roleLabel = new Label();
        favoriteButton = new Button("Add to Favorite");

        // Add an action to the favoriteButton
        favoriteButton.setOnAction(event -> {
            ActorRoleDisplay currentItem = getItem();
            // Perform action to add actor to favorites.
            // For example, you can call a method from your controller class
        });

        content = new HBox(imageView, nameLabel, roleLabel, favoriteButton);
        content.setSpacing(10);
    }



    @Override
    protected void updateItem(ActorRoleDisplay item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null && !empty) {
            imageView.setImage(new Image(item.getUrlImage()));
            nameLabel.setText(item.getActorName());
            roleLabel.setText(item.getRoleType());

            // Show the favorite button
            favoriteButton.setVisible(true);

            setGraphic(content);
        } else {
            setGraphic(null);
        }
    }

}
