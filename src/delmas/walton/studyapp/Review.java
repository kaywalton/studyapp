package delmas.walton.studyapp;

import java.io.*;
/**
 * Initializes a review session from a specified bank of questions
 * 
 * @author Kayla Walton
 *Created on: 9/21
 *Edited: 10-08-17		by Isabelle Delmas			Reason: changed Review(String) to Review(QuestionBank) for consistency
 */
public class Review {
   private QuestionBank bank;
   
   /**
    * Creates a review session for a given bankName
    * @param bankName needs the name of the quiz bank to be used
    */
   public Review(QuestionBank newBank)
   {
      this.bank = newBank;
   }
   
   /**
    * Starts a review session from loaded quiz bank
    */
   public void startReview()
   {
      System.out.println("Sorry, come backe later");
   }
}
