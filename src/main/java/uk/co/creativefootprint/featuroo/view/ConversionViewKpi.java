package uk.co.creativefootprint.featuroo.view;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConversionViewKpi extends ConversionView {

    String value;
    String kpi;

    public ConversionViewKpi(String value, String kpi) {
        super(value, null);

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
