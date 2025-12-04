package imt.archi.bfb.interfaces.rest.contracts.model.input;

import java.io.Serial;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import imt.archi.bfb.core.common.validators.DateValidator;
import io.swagger.v3.oas.annotations.media.Schema;
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return alreadySaved.toBuilder()
            .idClient(input.getIdClient().defaultIfNotOverwrite(alreadySaved.getIdClient()))
            .vehicleRegistration(input.getVehicleRegistration().defaultIfNotOverwrite(alreadySaved.getVehicleRegistration()))
            .startDate(
                    LocalDate.parse(
                            input.getStartDate().defaultIfNotOverwrite(alreadySaved.getStartDate().format(formatter)),
                            formatter)
            )
            .endDate(LocalDate.parse(
                    input.getEndDate().defaultIfNotOverwrite(alreadySaved.getEndDate().format(formatter)),
                    formatter))
            .state(input.getState().defaultIfNotOverwrite(alreadySaved.getState()))
            .build();
    }

    @Serial
    private static final long serialVersionUID = -6190479828349200043L;

    @Schema(description = "ID du client", type = "string")
    private UpdatableProperty<UUID> idClient = UpdatableProperty.empty();

    @Schema(description = "ID du véhicule", type = "string")
    private UpdatableProperty<String> vehicleRegistration = UpdatableProperty.empty();

    @Schema(description = "Date de début", example = "01/01/2000")
    private UpdatableProperty<String> startDate = UpdatableProperty.empty();

    @Schema(description = "Date de fin", example = "01/01/2000")
    private UpdatableProperty<String> endDate = UpdatableProperty.empty();

    @Schema(description = "Status du contrat", implementation = ContractState.class)
    private UpdatableProperty<ContractState> state = UpdatableProperty.empty();

    public void setIdClient(final UUID idClient) {
        this.idClient = UpdatableProperty.makesChanges(idClient);
    }

    public void setIdVehicle(final String idVehicle) {
        this.vehicleRegistration = UpdatableProperty.makesChanges(idVehicle);
    }

    public void setStartDate(final String startDate) {
        this.startDate = UpdatableProperty.makesChanges(startDate);
    }

    public void setEndDate(final String endDate) {
        this.endDate = UpdatableProperty.makesChanges(endDate);
    }

    public void setState(final ContractState state) {
        this.state = UpdatableProperty.makesChanges(state);
    }

    @JsonIgnore
    @DateValidator(required = true)
    public String getStartDateValidator() {
        if(this.startDate == null) return null;
        return this.startDate.getValue();
    }

    @JsonIgnore
    @DateValidator(required = true)
    public String getEndDateValidator() {
        if(this.endDate == null) return null;
        return this.endDate.getValue();
    }

}