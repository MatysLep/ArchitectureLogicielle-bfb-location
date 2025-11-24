package imt.archi.bfb.core.common.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Optional;

public enum VehicleState {
    AVAILABLE,
    RENT,
    BROKEN;

    @JsonCreator
    public static VehicleState fromString(String value) {
        if (value == null) return null;
        try {
            return VehicleState.valueOf(value.toUpperCase());
        }catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("invalid vehicle state: " + value);
        }
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }

    public static Optional<VehicleState> from(final String state) {
        try {
            return Optional.ofNullable(state)
                    .map(VehicleState::valueOf);
        } catch (final IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
