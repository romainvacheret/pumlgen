package pumlgen.analysis.summaries;

import lombok.Data;

import java.util.Set;

@Data
public class VariableSummary {
    private String name;
    private String type;
    private Set<String> modifiers;
}
