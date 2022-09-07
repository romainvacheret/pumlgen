package pumlgen.analysis.summaries;

import java.util.HashSet;
import java.util.Set;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClassOrInterfaceSummary {
	private Boolean isInterface;
	private String name;
	@Builder.Default
	private Set<String> modifiers = new HashSet<>();
	@Builder.Default
	private Set<String> implementedTypes = new HashSet<>();
	@Builder.Default
	private Set<String> extendedTypes = new HashSet<>();
	@Builder.Default
	private Set<MethodSummary> methods = new HashSet<>();
	@Builder.Default
	private Set<VariableSummary> attributes = new HashSet<>();
	@Builder.Default
	private Set<MethodSummary> constructors = new HashSet<>();
}