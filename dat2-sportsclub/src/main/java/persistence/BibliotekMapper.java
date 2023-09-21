package persistence;

import com.sun.org.apache.xpath.internal.operations.Variable;
import dk.cphbusiness.cphnw89.dtos.LaanerAndLaantDTO;
import entities.Book;
import entities.Laaner;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BibliotekMapper {

    private Database database;

    public BibliotekMapper(Database database){
        this.database = database;
    }

    public Laaner getLaanerById(int laanerID){
        Laaner laaner = null;

        String sql = "select laaner_id, navn, adresse, laaner.postnr, postnummer.by from laaner inner join postnummer on laaner.postnr = postnummer.postnr where laaner_id = ?";

        try(Connection connection = database.connect()){
            try(PreparedStatement ps = connection.prepareStatement(sql)){
                ps.setInt(1,laanerID);
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    laanerID = rs.getInt("laaner_id");
                    String name = rs.getString("navn");
                    String address = rs.getString("adresse");
                    int zip = rs.getInt("postnr");
                    String city = rs.getString("by");
                    laaner = new Laaner(laanerID,name,address,zip,city);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return laaner;
    }

    public List<Laaner> getAllLaaners(){
        List<Laaner> laaners = new ArrayList<>();

        String sql ="select laaner_id, navn, adresse, laaner.postnr, postnummer.by from laaner inner join postnummer on laaner.postnr = postnummer.postnr";
        try(Connection connection = database.connect()){
            try(PreparedStatement ps = connection.prepareStatement(sql)){
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    int laanerId = rs.getInt("laaner_id");
                    String name = rs.getString("navn");
                    String address = rs.getString("adresse");
                    int zip = rs.getInt("postnr");
                    String city = rs.getString("by");
                    laaners.add(new Laaner(laanerId,name,address,zip,city));
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return laaners;
    }


    public List<Book> getAllBooks(){
        List<Book> books = new ArrayList<>();

        String sql = "select bog_id, titel, udgivelsesaar, forfatter_id, forfatter.navn from bog join forfatter using(forfatter_id)";

        try(Connection connection = database.connect()){
            try(PreparedStatement ps = connection.prepareStatement(sql)){
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    int bookId = rs.getInt("bog_id");
                    String title = rs.getString("titel");
                    int year = rs.getInt("udgivelsesaar");
                    int authorId = rs.getInt("forfatter_id");
                    String authorName = rs.getString("navn");
                    books.add(new Book(bookId,title,year,authorId,authorName));
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return books;
    }

    public List<LaanerAndLaantDTO> getAllLUdlaan(){
        List<LaanerAndLaantDTO> udlaan = new ArrayList<>();

        String sql = "select laaner_id, laaner.navn, udlaan.bog_id, udlaan.dato, bog.titel, bog.udgivelsesaar, forfatter.navn as forfatterNavn, forfatter.forfatter_id from laaner join udlaan using(laaner_id) join bog using(bog_id) join forfatter using(forfatter_id)";

        try(Connection connection =  database.connect()){
            try(PreparedStatement ps = connection.prepareStatement(sql)){
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    int laanerId = rs.getInt("laaner_id");
                    String laanerName = rs.getString("navn");
                    int bookId = rs.getInt("bog_id");
                    Date date = rs.getDate("dato");
                    String bookTitle = rs.getString("titel");
                    int year = rs.getInt("udgivelsesaar");
                    String authorName = rs.getString("forfatterNavn");
                    int authorId = rs.getInt("forfatter_id");
                    udlaan.add(new LaanerAndLaantDTO(laanerId,laanerName,bookId,date,bookTitle,year,authorId,authorName));
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return udlaan;
    }

    public Laaner insertLaaner(Laaner laaner){
        int newId = 0;
        boolean result = false;
        String sql = "insert into laaner (navn, adresse, postnr) values (?,?,?)";
        try(Connection connection = database.connect()){
            try(PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
                ps.setString(1,laaner.getName());
                ps.setString(2,laaner.getAddress());
                ps.setInt(3,laaner.getZip());
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 1){
                    result = true;
                }
                ResultSet idResultset = ps.getGeneratedKeys();
                if (idResultset.next()){
                    newId = idResultset.getInt(1);
                    laaner.setLaaner_id(newId);
                } else {
                    laaner = null;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return laaner;
    }

    public boolean newUdlaan(int bookId, int laanerId){
        boolean result = false;
        String sql = "INSERT INTO public.udlaan(\n" +
                "\tbog_id, laaner_id, dato)\n" +
                "\tVALUES (?, ?, ?)";
        try(Connection connection = database.connect()){
            try(PreparedStatement ps = connection.prepareStatement(sql)){
                ps.setInt(1,bookId);
                ps.setInt(2,laanerId);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date utilDate = new java.util.Date();
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                ps.setDate(3,sqlDate);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 1){
                    result = true;
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public boolean deleteUdlaan(int laanerId, int bogId){
        boolean result = false;
        String sql = "delete from udlaan where laaner_id = ? and bog_id = ?";
        try(Connection connection = database.connect()){
            try(PreparedStatement ps = connection.prepareStatement(sql)){
                ps.setInt(1,laanerId);
                ps.setInt(2,bogId);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 1){
                    result = true;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public boolean updateBookTitel(Book book, String newTitle){
        boolean result = false;
        String sql = "update bog set titel = ?, udgivelsesaar = ?, forfatter_id = ? where bog_id = ?";
        try(Connection connection = database.connect()){
            try(PreparedStatement ps = connection.prepareStatement(sql)){
                ps.setString(1,newTitle);
                ps.setInt(2,book.getYear());
                ps.setInt(3,book.getAuthorId());
                ps.setInt(4,book.getBookId());
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 1){
                    result = true;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }


}
