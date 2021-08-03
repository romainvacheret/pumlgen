package pumlgen.analysis.builders;

import pumlgen.analysis.summaries.VariableSummary;

import java.util.HashSet;
import java.util.Set;

public class VariableBuilder implements AbstractBuilder<VariableSummary> {
    private String name;
    private String type;
    private final Set<String> modifiers = new HashSet<>();

    public VariableBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public VariableBuilder withType(String type) {
        this.type = type;
        return this;
    }

    public VariableBuilder withModifiers(Set<String> modifiers) {
        this.modifiers.addAll(modifiers);
        return this;
    }

    public VariableSummary build() {
        final VariableSummary rtr = new VariableSummary();
        rtr.setName(name);
        rtr.setType(type);
        rtr.setModifiers(modifiers);

        return rtr;
    }
}
