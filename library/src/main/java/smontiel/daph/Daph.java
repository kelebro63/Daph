package smontiel.daph;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import smontiel.daph.factories.MethodFactory;

public final class Daph
{
	private static Linker linker = new Linker();

	public void inject(Object target) {
		Class clazz = target.getClass();
		for(Field field : clazz.getDeclaredFields()) {

			if(field.isAnnotationPresent(Inject.class)) {
				linker.install(field.getType());

				Factory factory = linker.factoryFor(field.getType());

				try {
					if(!field.isAccessible()) field.setAccessible(true);

					//Field is instantied
					field.set(target, factory.get());
				}
				catch (IllegalAccessException|IllegalArgumentException e) {
					System.out.println(e);
				}
			}
		}
	}

	/**
     * @return an instance of type
     */
    public <T> T instance(Class<T> type) {
		T instance = linker.factoryFor(type).get();

        return instance;
    }

	public void modules(Object... targets) {
		for(Object target : targets) {
			Class<?> clazz = target.getClass();
			for(Method method : clazz.getDeclaredMethods()) {
				if(method.isAnnotationPresent(Provides.class)) {
					Class<?> returnType = method.getReturnType();
					Factory<?> factory = MethodFactory.of(method, target);
					
					linker.install(returnType, factory);
				}
			}
		}
	}

	public <T> void validate(Class<T> key) {
		linker.factoryFor(key);
	}
}

