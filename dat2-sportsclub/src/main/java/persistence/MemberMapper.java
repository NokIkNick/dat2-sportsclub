package persistence;

import dk.cphbusiness.cphnw89.dtos.GendervarietyDTO;
import dk.cphbusiness.cphnw89.dtos.NumberOfParticipentsDTO;
import dk.cphbusiness.cphnw89.dtos.TeamIncomeDTO;
import entities.Member;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberMapper {

        private Database database;

        public MemberMapper(Database database) {
            this.database = database;
        }

        public List<Member> getAllMembers() {

            List<Member> memberList = new ArrayList<>();

            String sql = "select member_id, name, address, m.zip, gender, city, year " +
                         "from member m inner join zip using(zip) " +
                         "order by member_id";

            try (Connection connection = database.connect()) {
                try (PreparedStatement ps = connection.prepareStatement(sql)) {
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        int memberId = rs.getInt("member_id");
                        String name = rs.getString("name");
                        String address = rs.getString("address");
                        int zip = rs.getInt("zip");
                        String city = rs.getString("city");
                        String gender = rs.getString("gender");
                        int year = rs.getInt("year");
                        memberList.add(new Member(memberId, name, address, zip, city, gender, year));
                    }
                } catch (SQLException throwables) {
                    // TODO: Make own throwable exception and let it bubble upwards
                    throwables.printStackTrace();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return memberList;
        }

        public Member getMemberById(int memberId) {
            Member member = null;

            String sql =  "select member_id, name, address, m.zip, gender, city, year " +
            "from member m inner join zip using(zip) " +
            "where member_id = ?";

            try (Connection connection = database.connect()) {
                try (PreparedStatement ps = connection.prepareStatement(sql)) {
                    ps.setInt(1, memberId);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        memberId = rs.getInt("member_id");
                        String name = rs.getString("name");
                        String address = rs.getString("address");
                        int zip = rs.getInt("zip");
                        String city = rs.getString("city");
                        String gender = rs.getString("gender");
                        int year = rs.getInt("year");
                        member = new Member(memberId, name, address, zip, city, gender, year);
                    }
                } catch (SQLException throwables) {
                    // TODO: Make own throwable exception and let it bubble upwards
                    throwables.printStackTrace();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            int a = 1;
            return member;
        }

        public boolean deleteMember(int member_id){
            boolean result = false;
            String sql = "delete from member where member_id = ?";
            try (Connection connection = database.connect()) {
                try (PreparedStatement ps = connection.prepareStatement(sql)) {
                    ps.setInt(1, member_id);
                    int rowsAffected = ps.executeUpdate();
                    if (rowsAffected == 1){
                        result = true;
                    }
                } catch (SQLException throwables) {
                    // TODO: Make own throwable exception and let it bubble upwards
                    throwables.printStackTrace();
                }
            } catch (SQLException throwables) {
                // TODO: Make own throwable exception and let it bubble upwards
                throwables.printStackTrace();
            }
            return result;
        }

        public Member insertMember(Member member){
            boolean result = false;
            int newId = 0;
            String sql = "insert into member (name, address, zip, gender, year) values (?,?,?,?,?)";
            try (Connection connection = database.connect()) {
                try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS )) {
                    ps.setString(1, member.getName());
                    ps.setString(2, member.getAddress());
                    ps.setInt(3, member.getZip());
                    ps.setString(4, member.getGender());
                    ps.setInt(5, member.getYear());
                    int rowsAffected = ps.executeUpdate();
                    if (rowsAffected == 1){
                        result = true;
                    }
                    ResultSet idResultset = ps.getGeneratedKeys();
                    if (idResultset.next()){
                        newId = idResultset.getInt(1);
                        member.setMemberId(newId);
                    } else {
                        member = null;
                    }
                } catch (SQLException throwables) {
                    // TODO: Make own throwable exception and let it bubble upwards
                    throwables.printStackTrace();
                }
            } catch (SQLException throwables) {
                // TODO: Make own throwable exception and let it bubble upwards
                throwables.printStackTrace();
            }
            return member;
        }

        public boolean updateMember(Member member) {
            boolean result = false;
            String sql =    "update member " +
                            "set name = ?, address = ?, zip = ?, gender = ?, year = ? " +
                            "where member_id = ?";
            try (Connection connection = database.connect()) {
                try (PreparedStatement ps = connection.prepareStatement(sql)) {
                    ps.setString(1, member.getName());
                    ps.setString(2, member.getAddress());
                    ps.setInt(3, member.getZip());
                    ps.setString(4, member.getGender());
                    ps.setInt(5, member.getYear());
                    ps.setInt(6, member.getMemberId());
                    int rowsAffected = ps.executeUpdate();
                    if (rowsAffected == 1){
                        result = true;
                    }
                } catch (SQLException throwables) {
                    // TODO: Make own throwable exception and let it bubble upwards
                    throwables.printStackTrace();
                }
            } catch (SQLException throwables) {
                // TODO: Make own throwable exception and let it bubble upwards
                throwables.printStackTrace();
            }
            return result;
        }


    public List<NumberOfParticipentsDTO> getAllParticipentsPerTeam(){
        List<NumberOfParticipentsDTO> participents = new ArrayList<>();

        String sql = "SELECT count(DISTINCT registration.member_id) AS memberId,\n" +
                "    registration.team_id,\n" +
                "    sport.sport\n" +
                "   FROM registration\n" +
                "     JOIN team ON registration.team_id::text = team.team_id::text\n" +
                "     JOIN sport ON team.sport_id = sport.sport_id\n" +
                "  GROUP BY registration.team_id, sport.sport;";

        try(Connection connection = database.connect()){
            try(PreparedStatement ps = connection.prepareStatement(sql)){
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    int memberId = rs.getInt("memberId");
                    String teamId = rs.getString("team_id");
                    String sport = rs.getString("sport");
                    participents.add(new NumberOfParticipentsDTO(teamId, sport, memberId));
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return participents;
    }

    public List<NumberOfParticipentsDTO> getAllParticipentsPerSport(){
        List<NumberOfParticipentsDTO> participents = new ArrayList<>();

        String sql = " SELECT count(registration.member_id) AS memberId,\n" +
                "    sport.sport\n" +
                "   FROM registration\n" +
                "     JOIN team USING (team_id)\n" +
                "     JOIN sport USING (sport_id)\n" +
                "  GROUP BY sport.sport;";

        try(Connection connection = database.connect()){
            try(PreparedStatement ps = connection.prepareStatement(sql)){
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    int memberId = rs.getInt("memberId");
                    String sport = rs.getString("sport");
                    participents.add(new NumberOfParticipentsDTO(sport, memberId));
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return participents;
    }

    public List<GendervarietyDTO> getGenderVariety(){
            List<GendervarietyDTO> genderVariety = new ArrayList<>();

            String sql = " SELECT count(member.member_id) AS number,\n" +
                    "    member.gender\n" +
                    "   FROM member\n" +
                    "  GROUP BY member.gender;";

            try(Connection connection = database.connect()){
                try(PreparedStatement ps =  connection.prepareStatement(sql)){
                    ResultSet rs = ps.executeQuery();
                    while(rs.next()){
                        int number = rs.getInt("number");
                        String gender = rs.getString("gender");
                        genderVariety.add(new GendervarietyDTO(gender, number));
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            return genderVariety;
    }


    public int getSumIncomeOfAllTeams(){
            int sum = 0;
            String sql = " SELECT sum(registration.price) AS sum\n" +
                    "   FROM registration;";

            try(Connection connection = database.connect()){
                try(PreparedStatement ps = connection.prepareStatement(sql)){
                    ResultSet rs = ps.executeQuery();
                    while(rs.next()){
                        sum = rs.getInt("sum");
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return sum;
    }

    public List<TeamIncomeDTO> getSumOfIncomePerTeam(){
            List<TeamIncomeDTO> teamIncome = new ArrayList<>();
            String sql = " SELECT registration.team_id,\n" +
                    "    sum(registration.price) AS sum\n" +
                    "   FROM registration\n" +
                    "  GROUP BY registration.team_id;";
            try(Connection connection = database.connect()){
                try(PreparedStatement ps = connection.prepareStatement(sql)){
                    ResultSet rs = ps.executeQuery();
                    while(rs.next()){
                        String teamId = rs.getString("team_id");
                        int sum = rs.getInt("sum");
                        teamIncome.add(new TeamIncomeDTO(sum,teamId));
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return teamIncome;
    }


    public List<TeamIncomeDTO> getAverageIncomePerTeam(){
            List<TeamIncomeDTO> averageIncome = new ArrayList<>();
            String sql = " SELECT avg(totalsumincomeperteam.sum) AS avg,\n" +
                    "    totalsumincomeperteam.team_id\n" +
                    "   FROM totalsumincomeperteam\n" +
                    "  GROUP BY totalsumincomeperteam.team_id;";

            try(Connection connection = database.connect()){
                try(PreparedStatement ps = connection.prepareStatement(sql)){
                    ResultSet rs = ps.executeQuery();
                    while(rs.next()){
                        int avg = rs.getInt("avg");
                        String teamId = rs.getString("team_id");
                        averageIncome.add(new TeamIncomeDTO(avg,teamId));
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            return averageIncome;
    }

}
