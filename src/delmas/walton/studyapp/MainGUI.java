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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
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
	private BorderPane root;
	private BorderPane manageBankPane;
	private VBox mainMenuPane;
	private Stage primaryStage;
	private FileChooser fileChooser;
	private VBox quizPane;
	private int quizLength;
	private int quizNumberRight;
	private int quizQAnswered;
	private ChoiceQuestion question;
	private ArrayList<RadioButton> answerSelection;
	private Label AskQuestionPrompt;
	final private ToggleGroup choiceGroup = new ToggleGroup();
	private VBox quizFeedbackPane;
	private Label quizFeedbackLabel;

	@Override
	public void start(Stage stage) {
		this.primaryStage = stage;
		
		// Create a button to go back to main menu
		Button backToMainBtn = new Button("Back to main menu");
		
		// Create the fixed menu bar
		this.root = new BorderPane();
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
		welcomePane.getChildren().addAll(this.mainTitle, this.welcomeString, this.newBankbtn, this.loadBankbtn,
				this.error_bankAlredyExists);
		welcomePane.setAlignment(Pos.CENTER);
		welcomePane.setSpacing(25);

		// Create the manage bank pane
		this.bankTitle = new Label();
		this.manageBankPane = new BorderPane();
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
		this.mainMenuPane = new VBox();
		Button startQuizBtn = new Button("Start Quiz");
		Button startReviewBtn = new Button("Start Review");
		Button manageBankBtn = new Button("Modify existing bank of questions");
		mainMenuPane.getChildren().addAll(this.bankTitle, startQuizBtn, startReviewBtn, manageBankBtn);
		mainMenuPane.setSpacing(15);
		mainMenuPane.setAlignment(Pos.CENTER);

		// Create the add question pane
		final int MAX_ANSWERS = 7;
		GridPane addQuestionPane = new GridPane();
		Label questionPrompt = new Label("question prompt:");
		TextField questionPromptField = new TextField();
		ArrayList<Label> answerLabels = new ArrayList<>();
		ArrayList<TextField> answerFields = new ArrayList<>();
		ArrayList<RadioButton> rightAnswerBox = new ArrayList<>();
		final ToggleGroup rightAnswerBoxGroup = new ToggleGroup();
		for (int i = 0; i < MAX_ANSWERS; i++) {
			answerLabels.add(new Label("answer " + (i + 1) + ":"));
			answerFields.add(new TextField());
			rightAnswerBox.add(new RadioButton());
			rightAnswerBox.get(i).setText("correct answer ");
			rightAnswerBox.get(i).setToggleGroup(rightAnswerBoxGroup);
		}
		CheckBox isShuffleable = new CheckBox("Can be shuffled");
		Button validateNewQuestionBtn = new Button("Add question");
		addQuestionPane.add(questionPrompt, 0, 0);
		addQuestionPane.add(questionPromptField, 1, 0, 3, 1);
		for (int i = 0; i < MAX_ANSWERS; i++) {
			addQuestionPane.add(answerLabels.get(i), 0, i + 1);
			addQuestionPane.add(answerFields.get(i), 1, i + 1, 3, 1);
			addQuestionPane.add(rightAnswerBox.get(i), 4, i + 1);
		}
		addQuestionPane.add(isShuffleable, 0, 9);
		addQuestionPane.add(validateNewQuestionBtn, 4, 10);

		// Create the quiz pane
		this.quizQAnswered = 0;
		this.quizLength = 0;
		this.quizNumberRight = 0;
		this.quizPane = new VBox();
		Label title = new Label("Quiz Time!");
		Label questionNumber = new Label("Question #" + this.quizQAnswered + "/" + this.quizLength);
		this.AskQuestionPrompt = new Label("This is the question");
		this.question = null;
		Button quizNextQuestionBtn = new Button("Next");
		this.answerSelection = new ArrayList<RadioButton>();
		quizPane.getChildren().addAll(title, questionNumber, AskQuestionPrompt);

		for (int i = 0; i < 7; i++) {
			answerSelection.add(new RadioButton());
			answerSelection.get(i).setToggleGroup(choiceGroup);
			answerSelection.get(i).setVisible(false);
			quizPane.getChildren().add(answerSelection.get(i));
		}
		quizPane.getChildren().addAll(quizNextQuestionBtn);
		
		// Create the quiz feedback pane
		this.quizFeedbackPane = new VBox();
		quizFeedbackLabel = new Label();
		this.quizFeedbackPane.getChildren().addAll(quizFeedbackLabel, backToMainBtn);

		
		// Create file opener and other utilities
		this.fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");

		// Set up initial display
		root.setTop(mainMenu);
		root.setCenter(welcomePane);

		// Listen for button clicks
		// When the back to main menu button is pressed
		backToMainBtn.setOnAction((ActionEvent e)->{
			this.root.setCenter(this.mainMenuPane);
		});
		
		// When the quiz next question button is pressed
		quizNextQuestionBtn.setOnAction(this::answerQuizQuestion);
		
		// When the startQuiz button is pressed
		startQuizBtn.setOnAction(this::startQuiz);

		// When the validate new question button is clicked
		// Saves the question and clear the fields
		validateNewQuestionBtn.setOnAction((ActionEvent e) -> {
			this.addQuestionToBank(questionPromptField, answerFields, rightAnswerBox, isShuffleable);
		});

		// When the "Load Bank" button is clicked
		loadBankbtn.setOnAction(this::loadBank);

		// When the "Create new Bank button is clicked
		newBankbtn.setOnAction(this::newBank);

		// When the manage bank button is clicked
		manageBankBtn.setOnAction((ActionEvent e) -> {
			root.setCenter(manageBankPane);
		});

		// When the add question button is clicked
		addQuestionbtn.setOnAction((ActionEvent e) -> {
			manageBankPane.setCenter(addQuestionPane);
		});

		// Display all
		primaryStage.setScene(new Scene(root, 1000, 600));
		primaryStage.show();

	}

	private void answerQuizQuestion(ActionEvent e) {
		
		// if the quiz is not over yet
		if(this.quizQAnswered < (this.quizLength - 1)) {
			// Check the answer
			if (this.question.checkAnswer(choiceGroup.getSelectedToggle().getUserData().toString())) {
				this.quizNumberRight++;
			}
			for (int i = 0; i < this.question.getChoices().size(); i++) {
				answerSelection.get(i).setVisible(false);
			}
			this.quizQAnswered++;
			this.promptRandomQuestion();

		} else {
			// Display feedback
			this.quizFeedbackLabel.setText("You got " + this.quizNumberRight + " questions right out of " + this.quizLength + ".");
			this.root.setCenter(quizFeedbackPane);
		}
	}

	private void promptRandomQuestion() {
			question = this.currentBank.getRandomQuestion();
			if( this.question != null) {
				this.AskQuestionPrompt.setText(question.getPrompt());
				ArrayList<String> choice = question.getChoices();
				for (int i = 0; i < choice.size(); i++) {
					answerSelection.get(i).setText(choice.get(i));
					answerSelection.get(i).setUserData(choice.get(i));
					answerSelection.get(i).setVisible(true);
				}
			
			}
			

	}

	private void startQuiz(ActionEvent e) {
		// Ask the user for the number of question they want in their test
		TextInputDialog dialog = new TextInputDialog("");
		dialog.setTitle("Number of Questions");
		dialog.setHeaderText("How long should the quiz be?");
		dialog.setContentText("Number of questions: ");
		Optional<String> result = dialog.showAndWait();

		// Prompt the first question
		this.promptRandomQuestion();

		// Open the quiz pane
		if (result.isPresent() && this.currentBank.numberOfQuestions() > 0 ) {
			// Try to load the bank
			String input = result.get();
			if (input != null && input.matches("[0-9]+")) {
				this.quizLength = Integer.parseInt(input);
				root.setCenter(quizPane);
				this.updateFile();
			}
		}
	}

	private void newBank(ActionEvent e) {
		// Ask for the name of the file
		TextInputDialog dialog = new TextInputDialog("");
		dialog.setTitle("Name your bank of question");
		dialog.setHeaderText("Name your new bank of question");
		dialog.setContentText("Name: ");
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			// Try to load the bank
			String fileName = result.get() + ".dat";
			File file = new File(fileName);

			// Check if the bank already exists
			if (file.exists()) {
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
		}
	}

	private void loadBank(ActionEvent e) {
		// Display a file chooser and loads the file
		// If the file was properly loaded
		if (this.loadFile(fileChooser.showOpenDialog(primaryStage))) {
			// Change the Pane
			this.bankTitle.setText(this.currentBank.getName());
			root.setCenter(mainMenuPane);
		} else {
			this.error_bankAlredyExists.setText(MainGUI.ERROR_FILE_CORRUPTED);
		}
		// Set the pane
	}

	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Updates the file storing the list of banks Display an error if the file could
	 * not be updated
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

	/*
	 * Read the bank of question from a file into this.currentBank
	 * 
	 * @param file file to read the bank from
	 * 
	 * @return true if the bank was properly loaded, false otherwise
	 */
	private boolean loadFile(File file) {
		boolean flag = false;

		if (file != null && file.exists()) {
			try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
				// Load bank of question
				this.currentBank = (QuestionBank) in.readObject();
				// Update return value
				flag = true;
			} catch (FileNotFoundException e) {
			} catch (IOException e) {
			} catch (ClassNotFoundException e) {
			} catch (ClassCastException e) {
			}
		}

		return flag;
	}

	/**
	 * Add a question to this.currentBank and clear all the fields
	 * 
	 * @param questionPromptField
	 *            prompt for the question
	 * @param answerFields
	 *            list of the possible answers
	 * @param rightAnswerBox
	 *            list of the check box for the right answer
	 * @param isShuffleable
	 *            check box indicating if the question is shufflable.
	 */
	private void addQuestionToBank(TextField questionPromptField, ArrayList<TextField> answerFields,
			ArrayList<RadioButton> rightAnswerBox, CheckBox isShuffleable) {
		ChoiceQuestion question = null;

		// Create the question
		if (questionPromptField.getText() != null && !questionPromptField.getText().equals("")) {
			question = new ChoiceQuestion(questionPromptField.getText(), isShuffleable.isSelected());
		}
		for (int i = 0; i < rightAnswerBox.size(); i++) {
			if (answerFields.get(i).getText() != null && !answerFields.get(i).getText().equals("")) {
				question.addChoice(answerFields.get(i).getText(), rightAnswerBox.get(i).isSelected());
			}
		}
		// Add it to the bank
		if (question != null) {
			// add the question to the bank
			this.currentBank.addQuestion(question);
			// update the file
			this.updateFile();
		}

		// Clear fields
		questionPromptField.clear();
		isShuffleable.setSelected(false);
		for (int i = 0; i < rightAnswerBox.size(); i++) {
			answerFields.get(i).clear();
			rightAnswerBox.get(i).setSelected(false);
		}

	}

}
