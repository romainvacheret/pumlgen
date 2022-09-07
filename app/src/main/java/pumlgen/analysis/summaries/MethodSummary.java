package pumlgen.analysis.summaries;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MethodSummary {
    private String name;
    private String type;
    @Builder.Default
    private Set<String> modifiers = new HashSet<>();
    @Builder.Default
    private List<VariableSummary> parameters = new ArrayList<>();
}
