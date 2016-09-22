package uk.co.creativefootprint.featuroo.view;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ClientView {

    @JsonProperty("client_id")
    String clientId;

    public ClientView() {
    }

    public String getClientId() {
        return clientId;
    }

    public ClientView withClientId(final String clientId) {
        this.clientId = clientId;
        return this;
    }


}
