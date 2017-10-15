package delmas.walton.studyapp;

import org.junit.Test;
import static org.junit.Assert.*;



public class ChoiceQuestionTest
{

   @Test
   public void test()
   {
      //create a choice question
      ChoiceQuestion firstQ = new ChoiceQuestion("Who is the Dark Knight?", "Batman", true);
      //test to see if display is not null
      assertNotNull(firstQ.displayQuestion());
      //check if first correct answer stored properly
      assertEquals(firstQ.checkAnswer("Batman"), true);
      //second test for multiple choice insertion
      
      ChoiceQuestion secondQ = new ChoiceQuestion("Who is Wonder Woman?", "Gal Gadot", true);
      
      //store wrong answers
      secondQ.addChoice("Vera Wang", false);
      secondQ.addChoice("Barbara Gordon", false);
      secondQ.addChoice("Sarah Palin", false);
      //verify answers are in list
      assertEquals("Choice check", "Vera Wang", secondQ.getChoices().get(1));
      assertEquals("Second choice", "Barbara Gordon", secondQ.getChoices().get(2));
      assertEquals("Third choice", "Sarah Palin", secondQ.getChoices().get(3));
      assertNotNull(secondQ.displayQuestion());
     
   }
  

}
