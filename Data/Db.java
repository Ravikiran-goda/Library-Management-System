package Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import LL.*;

public class Db {
    public void insertStudentsToDB(MemberShipll.Student s, Connection conn) throws SQLException {
      String sql = "INSERT INTO student (student_id, name, email, phone, address, membership_date, count) VALUES (?, ?, ?, ?, ?, ?, ?)";
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setInt(1, s.getStudent_id());
      stmt.setString(2, s.getName());
      stmt.setString(3, s.getEmail());
      stmt.setString(4, s.getPhone());
      stmt.setString(5, s.getAddress());
      stmt.setDate(6, java.sql.Date.valueOf(s.getMemberShipdate()));
      stmt.setInt(7, s.getcount());
      stmt.executeUpdate();
  }
  //Student books count
  public void updateBookCounttoDb(int count,int student_id,int num, Connection conn) throws SQLException{
    String sql="UPDATE STUDENT SET count=? WHERE student_id=?";
    PreparedStatement stmt= conn.prepareStatement(sql);
    stmt.setInt(1, count+num);
    stmt.setInt(2, student_id);
    stmt.executeUpdate();
  }
  public void SetReturnDate(int loan_id,String date,Connection conn)throws SQLException{
    String sql="Update loan set return_date=? where loan_id=?";
    PreparedStatement stmt=conn.prepareStatement(sql);
    stmt.setString(1, date);
    stmt.setInt(2, loan_id);
    stmt.executeUpdate();
  }

  public void insertAuthorToDB(AuthorLL.Author a, Connection conn) throws SQLException {
    String sql = "INSERT INTO Author (author_id, name, birth_date, nationality) VALUES (?, ?, ?, ?)";
    PreparedStatement stmt = conn.prepareStatement(sql);
    stmt.setInt(1, a.author_id());
    stmt.setString(2, a.name());
    stmt.setString(3, a.Birth_date());
    stmt.setString(4, a.Nationallity());
    stmt.executeUpdate();
    
  }
  public void deleteAuthorToDB(int id, Connection conn) throws SQLException {
    String sql = "Delete FROM Author WHERE author_id=?";
    PreparedStatement stmt = conn.prepareStatement(sql);
    stmt.setInt(1, id);
    stmt.executeUpdate(); 
  }

  public void insertCategoryToDB(categoryLl.Category c, Connection conn) throws SQLException {
    String sql = "INSERT INTO Category (id, name) VALUES (?, ?)";
    PreparedStatement stmt = conn.prepareStatement(sql);
    stmt.setInt(1, c.id());
    stmt.setString(2, c.Name());
    stmt.executeUpdate();
  }
  public void deleteCategoryToDB(int id, Connection conn) throws SQLException {
    String sql = "DELETE FROM category WHERE id=?";
    PreparedStatement stmt = conn.prepareStatement(sql);
    stmt.setInt(1, id);
    stmt.executeUpdate();
  }
  
  public void insertBookToDB(BookLL.Book b, Connection conn) throws SQLException {
    String sql = "INSERT INTO Book (book_id, category_id,title,publication_year,author_id,avl_copies) VALUES (?, ?,?, ?,?, ?)";
    PreparedStatement stmt = conn.prepareStatement(sql);
    stmt.setInt(1, b.getBook_id());
    stmt.setInt(2, b.getCategory_id());
    stmt.setString(3, b.getTitle());
    stmt.setString(4, b.getPublication_year());
    stmt.setInt(5, b.getAuthor_id());
    stmt.setInt(6,b.getAvl_copies());
    stmt.executeUpdate();
    
  }
  public void deleteBookfromDb(int book_id,Connection conn) throws SQLException{
    String sql="DELETE FROM Book where book_id=?";
    PreparedStatement stmt= conn.prepareStatement(sql);
    stmt.setInt(1, book_id);
    stmt.executeUpdate();
  }
  public void insertLoanToDB(LoanLL.Loan l , Connection conn) throws SQLException {
    String sql = "INSERT INTO Loan  (student_id,book_id,loan_date) VALUES (?,?,?)";
    PreparedStatement stmt = conn.prepareStatement(sql);
    stmt.setInt(1, l.getStudent_id());
    stmt.setInt(2, l.getBookId());
    stmt.setString(3, l.getLoanDate());
    stmt.executeUpdate();
  }
  public void returnBookDatetoDB(int loan_id,String date, Connection conn) throws SQLException{
    String sql="UPDATE LOAN SET return_date=? WHERE loan_id=?";
    PreparedStatement stmt= conn.prepareStatement(sql);
    stmt.setString(1, date);
    stmt.setInt(2,loan_id);
    stmt.executeUpdate();
  }
  public void updateBookCopies(int book_id, int availCopies, Connection conn)throws SQLException{
    String sql ="UPDATE BOOK SET avl_copies=? WHERE book_id=?";
    PreparedStatement stmt = conn.prepareStatement(sql);
    stmt.setInt(1,availCopies);
    stmt.setInt(2,book_id);
    stmt.executeUpdate();
  }

  
  public void isBookContainsornot(String Title,Connection conn)throws SQLException{
    String sql="Select book_id From Book where title=?";
    PreparedStatement stmt=conn.prepareStatement(sql);
    stmt.setString(1, Title);
    ResultSet rs =stmt.executeQuery();
    System.out.println(rs.next());
  }
  public boolean getStudentId(int id,Connection conn) throws SQLException{
    String sql="Select COUNT(DISTINCT student_id) From student where student_id=?";
    PreparedStatement stmt=conn.prepareStatement(sql);
    stmt.setInt(1, id);
    ResultSet rs=stmt.executeQuery();
    while(rs.next()){
      int count = rs.getInt(1);
      System.out.println(count);
      return count>=1;
    }
    return false;
  }
  
  public void DisplayCategory(Connection conn) throws SQLException{
    String sql="SELECT * FROM category";
    PreparedStatement stmt=conn.prepareStatement(sql);
    ResultSet rs = stmt.executeQuery();
    System.out.println("Category Id  Category Name");
    while (rs.next()) {
      int id = rs.getInt("id");
      String name = rs.getString("name");
      System.out.println("    " + id + "             : " + name);
  }
  rs.close();
  stmt.close();
  }
  // count in limit or not count<5 false or else true
  public int isBookCountinLimit(int sid,Connection conn) throws SQLException{
    String sql="Select count from student where student_id=?";
    PreparedStatement stmt=conn.prepareStatement(sql);
    stmt.setInt(1, sid);
    ResultSet rs=stmt.executeQuery();
    if(rs.next()){
      int count = rs.getInt(1);
      System.out.println("Count: " + count);
      return count;
    }
    return 0;
  
  }
  // get category id
  public int getcategoryId(String category, Connection conn) throws SQLException{
    System.out.println("In getcategory");
    String sql="Select id from category where name=?";
    PreparedStatement stmt=conn.prepareStatement(sql);
    stmt.setString(1, category);
    ResultSet rs=stmt.executeQuery();
    if(rs.next()){
      int id = rs.getInt(1);
      System.out.println(id);
      return id;
    }
    return 0;
  }
  // get no of books in category
  public int getBooksCountByCategory(int id, Connection conn) throws SQLException{
    System.out.println("In getcategory");
    String sql="Select Count(*) from BOOK where category_id=?";
    PreparedStatement stmt=conn.prepareStatement(sql);
    stmt.setInt(1, id);
    ResultSet rs=stmt.executeQuery();
    if(rs.next()){
      int count = rs.getInt(1);
      System.out.println(count);
      return count;
    }
    return 0;
  }
  public void displaybycatandAUthor(int id,Connection conn) throws SQLException{
    String sql="SELECT b.book_id,b.title,b.publication_year,b.avl_copies,a.name FROM book b JOIN author a ON b.author_id = a.author_id WHERE b.category_id = ?";
    PreparedStatement stmt=conn.prepareStatement(sql);
    stmt.setInt(1, id);
    ResultSet rs=stmt.executeQuery();

    while(rs.next()){
      int book_id=rs.getInt(1);
      String Title=rs.getString(2);
      String year=rs.getString(3);
      int ac=rs.getInt(4);
      String name=rs.getString(5);
      System.out.println("Book Id: "+book_id+"  "+"Title: "+Title+"  "+"publication year: "+year+"  "+" available Copies: "+ac+"   Author Name:"+name);
    } 
    return;
  }
  public boolean isBookInCategory(int bid,int cid,Connection conn)throws SQLException{
    String sql="Select count(*) from book where book_id=? and category_id=?";
    PreparedStatement stmt=conn.prepareStatement(sql);
    stmt.setInt(1, bid);
    stmt.setInt(2, cid);
    ResultSet rs= stmt.executeQuery();
    if(rs.next()){
      int count=rs.getInt(1);
      System.out.println(count);
      return count==1;
    }
    return false;
  }
  //getavailableCopies
  public int availCopies(int bid,Connection conn)throws SQLException{

    String sql="Select avl_copies from book where book_id=?";
    PreparedStatement stmt=conn.prepareStatement(sql);
    stmt.setInt(1, bid);
    ResultSet rs= stmt.executeQuery();
    if(rs.next()){
      int count=rs.getInt(1);
      System.out.println("available copies:  "+count);
      return count;
    }
    return 0;
  }
  // display loan
  public void displayLoans(int student_id,Connection conn) throws SQLException{
    String sql="Select loan_id, book_id, loan_date from loan where student_id=? and return_date is Null";
    PreparedStatement stmt=conn.prepareStatement(sql);
    stmt.setInt(1, student_id);
    ResultSet rs=stmt.executeQuery();
    System.out.println("Active loans");
    while(rs.next()){
      int loan_id=rs.getInt(1);
      int book_id=rs.getInt(2);
      String loan_date=rs.getString(3);
      System.out.println(loan_id+"     "+" "+student_id+"    "+book_id+"  "+"       "+loan_date);
    }
  }
  public boolean isbookBorrowedBy(int student_id,int book_id,Connection conn) throws SQLException{
    String sql="Select count(*) from loan where student_id=? and book_id=?";
    PreparedStatement stmt=conn.prepareStatement(sql);
    stmt.setInt(1, student_id);
    stmt.setInt(2, book_id);
    ResultSet rs=stmt.executeQuery();
    if(rs.next()){
      int check=rs.getInt(1);
      return check==1;
    }
    return false;
  }
 
}
