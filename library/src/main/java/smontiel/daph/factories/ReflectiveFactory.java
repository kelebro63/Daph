package smontiel.daph.factories;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

import smontiel.daph.Factory;
import smontiel.daph.Linker;

public class ReflectiveFactory<T> extends Factory<T>
{
	private final Constructor<T> constructor;
	private final ArrayList<Factory<?>> factories = new ArrayList<>();

	public ReflectiveFactory(Constructor<T> constructor) {
		this.constructor = constructor;
	}

	@Override
	protected void link(Linker linker) {
		for(Class<?> parameterType : constructor.getParameterTypes()) {
			factories.add(linker.factoryFor(parameterType));
		}
	}

	@Override
	public T get() {
		Object[] dependencies = new Object[factories.size()];
		for(int i = 0;i < dependencies.length; i++) {
			Factory<?> factory = factories.get(i);
			dependencies[i] = factory.get();
		}

		try {
			return constructor.newInstance(dependencies);
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
}

