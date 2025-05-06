package LibraryFunctions;
import java.util.Scanner;

import LL.*;

public class Library {
  
   /**
   * Adds a new category to the library system.
   * Checks for duplicate category names before adding.
   
   * @param sc Scanner object for user input
   * @param category_list List of categories in the library
   * @param book_list List of books in the library
   */

    public  void addCategory(Scanner sc, categoryLl category_list, BookLL book_list) {
        System.out.print("Enter the Category name: ");
       String category_name=sc.nextLine();
       int category_id;
       while (category_list.findByName(category_name)>=1){
            System.out.println("Category already Exists");
            System.out.print("Do you want to choose another category? (y/n): ");
            char choice = sc.next().charAt(0);
            sc.nextLine();
            if (choice == 'n' || choice == 'N') return;
            category_list.display();
            System.out.print("Enter a Category Name: ");
            category_name = sc.nextLine();
            category_id = category_list.findByName(category_name);
        }
        category_list.Setid();
        category_list.insertLast(new categoryLl.Category(category_name));
        System.out.println("Category successfully added.");
    }


    /**
     * Deletes a category from the library system.
     * Ensures no books exist in the category before deletion.
     
     * @param sc Scanner object for user input
     * @param category_list List of categories in the library
     * @param book_list List of books in the library
     */

    public  void deleteCategory(Scanner sc, categoryLl category_list,BookLL book_list) {
       
       category_list.display();
       String Cat_name;
       int id;
       
       while(true){
            System.out.print("Enter a Category Name: ");
            Cat_name=sc.nextLine();
            id=category_list.findByName(Cat_name);
            if(id>=1 && !book_list.findbycatid(id)){
                break;
            }
            System.out.println("Category Not Found / Books consists in Category");
            System.out.print("Do you want to choose another category? (y/n): ");
            char choice = sc.next().charAt(0);
            sc.nextLine();
            if (choice == 'n' || choice == 'N') return;
            category_list.display();
       }
       if(book_list.findbycatid(id)){
            System.out.println("Cannot delete category: Books exist in this category.");
            return;
       }
       category_list.deleteById(id);
       System.out.println("Category successfully deleted.");
       
    }


    /**
    * Adds a new book to the library system.
    * Creates category or author if they don't exist.
    
    * @param sc Scanner object for user input
    * @param book_list List of books in the library
    * @param category_list List of categories in the library
    * @param author_list List of authors in the library
    */

    public  void addBook(Scanner sc, BookLL book_list,categoryLl category_list,AuthorLL author_list) {
        System.out.print("Enter the Title of the Book: ");
        String title =sc.nextLine();
        while(book_list.findByName(title)>=1){
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
        int id= category_list.findByName(cat_name);
        if(id<1){
            category_list.Setid();
            category_list.insertLast(new categoryLl.Category(cat_name));
        }
        author_list.display();
        System.out.print("Enter The Author name of a Book: ");
        String author_name =sc.nextLine();
        int author_id=author_list.findByName(author_name);
        System.out.println(author_id);
        if(author_list.findByName(author_name)==0){
            System.out.println("The Author is not exits ");
            author_list.Setid();
            System.out.println(author_list.getSize());
            addAuthor(sc, author_list);
            author_id=author_list.findByName(author_name);
        }
        
        System.out.print("Enter the publication year of the Book: ");
        String publication_year=sc.nextLine();
        System.out.print("Enter the avialable Copies of a Book: ");
        int aval_cop=sc.nextInt();
        sc.nextLine();
        book_list.Setid();
        book_list.insertLast(new BookLL.Book(id, title, publication_year, author_id, aval_cop));
        book_list.display();
        category_list.display();
        author_list.display();
        
        System.out.println("Book Sucessfully added into Library");
    }


     /**
     * Adds copies to an existing book in the library.
     
     * @param sc Scanner object for user input
     * @param books_list List of books in the library
     */

    public  void addAvailableCopies(Scanner sc, BookLL books_list){
        System.out.print("Enter the Title of the book");
        String Title=sc.nextLine();
        if(books_list.findByName(Title)>0){
            System.out.println("Enter the Book id ");
            int Book_id=sc.nextInt();
            sc.nextLine();
            System.out.print("Enter the avialable Copies of a Book: ");
            int aval_cop=sc.nextInt();
            while(true){
                if(books_list.findById(Book_id)){
                    break;
                }
                System.out.println("Enter the Valid BookId");
                System.out.println("Do you want to continue y/n: ");
                char ch= sc.next().charAt(0);
                sc.nextLine();
                if(ch=='n' || ch=='N') return;
            }
            books_list.deleteById(Book_id, aval_cop);
            System.out.println("Copies are added to the available copies");
            return;

        }
        System.out.println("Entered Title is not present in the books");
    }
        
     /**
     * Deletes a book from the library system.
     * Ensures the book is not currently loaned before deletion.
     
     * @param sc Scanner object for user input
     * @param book_list List of books in the library
     * @param loan_list List of current loans
     */

    public  void deleteBook(Scanner sc, BookLL book_list, LoanLL loan_list){
       String Title;
       int book_id;
       while(true){
        System.out.print("Enter the Book Title: ");
        Title = sc.nextLine();
        book_list.findByName(Title);
        System.out.print("Enter the Book Id: ");
        book_id=sc.nextInt();
        sc.nextLine();
        if(book_list.findById(book_id) && loan_list.getLoanBooksCount(book_id)==0) break;
        System.out.println("Enter the Valid Book Title");
        System.out.println("Do you want to continue y/n: ");
        char ch= sc.next().charAt(0);
        sc.nextLine();
        if(ch=='n' || ch=='N') return;
       }
       book_list.deleteBook(book_id);
       book_list.display();
       System.out.println("Book is Successfully Deleted");
    }


    /**
     * Adds a new author to the library system.
     * Checks for duplicate author names before adding.
     
     * @param sc Scanner object for user input
     * @param author_list List of authors in the library
     */

    public  void addAuthor(Scanner sc, AuthorLL author_list) {
        System.out.print("Enter the Author Name: ");
        String Name=sc.nextLine();
        while (author_list.findByName(Name) > 0) {
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
        author_list.Setid();
        author_list.insertLast(new AuthorLL.Author(Name, Dob, Nationality));
        System.out.println("Author Successfully added.");

    }


     /**
     * Deletes an author from the library system.
     * Ensures the author has no books before deletion.
     
     * @param sc Scanner object for user input
     * @param author_list List of authors in the library
     * @param book_list List of books in the library
     */
    public  void deleteAuthor(Scanner sc, AuthorLL author_list, BookLL book_list) {
        int id;
        while(true){
            author_list.display();
            System.out.print("Enter the Author ID: ");
            id=sc.nextInt();
            sc.nextLine();
            System.out.println(book_list.getAuthorBooksCount(id));
            if(author_list.findById(id)>=1 && book_list.getAuthorBooksCount(id)==0){
                break;
            }
            System.out.println("Author already wrote books. Please enter a different Author.");
            System.out.print("Do you want to delete another Author? (y/n): ");
            char choice = sc.next().charAt(0);
            sc.nextLine();
            if (choice == 'n' || choice == 'N') return;
        }
        author_list.deleteById(id);
        System.out.println("Author is SUccessfully Deleted");
    }
}
