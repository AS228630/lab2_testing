
import java.util.Arrays;
import java.util.Collections;
import java.util.InputMismatchException;

public class Main {
    public static void main(String[] args) {
        Database db = new Database();
        int menuChoose = -1;
        Input input = new Input();
        System.out.println("Lab2 by Senmas Var0");
        for(;;) {
            menuChoose = input.requestInputDigit("MENU: Enter 1 to add book; \n " +
                    "Enter 2 to get Book Info by ISBN; \n " +
                    "Enter 3 to enter Book Search menu; \n " +
                    "Enter 4 to enter Book Delete menu; \n " +
                    "Enter 0 for exit ");
            switch (menuChoose) {
                case 0: {
                    System.out.println("Program exit is called. Exiting...");
                    return;
                }
                case 1: {
                 Book inputBook = input.requestBookInfo();
                 if(inputBook == null) {
                     return;
                 }
                 db.insertBook(inputBook);
                 System.out.println("Book inserted");
                 break;
                }
                case 2: {
                String isbn = Input.requestInputString("Enter book ISBN");
                try{
                    Input.checkISBNInput(isbn);
                }
                catch(InputMismatchException exp) {
                    System.out.println("You wrote an incorrect ISBN! Try again!");
                    break;
                }
                Book myBook = db.returnBookByISBN(isbn);
                if(myBook == null) {
                    System.out.println("Book with this ISBN does not exit in DB");
                }
                else {
                    System.out.println("ISBN: " + myBook.getISBN() + '\n' +
                            "BookName: " + myBook.getBookName() + '\n' +
                            "AuthorName: " + myBook.getAnnotation() + '\n' +
                            "Annotation: " + myBook.getAnnotation() + '\n' +
                            "Date: " + myBook.getDate());
                    break;
                }
                }
                case 3: {
                    String keyWord = Input.requestInputString("Enter book key word");
                    Book books[] = db.getBooksByKeyword(keyWord);
                    while(countNotNullElements(books) != 0) {
                        Book maxBook = null;
                        int maxBookNumber = 0;
                        for (int i = 0; i < books.length; ++i) {
                            if (books[i] != null) {
                                if (maxBook != null && maxBook.getKeywordCount() < books[i].getKeywordCount()) {
                                        maxBook = books[i];
                                        maxBookNumber = i;

                                }
                                if(maxBook == null) {
                                    maxBook = books[i];
                                    maxBookNumber = i;
                                }
                            }
                        }
                        if(maxBook.isKeyInAnnotation()) {
                            System.out.println("ALERT \n" + "It has key in annotation");
                        }
                        System.out.println("ISBN: " + maxBook.getISBN() + '\n' +
                                "BookName: " + maxBook.getBookName() + '\n' +
                                "AuthorName: " + maxBook.getAnnotation() + '\n' +
                                "Annotation: " + maxBook.getAnnotation() + '\n' +
                                "Date: " + maxBook.getDate() );
                        books[maxBookNumber] = null;
                    }
                    break;
                    }
                case 4: {
                    String isbn = Input.requestInputString("Enter book ISBN to delete");
                    boolean result = db.deleteBook(isbn);
                    if(result) {
                        System.out.println("Delete book from DB successfull");
                    }
                    else {
                        System.out.println("Delete book from DB UNsuccessfull");
                    }
                    break;
                }
                default: {
                    System.out.println("You wrote a wrong number. Please try again");
                }

            }
        }
    }
    public static int countNotNullElements(Book books[]) {
        int count = 0;
        for(Book book: books) {
            if (book != null) {
                count++;
            }
        }
        return count;
    }
}
