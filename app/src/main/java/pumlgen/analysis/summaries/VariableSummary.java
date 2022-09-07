package pumlgen.analysis.summaries;

import java.util.HashSet;
import java.util.Set;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VariableSummary {
    private String name;
    private String type;
    @Builder.Default
    private Set<String> modifiers = new HashSet<>();
}
