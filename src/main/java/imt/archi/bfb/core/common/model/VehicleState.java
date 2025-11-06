package imt.archi.bfb.core.common.model;

import java.util.Optional;

public enum VehicleState {
    AVAILABLE,
    RENT,
    BROKEN;

    public static VehicleState fromOrDefault(final String state) {
        return VehicleState.from(state).orElse(VehicleState.AVAILABLE);
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
