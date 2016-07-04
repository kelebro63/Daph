package smontiel.daph.sample;

import smontiel.daph.Singleton;

@Singleton
public class Counter {
	private int count = 0;

	public void increment() {
		count++;
	}

	public int getCount() {
		return count;
	}
}

