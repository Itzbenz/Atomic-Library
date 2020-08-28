package Atom.Annotation.Builder;

public class MethodBuilder {
    public String name;
    public String[] modifier;
    public String[] arguments;
    public String data;

    public MethodBuilder(String modifier,String name,  String arguments, String data) {
        this.name = name.replace(" ", "");
        this.modifier = modifier.split(" ");
        this.arguments = arguments.split(" ");
        this.data = data;
        if(arguments.length() != 0)
            if(arguments.length() % 2 == 0)
                throw new IllegalArgumentException("Argument invalid: " + arguments);
    }

    public void addLine(String s){
        data = data + "\n" + s;
    }

    @Override
    public String toString() {
        StringBuilder mod = new StringBuilder();
        StringBuilder arg = new StringBuilder();
        for(String s : arguments)
            if(!s.isEmpty())
                arg.append(s).append(" ");
        for(String s : modifier)
            if(!s.isEmpty())
                mod.append(s).append(" ");
        return mod + name + "(" + arg + ")" + "{" + "\n" + data + "\n" + "}";
    }

}
