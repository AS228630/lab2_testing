import java.sql.*;

public class Database {
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://localhost:5432/catalog";

    //  Database credentials
    static final String USER = "postgres";
    static final String PASS = "postgres";

    static Statement stmt = null;
    public Database() {
        try {
            //STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 3: Open a connection
            System.out.println("Connecting to a selected database...");
            dbConnection = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected database successfully...");
        }
        catch (ClassNotFoundException exp) {
            System.out.println("Error create connection. Error is " + exp);
        }
        catch (SQLException exp2) {
            System.out.println("Error create connection. Error is " + exp2);
        }
    }
    private Connection dbConnection = null;
    public Connection getConnection() {
        return dbConnection;
    }
    void insertBook(Book book) {
        String sql = "INSERT INTO CATALOG (ISBN,BookName,AuthorName,Annotation,Date) VALUES (" + "'" + book.getISBN() + "','" + book.getBookName()
                + "','" + book.getAuthorName() + "','" + book.getAnnotation() + "','" + book.getDate() + "');";

        try {
            stmt = dbConnection.createStatement();
            stmt.executeUpdate(sql);
        }
        catch (SQLException exp3) {
            System.out.println("Error create connection. Error is " + exp3);
            return;
        }

    }
    public Book returnBookByISBN(String isbn) {
        try {
            stmt = dbConnection.createStatement();
            String sql = "SELECT ISBN,BookName,AuthorName,Annotation,Date FROM CATALOG WHERE ISBN=" + "'" + isbn + "'" + ';';
            ResultSet rs = stmt.executeQuery(sql);
            if( rs.next() ) {
                return getBookByRs(rs);
            }
            else return null;

        }
        catch (SQLException exp2) {
            System.out.println("Error create connection. Error is " + exp2);
            return null;
        }
    }
    boolean deleteBook(String isbn) {
        try {
            if (returnBookByISBN(isbn) != null) {
                stmt = dbConnection.createStatement();
                String sql = "DELETE FROM CATALOG WHERE ISBN=" + "'" + isbn + "'" + ';';
                stmt.executeUpdate(sql);
                return true;
            }
            else {
                return false;
            }
        }
        catch (SQLException exp2) {
            System.out.println("Error create connection. Error is " + exp2);
            return false;
        }
    }
    public Book getBookByRs(ResultSet rs) {
        Book book = new Book();
        try {
            book.setISBN(rs.getString("ISBN"));
            book.setBookName(rs.getString("BookName"));
            book.setAuthorName(rs.getString("AuthorName"));
            book.setAnnotation(rs.getString("Annotation"));
            book.setDate(rs.getDate("Date"));
        }
        catch (SQLException exp2) {
            System.out.println("Error create connection. Error is " + exp2);
            return null;
        }
        return book;
    }
    public Book[] getBooksByKeyword(String keyword) {
        Book books[] = new Book[100]; //TODO: Fix constant array size
        int bookCount = 0;
        try {
            stmt = dbConnection.createStatement();
            String sql = "SELECT ISBN,BookName,AuthorName,Annotation,Date FROM CATALOG"+ ';';
            ResultSet rs = stmt.executeQuery(sql);
            while( rs.next() ) {
                Book book = getBookByRs(rs);
                int keywordCount = 0;
                int digit = returnKeywordCount(book.getAnnotation(), keyword);
                if(digit != 0 ) {
                    book.setKeyInAnnotation(true);
                }
                keywordCount+= digit;
                keywordCount+= returnKeywordCount(book.getBookName(), keyword);
                keywordCount+= returnKeywordCount(book.getAuthorName(), keyword);
                book.setKeywordCount(keywordCount);
                if(keywordCount != 0) {
                    books[bookCount] = book;
                    bookCount++;
                }
            }
            return books;
        }
        catch (SQLException exp2) {
            System.out.println("Error create connection. Error is " + exp2);
            return null;
        }
    }
    public int returnKeywordCount(String string, String keyword) {
        int count = 0;
        String[] arr = string.split(" ");
        for(String word : arr) {
            if(word.equals(keyword)) count++;
        }
        return count;
    }

    public Book[] selectAllBooks() {
        Book[] books = null;
        try {
            stmt = dbConnection.createStatement();
            String sql = "SELECT ISBN,BookName,AuthorName,Annotation,Date FROM CATALOG;";
            ResultSet rs = stmt.executeQuery(sql);
            int i = 0 ;
            boolean firstTime = true;
            while( rs.next() ) {
                if(firstTime) {
                    books = new Book[100];
                    firstTime = false;
                }
                books[i] = getBookByRs(rs);
                i++;
            }
            return books;

        }
        catch (SQLException exp2) {
            System.out.println("Error create connection. Error is " + exp2);
            return null;
        }
    }
    public void updateBook(Book newBook, Book oldBook) {
        Connection ourConnection = getConnection();
        try {
            Statement stmt  = ourConnection.createStatement();
            String sql = "UPDATE CATALOG SET BookName =" + prepareBeforeUpdate(newBook.getBookName()) +"," + "authorname =" + prepareBeforeUpdate(newBook.getAuthorName()) +"," + " annotation =" + prepareBeforeUpdate(newBook.getAnnotation()) +"," + " date =" + prepareBeforeUpdate(newBook.getDate().toString()) + " WHERE isbn =" +"'" + oldBook.getISBN() + "'" + ";";
            stmt.executeUpdate(sql);
        }
        catch (SQLException exp) {
            System.out.println("We have SQLException " + exp);
        }

    }
    public String prepareBeforeUpdate(String info) {
        return "'" + info + "'";
    }
}
