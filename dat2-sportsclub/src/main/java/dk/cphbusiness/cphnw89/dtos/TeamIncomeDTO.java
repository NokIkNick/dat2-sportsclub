package dk.cphbusiness.cphnw89.dtos;

public class TeamIncomeDTO {

    private int amount;

    private String teamId;

    public TeamIncomeDTO(int amount){
        this.amount = amount;
    }

    public TeamIncomeDTO(int amount, String teamId){
        this.amount = amount;
        this.teamId = teamId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    @Override
    public String toString() {
        return "TeamIncomeDTO{" +
                "amount=" + amount +
                ", teamId='" + teamId + '\'' +
                '}';
    }
}
