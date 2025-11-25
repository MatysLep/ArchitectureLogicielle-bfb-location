package imt.archi.bfb.infra.event.model;

import imt.archi.bfb.infra.event.vehicles.model.MouvementVehicleEvent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class MouvementVehicleEventTest {

    @Test
    void testEventCreation() {
        // Arrange
        String registration = "XX-999-ZZ";
        Object source = "TestSource";

        // Act
        MouvementVehicleEvent event = new MouvementVehicleEvent(source, registration);

        // Assert
        assertEquals(registration, event.getRegistration());
        assertEquals(source, event.getSource());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        Object source = "Source";
        MouvementVehicleEvent event1 = new MouvementVehicleEvent(source, "A");
        MouvementVehicleEvent event2 = new MouvementVehicleEvent(source, "A");
        MouvementVehicleEvent event3 = new MouvementVehicleEvent(source, "B");

        // Assert & Act
        // Test d'égalité
        assertEquals(event1, event2, "Deux événements avec les mêmes données devraient être égaux");
        assertNotEquals(event1, event3, "Deux événements avec des immatriculations différentes ne devraient pas être égaux");

        // Test du HashCode
        assertEquals(event1.hashCode(), event2.hashCode(), "Les hashcodes devraient être identiques pour des objets égaux");
    }
}