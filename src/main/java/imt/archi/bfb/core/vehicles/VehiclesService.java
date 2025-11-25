package imt.archi.bfb.core.vehicles;

import imt.archi.bfb.core.common.model.VehicleState;
import imt.archi.bfb.core.vehicles.model.Vehicle;
import imt.archi.bfb.infra.db.vehicles.VehiclesDbService;
import imt.archi.bfb.infra.event.vehicles.MouvementVehicleEventPublisher;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class VehiclesService {
    protected VehiclesDbService vehiclesDbService;
    private final MouvementVehicleEventPublisher vehicleEventPublisher;

    public Collection<Vehicle> findAll() {
        return Objects.requireNonNullElse(vehiclesDbService.getAll(), Collections.emptySet());
    }

    public Optional<Vehicle> getOne(final String registration) {
        return vehiclesDbService.get(registration);
    }

    public Vehicle create(final Vehicle vehicle) {return vehiclesDbService.save(vehicle); }

    public void update(final Vehicle vehicle) {
        if (vehicle.getState() == VehicleState.BROKEN) {
            vehiclesDbService.get(vehicle.getRegistration())
                    .ifPresent(existingVehicle -> vehicleEventPublisher.accept(vehicle));
        }
        vehiclesDbService.save(vehicle);
    }

    public void delete(final String registration) {
        vehiclesDbService.delete(registration);
    }
}
