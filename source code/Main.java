import java.util.List;

// read pdf and output sentences related to terms and conditions
public class Main {
	
    public static void main(String args[]) {
		Notebook notebook = new Notebook();
		
		try {
			String text = notebook.read();
			List<String> sentences = notebook.getSentences(text);
			notebook.writeAssociatedSentences(sentences);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
