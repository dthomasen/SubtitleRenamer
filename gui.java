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

public class gui implements ActionListener {
	
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
	
	public gui(){
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
		/** Adding action listeners for the 2 fileChooser buttons */
		subOpen.addActionListener(this);
		movOpen.addActionListener(this);
		
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
}
