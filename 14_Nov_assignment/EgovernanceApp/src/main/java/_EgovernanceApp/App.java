package _EgovernanceApp;
import org.apache.log4j.Logger;
public class App {
	final static Logger logger = Logger.getLogger(App.class);

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Hello, Gradle!");
		logger.info("Application started");
        logger.debug("Debug message");
        logger.error("An error occurred");
		
	}

}
