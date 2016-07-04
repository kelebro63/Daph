package smontiel.daph.factories;

import smontiel.daph.Factory;

public final class ValueFactory<T> extends Factory<T>
{
	public static <T> Factory<T> of(T instance) {
		return new ValueFactory<T>(instance);
	}

	private final T value;

	private ValueFactory(T value) {
		this.value = value;
	}

	@Override
	public T get() {
		return value;
	}
}

