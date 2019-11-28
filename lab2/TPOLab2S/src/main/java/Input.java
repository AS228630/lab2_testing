import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Input {
    public static Scanner scanner;
    Input() {
        scanner = new Scanner(System.in);
    }
    public static int requestInputDigit(String inputQuestion) {
        System.out.println(inputQuestion);
        // Create a Scanner object
        String input = scanner.nextLine();
        if(!checkUserInputOnDigit(input)) {
            System.out.println("Your input is not digit. Abort operation. Exiting.....");
            return -1;
        }
        int digit = Character.getNumericValue(input.charAt(0));
        return digit;
    }
    public static Book requestBookInfo() {
        Book myBook = new Book();
        try {
            System.out.println("Enter Book ISBN");
             // Create a Scanner object
            String input = scanner.nextLine();
            //checkISBNInput(input); //disable special isbn input
            userInputChecker(input);
            myBook.setISBN(input);
            System.out.println("Enter Book BookName");
            input = scanner.nextLine();
            userInputChecker(input);
            myBook.setBookName(input);
            System.out.println("Enter Book AuthorName");
            input = scanner.nextLine();
            userInputChecker(input);
            myBook.setAuthorName(input);
            System.out.println("Enter Book Annotation");
            input = scanner.nextLine();
            userInputChecker(input);
            myBook.setAnnotation(input);
            System.out.println("Enter Book Date");
            input = scanner.nextLine();
            userInputChecker(input);
            Date date = Date.valueOf(input);
            myBook.setDate(date);
        }
        catch(InputMismatchException exp) {
            System.out.println("Your input has special characters. Abort operation. Exiting.....");
            return null;
        }
        return myBook;

    }

    public static String requestInputString(String inputQuestion) {
        System.out.println(inputQuestion);
        String input = scanner.nextLine();
        return input;
    }
    public static Boolean checkUserInput(String userInput) {
        Pattern pattern = Pattern.compile(
                "[" +                   //начало списка допустимых символов
                        "а-яА-ЯёЁ" +    //буквы русского алфавита
                        "a-zA" + // латинница
                        "0-9" + //цифры
                        "]" +                   //конец списка допустимых символов
                        "*");
        Matcher matcher = pattern.matcher(userInput);
        return matcher.matches();
    }
    public static void userInputChecker(String userInput) {
        if(!checkUserInput(userInput)) {
            //System.exit(1);
            throw new InputMismatchException();
        }
    }
    public static Boolean checkUserInputOnDigit(String userInput) {
        Pattern pattern = Pattern.compile(
                "[" +                   //начало списка допустимых символов
                        "0-9" + //цифры
                        "]" +                   //конец списка допустимых символов
                        "*");
        Matcher matcher = pattern.matcher(userInput);
        return matcher.matches();
    }
    public static boolean checkISBNInput(String isbn) {
        if(isbn.matches("\\d-\\d\\d\\d-\\d\\d\\d\\d\\d-\\d")){
            return true;
        }
        else {
            throw new InputMismatchException();
            //return false;
        }
    }
}
