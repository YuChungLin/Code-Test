// Import Libaries
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.text.BreakIterator;
import java.util.Locale;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class Notebook{
   private static final String PDF_PATH = "./data/LINCOLN_CONTRACT.pdf";
   private static final String OUTPUT_PATH = "./data/terms_and_conditions_java.txt";


   // reads the pdf
   String read() throws Exception {
      PDDocument document = null;
      String text;
		try {
			// Read the raw PDF file
			  File file = new File(PDF_PATH);
			  document = Loader.loadPDF(file);
			  PDFTextStripper pdfStripper = new PDFTextStripper();
			  //Retrieving text from PDF document
			  text = pdfStripper.getText(document);
			
		} catch (IOException e) {
			throw new Exception("Error reading file " + PDF_PATH);
		} finally {
         if (document != null)
         {
             document.close();
         }
				  
		}
      return text;
   }
   
   // Breaks the string into sentences
	List<String> getSentences(String text) {

      // Break the text into sentences and then add it into a new string array
      List<String> new_text = new ArrayList<String>();
      BreakIterator iterator = BreakIterator.getSentenceInstance(Locale.US);
      iterator.setText(text);
      int boundary = iterator.first();

      while (boundary != BreakIterator.DONE) {
          int begin = boundary;
          boundary = iterator.next();
          int end = boundary;
		  
          if (end == BreakIterator.DONE) {
              break;
          }
		  
          new_text.add(text.substring(begin, end));
      }
	  
	  return new_text;
	}

   // Find and write the sentences that may represent the terms and conditions. 

   void writeAssociatedSentences(List<String> new_text) throws Exception {

      final String TERM = "term";
      final String TERMS = "terms";
      final String CONDITION = "condition";
      final String CONDITIONS = "conditions";
      final String MEANS = "means";
      final String WILL = "will";
      final String MUST = "must";
      final String HASTO = "has to";
      final String REQTO = "required to";
      final String ACKNW = "acknowledge";
      final String SHHAVE = "shall have";
      final String ENT = "entitled";
      final String LIA = "liable";
      final String OBL = "obligation";
      final String OBLIG = "obligated";

      // potential words that are associated with terms and conditions.
      Set<String> set2 = new HashSet<String>();
      // add the words to the set
      set2.add(TERM);
      set2.add(TERMS);
      set2.add(CONDITION);
      set2.add(CONDITIONS);
      set2.add(MEANS);
      set2.add(WILL);
      set2.add(MUST);
      set2.add(HASTO);
      set2.add(REQTO);
      set2.add(ACKNW);
      set2.add(SHHAVE);
      set2.add(ENT);
      set2.add(LIA);
      set2.add(OBL);
      set2.add(OBLIG);
      
      FileWriter writer = null;
	  try {
			  writer = new FileWriter(OUTPUT_PATH);

			  // check if sentences contain aforementioned words
			  for (String sentence : new_text) {
				 for (String term : set2) {
					// if contained, add the sentence to terms and conditions array
					if (sentence.contains(term)) {
					   // terms_and_conditions.add(sentence);
					   writer.write(sentence + System.lineSeparator());
					}
				 }
			  }
	  
	  		} catch (IOException e) {
			throw new Exception("Error writing to file " + OUTPUT_PATH);
		} finally {
         if (writer != null) {writer.close();}

		}
   }
}