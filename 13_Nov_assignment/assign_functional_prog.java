import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

public class assign_functional_prog {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Stream<String> lines = Files.lines(Paths.get("C:/Users/pooji/eclipse-workspace/13th_Nov_Assignment/src/sample"));
			Stream<String> words=lines.flatMap(line->Arrays.stream(line.split(" ")));
			Stream<String> wordIndia=words.filter(word->word.equalsIgnoreCase("India"));
			long count=wordIndia.count();
			System.out.println("Count is: "+count);
		} catch (IOException e) {
			e.printStackTrace();
		}
		}

}
