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
    private final Set<String> modifiers = new HashSet<>();
    private final List<String> attributes = new ArrayList<>();
    private final List<String> methods = new ArrayList<>();

    public UMLClassOrInterfaceBuilder(String name, boolean isInterface) {
        this.name = name;
        this.isInterface = isInterface;
    }


    public UMLClassOrInterfaceBuilder withMethod(MethodSummary method) {
        // TODO Add static/abstract
        methods.add(new StringBuilder(
            getVisibilityModifierSymbol(method.getModifiers()))
                .append(SPACE)
                .append(method.getName())
                .append(BRACKETS)
                .append(COLON)
                .append(SPACE)
                .append(method.getType())
                .toString());

        return this;
    }

    public UMLClassOrInterfaceBuilder withAttribute(VariableSummary attribute) {
        // TODO Add static/final
        attributes.add(new StringBuilder(
                getVisibilityModifierSymbol(attribute.getModifiers()))
                .append(SPACE)
                .append(attribute.getName())
                .append(COLON)
                .append(SPACE)
                .append(attribute.getType())
                .toString());

        return this;
    }

    public UMLClassOrInterfaceBuilder withMethods(Set<MethodSummary> methods) {
        methods.stream().forEach(method -> withMethod(method));
        return this;
    }

    public UMLClassOrInterfaceBuilder withAttributes(Set<VariableSummary> attributes) {
        attributes.stream().forEach(attribute -> withAttribute(attribute));
        return this;
    }

    @Override
    public String build() {
       return new StringBuilder(String.format("%s %s {", isInterface ? "interface" : "class", name))
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
