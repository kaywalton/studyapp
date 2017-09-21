package delmas.walton.studyapp;

/**
 * Class holding the content of a question, able to check if the answer provided is the right one.
 * @author Isabelle Delmas
 * Created on: 09-20-2017
 * Edited on:				by
 *
 */
public class Question {
	
	private String text;
	private String answer;
	private int knownFlag;
	/**
	 * Default constructor
	 * Set all the member attribute to 0 or ""
	 */
	public Question() {
		// TO DO
	}
	
	/**
	 * Constructor with two arguments
	 * @param question : the prompt to display when the question is asked
	 * @param newAnswer : the aswer the user has to give
	 */
	public Question(String question, String newAnswer) {
		//TO DO
	}
	
	/**
	 * Set the prompt to display to the user
	 * @param question : the prompt to display when the question is asked
	 * @return : return true if the prompt was properly set, false otherwise
	 */
	public boolean setText(String question) {
		//TO DO
		return false;
	}
	
	/**
	 * Set the answer
	 * @param newAnswer : the answer the user has to give
	 * @return : true if the answer was properly set, false otherwise
	 */
	public boolean setAnswer(String newAnswer) {
		//TO DO
		return false;
	}
	
	/**
	 * Check if the user entered the right answer
	 * @param response : answers give by the user
	 * @return : true if the response matches the right answer, false otherwise
	 */
	public boolean checkAnswer(String response) {
		//TO DO
		return false;
	}
	
	/**
	 * Return a string with the prompt to the user
	 * @return : a String to the user containing the prompt to display
	 */
	public String displayQuestion() {
		//TO DO
		return "";
	}
	
	/**
	 * Get the know status of the flag
	 * @return : true if the user already masters the question, false otherwise
	 */
	public boolean getKnownFlag() {
		return false;
	}
	
	/**
	 * Reset the knownFlag
	 * @return true if the know flag was properly reset, false otherwise
	 */
	public boolean resetKnownFlag() {
		//TO DO
		return false;
	}
}
