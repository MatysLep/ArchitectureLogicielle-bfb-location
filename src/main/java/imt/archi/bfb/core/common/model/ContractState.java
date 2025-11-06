package imt.archi.bfb.core.common.model;

import java.util.Optional;

public enum ContractState { 
    PENDING,     // en attente
    IN_PROGRESS, // en cours
    COMPLETED,   // terminé
    DELAYED,     // en retard
    CANCELLED;   // annulé

    public static ContractState fromOrDefault(final String state) {
        return ContractState.from(state).orElse(ContractState.PENDING);
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
