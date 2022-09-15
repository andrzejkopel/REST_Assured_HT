package org.example.petstore;

import org.apache.http.HttpStatus;
import org.example.entities.Pet;
import org.example.service.PetStoreService;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.example.entities.Pet.Status.*;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.*;

public class PetTest {

    private PetStoreService service;

    @BeforeClass
    public void authenticate() {
        service = new PetStoreService("https://petstore.swagger.io/v2", "special-key");
    }

    @Test
    public void addPet_shouldBeAvailableById() {
        Pet pet = new Pet(1, "pet", emptyList(), emptyList(), AVAILABLE);

        service.addPet(pet);
        Pet response = service.findPetById(pet.getId());

        assertNotNull(response, "Record should be available.");
        assertEquals(response.getName(), pet.getName(), "Name should be 'pet'.");
        assertTrue(response.getPhotoUrls().isEmpty(), "Photos list should be empty.");
        assertTrue(response.getTags().isEmpty(), "Tags list should be empty.");
        assertEquals(response.getStatus(), pet.getStatus(), "Status should be equals to AVAILABLE.");
    }

    @Test
    public void addPet_shouldBeAvailableByStatus() {
        Pet pet = new Pet(2, "dog", emptyList(), emptyList(), PENDING);

        service.addPet(pet);
        List<Pet> response = service.findPetByStatus(pet.getStatus());

        assertNotNull(response, "Record should be available.");
        assertEquals(response.size(), 1, "Should returns only one element defined in 'Arrange' section");
        assertEquals(response.get(0).getName(), pet.getName(), "Name should be 'dog'.");
        assertTrue(response.get(0).getPhotoUrls().isEmpty(), "Photos list should be empty.");
        assertTrue(response.get(0).getTags().isEmpty(), "Tags list should be empty.");
        assertEquals(response.get(0).getStatus(), pet.getStatus(), "Status should be equals to PENDING.");
    }

    @Test
    public void updatePet_petRecordShouldBeUpdated() {
        Pet pet = new Pet(3, "cat", emptyList(), emptyList(), PENDING);

        service.addPet(pet);
        service.updatePet(pet.setStatus(SOLD));
        Pet response = service.findPetById(pet.getId());

        assertNotNull(response, "Record should be available.");
        assertEquals(response.getName(), pet.getName(), "Name should be 'cat'.");
        assertTrue(response.getPhotoUrls().isEmpty(), "Photos list should be empty.");
        assertTrue(response.getTags().isEmpty(), "Tags list should be empty.");
        assertEquals(response.getStatus(), SOLD, "Status should be equals to SOLD.");
    }

    @Test
    public void deletePet_petShouldNotBeAvailableById() {
        Pet pet = new Pet(4, "pet", emptyList(), emptyList(), SOLD);

        service.addPet(pet);
        service.deletePet(pet.getId());

        Pet deletedPet = service.findPetById(pet.getId(), HttpStatus.SC_NOT_FOUND);
        assertNull(deletedPet, "Should be null");
    }
}
