package LL;

import java.time.LocalDate;
import java.util.*;

public class MemberShipll{
  public static class Student{
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
    public int getLenofBooks(){
      return bB.size();
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
    @Override
    public String toString() {
      return " student_id=" + student_id + ", name=" + name + ", email=" + email + ", phone=" + phone
          + ", address=" + address + ", memberShipdate=" + memberShipdate + ", bB=" + bB ;
    }
    

  }
}
