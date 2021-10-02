package pumlgen.uml.builders;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import pumlgen.analysis.summaries.ClassOrInterfaceSummary;

public class UMLPackageBuilder extends UMLAbstractBuilder {
    private final String packageName;
    private final Set<ClassOrInterfaceSummary> classOrInterfaceSummaries;

    public UMLPackageBuilder(String packageName) { 
        this.packageName = packageName; 
        this.classOrInterfaceSummaries = new HashSet<>();
    }
    
    public UMLPackageBuilder withClassesOrInterfaces(Set<ClassOrInterfaceSummary> elements) {
        classOrInterfaceSummaries.addAll(elements);
        return this;
    }

    @Override
    public String build() {
        return new StringBuilder(String.format("package %s {", packageName))
            .append(NEW_LINE)
            .append(NEW_LINE)
            .append(buildClassOrInterfaceSummaries(classOrInterfaceSummaries)
                .stream().collect(Collectors.joining("\n\n")))
            .append(NEW_LINE)
            .append(CLOSING_BRACE)
            .toString();
    }

    private Set<String> buildClassOrInterfaceSummaries(Set<ClassOrInterfaceSummary> summaries) {
        return summaries.stream().map(this::buildClassOrInterfaceSummary).collect(Collectors.toSet());
    }

    private String buildClassOrInterfaceSummary(ClassOrInterfaceSummary summary) {
        return new UMLClassOrInterfaceBuilder(
            summary.getName(),
            summary.getIsInterface(),
            summary.getModifiers().contains("abstract"))
                .withMethods(summary.getMethods())
                .withAttributes(summary.getAttributes())
                .build();
    }
}
