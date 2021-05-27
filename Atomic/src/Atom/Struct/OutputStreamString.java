package Atom.Struct;

import java.io.IOException;
import java.io.OutputStream;

public class OutputStreamString extends OutputStream {
    protected StringBuilder stringBuilder;
    
    public OutputStreamString(StringBuilder sb) {
        stringBuilder = sb;
    }
    
    public OutputStreamString() {
        this(new StringBuilder());
    }
    
    @Override
    public void write(int b) throws IOException {
        stringBuilder.append((char) b);
    }
    
    public StringBuilder getStringBuilder() {
        return stringBuilder;
    }
    
    @Override
    public String toString() {
        return stringBuilder.toString();
    }
}
