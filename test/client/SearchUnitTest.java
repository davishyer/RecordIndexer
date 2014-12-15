package client;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.*;

import server.database.*;
import shared.communication.*;
import shared.model.*;
import client.communication.ClientCommunicator;

public class SearchUnitTest
{
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		// Load database driver	
		Database.initialize();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception 
	{
		return;
	}
		
	private Database db;
	private UserDAO dbUser;
	private RecordDAO dbRecord;
	private ClientCommunicator cCom;

	@Before
	public void setUp() throws Exception 
	{	
		db = new Database();		
		db.startTransaction();
		
		ArrayList<User> users = db.getUserDAO().getAll();
		for(User u : users)
		{
			db.getUserDAO().delete(u);
		}
		
		ArrayList<Record> records = db.getRecordDAO().getAll();
		
		for (Record r : records) 
		{
			db.getRecordDAO().delete(r);
		}
		
		db.endTransaction(true);

		// Prepare database for test case	
		db = new Database();
		dbUser = db.getUserDAO();
		dbRecord = db.getRecordDAO();
		cCom = new ClientCommunicator();
	}

	@After
	public void tearDown() throws Exception 
	{
		db = null;
		dbUser = null;
	}
	
	@Test
	public void testProjects() throws DatabaseException 
	{
		db.startTransaction();
		User validate = new User(new Credentials("validate", "validate"), new UserInfo("Vali", "Date", "validate@validate.com"));
		dbUser.add(validate);
		Record record = new Record(1, 1, "davis", 1);
		dbRecord.add(record);
		db.endTransaction(true);
		ArrayList<Integer> fieldIDs = new ArrayList<Integer>();
		fieldIDs.add(1);
		ArrayList<String> values = new ArrayList<String>();
		values.add("davis");	
		ArrayList<Integer> badFieldIDs = new ArrayList<Integer>();
		badFieldIDs.add(100);
		ArrayList<String> badValues = new ArrayList<String>();
		values.add("badInput");
		
		//valid user
		SearchResult result = cCom.search(new SearchParameters("validate", "validate", fieldIDs, values));
		assertEquals(result.getRecords().size(), 1);
		//invalid fieldID
		SearchResult result2 = cCom.search(new SearchParameters("validate", "validate", badFieldIDs, values));
		assertEquals(result2.getLinks().size(), 0);
		//invalid values
		SearchResult result3 = cCom.search(new SearchParameters("validate", "validate", fieldIDs, badValues));
		assertEquals(result3.getRecords().size(), 0);
	}
}