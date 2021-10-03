package pumlgen.uml.builders;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import pumlgen.analysis.summaries.ClassOrInterfaceSummary;

public class UMLPackageBuilder extends UMLAbstractBuilder {
    private final String packageName;
    private final Set<ClassOrInterfaceSummary> classOrInterfaceSummaries;
    private final Set<UMLPackageBuilder> subPackages;

    public UMLPackageBuilder(String packageName) { 
        this.packageName = packageName; 
        this.classOrInterfaceSummaries = new HashSet<>();
        this.subPackages = new HashSet<>();
    }
    
    public UMLPackageBuilder withClassesOrInterfaces(Set<ClassOrInterfaceSummary> elements) {
        classOrInterfaceSummaries.addAll(elements);
        return this;
    }

    public UMLPackageBuilder withSubPackages(Set<UMLPackageBuilder> subPackages) {
        this.subPackages.addAll(subPackages);
        return this;
    }

    @Override
    public String build() {
        return new StringBuilder(String.format("%npackage %s {", packageName))
            .append(NEW_LINE)
            .append(NEW_LINE)
            .append(buildClassOrInterfaceSummaries(classOrInterfaceSummaries)
                .stream().collect(Collectors.joining("\n\n")))
            .append(buildSubPackages())
            .append(NEW_LINE)
            .append(NEW_LINE)
            .append(CLOSING_BRACE)
            .toString();
    }

    public UMLPackageBuilder merge(final UMLPackageBuilder builder) {
        System.out.println("merge " + packageName + " " + builder.getPackageName());
        if(packageName.equals(builder.getPackageName())) {
            classOrInterfaceSummaries.addAll(builder.getClassOrInterfaceSummaries());
        } else {
            subPackages.add(builder);
        }

        return this;
    }

    Set<ClassOrInterfaceSummary> getClassOrInterfaceSummaries() { return classOrInterfaceSummaries; }

    Set<UMLPackageBuilder> getSubPackages() { return subPackages; }

    String getPackageName() { return packageName; }

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

    private String buildSubPackages() {
        System.out.println("sub count: " + packageName + " " + subPackages.size());
        return subPackages.stream()
            .map(UMLPackageBuilder::build)
            .collect(Collectors.joining(NEW_LINE));
    }
}
