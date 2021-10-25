package com.example.librarysystem;

public class Connection {
    private static  String URL ="http://192.168.254.100/";
    private static String FOLDER_NAME_1 ="project/Login/";
    private static String FOLDER_NAME_2 ="project/Book/";
    private static String TB_BOOK ="project/TB_Book/";
    private static String TB_Borrowedbook ="project/TB_Borrowedbook/";
    private static String TB_Borrower ="project/TB_Borrower/";

    public static String URL_LOGIN = URL+FOLDER_NAME_1+"login.php";
    public static String URL_REGISTER = URL+FOLDER_NAME_1+"register.php";
    public static String URL_DETAILS = URL+FOLDER_NAME_1+"read_detail.php";
    public static String URL_EDIT = URL+FOLDER_NAME_1+"edit_detail.php";

    public static String URL_EDIT_BOOK = URL+FOLDER_NAME_2+"edit_book.php";
    public static String URL_BOOK_DETAILS = URL+FOLDER_NAME_2+"read_detail.php";
    public static String URL_BOOK_DETAILS2 = URL+FOLDER_NAME_2+"read_detail2.php";
    public static String URL_ADDBOOK = URL+FOLDER_NAME_2+"add_book.php";
    public static String URL_DELETE_BOOK = URL+FOLDER_NAME_2+"delete.php";

    public static String URL_EDIT_TB_BOOK = URL+TB_BOOK+"edit_book.php";
    public static String URL_DETAILS_TB_BOOK = URL+TB_BOOK+"read_detail.php";
    public static String URL_DETAILS2_TB_BOOK = URL+TB_BOOK+"read_detail2.php";
    public static String URL_ADD_TB_BOOK = URL+TB_BOOK+"add_book.php";
    public static String URL_DELETE_TB_BOOK = URL+TB_BOOK+"delete.php";

    public static String URL_EDIT_TB_BORROWER = URL+TB_Borrower+"edit_borrower.php";
    public static String URL_DETAILS_TB_BORROWER = URL+TB_Borrower+"read_detail.php";
    public static String URL_DETAILS2_TB_BORROWER = URL+TB_Borrower+"read_detail2.php";
    public static String URL_ADD_TB_BORROWER = URL+TB_Borrower+"add_borrower.php";
    public static String URL_DELETE_TB_BORROWER = URL+TB_Borrower+"delete.php";

    public static String URL_EDIT_TB_BORROWEDBOOK = URL+TB_Borrowedbook+"edit_borrowedbook.php";
    public static String URL_DETAILS_TB_BORROWEDBOOK = URL+TB_Borrowedbook+"read_detail.php";
    public static String URL_DETAILS2_TB_BORROWEDBOOK = URL+TB_Borrowedbook+"read_detail2.php";
    public static String URL_DETAILS3_TB_BORROWEDBOOK = URL+TB_Borrowedbook+"read_detail3.php";
    public static String URL_ADD_TB_BORROWEDBOOK = URL+TB_Borrowedbook+"add_borrowedbook.php";
    public static String URL_DELETE_TB_BORROWEDBOOK = URL+TB_Borrowedbook+"delete.php";
    public static String URL_SPINNER_BOOK_TB_BORROWEDBOOK = URL+TB_Borrowedbook+"spinner_book_detail.php";
    public static String URL_SPINNER_BORROWER_TB_BORROWEDBOOK = URL+TB_Borrowedbook+"spinner_borrower_detail.php";
    public static String URL_BOOK_QUANTITY = URL+TB_Borrowedbook+"read_book_quntity.php";
    public static String URL_EDIT_QUANITTY = URL+TB_Borrowedbook+"edit_book.php";
}
