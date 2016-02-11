package uk.co.creativefootprint.sixpack4j.view;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ParticipationView {

    AlternativeView alternative;
    ExperimentView experiment;

    @JsonProperty("client_id")
    String clientId;

    String status;

    public ParticipationView(AlternativeView alternative, ExperimentView experiment, String clientId, String status) {
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
