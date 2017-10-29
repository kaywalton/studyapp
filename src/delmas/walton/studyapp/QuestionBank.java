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
	private ArrayList<ChoiceQuestion> allQuestions = new ArrayList<ChoiceQuestion>();
	private String name;
	
	/**
	 * Default constructor
	 */
	public QuestionBank() {
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
		if(this.allQuestions != null) {
			this.allQuestions.add(newQuestion);
		} else {
			System.out.println("allQuestion is null");
		}
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
	 * @return : a random question, null if the bank is empty
	 */
	public ChoiceQuestion getRandomQuestion() {
		final double MASTERED_RATE = 25; 	// 25 out of 100;
		boolean found = false;
		ChoiceQuestion question = null;
		
		// Make sure the list is not empty
		if(this.allQuestions.isEmpty()){
			found = true;	// will not enter the next while loop and will return null
		}
		
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
			} else{
				found = true;
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
	
	/**
	 * temp toString
	 */
	public String toString() {
		return this.getName();
	}
		
	public void removeAllQuestions() {
		this.allQuestions = new ArrayList<ChoiceQuestion>();
	}
	
	/**
	 * Displays all the questions in the bank
	 * @return a String with the list of questions
	 */
	public String displayQuestions() {
		String returnValue = "";
		for(int i = 0; i < this.allQuestions.size(); i++) {
			returnValue += this.allQuestions.get(i).getPrompt() + "\n";
		}
		return returnValue;
	}
	
	public ArrayList<String> getQuestionsPrompt(){
		ArrayList<String> list = new ArrayList<>();
		
		for(Question element : this.allQuestions) {
			list.add(element.displayQuestion());
		}
		
		return list;
	}
	
	/**
	 * Get the number of question known
	 * @return number of question known
	 */
	public int getKnown() {
		int known = 0;
		for(Question element : this.allQuestions) {
			if(element.getKnownFlag()) {
				known++;
			}
		}
		return known;
	}
	
	/**
	 * Get the percent of question known
	 * @return percent of question known
	 */
	public double getPercentKnown() {
		double percentKnown = 0.0;
		if(this.allQuestions.size() > 0 ) {
			percentKnown = this.getKnown()*100/this.allQuestions.size();
		}
		return percentKnown;
	}
	
	/**
	 * Get a list with all the question tagged and their answers
	 * @return
	 */
	public ArrayList<String> getTagged(){
		ArrayList<String> tagued = new ArrayList<>();
		for(Question element : this.allQuestions) {
			tagued.add(element.toString()); 
		}
		return tagued;
	}
	
	/**
	 * Sets the tag flag to false and the know flag to false for all the questions in the bank
	 */
	public void resetAllFlags() {
		for(int i = 0; i < this.allQuestions.size(); i++) {
			this.allQuestions.get(i).resetKnownFlag();
			this.allQuestions.get(i).tagged(false);
		}
	}

}
