import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {
    ResultSet  rs = null;
    /*
    @BeforeClass
    public void prepare() {
    }
    */

    //Реализовать запрос таблицы с книгами из БД.
    @Test
    public void testDBSelect() {
        Book book = new Book();
        book.setDate(Date.valueOf("2019-01-01"));
        book.setAnnotation("Blah");
        book.setAuthorName("David");
        book.setBookName("BookName");
        book.setISBN("2-266-11156-6");

       Database db = new Database();
       db.insertBook(book);

       Book[] books = db.selectAllBooks();

       db.deleteBook(book.getISBN());
       for(int i = 0; i < 100; i++) {
           if(books[i].getISBN().equals("2-266-11156-6")) {
               break;
           }
           if(books[i] == null) {
               fail("Failed testDBSelect");
           }
       }
    }
    @Ignore
    //Реализовать сохранение в БД названия, ISBN, имени автора, аннотации и даты
    @Test
    public void testDBInsert() {
        Book book = new Book();
        book.setDate(Date.valueOf("2019-01-01"));
        book.setAnnotation("Blah");
        book.setAuthorName("David");
        book.setBookName("BookName");
        book.setISBN("2-266-11156-6");

        Database db = new Database();
        db.insertBook(book);

        Book returnedBook = db.returnBookByISBN(book.getISBN());
        db.deleteBook(book.getISBN());
        if(returnedBook == null) {
            fail("Failed testDBInsert");
        }

    }

    //Реализовать удаление книги из БД по введённому ISBN.
    @Test
    public void testDBDelete() {
        Book book = new Book();
        book.setDate(Date.valueOf("2019-01-01"));
        book.setAnnotation("Blah");
        book.setAuthorName("David");
        book.setBookName("BookName");
        book.setISBN("2-266-11156-6");

        Database db = new Database();
        db.insertBook(book);

        db.deleteBook(book.getISBN());

        Book returnedBook = db.returnBookByISBN(book.getISBN());
        if(returnedBook != null) {
            fail("Failed testDBDelete");
        }

    }

    //тест обвновления записи в БД
    @Test
    public void testDBUpdate() {
        Book oldBook = new Book();
        oldBook.setDate(Date.valueOf("2019-01-01"));
        oldBook.setAnnotation("Blah");
        oldBook.setAuthorName("David");
        oldBook.setBookName("BookName");
        oldBook.setISBN("2-266-11156-6");

        Database db = new Database();
        db.insertBook(oldBook);

        Book newBook = new Book();
        newBook.setDate(Date.valueOf("2019-01-01"));
        newBook.setAnnotation("Blah2001");
        newBook.setAuthorName("Ivan");
        newBook.setBookName("Name");
        newBook.setISBN("2-266-11156-6");

        db.updateBook(newBook, oldBook);


        Book returnedBook = db.returnBookByISBN(newBook.getISBN());
        db.deleteBook(newBook.getISBN());
        if(!returnedBook.getISBN().equals(newBook.getISBN()) || !returnedBook.getDate().toString().equals(newBook.getDate().toString())
        || !returnedBook.getAnnotation().equals(newBook.getAnnotation()) || !returnedBook.getBookName().equals(newBook.getBookName())
                || !returnedBook.getAuthorName().equals(newBook.getAuthorName()) ) {
            fail("Failed testDBUpdate");
        }
        }

    //Реализовать поиск по ключевым словам
    @Test
    public void testSelectOrderedByKeyword() {
        Book firstBook = new Book();
        firstBook.setDate(Date.valueOf("2019-01-01"));
        firstBook.setAnnotation("q");
        firstBook.setAuthorName("q");
        firstBook.setBookName("q");
        firstBook.setISBN("2-266-11156-7");

        Database db = new Database();
        db.insertBook(firstBook);

        Book secondBook = new Book();
        secondBook.setDate(Date.valueOf("2019-01-02"));
        secondBook.setAnnotation("qb");
        secondBook.setAuthorName("q");
        secondBook.setBookName("q");
        secondBook.setISBN("2-266-11156-8");

        db.insertBook(secondBook);

        Book books[] = new Book[100];
        String keyword = "q";

            //we made a two records: first with one keyword and secod with two
             books = db.getBooksByKeyword(keyword);
            if(books[0].getKeywordCount() == 3 && books[1].getKeywordCount() == 2) {
                cleanAfterTestSelectOrderedByKeyword();
                System.out.println("Testing select ordered by keywords success");
            }
            else {
                cleanAfterTestSelectOrderedByKeyword();
                fail("Testing select ordered by keywords failed");
            }
    }

    //метод для приведение в БД в состояние, которое было до запуска теста testSelectOrderedByKeyword
    public void cleanAfterTestSelectOrderedByKeyword() {
        Database db = new Database();
        db.deleteBook("2-266-11156-7");
        db.deleteBook("2-266-11156-8");
    }

    //Реализовать получение книги из БД по ISBN
    @Test
    public void testSelectBookByISBN() {
        Book firstBook = new Book();
        firstBook.setDate(Date.valueOf("2019-10-10"));
        firstBook.setAnnotation("There you can read Ivan Ivanov biography");
        firstBook.setAuthorName("Ivan Ivanov");
        firstBook.setBookName("Ivan Biography");
        firstBook.setISBN("2-296-11156-7");

        Database db = new Database();
        db.insertBook(firstBook);

            Book book = db.returnBookByISBN("2-296-11156-7");
            if(book.getISBN().equals("2-296-11156-7") && book.getAuthorName().equals("Ivan Ivanov") && book.getBookName().equals("Ivan Biography")
            && book.getAnnotation().equals("There you can read Ivan Ivanov biography") && book.getDate().toString().equals("2019-10-10") ) {
                cleanAfterTestSelectBookByISBN();
                System.out.println("Test search book by ISBN success");
            }
            else {
                cleanAfterTestSelectBookByISBN();
                fail("Test search book by ISBN failed");
            }
    }
    public void cleanAfterTestSelectBookByISBN() {
        Database db = new Database();
        db.deleteBook("2-296-11156-7");
    }

}