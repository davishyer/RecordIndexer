package client.facade;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import client.facade.SpellCorrector.NoSimilarWordFoundException;


public class Driver 
{
	public static void main(String[] args) throws NoSimilarWordFoundException, IOException
	{
		String file;
		if(args.length > 0)
		{
			file = args[0];
		}
		else
		{
			String filePrefix = new File("").getAbsolutePath();
			file = filePrefix + "/Records/knowndata/ethnicities.txt";
		}
		Corrector c = new Corrector();
		c.useDictionary(file);
//		System.out.println(c.Dictionary.toString());
		String input;
		if(args.length > 1)
		{
			input = args[1];
		}
		else
		{
			input = "American Indian";
		}
		System.out.println("\nSearching for: " + input);
		ArrayList<String> results = c.suggestSimilarWord(input);
		for(String s : results)
		{
			System.out.println(s);
		}
	}
}