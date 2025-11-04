package imt.archi.bfb.infra.db.vehicles.repositories.mappers;

import imt.archi.bfb.core.vehicles.model.Vehicle;
import imt.archi.bfb.infra.db.common.model.mappers.AbstractDbMapper;
import imt.archi.bfb.infra.db.vehicles.repositories.entities.VehicleEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Mapper pour convertir entre les objets métier Vehicule et les entités VehicleEntity
 */

@Service
@AllArgsConstructor
public class VehicleDbMapper extends AbstractDbMapper<Vehicle, VehicleEntity> {

    @Override
    public Vehicle from(final VehicleEntity input) {
        return Vehicle.builder()
                .registration(input.getRegistration())
                .brand(input.getBrand())
                .model(input.getModel())
                .motorization(input.getMotorization())
                .color(input.getColor())
                .acquisitionDate(input.getAcquisitionDate())
                .state(input.getState())
                .build();
    }

    @Override
    public VehicleEntity to(final Vehicle vehicle) {
        return VehicleEntity.builder()
                .registration(vehicle.getRegistration())
                .brand(vehicle.getBrand())
                .model(vehicle.getModel())
                .motorization(vehicle.getMotorization())
                .color(vehicle.getColor())
                .acquisitionDate(vehicle.getAcquisitionDate())
                .state(vehicle.getState())
                .build();
    }
}