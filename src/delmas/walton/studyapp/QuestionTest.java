package delmas.walton.studyapp;

import org.junit.Assert;
import org.junit.Test;

public class QuestionTest {

	@Test
	public void test() {
		// Create a question using default constructor
		Question q1 = new Question();
		q1.setText("Who shot first? ");
		q1.setAnswer("Han Solo");
		// Check a wrong answer
		Assert.assertFalse(q1.checkAnswer("The other guy"));
		// Check a right answer
		Assert.assertTrue(q1.checkAnswer("Han Solo"));
		// Check if answer is case sensitive
		Assert.assertTrue(q1.checkAnswer("HAN SOLO"));
		
		// Check the Image path accessor and mutator
		Assert.assertEquals("",  q1.getImagePath());
		q1.setImagePath("testPath");
		Assert.assertEquals(q1.getImagePath(), "testPath");
		// Check the tag flag
		Assert.assertFalse(q1.isTagged());
		q1.tagged(true);
		Assert.assertTrue(q1.isTagged());
		
		
		// Create a question using default constructor
		Question q2 = new Question("Who shot first? ", "Han Solo");
		// Check a wrong answer
		Assert.assertFalse(q2.checkAnswer("The other guy"));
		// Check a right answer
		Assert.assertTrue(q2.checkAnswer("Han Solo"));
		// Check if answer is case sensitive
		Assert.assertTrue(q2.checkAnswer("HAN SOLO"));
		
		//Test Display method
		Assert.assertEquals("Who shot first? ", q2.displayQuestion());
		Assert.assertEquals("Who shot first? ", q1.displayQuestion());
		
		// Test getKnownFlag method
		Question q3 = new Question("Who shot first? ", "Han Solo");
		Assert.assertFalse(q3.getKnownFlag());
		Assert.assertTrue(q3.checkAnswer("Han Solo"));
		Assert.assertFalse(q3.getKnownFlag());
		Assert.assertTrue(q3.checkAnswer("Han Solo"));
		Assert.assertFalse(q3.getKnownFlag());
		Assert.assertTrue(q3.checkAnswer("Han Solo"));
		Assert.assertTrue(q3.getKnownFlag());
		Assert.assertTrue(q3.resetKnownFlag());
		Assert.assertFalse(q3.getKnownFlag());
		Assert.assertTrue(q3.checkAnswer("Han Solo"));
		Assert.assertFalse(q3.getKnownFlag());
		Assert.assertTrue(q3.checkAnswer("Han Solo"));
		Assert.assertFalse(q3.getKnownFlag());
		Assert.assertTrue(q3.checkAnswer("Han Solo"));
		Assert.assertTrue(q3.getKnownFlag());



	}

}
