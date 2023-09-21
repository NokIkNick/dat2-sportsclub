package dk.cphbusiness.cphnw89.dtos;

import java.util.Date;

public class LaanerAndLaantDTO {

    private int laanerId;
    private String laanerName;
    private int bookId;
    private Date date;
    private String bookTitel;
    private int bookYear;
    private int authorId;
    private String authorName;

    public LaanerAndLaantDTO(int laanerId, String laanerName, int bookId, Date date, String bookTitel, int bookYear, int authorId, String authorName) {
        this.laanerId = laanerId;
        this.laanerName = laanerName;
        this.bookId = bookId;
        this.date = date;
        this.bookTitel = bookTitel;
        this.bookYear = bookYear;
        this.authorId = authorId;
        this.authorName = authorName;
    }

    public int getLaanerId() {
        return laanerId;
    }

    public void setLaanerId(int laanerId) {
        this.laanerId = laanerId;
    }

    public String getLaanerName() {
        return laanerName;
    }

    public void setLaanerName(String laanerName) {
        this.laanerName = laanerName;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public Date getDate() {
        return date;
    }

    public void Date(Date date) {
        this.date = date;
    }

    public String getBookTitel() {
        return bookTitel;
    }

    public void setBookTitel(String bookTitel) {
        this.bookTitel = bookTitel;
    }

    public int getBookYear() {
        return bookYear;
    }

    public void setBookYear(int bookYear) {
        this.bookYear = bookYear;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    @Override
    public String toString() {
        return "LaanerAndLaantDTO{" +
                "laanerId=" + laanerId +
                ", laanerName='" + laanerName + '\'' +
                ", bookId=" + bookId +
                ", date='" + date + '\'' +
                ", bookTitel='" + bookTitel + '\'' +
                ", bookYear=" + bookYear +
                ", authorId=" + authorId +
                ", authorName='" + authorName + '\'' +
                '}';
    }
}
