package pumlgen.uml.builders;

import pumlgen.analysis.summaries.MethodSummary;
import pumlgen.analysis.summaries.VariableSummary;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UMLClassOrInterfaceBuilder extends UMLAbstractBuilder {
    private final String name;
    private final boolean isInterface;
    private final boolean isAbstract;
    private final Set<String> modifiers = new HashSet<>();
    private final List<String> attributes = new ArrayList<>();
    private final List<String> methods = new ArrayList<>();

    public UMLClassOrInterfaceBuilder(String name, boolean isInterface, boolean isAbstract) {
        this.name = name;
        this.isInterface = isInterface;
        this.isAbstract = isAbstract;
    }


    public UMLClassOrInterfaceBuilder withMethod(MethodSummary method) {
        // TODO Add static/abstract
        methods.add(new StringBuilder(
            getVisibilityModifierSymbol(method.getModifiers()))
                .append(SPACE)
                .append(method.getName())
                .append(SPACE)
                .append("(")
                .append(method.getParameters().stream()
                    .map(parameter -> String.format("%s: %s", parameter.getName(), parameter.getType()))
                    .collect(Collectors.joining(", ")))
                .append(")")
                .append(COLON)
                .append(SPACE)
                .append(method.getType())
                .toString());

        return this;
    }

    public UMLClassOrInterfaceBuilder withAttribute(VariableSummary attribute) {
        // TODO Add static/final
        attributes.add(new StringBuilder()
                .append(getVisibilityModifierSymbol(attribute.getModifiers()))
                .append(SPACE)
                .append(attribute.getName())
                .append(COLON)
                .append(SPACE)
                .append(attribute.getType())
                .toString());

        return this;
    }

    //TODO Add constructors
    public UMLClassOrInterfaceBuilder withMethods(Set<MethodSummary> methods) {
        methods.stream().forEach(this::withMethod);
        return this;
    }

    public UMLClassOrInterfaceBuilder withAttributes(Set<VariableSummary> attributes) {
        attributes.stream().forEach(this::withAttribute);
        return this;
    }

    @Override
    public String build() {
       return new StringBuilder(String.format("%s %s %s {", isAbstract ? "abstract" : "", isInterface ? "interface" : "class", name))
           .append(NEW_LINE)
           .append(TAB)
           .append(attributes.stream().collect(Collectors.joining(TABBED_NEW_LINE)))
           .append(TABBED_NEW_LINE)
           .append(methods.stream().collect(Collectors.joining(TABBED_NEW_LINE)))
           .append(NEW_LINE)
           .append(CLOSING_BRACE)
           .toString();
    }
}
