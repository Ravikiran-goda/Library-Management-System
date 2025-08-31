package LL;

import java.util.ArrayList;

public class BookLL{
  public void displayBook(ArrayList<Book> bookList){
    bookList.forEach(System.out::println);
  }
  
  public static class Book{
    private int Book_id;
    private int Category_id;
    private String Title;
    private String publication_year;
    private int author_id;
    private int avl_copies;
    private String authorName;
    public Book( int id,int category_id, String title, String publication_year, int author_id, int avl_copies) {
      Book_id=id;
      Category_id = category_id;
      Title = title;
      this.publication_year = publication_year;
      this.author_id = author_id;
      this.avl_copies = avl_copies;
    }
    
    public Book(int book_id, String title, String publication_year, int avl_copies,
        String authorName) {
      Book_id = book_id;
      Title = title;
      this.publication_year = publication_year;
      this.avl_copies = avl_copies;
      this.authorName = authorName;
    }

    public void setAvail_copies(int aval_cop){
      this.avl_copies = aval_cop;
    }
    public int getBook_id() {
      return Book_id;
    }
    public int getCategory_id() {
      return Category_id;
    }
    public String getTitle() {
      return Title;
    }
    public String getPublication_year() {
      return publication_year;
    }
    public int getAuthor_id() {
      return author_id;
    }
    public int getAvl_copies() {
      return avl_copies;
    }

    @Override
    public String toString() {
      return "Book_id=" + Book_id + ", Title=" + Title + ", publication_year=" + publication_year
          + ", avl_copies=" + avl_copies + ", authorName=" + authorName;
    }
    
    
  }
}
