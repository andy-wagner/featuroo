package uk.co.creativefootprint.featuroo.view;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ParticipationResultView {

    AlternativeView alternative;
    ExperimentView experiment;

    @JsonProperty("client_id")
    String clientId;

    String status;

    public ParticipationResultView(AlternativeView alternative, ExperimentView experiment, String clientId, String status) {
        this.alternative = alternative;
        this.experiment = experiment;
        this.clientId = clientId;
        this.status = status;
    }

    public AlternativeView getAlternative() {
        return alternative;
    }

    public ExperimentView getExperiment() {
        return experiment;
    }

    public String getClientId() {
        return clientId;
    }

    public String getStatus() {
        return status;
    }
}
