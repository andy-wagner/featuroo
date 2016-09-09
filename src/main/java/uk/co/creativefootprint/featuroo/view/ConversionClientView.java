package uk.co.creativefootprint.featuroo.view;

public class ConversionClientView {

    private String clientId;
    private String goal;

    public ConversionClientView(String clientId, String goal) {
        this.clientId = clientId;
        this.goal = goal;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }
}
