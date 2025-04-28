import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoanLL implements Serializable{
    private static final long serialVersionUID = 394847920384759203L;
    private Node head;
    private Node tail;
    private int size;
    
    public void insertLast(Loan object){
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
            System.out.println(temp.object );
            temp=temp.next;
        }
        System.out.println("END");
    }

    public int deleteById(int id){
        if (size==0){
            return -1;
        }
        if (head.object.getLoanId()==id){
            head=head.next;
            size--;
            return 1;
        }
        Node temp=head;
        temp=temp.next;
        while (temp != null) {
            if (temp.object.getLoanId()==id){
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
    public void SetReturnDate(int BookId, int loan_id){
        Node tem=head;
        while(tem!=null){
            if(tem.object.getBookId()==BookId && tem.object.getLoanId()==loan_id){
                tem.object.setReturnDate();
            }
            tem=tem.next;
        }
    }
    public int findById(int id){
        if(size==0){
            return -1 ;
        }
        Node temp=head;
        int check=0;
        while(temp!=null){
            if(temp.object.getLoanId()==id ){
                System.out.println(temp.object);
                check=1;
            }
            temp=temp.next;
        }
        return check;
    }

    public int getSize() {
        return size;
    }
    
    public LoanLL(){
        this.size = 0;
    }
    
    private  class Node implements Serializable{
        private static final long serialVersionUID = 839495827384739489L;
        private Loan object;
        private Node next;

        public Node(Loan object){
            this.object = object;
        }

        
    }
    public void SetNextId(){
        Loan.setNextId(size+1);
    }
    //   record Loan(int loanId,int student_id, String LoanDate, String returnDate, int BookId){};
    public static class Loan implements Serializable{
        private static final long serialVersionUID = 938475029384759238L;
        private static int nextId=1;
        private int loanId;
        private int student_id;
        private String LoanDate;
        private String returnDate;
        private int BookId;
        
        public Loan(int student_id, String loanDate, int bookId) {
            this.loanId=nextId++;
            this.student_id = student_id;
            LoanDate = loanDate;
            BookId = bookId;
        }
        public void setReturnDate() {
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd   HH:mm:ss");
            String DateTime = currentDateTime.format(formatter);
            this.returnDate =DateTime;
        }
        
        public static void setNextId(int nextId) {
            Loan.nextId = nextId;
        }
        public int getLoanId() {
            return loanId;
        }
        public int getBookId() {
            return BookId;
        }

        @Override
        public String toString() {
            return " loanId=" + loanId + ", student_id=" + student_id + ", LoanDate=" + LoanDate + ", returnDate="
                    + returnDate + ", BookId=" + BookId ;
        }
        
    }
    }

