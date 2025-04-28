import java.io.Serializable;

public class BookLL implements Serializable{
  private static final long serialVersionUID = 832928312981928492L;
  private Node head;
  private Node tail;
  private int size;

//   Validates an categoriess object and prints errors to console
 
  public void insertLast(Book object){
      if(head==null || tail==null){
        head= new Node(object);
        tail=head;
        size++;
        return ;
      }
      Node temp=new Node(object);
      tail.next=temp;
      tail=temp;
      size++;
  }

  public void display(){
      Node temp=head;
      while(temp!=null){
          System.out.println(temp.object);
          temp=temp.next;
      }
      System.out.println("END");
  }
  public int getavailblecopies(int id){
    Node temp=head;
    while(temp!=null){
      if(temp.object.getBook_id()==id){
        return temp.object.getAvl_copies();
      }
    }
    return 0;
  }
  public int deleteById(int id, int num){
      if (size==0){
          return -1;
      }
      if (head.object.getBook_id()==id){
        if(head.object.getAvl_copies()>1){
          head.object.setAvail_copies(head.object.getAvl_copies()+num);
          return 2;
        }
        return 1;
      }
      Node temp=head.next;
      while (temp != null) {
          if (temp.object.getBook_id() == id) {
                if(temp.object.getAvl_copies()==0){
                  System.out.println("Currently books are in loan please comeback within 3-4 days");
                  return -1;
                }
                if(temp.object.getAvl_copies()>0){
                  temp.object.setAvail_copies(temp.object.getAvl_copies()+num);
                    return 2;
                }
              return 1;
          }
          temp = temp.next;
      }
      return -1;

  }
  public boolean addIfParametersAreEqual(String Title, int Author_id,String publication_year,int Available_copies,BookLL list){
   
   Node temp=head;
   while(temp!=null){
    if(temp.object.getTitle()==Title && temp.object.getAuthor_id()==Author_id && temp.object.getPublication_year()==publication_year){
      int book_id=list.findByName(Title);
      list.deleteById(book_id,-Available_copies);
      return true;
    }
    temp=temp.next;
   }
    return false;
  }
  public int findByName(String Title){
      if (size==0){
          return 0;
      }
      if(Title.length()==0){
          System.out.println("Please Provide the Proper Name which contains >1 character");
          return -1;
      }
      Node temp=head;
      int check=0;
      while(temp!=null){
          if(temp.object.Title.toLowerCase().contains(Title.toLowerCase())){
              System.out.println(temp.object);
              check=temp.object.getBook_id();
          }
          temp=temp.next;
      }
      return check;
  }
  public boolean findById(int id){
      if(size==0){
          return false ;
      }
      if (id<=0){
          System.out.println("Please Provide the Proper id greater than 0");
          return false;
      }
      Node temp=head;
      while(temp!=null){
          if(temp.object.getBook_id()==id){
            return true;
          }
          temp=temp.next;
      }
      return false;
  }
  public void displaybookbycat(int cat_id,AuthorLL obj){
    Node temp=head;
    while(temp!=null){
      if(temp.object.getCategory_id()==cat_id){
        System.out.println(temp.object+" "+ obj.getAuthor(temp.object.getAuthor_id()));
      }
      temp=temp.next;
    }
  }
  public boolean findbycatid(int catid){
      if(size==0){
        return false ;
      }
      if (catid<=0){
          System.out.println("Please Provide the Proper id greater than 0");
          return false;
      }
      Node temp=head;
      boolean check=false;
      while(temp!=null){
          if(temp.object.getCategory_id()==catid){
              check=true;
          }
          temp=temp.next;
      }
      return check;
  }

  public int getSize() {
      return size;
  }
  
  public BookLL() {
      this.size = 0;
  }
  
  public BookLL.Book getTail() {
      return tail.object;
  }

  private  class Node implements Serializable{
    private static final long serialVersionUID = 582758493274928201L;
      private Book object;
      private Node next;

      public Node(Book object) {
          this.object = object;
      }

      
  }
  public void Setid(){
    Book.setId(size+1);
  }
  // // record Book(int Book_id,int Category_id, String Title, String publication_year,int author_id,int avl_copies){}
  public static class Book implements Serializable{
    private static final long serialVersionUID = 182736492839182039L;
    private static int nextid=1;
    private int Book_id;
    private int Category_id;
    private String Title;
    private String publication_year;
    private int author_id;
    private int avl_copies;
    public Book( int category_id, String title, String publication_year, int author_id, int avl_copies) {
      Book_id = nextid++;
      Category_id = category_id;
      Title = title;
      this.publication_year = publication_year;
      this.author_id = author_id;
      this.avl_copies = avl_copies;
    }
    public static void setId(int size){
      nextid=size;
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
      return "Book [Book_id=" + Book_id + ", Category_id=" + Category_id + ", Title=" + Title + ", publication_year="
          + publication_year + ", author_id=" + author_id + ", avl_copies=" + avl_copies + "]";
    }
    
  }
}
