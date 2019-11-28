import org.junit.Ignore;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Date;

import static org.junit.Assert.fail;

class InputTest {
    @Ignore
    //Реализовать проверку введенных слов на наличие спец. Символов.
    @Test
    void testSpecialSymbols() {
        Input input = new Input();
        String latin = "Test";
        String digits = "Test0123";
        String cyrylic = "проверка";
        String specialSymbols = "@";
        if (input.checkUserInput(latin)) {
            showError( "latin");
        }
        if (input.checkUserInput(digits)) {
            showError("digits");
        }
       // if (input.checkUserInput(cyrylic)) {
         //   showError("cyrylic");
       // }
        if (input.checkUserInput(specialSymbols)) {
            System.out.println(input.checkUserInput(specialSymbols));
            showError("specialSymbols");
        }
    }
    //Реализовать проверку введенной цифры на наличие спец. Символов, кириллицы, и латиницы
    @Ignore
    @Test
    void testDigit() {
        Input input = new Input();
        String digit = "0";
        if(!input.checkUserInputOnDigit(digit)) {
            showError("digits");
        }
    }

    //Реализовать вывод сообщения об ошибке, если слово, введенное пользователем, не соответствует шаблону.
    @Ignore
    @Test
    void isErrorDisplayedDuringUserInput() {
        String incorrectISBN = "@180-90-23!";
        InputStream in = new ByteArrayInputStream(incorrectISBN.getBytes());
        System.setIn(in);
        Input input = new Input();
        java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(out));
        Input testInput = new Input();
        testInput.requestBookInfo();
        System.out.println(out.toString());

        //reseting system in and out to standart
        System.setIn(System.in);
        System.setOut(System.out);
        String output = out.toString();
//
        if(!output.contains("Enter Book ISBN\r\nYour input has special characters. Abort operation. Exiting.....\r\n")) {
            showError("of special characters during book info test");
        }
    }
    //Реализовать выход из программы при вводе цифры 0
    @Ignore
    @Test
    void testExitIfZeroWaaInput() {
        Main main = new Main(); //some sh code
        String userChoice = "0";
        InputStream in = new ByteArrayInputStream(userChoice.getBytes());
        System.setIn(in);
        java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(out));
        main.main(null); // what i am doing....

        //reseting system in and out to standart
        System.setIn(System.in);
        System.setOut(System.out);
        String output = out.toString();
//
        if(!output.contains("Program exit is called. Exiting...")) {
            showError("of special characters during book info test");
        }


    }

    //Реализовать отражение в выборке, если слово найдено в аннотации
    @Test
    void testIsBookHasKeyInAnnotationDisplay() {
        String userChoice = "3\r\n" + "Qwerty\r\n" + "0\r\n";
        InputStream in = new ByteArrayInputStream(userChoice.getBytes());
        System.setIn(in);
        java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(out));
        Book book = new Book("1234", "Как стать миллионером", "Иван Иванов", "Qwerty", "2019-01-01");
        Database db = new Database();
        db.insertBook(book);
        Main.main(null); // what i am doing....

        //reseting system in and out to standart
        System.setIn(System.in);
        System.setOut(System.out);
        String output = out.toString();

        //delete record from db
        db.deleteBook("1234");
//
        if(!output.contains("It has key in annotation")) {
            showError("annotaion alert was not displayed during book info test");
        }
    }

    void showError(String explain) {
        fail("Test is not passed because " + explain);
    }
    //Реализовать проверку ISBN: допускается наличие только цифр и дефиса
    @Test
    void testISBNInput() {
        Main main = new Main(); //some sh code
        String userInput = "2\r\n" + "2-266-11156-60\r\n" + "0\r\n";
        InputStream in = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(in);
        java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(out));
        main.main(null); // what i am doing....

        //reseting system in and out to standart
        System.setIn(System.in);
        System.setOut(System.out);
        String output = out.toString();
//
        if(!output.contains("You wrote an incorrect ISBN! Try again!")) {
            showError("of no error message after incorrect isbn input");
        }

    }

    //Реализовать вывод результата поиска в БД по ISBN в консоль
    @Test
    void testDisplayOfISBNSearchResult() {
        Book book = new Book();
        book.setDate(Date.valueOf("2019-01-01"));
        book.setAnnotation("Blah");
        book.setAuthorName("Davidok");
        book.setBookName("BookName");
        book.setISBN("2-266-19956-6");

        Database db = new Database();
        db.insertBook(book);

        String userChoice = "2\r\n" + "2-266-19956-6\r\n" + "0\r\n";
        InputStream in = new ByteArrayInputStream(userChoice.getBytes());
        System.setIn(in);
        java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(out));
        Main.main(null); // what i am doing....

        //reseting system in and out to standart
        System.setIn(System.in);
        System.setOut(System.out);
        String output = out.toString();

        db.deleteBook(book.getISBN());

        if(!output.contains("ISBN: 2-266-19956-6")) {
            showError("Failed testDisplayOfISBNSearchResult");
        }
    }
    //Реализовать вывод полученного списка книг из БД по ключевым словам
    @Test
    void testDisplaySearchByKeywordResult() {
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

        String userChoice = "3\r\n" + "q\r\n" + "0\r\n";
        InputStream in = new ByteArrayInputStream(userChoice.getBytes());
        System.setIn(in);
        java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(out));
        Main.main(null); // what i am doing....

        //reseting system in and out to standart
        System.setIn(System.in);
        System.setOut(System.out);
        String output = out.toString();

        db.deleteBook(firstBook.getISBN());
        db.deleteBook(secondBook.getISBN());

        if(!output.contains("ISBN: 2-266-11156-7") || !output.contains("ISBN: 2-266-11156-8") ) {
            showError("Failed testDisplayOfISBNSearchResult");
        }
    }
    @Test
    //Реализовать обработку выбора пользователя в зависимости от ввода цифры в меню
    void testMenu() {
        String userChoice = "0\r\n";
        InputStream in = new ByteArrayInputStream(userChoice.getBytes());
        System.setIn(in);
        java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(out));
        Main.main(null); // what i am doing....

        //reseting system in and out to standart
        System.setIn(System.in);
        System.setOut(System.out);
        String output = out.toString();

        if(!output.contains("Program exit is called. Exiting...") ) {
            showError("Failed testMenu after 0 input");
        }

        userChoice = "1\r\n" + "!\r\n";
        in = new ByteArrayInputStream(userChoice.getBytes());
        System.setIn(in);
        out = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(out));
        Main.main(null); // what i am doing....

        //reseting system in and out to standart
        System.setIn(System.in);
        System.setOut(System.out);
        output = out.toString();

        if(!output.contains("Enter Book ISBN") ) {
            showError("Failed testMenu after 1 input");
        }

        userChoice = "2\r\n" + "q\r\n"+ "0\r\n";
        in = new ByteArrayInputStream(userChoice.getBytes());
        System.setIn(in);
        out = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(out));
        Main.main(null); // what i am doing....

        //reseting system in and out to standart
        System.setIn(System.in);
        System.setOut(System.out);
        output = out.toString();

        if(!output.contains("Enter book ISBN") ) {
            showError("Failed testMenu after 2 input");
        }

        userChoice = "3\r\n" + "0\r\n" + "0\r\n";
        in = new ByteArrayInputStream(userChoice.getBytes());
        System.setIn(in);
        out = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(out));
        Main.main(null); // what i am doing....

        //reseting system in and out to standart
        System.setIn(System.in);
        System.setOut(System.out);
        output = out.toString();

        if(!output.contains("Enter book key word") ) {
            showError("Failed testMenu after 3 input");
        }
        userChoice = "4\r\n" + "0\r\n" + "0\r\n";
        in = new ByteArrayInputStream(userChoice.getBytes());
        System.setIn(in);
        out = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(out));
        Main.main(null); // what i am doing....

        //reseting system in and out to standart
        System.setIn(System.in);
        System.setOut(System.out);
        output = out.toString();

        if(!output.contains("Enter book ISBN to delete") ) {
            showError("Failed testMenu after 4 input");
        }

    }
    @Test
    //Реализовать вывод об ошибке, если пользователь ввёл не цифру в меню
    void testWrongDigitInMenuInput() {
        String userChoice = "F\r\n" + "0\r\n";
        InputStream in = new ByteArrayInputStream(userChoice.getBytes());
        System.setIn(in);
        java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(out));
        Main.main(null); // what i am doing....

        //reseting system in and out to standart
        System.setIn(System.in);
        System.setOut(System.out);
        String output = out.toString();

        if (!output.contains("Your input is not digit. Abort operation. Exiting.....")) {
            showError("Failed testWrongDigitInMenuInput after F input");
        }

        userChoice = "9\r\n" + "0\r\n";
        in = new ByteArrayInputStream(userChoice.getBytes());
        System.setIn(in);
        out = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(out));
        Main.main(null); // what i am doing....

        //reseting system in and out to standart
        System.setIn(System.in);
        System.setOut(System.out);
        output = out.toString();

        if (!output.contains("You wrote a wrong number. Please try again")) {
            showError("Failed testWrongDigitInMenuInput after 9 input");
        }
    }
}

