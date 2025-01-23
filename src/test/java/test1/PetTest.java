package test1;

import io.qameta.allure.Allure;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Listeners({io.qameta.allure.testng.AllureTestNg.class})
public class PetTest {
    private final String BASE_URL = "https://petstore.swagger.io/";
    private PetSteps petSteps = new PetSteps();
    private int id;
    List<Pet> pets = new ArrayList<>();

    @Test(description = "Add pet and check")
    public void test() {
        Pet pet1 = new Pet.Builder(4, "doggie", "available").build();
        Pet pet2 = new Pet.Builder(1, "doggie", "available").build();
        Pet pet3 = new Pet.Builder(3, "doggie", "unavailable").build();
        Pet pet4 = new Pet.Builder(2, "doggie", "unavailable").build();
        Pet pet5 = new Pet.Builder(5, "doggie", "available").build();

//        pets.stream().sorted(Comparator.comparing());
        List<Pet> sortedPets = new ArrayList<>();
        pets.add(pet1);
        pets.add(pet2);
        pets.add(pet3);
        pets.add(pet4);
        pets.add(pet5);
        sortedPets = pets.stream()
                .sorted(Comparator.comparing(Pet::getId))
                .collect(Collectors.toList());
        sortedPets.removeIf(pet -> "unavailable".equals(pet.getStatus()));


        Allure.step("Adding a new pet", () -> {
            Response responsePOST = petSteps.addPet(BASE_URL, pet1);
            Pet petPOST = responsePOST.as(Pet.class);
            Assert.assertNotNull(petPOST, "Pet POST response is null");
        });

        Allure.step("Retrieving the created pet", () -> {
            id = pet1.getId();
            Response responseGet = petSteps.getPet(BASE_URL, id);
            Pet petGet = responseGet.as(Pet.class);
            petSteps.checkPet(pet1, petGet);
//            responseGet.headers().getValue("")
        });

        System.out.println(sortedPets);

        //a as
    }



    @AfterMethod
    public void afterMethod() {
        petSteps.deletePet(BASE_URL, id);
    }


}
