package org.example.service;

import io.restassured.response.Response;
import lombok.extern.java.Log;
import org.apache.http.HttpStatus;
import org.example.entities.Pet;

import java.util.Arrays;
import java.util.List;

@Log
public class PetStoreService extends CommonService {

    public PetStoreService(String baseUri, String apiKey) {
        super(baseUri, apiKey);
    }

    public Pet addPet(Pet pet) {
        log.fine("Adding new Pet to store:\n" + pet);
        return post("/pet", pet).as(Pet.class);
    }

    public Pet findPetById(long petId, int expectedStatus) {
        log.fine("Returning Pet by id=" + petId);
        Response response = get("/pet/" + petId, expectedStatus);
        if (response.getStatusCode() == HttpStatus.SC_OK) {
            return response.as(Pet.class);
        }
        log.fine(String.format("Pet with ID=%d not found.", petId));
        return null;
    }

    public Pet findPetById(long petId) {
        return findPetById(petId, HttpStatus.SC_OK);
    }

    public List<Pet> findPetByStatus(Pet.Status status) {
        log.fine("Returning Pet by status=" + status.name());
        return Arrays.asList(get("/pet/findByStatus?status=" + status.name()).as(Pet[].class));
    }

    public Pet updatePet(Pet pet) {
        log.fine("Updating Pet with ID=" + pet.getId());
        log.fine("Updated Pet:\n" + pet);
        return put("/pet", pet).as(Pet.class);
    }

    public void deletePet(long petId) {
        log.fine("Deleting Pet with ID=" + petId);
        delete("/pet/" + petId);
    }
}
