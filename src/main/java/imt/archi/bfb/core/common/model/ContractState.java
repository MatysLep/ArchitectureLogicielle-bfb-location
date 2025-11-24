package imt.archi.bfb.core.common.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Optional;

public enum ContractState { 
    PENDING,     // en attente
    IN_PROGRESS, // en cours
    COMPLETED,   // terminé
    DELAYED,     // en retard
    CANCELLED;   // annulé

    @JsonCreator
    public static ContractState fromString(String state) {
        if (state == null) return null;
        try {
            return ContractState.valueOf(state.toUpperCase());
        }catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("invalid contract state: " + state);
        }
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }

    public static Optional<ContractState> from(final String state) {
        try {
            return Optional.ofNullable(state)
                    .map(ContractState::valueOf);
        } catch (final IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
