package delmas.walton.studyapp;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainGUI extends Application {
	private Label mainTitle;
	private Label welcomeString;
	private Button newBankbtn;
	private Button loadBankbtn;
	
	@Override
	public void start(Stage primaryStage) {
		// Create the fixed menu bar
		BorderPane root = new BorderPane();
		MenuBar mainMenu = new MenuBar();
		final Menu menu1 = new Menu("File");
		final Menu menu2 = new Menu("Options");
		final Menu menu3 = new Menu("Edit");
		final MenuItem item1 = new MenuItem("save");
		final MenuItem item2 = new MenuItem("delete");
		menu1.getItems().addAll(item1, item2);
		mainMenu.getMenus().addAll(menu1, menu2, menu3);
		
		// Create the welcome pane
		VBox welcomePane = new VBox();
		this.mainTitle = new Label("StudyBuddy");
		this.welcomeString = new Label("Welcome to your MCQ application.\nLet's get started!");
		this.newBankbtn = new Button("Create a new bank");
		this.loadBankbtn = new Button("Load an existing bank");
		
		// Create the manage bank pane
		BorderPane manageBankPane = new BorderPane();
		VBox leftMenu = new VBox();
		Button addQuestionbtn = new Button("Add a question");
		Button removeQuestionbtn = new Button("Remove a question");
		leftMenu.getChildren().addAll(addQuestionbtn, removeQuestionbtn);
		leftMenu.setSpacing(10);
		leftMenu.setAlignment(Pos.CENTER);
		manageBankPane.setLeft(leftMenu);
		
		welcomePane.getChildren().addAll(this.mainTitle, this.welcomeString, this.newBankbtn, this.loadBankbtn);
		welcomePane.setAlignment(Pos.CENTER);
		welcomePane.setSpacing(25);
		
		//Set up initial display
		root.setTop(mainMenu);
		root.setCenter(welcomePane);
		
		// Listen for button clicks
		newBankbtn.setOnAction((ActionEvent e)-> {
			root.setCenter(manageBankPane);
		});
		// Display all
		primaryStage.setScene(new Scene(root, 300, 300));
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
}
