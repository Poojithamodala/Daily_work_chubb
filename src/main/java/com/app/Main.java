package com.app;

import org.apache.log4j.Logger;

public class Main {
	final static Logger logger = Logger.getLogger(Main.class);

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		logger.info("Application started");
        logger.debug("Debug message");
        logger.error("An error occurred");

	}

}
