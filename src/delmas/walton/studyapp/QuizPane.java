package delmas.walton.studyapp;

import java.util.ArrayList;

import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class QuizPane extends VBox {
	QuestionBank bank;
	int length;
	
	public QuizPane(QuestionBank currentBank, int numberOfQuestions) {
		this.bank = currentBank;
		this.length = numberOfQuestions;
		Label title = new Label("Quiz Time!");
		Label questionDisplay = new Label("");
		Label questionPrompt = new Label("");
		ArrayList<String> answerChoices = new ArrayList<String>();
		Question question = null;
		ArrayList<CheckBox> answerSelection = new ArrayList<CheckBox>();
		for(int i = 0; i < 7; i++) {
			answerSelection.add(new CheckBox());
		}
		
		this.getChildren().addAll(title, questionDisplay);
	}
}
