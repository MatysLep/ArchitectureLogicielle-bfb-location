package imt.archi.bfb.interfaces.rest.contracts.model.input;

import java.io.Serial;
import java.time.LocalDate;

import lombok.*;

import imt.archi.bfb.core.common.model.ContractState;
import imt.archi.bfb.core.contracts.model.Contract;
import imt.archi.bfb.interfaces.rest.common.model.input.AbstractInput;
import imt.archi.bfb.interfaces.rest.common.model.input.UpdatableProperty;

@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class ContractUpdateInput extends AbstractInput {

    public static Contract from(final ContractUpdateInput input, final Contract alreadySaved) {
        return alreadySaved.toBuilder()
            .idClient(input.getIdClient().defaultIfNotOverwrite(alreadySaved.getIdClient()))
            .idVehicle(input.getIdVehicle().defaultIfNotOverwrite(alreadySaved.getIdVehicle()))
            .startDate(input.getStartDate().defaultIfNotOverwrite(alreadySaved.getStartDate()))
            .endDate(input.getEndDate().defaultIfNotOverwrite(alreadySaved.getEndDate()))
            .state(input.getState().defaultIfNotOverwrite(alreadySaved.getState()))
            // .state(input.getState().defaultIfNotOverwrite(ContractState::fromOrDefault, alreadySaved.getState())) // TODO voir pk different du prof 
            .build();
    }

    @Serial
    private static final long serialVersionUID = -6190479828349200043L;
    private UpdatableProperty<String> idClient = UpdatableProperty.empty();;
    private UpdatableProperty<String> idVehicle = UpdatableProperty.empty();;
    private UpdatableProperty<LocalDate> startDate = UpdatableProperty.empty();;
    private UpdatableProperty<LocalDate> endDate = UpdatableProperty.empty();;
    private UpdatableProperty<ContractState> state = UpdatableProperty.empty();;

    public void setIdClient(final String idClient) {
        this.idClient = UpdatableProperty.makesChanges(idClient);
    }

    public void setIdVehicle(final String idVehicle) {
        this.idVehicle = UpdatableProperty.makesChanges(idVehicle);
    }

    public void setStartDate(final LocalDate startDate) {
        this.startDate = UpdatableProperty.makesChanges(startDate);
    }

    public void setEndDate(final LocalDate endDate) {
        this.endDate = UpdatableProperty.makesChanges(endDate);
    }

    public void setState(final ContractState state) {
        this.state = UpdatableProperty.makesChanges(state);
    }

}