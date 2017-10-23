package com.mycompany.app;

import java.awt.AWTException; 
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import tfs.ConnectionToVisualStudio;
import tfs.Custom_Properties;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

/**
 * @author Gurchet Singh The program provides the GUI for the Application having
 *         3 screens 1. To create New Document(OR New Session) 2. Dashboard. 3.
 *         To save Additional information with screen shot i.e. Comment,Test
 *         Data
 */

public class SingleButtonapp {

	// WatermarkTextField filename;
	protected static final Component JTextArea = null;
	MyDialog d;
	TakeScreenShot takeScreenShot;
	String document_name;
	String steps = "";
	final JTextField filename;
	List<BufferedImage> images;
	Custom_Properties cus_properties;
	long thread_Id;

	public SingleButtonapp() {

		// System.out.println("Sub thread Id : " + Thread.currentThread().getId());
		thread_Id = Thread.currentThread().getId();

		cus_properties = new Custom_Properties();

		// JDialog d = new JDialog();
		d = new MyDialog("Add File");
		d.setTitle("Add File");
		JPanel pnlButton = new JPanel();
		Box box = Box.createVerticalBox();
		filename = new JTextField("File Name");
		filename.setText("File Name");
		JButton Save = new JButton("Save");

		d.setSize(200, 100);

		box.add(filename);
		box.add(Save);
		pnlButton.add(box);
		d.add(pnlButton);
		d.setVisible(true);
		d.setLocationRelativeTo(null);
		d.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		Save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (!filename.equals(null)) {
					document_name = filename.getText();

					try {
						takeScreenShot = new TakeScreenShot(document_name);
					} catch (InvalidFormatException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					d.dispose();
					screenController();
				}
			}

		});

	}

	/*
	 * 2. Dashboard. Function takes first argument as object of TakeScreenShot class
	 * to use its methods the another argument is the document name which would be
	 * shown on the title bar of the dashboard.
	 */
	public void screenController() {

		final JFrame frm_main = new JFrame();
		JPanel pnlButton = new JPanel();
		JButton NewButton;
		JButton Capture;
		JButton Done;
		JButton Comment;
		JButton Bug;

		NewButton = new JButton("New");
		Capture = new JButton("Capture");
		Done = new JButton("Done");
		Comment = new JButton("Comment");
		Bug = new JButton("Bug");

		pnlButton.add(NewButton);
		pnlButton.add(Capture);
		pnlButton.add(Comment);
		pnlButton.add(Bug);
		pnlButton.add(Done);

		frm_main.add(pnlButton);

		// JFrame properties
		frm_main.setSize(350, 110);
		frm_main.setBackground(Color.BLACK);
		frm_main.setTitle(document_name);
		frm_main.setLocationRelativeTo(null);
		frm_main.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frm_main.setVisible(true);

		NewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// new SingleButtonapp();

				Thread thread = new Thread() {
					public void run() {
						new SingleButtonapp();

						try {
							Thread.sleep(500000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							// e.printStackTrace();

							System.out.println("Thread is terminated");
						}
					}
				};
				thread.start();
				System.out.println("New Button Thread : " + thread.getId());
			}
		});

		Capture.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BufferedImage screenFullImage = null;
				frm_main.setVisible(false);
				try {
					screenFullImage = takeScreenShot.captureScreen();
				} catch (AWTException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				frm_main.setVisible(true);
				// Printing screen shot
				try {
					takeScreenShot.printScreen();
				} catch (InvalidFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		Comment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				frm_main.disable();

				// JDialog dlg_Save = new JDialog();
				final MyDialog dlg_Save = new MyDialog(document_name);
				dlg_Save.setTitle(document_name);
				dlg_Save.setBounds(0, 0, 300, 200);

				JPanel pnl_main = new JPanel();

				pnl_main.setLayout(new BoxLayout(pnl_main, BoxLayout.Y_AXIS));

				pnl_main.setBounds(30, 30, 250, 150);

				final JTextArea txt_Comment = new JTextArea("Comment");
				txt_Comment.setWrapStyleWord(true);
				txt_Comment.setLineWrap(true);
				txt_Comment.setEditable(true);
				txt_Comment.setFocusable(true);

				JScrollPane scroll_pane = new JScrollPane(txt_Comment);
				scroll_pane.setBounds(50, 0, 200, 90);
				pnl_main.add(scroll_pane);

				JPanel myPanel = new JPanel();
				myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.X_AXIS));
				myPanel.setBounds(0, 100, 250, 50);

				JButton btn_save;
				btn_save = new JButton("Save");

				myPanel.add(btn_save);
				pnl_main.add(myPanel);

				dlg_Save.add(pnl_main);

				dlg_Save.setLocationRelativeTo(frm_main);
				dlg_Save.setVisible(true);

				btn_save.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						String comment = txt_Comment.getText();
						takeScreenShot.PrintComment(comment);
						steps += "\n" + comment;
						System.out.println("steps  :  " + steps);
						dlg_Save.setVisible(false);
						frm_main.setVisible(true);
						dlg_Save.dispose();
						frm_main.enable();
					}
				});

				dlg_Save.addWindowListener(new WindowAdapter() {

					@Override
					public void windowClosing(WindowEvent e) {

						// dlg_Save.setVisible(false);
						dlg_Save.dispose();
						frm_main.setVisible(true);
						frm_main.enable();

					}

				});

			}

		});

		Bug.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				frm_main.disable();

				final MyDialog dlg_Bug = new MyDialog(document_name);
				dlg_Bug.setTitle(document_name);
				JPanel panel = new JPanel(new GridBagLayout());
				GridBagConstraints c = new GridBagConstraints();

				JLabel label1 = new JLabel();
				label1.setText("Title : ");
				c.weightx = 0;
				c.fill = GridBagConstraints.HORIZONTAL;
				c.gridx = 0;
				c.gridy = 0;
				c.anchor = GridBagConstraints.FIRST_LINE_START;
				panel.add(label1, c);

				final JTextField text1 = new JTextField(15);
				text1.setText(cus_properties.getTfs_bugTitle());
				c.weightx = 1;
				c.fill = GridBagConstraints.HORIZONTAL;
				c.gridx = 2;
				c.gridy = 0;
				c.gridwidth=1;
				panel.add(text1, c);

				JLabel label2 = new JLabel();
				label2.setText("Area Path : ");
				c.weightx = 0;
				c.fill = GridBagConstraints.HORIZONTAL;
				c.gridx = 0;
				c.gridy = 1;
				c.anchor = GridBagConstraints.FIRST_LINE_START;
				panel.add(label2, c);

				final JTextField text2 = new JTextField(15);
				text2.setText(cus_properties.getTfs_areaPath());
				c.weightx = 1;
				c.fill = GridBagConstraints.HORIZONTAL;
				c.gridx = 2;
				c.gridy = 1;
				c.gridwidth=1;
				panel.add(text2, c);

				JLabel label3 = new JLabel();
				label3.setText("Iteration Path : ");
				c.weightx = 0;
				c.fill = GridBagConstraints.HORIZONTAL;
				c.gridx = 0;
				c.gridy = 2;
				c.anchor = GridBagConstraints.FIRST_LINE_START;
				panel.add(label3, c);

				final JTextField text3 = new JTextField(15);
				text3.setText(cus_properties.getTfs_iterationPath());
				c.weightx = 1;
				c.fill = GridBagConstraints.HORIZONTAL;
				c.gridx = 2;
				c.gridy = 2;
				c.gridwidth=1;
				panel.add(text3, c);

				JLabel label4 = new JLabel();
				label4.setText("Assign To : ");
				c.weightx = 0;
				c.fill = GridBagConstraints.HORIZONTAL;
				c.gridx = 0;
				c.gridy = 3;
				c.anchor = GridBagConstraints.FIRST_LINE_START;
				panel.add(label4, c);

				final JTextField text4 = new JTextField(15);
				text4.setText(cus_properties.getTfs_assignedTo());
				c.weightx = 1;
				c.fill = GridBagConstraints.HORIZONTAL;
				c.gridx = 2;
				c.gridy = 3;
				c.gridwidth=1;
				panel.add(text4, c);

				JLabel label5 = new JLabel();
				label5.setText("Steps : ");
				c.weightx = 0;
				c.fill = GridBagConstraints.HORIZONTAL;
				c.gridx = 0;
				c.gridy = 4;
				c.anchor = GridBagConstraints.FIRST_LINE_START;
				panel.add(label5, c);

				final JTextArea textAreaStps = new JTextArea(30,30);
				JScrollPane scrollPane = new JScrollPane(textAreaStps);
				
				textAreaStps.setWrapStyleWord(true);
				textAreaStps.setLineWrap(true);
				textAreaStps.setEditable(true);
				textAreaStps.setFocusable(true);
				textAreaStps.setRows(3);
				textAreaStps.setText(cus_properties.getTfs_steps());
			
				//c.weightx = 1;
				c.fill = GridBagConstraints.HORIZONTAL;
				c.gridx = 2;
				c.gridy = 4;
				//c.gridwidth=1;
				//add(new JScrollPane(new JTextArea(50, 50)), BorderLayout.PAGE_START);
				textAreaStps.setPreferredSize(new Dimension(10, 5));
				panel.add(textAreaStps, c);
			
		
				
				JButton btn_save = new JButton();
				btn_save.setText("Create Bug");
				c.weightx = 0;
				c.fill = GridBagConstraints.SOUTHWEST;
				c.gridx = 0;
				c.gridy = 9;

				panel.add(btn_save, c);
				c.weightx = 0;
				c.fill = GridBagConstraints.SOUTHWEST;
				c.gridx = 0;
				c.gridy = 10;
			

			     
				dlg_Bug.setBounds(0, 0, 800, 300);
				dlg_Bug.add(panel, BorderLayout.CENTER);
				//dlg_Bug.add(btn_save, BorderLayout.PAGE_END);
				dlg_Bug.setLocationRelativeTo(frm_main);
				dlg_Bug.setVisible(true);
				dlg_Bug.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

				btn_save.addActionListener(new ActionListener() {
					
					public void actionPerformed(ActionEvent e) {
						
						/////////////////////////////////////////////////////////In construction///////////////////////
						JFrame jf= new JFrame();
						JPanel panle2=new JPanel();
						Icon logoImage = new ImageIcon("./src/main/Resources/Images/Loading_icon.gif");
						 final JLabel   lblIcon = new JLabel(logoImage); ;
						 lblIcon.setBounds(9,1,5,5);
						 GridBagConstraints c = new GridBagConstraints();
						 c.weightx = 0;
							c.fill = GridBagConstraints.SOUTHWEST;
							c.gridx = 0;
							c.gridy = 10;
						 panle2.add(lblIcon,c);
						 panle2.setBounds(9,1,5,5);
						 panle2.setPreferredSize(new Dimension(20,20));
						 jf.add(panle2);
						 jf.setVisible(true);
							//lblIcon.setVisible(false);
						 JDialog jd= new JDialog();
						   
						 jd.setBounds(0, 0, 800, 300);
						 jd.add(panle2, BorderLayout.CENTER);
						// jd.setLocation(50, 50);
						 jd.setVisible(true);
						 //jd.add(jf);
						 
						/////////////////////////////////////////////////////////In construction///////////////////////

						 
						dlg_Bug.setEnabled(false);
						takeScreenShot.closeFile();

						String bugTitle = text1.getText();
						String areaPath = text2.getText();
						String iteration = text3.getText();
						String assignTo = text4.getText();
						String reproSteps = textAreaStps.getText();

						System.out.println("bugTitle   " + bugTitle);
						System.out.println("areaPath   " + areaPath);
						System.out.println("iteration   " + iteration);
						System.out.println("assignTo   " + assignTo);
						System.out.println("reproSteps   " + reproSteps);

						int maxImageNum = takeScreenShot.image_number;
						String folderName = takeScreenShot.folder_name;
						ConnectionToVisualStudio tfs = new ConnectionToVisualStudio();

						tfs.connectToTFS();

						System.out.println("TFS has been Linked");
						try {
							tfs.connectToProject();
							System.out.println("project has been Linked");

							if (!steps.equals(null)) {
								tfs.createWorkItem("Bug", bugTitle);
								System.out.println("Work Item created");
							} else {
								System.out.println("Work Item Name was not given");
							}

							tfs.areFieldsAvailable();

							 //------------------addAreaPath-------------------------------------------------------

							 tfs.addAreaPath(areaPath);

							 //------------------addIterationPath----------------------------------------------------

							 tfs.addIterationPath(iteration);

							 //------------------addAssignedTo-------------------------------------------------------

							tfs.addAssignedTo(assignTo);

							 //------------------addReproSteps-------------------------------------------------------

							 tfs.addReproSteps(reproSteps);

							 //------------------addScreenShot-------------------------------------------------------

							InputStream pic = null;
							String fullname = "";
							for (int i = 0; i <= maxImageNum; i++) {
								fullname = takeScreenShot.screenshot_name + "_" + i + "." + takeScreenShot.image_format;
								try {
									pic = new FileInputStream(folderName + "/" + fullname);
								} catch (Exception e1) {
									// TODO Auto-generated catch block
									System.out.println("File " + takeScreenShot.screenshot_name + " not found");
									// e1.printStackTrace();
								}

								if (pic != null) {
									tfs.addScreenShot(folderName + "/" + fullname, fullname);
								}
							}

							// ------------------addDocument-------------------------------------------------------

							InputStream stm_doc = null;
							String completePath = folderName + "/" + takeScreenShot.filename + "."
									+ takeScreenShot.doc_format;
							try {
								stm_doc = new FileInputStream(completePath);
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								System.out.println("File " + takeScreenShot.filename + " not found");
								// e1.printStackTrace();
							}

							if (pic != null) {
								tfs.addDocument(completePath, takeScreenShot.filename);
							}
							tfs.saveWorkItem();
							System.out.println("Work item " + tfs.newWorkItem.getID() + " successfully created");

						} finally {
							try {
								
								tfs.tpc.close();
							} catch (Exception except) {
								except.printStackTrace();
							}

							dlg_Bug.dispose();
							// Thread.currentThread().interrupt();
							// frm_main.setVisible(false);

							frm_main.dispose();

							Set<Thread> setMyThread = Thread.getAllStackTraces().keySet();
							System.out.println("-------Closing the thread-------");
							for (Thread thread : setMyThread) {

								System.out.println(
										thread.getId() + " --- " + thread.getName() + " --- " + thread.getState());
								if (thread.getId() == thread_Id) {
									System.out.println("Thread " + thread_Id + " is about to close");
									thread.interrupt();
									System.out.println(
											"Thread " + thread_Id + " is intrupted : " + thread.isInterrupted());
								}
							}

							Set<Thread> set_thread2 = Thread.getAllStackTraces().keySet();
							int isThreadAvailable = 0;
							for (Thread thread : set_thread2) {

								// System.out.println(thread.getId()+" --- " +thread.getName() + " --- " +
								// thread.getState());
								if (thread.getName().contains("Thread-")) {
									isThreadAvailable++;
								}

							}

							System.out.println("isThreadAvailable : " + isThreadAvailable);

							if (isThreadAvailable == 0) {
								System.exit(1);
							}
						}
					}
				});

				dlg_Bug.addWindowListener(new WindowAdapter() {

					@Override
					public void windowClosing(WindowEvent e) {

						// dlg_Save.setVisible(false);
						dlg_Bug.dispose();
						frm_main.setVisible(true);
						frm_main.enable();

					}

				});

			}

		});

		Done.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				takeScreenShot.closeFile();
				int isThreadAvailable = 0;

				frm_main.dispose();

				Set<Thread> set_thread = Thread.getAllStackTraces().keySet();
				System.out.println("-------Closing the thread-------");
				for (Thread thread : set_thread) {

					System.out.println(thread.getId() + " --- " + thread.getName() + " --- " + thread.getState());
					if (thread.getId() == thread_Id) {
						System.out.println("Thread " + thread_Id + " is about to close");
						thread.interrupt();
						System.out.println("Thread " + thread_Id + " is intrupted : " + thread.isInterrupted());
					}
				}

				Set<Thread> set_thread2 = Thread.getAllStackTraces().keySet();
				for (Thread thread : set_thread2) {

					// System.out.println(thread.getId()+" --- " +thread.getName() + " --- " +
					// thread.getState());
					if (thread.getName().contains("Thread-")) {
						isThreadAvailable++;
					}

				}

				System.out.println("isThreadAvailable : " + isThreadAvailable);

				if (isThreadAvailable == 0) {
					System.exit(1);
				}
			}
		});

		frm_main.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {

				takeScreenShot.closeFile();
				// frm_main.setVisible(false);
				frm_main.dispose();

				Set<Thread> setMyThread = Thread.getAllStackTraces().keySet();
				System.out.println("-------Closing the thread-------");
				for (Thread thread : setMyThread) {

					System.out.println(
							thread.getId() + " --- " + thread.getName() + " --- " + thread.getState());
					if (thread.getId() == thread_Id) {
						System.out.println("Thread " + thread_Id + " is about to close");
						thread.interrupt();
						System.out.println(
								"Thread " + thread_Id + " is intrupted : " + thread.isInterrupted());
					}
				}

				Set<Thread> set_thread2 = Thread.getAllStackTraces().keySet();
				int isThreadAvailable = 0;
				for (Thread thread : set_thread2) {

					// System.out.println(thread.getId()+" --- " +thread.getName() + " --- " +
					// thread.getState());
					if (thread.getName().contains("Thread-")) {
						isThreadAvailable++;
					}

				}

				System.out.println("isThreadAvailable : " + isThreadAvailable);

				if (isThreadAvailable == 0) {
					System.exit(1);
				}
			}

		});
		
		d.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {

				
				// frm_main.setVisible(false);
				d.dispose();

				Set<Thread> setMyThread = Thread.getAllStackTraces().keySet();
				System.out.println("-------Closing the thread-------");
				for (Thread thread : setMyThread) {

					System.out.println(
							thread.getId() + " --- " + thread.getName() + " --- " + thread.getState());
					if (thread.getId() == thread_Id) {
						System.out.println("Thread " + thread_Id + " is about to close");
						thread.interrupt();
						System.out.println(
								"Thread " + thread_Id + " is intrupted : " + thread.isInterrupted());
					}
				}

				Set<Thread> set_thread2 = Thread.getAllStackTraces().keySet();
				int isThreadAvailable = 0;
				for (Thread thread : set_thread2) {

					// System.out.println(thread.getId()+" --- " +thread.getName() + " --- " +
					// thread.getState());
					if (thread.getName().contains("Thread-")) {
						isThreadAvailable++;
					}

				}

				System.out.println("isThreadAvailable : " + isThreadAvailable);

				if (isThreadAvailable == 0) {
					System.exit(1);
				}
			}

		});
		
		

	}

	// public static void main(String[] args) throws IOException, AWTException {
	// SingleButtonapp obj_app = new SingleButtonapp();
	// lst_appobjects.add(obj_app);
	//
	// }
}

class MyDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final List<Image> icon = Arrays.asList(new ImageIcon("icon_16.png").getImage(),
			new ImageIcon("icon_32.png").getImage(), new ImageIcon("icon_64.png").getImage());

	MyDialog(String name) {
		super(new DummyFrame(name, icon));
		// super(null, java.awt.Dialog.ModalityType.TOOLKIT_MODAL);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

	}

	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (!visible) {
			((DummyFrame) getParent()).dispose();
		}
	}
}

class DummyFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	DummyFrame(String title, List<? extends Image> iconImages) {

		super(title);
		setUndecorated(true);
		setVisible(true);
		setLocationRelativeTo(null);
		setIconImages(iconImages);
	}
}
