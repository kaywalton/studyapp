package delmas.walton.studyapp;
/**
 * Class holding the content of a Bank of Question.
 * @author Isabelle Delmas
 * Created on: 09-21-2017
 * Edited on:				by
 *
 */

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Random;

public class QuestionBank implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4401620113342983294L;
	private ArrayList<ChoiceQuestion> allQuestions;
	private String name;
	
	/**
	 * Default constructor
	 */
	public QuestionBank() {
		allQuestions = new ArrayList<ChoiceQuestion>();
	}
	
	/**
	 * Constructor that takes the name of the bank
	 * @param newName : name of the bank
	 */
	public QuestionBank(String newName) {
		this.setName(newName);
	}
	
	/**
	 * Return the name of the bank
	 * @return name of the bank
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Set the name of the bank
	 * @param newName : name of the bank
	 */
	public void setName(String newName) {
		this.name = newName;
	}

	 /**
	  * Adds a new question to the list of question saved in the bank
	  * @param newQuestion : question to add to the bank
	  */
	public void addQuestion(ChoiceQuestion newQuestion) {

		// Add the new question to the list
		allQuestions.add(newQuestion);
	}
	
	/**
	 * Remove a question from the list of question in the bank
	 * @param questionPrompt : the prompt from the question to delete
	 * @return : true if the question was found and removed
	 */
	public boolean removeQuestion(String questionPrompt) {
		boolean returnValue = false;
		// Go through the list of question
		for(int i = 0; i < this.allQuestions.size(); i++) {
			ChoiceQuestion questionToRemove = this.allQuestions.get(i);
			// If the prompt matches
			if (questionToRemove.getPrompt().equals(questionPrompt)) {
				// Update return value and delete the question from the list
				returnValue = true;
				this.allQuestions.remove(questionToRemove);
			}
		}
		
		return returnValue;
	}
	
	
	/**
	 * Returns a random question to the user
	 * Higher probability to get a non-mastered question
	 * @return : a random question
	 */
	public ChoiceQuestion getRandomQuestion() {
		final double MASTERED_RATE = 25; 	// 25 out of 100;
		boolean found = false;
		ChoiceQuestion question = null;
		
		// Repeat until a question is found
		while (!found) {
			// Get a random question
			Random randomGenerator = new Random(Instant.now().getNano());
			int randomIndex = randomGenerator.nextInt(this.allQuestions.size());
			question = this.allQuestions.get(randomIndex);
			
			// If the question at that index is mastered, returns it only MASTERED_RATE times
			if (question.getKnownFlag()) {
				int chance = randomGenerator.nextInt(100);
				if (chance < MASTERED_RATE) {found = true;}
				// Else gets another question
			}
		}
		return question;
	}
	
	/**
	 * Get the number of questions stored in the bank
	 * @return number of questions
	 */
	public int numberOfQuestions() {
		return this.allQuestions.size();
	}
	

}
