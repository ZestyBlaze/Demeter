package dev.teamcitrus.betterfarms.attachment;

public class AnimalAttachment {
    private Gender gender;
    
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Gender getGender() {
        return this.gender;
    }

    public enum Gender {
        MALE("male"), FEMALE("female"), NONE("none");

        private final String id;

        Gender(String name) {
            this.id = name;
        }

        public String getId() {
            return id;
        }

        public static Gender getGender(String id) {
            if (id.equals(FEMALE.getId())) {
                return Gender.FEMALE;
            }
            return Gender.MALE;
        }
    }
}
