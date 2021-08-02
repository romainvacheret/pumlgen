package pumlgen.analysis.summaries;

import java.util.Set;

import lombok.Data;

@Data
public class ClassOrInterfaceSummary {
	private Boolean isInterface;
	private String name;
	private Set<String> modifiers;
	private Set<String> implementedTypes;
	private Set<MethodSummary> methods;
	private Set<VariableSummary> attributes;
}
