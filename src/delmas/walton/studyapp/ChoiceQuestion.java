package delmas.walton.studyapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Creates a multiple choice question and prepares a formatted string with the
 * question and choice options. Also includes the correct answer for verifying.
 * 
 * @author Kayla Walton creation date: 
 * 
 * Update : 10-07-2017 Reason: added constructor(String, boolean), signaled a bug in addChoice(String, boolean) by: Isabelle Delmas
 * Update : 10-13-2017 Reason: added stub for checkAnswer(int), fixed bug in displayQuestion, implemented shuffle() by: Isabelle Delmas
 * Update : 10-27-2017 Reason: fixed several methods by: Isabelle Delmas
 * 
 */
public class ChoiceQuestion extends Question implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2393515154114642504L;
	private ArrayList<String> choices = new ArrayList<String>();
	// private String correctChoice;
	private boolean shuffleable;

	/**
	 * construct a question with choices object
	 * 
	 * @param question
	 *            : question prompt
	 * @param rightAnswer
	 *            : the correct answer
	 * @param isShuffleable
	 *            : can the question be shuffled
	 */
	public ChoiceQuestion(String question, String rightAnswer, boolean isShuffleable) {
		super(question, rightAnswer);
		this.choices.add(rightAnswer);
		this.shuffleable = isShuffleable;
	}

	/**
	 * construct a question with choices object
	 * 
	 * @param question
	 *            : question prompt
	 * @param isShuffleable
	 *            : can the question be shuffled
	 */
	public ChoiceQuestion(String question, boolean isShuffleable) {
		super();
		this.setText(question);
		this.shuffleable = isShuffleable;
	}

	/**
	 * DOES NOT WORK WHEN TRYING TO ADD A NEW CORRECT ANSWER Stores a choice and
	 * whether it is correct
	 * 
	 * @param newChoice
	 *            choice to be added
	 * @param truthValue
	 *            is it correct?
	 */
	public void addChoice(String newChoice, boolean truthValue) {
		if (truthValue) // if it is the correct value, then mark it and add to list
		{
			choices.add(newChoice);
			super.setAnswer(newChoice);
		} else {
			choices.add(newChoice);
		}
	}

	/**
	 * Gather all of the available choices
	 * 
	 * @return returns the array list of current choices
	 */
	public ArrayList<String> getChoices() {
		this.shuffle();
		return choices;
	}

	/**
	 * Return only the prompt of the question
	 * 
	 * @return : prompt of the question
	 */
	public String displayQuestionWithChoice() {
		this.shuffle();
		String displayChoices = this.getPrompt() + "\n";
		for (int i = 0; i < choices.size(); i++) {
			displayChoices = displayChoices + (i + 1) + ": " + choices.get(i) + "\n";
		}
		return displayChoices;
	}
	
	public String getPrompt() {
		return super.displayQuestion();
	}

	/**
	 * If possible, shuffles the choices
	 */
	private void shuffle() {
		if (this.shuffleable) {
			Collections.shuffle(this.choices);
		}
	}
}
