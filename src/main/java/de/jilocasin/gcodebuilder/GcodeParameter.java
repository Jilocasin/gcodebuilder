package de.jilocasin.gcodebuilder;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import lombok.Getter;

@Getter
public abstract class GcodeParameter {

    private static final int MAX_DOUBLE_DIGITS = 5;

    private static final DecimalFormat DOUBLE_FORMATTER = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));

    static {
        DOUBLE_FORMATTER.setMaximumFractionDigits(MAX_DOUBLE_DIGITS);
    }

    private final String key;

    private final String value;

    public GcodeParameter(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public GcodeParameter(String key, Double value) {
        this.key = key;
        this.value = value != null ? DOUBLE_FORMATTER.format(value) : null;
    }

    public GcodeParameter(String key, Integer value) {
        this.key = key;
        this.value = value != null ? String.valueOf(value) : null;
    }

    public GcodeParameter(String key, Boolean value) {
        this.key = key;
        this.value = value != null ? convertBoolean(value) : null;
    }

    public abstract boolean isValid();

    public abstract boolean isIncluded();

    @Override
    public String toString() {
        return key + value;
    }

    private String convertBoolean(boolean value) {
        return value ? "1" : "0";
    }
}
