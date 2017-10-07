package delmas.walton.studyapp;

import org.junit.Assert;
import org.junit.Test;

public class QuestionBankTest {

	@Test
	public void test() {
		QuestionBank bank = new QuestionBank();
		ChoiceQuestion question = new ChoiceQuestion("How do you spell 2? ", "two", true);
		question.addChoice("three",  false);
		question.addChoice("too", false);
		bank.addQuestion(question);
		Assert.assertEquals(1,  bank.numberOfQuestions());
		bank.removeQuestion("How do you spell 2? ");
		Assert.assertEquals(0,  bank.numberOfQuestions());
		bank.addQuestion(question);
		ChoiceQuestion question2 = bank.getRandomQuestion();
		Assert.assertTrue(question2.displayQuestion().equals("How do you spell 2? "));
		Assert.assertFalse(question2.displayQuestion().equals(""));
		question2 = new ChoiceQuestion("question2", "2", true);
		ChoiceQuestion question3 = new ChoiceQuestion("question3", "3", true);
		bank.addQuestion(question2);
		bank.addQuestion(question3);
		bank.getRandomQuestion();
		
	}

}
