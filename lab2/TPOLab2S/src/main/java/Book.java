import java.sql.Date;

public class Book {
    private String ISBN;
    private String BookName;
    private String AuthorName;
    private String Annotation;
    private Date date;
    private int keywordCount;
    private boolean isKeyInAnnotation;

    public Book() {
        keywordCount = 0;
        isKeyInAnnotation = false;
    }
    public Book(String isbn, String bookname, String authorname, String annotation, String insertDate){
        this.ISBN = isbn;
        this.BookName = bookname;
        this.AuthorName = authorname;
        this.Annotation = annotation;
        Date insertDateToBook = Date.valueOf(insertDate);
        this.date = insertDateToBook;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getBookName() {
        return BookName;
    }

    public void setBookName(String bookName) {
        BookName = bookName;
    }

    public String getAuthorName() {
        return AuthorName;
    }

    public void setAuthorName(String authorName) {
        AuthorName = authorName;
    }

    public String getAnnotation() {
        return Annotation;
    }

    public void setAnnotation(String annotation) {
        Annotation = annotation;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    public int getKeywordCount() {
        return keywordCount;
    }

    public void setKeywordCount(int keywordCount) {
        this.keywordCount = keywordCount;
    }
    public boolean isKeyInAnnotation() {
        return isKeyInAnnotation;
    }

    public void setKeyInAnnotation(boolean keyInAnnotation) {
        isKeyInAnnotation = keyInAnnotation;
    }


}
