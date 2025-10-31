package imt.archi.bfb.infra.db.clients.repositories;

import imt.archi.bfb.infra.db.clients.repositories.entity.ClientEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends MongoRepository<ClientEntity, String> {
}
