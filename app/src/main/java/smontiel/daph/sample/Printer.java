package smontiel.daph.sample;


public class Printer
{
	private final Logger logger;

	public Printer(Logger logger, String a) {
		this.logger = logger;
	}
	
	public Printer(Logger logger) {
		this.logger = logger;
	}

	public Logger getLogger() {
		return logger;
	}
}
