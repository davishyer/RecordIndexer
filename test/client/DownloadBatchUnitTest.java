package client;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import server.database.BatchDAO;
import server.database.Database;
import server.database.DatabaseException;
import server.database.UserDAO;
import shared.communication.DownloadBatchParameters;
import shared.communication.DownloadBatchResult;
import shared.model.Batch;
import shared.model.Credentials;
import shared.model.User;
import shared.model.UserInfo;
import client.communication.ClientCommunicator;

public class DownloadBatchUnitTest 
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
	private BatchDAO dbBatch;
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
		
		ArrayList<Batch> batches = db.getBatchDAO().getAll();
		
		for (Batch b : batches) 
		{
			db.getBatchDAO().delete(b);
		}
		
		db.endTransaction(true);

		// Prepare database for test case	
		db = new Database();
		dbUser = db.getUserDAO();
		dbBatch = db.getBatchDAO();
		cCom = new ClientCommunicator();
	}

	@After
	public void tearDown() throws Exception 
	{
		db = null;
		dbUser = null;
	}
	
	@Test
	public void testDownloadBatch() throws DatabaseException 
	{
		db.startTransaction();
		User validate = new User(new Credentials("validate", "validate"), new UserInfo("Vali", "Date", "validate@validate.com"));
		dbUser.add(validate);
		Batch batch = new Batch("TempFilePath", 1, 0);
		dbBatch.add(batch);
		db.endTransaction(true);
		//invalid user
		DownloadBatchResult result2 = cCom.downloadBatch(new DownloadBatchParameters("invalid", "validate", 1));
		assertEquals(false, result2.isValidUser());
		//invalid projectID
		DownloadBatchResult result3 = cCom.downloadBatch(new DownloadBatchParameters("validate", "validate", 100));
		assertEquals(null, result3.getFields());
	}
}
