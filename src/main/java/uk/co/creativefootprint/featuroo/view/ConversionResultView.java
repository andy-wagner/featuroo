package uk.co.creativefootprint.featuroo.view;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConversionResultView {

    AlternativeView alternative;
    ExperimentView experiment;
    ConversionView conversion;

    @JsonProperty("client_id")
    String clientId;

    public ConversionResultView(AlternativeView alternative, ExperimentView experiment, ConversionView conversion, String clientId) {
        this.alternative = alternative;
        this.experiment = experiment;
        this.conversion = conversion;
        this.clientId = clientId;
    }

    public AlternativeView getAlternative() {
        return alternative;
    }

    public ExperimentView getExperiment() {
        return experiment;
    }

    public ConversionView getConversion() {
        return conversion;
    }

    public String getClientId() {
        return clientId;
    }
}
