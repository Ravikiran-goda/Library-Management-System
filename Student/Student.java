package Student;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

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
   */
  public  void borrowBook(Scanner sc, MemberShipll.Student student, BookLL book_list, categoryLl category_list, LoanLL loan_list,MemberShipll student_list,AuthorLL author_list)  {
    if (student.getcount() >= 5) {
          System.out.println("Sorry, You have reached the maximum limit of 5 books.");
          return;
      }
      category_list.display();
      System.out.print("Enter a Category Name: ");
      String category_name = sc.nextLine();
      int category_id = category_list.findByName(category_name);
      if (category_id <= 0 ) {
          System.out.println("Category not found.");
          return;
      }
      while (!book_list.findbycatid(category_id)) {
        System.out.println("Books in this category are currently unavailable. Please come back in 3 to 4 days.");
        System.out.print("Do you want to choose another category? (y/n): ");
        char choice = sc.next().charAt(0);
        sc.nextLine();
        if (choice == 'n' || choice == 'N') return;
        category_list.display();
        System.out.print("Enter a Category Name: ");
        category_name = sc.nextLine();
        category_id = category_list.findByName(category_name);
    }
    
  
    //   book_list.findbycatid(category_id);
      
      int book_id;
      while (true) {
          book_list.displaybookbycat(category_id,author_list);
          System.out.print("Enter the Book ID you want to borrow: ");
          book_id = sc.nextInt();
          sc.nextLine();
          boolean check = book_list.findById(book_id,category_id) && book_list.getavailblecopies(book_id)>=1;
          if (check) {
              break;
          } else {
              System.out.println("Book ID not found in Category / Books are in Loan  Please come back in 3 to 4 days.");
              System.out.print("Do you want to try again? (y/n): ");
              char choice = sc.next().charAt(0);
              sc.nextLine();
              if (choice == 'n' || choice == 'N') return;
          }
      }
  
    int student_id = student.getStudent_id();
    book_list.deleteById(book_id, -1);
    loan_list.Setid();
    LocalDateTime currentDateTime = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd   HH:mm:ss");
    String formattedDateTime = currentDateTime.format(formatter);
    LoanLL.Loan loan = new LoanLL.Loan(student_id,formattedDateTime,book_id);
    loan_list.insertLast(loan);
    student.addbookid(book_id);
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

    public void returnBook(Scanner sc, MemberShipll.Student student, BookLL book_list, LoanLL loan_list) {
       if(!loan_list.display(student.getStudent_id())){
        System.out.println("Your Id doesn't have loans");
        return;
       }
       while(student.getLenofBooks()>0){
            System.out.print("Enter the LoanId: "); 
            int loan_id=sc.nextInt();
            sc.nextLine();
            System.out.print("Enter the Book Id: ");
            int Book_id=sc.nextInt();
            sc.nextLine();
            if(student.isPresentorNot(Book_id)==-1){
                    System.out.println("This book was not borrowed by you");
                    return;
            }
            
            student.removebook(Book_id);
            book_list.deleteById(Book_id,1);
            loan_list.SetReturnDate(Book_id,loan_id);
        
            System.out.println("Book successfully returned.");
            System.out.print("Do you have any other books to be returned y/n: ");
            char choice =sc.next().charAt(0);
            sc.nextLine();
            if(choice=='n' || choice=='N') return ;
            loan_list.display(student.getStudent_id());
       }
    }
}
