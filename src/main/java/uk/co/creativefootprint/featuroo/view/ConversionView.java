package uk.co.creativefootprint.featuroo.view;

public class ConversionView {

    String value;
    String goal;

    public ConversionView(String value, String goal) {

        this.value = value;
        this.goal = goal;
    }

    public String getValue() {
        return value;
    }

    public String getGoal() {
        return goal;
    }
}