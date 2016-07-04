package smontiel.daph.factories;

import smontiel.daph.Factory;

public final class SingletonFactory<T> extends Factory<T>
{
	public static <T> Factory<T> of(Factory<T> factory) {
		return new SingletonFactory<T>(factory);
	}

	private final Factory<T> factory;
	private T instance;

	private SingletonFactory(Factory<T> factory) {
		this.factory = factory;
	}

	@Override
	public final T get() {
		if(instance == null) 
			instance = factory.get();
		return instance;
	}
}

