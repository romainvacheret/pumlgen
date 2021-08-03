package pumlgen.analysis.builders;

import java.util.HashSet;
import java.util.Set;

import pumlgen.analysis.summaries.ClassOrInterfaceSummary;
import pumlgen.analysis.summaries.MethodSummary;
import pumlgen.analysis.summaries.VariableSummary;

public class ClassOrInterfaceBuilder implements AbstractBuilder<ClassOrInterfaceSummary> {
	private String name;
	private boolean isInterface;
	private final Set<String> modifiers = new HashSet<>();
	private final Set<String> implementedTypes = new HashSet<>();
	private final Set<MethodSummary> methods = new HashSet<>();
	private final Set<VariableSummary> attributes = new HashSet<>();

	public ClassOrInterfaceBuilder withName(String name) { 
		this.name = name; 
		return this;
	}

	public ClassOrInterfaceBuilder withModifiers(Set<String> modifiers) {
		this.modifiers.addAll(modifiers);
		return this;
	}

	public ClassOrInterfaceBuilder withImplementedTypes(Set<String> implementedTypes) {
		this.implementedTypes.addAll(implementedTypes);
		return this;
	}

	public ClassOrInterfaceBuilder withMethods(Set<MethodSummary> methods) {
		this.methods.addAll(methods);
		return this;
	}

	public ClassOrInterfaceBuilder withAtrributes(Set<VariableSummary> attributes) {
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
		rtr.setMethods(methods);
		rtr.setAttributes(attributes);
		return rtr;
	}
}
