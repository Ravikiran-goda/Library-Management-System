import java.io.Serializable;

public class categoryLl implements Serializable{
  private static final long serialVersionUID = 482982374923839495L;
  private Node head;
  private Node tail;
  private int size;



  
  public void insertLast(Category object){
      if(head==null && tail==null){
        head=new Node(object);
        size++;
        tail=head;
        return ;
      }
      Node temp=new Node(object);
      tail.next=temp;
      tail=temp;
      size++;
  }

  public void display(){
      if(head==null){
        System.out.println("doesn't have categories");
        return ;
      }
      System.out.println("Category Id  Category Name");
      Node temp=head;
      while(temp!=null){
          System.out.println(temp.object);
          temp=temp.next;
      }
      
  }

  public int deleteById(int value){
      if (size==0){
          return -1;
      }
      Node temp=head;
      if (head.object.id()==value){
          head=head.next;
          return value;
      }
      while (temp.next != null) {
          if (temp.next.object.id == value) {
              Category removedEmployee = temp.next.object;
              temp.next = temp.next.next;
              
              if (temp.next == null) {
                  tail = temp; 
              }
              removedEmployee=null;
              return value;
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
          System.out.println("Please Provide the valid category name ");
          return -1;
      }
      Node temp=head;
      while(temp!=null){
          if(temp.object.Name().toLowerCase().contains(Name.toLowerCase())){
              return temp.object.id();
          }
          temp=temp.next;
      }
      return 0;
  }
  public void deletebyName(String Name){
    Node temp=head;
      while(temp!=null){
          if(temp.object.Name().toLowerCase().contains(Name.toLowerCase())){
            System.out.println(temp.object.Name());
              temp=temp.next;
          }
          temp=temp.next;
      }
  }
  public boolean findById(int id){
      if(size==0){
          return false ;
      }
      Node temp=head;
      boolean check=false;
      while(temp!=null){
          if(temp.object.id()==id){
              check=true;
          }
          temp=temp.next;
      }
      return check;
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
  
  public categoryLl() {
      this.size = 0;
  }
  
  public categoryLl.Category getTail() {
      return tail.object;
  }

  private  class Node implements Serializable{
         private static final long serialVersionUID = 129384756283745839L;
      private Category object;
      private Node next;

      public Node(Category object) {
          this.object = object;
      }

      public Node(Category object, categoryLl.Node next) {
          this.object = object;
          this.next = next;
      }
      
  }
  public void Setid(){
    Category.setid(size+1);
  }
  public static class Category implements Serializable{
    private static int nextid;
    private int id;
    private String Name;
    public Category( String name) {
        this.id = nextid++;
        Name = name;
    }
    public static void setid(int nextId){
        Category.nextid=nextId;
    }
    public int id() {
        return id;
    }
    public String Name() {
        return Name;
    }
    @Override
    public String toString() {
        return "    " + id + "             " + Name ;
    }
    
  }
}