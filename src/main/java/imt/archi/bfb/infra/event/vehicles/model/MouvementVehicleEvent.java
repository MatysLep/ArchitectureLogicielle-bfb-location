package imt.archi.bfb.infra.event.vehicles.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
public class MouvementVehicleEvent extends ApplicationEvent {
    private final String registration;

    public MouvementVehicleEvent(final Object source, final String registration) {
        super(source);
        this.registration = registration;
    }
}
