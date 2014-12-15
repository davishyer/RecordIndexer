package client;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import server.database.DatabaseException;
import client.facade.Corrector;

public class QualityCheckUnitTests 
{
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception 
	{
		return;
	}
		
	private Corrector corrector;

	@Before
	public void setUp() throws Exception 
	{	
		this.corrector = new Corrector();
	}

	@After
	public void tearDown() throws Exception 
	{
		corrector = null;
	}
	
	@Test
	public void testCorrector() throws DatabaseException 
	{
		String dictionary = "BYU,USU,UVU,FSU,BSU,USC,SUU";
		ArrayList<String> suggestions = null;
		try
		{
			corrector.useDictionary(dictionary);
			
			//edit distance one
			suggestions = corrector.suggestSimilarWord("BY");
			assertTrue(suggestions.contains("byu"));
			
			//edit distance two
			suggestions = corrector.suggestSimilarWord("B");
			assertTrue(suggestions.contains("byu"));
			
			//valid word
			suggestions = corrector.suggestSimilarWord("USU");
			assertTrue(suggestions.contains("usu"));
			
			//invalid word
			suggestions = corrector.suggestSimilarWord("superlongword");
			assertEquals(0, suggestions.size());
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
