package smontiel.daph;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import smontiel.daph.factories.ReflectiveFactory;
import smontiel.daph.factories.SingletonFactory;

public final class Linker
{
	private final Map<Class<?>, Factory<?>> factories = new HashMap<>();
	private final Map<Class<?>, Factory<?>> linkedFactories = new HashMap<>();

	public <T> void install(final Class<T> key) {
		if (!isInstalled(key)) 
			install(key, factoryFor(key));
	} 

	public <T> void install(Class<T> key, Factory<T> factory) {
		factories.put(key, factory);
	}

	private <T> boolean isInstalled(Class<T> key) {
		if (factories.get(key) != null) 
			return true;
		else return false;
	}

	public <T> Factory<T> factoryFor(Class<T> key) {
		//	System.out.println("Get factory for " + key);
		Factory<?> factory = linkedFactories.get(key);

		if(factory == null) {
			//	System.out.println("Link factory for " + key);
			factory = loadFactory(key);
			factory.link(this);
			linkedFactories.put(key, factory);
		}

		return (Factory<T>) factory;
	}

	private <T> Factory<?> loadFactory(Class<T> key) {
		Factory<?> factory = factories.get(key);

		//return (factory == null) ? buildFactory(key) : factory;
		
		if(factory == null) {
			return buildFactory(key);
		}

		if(factory != null) {
			return factory;
		}
		
		throw new IllegalStateException("No factory for " + key);
	}

	private <T> Factory<T> buildFactory(final Class<T> key) {
		Factory<T> factory = null;//maybe change null
		Constructor<T> constructor = findUniqueConstructor(key);
		if (constructor != null) {
			factory = new ReflectiveFactory<T>(constructor);
			if (key.isAnnotationPresent(Singleton.class)) {
		 		factory = SingletonFactory.of(factory);
			}
		 	return factory;
		}
		return factory;
	}

	//Refactor: This method must search for a Inject annotation in one constructor
	private <T> Constructor<T> findUniqueConstructor(Class<T> type) {
		int numConstructors = type.getConstructors().length;
		if(numConstructors == 1) 
			return (Constructor<T>) type.getConstructors()[0];

		throw new IllegalStateException(type.getName() + " must have ONE constructor.");
	}
}

