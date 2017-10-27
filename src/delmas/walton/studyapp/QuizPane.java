package delmas.walton.studyapp;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class QuizPane extends VBox {
	QuestionBank bank;
	int length;
	ChoiceQuestion question = null;
	int rightCounter;
	int questionCounter;
	
	public QuizPane(QuestionBank currentBank, int numberOfQuestions) {
		int questionCounter = 0;
		this.rightCounter = 0;
		
		// Create the layout
		this.bank = currentBank;
		this.length = numberOfQuestions;
		Label title = new Label("Quiz Time!");
		Label questionNumber = new Label("Question #" + questionCounter + "/" + this.length);
		Label questionPrompt = new Label("This is the question");
		ChoiceQuestion question = null;
		final ToggleGroup choiceGroup = new ToggleGroup();
		Button nextQuestionBtn = new Button("Next");
		ArrayList<RadioButton> answerSelection = new ArrayList<RadioButton>();
		this.getChildren().addAll(title, questionNumber, questionPrompt);

		for(int i = 0; i < 7; i++) {
			answerSelection.add(new RadioButton());
			answerSelection.get(i).setToggleGroup(choiceGroup);
			answerSelection.get(i).setVisible(false);
			this.getChildren().add(answerSelection.get(i));
		}
		this.getChildren().addAll(nextQuestionBtn);
		
		// Prompt the questions
		question = this.bank.getRandomQuestion();
		questionPrompt.setText(question.getPrompt());
		ArrayList<String> choice = question.getChoices();
		for(int i = 0; i < choice.size(); i++) {
			answerSelection.get(i).setText(choice.get(i));
			answerSelection.get(i).setUserData(choice.get(i));
			answerSelection.get(i).setVisible(true);
		}
		
		// Wait for the next question button to be pressed to check the answer
		nextQuestionBtn.setOnAction((ActionEvent e)->{
			// Check the answer
			String user = choiceGroup.getSelectedToggle().getUserData().toString();
			if(this.question.checkAnswer(user)) {
				this.rightCounter++;
			}
			for(int i = 0; i < this.question.getChoices().size(); i++) {
				answerSelection.get(i).setVisible(false);
			}
			this.questionCounter++;
		});
		
	}
}
