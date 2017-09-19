package delmas.walton.studyapp;
import java.util.ArrayList;
public class ChoiceQuestion extends Question
{
   private ArrayList<String> choices = new ArrayList<String>() ;
   private String correctChoice;

   /**
    * construct a question with choices object
    */
   
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
    */
   public void displayQuestionChoices()
   {
      for (int i = 0; i < choices.size(); i++) 
      {
      System.out.print(i + ": " + choices.get(i) + "\n");
      }
   }
}
