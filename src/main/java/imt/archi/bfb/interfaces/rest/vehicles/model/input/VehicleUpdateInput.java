package imt.archi.bfb.interfaces.rest.vehicles.model.input;

import com.fasterxml.jackson.annotation.JsonFormat;
import imt.archi.bfb.core.common.model.VehicleState;
import imt.archi.bfb.core.vehicles.model.Vehicle;
import imt.archi.bfb.interfaces.rest.common.model.input.AbstractInput;
import imt.archi.bfb.interfaces.rest.common.model.input.UpdatableProperty;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.util.Date;

@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class VehicleUpdateInput extends AbstractInput {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "Marque du véhicule", type = "string", example = "Tesla")
    private UpdatableProperty<String> brand = UpdatableProperty.empty();

    @Schema(description = "Modèle du véhicule", type = "string", example = "Model Y")
    private UpdatableProperty<String> model = UpdatableProperty.empty();

    @Schema(description = "Motorisation du véhicule", type = "string", example = "Electric")
    private UpdatableProperty<String> motorization = UpdatableProperty.empty();

    @Schema(description = "Couleur du véhicule", type = "string", example = "Rouge")
    private UpdatableProperty<String> color = UpdatableProperty.empty();

    @Schema(description = "État du véhicule", implementation = VehicleState.class)
    private UpdatableProperty<VehicleState> state = UpdatableProperty.empty();

    public void setState(final VehicleState state) {
        this.state = UpdatableProperty.makesChanges(state);
    }

    public void setColor(final String color) {
        this.color = UpdatableProperty.makesChanges(color);;
    }

    public void setMotorization(final String motorization) {
        this.motorization = UpdatableProperty.makesChanges(motorization);
    }

    public void setModel(final String model) {
        this.model = UpdatableProperty.makesChanges(model);
    }

    public void setBrand(final String brand) {
        this.brand = UpdatableProperty.makesChanges(brand);
    }

    public static Vehicle from(final VehicleUpdateInput input, final Vehicle alreadySaved) {
        return alreadySaved.toBuilder()
            .brand(input.getBrand().defaultIfNotOverwrite(alreadySaved.getBrand()))
            .model(input.getModel().defaultIfNotOverwrite(alreadySaved.getModel()))
            .motorization(input.getMotorization().defaultIfNotOverwrite(alreadySaved.getMotorization()))
            .color(input.getColor().defaultIfNotOverwrite(alreadySaved.getColor()))
            .state(input.getState().defaultIfNotOverwrite(alreadySaved.getState()))
            .build();
    }



}
