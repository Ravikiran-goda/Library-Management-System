import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.*;
import java.util.Scanner;

import Data.*;
import LL.*;
import Student.*;
import LibraryFunctions.*;
/*
    This application provides functionality for managing a library system with two types of users:
    Student - Can borrow and return books
    Librarian - Can manage categories, books, authors, and other administrative tasks
    The system uses serialization to persist data between program executions.
    @author Ravi Kiran Gunnabattula
    @version 30-04-2025 v3 jdbc

 */
public class library_mgmt {
    
    public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException, SQLException {
        Scanner sc = new Scanner(System.in);
        AuthorLL author_list = new AuthorLL();
        categoryLl category_list=new categoryLl();
        BookLL book_list=new BookLL();
        LoanLL loan_list = new LoanLL();

        Dbintiation dbintiation=new Dbintiation();
        Connection conn=dbintiation.getConnection();
        
        studentdb sdb=new studentdb();
        categorydb cdb= new categorydb();
        bookdb bdb=new bookdb();
        loandb ldb=new loandb();
        authordb adb= new authordb();
        
        int choice;
        Student studentFun= new Student();
        Library LibraryFun=new Library();
        
        do {
            System.out.println("Welcome to the Library Management System");
            System.out.println("1. Student");
            System.out.println("2. Librarian");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();
           
            switch (choice) {
                case 1 -> {
                    System.out.print("Enter Student ID: ");
                    int student_id = sc.nextInt();
                    sc.nextLine();
                    if (!sdb.isStudentHasMember(student_id, conn)) {
                        System.out.println("Student not found. Creating new membership.");
                        System.out.print("Enter Name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter Email: ");
                        String email = sc.nextLine();
                        System.out.print("Enter Mobile No: ");
                        String mobile_no = sc.nextLine();
                        System.out.print("Enter Address: ");
                        String address = sc.nextLine();
                        LocalDate memberShipDate = LocalDate.now();
                        sdb.insertStudentsToDB(new MemberShipll.Student(student_id, name, email, mobile_no, address, memberShipDate),conn);
                    }
                    int Student_choice;
                  
                    do {
                        System.out.println("1 -> Borrow a Book");
                        System.out.println("2 -> Return a Book");
                        System.out.println("3 -> Exit to Main Menu");
                        System.out.print("Choose option: ");
                        Student_choice = sc.nextInt();
                        sc.nextLine();
                        
                        switch (Student_choice) {
                            case 1:
                                studentFun.borrowBook(sc,student_id,category_list,book_list,sdb,cdb,bdb,ldb,conn);
                                break;          
                            case 2:
                               studentFun.returnBook(sc,student_id,loan_list,sdb,bdb,ldb,conn);
                                break;
                            case 3:
                                System.out.println("Return to main menu");
                                break;
                            default:
                                System.out.println("Invalid choice.");
                                break;
                        }
                    } while (Student_choice!=0 && Student_choice>3);
                }

                case 2 -> {
                    System.out.print("Enter Librarian ID: ");
                    int libId = sc.nextInt();
                    sc.nextLine();
                    if (libId <= 0) {
                        System.out.println("Invalid Librarian ID");
                        break;
                    }
                    int op;
                    do {
                        System.out.println("1 -> Add Category");
                        System.out.println("2 -> Delete Category");
                        System.out.println("3 -> Add a new Book");
                        System.out.println("4 -> Add a Add Available Copies into a Book");
                        System.out.println("5 -> Delete Book");
                        System.out.println("6 -> Add Author");
                        System.out.println("7 -> Delete Author");
                        System.out.println("8 -> Exit to Main Menu");
                        System.out.print("Choose option: ");
                        op = sc.nextInt();
                        sc.nextLine();
                        switch (op) {
                            case 1:
                                LibraryFun.addCategory(sc,cdb,conn,category_list);
                                 break;
                            case 2:
                                LibraryFun.deleteCategory(sc,cdb,bdb,conn,category_list);
                                break;
                            case 3:
                                 LibraryFun.addBook(sc,cdb,bdb,adb,conn,author_list);
                                 break;
                            case 4: 
                                LibraryFun.addAvailableCopies(sc,bdb,conn);
                                break;
                            case 5:
                                LibraryFun.deleteBook(sc, bdb,ldb,conn);
                                break;
                            case 6:
                                LibraryFun.addAuthor(sc, adb,conn);
                                break;
                            case 7:
                                 LibraryFun.deleteAuthor(sc, adb,conn);
                                break;
                            case 8:
                                System.out.println("Return to main Menu");
                                op=0;
                                break;
                            default: 
                                System.out.println("Invalid choice.");
                                op = 1;
                            
                        }
                    } while (op !=0);
                }

                case 3 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid Choice");
            }
        } while (choice != 3);
        sc.close();
        conn.close();
    }
}
