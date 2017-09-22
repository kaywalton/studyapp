package delmas.walton.studyapp;
import java.util.Scanner;

/**
 * Creates a test using a given quiz bank.
 * Keeps score of total questions right.
 * @author Kayla Walton
 *
 */
public class Test {
   private int sizeOfTest;
   private int score;
   private String fileName;
   /**
    * Takes a given bank and creates a test
    * @param bankName bank to be used for test
    */
   public Test(String bankName)
   {
      this.fileName = bankName;
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
      scan.close();
   }
   /**
    * private method used by startTest() to increase score
    */
   private boolean increaseScore()
   {
      //person answered correctly. increase the score.
      score =+ 1;
      return true;
   }
}
