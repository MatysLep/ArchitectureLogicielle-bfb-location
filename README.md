# ArchitectureLogicielle-bfb-location

## Accès à la documentation Swagger

Une fois l'application démarrée, la documentation de l'API est accessible via le lien :
[Swagger](http://localhost:8080/docs)


## ISSUES

- CONTRACTS
    - FIX
        - validators create -> renvoie toujours une 404 sur le check IdClientExists
        - fix 
            - /api/contracts/client/{idClient}
            - /api/contracts/vehicle/{vehicleRegistration}
    - Update
        - ajouter validators -> date coherente (start > end)
        - valider tout le reste de la partie contract
        - test unitaire pour les erreurs notifié

        - ajouter validators EXISTS 
            - /api/contracts/client/{idClient}
            - /api/contracts/vehicle/{vehicleRegistration}

- Core/Common

    - State -> common ?
        - VehicleState
        - ContractState

    - model/ValidatorResult.java -> validators/ValidatorResult.java ?

- interfaces/rest

    - commmon/model/input/AbstractUpdateInput.java -> Besoin ?

- Update Code -> 204 ?


## Notifed Errors

- Vehicle

    - Create
        - 500 -> NullPointerException si on retire AcquisitionDate

    - Create & Update
        - 500 -> "state": "azer"

    - Delete
        - 204 -> sans suppression

    - Get & Delete & Update
        - ne vérifie pas le pattern registration


- Client

    - Create
        - 500 -> NullPointerException si on retire la BirthDate

    - Delete
        - 204 -> sans suppression

    - Get & Delete & Update
        - 500 -> si le pattern d'ID est mauvais
        

## Errors Docs

- Vehicle

    - Create
        - Registration: 400 -> wrong pattern
        - Registration: 409 -> already exists
        - Date: 400 -> wrong date format

    - Update 
        - Registration: 400 -> wrong pattern
        - Date: 400 -> wrong date format
        - 404 -> not found

    - Get & Update
        - Registration: 404 -> not found


- Clients

    - Create
        - Date: 400 -> wrong date format
        - driverLicenseNumber: 400 -> wrong length
        - Copy & driverLicenseNumber: 409 -> already exists

    - Udpate
        - driverLicenseNumber: 400 -> wrong length
        - Date: 400 -> wrong date format

    - Get & Update
        - id: 404 -> not found

