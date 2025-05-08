package LibraryFunctions;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import Data.*;
import LL.*;

public class Library {
  
     /**
     * Creates a new category in the library system database.
     * 
     * This method handles the category creation process including:
     * - Prompting for a new category name
     * - Validating that the category name doesn't already exist
     * - Allowing multiple attempts if a duplicate is found
     * - Persisting the new category to the database when valid
     *
     * @param sc Scanner object for reading user input
     * @param cdb Category database handler for category operations
     * @param conn Active database connection
     * @param category Category linked list object for displaying existing categories
     * @throws SQLException If any database operation fails during category creation
     */

    public  void addCategory(Scanner sc,categorydb cdb,Connection conn,categoryLl category) throws SQLException{
        System.out.print("Enter the Category name: ");
        String category_name=sc.nextLine();
        int category_id=cdb.getcategoryId(category_name, conn);
        while (category_id>=1){
            System.out.println("Category already Exists");
            System.out.print("Do you want to choose another category? (y/n): ");
            char choice = sc.next().charAt(0);
            sc.nextLine();
            if (choice == 'n' || choice == 'N') return;
            category.display(cdb.getCategory(conn));
            System.out.print("Enter a Category Name: ");
            category_name = sc.nextLine();
            category_id = cdb.getcategoryId(category_name, conn);
        }
        cdb.insertCategoryToDB(category_name, conn);
        System.out.println("Category successfully added.");
    }


     /**
     * Removes a category from the library system database.
     * 
     * This method handles the category deletion process including:
     * - Displaying all existing categories for selection
     * - Validating the category exists before attempting deletion
     * - Ensuring the category contains no books (preventing orphaned book records)
     * - Allowing multiple attempts if invalid selection or non-empty category
     * - Executing the deletion from the database when all conditions are met
     *
     * @param sc Scanner object for reading user input
     * @param cdb Category database handler for category validation and deletion
     * @param bdb Book database handler for checking if books exist in the category
     * @param conn Active database connection
     * @param category Category linked list object for displaying available categories
     * @throws SQLException If any database operation fails during the deletion process
     */

    public  void deleteCategory(Scanner sc,categorydb cdb,bookdb bdb,Connection conn,categoryLl category) throws SQLException{
        category.display(cdb.getCategory(conn));
        String Cat_name;
        int id;
        while(true){
            System.out.print("Enter a Category Name: ");
            Cat_name=sc.nextLine();
            id=cdb.getcategoryId(Cat_name, conn);
            if(id>=1 && !bdb.CategoryContainBooksOrnot(id, conn)){
                break;
            }
            System.out.println("Category Not Found / Books consists in Category");
            System.out.print("Do you want to choose another category? (y/n): ");
            char choice = sc.next().charAt(0);
            sc.nextLine();
            if (choice == 'n' || choice == 'N') return;
            category.display(cdb.getCategory(conn));
        }
        cdb.deleteCategoryToDB(id, conn);
        System.out.println("Category successfully deleted.");
        
    }


     /**
     * Creates a new book entry in the library system database.
     * 
     * This method handles the complete book addition process including:
     * - Validating the book title doesn't already exist in the system
     * - Checking if the specified category exists and creating it if needed
     * - Verifying author existence and providing option to add new authors
     * - Collecting all required book metadata (title, publication year, copies)
     * - Persisting the new book record with all its relationships
     *
     * @param sc Scanner object for reading user input
     * @param cdb Category database handler for category validation and creation
     * @param bdb Book database handler for book operations and validation
     * @param adb Author database handler for author validation and lookups
     * @param conn Active database connection
     * @param author Author linked list object for displaying existing authors
     * @throws SQLException If any database operation fails during the book addition process
     */

    public  void addBook(Scanner sc,categorydb cdb,bookdb bdb,authordb adb,Connection conn,AuthorLL author) throws SQLException{
        System.out.print("Enter the Title of the Book: ");
        String title =sc.nextLine();
        while(bdb.BookExitedOrNot(title, conn)){
            System.out.println("book already existed");
            System.out.print("Do you want to add another Book? (y/n): ");
            char choice = sc.next().charAt(0);
            sc.nextLine();
            if(choice=='n' || choice=='N') return;
            System.out.print("Enter the Title of the Book: ");
            title =sc.nextLine();
        }
        System.out.print("Enter the Category name: ");
        String cat_name=sc.nextLine();
        int id= cdb.getcategoryId(cat_name, conn);
        if(id<1){
            cdb.insertCategoryToDB(cat_name, conn);
            id=cdb.getcategoryId(cat_name, conn);
        }
        author.display(adb.getAuthorsList(conn));
        System.out.print("Enter The Author name of a Book: ");
        String author_name =sc.nextLine();
        int author_id=adb.getAuthorIdIfexists(author_name, conn);
        if(author_id<=0){
            System.out.println("The Author is not exits ");
            addAuthor(sc,adb,conn);
            author_id=adb.getAuthorIdIfexists(author_name, conn);
        }
        
        System.out.print("Enter the publication year of the Book: ");
        String publication_year=sc.nextLine();
        System.out.print("Enter the avialable Copies of a Book: ");
        int aval_cop=sc.nextInt();
        sc.nextLine();
        bdb.insertBookToDB(id, title, publication_year, author_id, aval_cop, conn);
        System.out.println("Book Sucessfully added into Library");
        
    }


     /**
     * Updates the available copy count for an existing book in the library.
     * 
     * This method handles the process of increasing book inventory including:
     * - Validating that the specified book title exists in the system
     * - Allowing multiple attempts if an invalid book title is entered
     * - Retrieving the current available copy count
     * - Adding the specified number of copies to the existing inventory
     * - Updating the database with the new copy count
     *
     * @param sc Scanner object for reading user input
     * @param bdb Book database handler for book validation and inventory updates
     * @param conn Active database connection
     * @throws SQLException If any database operation fails during the inventory update
     */

    public  void addAvailableCopies(Scanner sc,bookdb bdb, Connection conn) throws SQLException{
        int id;
        while(true){
            System.out.print("Enter the Title of the book: ");
            String Title=sc.nextLine();
            if(bdb.BookExitedOrNot(Title, conn)) {
                id=bdb.getBookId(Title, conn);
                break;
            }
            System.out.println("Enter the Valid Title");
            System.out.println("Do you want to continue y/n: ");
            char ch= sc.next().charAt(0);
            sc.nextLine();
            if(ch=='n' || ch=='N') return;
        }
        System.out.print("Enter the Available copies of a Book: ");
        int eac=bdb.availCopies(id, conn);
        int ac=sc.nextInt();
        sc.nextLine();
        bdb.updateBookCopies(id, ac+eac, conn);
        System.out.println("Book copies are Successfully added! ");
        
    }
        
     /**
     * Removes a book from the library system database.
     * 
     * This method handles the book deletion process including:
     * - Validating that the specified book title exists in the system
     * - Checking that the book is not currently on loan to any student
     * - Allowing multiple attempts if an invalid book title is entered or book is on loan
     * - Safely removing the book record from the database when all conditions are met
     * - Providing feedback on the success of the operation
     *
     * @param sc Scanner object for reading user input
     * @param bdb Book database handler for book validation and deletion operations
     * @param ldb Loan database handler for checking if the book is currently on loan
     * @param conn Active database connection
     * @throws SQLException If any database operation fails during the deletion process
     */

    public  void deleteBook(Scanner sc,bookdb bdb,loandb ldb,  Connection conn) throws SQLException{
       String Title;
       int book_id;
       while(true){
        System.out.print("Enter the Book Title: ");
        Title = sc.nextLine();
        book_id=bdb.getBookId(Title, conn);
        if(book_id>0 && ldb.BookinLoanorNot(book_id, conn)) break;
        System.out.println("Enter the Valid Book Title");
        System.out.print("Do you want to continue y/n: ");
        char ch= sc.next().charAt(0);
        sc.nextLine();
        if(ch=='n' || ch=='N') return;
       }
       bdb.deleteBookfromDb(book_id, conn);
       System.out.println("Book is Successfully Deleted");
      
    }


     /**
     * Creates a new author entry in the library system database.
     * 
     * This method handles the author creation process including:
     * - Validating that the author name doesn't already exist in the system
     * - Allowing multiple attempts if a duplicate author name is found
     * - Collecting comprehensive author metadata (name, date of birth, nationality)
     * - Persisting the new author record to the database
     * - Providing confirmation of successful addition
     *
     * @param sc Scanner object for reading user input
     * @param adb Author database handler for author validation and creation
     * @param conn Active database connection
     * @throws SQLException If any database operation fails during the author creation process
     */

    public  void addAuthor(Scanner sc,authordb adb,Connection conn) throws SQLException{
        System.out.print("Enter the Author Name: ");
        String Name=sc.nextLine();
        while (adb.IsAthorContainsorNot(Name, conn)) {
            System.out.print("Do you want to add another Author? (y/n): ");
            char choice = sc.next().charAt(0);
            sc.nextLine();
            if (choice == 'n' || choice == 'N') return;
            System.out.println("Author already exists. Please enter a different name.");
            System.out.print("Enter the Author Name: ");
            Name = sc.nextLine();
           
        }
        System.out.print("Enter the DOB of a Author: ");
        String Dob=sc.nextLine();
        System.out.print("Enter the Nationality of the author: ");
        String Nationality=sc.nextLine();
        adb.insertAuthorToDB(Name, Dob, Nationality, conn);
        System.out.println("Author Successfully added.");

    }


     /**
     * Removes an author from the library system database.
     * 
     * This method handles the author deletion process including:
     * - Validating that the specified author exists in the system
     * - Ensuring the author has no associated books in the database (preventing orphaned records)
     * - Allowing multiple attempts if an invalid author is selected or author has books
     * - Safely removing the author record when all conditions are met
     * - Providing confirmation of successful deletion
     *
     * @param sc Scanner object for reading user input
     * @param adb Author database handler for author validation and deletion operations
     * @param conn Active database connection
     * @throws SQLException If any database operation fails during the deletion process
     */
    public  void deleteAuthor(Scanner sc,authordb adb,Connection conn) throws SQLException{
        String Name;
        int id;
        while(true){
            System.out.print("Enter the Author name: ");
            Name=sc.nextLine();
            id=adb.getAuthorIdIfexists(Name, conn);
            if(adb.IsAthorContainsorNot(Name, conn) && adb.getAuthorBooksCount(id, conn)==0){
                break;
            }
            System.out.println("Author already wrote books. Please enter a different Author.");
            System.out.print("Do you want to delete another Author? (y/n): ");
            char choice = sc.next().charAt(0);
            sc.nextLine();
            if (choice == 'n' || choice == 'N') return;
        }
        adb.deleteAuthorToDB(id, conn);
        System.out.println("Author is SUccessfully Deleted");
    }
}
