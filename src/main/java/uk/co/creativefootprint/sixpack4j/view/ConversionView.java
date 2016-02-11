package uk.co.creativefootprint.sixpack4j.view;

public class ConversionView {

    String value;
    String kpi;

    public ConversionView(String value, String kpi) {

        this.value = value;
        this.kpi = kpi;
    }

    public String getValue() {
        return value;
    }

    public String getKpi() {
        return kpi;
    }
}
