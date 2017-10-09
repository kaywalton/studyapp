package delmas.walton.studyapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main class of the StudyApp application
 * Takes care of all the input/output and communication with the user
 * @author Isabelle Delmas
 * creation: October 6 2017
 * updates : October 7 2017 	by: Isabelle Delmas		Reason : continued implementation
 * updates : October 8 2017 	by: Isabelle Delmas		Reason : continued implementation
 *
 */

@SuppressWarnings("resource")
public class QuizBankMenu {
	private ListofBanks list;
	private final int QUIZ_LENGTH = 20;
	private String fileName;
	private final String HEADER = "Main Menu";
	private final String[] OPTIONS = {"Start Quiz", 
			"Start Review",
			"Display Statistics",
			"Create a new bank of questions", 
			"Modify an existing bank of questions",
			"Erase a bank of questions",
			"Display all lists of banks", 
			"Delete all the banks"};
	private final String[] UPDATE_OPTIONS = {"Add a question",
			"Remove a question",
			"Remove all questions",
			"Print all questions"};
	private final String WRONG_EXIT = "quit";
	private final String CHOICE_PROMPT = "Your choice: ";
	private final int EXIT_VALUE = 0;
	private final String EXIT = EXIT_VALUE + ") Exit";
	private final String NAME_PROMPT = "Name of the bank of question: ";
	private final String BANK_NOT_FOUND = "This bank of question does not exits";
	private final String QUESTION_PROMPT = "Question: ";
	private final String SHUFFELABLE_PROMPT = "Can the question be shuffeled? ";
	private final String ANSWER_PROMPT= "Right answer: ";
	private final String ANSWER_PROMPT2= "Possibe answer (" + this.WRONG_EXIT + "): ";
	private final String WRONG_PROMPT = "Wrong answer (" + this.WRONG_EXIT + " to exit): ";
	private final String QUESTION_NOT_FOUND = "Question not found";
	private final String QUESTION_REMOVED = "Question succeffuly removed";
	private final String RIGHT_WRONG = "Is the the (r)ight or the (w)rong answer? ";

	/**
	 * Constructor
	 * @param newFileName : file of the name storing the list of bank
	 */
	public QuizBankMenu(String newFileName) {
		this.fileName = newFileName + ".dat";
		this.list = new ListofBanks();
		File file = new File(this.fileName);

		// Check if the file can be open
		if(file.exists()) {
			try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file)))
			{
				this.list = (ListofBanks) in.readObject();
			}// If the file was corrupted, does not contain the right information, or could not be open, create a new file 
			catch (FileNotFoundException e) {
				this.list = new ListofBanks();
			} catch (IOException e) {
				this.list = new ListofBanks();
			} catch (ClassNotFoundException e) {
				this.list = new ListofBanks();
			} 
		// If the file does not exists yet.
		} else {
			this.list = new ListofBanks();
		}
	}
	/**
	 * Display the header and the menu to the user
	 */
	private void displayMenu() {
		
		// Display the header
		System.out.println(this.HEADER);
		for(int i=0; i < this.HEADER.length(); i++) {
			System.out.print("-");
		}
		System.out.println("");
		
		// Display the Menu
		for(int i = 0; i < this.OPTIONS.length; i++) {
			System.out.println(i+1 + ") " + this.OPTIONS[i]);
		}
	}
	
	/**
	 * Run the application. To be called by main after initiating the QuizBankMenu object
	 */
	public void runApplication() {
		int choice = -1;
		String bankName = "";
		Scanner in = new Scanner(System.in);		

		
		while(choice != 0) {
			this.displayMenu();
			choice = getUserChoice(this.OPTIONS.length);
			
			switch (choice) {
			// The user choose to start a quiz
			case 1:
				this.startQuiz();
				break;
			// The user choose to start a review session
			case 2:
				this.startReview();
				break;
			// The user choose to display some statistics
			case 3:
				this.displayStatistics();
				break;
			// The user choose to create a new bank
			case 4:
				this.createNewBank();
				break;
			// The user choose to update an existing bank
			case 5:
				System.out.print(this.NAME_PROMPT);
				bankName = in.nextLine();
				this.updateBank(bankName);
				break;
			// The user choose to display all the banks
			case 7:
				System.out.println(this.list.toString());
				in.nextLine();
				break;
			// The user choose to delete all the banks
			case 8:
				this.deleteAllBanks();
				break;
			}		
		}

		
	}
	
	/**
	 * Start a quiz
	 * TBD
	 */
	private void startQuiz() {
		Scanner in = new Scanner(System.in);
		// Ask the user for a bank to use
		System.out.print(this.NAME_PROMPT);
		QuestionBank bank = this.list.getBank(in.nextLine());
		// Check if the bank exit
		if (bank == null) {
			System.out.println(this.BANK_NOT_FOUND);
		// If the bank exists, start a quiz
		} else {
			Quiz quiz = new Quiz(bank);
			quiz.startTest();
		}		
	}
	
	/**
	 * Start a review session
	 * TBD
	 */
	private void startReview() {
		Scanner in = new Scanner(System.in);
		System.out.println("Please, come back later.");
		in.nextLine();
	}
	
	/**
	 * Display statistics
	 * TBD
	 */
	private void displayStatistics() {
		Scanner in = new Scanner(System.in);
		System.out.println("Please, come back later.");
		in.nextLine();
	}
	
	/**
	 * Ask the user to input an int between EXIT_VALUE (0) and max
	 * @param max : max number of input
	 * @return the choice of the user
	 */
	private int getUserChoice(int max) {
		Scanner in = new Scanner(System.in);
		System.out.println(this.EXIT);
		int userChoice = -1;
		while(userChoice < 0 || userChoice > max) {
			System.out.print(this.CHOICE_PROMPT);
			if(in.hasNextInt()) {
				userChoice = in.nextInt();
			}
			in.nextLine();
		}
		return userChoice;
	}
	/**
	 * Add a new bank to the list of bank
	 * @param bankName : the name of the new bank
	 * @return : true is the bank was successfully added
	 */
	private void createNewBank() {
		String bankName = "";
		Scanner in = new Scanner(System.in);		
		// Ask the user for the name of the new bank of questions
		System.out.print(this.NAME_PROMPT);
		bankName = in.nextLine();
		
		// Check if the bank does not exists yet
		if(this.list.getBank(bankName) == null) {
			// Create the bank
			QuestionBank newBank = new QuestionBank(bankName);
			// Add the bank to the list
			this.list.addBank(newBank);
			// Gives the user the option to modify the bank to add the questions
			this.updateBank(bankName);
			// The bank was successfully created
		} else {
			System.out.println(this.BANK_NOT_FOUND);
		}
		
		// Updates the bank in the hard drive file
		this.updateFile();
}
	
	/**
	 * Display a menu and offer the user to modify a bank (add/remove questions)
	 * @param bankName
	 */
	private void updateBank(String bankName) {
		Scanner in = new Scanner(System.in);

		QuestionBank bank = this.list.getBank(bankName);
		int choice = -1;
		// Check if the bank exit
		if (bank == null) {
			System.out.println(this.BANK_NOT_FOUND);
		// If the bank exists, display another menu
		} else {
			while(choice != 0) {
				this.displayUpdateBankMenu(bankName);
				choice = this.getUserChoice(this.UPDATE_OPTIONS.length);
				switch (choice) {
				// If the user wants to add a question
				case 1:
					this.addQuestionToBank(bank);
					break;
				// If the user wants to delete a question
				case 2:
					this.deleteQuestion(bank);
					in.nextLine();
					break;
				// If the user wants to delete all the questions
				case 3:
					this.deleteAllQuestions(bank);
					in.nextLine();
					break;
				case 4:
					System.out.println(bank.displayQuestions());
					in.nextLine();
					break;
				}	
			}
		}
	}
	
	/**
	 * Delete all the questions in a given bank of question
	 * @param bank : the bank of question to delete the questions from
	 */
	private void deleteAllQuestions(QuestionBank bank) {
		Scanner in = new Scanner(System.in);
		String yn = "";
		while ( !yn.equals("Y") && !yn.equals("N")) {
			System.out.println("Are you sure you want to delete all the questions? This action cannot be undone.");
			yn = in.nextLine().substring(0, 1).toUpperCase();
		}
		if (yn.equals("Y")) {
			bank.removeAllQuestions();
		}
		
		//Update the file
		this.updateFile();
	}
	
	/**
	* Delete one question from a specific bank of question.
	* Ask the user what question to remove
	 * @param bank : bank to remove the question form
	 */
	private void deleteQuestion(QuestionBank bank) {
		Scanner in = new Scanner(System.in);
		
		// Get the question to remove
		System.out.print(this.QUESTION_PROMPT);
		if (bank.removeQuestion(in.nextLine())) {
			System.out.println(this.QUESTION_REMOVED);
		} else {
			System.out.println(this.QUESTION_NOT_FOUND);
		}
		
		//Update the file
		this.updateFile();
	}
	
	
	/**
	 * Add a question to a specific bank
	 * prompt the user for all the needed information
	 * @param bank : the bank to add a question to
	 */
	private void addQuestionToBank(QuestionBank bank) {
		Scanner in = new Scanner(System.in);
		ChoiceQuestion question = null;
		String prompt = "";
		boolean shufflable;
		String answer = "";
		String temp = "";
		ArrayList<String> wrong = new ArrayList<String>();
		
		// Get the question to add
		System.out.print(this.QUESTION_PROMPT);
		prompt = in.nextLine();
		
		// Can be shuffled or not
		String yn = "";
		while ( !yn.equals("Y") && !yn.equals("N")) {
			System.out.print(this.SHUFFELABLE_PROMPT);
			yn = in.nextLine().substring(0, 1).toUpperCase();
		}
		shufflable = yn.equals("Y");
		
		// If the question can be shuffled
		if(shufflable) {
			// Get the right answer
			System.out.print(this.ANSWER_PROMPT);
			answer = in.nextLine();
			
			// get the wrong answers
			while(!temp.toLowerCase().equals("quit")) {
				System.out.print(this.WRONG_PROMPT);
				temp = in.nextLine();
				wrong.add(temp);
			}
			
			// Set up the question
			question = new ChoiceQuestion(prompt, answer, shufflable);
			for(int i = 0; i < wrong.size(); i++) {
				question.addChoice(wrong.get(i),  false);
			}
		} else {
			// If the question is not shufflable
			question = new ChoiceQuestion(prompt, shufflable);
			while (true) {
				// Get an answer
				System.out.print(this.ANSWER_PROMPT2);
				answer = in.nextLine();
				if (answer.toUpperCase().equals(this.WRONG_EXIT.toUpperCase())) {break;}
				// Is it the right answer?
				String wr = "";
				while ( !wr.equals("W") && !wr.equals("R")) {
					System.out.print(this.RIGHT_WRONG);
					wr = in.nextLine().substring(0, 1).toUpperCase();
				}
				boolean truth = wr.equals("R");
				question.addChoice(answer,  truth);	
			}
		}
		
		// add the question to the bank and update file
		bank.addQuestion(question);
		this.updateFile();		
	}
	
	/**
	 * Display the menu with the option to modify a bank of questions
	 * @param bankName : the name of the bank to display
	 */
	private void displayUpdateBankMenu(String bankName) {
		// Print the header
		System.out.println(this.list.getBank(bankName).getName().toUpperCase());
		for(int i=0; i < this.HEADER.length(); i++) {
			System.out.print("-");
		}
		System.out.println("");
		
		// Display the Menu
		for(int i = 0; i < this.UPDATE_OPTIONS.length; i++) {
			System.out.println(i+1 + ") " + this.UPDATE_OPTIONS[i]);
		}
	}
	
	
	/**
	 * Erases all the banks of questions
	 */
	private void deleteAllBanks() {
		Scanner in = new Scanner(System.in);
		String yn = "";
		while ( !yn.equals("Y") && !yn.equals("N")) {
			System.out.println("Are you sure you want to delete all the bank of questions? This action cannot be undone.");
			yn = in.nextLine().substring(0, 1).toUpperCase();
		}
		if (yn.equals("Y")) {
			this.list = new ListofBanks();
			this.updateFile();
			System.out.println("Allright, lets get ride of everything!");
		}
		
		//Update the file
		this.updateFile();

	}
	
	/**
	 * Updates the file storing the list of banks
	 * Display an error if the file could not be updated
	 */
	private void updateFile() {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(this.fileName));
			out.writeObject(this.list);
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Could not update file, your work will not be saved");
		}
	}
}
