package pumlgen.analysis.builders;

import pumlgen.analysis.summaries.VariableSummary;

public class VariableBuilder implements AbstractBuilder<VariableSummary> {
    private String name;
    private String type;

    public VariableBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public VariableBuilder withType(String type) {
        this.type = type;
        return this;
    }

    public VariableSummary build() {
        final VariableSummary rtr = new VariableSummary();
        rtr.setName(name);
        rtr.setType(type);

        return rtr;
    }
}
