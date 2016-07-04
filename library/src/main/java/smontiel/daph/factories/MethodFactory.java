package smontiel.daph.factories;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import smontiel.daph.Factory;
import smontiel.daph.Linker;
import smontiel.daph.Singleton;

public class MethodFactory<T> extends Factory<T>
{
	public static <T> Factory<T> of(Method method, Object target) {
		Factory<T> defaultFactory = new MethodFactory<T>(method, target);
		if(method.isAnnotationPresent(Singleton.class)) 
			return SingletonFactory.of(defaultFactory);
			
		return defaultFactory;
	}
	
	private final Method method;
	private final Object target;
	private final ArrayList<Factory<?>> factories = new ArrayList<>();

	private MethodFactory(Method method, Object target) {
		this.method = method;
		this.target = target;
	}

	@Override
	protected void link(Linker linker) {
		for(Class<?> parameterType : method.getParameterTypes()) {
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
			return (T) method.invoke(target, dependencies);
		}
		catch (IllegalAccessException|IllegalArgumentException|InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}
}
