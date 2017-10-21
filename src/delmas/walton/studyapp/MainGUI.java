package delmas.walton.studyapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Optional;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainGUI extends Application {
	// Static variable used to display to the user
	static final String ERROR_BANK_ALREADY_EXISTS = "This bank already exists. Try to load it instead of creating it.";
	static final String ERROR_FILE_NOT_SAVED = "Your file could not be updated; your work will not be saved.";
	static final String ERROR_FILE_CORRUPTED = "This file could not be open";
	
	// Instance variables
	private QuestionBank currentBank;
	private Label mainTitle;
	private Label welcomeString;
	private Button newBankbtn;
	private Button loadBankbtn;
	private Label bankTitle;
	private Label error_bankAlredyExists;
	
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
		this.error_bankAlredyExists = new Label("");
		VBox welcomePane = new VBox();
		this.mainTitle = new Label("StudyBuddy");
		this.welcomeString = new Label("Welcome to your MCQ application.\nLet's get started!");
		this.newBankbtn = new Button("Create a new bank");
		this.loadBankbtn = new Button("Load an existing bank");
		welcomePane.getChildren().addAll(this.mainTitle, this.welcomeString, this.newBankbtn, this.loadBankbtn, this.error_bankAlredyExists);
		welcomePane.setAlignment(Pos.CENTER);
		welcomePane.setSpacing(25);
		
		
		// Create the manage bank pane
		this.bankTitle = new Label();
		BorderPane manageBankPane = new BorderPane();
		VBox leftMenu = new VBox();
		Button addQuestionbtn = new Button("Add a question");
		Button removeQuestionbtn = new Button("Remove a question");
		leftMenu.getChildren().addAll(addQuestionbtn, removeQuestionbtn);
		leftMenu.setSpacing(10);
		leftMenu.setAlignment(Pos.CENTER);
		manageBankPane.setLeft(leftMenu);
		manageBankPane.setTop(this.bankTitle);
		BorderPane.setAlignment(this.bankTitle, Pos.CENTER);
		
		// Create the main menu pane
		VBox mainMenuPane = new VBox();
		Button startQuizBtn = new Button("Start Quiz");
		Button startReviewBtn = new Button("Start Review");
		Button manageBankBtn = new Button ("Modify existing bank of questions");
		mainMenuPane.getChildren().addAll(this.bankTitle, startQuizBtn, startReviewBtn, manageBankBtn);
		mainMenuPane.setSpacing(15);
		mainMenuPane.setAlignment(Pos.CENTER);
		
		// Create the add question pane
		GridPane addQuestionPane = new GridPane();
		Label questionPrompt = new Label("question prompt:");
		TextField questionPromptField = new TextField();
		ArrayList<Label> answerLabels = new ArrayList<>();
		ArrayList<TextField> answerFields = new ArrayList<>();
		for(int i = 0; i < 7; i++) {
			answerLabels.add(new Label());
			answerFields.add(new TextField());
			answerLabels.get(i).setText("answer " + (i+1) + ":");
		}
		CheckBox isShuffleable = new CheckBox("The question can be shuffled");
		addQuestionPane.add(questionPrompt, 0,  0);
		addQuestionPane.add(questionPromptField, 1, 0);
		for(int i = 0; i < 7; i++ ) {
			addQuestionPane.add(answerLabels.get(i), 0, i+1);
			addQuestionPane.add(answerFields.get(i), 1, i+1);
		}
		addQuestionPane.add(isShuffleable, 0, 9);

		
		
		//Set up initial display
		root.setTop(mainMenu);
		root.setCenter(welcomePane);
		
		// Listen for button clicks
		// Create file opener and other utilities
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		
		// Listen for button clicks
		
		
		// When the "Load Bank" button is clicked
		loadBankbtn.setOnAction((ActionEvent e)->{
			// Display a file chooser and loads the file
			// If the file was properly loaded
			if( this.loadFile(fileChooser.showOpenDialog(primaryStage)) ) {
				// Change the Pane
				this.bankTitle.setText(this.currentBank.getName());
				root.setCenter(mainMenuPane);
			} else {
				this.error_bankAlredyExists.setText(MainGUI.ERROR_FILE_CORRUPTED);
			}
			// Set the pane
			
		});
		
		// When the "Create new Bank button is clicked
		newBankbtn.setOnAction((ActionEvent e)-> {
			//Ask for the name of the file
			TextInputDialog dialog = new TextInputDialog("");
			dialog.setTitle("Name your bank of question");
			dialog.setHeaderText("Name your new bank of question");
			dialog.setContentText("Name: ");
			Optional<String> result = dialog.showAndWait();
			if (result.isPresent()){
				// Try to load the bank
				String fileName = result.get() + ".dat";
				File file = new File(fileName);
						
				// Check if the bank already exists
				if(file.exists()) {
					// Display an error message on the welcome pane
					this.error_bankAlredyExists.setText(MainGUI.ERROR_BANK_ALREADY_EXISTS);
				} else {
					// Create the bank
					this.currentBank = new QuestionBank(result.get());
				    this.bankTitle.setText(result.get());
					// display the new menu
					root.setCenter(manageBankPane);	
					// Create the file
					this.updateFile();
				}
				
					// else create the new QuestionBank file
					// set this.currentBank
			}

		});
		
		// When the manage bank button is clicked
		manageBankBtn.setOnAction((ActionEvent e)->{
			root.setCenter(manageBankPane);
		});
		
		// When the add question button is clicked
		addQuestionbtn.setOnAction((ActionEvent e)->{
			manageBankPane.setCenter(addQuestionPane);
		});

		
		// Display all
		primaryStage.setScene(new Scene(root, 1000, 600));
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
	
	/**
	 * Updates the file storing the list of banks
	 * Display an error if the file could not be updated
	 */
	private void updateFile() {
		try {
			// Save the file to disk
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(this.currentBank.getName() + ".dat"));
			out.writeObject(this.currentBank);
			out.close();
		} catch (IOException e) {
			// Pop up a warning message
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("Could not savd data");
			alert.setContentText(MainGUI.ERROR_FILE_NOT_SAVED);
			alert.showAndWait();
		}
	}
	
	/* Read the bank of question from a file into this.currentBank
	 * @param file file to read the bank from
	 * @return true if the bank was properly loaded, false otherwise
	 */
	private boolean loadFile(File file) {
		boolean flag = false;
		
		if(file != null && file.exists()) {
			try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))){
				// Load bank of question
				this.currentBank = (QuestionBank) in.readObject();
				// Update return value
				flag = true;
			} catch(FileNotFoundException e) {} 
			catch(IOException e) {} 
			catch (ClassNotFoundException e) {} 
			catch (ClassCastException e) {}
		}
		
		return flag;
	}
}
