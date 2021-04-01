package Atom.Struct;

@FunctionalInterface
public interface Filter<T> {
	
	
	boolean accept(T t);
	
	
}
