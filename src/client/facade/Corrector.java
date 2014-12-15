package client.facade;

import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.Scanner;
import java.util.Collections;

public class Corrector implements SpellCorrector
{
	public MyTrie Dictionary;
	public boolean implemented;
	
	public Corrector()
	{
		Dictionary = new MyTrie();
		implemented = false;
	}
	@Override
	public void useDictionary(String dictionaryFileName) throws IOException 
	{
		implemented = true;
		Scanner readIn = new Scanner(dictionaryFileName);
		String word;
		word = readIn.nextLine();
		List<String> newList = Arrays.asList(word.split(",",-1));
		for(String s : newList)
		{
			//we now allow for " " and "-"
			if(s.matches("[-a-zA-Z ]+"));
			{
				s = s.toLowerCase();
				Dictionary.add(s);
			}
		}
		readIn.close();
	}

	@Override
	public ArrayList<String> suggestSimilarWord(String inputWord)throws NoSimilarWordFoundException
	{
		if(!inputWord.matches("[-a-zA-Z ]+"))
		{
			throw new NoSimilarWordFoundException();
		}
		inputWord = inputWord.toLowerCase();
		ArrayList<String> edit1 = new ArrayList<String>();
		edit1.add(inputWord);
		if(Dictionary.find(inputWord) != null)
		{
			return edit1;
		}
		
		//edit distance one
		edit(edit1);
		
		//edit distance two
		edit(edit1);
		
		ArrayList<String> result = new ArrayList<String>();
		
		for(String s : edit1)
		{
			if(Dictionary.find(s) != null)
			{
				if(!result.contains(s))
				{
					result.add(s);
				}
			}
		}
		
		Collections.sort(result);
		
		return result;
	}
	
	private void edit(ArrayList<String> edits)
	{
		int k = edits.size();
		for(int i = 0; i < k; i++)
		{
			deletion(edits.get(i), edits);
			transpostion(edits.get(i), edits);
			alteration(edits.get(i), edits);
			addition(edits.get(i), edits);
		}
	}
	
	private void deletion(String word, ArrayList<String> edits)
	{
		for(int i = 0; i < word.length(); i++)
		{
			StringBuilder sb = new StringBuilder(word);
			sb.deleteCharAt(i);
			edits.add(sb.toString());
		}
	}
	
	private void transpostion(String word, ArrayList<String> edits)
	{
		for(int i = 0; i < word.length() - 1; i++)
		{
			StringBuilder sb = new StringBuilder(word);
			char c = sb.charAt(i);
			sb.deleteCharAt(i);
			sb.insert(i + 1, c);
			edits.add(sb.toString());
		}
	}
	
	private void alteration(String word, ArrayList<String> edits)
	{
		for(int i = 0; i < 26; i++)
		{
			char c = 'a';
			c += i;
			for(int j = 0; j < word.length(); j++)
			{
				StringBuilder sb = new StringBuilder(word);
				sb.deleteCharAt(j);
				sb.insert(j, c);
				edits.add(sb.toString());
			}
		}
	}
	
	private void addition(String word, ArrayList<String> edits)
	{
		for(int i = 0; i < 26; i++)
		{
			char c = 'a';
			c += i;
			for(int j = 0; j <= word.length(); j++)
			{
				StringBuilder sb = new StringBuilder(word);
				sb.insert(j, c);
				edits.add(sb.toString());
			}
		}
	}
	
	@SuppressWarnings("unused")
	private class Word
	{
		public String word;
		public int count;
		Word(String w, int c)
		{
			word = w;
			count = c;
		}
	}

}