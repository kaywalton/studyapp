package delmas.walton.studyapp;
/**
 * Class holding the content of a Bank of Question.
 * @author Isabelle Delmas
 * Created on: 09-21-2017
 * Edited on:				by
 *
 */

import java.time.Instant;
import java.util.ArrayList;
import java.util.Random;

public class QuestionBank {
	private ArrayList<ChoiceQuestion> allQuestions;
	
	/**
	 * Constructor with the name of the file
	 * If the file does not exists, creates it
	 * @param newFileName the name of the file where the questions are stored
	 */

	 /**
	  * Adds a new question to the list of question saved in the bank
	  * @param rightAnswer : the right answer to the question
	  * @param possibleAnswers : all the possible answers
	  * @param question : the prompt to the user
	  * @param isShuffleable : can the possible answers the shuffled
	  * @return : true if the question was properly added
	  */
	public void addQuestion(String rightAnswer, ArrayList<String> possibleAnswers, String question, boolean isShuffleable ) {
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
	public Question getRandomQuestion() {
		final double MASTERED_RATE = 25; 	// 25 out of 100;
		boolean found = false;
		ChoiceQuestion question = null;
		
		// Repeat until a question is found
		while (!found) {
			// Get a random question
			Random randomGenerator = new Random(Instant.now().getNano());
			int randomIndex =randomGenerator.nextInt(this.allQuestions.size() -1);
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
	

}
