package uk.co.creativefootprint.featuroo.view;

import java.util.List;

public class ExperimentView {

    String name;
    List<String> alternatives;
    Double trafficFraction;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getAlternatives() {
        return alternatives;
    }

    public void setAlternatives(List<String> alternatives) {
        this.alternatives = alternatives;
    }

    public Double getTrafficFraction() {
        return trafficFraction;
    }

    public void setTrafficFraction(Double trafficFraction) {
        this.trafficFraction = trafficFraction;
    }

    public ExperimentView withName(final String name) {
        this.name = name;
        return this;
    }

    public ExperimentView withAlternatives(final List<String> alternatives) {
        this.alternatives = alternatives;
        return this;
    }

    public ExperimentView withFrafficFraction(final Double trafficFraction) {
        this.trafficFraction = trafficFraction;
        return this;
    }


}
