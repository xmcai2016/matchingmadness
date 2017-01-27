import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;
/*Caroline's Tests 
 * CIS 120 Final Project
 * 4/26/2016
 */

public class HighScoresTest {

	@Test
	public void testUpdateHighScores() throws IOException {
		try {
			new HighScores();
		} 
		catch (Exception ex) {
			System.out.println("error in generating HighScores");
		};
		
		HighScores.updateHighscores(1, "Caroline the Great");

		System.out.println(HighScores.getHighscores());
		String actual = HighScores.getHighscores();
		String expected = "<html> <p align='CENTER'> <font color='YELLOW'><font size='6'>  <b> TOP "
				+ "TEN HIGH SCORES<font color='WHITE'><font size='5'<i><br> #1: Caroline the Great "
				+ "-- 1 Seconds<br> #2: Caroline the Great -- 1 Seconds<br> #3: Caroline the Great "
				+ "-- 1 Seconds<br> #4: Caroline the Great -- 1 Seconds<br> #5: Caroline the Great "
				+ "-- 1 Seconds<br> #6: Caroline the Great -- 1 Seconds<br> #7: Caroline the Great "
				+ "-- 1 Seconds<br> #8: Caroline the Great -- 1 Seconds<br> #9: Caroline the Great "
				+ "-- 1 Seconds<br> #10: Caroline the Great -- 1 Seconds<br> #11: Caroline the Great"
				+ " -- 1 Seconds";
		assertEquals(expected, actual);
	}
}
