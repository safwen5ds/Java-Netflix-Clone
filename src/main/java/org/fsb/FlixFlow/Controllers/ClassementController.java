package org.fsb.FlixFlow.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.fsb.FlixFlow.Models.SeriesRanking;
import org.fsb.FlixFlow.Utilities.DatabaseUtil;

import java.time.LocalDate;

public class ClassementController {
	@FXML
	private DatePicker startDatePicker;

	@FXML
	private DatePicker endDatePicker;
	@FXML
	private Button searchButton;

	@FXML
	private TableView<SeriesRanking> seriesRankingTable;

	@FXML
	private TableColumn<SeriesRanking, Integer> rankColumn;

	@FXML
	private TableColumn<SeriesRanking, String> nomColumn;

	@FXML
	private TableColumn<SeriesRanking, Integer> viewsColumn;

	public void initialize() {
		rankColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));
		nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
		viewsColumn.setCellValueFactory(new PropertyValueFactory<>("views"));
		 searchButton.setOnAction(event -> SeriesRanking());
	}

	@FXML
	public void SeriesRanking() {
		LocalDate startDate = startDatePicker.getValue();
		LocalDate endDate = endDatePicker.getValue();

		if (startDate == null || endDate == null) {

			return;
		}

		ObservableList<SeriesRanking> seriesRankings = FXCollections
				.observableArrayList(DatabaseUtil.getSeriesRanking(startDate, endDate));
		seriesRankingTable.setItems(seriesRankings);
	}
}
