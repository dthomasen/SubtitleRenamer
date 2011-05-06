import java.awt.GridLayout;
import java.awt.List;
import java.awt.Menu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SubtitleRenamer implements ActionListener {
	
	public static void main(String[] args){
		SubtitleRenamer renamer = new SubtitleRenamer();
	}
	
	private JFrame frame;
	private JPanel frameHelper;
	private JPanel subtitle;
	private JPanel movie;
	private JButton subOpen;
	private JButton movOpen;
	private JButton goButton;
	private JMenuItem openSubtitle;
	private JMenuItem aboutItem;
	private JMenuItem openMovie;
	private JMenuItem renamerHelp;
	private JMenuItem exit;
	
	private JFileChooser fileChooser;
	FileNameExtensionFilter subFilter;
	FileNameExtensionFilter movFilter;
	
	private JList subList;
	private JList movList;
	
	/**One array to store selected subtitle Files
	 * And another one to store selected movie Files
	 * Saved as File instances
	 */
	private File[] subFiles;
	private File[] movFiles;
	
	public SubtitleRenamer(){
		frame = new JFrame("Subtitle Renamer - v1.0 alpha");
		frameHelper = new JPanel();
		subtitle = new JPanel();
		movie = new JPanel();
		subOpen = new JButton("Choose Subtitle(s)");
		movOpen = new JButton("Choose Movie(s)");
		goButton = new JButton("GO!");
		
		subList = new JList();
		movList = new JList();
		
		/** Creating the top menu bar */
		JMenuBar menuBar = new JMenuBar();
		JMenu filesMenu = new JMenu("Files");
		JMenu helpMenu = new JMenu("Help");
		
		menuBar.add(filesMenu);
		menuBar.add(helpMenu);
		openSubtitle = new JMenuItem("Open Subtitle(s)");
		openMovie = new JMenuItem("Open Movie(s)");
		aboutItem = new JMenuItem("About");
		renamerHelp = new JMenuItem("Renamer Help");
		exit = new JMenuItem("Exit");
		openSubtitle.addActionListener(this);
		openMovie.addActionListener(this);
		aboutItem.addActionListener(this);
		renamerHelp.addActionListener(this);
		exit.addActionListener(this);
		filesMenu.add(openSubtitle);
		filesMenu.add(openMovie);
		filesMenu.add(exit);
		helpMenu.add(aboutItem);
		helpMenu.add(renamerHelp);
		
		/** Only necessary to use 1 fileChooser for both sub and mov dialogs
		 *  Setting the fileChooser parameter to allow multiple file selection
		 */
		fileChooser = new JFileChooser();
		fileChooser.setMultiSelectionEnabled(true);
		/** Creating 2 filters, to filter subtitle and movie files accordingly */
		subFilter = new FileNameExtensionFilter("Subtitle filter", "srt");
		movFilter = new FileNameExtensionFilter("Movie filter", "mkv", "avi", "divx");
		/** Adding action listeners for the 3 buttons */
		subOpen.addActionListener(this);
		movOpen.addActionListener(this);
		goButton.addActionListener(this);
		
		/** Creating one panel for subtitle options and one for movie options */
		subtitle.setLayout(new GridLayout(2, 1));
		movie.setLayout(new GridLayout(2,1));
		subtitle.add(subOpen);
		subtitle.add(subList);
		movie.add(movOpen);
		movie.add(movList);
		
		/** Created a frameHelper because only 1 component could be assigned to ContentPane */
		frameHelper.add(subtitle);
		frameHelper.add(goButton);
		frameHelper.add(movie);
		frame.getContentPane().add(frameHelper);
		frame.setJMenuBar(menuBar);
				
		/** Close program on exit */
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
		frame.pack();
	}

	/** Method for handling actionEvents from buttons */
	@Override
	public void actionPerformed(ActionEvent e) {
			if(e.getSource() == subOpen || e.getSource() == openSubtitle){
				//Assigning the sub filter to the fileChooser
				fileChooser.addChoosableFileFilter(subFilter);
				subFiles = fileChooserMethod();
				subList.setListData(subFiles);
				frame.pack();
			}
			
			if(e.getSource() == movOpen || e.getSource() == openMovie){
				//Assigning the mov filter to the fileChooser
				fileChooser.addChoosableFileFilter(movFilter);
				movFiles = fileChooserMethod();
				movList.setListData(movFiles);
				frame.pack();
			}
			
			if(e.getSource() == goButton){
				renameProcedure();
			}
			
			if(e.getSource() == aboutItem){
				JOptionPane.showMessageDialog(frame,
					    "Dennis Thomasen - 20 year old \nComputer science student at the University of Aarhus\nEmail: dennisthomasen@gmail.com",
					    "About me",
					    JOptionPane.INFORMATION_MESSAGE,
					    new ImageIcon("aboutme.png"));
				}
			
			if(e.getSource() == renamerHelp){
				JOptionPane.showMessageDialog(frame,
					    "Select subtitles to be renamed and the equivalent movie file.\nMy program will then rename the subtitles so that i.e. \nVLC will automatically play them along with the movie", 
					    "Subtitle Renamer Help", JOptionPane.INFORMATION_MESSAGE);
			}
			
			if(e.getSource() == exit){
				frame.dispose();
			}
			
	}
	
	/**
	 * Method that assigns the selected files to the array's of movie and subtitle files
	 * @return the selected files from the fileChooser or null (If dialog is closed)
	 */
	private File[] fileChooserMethod(){
		int returnVal = fileChooser.showOpenDialog(frame);
		
		if(returnVal == fileChooser.APPROVE_OPTION){
			return fileChooser.getSelectedFiles();
		}else{
			return null;
		}
	}
	
	/**
	 * Renames the subtitle files to the mov fileName but keeping the original file extension
	 */
	private void renameProcedure(){
		
		for(int i = 0; i <= movFiles.length - 1; i++){
			subFiles[i].renameTo(new File(getFilenameWithoutExtension(movFiles[i]) + getFileExtension(subFiles[i])));
		}
	}
	
	/** 
	 * Method to get the filename of a file, without the extension
	 * @param f - the file to get the name from
	 * @return String with the name
	 */
	private String getFilenameWithoutExtension(File f){
		int index = f.getPath().lastIndexOf(".");
		
		String name = "";
	
		 if (index > 0 && index <= f.getPath().length() - 2 ) {
			 name = f.getPath().substring(0, index);
		 }  
		 return name;
	}
	
	/**
	 * Method to get the file extension only
	 * @param f - the file to get the extension from
	 * @return the extension as a string
	 */
	private String getFileExtension(File f){
		int index = f.getName().lastIndexOf(".");
		
		String extension = "";
		
		if(index > 0 && index <= f.getName().length() - 1){
			extension = f.getName().substring(index);
		}
		return extension;
	}
}
