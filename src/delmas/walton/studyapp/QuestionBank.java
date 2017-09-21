package delmas.walton.studyapp;
/**
 * Class holding the content of a Bank of Question.
 * @author Isabelle Delmas
 * Created on: 09-21-2017
 * Edited on:				by
 *
 */

import java.util.ArrayList;

public class QuestionBank {
	private int numberOfQuestions;
	private ArrayList<ChoiceQuestion> allQuestions;
	private String fileName;
	
	/**
	 * Constructor with the name of the file
	 * If the file does not exists, creates it
	 * @param newFileName the name of the file where the questions are stored
	 */
	public QuestionBank(String newFileName) {
		readInBank();
	}
	 /**
	  * Adds a new question to the list of question saved in the bank
	  * @param rightAnswer : the right answer to the question
	  * @param possibleAnswers : all the possible answers
	  * @param question : the prompt to the user
	  * @param isShuffleable : can the possible answers the shuffled
	  * @return : true if the question was properly added
	  */
	public void createNewQuestion(String rightAnswer, ArrayList<String> possibleAnswers, String question, boolean isShuffleable ) {
		// Create a new question
		ChoiceQuestion newQ = new ChoiceQuestion(question, rightAnswer, isShuffleable);
		boolean flag = false;
		// Add all the choices and checks if it is the right answer
		for (int i = 0; i < possibleAnswers.size(); i++) {
			flag = (rightAnswer == possibleAnswers.get(i));
			newQ.addChoice(possibleAnswers.get(i), flag);
		}
		// Add the new question to the list
		allQuestions.add(newQ);
	}
	
	/**
	 * Remove a question from the list of question in the bank
	 * @return : true if the question was found and removed
	 */
	public boolean removeQuestion() {
		return false;
	}
	
	
	/**
	 * Returns a random question to the user
	 * Higher probability to get a non-mastered question
	 * @return : a random question
	 */
	public Question getRandomQuestion() {
		return new Question();
	}
	
	/**
	 * Write the sate of the bank of questions to the file.
	 * Overwrite the previous file
	 * this action is irreversible
	 * @return : true if the file was properly overwritten
	 */
	public boolean updateFile() {
		return false;
	}
	
	/**
	 * used by the constructor to load the content of a file into the bank
	 * @return : true if the file was correctly read
	 */
	private boolean readInBank() {
		return false;
	}
}
