package imt.archi.bfb.interfaces.rest.contracts;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import imt.archi.bfb.core.contracts.ContractsServiceValidator;
import imt.archi.bfb.core.contracts.model.Contract;
import imt.archi.bfb.interfaces.rest.common.exception.NotFoundException;
import imt.archi.bfb.interfaces.rest.contracts.model.input.ContractInput;
import imt.archi.bfb.interfaces.rest.contracts.model.input.ContractUpdateInput;
import imt.archi.bfb.interfaces.rest.contracts.model.output.ContractOutput;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("api/contracts")
public class ContractsController {

    private final ContractsServiceValidator service;

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<ContractOutput> getAll() {
        return Objects.requireNonNullElse(service.getAll(), Collections.<Contract>emptySet()).stream()
                .map(ContractOutput::from)
                .collect(Collectors.toSet());
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "/{idContract}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ContractOutput getOne(@PathVariable("idContract") final String identifier) {
        return service.get(UUID.fromString(identifier))
                .map(ContractOutput::from)
                .orElseThrow(() -> new NotFoundException(String.format("Le contract d'identifiant %s n'a pas été trouvé.", identifier)));
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ContractOutput create(@RequestBody @Valid final ContractInput contract) {
        return ContractOutput.from(service.create(ContractInput.convert(contract)));
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PatchMapping(value = "/{idContract}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@PathVariable("idContract") final String identifier, @RequestBody @Valid final ContractUpdateInput contract) {
        service.update(
                service.get(UUID.fromString(identifier))
                        .map(alreadySaved -> ContractUpdateInput.from(contract, alreadySaved))
                        .orElseThrow(() -> new NotFoundException(String.format("Le contract d'identifiant %s n'a pas été trouvé.", identifier)))
        );
    }

    
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{idContract}")
    public void delete(@PathVariable("idContract") final String identifier) {
        service.delete(UUID.fromString(identifier));
    }


}
