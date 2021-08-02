package pumlgen.analysis.builders;

import pumlgen.analysis.summaries.MethodSummary;
import pumlgen.analysis.summaries.VariableSummary;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MethodBuilder implements AbstractBuilder<MethodSummary> {
    private String name;
    private String type;
    private final Set<String> modifiers = new HashSet<>();
    private final List<VariableSummary> parameters = new ArrayList<>();

    public MethodBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public MethodBuilder withType(String type) {
        this.type = type;
        return this;
    }

    public MethodBuilder withModifiers(Set<String> modifiers) {
        this.modifiers.addAll(modifiers);
        return this;
    }

    public MethodBuilder withParameters(List<VariableSummary> parameters) {
        this.parameters.addAll(parameters);
        return this;
    }

    public MethodSummary build() {
        final MethodSummary rtr = new MethodSummary();
        rtr.setName(name);
        rtr.setType(type);
        rtr.setModifiers(modifiers);
        rtr.setParameters(parameters);

        return rtr;
    }


}
