package tfs;

import java.io.File;

import com.microsoft.tfs.core.TFSTeamProjectCollection;
import com.microsoft.tfs.core.clients.workitem.CoreFieldReferenceNames;
import com.microsoft.tfs.core.clients.workitem.WorkItem;
import com.microsoft.tfs.core.clients.workitem.files.Attachment;
import com.microsoft.tfs.core.clients.workitem.files.AttachmentFactory;
import com.microsoft.tfs.core.clients.workitem.project.Project;
import com.microsoft.tfs.core.clients.workitem.wittype.WorkItemType;
import com.microsoft.tfs.core.httpclient.Credentials;
import com.microsoft.tfs.core.httpclient.UsernamePasswordCredentials;
import com.microsoft.tfs.core.util.URIUtils;
import com.microsoft.tfs.core.clients.workitem.exceptions.UnableToSaveException;

public class ConnectionToVisualStudio {

	private static final String ROOTDIR = System.getProperty("user.dir");
	
	public TFSTeamProjectCollection tpc;
	public Project project;
	public WorkItem newWorkItem;
	Custom_Properties cus_properties;
	
	
	public void connectToTFS() { 
		System.out.println("Root Path :  " + ConnectionToVisualStudio.ROOTDIR);
		System.setProperty("com.microsoft.tfs.jni.native.base-directory", ConnectionToVisualStudio.ROOTDIR + "/src/main/Resources/native");
		System.out.println("com.microsoft.tfs.jni.native.base-directory Path :  "
				+ System.getProperty("com.microsoft.tfs.jni.native.base-directory"));

		cus_properties = new Custom_Properties();
		
		Credentials credentials;
		credentials = new UsernamePasswordCredentials(cus_properties.tfs_username,cus_properties.tfs_password);
		try {
			tpc = new TFSTeamProjectCollection(URIUtils.newURI(cus_properties.tfs_baseURL), credentials);
		}

		catch (Exception e) {
			System.out.println("The connection has been refused");
		}
	
	}
	
	
	public void connectToProject(){ 
			project = tpc.getWorkItemClient().getProjects().get(cus_properties.getTfs_projectName());
	}
	
	public void createWorkItem(String workItemType,String title){   
		
		WorkItemType bugWorkItemType =null;
		if(workItemType.equalsIgnoreCase("Bug")) {
			
			System.out.println("------------------Adding WorkItem-------------------------------------------------------");
			
			System.out.println("Title  -  "+title);
			
			bugWorkItemType = project.getWorkItemTypes().get("Bug");
			newWorkItem = project.getWorkItemClient().newWorkItem(bugWorkItemType);
			newWorkItem.setTitle(title);
			//newWorkItem.getFields().getField(CoreFieldReferenceNames.AREA_PATH).setValue("MyFirstProject\\TESTING2");
			//newWorkItem.save();
			cus_properties.setTfs_bugTitle(title);
		}
}

	public void areFieldsAvailable() {
		
		System.out.println("Work Iem Fields");

		System.out.println("ASSIGNED_TO : "+newWorkItem.getFields().contains(CoreFieldReferenceNames.ASSIGNED_TO));
		System.out.println("AREA_PATH : "+newWorkItem.getFields().contains(CoreFieldReferenceNames.AREA_PATH));
		System.out.println("ITERATION_PATH : "+newWorkItem.getFields().contains(CoreFieldReferenceNames.ITERATION_PATH));
		System.out.println("Repro Steps : "+newWorkItem.getFields().contains("Repro Steps"));


	}
	
	public void addAssignedTo(String assignTo) { 
		
		System.out.println("------------------Adding AssignedTo-------------------------------------------------------");
		
		System.out.println("AssignedTo  -  "+assignTo);
		newWorkItem.getFields().getField(CoreFieldReferenceNames.ASSIGNED_TO).setValue(assignTo);
		cus_properties.setTfs_assignedTo(assignTo);
		
	}	
	
	
	public void addAreaPath(String areaPath) { 
		
		System.out.println("------------------Adding AreaPath-------------------------------------------------------");

		System.out.println("AreaPath   -  "+areaPath);
		newWorkItem.getFields().getField(CoreFieldReferenceNames.AREA_PATH).setValue(areaPath);
		cus_properties.setTfs_areaPath(areaPath);

		
	}	
	
	public void addIterationPath(String iterationPath) { 
		
		System.out.println("------------------Adding iterationPath-------------------------------------------------------");

		System.out.println("IterationPath  -  "+iterationPath);
		newWorkItem.getFields().getField(CoreFieldReferenceNames.ITERATION_PATH).setValue(iterationPath);
		cus_properties.setTfs_iterationPath(iterationPath);
		
	}	
	
	public void addReproSteps(String steps) { 
		
		System.out.println("------------------Adding ReproSteps-------------------------------------------------------");

		System.out.println("steps  -  "+steps);
		newWorkItem.getFields().getField("Repro Steps").setValue(steps);
		cus_properties.setTfs_steps(steps);
	}

	

	public void addScreenShot(String imagePath,String imageNme) { 
		
		System.out.println("------------------Adding ScreenShot-------------------------------------------------------");

		System.out.println("ImagePath : "+imagePath);
		Attachment a = AttachmentFactory.newAttachment(new File(imagePath),imageNme);
		newWorkItem.getAttachments().add(a);
		
	}

	public void addDocument(String documentPath,String documentNme) { 
		
		System.out.println("------------------Adding Document-------------------------------------------------------");

		System.out.println("document Path : "+documentPath);
		Attachment a = AttachmentFactory.newAttachment(new File(documentPath),documentNme);
		newWorkItem.getAttachments().add(a);
	}
		
	
	public int saveWorkItem() {
		
		try {
			newWorkItem.save();
			
		}
		catch(UnableToSaveException e) {
			
			System.out.println("UnableToSaveException  - WorkItem :  "+newWorkItem.getTitle()+" is not saved");
		}
		cus_properties.saveProperties();
		
		return newWorkItem.getID();
	}
	
	
	
//	public static void main(String[] args) { 
		
	public void connectTo() {
		
		ConnectionToVisualStudio connect = new ConnectionToVisualStudio();
		connect.connectToTFS();
		System.out.println("TFS has been Linked");
		
		connect.connectToProject();
		System.out.println("project has been Linked");
		
		connect.createWorkItem("Bug","Issue in Click button");
		System.out.println("Work Item created");

		String description = "1. Go to Application"
		+ "2. Login to Application"
		+ "3. Go to Search Page";
		
		connect.addReproSteps(description);
		System.out.println("description added");
		
		System.out.println("Work item " + connect.newWorkItem.getID() + " successfully created");
		
	}
	@Override
	protected void finalize() throws Throwable{
	    try { 	
	    	tpc.close(); 
	    	super.finalize();
	    	} 
	    catch (Exception e) {
	    	e.printStackTrace();
	    	}
	}

} 

