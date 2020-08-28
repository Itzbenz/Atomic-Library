package Atom.Annotation.Builder;

public class VariableBuilder {
    public String name;
    public String[] modifier;
    public String type;
    public String data;

    public VariableBuilder(String modifier, String type, String name, String data) {
        this.modifier = modifier.split(" ");
        this.name = name.replace(" ", "");
        this.type = type.replace(" ", "");
        this.data = data.replace(" ", "");
    }

    @Override
    public String toString() {
        StringBuilder modifiers = new StringBuilder();
        for (String s : modifier) {
            if(s.isEmpty())
                continue;
            modifiers.append(s).append(" ");
        }
        return modifiers + type + " " + name + (data.isEmpty() ? ";" : " = " + data + ";");
    }
}
