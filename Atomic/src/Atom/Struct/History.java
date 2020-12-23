package Atom.Struct;

public class History<T> {
	T Old, Neu;
	long old, neu;
	
	public History(T type) {
		Neu = type;
		neu = System.currentTimeMillis();
	}
	
	public T getOld() {
		return Old;
	}
	
	public T getNew() {
		return Neu;
	}
	
	public void setNew(T object) {
		old = neu;
		neu = System.currentTimeMillis();
		Old = Neu;
		Neu = object;
	}
	
	public long getOldTimestamp() {
		return old;
	}
	
	public long getNewTimestamp() {
		return neu;
	}
}
