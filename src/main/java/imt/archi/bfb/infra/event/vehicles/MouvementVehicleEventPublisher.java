package imt.archi.bfb.infra.event.vehicles;

import imt.archi.bfb.core.vehicles.model.Vehicle;
import imt.archi.bfb.infra.event.vehicles.model.MouvementVehicleEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class MouvementVehicleEventPublisher {
    private final ApplicationEventPublisher publisher;

    public void accept(final Vehicle vehicle){
        Objects.requireNonNull(vehicle,"Impossible de vérifier un véhicule nul");
        this.publisher.publishEvent(new MouvementVehicleEvent(this,vehicle.getRegistration()));
    }
}
