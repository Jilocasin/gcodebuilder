package de.jilocasin.gcodebuilder;

import lombok.Getter;

@Getter
public class OptionalGcodeParameter extends GcodeParameter {

    public OptionalGcodeParameter(String key, String value) {
        super(key, value);
    }

    public OptionalGcodeParameter(String key, Double value) {
        super(key, value);
    }

    public OptionalGcodeParameter(String key, Integer value) {
        super(key, value);
    }

    public OptionalGcodeParameter(String key, Boolean value) {
        super(key, value);
    }

    @Override
    public boolean isValid() {
        return getKey() != null;
    }

    @Override
    public boolean isIncluded() {
        return getValue() != null;
    }
}
