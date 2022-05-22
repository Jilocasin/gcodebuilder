package de.jilocasin.gcodebuilder;

import lombok.Getter;

@Getter
public class RequiredGcodeParameter extends GcodeParameter {

    public RequiredGcodeParameter(String key, String value) {
        super(key, value);
    }

    public RequiredGcodeParameter(String key, Double value) {
        super(key, value);
    }

    public RequiredGcodeParameter(String key, Integer value) {
        super(key, value);
    }

    public RequiredGcodeParameter(String key, Boolean value) {
        super(key, value);
    }

    @Override
    public boolean isValid() {
        return getKey() != null && getValue() != null;
    }

    @Override
    public boolean isIncluded() {
        return true;
    }
}
