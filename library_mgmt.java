import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.*;
import java.util.Scanner;

import Data.Db;
import Data.ReadWrite;
import LL.*;
import Student.*;
import LibraryFunctions.*;
/*
    This application provides functionality for managing a library system with two types of users:
    Student - Can borrow and return books
    Librarian - Can manage categories, books, authors, and other administrative tasks
    The system uses serialization to persist data between program executions.
    @author Ravi Kiran Gunnabattula
    @version 30-04-2025 v1

 */
public class library_mgmt {
    
    public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException, SQLException {
        Scanner sc = new Scanner(System.in);
        File Librarydata=new File("D:\\library_mgmt\\library_mgmt\\library_mgmt\\library.dat");
        MemberShipll student_list = new MemberShipll();
        AuthorLL author_list = new AuthorLL();
        categoryLl category_list=new categoryLl();
        BookLL book_list=new BookLL();
        LoanLL loan_list = new LoanLL();
        ReadWrite rw=new ReadWrite();
        // rw.readObjectsFromFile(Librarydata, category_list, book_list, author_list, student_list, loan_list);
         /**
         * Reads serialized linked list objects from a file and loads them into memory.
         * This includes category, book, author, membership, and loan linked lists.
         
         * @param Librarydata The file from which to read the serialized objects
         * @throws IOException if an I/O error occurs during reading
         * @throws ClassNotFoundException if a class of a serialized object cannot be found
         */

        try(FileInputStream fin= new FileInputStream(Librarydata)){
            ObjectInputStream ois= new ObjectInputStream(fin);
            category_list=(categoryLl)ois.readObject();
            book_list=( BookLL)ois.readObject();
            author_list=(AuthorLL)ois.readObject();
            student_list=(MemberShipll)ois.readObject();
            loan_list=(LoanLL)ois.readObject();
            ois.close();
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        Dbintiation dbintiation=new Dbintiation();
        Connection conn=dbintiation.getConnection();
        Db database=new Db();
        database.DisplayCategory(conn);
        System.out.println(database.getcategoryId("ECE", conn));
        System.out.println(database.getcategoryId("MECHANICAL", conn));
        System.out.println(database.getcategoryId("CSE", conn));
        System.out.println(database.getBooksCountByCategory(database.getcategoryId("ECE", conn), conn));
        database.displaybycatandAUthor(database.getcategoryId("ECE", conn), conn);
        System.out.println(database.isBookInCategory(7, database.getcategoryId("ECE",conn),conn));
        System.out.println(database.availCopies(1, conn));
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
                    MemberShipll.Student student;
                    sc.nextLine();
                    if (!database.getStudentId(student_id, conn)) {
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
                        database.insertStudentsToDB(new MemberShipll.Student(student_id, name, email, mobile_no, address, memberShipDate),conn);
                    }
                    student = student_list.getstudent(student_id);
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
                                studentFun.borrowBook(sc,student_id,database,conn);
                                break;          
                            case 2:
                               studentFun.returnBook(sc,student_id,database,conn);
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
                                 LibraryFun.addCategory(sc, category_list,book_list);
                                 break;
                            case 2:
                                LibraryFun.deleteCategory(sc, category_list,book_list);
                                break;
                            case 3:
                                 LibraryFun.addBook(sc, book_list,category_list,author_list);
                                 break;
                            case 4: 
                                LibraryFun.addAvailableCopies(sc, book_list);
                                break;
                            case 5:
                                LibraryFun.deleteBook(sc, book_list,loan_list);
                                break;
                            case 6:
                                LibraryFun.addAuthor(sc, author_list);
                                break;
                            case 7:
                                LibraryFun.deleteAuthor(sc, author_list,book_list);
                                break;
                            case 8:
                                System.out.println("Return to main Menu");
                                op=0;
                                rw.writeobjectsintofile(Librarydata, category_list, book_list,author_list,student_list, loan_list);
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
    }
}
