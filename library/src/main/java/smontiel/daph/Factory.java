package smontiel.daph;

public abstract class Factory<T> {

	protected void link(Linker linker) {
		//
	}
	
	public abstract T get();
}

