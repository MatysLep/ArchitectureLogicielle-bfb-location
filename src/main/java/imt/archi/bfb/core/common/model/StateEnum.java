package imt.archi.bfb.core.common.model;

import java.util.Optional;

public enum StateEnum {
    AVAILABLE,
    RENT,
    BROKEN;

    public static StateEnum fromOrDefault(final String state) {
        return StateEnum.from(state).orElse(StateEnum.AVAILABLE);
    }

    public static Optional<StateEnum> from(final String state) {
        try {
            return Optional.ofNullable(state)
                    .map(StateEnum::valueOf);
        } catch (final IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
