package delmas.walton.studyapp;
import java.util.ArrayList;
/**
 * Creates a multiple choice question and prepares a formatted string with 
 * the question and choice options. Also includes the correct answer for verifying.
 * @author Kayla Walton
 *
 */
public class ChoiceQuestion extends Question
{
   private ArrayList<String> choices = new ArrayList<String>() ;
   private String correctChoice;
   private boolean canBeShuffled;

   /**
    * construct a question with choices object
    */
   
   
   /**
    * Sets the shuffle status on a question, allowing for the choices to be shuffled.
    * @param yesORno
    */
   public void setShuffleStatus(boolean yesORno)
   {
      canBeShuffled = yesORno;
   }
   /**
    * Returns the status of a question: can it be shuffled or not.
    * @return true if it can. false if it can't.
    */
   public boolean getShuffleStatus()
   {
      return canBeShuffled;
   }
   /**
    * Stores a choice and whether it is correct
    * @param newChoice choice to be added
    * @param truthValue is it correct?
    */
   public void addChoice(String newChoice, boolean truthValue)
   {
      if (truthValue)   //if it is the correct value, then mark it and add to list
      {
         correctChoice = newChoice;
         choices.add(newChoice);
      }
      else
      {
         choices.add(newChoice);
      }
   }
   /**
    * Displays the question and all added choices
    * @return a formatted string with the choice options
    */
   public String displayQuestion()
   {
      String choiceString = "";
      for (int i = 0; i < choices.size(); i++) 
      {
      choiceString = choiceString + i + ": " + choices.get(i) + "\n";
      }
      return choiceString;
   }
}
