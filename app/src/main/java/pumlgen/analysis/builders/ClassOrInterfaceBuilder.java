package pumlgen.analysis.builders;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import pumlgen.analysis.summaries.ClassOrInterfaceSummary;
import pumlgen.analysis.summaries.MethodSummary;
import pumlgen.analysis.summaries.VariableSummary;

public class ClassOrInterfaceBuilder implements AbstractBuilder<ClassOrInterfaceSummary> {
	private String name;
	private boolean isInterface = false;
	private final Set<String> modifiers = new HashSet<>();
	private final Set<String> implementedTypes = new HashSet<>();
	private final Set<String> extendedTypes = new HashSet<>();
	private final Set<MethodSummary> methods = new HashSet<>();
	private final Set<VariableSummary> attributes = new HashSet<>();

	public ClassOrInterfaceBuilder withName(String name) {
		this.name = name;
		return this;
	}

	public ClassOrInterfaceBuilder withIsInterface(boolean isInterface) {
		this.isInterface = isInterface;
		return this;
	}

	public ClassOrInterfaceBuilder withModifiers(Set<String> modifiers) {
		this.modifiers.addAll(modifiers.stream()
			.map(modifier -> modifier.replace(" ", ""))
			.collect(Collectors.toSet()));
		return this;
	}

	public ClassOrInterfaceBuilder withImplementedTypes(Set<String> implementedTypes) {
		this.implementedTypes.addAll(implementedTypes);
		return this;
	}

	public ClassOrInterfaceBuilder withExtendedTypes(Set<String> extendedTypes) {
		this.extendedTypes.addAll(extendedTypes);
		return this;
	}

	public ClassOrInterfaceBuilder withMethods(Set<MethodSummary> methods) {
		this.methods.addAll(methods);
		return this;
	}

	public ClassOrInterfaceBuilder withAttributes(Set<VariableSummary> attributes) {
		this.attributes.addAll(attributes);
		return this;
	}

	@Override
	public ClassOrInterfaceSummary build() {
		final ClassOrInterfaceSummary rtr = new ClassOrInterfaceSummary();
		rtr.setName(name);
		rtr.setIsInterface(isInterface);
		rtr.setModifiers(modifiers);
		rtr.setImplementedTypes(implementedTypes);
		rtr.setExtendedTypes(extendedTypes);
		rtr.setMethods(methods);
		rtr.setAttributes(attributes);
		return rtr;
	}
}
