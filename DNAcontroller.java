package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javafx.event.ActionEvent; 
import javafx.fxml.FXML; 
import javafx.scene.control.TextArea; 
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton; 
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

public class DNAController {

	@FXML 
	private TextArea Input; 
	@FXML
	private RadioButton upper; 
	@FXML
	private CheckBox Original; 
	@FXML 
	private CheckBox NumVisual; 
	@FXML
	private CheckBox BaseP; 
	@FXML
	private CheckBox spaces;
	@FXML
	private Button FormatDNA;
	@FXML
	private Button prefSet;
	@FXML
	private Button prefUse;
	@FXML 
	private TextField charLine; 
	@FXML 
	private TextField CountTextField; 
	@FXML
	private TextField ACountField; 
	@FXML
	private TextField GCountField; 
	@FXML
	private TextField CCountField; 
	@FXML
	private TextField TCountField; 
	@FXML
	private TextField OCountField; 
	@FXML
	private TextField APercent; 
	@FXML 
	private TextField GPercent; 
	@FXML
	private TextField CPercent; 
	@FXML
	private TextField TPercent; 
	@FXML
	private TextField OPercent; 
	@FXML
	private TextField BaseCount; 
	@FXML
	private CheckBox Preprocess;
	
	private String preFormattedInput;
	
	private int numChar = 60;
	
	public void setpreferences(ActionEvent event) throws FileNotFoundException{
		PrintWriter writer = new PrintWriter("preferences.txt");
		writer.println(upper.isSelected());
		writer.println(charLine.getText());
		writer.println(NumVisual.isSelected());
		writer.println(BaseP.isSelected());
		writer.println(spaces.isSelected());
		writer.close();
	}
	
	public void usepreferences(ActionEvent event) throws FileNotFoundException{
		CallProcess(event);
		File pref = new File("preferences.txt"); 
		Scanner prefRead = new Scanner(pref);
		upper.setSelected(Boolean.parseBoolean(prefRead.nextLine().trim()));
		charLine.setText(prefRead.nextLine().trim());
		NumVisual.setSelected(Boolean.parseBoolean(prefRead.nextLine().trim()));
		BaseP.setSelected(Boolean.parseBoolean(prefRead.nextLine().trim()));
		spaces.setSelected(Boolean.parseBoolean(prefRead.nextLine().trim()));
		prefRead.close();
		CallUpper(event);
		formatButton(event);
	}	
	
// Will change the sequence from either upper or lower case, default being lower 
	public void CallUpper(ActionEvent event){
		// Calls upper case string
		String input = Input.getText(); 
		//System.out.print(input);
		if(upper.isSelected()) {
			Input.setText(input.toUpperCase());
		}
		else { 
			Input.setText(input.toLowerCase());
		}
	
	}
	private void CallPercentage() { 				
		
		if(BaseP.isSelected()) {
			
			String DNAcount = Input.getText();
			DNAcount = DNAcount.toUpperCase();//
			DNAcount = DNAcount.replaceAll("[^ACGTUBDHVNWSMKRYacgtubdhvnwsmkry]", ""); 
			
			int ACount = 0;
			int CCount = 0;
			int GCount = 0;
			int TCount = 0; 
			int OCount = 0;
			for (int i = 0; i < DNAcount.length(); i++) {
				if(DNAcount.charAt(i) == 'A') {
					ACount++;}
				else if(DNAcount.charAt(i) == 'T'){
					TCount++;}
				else if(DNAcount.charAt(i) == 'C' ) {
					CCount++;
				}
				else if (DNAcount.charAt(i)=='G'){
					GCount++;}
				else {
					OCount++;
				}
			}
			// Prints Counts of letter 
			BaseCount.setText(String.valueOf(ACount+CCount+TCount+GCount+OCount)); 
			ACountField.setText(String.valueOf(ACount));
			CCountField.setText(String.valueOf(CCount));
			TCountField.setText(String.valueOf(TCount));
			GCountField.setText(String.valueOf(GCount));
			OCountField.setText(String.valueOf(OCount));

			
			// Prints the Base Percentage To Screen
			float length1 = DNAcount.length(); 
			float A = ACount;
			float T = TCount; 
			float G = GCount; 
			float C = CCount; 
			float O = OCount;
			APercent.setText(String.valueOf(A/length1 * 100));
			TPercent.setText(String.valueOf(T/length1 * 100));
			GPercent.setText(String.valueOf(G/length1 * 100)); 
			CPercent.setText(String.valueOf(C/length1 * 100));
			OPercent.setText(String.valueOf(O/length1 * 100));

		
			//Will print 0 if de-selected
			}
		
		else {
				ACountField.setText(String.valueOf(0)); 
				CCountField.setText(String.valueOf(0));
				TCountField.setText(String.valueOf(0));
				GCountField.setText(String.valueOf(0)); 
				OCountField.setText(String.valueOf(0)); 
				APercent.setText(String.valueOf(0)); 
				TPercent.setText(String.valueOf(0)); 
				GPercent.setText(String.valueOf(0));
				CPercent.setText(String.valueOf(0));
				OPercent.setText(String.valueOf(0)); 

				BaseCount.setText(String.valueOf(0)); 

			}	
	}
	
// When this is pressed it will revert to the original sequence removing formatting
	public void CallOG(ActionEvent event) {
		Input.setText(preFormattedInput);
	} 
	
	public void sequenceSave(ActionEvent event) {
		preFormattedInput = Input.getText();
	}
	
// This method will format the sequence to have spaces every 10 letters and start a new line 
// every 60 letters
	public void formatButton(ActionEvent event) {
		
		CallPercentage();
		
		numChar = Integer.parseInt(charLine.getText());
		int numbases = 0;
		String dna = Input.getText();
		// this will filter out any unwanted characters
		dna = dna.replaceAll("[^ACGTUBDHVNWSMKRYacgtubdhvnwsmkry]", ""); 
		StringBuilder dna1 = new StringBuilder();
		if (NumVisual.isSelected()) {
			dna1.append(String.format("%4d ", 1));
		}
		for (int i = 0; i < dna.length(); i++) { 
			
			if (i%numChar == 0 && i > 1) {
				dna1.append("\n");
				numbases = 0;
				if (NumVisual.isSelected()) {
					dna1.append(String.format("%4d ", i + 1));
				}
			}
			
			// Spaces between every 10th entry		
			if (numbases % 10 == 0 && numbases > 0 && spaces.isSelected()) { 
				dna1.append(" "); 
			}
					
			dna1.append(dna.charAt(i));
			numbases = numbases +1;
		}
		Input.setText(dna1.toString());
	}
	
	public void CallProcess(ActionEvent event) {
		String dna = new String (Input.getText()); 
		String dnaStringReplace = dna.replaceAll("[^ACGTUBDHVNWSMKRYacgtubdhvnwsmkry]", ""); 
		Input.setText(dnaStringReplace.toString());		if (preFormattedInput == null) {
			preFormattedInput = dna;
		}
	}
} 
