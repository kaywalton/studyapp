package delmas.walton.studyapp;
import java.util.ArrayList;
/**
 * Creates a multiple choice question and prepares a formatted string with 
 * the question and choice options. Also includes the correct answer for verifying.
 * @author Kayla Walton
 * Created by:  Kayla on 9/15
 * Edited by:           on 
 */
public class ChoiceQuestion extends Question
{
   private ArrayList<String> choices = new ArrayList<String>() ;
   private String correctChoice;
   private boolean shuffleable;

   /**
    * construct a question with choices object
    */
   
   public ChoiceQuestion(String question, String rightAnswer, boolean isShuffleable) {
   super(question, rightAnswer);
   this.shuffleable = isShuffleable;
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
    */
   public String displayQuestionChoices()
   {
      String choiceDisplays = "";
      for (int i = 0; i < choices.size(); i++) 
      {
      choiceDisplays = choiceDisplays + i + ": " + choices.get(i) + "\n";
      }
      return choiceDisplays;
   }