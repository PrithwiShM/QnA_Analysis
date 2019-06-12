import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class checker
{
	public static void main ( String args [])
	{
		BufferedReader br = null;
		SearchEngine r = new SearchEngine();

		try {
			String actionString;
			br = new BufferedReader(new FileReader("que3"));
			int j = 1;
			int count = 0 ;
			while ((actionString = br.readLine()) != null) {
				System.out.print("que"+ j + ":  ");
				if(r.performAction(actionString, ("ans" + j) ))
					count = count + 1;
				j = j +1;

			}
			System.out.println("module3_accuracy: " +((float) 100*count/121));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}
}
