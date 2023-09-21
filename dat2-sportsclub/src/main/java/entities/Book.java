package entities;

public class Book {

    private int bookId;
    private String titel;
    private int year;
    private int authorId;
    private String authorName;

    public Book(int bookId, String titel, int year, int authorId, String authorName){
        this.bookId = bookId;
        this.titel = titel;
        this.year = year;
        this.authorId = authorId;
        this.authorName = authorName;
    }


    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
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
        return "Book{" +
                "bookId=" + bookId +
                ", titel='" + titel + '\'' +
                ", year=" + year +
                ", authorId=" + authorId +
                ", authorName='" + authorName + '\'' +
                '}';
    }
}
