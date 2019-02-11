package com.cerner.searchlog.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilterReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.cerner.fileSearch.FileSearch;
import com.cerner.fileSearch.dto.FileSearchRequestDTO;
import com.cerner.fileSearch.dto.FileSearchResultDTO;
import com.cerner.folderSearch.FolderSearch;
import com.cerner.folderSearch.dto.FolderSearchRequestDto;
import com.cerner.folderSearch.dto.FolderSearchResultDto;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class HomeFormController {

	private static final String ENV_LIST_JSON = ".\\envList.json";
	@FXML
	private TableView<FolderSearchRequestDto> tableView;
	@FXML
	private TableColumn<FolderSearchRequestDto, String> envNameCol;
	@FXML
	private TableColumn<FolderSearchRequestDto, String> envDirectoryPathCol;
	@FXML
	private TextField txtEnvName, txtEnvDirectoryPath, txtKeyword, fromTime, toTime;
	@FXML
	private Button btnAdd, btnDelete, btnSearch;
	@FXML
	private DatePicker fromDate, toDate;

	private Stage stage;
	private Parent parent;
	private Scene scene;

	private ObservableList<FolderSearchRequestDto> data = FXCollections.observableArrayList();

	public HomeFormController() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/cerner/searchlog/view/HomeNew.fxml"));
		fxmlLoader.setController(this);
		try {
			parent = (Parent) fxmlLoader.load();
			scene = new Scene(parent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void showEnvironmentTable() {
		envNameCol.setCellValueFactory(new PropertyValueFactory<FolderSearchRequestDto, String>("envName"));
		envDirectoryPathCol.setCellValueFactory(new PropertyValueFactory<FolderSearchRequestDto, String>("envDirectoryPath"));
		tableView.setEditable(false);
		FolderSearchRequestDto environment = new FolderSearchRequestDto();
		environment.setEnvName(txtEnvName.getText());
		environment.setEnvDirectoryPath(txtEnvDirectoryPath.getText());
		data.add(environment);
		tableView.setItems(data);
		
		
		File file = new File(ENV_LIST_JSON);
		
		if(file.exists()){
			FileReader fr;
			try {
				fr = new FileReader(file);
				JSONParser  parser = new JSONParser ();
				JSONObject obj=(JSONObject) parser.parse(fr);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
	}
	
	private void saveEnvironmentDataAsJson() {
		JSONObject obj = new JSONObject();
		if(!tableView.getItems().isEmpty()){
			for(FolderSearchRequestDto env: tableView.getItems()){
				obj.put(env.getEnvName(), env.getEnvDirectoryPath());
			}
			File file = new File(ENV_LIST_JSON);
			try {
				if(!file.exists()){
					file.createNewFile();	
				}
				
				FileWriter fileWriter = new FileWriter(file);
				
	            fileWriter.write(obj.toJSONString());
	            fileWriter.flush();

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		}
	}

	@FXML
	private void addButtonAction() {
		showEnvironmentTable();
		txtEnvName.clear();
		txtEnvDirectoryPath.clear();
	}
	
	@FXML
	private void deleteButtonAction(){
		FolderSearchRequestDto selectedItem = tableView.getSelectionModel().getSelectedItem();
		tableView.getItems().remove(selectedItem);
	}
	
	@FXML
	private void searchButtonAction(){
		if(!tableView.getItems().isEmpty()){
			for(FolderSearchRequestDto request: tableView.getItems()){
				FolderSearch folderSearch=new FolderSearch();
				List<FolderSearchResultDto> list=folderSearch.folderSearch(request);
				if(list!=null && list.size()>0){
					
					for(FolderSearchResultDto searchRersultDto:list){
						FileSearch fileSearch=new FileSearch();
						System.out.println( txtKeyword.getText());
						FileSearchRequestDTO fileSearchRequestDTO=new FileSearchRequestDTO(searchRersultDto.getFileName(), txtKeyword.getText(), asDate(fromDate.getValue()), asDate( toDate.getValue()));
						FileSearchResultDTO result=fileSearch.fileSearch(fileSearchRequestDTO);
						System.out.println(result.getFileName());
						System.out.println(result.getLineNoKeyWord());
					}
				}
			}
		}
		
	}
	
	public static Date asDate(LocalDate localDate) {
		if(localDate!=null)
	    {return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());}else{return null;}
	  }

	public void openHomeForm(Stage stage) {
		this.stage = stage;
		stage.setTitle("InstaLog");
		stage.initStyle(StageStyle.DECORATED);
		stage.setScene(scene);
		stage.setResizable(true);
		stage.show();
		Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2); 
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 4); 
        stage.getIcons().add(new Image("/com/cerner/searchlog/image/search_image3.png"));
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent t) {
				stage.close();
				saveEnvironmentDataAsJson();
				System.exit(0);

			}
		});

	}

}
