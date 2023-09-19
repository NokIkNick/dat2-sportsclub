package dk.cphbusiness.cphnw89.dtos;

public class NumberOfParticipentsDTO {

    private String team;
    private String sport;
    private int number;

    public NumberOfParticipentsDTO(String team, String sport, int number){
        this.team = team;
        this.sport = sport;
        this.number = number;
    }

    public NumberOfParticipentsDTO(String sport, int number){
        this.sport = sport;
        this.number = number;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }


    @Override
    public String toString(){
        if(team != null){
            return "Sportsgraen: "+sport+" "+"HoldId: "+team+" "+"deltagere: "+number;
        }else {
            return "Sportsgraen: "+sport+" "+"deltagere: "+number;
        }
    }

}
