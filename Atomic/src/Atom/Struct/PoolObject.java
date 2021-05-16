//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package Atom.Struct;


import java.util.ArrayList;
import java.util.List;

public abstract class PoolObject<T> {
    protected final int max;
    protected final ArrayList<T> freeObjects;
    protected int peak;
    
    public PoolObject() {
        this(16, 2147483647);
    }
    
    public PoolObject(int initialCapacity) {
        this(initialCapacity, 2147483647);
    }
    
    public PoolObject(int initialCapacity, int max) {
        this.freeObjects = new ArrayList<>(initialCapacity);
        this.max = max;
    }
    
    public int getPeak() {
        return peak;
    }
    
    public int getMax() {
        return max;
    }
    
    protected abstract T newObject();
    
    public T obtain() {
        return this.freeObjects.size() == 0 ? this.newObject() : this.freeObjects.remove(0);
    }
    
    public void free(T object) {
        if (object == null) {
            throw new IllegalArgumentException("object cannot be null.");
        }else {
            if (this.freeObjects.size() < this.max) {
                this.freeObjects.add(object);
                this.peak = Math.max(this.peak, this.freeObjects.size());
            }
            
            this.reset(object);
        }
    }
    
    protected void reset(T object) {
        if (object instanceof Object) {
            ((Object) object).reset();
        }
        
    }
    
    public void freeAll(List<T> objects) {
        if (objects == null) {
            throw new IllegalArgumentException("objects cannot be null.");
        }else {
            List<T> freeObjects = this.freeObjects;
            int max = this.max;
            for (T object : objects) {
                if (object != null) {
                    if (freeObjects.size() < max) {
                        freeObjects.add(object);
                    }
                    
                    this.reset(object);
                }
            }
            
            this.peak = Math.max(this.peak, freeObjects.size());
        }
    }
    
    public void clear() {
        this.freeObjects.clear();
    }
    
    public int getFree() {
        return this.freeObjects.size();
    }
    
    public interface Object {
        void reset();
    }
}
