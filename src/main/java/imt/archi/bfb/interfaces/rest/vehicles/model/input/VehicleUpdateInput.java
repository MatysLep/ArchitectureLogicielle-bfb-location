package imt.archi.bfb.interfaces.rest.vehicles.model.input;

import imt.archi.bfb.core.common.model.StateEnum;
import imt.archi.bfb.core.vehicles.model.Vehicle;
import imt.archi.bfb.interfaces.rest.common.model.input.AbstractInput;
import imt.archi.bfb.interfaces.rest.common.model.input.UpdatableProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.swing.plaf.nimbus.State;
import java.io.Serial;
import java.util.Date;

@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class VehicleUpdateInput extends AbstractInput {
    @Serial
    private static final long serialVersionUID = 1L;
    private UpdatableProperty<String> brand = UpdatableProperty.empty();
    private UpdatableProperty<String> model = UpdatableProperty.empty();
    private UpdatableProperty<String> motorization = UpdatableProperty.empty();
    private UpdatableProperty<String> color = UpdatableProperty.empty();
    private UpdatableProperty<Date> acquisitionDate = UpdatableProperty.empty();

    public void setState(final StateEnum state) {
        this.state = UpdatableProperty.makesChanges(state);
    }

    public void setAcquisitionDate(final Date acquisitionDate) {
        this.acquisitionDate = UpdatableProperty.makesChanges(acquisitionDate);
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

    private UpdatableProperty<StateEnum> state = UpdatableProperty.empty();

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
