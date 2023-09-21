import dk.cphbusiness.cphnw89.dtos.LaanerAndLaantDTO;
import entities.Book;
import entities.Laaner;
import entities.Member;
import persistence.BibliotekMapper;
import persistence.Database;
import persistence.MemberMapper;

import java.util.List;

public class Main {

    private final static String USER = "postgres";
    private final static String PASSWORD = "postgres";
    private final static String URL = "jdbc:postgresql://localhost:5432/bibliotek?serverTimezone=CET&useSSL=false&allowPublicKeyRetrieval=true";

    public static void main(String[] args) {

        Database db = new Database(USER, PASSWORD, URL);
        BibliotekMapper bibliotekMapper = new BibliotekMapper(db);
        System.out.println(bibliotekMapper.getLaanerById(1));
        bibliotekMapper.insertLaaner(new Laaner("Nicklas","Hareskovvej 25",2700));
        List<Laaner> laaners = bibliotekMapper.getAllLaaners();
        List<Book> books = bibliotekMapper.getAllBooks();
        Book book1 = books.get(1);
        System.out.println(book1);
        System.out.println(bibliotekMapper.updateBookTitel(book1, "A new hope!"));

        //bibliotekMapper.deleteUdlaan(6,2);

        //List<Book> books = bibliotekMapper.getAllBooks();
        //List<LaanerAndLaantDTO> udlaan = bibliotekMapper.getAllLUdlaan();
        showMembers(books);


        //showMembers(members);
        //showMemberById(memberMapper, 13);

        /*  
            int newMemberId = insertMember(memberMapper);
            deleteMember(newMemberId, memberMapper);
            showMembers(members);
            updateMember(13, memberMapper);
        */
    }

    private static void deleteMember(int memberId, MemberMapper memberMapper) {
        if (memberMapper.deleteMember(memberId)){
            System.out.println("Member with id = " + memberId + " is removed from DB");
        }
    }

    private static int insertMember(MemberMapper memberMapper) {
        Member m1 = new Member("Ole Olsen", "Banegade 2", 3700, "Rønne", "m", 1967);
        Member m2 = memberMapper.insertMember(m1);
        showMemberById(memberMapper, m2.getMemberId());
        return m2.getMemberId();
    }

    private static void updateMember(int memberId, MemberMapper memberMapper) {
        Member m1 = memberMapper.getMemberById(memberId);
        m1.setYear(1970);
        if(memberMapper.updateMember(m1)){
            showMemberById(memberMapper, memberId);
        }
    }

    private static void showMemberById(MemberMapper memberMapper, int memberId) {
        System.out.println("***** Vis medlem nr. 13: *******");
        System.out.println(memberMapper.getMemberById(memberId).toString());
    }

    private static void showMembers(List in) {
        System.out.println("***** Vis alle medlemmer *******");
        for (Object ob : in) {
            System.out.println(ob.toString());
        }
    }


}
