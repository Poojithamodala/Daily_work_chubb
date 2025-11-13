import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class assignment {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String filename="sample.txt";
		int c=0;
		try {
			BufferedReader br=new BufferedReader(new FileReader("C:/Users/pooji/eclipse-workspace/13th_Nov_Assignment/src/sample"));
			String line;
			while((line=br.readLine())!=null) {
				String[] w=line.split(" ");
				for(String k: w) {
					if(k.equalsIgnoreCase("India"))
						c++;
				}
			}
			System.out.println("Total count: "+c);
		} catch (IOException e) {
			System.out.println("Cannot read file");
		}

	}

}
