package delmas.walton.studyapp;

import java.io.Serializable;

/**
 * Class holding the content of a question, able to check if the answer provided is the right one.
 * @author Isabelle Delmas
 * Created on: 09-20-2017
 * Edited on: 09-21-2017			by Isabelle Delmas			Reason : Implemented content of member functions
 *
 */
public class Question implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3897957486081603794L;

	private String text;
	private String answer;
	private int knownFlag;
	private boolean tag;
	/**
	 * Default constructor
	 * Set all the member attribute to 0 or ""
	 */
	public Question() {
		//Initialize all the member variables
		this.text = "";
		this.answer = "";
		this.knownFlag = 0;
		this.tag = false;
	}
	
	/**
	 * Constructor with two arguments
	 * @param question : the prompt to display when the question is asked
	 * @param newAnswer : the answer the user has to give
	 */
	public Question(String question, String newAnswer) {
		this.text = question;
		this.answer = newAnswer;
		this.knownFlag = 0;
		this.tag = false;
	}
	
	/**
	 * Set the tag flag
	 * @param tagValue new tag value
	 */
	public void tagued(boolean tagValue) {
		this.tag = tagValue;
	}
	
	public boolean isTagued() {
		return this.tag;
	}
	
	/**
	 * Set the prompt to display to the user
	 * @param question : the prompt to display when the question is asked
	 * @return : return true if the prompt was properly set, false otherwise
	 */
	public boolean setText(String question) {
		this.text = question;
		// Make sure the assignment was made right
		return this.text.equals(question);
	}
	/**
	 * Set the answer
	 * @param newAnswer : the answer the user has to give
	 * @return : true if the answer was properly set, false otherwise
	 */
	public boolean setAnswer(String newAnswer) {
		this.answer = newAnswer;
		// Make sure the assignment was made right
		return this.answer.equals(newAnswer);
	}
	/**
	 * Check if the user entered the right answer
	 * @param response : answers give by the user
	 * @return : true if the response matches the right answer, false otherwise
	 */
	public boolean checkAnswer(String response) {
		// Compare the user's response with the stored answer.
		boolean check =  this.answer.toLowerCase().equals(response.toLowerCase());
		
		// Update the knownFlag
		if (check) {this.knownFlag ++;}
		else if (this.knownFlag > 1) {this.knownFlag --;}
		
		// Provide feedback
		return check;
	}
	
	/**
	 * Return a string with the prompt to the user
	 * @return : a String to the user containing the prompt to display
	 */
	public String displayQuestion() {
		return this.text;
	}
	
	/**
	 * Get the know status of the flag
	 * @return : true if the user already masters the question, false otherwise
	 */
	public boolean getKnownFlag() {
		final int KNOWN_SCORE = 3;
		boolean flag = false;
		
		//If the user answered the question right KNOWN_SCORE times, return true
		if(this.knownFlag >= KNOWN_SCORE) {
			flag = true;
		}
		//Else return false
		return flag;
	}
	
	/**
	 * Reset the knownFlag
	 * @return true if the know flag was properly reset, false otherwise
	 */
	public boolean resetKnownFlag() {
		this.knownFlag = 0;
		// Make sure the assignment worked properly
		return (this.knownFlag == 0);
	}
	
	/**
	 * Return the answer to a question
	 * @return : the right answer
	 */
	public String getAnswer(){
		return this.answer;
	}
	
	@Override
	public String toString() {
		return (this.text + "/n" + "Right answer: " + this.answer); 
	}
}
