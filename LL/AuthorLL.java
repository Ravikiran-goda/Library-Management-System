package LL;
import java.io.Serializable;

public class AuthorLL implements Serializable{
    private static final long serialVersionUID = 284920384928374958L;
  private Node head;
  private Node tail;
  private int size;


 
  public void insertLast(Author object){
      if(head==null || tail==null){
        head=new Node(object);
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

  public int deleteById(int id){
      if (size==0){
          return -1;
      }
      Node temp=head;
      if (head.object.author_id()==id){
          head=head.next;
          return id;
      }
      while (temp.next != null) {
          if (temp.next.object.author_id() == id ){
            System.out.println(temp.object.name());
              Author removedAuthor = temp.next.object;
              temp.next = temp.next.next;
              if (temp.next == null) {
                  tail = temp; 
              }
              removedAuthor=null;
              size--;
              return id;
          }
          temp = temp.next;
      }
      return -1;

  }
  public int findByName(String Name){
      if (size==0){
          return 0;
      }
      if(Name.length()==0){
          System.out.println("Please Provide the Proper Author Name");
          return 0;
      }
      Node temp=head;
    
      while(temp!=null){
          if(temp.object.name().toLowerCase().contains(Name.toLowerCase())){
              System.out.println(temp.object);
              return temp.object.author_id();
          }
          temp=temp.next;
      }
      return 0;
  }
  public int findById(int id){
      if(size==0){
          return -1 ;
      }
      if (id<=0){
          System.out.println("Please Provide the Proper id greater than 0");
          return -2;
      }
      Node temp=head;
      int check=0;
      while(temp!=null){
          if(temp.object.author_id()==id){
              System.out.println(temp.object);
              check=1;
          }
          temp=temp.next;
      }
      return check;
  }
  public String getAuthor(int id){
    Node temp=head;
    while(temp!=null){
        if(temp.object.author_id()==id){
            return temp.object.name();
        }
        temp=temp.next;
    }
    return "";
    }
  public Node getIndex(int index){
       if (size==0 || index>=size){
          return null;
      }
      Node temp=head;
      for(int i=0;i<index;i++){
          temp=temp.next;
      }
      return temp;
  }

  public int getSize() {
      return size;
  }
  
  public AuthorLL() {
      this.size = 0;
  }
  
  public AuthorLL.Author getTail() {
      return tail.object;
  }

  private  class Node implements Serializable{
    private static final long serialVersionUID = 908374920347823475L;
      private Author object;
      private Node next;

      public Node(Author object) {
          this.object = object;
      }
      
  }
  public void Setid(){
    int max_id=0;
    Node temp=head;
    while(temp!=null){
        if(temp.object.author_id()>max_id) max_id=temp.object.author_id();
        temp=temp.next;
    }
    AuthorLL.Author.setid(max_id+1);
  }
  public static class  Author implements Serializable{
    private static final long serialVersionUID = 283746592837459184L;
    private static int nextid=1;
    int author_id;
    String name;
    String Birth_date;
    String Nationallity;
    public Author(String name, String birth_date, String nationallity) {
        this.author_id = nextid++;
        this.name = name;
        Birth_date = birth_date;
        Nationallity = nationallity;
    }
    public static void setid(int nextId){
        nextid=nextId;
    }
    public int author_id() {
        return author_id;
    }
    public String name() {
        return name;
    }
    public String Birth_date() {
        return Birth_date;
    }
    public String Nationallity() {
        return Nationallity;
    }
    @Override
    public String toString() {
        return " author_id=" + author_id + ", name=" + name + ", Birth_date=" + Birth_date + ", Nationallity="
                + Nationallity;
    }
    
  }
}
