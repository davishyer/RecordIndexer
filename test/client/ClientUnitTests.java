package client;

import org.junit.*;

import server.Server;
import static org.junit.Assert.*;

public class ClientUnitTests 
{
	
	@SuppressWarnings("static-access")
	@Before
	public void setup() 
	{
		Server server = new Server();
		server.main(null);
	}
	
	@After
	public void teardown() 
	{
		
	}
	
	@Test
	public void test_1() 
	{		
		assertEquals("OK", "OK");
		assertTrue(true);
		assertFalse(false);
	}

	public static void main(String[] args) 
	{

		String[] testClasses = new String[] 
		{
				"client.ClientUnitTests",
				"client.ValidateUserUnitTest",
				"client.GetFieldsUnitTest",
				"client.DownloadBatchUnitTest",
				"client.GetProjectsUnitTest",
				"client.GetSampleImageUnitTest",
				"client.SearchUnitTest",
				"client.SubmitBatchUnitTest",
				"client.DownloadFileUnitTest",
				"client.QualityCheckUnitTests"
		};

		org.junit.runner.JUnitCore.main(testClasses);
	}
}

