import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

public class MemberShipll implements Serializable{
  private static final long serialVersionUID = 194827394928374839L;
  private Node head;
  private Node tail;
  private int size;

//   Validates an categoriess object and prints errors to console
 
  public void insertLast(Student  object){
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
          System.out.print(temp.object+"  ->  ");
          temp=temp.next;
      }
      System.out.println("END");
  }

  public int deleteById(int id){
      if (size==0){
          return -1;
      }
      if (head.object.getStudent_id()==id){
        head=head.next;
        size--;
        return 1;
      }
      Node temp=head;
      temp=temp.next;
      while (temp != null) {
          if (temp.object.getStudent_id()==id){
              temp = temp.next;
              size--;
              if (temp.next == null) {
                  tail = temp; 
              }
              return 1;
          }
          temp = temp.next;
      }
      return -1;

  }
  public boolean findById(int id){
      if(size==0){
          return false;
      }
      Node temp=head;
      boolean check=false;
      while(temp!=null){
          if(temp.object.getStudent_id()==id){
              System.out.println(temp.object);
              check=true;
          }
          temp=temp.next;
      }
      return check;
  }
  public Student getstudent(int id){
     if(size==0){
          return null;
      }
      Node temp=head;
      while(temp!=null){
          if(temp.object.getStudent_id()==id){
              return temp.object;
          }
          temp=temp.next;
      }
      return null;
  }

  public int getSize() {
      return size;
  }
  
  public MemberShipll(){
      this.size = 0;
  }
  
  public MemberShipll.Student getTail(){
      return tail.object;
  }

  private  class Node implements Serializable{
    private static final long serialVersionUID = 493847582374927374L;
      private Student object;
      private Node next;

      public Node(Student object){
          this.object = object;
      }

      
  }
  // // record Book(int Book_id,int Category_id, String Title, String publication_year,int author_id,int avl_copies){}
  public static class Student implements Serializable{
    private static final long serialVersionUID = 739475028473750928L;
    private int student_id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private LocalDate memberShipdate;
    private List<Integer> bB=new ArrayList<>();
    private int count=0;
    
    public Student(int student_id, String name, String email, String phone, String address, LocalDate memberShipdate) {
      this.student_id = student_id;
      this.name = name;
      this.email = email;
      this.phone = phone;
      this.address = address;
      this.memberShipdate = memberShipdate; 
      count=0;
    }
    public int getStudent_id() {
      return student_id;
    }
    public String getName() {
      return name;
    }
    public String getEmail() {
      return email;
    }
    public String getPhone() {
      return phone;
    }
    public String getAddress() {
      return address;
    }
    public LocalDate getMemberShipdate() {
      return memberShipdate;
    }
    
    public List<Integer> getbB() {
      return bB;
    }
    public void addbookid(int book_id) {
        bB.add(book_id);
        count++;
    }
    public int isPresentorNot(int book_id){
      return bB.indexOf(book_id);
    }
    public void removebook(int book_id){
      bB.remove(bB.indexOf(book_id));
      count--;
    }
    public int getcount(){
      return count;
    }
    public void setcount(){
      count+=1;
    }
    @Override
    public String toString() {
      return " student_id=" + student_id + ", name=" + name + ", email=" + email + ", phone=" + phone
          + ", address=" + address + ", memberShipdate=" + memberShipdate + ", bB=" + bB ;
    }
    

  }
}
