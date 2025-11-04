package imt.archi.bfb.infra.db.vehicles.repositories;

import imt.archi.bfb.infra.db.vehicles.repositories.entities.VehicleEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VehicleRepository extends MongoRepository<VehicleEntity, String> {
}