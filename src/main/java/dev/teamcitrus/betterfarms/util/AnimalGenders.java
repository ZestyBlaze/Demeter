package dev.teamcitrus.betterfarms.util;

public enum AnimalGenders {
    MALE("male"), FEMALE("female");

    private final String id;

    AnimalGenders(String name) {
        this.id = name;
    }

    public String getId() {
        return id;
    }

    public static AnimalGenders getGender(String id) {
        if (id.equals(FEMALE.getId())) {
            return AnimalGenders.FEMALE;
        }
        return AnimalGenders.MALE;
    }
}
