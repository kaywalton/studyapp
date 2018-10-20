package delmas.walton.studyapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainGUI extends Application {
	// Static variable used to display to the user
	static final String ERROR_BANK_ALREADY_EXISTS = "This bank already exists. Try to load it instead of creating it.";
	static final String ERROR_FILE_NOT_SAVED = "Your file could not be updated; your work will not be saved.";
	static final String ERROR_FILE_CORRUPTED = "This file could not be open";
	static final int SCREEN_HEIGHT = 750;
	static final int SCREEN_WIDTH = 1450;
	static final int IMAGE_MAX_HEIGHT = 450;
	static final int IMAGE_MAX_WIDTH = 1400;

	// Instance variables
	private File selectedDirectory;
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
	private Label questionNumberLabel;
	private VBox removeQuestionPane;
	private ArrayList<CheckBox> listOfQuestion = new ArrayList<>();
	private VBox reviewPane;
	private Label reviewQuestionNumer;
	private Label reviewAskQuestionPrompt;
	private ArrayList<RadioButton> reviewChoiceSelection;
	final private ToggleGroup reviewChoiceGroup = new ToggleGroup();
	private CheckBox addImageBox;
	private File imageFile;
	private ImageView reviewImageView;
	private ImageView quizImageView;
	private VBox taggedPane;
	private ArrayList<CheckBox> listOfTaggedQuestion = new ArrayList<>();
	private Button untagSelectedTaggedQuestionsBtn;
	private Button removeSelectedQuestionsBtn;
	private ArrayList<ImageView> taggedImage = new ArrayList<>();
	private ScrollPane scrollTagged;
	
	@Override
	public void stop() {
		if(this.currentBank != null) {
			this.updateFile();
		}
		try {
			super.stop();
		} catch (Exception e) {
		}
	}

	@Override
	public void start(Stage stage) {
		this.primaryStage = stage;
		
		// Create a button to go back to main menu
		Button backToMainBtn = new Button("Back to main menu");
		
		// Create the fixed menu bar
		this.root = new BorderPane();
		MenuBar mainMenu = new MenuBar();
		final Menu menu1 = new Menu("File");
		final Menu menu2 = new Menu("Manage Bank");
		final Menu menu3 = new Menu("Quiz/Review");
		final MenuItem saveAndExit = new MenuItem("Save progress and exits");
		final MenuItem backToMain = new MenuItem("Back to main menu");
		final MenuItem loadOtherBank = new MenuItem("Load an other bank");
		final MenuItem createNewBank = new MenuItem("Create a new bank");
		final MenuItem removeQuestions = new MenuItem("Remove questions");
		final MenuItem seeTaggedQuestions = new MenuItem("See taggued questions");
		final MenuItem addQuestions = new MenuItem("Add questions");
		final MenuItem startQuiz = new MenuItem("Start Quiz");
		final MenuItem startReview = new MenuItem("Start Review");
		menu1.getItems().addAll(backToMain, loadOtherBank, createNewBank, saveAndExit);
		menu2.getItems().addAll(removeQuestions, seeTaggedQuestions, addQuestions);
		menu3.getItems().addAll(startQuiz, startReview);
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
		final Button addQuestionbtn = new Button("Add a question");
		final Button removeQuestionbtn = new Button("Remove a question");
		final Button backToMMbtn = new Button("Back to main menu");
		final Button resetProgressBtn = new Button("Reset Progress");
		final Button displayTaggedQuestionsBtn = new Button("Tagged Questions");
		leftMenu.getChildren().addAll(backToMMbtn, addQuestionbtn, removeQuestionbtn, resetProgressBtn, displayTaggedQuestionsBtn);
		leftMenu.setSpacing(10);
		leftMenu.setAlignment(Pos.CENTER);
		manageBankPane.setLeft(leftMenu);
		manageBankPane.setTop(this.bankTitle);
		BorderPane.setAlignment(this.bankTitle, Pos.CENTER);

		// Create the main menu pane
		this.mainMenuPane = new VBox();
		Button startQuizBtn = new Button("Start Quiz");
		Button startReviewBtn = new Button("Start Review");
		Button manageBankBtn = new Button("Manage Questions and Progress");
		Button feedbackBtn = new Button("Feedback");
		mainMenuPane.getChildren().addAll(this.bankTitle, startQuizBtn, startReviewBtn, manageBankBtn, feedbackBtn);
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
		Button addImageBtn = new Button("Choose image");
		this.addImageBox = new CheckBox("include image");
		addQuestionPane.add(questionPrompt, 0, 0);
		addQuestionPane.add(questionPromptField, 1, 0, 3, 1);
		for (int i = 0; i < MAX_ANSWERS; i++) {
			addQuestionPane.add(answerLabels.get(i), 0, i + 1);
			addQuestionPane.add(answerFields.get(i), 1, i + 1, 3, 1);
			addQuestionPane.add(rightAnswerBox.get(i), 4, i + 1);
		}
		addQuestionPane.add(isShuffleable, 0, 9);
		addQuestionPane.add(addImageBtn,  1,  10);
		addQuestionPane.add(this.addImageBox, 2, 10);
		addQuestionPane.add(validateNewQuestionBtn, 4, 11);

		// Create the quiz pane
		this.quizQAnswered = 0;
		this.quizLength = 0;
		this.quizNumberRight = 0;
		this.quizPane = new VBox();
		Label title = new Label("Quiz Time!");
		this.questionNumberLabel = new Label("Question #" + this.quizQAnswered + "/" + this.quizLength);
		this.AskQuestionPrompt = new Label("This is the question");
		this.AskQuestionPrompt.setWrapText(true);
		this.question = null;
		Button quizNextQuestionBtn = new Button("Next");
		this.answerSelection = new ArrayList<RadioButton>();
		this.quizImageView = new ImageView();
		this.quizImageView.setFitWidth(MainGUI.IMAGE_MAX_WIDTH);
		this.quizImageView.setFitHeight(MainGUI.IMAGE_MAX_HEIGHT);
		this.quizImageView.setPreserveRatio(true);
		quizPane.getChildren().addAll(title, this.questionNumberLabel, this.AskQuestionPrompt, this.quizImageView);
		this.quizImageView.setPreserveRatio(true);
		for (int i = 0; i < MAX_ANSWERS; i++) {
			answerSelection.add(new RadioButton());
			answerSelection.get(i).setToggleGroup(choiceGroup);
			answerSelection.get(i).setVisible(false);
			quizPane.getChildren().add(answerSelection.get(i));
		}
		quizPane.getChildren().addAll(quizNextQuestionBtn);
		
		// Create the review pane
		Label reviewTitle = new Label("Review Time!");
		this.reviewQuestionNumer = new Label();
		this.reviewPane = new VBox();
		Button reviewNextQuestionBtn = new Button("Check Answer");
		Button reviewEndBtn = new Button("End Review");
		this.reviewAskQuestionPrompt = new Label();
		this.reviewAskQuestionPrompt.setWrapText(true);
		this.reviewChoiceSelection = new ArrayList<RadioButton>();
		this.reviewImageView = new ImageView();
		this.reviewImageView.setFitWidth(MainGUI.IMAGE_MAX_WIDTH);
		this.reviewImageView.setFitHeight(MainGUI.IMAGE_MAX_HEIGHT);
		this.reviewImageView.setPreserveRatio(true);
		
		for(int i = 0; i < MAX_ANSWERS; i++) {
			reviewChoiceSelection.add(new RadioButton());
			reviewChoiceSelection.get(i).setToggleGroup(this.reviewChoiceGroup);
			reviewChoiceSelection.get(i).setVisible(false);
		}
		this.reviewPane.getChildren().addAll(reviewTitle,this.reviewQuestionNumer, this.reviewAskQuestionPrompt, this.reviewImageView);
		this.reviewPane.getChildren().addAll(reviewChoiceSelection);
		this.reviewPane.getChildren().addAll(reviewNextQuestionBtn, reviewEndBtn);
		
		// Create the quiz feedback pane
		this.quizFeedbackPane = new VBox();
		quizFeedbackLabel = new Label();
		this.quizFeedbackPane.getChildren().addAll(quizFeedbackLabel, backToMainBtn);
		
		// Create the remove question pane;
		this.removeQuestionPane = new VBox();
		this.removeSelectedQuestionsBtn = new Button("Remove Selected Questions");
		this.removeQuestionPane.getChildren().add(removeSelectedQuestionsBtn);
		
		// Create the tagged question pane
		this.taggedPane = new VBox();
		this.untagSelectedTaggedQuestionsBtn = new Button("Untag Selected Questions");
		this.taggedPane.getChildren().add(untagSelectedTaggedQuestionsBtn);
		this.taggedPane.setSpacing(15);
		this.scrollTagged = new ScrollPane(taggedPane);

		
		// Create file opener and other utilities
		this.fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");

		// Set up initial display
		root.setTop(mainMenu);
		root.setCenter(welcomePane);

		// Listen for button clicks
		// When the top menu bar is used
		saveAndExit.setOnAction((ActionEvent e)->{
			Platform.exit();
			System.exit(0);
		});
		backToMain.setOnAction((ActionEvent e)->{
			if(this.currentBank != null) {
				this.backToMain(e);
			}
		});
		loadOtherBank.setOnAction(this::loadBank);
		createNewBank.setOnAction(this::newBank);
		addQuestions.setOnAction((ActionEvent e) -> {
			if(this.currentBank != null) {
				this.manageBankPane.setCenter(addQuestionPane);
				root.setCenter(manageBankPane);
			}
		});
		seeTaggedQuestions.setOnAction((ActionEvent e) -> {
			if(this.currentBank != null) {
				this.manageBankPane.setCenter(this.scrollTagged);
				root.setCenter(manageBankPane);
				this.displayTaggedQuestions(e);
			}
		});
		removeQuestions.setOnAction((ActionEvent e) -> {
			if(this.currentBank != null) {
				this.manageBankPane.setCenter(removeQuestionPane);
				root.setCenter(manageBankPane);
				this.displayQuestionsToRemove(e);
			}
		});
		startQuiz.setOnAction((ActionEvent e) -> {
			if(this.currentBank != null) {
				this.startQuiz(e);
			}
		});
		startReview.setOnAction((ActionEvent e) -> {
			if(this.currentBank != null) {
				this.startReview(e);
			}
		});
		// When the removed selected tagged question button is pressed
		untagSelectedTaggedQuestionsBtn.setOnAction(this::untagSelectedQuestions);
		// When the display tagged question is pressed
		displayTaggedQuestionsBtn.setOnAction(this::displayTaggedQuestions);
		// When the reset progress button is pressed
		resetProgressBtn.setOnAction(this::resetProgress);
		// When the add image button is pressed
		addImageBtn.setOnAction((ActionEvent e) -> {
			this.fileChooser.setTitle("Chose an image file");
			this.imageFile = this.fileChooser.showOpenDialog(primaryStage);
			this.addImageBox.setSelected(true);
		});
		// When the feedback button is pressed
		feedbackBtn.setOnAction(this::promptGeneralFeedback);
		//When the answer review question btn is pressed
		reviewNextQuestionBtn.setOnAction(this::answerReviewQuestion);
		//When the end review button is pressed, go back to main menu
		reviewEndBtn.setOnAction(this::backToMain);
		// When the start review button is pressed
		startReviewBtn.setOnAction(this::startReview);
		// When the remove selected question button is pressed
		removeSelectedQuestionsBtn.setOnAction(this::removeSelectedQuestions);
		// When the remove question button is pressed
		removeQuestionbtn.setOnAction(this::displayQuestionsToRemove);
		// When the back to main menu button is pressed
		backToMainBtn.setOnAction(this::backToMain);
		// When the back to main menu button is pressed
		backToMMbtn.setOnAction(this::backToMain);
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
		this.loadBankbtn.setOnAction(this::loadBank);
		// When the "Create new Bank button is clicked
		this.newBankbtn.setOnAction(this::newBank);
		// When the manage bank button is clicked
		manageBankBtn.setOnAction((ActionEvent e) -> {
			this.manageBankPane.setCenter(addQuestionPane);
			root.setCenter(manageBankPane);
		});
		// When the add question button is clicked
		addQuestionbtn.setOnAction((ActionEvent e) -> {
			manageBankPane.setCenter(addQuestionPane);
		});
		
		// Display all
		primaryStage.setScene(new Scene(root, MainGUI.SCREEN_WIDTH, MainGUI.SCREEN_HEIGHT));
		primaryStage.show();

	}
	
	private void displayTaggedQuestions(ActionEvent e) {
		if (this.taggedPane.getChildren() != null) {
			this.taggedPane.getChildren().clear();
			this.taggedPane.getChildren().add(this.untagSelectedTaggedQuestionsBtn);
		}
		this.listOfTaggedQuestion.clear();
		ArrayList<String> questionList = this.currentBank.getTaggedQuestionsWithAnswers();
		ArrayList<String> promptList = this.currentBank.getTaggedPrompts();
		ArrayList<String> pathList  = this.currentBank.getTaggedImagePaths();
		Image tempI = null;
		for(int i = 0; i < questionList.size(); i++) {
			this.listOfTaggedQuestion.add(new CheckBox(questionList.get(i)));
			this.listOfTaggedQuestion.get(i).setUserData(promptList.get(i));
			this.taggedPane.getChildren().add(this.listOfTaggedQuestion.get(i));
			
			//Add the image after the question if there is an image
			// Make sure there is an image associated with the question before trying to display it
			if(pathList.get(i).equals("") == false) {
				try{
					// Load the image
					String path = this.selectedDirectory.getParent() + "/" + pathList.get(i);		// get absolute the path
					FileInputStream fileInput = null;
					try {
						fileInput = new FileInputStream(path);			// Create an fileInputStrem with the image
					} catch (FileNotFoundException er) {
						this.promptErrorDisplayImage();
					}
					tempI = new Image(fileInput);						// Create the image
					ImageView tempIV = new ImageView(tempI);
					if(tempIV.getFitHeight() > MainGUI.IMAGE_MAX_HEIGHT) {
						tempIV.setPreserveRatio(true);
						tempIV.setFitHeight(MainGUI.IMAGE_MAX_HEIGHT);
					}
					taggedImage.add(tempIV);
					this.taggedPane.getChildren().add(taggedImage.get(this.taggedImage.size()-1));
				}catch(IllegalArgumentException er) {
					this.promptErrorDisplayImage();
				} catch(NullPointerException er) {
					this.promptErrorDisplayImage();
				}
				
			}
		}
		//this.taggedPane.getChildren().addAll(this.listOfTaggedQuestion);
		this.manageBankPane.setCenter(this.scrollTagged);
	}
	
	private void untagSelectedQuestions(ActionEvent e) {
		// Go through the list of all the check boxes associated with the questions
		for(int i = 0; i < this.listOfTaggedQuestion.size(); i++) {
			// if the checkbox was selected
			if(this.listOfTaggedQuestion.get(i).isSelected()) {
				// remove the question associated to the checkbox
				this.currentBank.untagQuestion(this.listOfTaggedQuestion.get(i).getUserData().toString());
			}
		}
		this.updateFile();
		this.displayTaggedQuestions(e);
	}
	
	private void resetProgress(ActionEvent e) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Progress");
		alert.setHeaderText("You are about to delete your progress.");
		alert.setContentText("This irreversible action will set all the questions\nto unknown and remove all the tagged questions.");
		ButtonType cancelBtn = new ButtonType("Cancel");
		ButtonType continuBtn = new ButtonType("Delete Progress");
		alert.getButtonTypes().setAll(cancelBtn, continuBtn);
		Optional<ButtonType> result = alert.showAndWait();
		// If the user wants to tag the question
		if(result.get() == continuBtn) {
			this.currentBank.resetAllFlags();
		}
		this.updateFile();
	}
	
	private void promptGeneralFeedback(ActionEvent e) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Progress Report");
		alert.setHeaderText("Here is your progress so far.");
		alert.setContentText("You master " + this.currentBank.getPercentKnown() + "% of the question(s).\n\nThere are still " + (this.currentBank.numberOfQuestions()-this.currentBank.getKnown()) + " questions you need to work on\nout of the " + this.currentBank.numberOfQuestions() + " questions in that bank.");

		alert.showAndWait();
	}
	
	private void answerReviewQuestion(ActionEvent e) {
		ButtonType okBtn = new ButtonType("Ok");
		ButtonType tagBtn = new ButtonType("Tag the question");
		Optional<ButtonType> result;

		// if the answer was right
		if (this.question.checkAnswer(reviewChoiceGroup.getSelectedToggle().getUserData().toString())) {
			// Display positive feedback
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Feedback");
			alert.setHeaderText(null);
			alert.setContentText("That's correct!");
			alert.getButtonTypes().setAll(okBtn, tagBtn);
			result = alert.showAndWait();
			// If the user wants to tag the question
			if(result.get() == tagBtn) {
				this.question.tagged(true);
			}
		} else {
			// Display the right answer
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Feedback");
			alert.setHeaderText("Not quite");
			// TODO: wrap text in alert box
			Label feedbackAnswer = new Label ("The correct answer was:\n" + this.question.getAnswer());
			feedbackAnswer.setWrapText(true);
			alert.getDialogPane().setContent(feedbackAnswer);
			alert.getButtonTypes().setAll(okBtn, tagBtn);
			
			result = alert.showAndWait();
			// If the user wants to tag the question
			if(result.get() == tagBtn) {
				this.question.tagged(true);
			}
		}
		// hides the answers
		for (int i = 0; i < this.question.getChoices().size(); i++) {
			reviewChoiceSelection.get(i).setVisible(false);
			reviewChoiceSelection.get(i).setSelected(false);
		}
		// Remove the image
		reviewImageView.setImage(null);
		
		// Display the next question
		this.promptRandomQuestion(this.reviewAskQuestionPrompt, this.reviewChoiceSelection, this.reviewImageView);
		this.quizQAnswered++;
		this.reviewQuestionNumer.setText("Question #" + this.quizQAnswered);
		this.updateFile();
	}

	private void startReview(ActionEvent e) {
		
		if (this.currentBank.numberOfQuestions() > 0 ) {
			// Try to load the bank
				this.quizQAnswered = 1;
				this.promptRandomQuestion(this.reviewAskQuestionPrompt, this.reviewChoiceSelection, this.reviewImageView);
				this.reviewQuestionNumer.setText("Question #" + this.quizQAnswered);
				this.root.setCenter(this.reviewPane);
		}
		
	}
	
	private void backToMain(ActionEvent e) {
		this.root.setCenter(this.mainMenuPane);
	}
	
	private void removeSelectedQuestions(ActionEvent e) {
		// Go through the list of all the check boxes associated with the questions
		for(int i = 0; i < this.listOfQuestion.size(); i++) {
			// if the checkbox was selected
			if(this.listOfQuestion.get(i).isSelected()) {
				// remove the question associated to the checkbox
				this.currentBank.removeQuestion(this.listOfQuestion.get(i).getUserData().toString());
			}
		}
		this.updateFile();
		this.displayQuestionsToRemove(e);
	}
	
	private void displayQuestionsToRemove(ActionEvent e) {
		if(this.removeQuestionPane.getChildren() != null) {
			this.removeQuestionPane.getChildren().clear();
			this.removeQuestionPane.getChildren().add(this.removeSelectedQuestionsBtn);
		}
		
		ArrayList<String> questionsList = this.currentBank.getQuestionsPrompt();
		this.listOfQuestion.clear();

		for(int i = 0; i < questionsList.size(); i++) {
			this.listOfQuestion.add(new CheckBox(questionsList.get(i)));
			// set the data associated to the check box to the question prompt
			this.listOfQuestion.get(i).setUserData(questionsList.get(i));

			this.removeQuestionPane.getChildren().add(this.listOfQuestion.get(i));
		}
		
		this.manageBankPane.setCenter(this.removeQuestionPane);
	}
	
	private void answerQuizQuestion(ActionEvent e) {

		// if the quiz is not over yet
		if(this.quizQAnswered < this.quizLength) {
			// Check the answer
			if (this.question.checkAnswer(choiceGroup.getSelectedToggle().getUserData().toString())) {
				this.quizNumberRight++;
			}
			
			// Reset the question fields
			for (int i = 0; i < this.question.getChoices().size(); i++) {
				answerSelection.get(i).setVisible(false);
				answerSelection.get(i).setSelected(false);
			}
			
			// Ask a new question
			this.promptRandomQuestion(this.AskQuestionPrompt, this.answerSelection, this.quizImageView);
			this.quizQAnswered++;
			this.questionNumberLabel.setText("Question #" + this.quizQAnswered + "/" + this.quizLength);

		// If this was the last question
		} else {
			// Check the last answer
			if (this.question.checkAnswer(choiceGroup.getSelectedToggle().getUserData().toString())) {
				this.quizNumberRight++;
			}
			// Hides the section boxes
			for (int i = 0; i < this.question.getChoices().size(); i++) {
				answerSelection.get(i).setVisible(false);
				answerSelection.get(i).setSelected(false);
			}
			
			// Display feedback
			this.quizFeedbackLabel.setText("You got " + this.quizNumberRight + " questions right out of " + this.quizLength + ".");
			// Save progress before exiting quiz
			this.updateFile();
			// End quiz
			this.root.setCenter(quizFeedbackPane);
		}
	}

	private void promptRandomQuestion(Label prompt, ArrayList<RadioButton> list, ImageView image) {
			this.question = this.currentBank.getRandomQuestion();
			//File temp = new File(question.getImagePath());
			Image tempI = null;
			
			if( this.question != null) {
				// Make sure there is an image associated with the question before trying to display it
				if(this.question.getImagePath().equals("") == false) {
					try{
						// Load the image
						String path = this.selectedDirectory.getParent() + "/" + question.getImagePath();		// get absolute the path
						FileInputStream fileInput = null;
						try {
							fileInput = new FileInputStream(path);			// Create an fileInputStrem with the image
						} catch (FileNotFoundException e) {
							this.promptErrorDisplayImage();
						}
						tempI = new Image(fileInput);						// Create the image
						image.setImage(tempI);								// Load the image to the pane
						// Adjust the size of the image while keeping the w/h ratio the same
						image.setVisible(true);
						image.setPreserveRatio(false);
						image.setFitHeight(tempI.getHeight());
						image.setFitWidth(tempI.getWidth());
						if(image.getFitHeight() > MainGUI.IMAGE_MAX_HEIGHT) {
							image.setPreserveRatio(true);
							image.setFitHeight(MainGUI.IMAGE_MAX_HEIGHT);
						}
					} catch(IllegalArgumentException e) {
						this.promptErrorDisplayImage();
					} catch(NullPointerException e) {
						this.promptErrorDisplayImage();
					}
					
				} else {
					// If there is no image, reduces the size of the image view and set it to non visible
					image.setFitHeight(1);
					image.setFitWidth(1);
					image.setVisible(false);
				}
				// Display the other elements
				prompt.setText(question.displayQuestion());
				ArrayList<String> choice = question.getChoices();
				for (int i = 0; i < choice.size(); i++) {
					list.get(i).setText(choice.get(i));
					list.get(i).setUserData(choice.get(i));
					list.get(i).setVisible(true);
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
		this.promptRandomQuestion(this.AskQuestionPrompt, this.answerSelection, this.quizImageView);

		// Open the quiz pane
		if (result.isPresent() && this.currentBank.numberOfQuestions() > 0 ) {
			// Try to load the bank
			String input = result.get();
			if (input != null && input.matches("[0-9]+")) {
				this.quizQAnswered = 1;
				this.quizNumberRight = 0;
				this.quizLength = Integer.parseInt(input);
				this.questionNumberLabel.setText("Question #" + this.quizQAnswered + "/" + this.quizLength);
				root.setCenter(quizPane);
			}
		}
	}

	private void newBank(ActionEvent e) {
		// Ask for the name of the file
		TextInputDialog dialog = new TextInputDialog("");
		dialog.setTitle("Name your bank of question");
		dialog.setHeaderText("Name your new bank of question.\nNext, you will be asked to select a folder to save your data.\nWe recommande that you save your data in a SEPARATE FOLDER.");
		dialog.setContentText("Name: ");
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			// Try to load the bank
			String fileName = "/" + result.get() + ".studyBuddy";


			// Ask for a directory where to store the file
			DirectoryChooser chooser = new DirectoryChooser();
			chooser.setTitle("Location of the new bank");
			this.selectedDirectory = chooser.showDialog(primaryStage);
			String path = this.selectedDirectory.getPath() + fileName;
			this.selectedDirectory = new File(path);
			// Check if the bank already exists
			if (this.selectedDirectory.exists()) {
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
		this.selectedDirectory = fileChooser.showOpenDialog(primaryStage);
		// If the file was properly loaded
		if (this.loadFile()) {
			// Change the Pane
			this.bankTitle.setText(this.currentBank.getName());
			root.setCenter(mainMenuPane);
		} else {
			this.error_bankAlredyExists.setText(MainGUI.ERROR_FILE_CORRUPTED);
		}
		// Set the pane
	}

	public static void main(String[] args) {
		try {
			launch(args);
		} catch(Exception e) {
			System.out.println(e.fillInStackTrace());
			e.printStackTrace();
		}
	}

	/**
	 * Updates the file storing the list of banks Display an error if the file could
	 * not be updated
	 */
	private void updateFile() {
		try {
			// Save the file to disk
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(this.selectedDirectory));
			out.writeObject(this.currentBank);
			out.close();
		} catch (IOException e) {
			// Pop up a warning message
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("Could not save data");
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
	private boolean loadFile() {
		boolean flag = false;

		if (this.selectedDirectory != null && this.selectedDirectory.exists()) {
			try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(this.selectedDirectory))) {
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

	private void promptErrorDisplayImage() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Warning");
		alert.setHeaderText("Missing Component");
		alert.setContentText("The image located at:\n" + question.getImagePath() + "\nwas not found.");
		alert.showAndWait();
	}
	
	private void promptErrorCopyImage() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Warning");
		alert.setHeaderText("Could not save data");
		alert.setContentText("Enable to copy the image.");
		alert.showAndWait();
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
		String imageFileName = "";

		// Create the question
		if (questionPromptField.getText() != null && !questionPromptField.getText().equals("")) {
			question = new ChoiceQuestion(questionPromptField.getText(), isShuffleable.isSelected());
		}
		for (int i = 0; i < rightAnswerBox.size(); i++) {
			if (answerFields.get(i).getText() != null && !answerFields.get(i).getText().equals("")) {
				question.addChoice(answerFields.get(i).getText(), rightAnswerBox.get(i).isSelected());
			}
		}
		
		// If the user chose to add an image
		if(this.imageFile != null && this.addImageBox.isSelected()) {
			// Copy the image in the same directory as the bank
			try{
				final Path path1 = Paths.get(this.imageFile.getPath());
				final Path path2 = Paths.get(this.selectedDirectory.getParent() +"/" + this.imageFile.getName());	
				Files.copy(path1, path2);
			} catch (InvalidPathException e) {
				this.promptErrorCopyImage();
				e.printStackTrace();
			} catch (IOException e) {
				this.promptErrorCopyImage();
				e.printStackTrace();
			}
			imageFileName = this.imageFile.getName();
		}
		// Add the path to the question, or "" if no image
		question.setImagePath(imageFileName);
		
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
		this.addImageBox.setSelected(false);
		for (int i = 0; i < rightAnswerBox.size(); i++) {
			answerFields.get(i).clear();
			rightAnswerBox.get(i).setSelected(false);
		}

	}

}
