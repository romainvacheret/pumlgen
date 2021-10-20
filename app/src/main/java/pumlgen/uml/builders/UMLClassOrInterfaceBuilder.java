package pumlgen.uml.builders;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import pumlgen.analysis.summaries.MethodSummary;
import pumlgen.analysis.summaries.VariableSummary;

public class UMLClassOrInterfaceBuilder extends UMLAbstractBuilder {
    private final String name;
    private final boolean isInterface;
    private final boolean isAbstract;
    private final List<String> attributes = new ArrayList<>();
    private final List<String> methods = new ArrayList<>();
    private final Set<String> implementedTypes = new HashSet<>();
    private final Set<String> extendedTypes = new HashSet<>();

    public UMLClassOrInterfaceBuilder(String name, boolean isInterface, boolean isAbstract) {
        this.name = name;
        this.isInterface = isInterface;
        this.isAbstract = isAbstract;
    }

    public UMLClassOrInterfaceBuilder withMethod(MethodSummary method) {
        methods.add(new StringBuilder(getStaticOrAbstractModifiers(method.getModifiers()))
                .append(getVisibilityModifierSymbol(method.getModifiers()))
                .append(SPACE)
                .append(method.getName())
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
        attributes.add(new StringBuilder(getStaticOrAbstractModifiers(attribute.getModifiers()))
                .append(getVisibilityModifierSymbol(attribute.getModifiers()))
                .append(SPACE)
                .append(attribute.getName())
                .append(COLON)
                .append(SPACE)
                .append(attribute.getType())
                .toString());

        return this;
    }

    public UMLClassOrInterfaceBuilder withMethods(Set<MethodSummary> methods) {
        methods.stream().forEach(this::withMethod);
        return this;
    }

    public UMLClassOrInterfaceBuilder withAttributes(Set<VariableSummary> attributes) {
        attributes.stream().forEach(this::withAttribute);
        return this;
    }

    public UMLClassOrInterfaceBuilder withExtendedTypes(Set<String> extendedTypes) {
        this.extendedTypes.addAll(extendedTypes);
        return this;
    }

    public UMLClassOrInterfaceBuilder withImplementedTypes(Set<String> implementedTypes) {
        this.implementedTypes.addAll(implementedTypes);
        return this;
    }

    private String getFormatedInheritance(Set<String> types, String keyword) {
        StringBuilder sb = new StringBuilder(types.isEmpty() ? "" : String.format("%s ", keyword));
        types.stream().map(str -> String.format("%s ", str)).forEach(sb::append);
        return sb.toString();
    }

    @Override
    public String build() {
       return new StringBuilder(String.format("%s %s %s ", isAbstract ? "abstract" : "", isInterface ? "interface" : "class", name))
            .append(getFormatedInheritance(extendedTypes, "extends"))
            .append(getFormatedInheritance(implementedTypes, "implements"))
            .append(OPENING_BRACE)
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
