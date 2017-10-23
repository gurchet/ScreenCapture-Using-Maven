package tfs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Custom_Properties {

	Properties prop;
	FileInputStream fileInput;
	
	public String tfs_baseURL;
	public String tfs_projectName;
	public String tfs_username;
	public String tfs_password;
	public String tfs_bugTitle;
	public String tfs_assignedTo;
	public String tfs_areaPath;
	public String tfs_iterationPath;
	public String tfs_steps;

//	public void update_Properties() {
//		
//		prop.setProperty(key, value)
//		
//	}
	
	public Custom_Properties() {

		try {
			File file = new File("Global.properties");
			fileInput = new FileInputStream(file);
			prop = new Properties();
			prop.load(fileInput);
			

			tfs_baseURL = prop.getProperty("baseURL");
			tfs_projectName = prop.getProperty("projectName");
			tfs_username = prop.getProperty("username");
			tfs_password = prop.getProperty("password");
			tfs_bugTitle = prop.getProperty("bugTitle");
			tfs_assignedTo = prop.getProperty("assignedTo");
			tfs_areaPath = prop.getProperty("areaPath");
			tfs_iterationPath = prop.getProperty("iterationPath");
			tfs_steps = prop.getProperty("steps");
			fileInput.close();
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public String getTfs_steps() {
		return tfs_steps;
	}

	public void setTfs_steps(String tfs_steps) {
		this.tfs_steps = tfs_steps;
		System.out.println("-----------Setting up steps : "+tfs_steps+" ------------");
		prop.setProperty("steps", tfs_steps);
	}
	
	public String getTfs_baseURL() {
		return tfs_baseURL;
	}

	public void setTfs_baseURL(String tfs_baseURL) {
		this.tfs_baseURL = tfs_baseURL;
		System.out.println("-----------Setting up baseURL : "+tfs_baseURL+" ------------");
		
		
	}

	public String getTfs_projectName() {
		return tfs_projectName;
	}

	public void setTfs_projectName(String tfs_projectName) {
		this.tfs_projectName = tfs_projectName;
		System.out.println("-----------Setting up projectName : "+tfs_projectName+" ------------");
	}

	public String getTfs_username() {
		return tfs_username;
	}

	public void setTfs_username(String tfs_username) {
		this.tfs_username = tfs_username;
		System.out.println("-----------Setting up username : "+tfs_username+" ------------");

	}

	public String getTfs_password() {
		return tfs_password;
	}

	public void setTfs_password(String tfs_password) {
		this.tfs_password = tfs_password;
		System.out.println("-----------Setting up password : "+tfs_password+" ------------");
	}

	public String getTfs_bugTitle() {
		return tfs_bugTitle;
	}

	public void setTfs_bugTitle(String tfs_bugTitle) {
		this.tfs_bugTitle = tfs_bugTitle;
		System.out.println("-----------Setting up bugTitle : "+tfs_bugTitle+" ------------");
		prop.setProperty("bugTitle", tfs_bugTitle);

	}

	public String getTfs_assignedTo() {
		return tfs_assignedTo;
	}

	public void setTfs_assignedTo(String tfs_assignedTo) {
		this.tfs_assignedTo = tfs_assignedTo;
		System.out.println("-----------Setting up assignedTo : "+tfs_assignedTo+" ------------");
		prop.setProperty("assignedTo", tfs_assignedTo);

	}

	public String getTfs_areaPath() {
		return tfs_areaPath;
	}

	public void setTfs_areaPath(String tfs_areaPath) {
		this.tfs_areaPath = tfs_areaPath;
		System.out.println("-----------Setting up areaPath : "+tfs_areaPath+" ------------");
		prop.setProperty("areaPath", tfs_areaPath);

	}

	public String getTfs_iterationPath() {
		return tfs_iterationPath;
	}

	public void setTfs_iterationPath(String tfs_iterationPath) {
		this.tfs_iterationPath = tfs_iterationPath;
		System.out.println("-----------Setting up iterationPath : "+tfs_iterationPath+" ------------");
		prop.setProperty("iterationPath", tfs_iterationPath);

	}
	
	public void saveProperties() {
//    	baseURL=https://shoot-fast.visualstudio.com
//		projectName=MyFirstProject
//		username=sgurchet
//		password=GUR20march1991
//		bugTitle=Defect
//		assignedTo=sgurchet@yahoo.com
//		areaPath=MyFirstProject
//		iterationPath=MyFirstProject
//		steps=Login to Application
	
	FileOutputStream fileOutput = null;
	try {
		fileOutput = new FileOutputStream(new File("Global.properties"));
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		System.out.println("FileNotFoundException : File Global.properties was not found");
	}
	try {
		prop.store(fileOutput,"Comment");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		System.out.println("IOException : Property file Global.properties was not able to store the property values");
	}
	try {
		fileOutput.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		System.out.println("IOException : Output stream for Global.properties was not closed successfully");
	}
		
	}

}
