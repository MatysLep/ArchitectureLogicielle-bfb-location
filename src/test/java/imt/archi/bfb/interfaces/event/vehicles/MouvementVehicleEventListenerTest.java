package imt.archi.bfb.interfaces.event.vehicles;

import imt.archi.bfb.core.contracts.ContractsService;
import imt.archi.bfb.infra.event.vehicles.model.MouvementVehicleEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MouvementVehicleEventListenerTest {

    @Mock
    private ContractsService contractsService;

    @InjectMocks
    private MouvementVehicleEventListener listener;

    @Test
    void testOnApplicationEvent() {
        // Arrange
        String registration = "AB-123-CD";
        // On simule la source de l'événement (peut être n'importe quel objet)
        Object source = new Object();
        MouvementVehicleEvent event = new MouvementVehicleEvent(source, registration);

        // Act
        // Appel direct de la méthode (l'aspect @Async est géré par Spring au runtime,
        // mais ici on teste la logique interne de la méthode)
        listener.onApplicationEvent(event);

        // Assert
        // Vérifie que le service a été appelé avec la bonne immatriculation
        verify(contractsService, times(1)).cancelContracts(registration);
    }
}