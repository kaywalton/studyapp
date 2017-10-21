package delmas.walton.studyapp;

import java.util.Scanner;

//import org.apache.commons.lang3.time.StopWatch;
/**
 * Initializes a review session from a specified bank of questions
 * 
 * @author Kayla Walton
 *Created on: 9/21
 *Edited: 10-08-17		by Isabelle Delmas			Reason: changed Review(String) to Review(QuestionBank) for consistency
 */
public class Review {
	public static final String GOOD_JOB = "This is correct. Good job!";
	public static final String INNCORRECT = "Not quite. The correct answer is: ";
	public static final String EMPTY_BANK = "There is no question to display.";
	public static final String CORRECT = "Question you got right: ";
	public static final String TOTAL_QUESTIONS = "Total question answered: ";
	private QuestionBank bank;
	private long maxTime;
	private int numberRight;
	private int numberQuestions;

	/**
	 * Creates a review session for a given bankName
	 * @param bankName needs the name of the quiz bank to be used
	 */
	public Review(QuestionBank newBank, long newMaxTime)
	{
		this.bank = newBank;
		this.maxTime = newMaxTime;
		this.numberQuestions = 0;
		this.numberRight = 0;
	}

	/**
	 * Starts a review session from loaded quiz bank
	 */
	public void startReview()
	{
		/*
		 @SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		int answer = -1;
		boolean right = false;
		ChoiceQuestion question = null;

		// Initiate timer
		StopWatch timer = StopWatch.createStarted();

		if(this.bank.numberOfQuestions() != 0){
			// Prompts questions until the time is up
			while(timer.getTime() < this.maxTime){
				// Prompt user with a question
				question = this.bank.getRandomQuestion();
				question.shuffle();
				while(answer == -1){
					System.out.println(question.displayQuestion());

					// Get answer
					if(in.hasNextInt()){
						answer = in.nextInt();
						
						// Check answer
						right = question.checkAnswer(answer);

						// Print feedback.
						if(right){
							System.out.println(Review.GOOD_JOB);
						} else{
							System.out.println(Review.INNCORRECT + question.getAnswer());
						}

					} else {
						System.out.println("Enter an interger.");
						in.nextLine();
					}
				}

				// Update count of questions
				this.numberQuestions++;
				if(right){this.numberRight++;}
				answer = -1;	// Makes sure the previous while loop will start for the next question 
			}

			// Print feedback
			System.out.println(Review.CORRECT + this.numberRight);
			System.out.println(Review.TOTAL_QUESTIONS + this.numberQuestions);
		} else{
			System.out.println(Review.EMPTY_BANK);
		}

	}*/
	}
}
