package Student;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import Data.*;
import LL.*;

public class Student {

  /**
 * Processes a book borrowing transaction for a student in the library system.
 * 
 * This method handles the entire borrowing workflow including:
 * - Verifying if the student has not exceeded their borrowing limit (max 5 books)
 * - Displaying available categories for selection
 * - Validating category selection and checking book availability
 * - Displaying books within the selected category
 * - Processing the book loan transaction
 * - Updating necessary database records
 *
 * @param sc Scanner object for reading user input
 * @param StudentId ID of the student attempting to borrow a book
 * @param cat Category linked list object for displaying categories
 * @param booklist Book linked list object for displaying available books
 * @param sdb Student database handler for student-related operations
 * @param cdb Category database handler for category-related operations
 * @param bdb Book database handler for book-related operations
 * @param ldb Loan database handler for managing book loans
 * @param conn Active database connection
 * @throws SQLException If any database operation fails
 */

  public  void borrowBook(Scanner sc,int StudentId,categoryLl cat,BookLL booklist,studentdb sdb,categorydb cdb,bookdb bdb,loandb ldb,Connection conn) throws SQLException{
   int count=sdb.getBookCountinLimit(StudentId, conn);
    if (count>5){
          System.out.println("Sorry, You have reached the maximum limit of 5 books.");
          return;
      }
      cat.display(cdb.getCategory(conn));
      System.out.print("Enter a Category Name: ");
      String category_name = sc.nextLine();
      int category_id =cdb.getcategoryId(category_name, conn);
      if (category_id <= 0 ) {
          System.out.println("Category not found.");
          return;
      }
      
      while (cdb.getBooksCountByCategory(category_id,conn)==0) {
        System.out.println("Books in this category are currently unavailable. Please come back in 3 to 4 days.");
        System.out.print("Do you want to choose another category? (y/n): ");
        char choice = sc.next().charAt(0);
        sc.nextLine();
        if (choice == 'n' || choice == 'N') return;
        cat.display(cdb.getCategory(conn));
        System.out.print("Enter a Category Name: ");
        category_name = sc.nextLine();
        category_id = cdb.getcategoryId(category_name, conn);
    } 
      int book_id;
      int availcopies;
      while (true) {
          booklist.displayBook(bdb.getBookandAUthor(category_id, conn));
          System.out.print("Enter the Book ID you want to borrow: ");
          book_id = sc.nextInt();
          sc.nextLine();
          boolean check = bdb.isBookInCategory(book_id,category_id,conn) && bdb.availCopies(book_id, conn)>0;
          if (check) {
              availcopies=bdb.availCopies(book_id, conn);
              break;
          } else {
              System.out.println("Book ID not found in Category / Books are in Loan  Please come back in 3 to 4 days.");
              System.out.print("Do you want to try again? (y/n): ");
              char choice = sc.next().charAt(0);
              sc.nextLine();
              if (choice == 'n' || choice == 'N') return;
          }
      }
      bdb.updateBookCopies(book_id, availcopies-1, conn);
      LocalDateTime currentDateTime = LocalDateTime.now();
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd   HH:mm:ss");
      String formattedDateTime = currentDateTime.format(formatter);
      sdb.updateBookCounttoDb(count , StudentId, +1, conn);
      ldb.insertLoanToDB(StudentId,book_id,formattedDateTime, conn);
      System.out.println("Book successfully borrowed!");
  }

   /**
   * Manages the book return process for a student in the library system.
   * 
   * This method handles the complete return workflow including:
   * - Displaying all active loans for the given student
   * - Verifying that the student has active loans to return
   * - Validating that the selected book was actually borrowed by this student
   * - Processing the book return transaction
   * - Updating book availability in the inventory
   * - Recording the return date and time
   * - Handling multiple returns in a single session if requested
   *
   * @param sc Scanner object for reading user input
   * @param StudentId ID of the student returning book(s)
   * @param loanlist Loan linked list object for displaying active loans
   * @param sdb Student database handler for updating student borrowing counts
   * @param bdb Book database handler for updating book availability
   * @param ldb Loan database handler for recording return transactions
   * @param conn Active database connection
   * @throws SQLException If any database operation fails during the return process
   */

    public void returnBook(Scanner sc,int StudentId,LoanLL loanlist, studentdb sdb,bookdb bdb,loandb ldb, Connection conn) throws SQLException{
        loanlist.displayLoans(ldb.getActiveLoans(StudentId, conn));
        int count=sdb.getBookCountinLimit(StudentId, conn);
        if(count==0){
          System.out.println("Your Id doesn't have loans");
          return;
        }
       int loan_id;
       int Book_id;
       int availCopies;
       while(count>0){
            count=sdb.getBookCountinLimit(StudentId, conn);
            System.out.print("Enter the LoanId: "); 
            loan_id=sc.nextInt();
            sc.nextLine();
            System.out.print("Enter the Book Id: ");
            Book_id=sc.nextInt();
            sc.nextLine();
            if(!ldb.isbookBorrowedBy(StudentId, loan_id, conn)){
                    System.out.println("This book was not borrowed by you"); 
                    return;
            }
            sdb.updateBookCounttoDb(count , StudentId, -1, conn);
            availCopies=bdb.availCopies(Book_id, conn);
            bdb.updateBookCopies(Book_id, availCopies+1, conn);
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd   HH:mm:ss");
            String formattedDateTime = currentDateTime.format(formatter);
            ldb.SetReturnDate(loan_id, formattedDateTime, conn);
            System.out.println("Book successfully returned.");
            if(sdb.getBookCountinLimit(StudentId, conn)==0) return;
            System.out.print("Do you have any other books to be returned y/n: ");
            char choice =sc.next().charAt(0);
            sc.nextLine();
            if(choice=='n' || choice=='N') return ;
            loanlist.displayLoans(ldb.getActiveLoans(StudentId, conn));
         
       }
    }
}
