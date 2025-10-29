package imt.archi.bfb.core.vehicles;

import imt.archi.bfb.core.vehicles.model.Vehicle;
import imt.archi.bfb.infra.db.vehicles.VehiclesDbService;
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

    public Collection<Vehicle> findAll() {
        return Objects.requireNonNullElse(vehiclesDbService.getAll(), Collections.emptySet());
    }

    public Optional<Vehicle> getOne(final String registration) {
        return vehiclesDbService.get(registration);
    }

    public Vehicle create(final Vehicle vehicle) {return vehiclesDbService.save(vehicle); }
}
