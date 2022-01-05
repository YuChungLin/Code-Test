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
     public static void main(String args[]) throws IOException {
      // Read the raw PDF file
      File file = new File("./data/LINCOLN_CONTRACT.pdf");
      PDDocument document = Loader.loadPDF(file);
      PDFTextStripper pdfStripper = new PDFTextStripper();
      //Retrieving text from PDF document
      String text = pdfStripper.getText(document);
      //System.out.println(text);

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

      // turn strings into hashsets to easily find if the sentences contain words associatred with terms and conditions. 
      
      Set<String> set1 = new HashSet<String>(); //Load the text into set
      for (String sen : new_text) {
         set1.add(sen);
      }

      // potential words that are associated with terms and conditions.
      Set<String> set2 = new HashSet<String>();
      // add the words to the set
      set2.add("term");
      set2.add("terms");
      set2.add("condition");
      set2.add("conditions");
      set2.add("means");
      set2.add("must");
      set2.add("will");
      set2.add("has to");
      set2.add("required to");
      set2.add("acknowledge");
      set2.add("shall have");
      set2.add("entiled");
      set2.add("liable");
      set2.add("obligation");
      set2.add("obligated");

      List<String> terms_and_conditions = new ArrayList<String>();

      // check if sentences contain aforementioned words
      for (String sentence : set1) {
         for (String term : set2) {
            // if contained, add the sentence to terms and conditions array
            if (sentence.contains(term)) {
               terms_and_conditions.add(sentence);
            }
         }
      }
      //System.out.println(terms_and_conditions);

      // Write the terms and conditions into a file for easier access and reading
      FileWriter writer = new FileWriter("./data/terms_and_conditions_java.txt"); 
      for(String str: terms_and_conditions) {
        writer.write(str + System.lineSeparator());
      }
      writer.close();


      //Closing the document
      document.close();



   }
}