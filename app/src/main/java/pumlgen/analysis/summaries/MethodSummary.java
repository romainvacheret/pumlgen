package pumlgen.analysis.summaries;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class MethodSummary {
    private String name;
    private String type;
    private Set<String> modifiers;
    private List<VariableSummary> parameters;
}
