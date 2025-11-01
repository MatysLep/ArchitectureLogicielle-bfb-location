package imt.archi.bfb.infra.db.clients;

import imt.archi.bfb.core.clients.model.Client;
import imt.archi.bfb.infra.db.clients.repositories.ClientRepository;
import imt.archi.bfb.infra.db.clients.repositories.entity.ClientEntity;
import imt.archi.bfb.infra.db.clients.repositories.mappers.ClientBddMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("ClientBddService - interactions avec la base")
class ClientBddServiceTest {

    @Mock
    private ClientRepository repository;

    @Mock
    private ClientBddMapper mapper;

    @InjectMocks
    private ClientBddService clientBddService;

    private Client client;
    private ClientEntity entity;

    @BeforeEach
    void setUp() {
        UUID id = UUID.randomUUID();
        client = Client.builder()
                .id(id)
                .name("John")
                .surname("Doe")
                .birthDate(LocalDate.of(1990, 1, 1))
                .driverLicenseNumber("DL-12345")
                .address("1 Main Street")
                .build();

        entity = ClientEntity.builder()
                .id(id.toString())
                .name(client.getName())
                .surname(client.getSurname())
                .birthDate(client.getBirthDate())
                .driverLicenseNumber(client.getDriverLicenseNumber())
                .address(client.getAddress())
                .build();
    }

    @Nested
    @DisplayName("exist - vérification d'existence")
    class Exist {

        @Test
        @DisplayName("doit retourner vrai lorsque le repository trouve l'identifiant")
        void shouldReturnTrueWhenRepositoryFindsIdentifier() {
            // Given
            when(repository.existsById(entity.getId())).thenReturn(true);

            // When
            boolean exists = clientBddService.exist(UUID.fromString(entity.getId()));

            // Then
            assertTrue(exists);
            verify(repository).existsById(entity.getId());
        }

        @Test
        @DisplayName("doit retourner faux lorsque l'identifiant est nul")
        void shouldReturnFalseWhenIdentifierIsNull() {
            // Given
            // When
            boolean exists = clientBddService.exist(null);

            // Then
            assertFalse(exists);
            verify(repository, never()).existsById(any());
        }
    }

    @Nested
    @DisplayName("getAll - récupération complète")
    class GetAll {

        @Test
        @DisplayName("doit retourner les clients mappés lorsque le repository renvoie des entités")
        void shouldReturnMappedClientsWhenRepositoryReturnsEntities() {
            // Given
            when(repository.findAll()).thenReturn(List.of(entity));
            when(mapper.from(entity)).thenReturn(client);

            // When
            Collection<Client> result = clientBddService.getAll();

            // Then
            assertEquals(Set.of(client), new HashSet<>(result));
            verify(mapper).from(entity);
        }

        @Test
        @DisplayName("doit retourner un ensemble vide lorsque le repository renvoie null")
        void shouldReturnEmptySetWhenRepositoryReturnsNull() {
            // Given
            when(repository.findAll()).thenReturn(null);

            // When
            Collection<Client> result = clientBddService.getAll();

            // Then
            assertTrue(result.isEmpty());
            verify(mapper, never()).from(any());
        }
    }

    @Nested
    @DisplayName("get - récupération unitaire")
    class Get {

        @Test
        @DisplayName("doit retourner le client mappé lorsque le repository trouve l'identifiant")
        void shouldReturnMappedClientWhenRepositoryFindsIdentifier() {
            // Given
            when(repository.findById(entity.getId())).thenReturn(Optional.of(entity));
            when(mapper.from(entity)).thenReturn(client);

            // When
            Optional<Client> result = clientBddService.get(UUID.fromString(entity.getId()));

            // Then
            assertTrue(result.isPresent());
            assertSame(client, result.orElseThrow());
            verify(mapper).from(entity);
        }

        @Test
        @DisplayName("doit retourner un Optional vide lorsque l'identifiant est nul")
        void shouldReturnEmptyOptionalWhenIdentifierIsNull() {
            // Given
            // When
            Optional<Client> result = clientBddService.get(null);

            // Then
            assertTrue(result.isEmpty());
            verify(repository, never()).findById(any());
        }

        @Test
        @DisplayName("doit retourner un Optional vide lorsque le repository ne trouve pas l'identifiant")
        void shouldReturnEmptyOptionalWhenRepositoryDoesNotFindIdentifier() {
            // Given
            when(repository.findById(entity.getId())).thenReturn(Optional.empty());

            // When
            Optional<Client> result = clientBddService.get(UUID.fromString(entity.getId()));

            // Then
            assertTrue(result.isEmpty());
            verify(mapper, never()).from(any());
        }
    }

    @Nested
    @DisplayName("save - sauvegarde client")
    class Save {

        @Test
        @DisplayName("doit persister le client et retourner le résultat mappé")
        void shouldPersistClientAndReturnMappedResult() {
            // Given
            when(mapper.to(client)).thenReturn(entity);
            when(repository.save(entity)).thenReturn(entity);
            when(mapper.from(entity)).thenReturn(client);

            // When
            Client result = clientBddService.save(client);

            // Then
            assertSame(client, result);
            verify(repository).save(entity);
        }

        @Test
        @DisplayName("doit lever NullPointerException lorsque le client est nul")
        void shouldThrowNullPointerExceptionWhenClientIsNull() {
            // Given
            // When
            Executable invocation = () -> clientBddService.save(null);

            // Then
            assertThrows(NullPointerException.class, invocation);
            verify(repository, never()).save(any());
        }
    }

    @Nested
    @DisplayName("delete - suppression client")
    class Delete {

        @Test
        @DisplayName("doit supprimer le client lorsque l'identifiant est fourni")
        void shouldDeleteClientWhenIdentifierProvided() {
            // Given
            UUID identifier = UUID.fromString(entity.getId());

            // When
            clientBddService.delete(identifier);

            // Then
            verify(repository).deleteById(entity.getId());
        }

        @Test
        @DisplayName("ne doit pas supprimer lorsque l'identifiant est nul")
        void shouldNotDeleteWhenIdentifierIsNull() {
            // Given
            // When
            clientBddService.delete(null);

            // Then
            verify(repository, never()).deleteById(any());
        }
    }
}
