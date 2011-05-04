import java.awt.GridLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
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
		frame = new JFrame("Subtitle Renamer - v0.1");
		frameHelper = new JPanel();
		subtitle = new JPanel();
		movie = new JPanel();
		subOpen = new JButton("Choose Subtitle(s)");
		movOpen = new JButton("Choose Movie(s)");
		goButton = new JButton("GO!");
		
		subList = new JList();
		movList = new JList();
		
		
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
		
		/** Close program on exit */
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
		frame.pack();
	}

	/** Method for handling actionEvents from buttons */
	@Override
	public void actionPerformed(ActionEvent e) {
			if(e.getSource() == subOpen){
				//Assigning the sub filter to the fileChooser
				fileChooser.addChoosableFileFilter(subFilter);
				subFiles = fileChooserMethod();
				subList.setListData(subFiles);
				frame.pack();
			}
			
			if(e.getSource() == movOpen){
				//Assigning the mov filter to the fileChooser
				fileChooser.addChoosableFileFilter(movFilter);
				movFiles = fileChooserMethod();
				movList.setListData(movFiles);
				frame.pack();
			}
			
			if(e.getSource() == goButton){
				renameProcedure();
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
