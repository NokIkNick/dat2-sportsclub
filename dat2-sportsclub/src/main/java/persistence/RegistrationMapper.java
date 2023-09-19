package persistence;

import entities.Member;
import entities.Registration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RegistrationMapper {

    private Database database;

    public RegistrationMapper(Database database){
        this.database = database;
    }

    public Registration addToTeam(int member_id, String team_id, int price){
        Registration rg = null;
        boolean result = false;
        String sql = "INSERT INTO public.registration(\n" +
                "\tmember_id, team_id, price)\n" +
                "\tVALUES (?, ?, ?);";
        try(Connection connection = database.connect()){
            try(PreparedStatement ps = connection.prepareStatement(sql)){
                ps.setInt(1,member_id);
                ps.setString(2,team_id);
                ps.setInt(3,price);
                int rowsAffected = ps.executeUpdate();
                if(rowsAffected == 1){
                    result = true;
                }
                rg = new Registration(member_id,team_id,price);
            }catch(Exception e){
                e.printStackTrace();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return rg;
    }

    public List<Registration> getAllRegistrations(){
        List<Registration> registrations = new ArrayList<>();

        String sql = "SELECT member_id, team_id, price\n" +
                "\tFROM public.registration;";

        try(Connection connection = database.connect()){
            try(PreparedStatement ps = connection.prepareStatement(sql)){
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    int memberId = rs.getInt("member_id");
                    String teamId = rs.getString("team_id");
                    int price = rs.getInt("price");
                    registrations.add(new Registration(memberId, teamId, price));
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return registrations;
    }

}
