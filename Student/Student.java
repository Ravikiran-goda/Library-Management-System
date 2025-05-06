package Student;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import Data.Db;
import LL.*;

public class Student {

  /**
   * Handles the book borrowing process for a student.
   * Checks if the student has reached the maximum borrowing limit,
   * then guides them through selecting a category and book to borrow.
   
   * @param sc Scanner object for user input
   * @param student The student who wants to borrow a book
   * @param book_list List of books in the library
   * @param category_list List of categories in the library
   * @param loan_list List of current loans
   * @param student_list List of all student memberships
   * @param author_list List of authors in the library
  //  */
  public  void borrowBook(Scanner sc,int StudentId, Db db,Connection conn) throws SQLException{
   int count=db.isBookCountinLimit(StudentId, conn);
    if (count>5){
          System.out.println("Sorry, You have reached the maximum limit of 5 books.");
          return;
      }
      db.DisplayCategory(conn);
      System.out.print("Enter a Category Name: ");
      String category_name = sc.nextLine();
      int category_id =db.getcategoryId(category_name, conn);
      if (category_id <= 0 ) {
          System.out.println("Category not found.");
          return;
      }
      System.out.println(db.getBooksCountByCategory(category_id, conn));
      while (db.getBooksCountByCategory(category_id,conn)==0) {
        System.out.println("Books in this category are currently unavailable. Please come back in 3 to 4 days.");
        System.out.print("Do you want to choose another category? (y/n): ");
        char choice = sc.next().charAt(0);
        sc.nextLine();
        if (choice == 'n' || choice == 'N') return;
        db.DisplayCategory(conn);
        System.out.print("Enter a Category Name: ");
        category_name = sc.nextLine();
        category_id = db.getcategoryId(category_name, conn);
    } 
      int book_id;
      int availcopies;
      while (true) {
          db.displaybycatandAUthor(category_id, conn);
          System.out.print("Enter the Book ID you want to borrow: ");
          book_id = sc.nextInt();
          sc.nextLine();
          // boolean check = book_list.findById(book_id,category_id) && book_list.getavailblecopies(book_id)>=1;

          boolean check = db.isBookInCategory(book_id,category_id,conn) && db.availCopies(book_id, conn)>0;
          if (check) {
              availcopies=db.availCopies(book_id, conn);
              break;
          } else {
              System.out.println("Book ID not found in Category / Books are in Loan  Please come back in 3 to 4 days.");
              System.out.print("Do you want to try again? (y/n): ");
              char choice = sc.next().charAt(0);
              sc.nextLine();
              if (choice == 'n' || choice == 'N') return;
          }
      }
    db.updateBookCopies(book_id, availcopies-1, conn);
    LocalDateTime currentDateTime = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd   HH:mm:ss");
    String formattedDateTime = currentDateTime.format(formatter);
    LoanLL.Loan loan = new LoanLL.Loan(StudentId,formattedDateTime,book_id);
    db.updateBookCounttoDb(count , StudentId, +1, conn);
    db.insertLoanToDB(loan, conn);
    System.out.println("Book successfully borrowed!");
  }

   /**
   * Handles the book return process for a student.
   * Displays loans for the student and processes returns.
   
   * @param sc Scanner object for user input
   * @param student The student who wants to return a book
   * @param book_list List of books in the library
   * @param loan_list List of current loans
   */

    public void returnBook(Scanner sc,int StudentId, Db db,Connection conn) throws SQLException{
      db.displayLoans(StudentId, conn);
      int count=db.isBookCountinLimit(StudentId, conn);
      if(count==0){
        System.out.println("Your Id doesn't have loans");
        return;
       }
       int loan_id;
       int Book_id;
       while(db.isBookCountinLimit(StudentId, conn)>0){
            System.out.print("Enter the LoanId: "); 
            loan_id=sc.nextInt();
            sc.nextLine();
            System.out.print("Enter the Book Id: ");
            Book_id=sc.nextInt();
            sc.nextLine();
            if(!db.isbookBorrowedBy(StudentId, Book_id, conn)){
                    System.out.println("This book was not borrowed by you");
                    return;
            }
            db.updateBookCounttoDb(count , StudentId, -1, conn);
            db.updateBookCopies(Book_id, db.availCopies(Book_id, conn)+1, conn);
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd:mm:yyyy   HH:MM:SS");
            String Dt= currentDateTime.format(formatter);

           db.SetReturnDate(loan_id, Dt, conn);
        
            System.out.println("Book successfully returned.");
            System.out.print("Do you have any other books to be returned y/n: ");
            char choice =sc.next().charAt(0);
            sc.nextLine();
            if(choice=='n' || choice=='N') return ;
            db.displayLoans(StudentId, conn);
       }
    }
}
