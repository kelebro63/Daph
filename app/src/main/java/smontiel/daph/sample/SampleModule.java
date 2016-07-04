package smontiel.daph.sample;

import smontiel.daph.Provides;
import smontiel.daph.Singleton;

public class SampleModule
{
	/*@Provides @Singleton public Counter getCounter() {
		return new Counter();
	}*/
	
	@Provides public Printer getPrinter(Logger logger) {
		
		return new Printer(logger);
	}
}
