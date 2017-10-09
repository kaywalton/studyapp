package delmas.walton.studyapp;
import java.util.Scanner;

/**
 * Creates a test using a given quiz bank.
 * Keeps score of total questions right.
 * @author Kayla Walton
 * Updated on 10-08-17 by Isabelle Delmas		Reason: Changed Test(String) to Test(QuestionBank) to be constant with QuizBankMenu
 * 														Changed name of the class
 */
public class Quiz {
   private int sizeOfTest;
   private int score;
   private QuestionBank bank;
   /**
    * Takes a given bank and creates a test
    * @param bankName bank to be used for test
    */
   public Quiz(QuestionBank newBank)
   {
      this.bank = newBank;
      this.score = 0;
      this.sizeOfTest = 0;
   }
   
   /**
    * Start the test, set the size of test, and updates score.
    */
   public void startTest()
   {
      this.setSizeOfTest();
      
      
   } //end startTest()
   
   /**
    * Take keyboard input to get the desired size of test.
    */
   private void setSizeOfTest()
   {
      //open scanner to grab test size
      Scanner scan = new Scanner(System.in);
      System.out.print("How many questions will this test have? ");
      boolean checkFlag = true;
      while (checkFlag)
      {
         if (scan.hasNextInt())
         {
            sizeOfTest = scan.nextInt();
            scan.nextLine();
            checkFlag = false;
         }
         else
         {
            scan.nextLine();
            System.err.println("Input wasn't an integer");
         }
       } //end while loop
      //scan.close(); <-- [by Isabelle] do not close System.in, I do not know why but is bugs when you reopen it later in the same program
   }
   /**
    * private method used by startTest() to increase score
    */
   private boolean increaseScore()
   {
      //person answered correctly. increase the score.
	   					// Comment by Isabelle on 10-08-17
      score =+ 1;		//<-- if we choose to have a return value to signify success, we need to have some kind of check
      					// like check if the score will not go above the max int
      					// or if we do not check anything we do not need a return value
      return true;
   }
}
