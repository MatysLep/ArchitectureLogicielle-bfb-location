package imt.archi.bfb.infra.db.vehicles;

import imt.archi.bfb.core.vehicles.model.Vehicle;
import imt.archi.bfb.infra.db.vehicles.repositories.VehicleRepository;
import imt.archi.bfb.infra.db.vehicles.repositories.entities.VehicleEntity;
import imt.archi.bfb.infra.db.vehicles.repositories.mappers.VehicleDbMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VehiclesDbService {

    private VehicleRepository vehicleRepository;

    private VehicleDbMapper vehicleDbMapper;

    public boolean exist(final String registration){
        return Optional.ofNullable(registration)
                .map(vehicleRepository::existsById)
                .orElse(false);
    }

    public Collection<Vehicle> getAll(){
        return Objects.requireNonNullElse(vehicleRepository.findAll(), Collections.<VehicleEntity>emptyList())
                .stream()
                .map(vehicleDbMapper::from)
                .collect(Collectors.toSet());
    }

    public Optional<Vehicle> get(final String registration){
        return Optional.ofNullable(registration)
                .flatMap(vehicleRepository::findById)
                .map(vehicleDbMapper::from);
    }

    public Vehicle save(final Vehicle vehicle){
        Objects.requireNonNull(vehicle, "vehicle is null");
        return vehicleDbMapper.from(vehicleRepository.save(vehicleDbMapper.to(vehicle)));
    }

    public void delete(final String registration){
        Optional.ofNullable(registration)
                .ifPresent(vehicleRepository::deleteById);
    }

}
