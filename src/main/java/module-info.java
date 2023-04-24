module org.fsb.FlixFlow {
	requires javafx.controls;
	requires javafx.fxml;
	requires de.jensd.fx.glyphs.fontawesome;
	requires transitive javafx.graphics;
	requires transitive java.sql;
	opens org.fsb.FlixFlow to javafx.fxml, de.jensd.fx.glyphs.fontawesome;

	exports org.fsb.FlixFlow;
	opens org.fsb.FlixFlow.Controllers;
	opens org.fsb.FlixFlow.Models;
	opens org.fsb.FlixFlow.Views;

	exports org.fsb.FlixFlow.Controllers;
	exports org.fsb.FlixFlow.Models;
	exports org.fsb.FlixFlow.Views;

	requires javafx.media;
	requires javafx.web;
	requires java.desktop;
    requires uk.co.caprica.vlcj;

}
