import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.Locale.Category;

public class library_mgmt {
    
    public static void writeobjectsintofile(File file,categoryLl category_list,BookLL obj2,AuthorLL obj3,MemberShipll obj4,LoanLL obj5 ) throws FileNotFoundException, IOException{
      if(file.exists()){
        file.createNewFile();
      }
      ObjectOutputStream oos= new ObjectOutputStream(new FileOutputStream(file));
      oos.writeObject(category_list);
      oos.writeObject(obj2);
      oos.writeObject(obj3);
      oos.writeObject(obj4);
      oos.writeObject(obj5);
      oos.close();
    }
    public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
        Scanner sc = new Scanner(System.in);
        File Librarydata=new File("D:\\library_mgmt\\library_mgmt\\library_mgmt\\library.dat");
        MemberShipll student_list = new MemberShipll();
        AuthorLL author_list = new AuthorLL();
        categoryLl category_list=new categoryLl();
        BookLL book_list=new BookLL();
        LoanLL loan_list = new LoanLL();
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
        category_list.display();
        System.out.println();
        book_list.display();
        System.out.println();
        author_list.display();
        System.out.println();
        loan_list.display();
        System.out.println();
        student_list.display();
        int choice;
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
                    if (!student_list.findById(student_id)) {
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
                        student_list.insertLast(new MemberShipll.Student(student_id, name, email, mobile_no, address, memberShipDate));
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
                                borrowBook(sc, student, book_list, category_list, loan_list,student_list,author_list,Librarydata);
                                writeobjectsintofile(Librarydata, category_list, book_list,author_list,student_list, loan_list);
                                break;

          
                            case 2:
                                returnBook(sc, student, book_list, loan_list);
                                writeobjectsintofile(Librarydata, category_list, book_list,author_list,student_list, loan_list);
                                break;
                            case 3:
                                System.out.println("Return to main menu");
                                writeobjectsintofile(Librarydata, category_list, book_list,author_list,student_list, loan_list);
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
                        System.out.println("3 -> Add Book");
                        System.out.println("4 -> Delete Book");
                        System.out.println("5 -> Add Author");
                        System.out.println("6 -> Delete Author");
                        System.out.println("7 -> Exit to Main Menu");
                        System.out.print("Choose option: ");
                        op = sc.nextInt();
                        sc.nextLine();
                        switch (op) {
                            case 1:
                                 addCategory(sc, category_list,book_list);
                                 writeobjectsintofile(Librarydata, category_list, book_list,author_list,student_list, loan_list);
                                 break;
                            case 2:
                                deleteCategory(sc, category_list,book_list);
                                writeobjectsintofile(Librarydata, category_list, book_list,author_list,student_list, loan_list);
                                break;
                            case 3:
                                 addBook(sc, book_list,category_list,author_list);
                                 writeobjectsintofile(Librarydata, category_list, book_list,author_list,student_list, loan_list);
                                 break;
                            case 4:
                                 deleteBook(sc, book_list);
                                 break;
                            case 5:
                                addAuthor(sc, author_list);
                                writeobjectsintofile(Librarydata, category_list, book_list,author_list,student_list, loan_list);
                                break;
                            case 6:
                                deleteAuthor(sc, author_list);
                                break;
                            case 7:
                                System.out.println("exit");
                                op=0;
                                writeobjectsintofile(Librarydata, category_list, book_list,author_list,student_list, loan_list);
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

  private static void borrowBook(Scanner sc, MemberShipll.Student student, BookLL book_list, categoryLl category_list, LoanLL loan_list,MemberShipll student_list,AuthorLL author_list,File file) throws FileNotFoundException, IOException {
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
          boolean check = book_list.findById(book_id);
          System.out.println(check);
          if (check) {
                System.out.println("id found");
              break;
          } else {
              System.out.println("Book ID not found.");
              System.out.print("Do you want to try again? (y/n): ");
              char choice = sc.next().charAt(0);
              sc.nextLine();
              if (choice == 'n' || choice == 'N') return;
          }
      }
  
      int student_id = student.getStudent_id();
      loan_list.SetNextId();
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd   HH:mm:ss");
       String formattedDateTime = currentDateTime.format(formatter);
      LoanLL.Loan loan = new LoanLL.Loan(student_id,formattedDateTime,book_id);
      loan_list.insertLast(loan);
      student.addbookid(book_id);
      book_list.deleteById(book_id,-1);
      System.out.println("Book successfully borrowed!");
  }
  
    private static void returnBook(Scanner sc, MemberShipll.Student student, BookLL book_list, LoanLL loan_list) {
        loan_list.display();
        System.out.print("Enter the loan Id: ");
        int loan_id=sc.nextInt();
        sc.nextLine();
        System.out.print("Enter the Book Id: ");
        int Book_id=sc.nextInt();
        sc.nextLine();
        if(student.isPresentorNot(Book_id)==-1){
                System.out.println("This book was not borrowed by you");
                return;
        }
        loan_list.findById(Book_id);
        student.removebook(Book_id);
        book_list.deleteById(Book_id,1);
        loan_list.SetReturnDate(Book_id,loan_id);
        loan_list.display();
        System.out.println("Book successfully returned.");

    }

    private static void addCategory(Scanner sc, categoryLl category_list, BookLL book_list) {
        System.out.print("Enter the Category name: ");
       String branch= sc.nextLine();
       if(category_list.findByName(branch)>=1){
            System.out.println("Category already Exists");
            return;
       }
       System.out.println(category_list.getSize());
       category_list.Setid();
       category_list.insertLast(new categoryLl.Category(branch));
       System.out.println("Category successfully added.");
    }

    private static void deleteCategory(Scanner sc, categoryLl category_list,BookLL book_list) {
       
       category_list.display();
       String Cat_name = sc.nextLine();
       int id= category_list.findByName(Cat_name);
       if(id<1){
        System.out.println("Category Not Found");
        return;
       }
       if(book_list.findbycatid(id)){
        System.out.println("Cannot delete category: Books exist in this category.");
        return;
       }
       category_list.deleteById(id);
       System.out.println("Category successfully deleted.");
       
    }

    private static void addBook(Scanner sc, BookLL book_list,categoryLl category_list,AuthorLL author_list) {
        System.out.print("Enter the Title of the Book: ");
        String title =sc.nextLine();
        if(book_list.findByName(title)>=1){
            System.out.print("Enter The Author name of a Book: ");
            String author_name =sc.nextLine();
            int author_id=author_list.findByName(author_name);
            System.out.print("Enter the publication year of the Book: ");
            String publication_year=sc.nextLine();
            System.out.print("Enter the avialable Copies of a Book: ");
            int aval_cop=sc.nextInt();
            book_list.addIfParametersAreEqual(title,author_id,publication_year,aval_cop,book_list);
            System.out.println("Book already existed");
            return;
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
            library_mgmt.addAuthor(sc, author_list);
            author_id=author_list.findByName(author_name);
        }
        
        System.out.print("Enter the publication year of the Book: ");
        String publication_year=sc.nextLine();
        System.out.print("Enter the avialable Copies of a Book: ");
        int aval_cop=sc.nextInt();
        sc.nextLine();
        book_list.Setid();
        BookLL.Book TEMP = new BookLL.Book(id, title, publication_year, author_id, aval_cop);
        book_list.display();
        category_list.display();
        author_list.display();
        book_list.insertLast(TEMP);
        System.out.println("Book Sucessfully added into Library");
    }

    private static void deleteBook(Scanner sc, BookLL book_list) {
       
    }

    private static void addAuthor(Scanner sc, AuthorLL author_list) {
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

    private static void deleteAuthor(Scanner sc, AuthorLL author_list) {
       
    }
}
