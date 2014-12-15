package server.database;

import java.sql.*;
import java.util.ArrayList;

import shared.model.*;

public class ProjectDAO 
{
	private Database db;
	public ProjectDAO(Database db)
	{
		this.db = db;
	}
	
	public ArrayList<Project> getAll()
	{
		ArrayList<Project> projects = new ArrayList<Project>();
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet resultset = null;
		
		try
		{
			connection = db.getConnection();
			String selectsql = "SELECT * from Project";
			pstmt = connection.prepareStatement(selectsql);
			 
			resultset = pstmt.executeQuery();
			
			while(resultset.next())
			{
				Project returnProject = new Project();
				ProjectInfo p = new ProjectInfo();
				p.setID(resultset.getInt(1));
				p.setName(resultset.getString(2));
				returnProject.setProjInfo(p);
				returnProject.setRecordsPerBatch(resultset.getInt(3));
				returnProject.setFirstY(resultset.getInt(4));
				returnProject.setRecordHeight(resultset.getInt(5));
				
				projects.add(returnProject);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		connection = null;
		pstmt = null;
		resultset = null;
			
		return projects;
	}
	
	public Project getProject(int id)
	{
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet resultset = null;
		Project returnProject = new Project();
		try
		{
			connection = db.getConnection();
			String selectsql = "SELECT * from Project WHERE ID = ?";
			pstmt = connection.prepareStatement(selectsql);
			pstmt.setInt(1, id);
			
			resultset = pstmt.executeQuery();
			
			resultset.next();
			ProjectInfo pi = new ProjectInfo();
			pi.setID(id);
			pi.setName(resultset.getString(2));
			returnProject.setProjInfo(pi);
			returnProject.setRecordsPerBatch(resultset.getInt(3));
			returnProject.setFirstY(resultset.getInt(4));
			returnProject.setRecordHeight(resultset.getInt(5));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		connection = null;
		pstmt = null;
		resultset = null;
		if(returnProject.getProjInfo().getName() == "")
		{
			return null;
		}
		return returnProject;
	}
	
	public int add(Project project)
	{
		Connection connection = null;
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet resultset = null;
		int id = -1;
		try
		{
			connection = db.getConnection();
			String insertsql = "INSERT INTO Project (Name, RecordsPerBatch, FirstYCoord, RecordHeight)" +
								"VALUES (?, ?, ?, ?)";
			pstmt = connection.prepareStatement(insertsql);
			pstmt.setString(1, project.getProjInfo().getName());
			pstmt.setInt(2, project.getRecordsPerBatch());
			pstmt.setInt(3, project.getFirstY());
			pstmt.setInt(4, project.getRecordHeight());
			
			if(pstmt.executeUpdate() == 1)
			{
				stmt = connection.createStatement();
				resultset = stmt.executeQuery("SELECT last_insert_rowid()");
				resultset.next();
				id = resultset.getInt(1);
				project.getProjInfo().setID(id);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		connection = null;
		pstmt = null;
		stmt = null;
		resultset = null;
		return id;
	}
	
	public void delete(Project project)
	{
		Connection connection = null;
		PreparedStatement pstmt = null;
		try
		{
			connection = db.getConnection();
			String selectsql = "DELETE from Project WHERE ID = ?";
			pstmt = connection.prepareStatement(selectsql);
			pstmt.setInt(1, project.getProjInfo().getID());
			 
			pstmt.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		connection = null;
		pstmt = null;
	}
}
