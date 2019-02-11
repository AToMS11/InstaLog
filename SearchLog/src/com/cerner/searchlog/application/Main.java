package com.cerner.searchlog.application;
	
import com.cerner.searchlog.controller.HomeFormController;

import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			HomeFormController homeFormController = new HomeFormController();
			homeFormController.openHomeForm(primaryStage);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
