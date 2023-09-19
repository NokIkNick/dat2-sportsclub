package dk.cphbusiness.cphnw89.dtos;

public class GendervarietyDTO {

    private String gender;
    private int number;

    public GendervarietyDTO(String gender, int number){
        this.gender = gender;
        this.number = number;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "gender: '" + gender + '\'' +
                ", count: " + number;
    }
}
