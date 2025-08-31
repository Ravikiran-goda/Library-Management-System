package Data;
import java.sql.*;
import java.util.ArrayList;

import LL.*;

public class Db {
  // student db
    public void insertStudentsToDB(MemberShipll.Student s, Connection conn) throws SQLException {
      String sql = "INSERT INTO student (student_id,name, email, phone, address, membership_date, count) VALUES (?, ?, ?, ?, ?, ?, ?)";
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setInt(1, s.getStudent_id());
      stmt.setString(2, s.getName());
      stmt.setString(3, s.getEmail());
      stmt.setString(4, s.getPhone());
      stmt.setString(5, s.getAddress());
      stmt.setDate(6, java.sql.Date.valueOf(s.getMemberShipdate()));
      stmt.setInt(7, s.getcount());
      stmt.executeUpdate();
      stmt.close();
  }
  //Student books count bookdb
  public void updateBookCounttoDb(int count,int student_id,int num, Connection conn) throws SQLException{
    String sql="UPDATE STUDENT SET count=? WHERE student_id=?";
    PreparedStatement stmt= conn.prepareStatement(sql);
    stmt.setInt(1, count+num);
    stmt.setInt(2, student_id);
    stmt.executeUpdate();
  }
  // loandb
  public void SetReturnDate(int loan_id,String date,Connection conn)throws SQLException{
    String sql="Update loan set return_date=? where loan_id=?";
    PreparedStatement stmt=conn.prepareStatement(sql);
    stmt.setString(1, date);
    stmt.setInt(2, loan_id);
    stmt.executeUpdate();
    stmt.close();
  }
  // author db
  public void insertAuthorToDB(String name,String dob,String Nat,Connection conn) throws SQLException {
    String sql = "INSERT INTO Author ( name, birth_date, nationality) VALUES ( ?, ?, ?)";
    PreparedStatement stmt = conn.prepareStatement(sql);
    stmt.setString(1, name);
    stmt.setString(2, dob);
    stmt.setString(3, Nat);
    stmt.executeUpdate();
    stmt.close();
    
  }
  //authordb
  public void deleteAuthorToDB(int id, Connection conn) throws SQLException {
    String sql = "Delete FROM Author WHERE author_id=?";
    PreparedStatement stmt = conn.prepareStatement(sql);
    stmt.setInt(1, id);
    stmt.executeUpdate(); 
    stmt.close();
  }
  //authordb
  public int getAuthorIdIfexists(String Name,Connection conn)throws SQLException{
    String sql="Select author_id from author where name=?";
    PreparedStatement stmt=conn.prepareStatement(sql);
    stmt.setString(1, Name);
    ResultSet rs=stmt.executeQuery();
    if(rs.next()){
      int id=rs.getInt(1);
      stmt.close();
      return id;
    }
    return 0;
  }
  //authordb
  public int getAuthorBooksCount(int id, Connection conn) throws SQLException{
    String sql="Select Count(*) from book where author_id=?";
    PreparedStatement stmt=conn.prepareStatement(sql);
    stmt.setInt(1, id);
    ResultSet rs = stmt.executeQuery();
    if(rs.next()){
      int BooksCount=rs.getInt(1);
      stmt.close();
      return BooksCount;
    }
    return 0;
  }
  //categorydb
  public void insertCategoryToDB(String name, Connection conn) throws SQLException {
    String sql = "INSERT INTO Category (name) VALUES (?)";
    PreparedStatement stmt = conn.prepareStatement(sql);
    stmt.setString(1, name);
    stmt.executeUpdate();
    stmt.close();
  }
  //categorydb
  public void deleteCategoryToDB(int id, Connection conn) throws SQLException {
    String sql = "DELETE FROM category WHERE id=?";
    PreparedStatement stmt = conn.prepareStatement(sql);
    stmt.setInt(1, id);
    stmt.executeUpdate();
    stmt.close();
  }
  //bookdb
  public void insertBookToDB(int categoryid,String Title,String year,int author_id,int avc, Connection conn) throws SQLException {
    String sql = "INSERT INTO Book  ( category_id, title, publication_year, author_id, avl_copies) VALUES (?, ?,?, ?,?)";
    PreparedStatement stmt = conn.prepareStatement(sql);
    stmt.setInt(1, categoryid);
    stmt.setString(2, Title);
    stmt.setString(3, year);
    stmt.setInt(4, author_id);
    stmt.setInt(5,avc);
    stmt.executeUpdate();
    stmt.close();
  }
  //bookdb
  public void deleteBookfromDb(int book_id,Connection conn) throws SQLException{
    String sql="DELETE FROM Book where book_id=?";
    PreparedStatement stmt= conn.prepareStatement(sql);
    stmt.setInt(1, book_id);
    stmt.executeUpdate();
    stmt.close();
  }
  //loandb
  public void insertLoanToDB(int student_id,int book_id,String date, Connection conn) throws SQLException {
    String sql = "INSERT INTO Loan  (student_id,book_id,loan_date) VALUES (?,?,?)";
    PreparedStatement stmt = conn.prepareStatement(sql);
    stmt.setInt(1, student_id);
    stmt.setInt(2, book_id);
    stmt.setString(3, date);
    stmt.executeUpdate();
    stmt.close();
  }

  //bookdb
  public int getBookId(String Name, Connection conn) throws SQLException{
    String sql="SELECT book_id from book where title=?";
    PreparedStatement stmt=conn.prepareStatement(sql);
    stmt.setString(1, Name);
    ResultSet rs= stmt.executeQuery();
    if(rs.next()){
      int id= rs.getInt(1);
      stmt.close();
      return id;
    }
    return 0;
  }
  //bookdb
  public void updateBookCopies(int book_id, int availCopies, Connection conn)throws SQLException{
    String sql ="UPDATE BOOK SET avl_copies=? WHERE book_id=?";
    PreparedStatement stmt = conn.prepareStatement(sql);
    stmt.setInt(1,availCopies);
    stmt.setInt(2,book_id);
    stmt.executeUpdate();
    stmt.close();
  }
  //studentn db
  public boolean isStudentHasMember(int id,Connection conn) throws SQLException{
    String sql="Select COUNT(DISTINCT student_id) From student where student_id=?";
    PreparedStatement stmt=conn.prepareStatement(sql);
    stmt.setInt(1, id);
    ResultSet rs=stmt.executeQuery();
    while(rs.next()){
      int count = rs.getInt(1);
      stmt.close();
      return count>=1;
    }
    return false;
  }

  //categorydb
  public ArrayList<categoryLl.Category> getCategory(Connection conn) throws SQLException{
    ArrayList<categoryLl.Category> categoryList=new ArrayList<>();
    String sql="SELECT * FROM category";
    PreparedStatement stmt=conn.prepareStatement(sql);
    ResultSet rs = stmt.executeQuery();
    while (rs.next()) {
      int id = rs.getInt("id");
      String name = rs.getString("name");
      categoryList.add(new categoryLl.Category(id, name));
  }
  rs.close();
  stmt.close();
  return categoryList;
  }
  //authordb
  public ArrayList<AuthorLL.Author> getAuthorsList(Connection conn)throws SQLException{
    ArrayList<AuthorLL.Author> authorlist=new ArrayList<>();
    String sql="SELECT * FROM author";
    PreparedStatement stmt=conn.prepareStatement(sql);
    ResultSet rs = stmt.executeQuery();
    while (rs.next()) {
      int id = rs.getInt(1);
      String name = rs.getString(2);
      String dob=rs.getString(3);
      String nat=rs.getString(4);
      authorlist.add(new AuthorLL.Author(id, name, dob,nat));
    }
    rs.close();
    stmt.close();
    return authorlist;
  }
  //author db
  public boolean IsAthorContainsorNot(String authorname ,Connection conn) throws SQLException{
    String sql="SELECT count(*) from author where name=?";
    PreparedStatement stm=conn.prepareStatement(sql);
    stm.setString(1, authorname);
    ResultSet rs= stm.executeQuery();
    if(rs.next()){
      int count=rs.getInt(1);
      stm.close();
      return count>=1;
    }
    return false;
  }
  // count in limit or not count<5 false or else true student db
  public int getBookCountinLimit(int sid,Connection conn) throws SQLException{
    String sql="Select count from student where student_id=?";
    PreparedStatement stmt=conn.prepareStatement(sql);
    stmt.setInt(1, sid);
    ResultSet rs=stmt.executeQuery();
    if(rs.next()){
      int count = rs.getInt(1);
      stmt.close();
      return count;
    }
    return 0;
  
  }
  // get category id category db
  public int getcategoryId(String category, Connection conn) throws SQLException{
    String sql="Select id from category where name=?";
    PreparedStatement stmt=conn.prepareStatement(sql);
    stmt.setString(1, category);
    ResultSet rs=stmt.executeQuery();
    if(rs.next()){
      int id = rs.getInt(1);
      stmt.close();
      return id;
    }
    return 0;
  }
  // get no of books in category categorydb
  public int getBooksCountByCategory(int id, Connection conn) throws SQLException{
    String sql="Select Count(*) from BOOK where category_id=?";
    PreparedStatement stmt=conn.prepareStatement(sql);
    stmt.setInt(1, id);
    ResultSet rs=stmt.executeQuery();
    if(rs.next()){
      int count = rs.getInt(1);
      stmt.close();
      return count;
    }
    return 0;
  }
  public ArrayList<BookLL.Book> getBookandAUthor(int id,Connection conn) throws SQLException{
    ArrayList<BookLL.Book> bookList=new ArrayList<>();
    String sql="SELECT b.book_id,b.title,b.publication_year,b.avl_copies,a.name FROM book b JOIN author a ON b.author_id = a.author_id WHERE b.category_id = ?";
    PreparedStatement stmt=conn.prepareStatement(sql);
    stmt.setInt(1, id);
    ResultSet rs=stmt.executeQuery();
    int book_id;
    String Title;
    String year;
    String name;
    int ac;
    while(rs.next()){
      book_id=rs.getInt(1);
      Title=rs.getString(2);
      year=rs.getString(3);
      ac=rs.getInt(4);
      name=rs.getString(5);
      bookList.add(new BookLL.Book(book_id, Title, year, ac, name));
    } 
    stmt.close();
    return bookList;
  }
  public boolean isBookInCategory(int bid,int cid,Connection conn)throws SQLException{
    String sql="Select count(*) from book where book_id=? and category_id=?";
    PreparedStatement stmt=conn.prepareStatement(sql);
    stmt.setInt(1, bid);
    stmt.setInt(2, cid);
    ResultSet rs= stmt.executeQuery();
    if(rs.next()){
      int count=rs.getInt(1);
      stmt.close();
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
      stmt.close();
      return count;
    }
    return 0;
  }
  // display loan
  public ArrayList<LoanLL.Loan> getActiveLoans(int student_id,Connection conn) throws SQLException{
    ArrayList<LoanLL.Loan> loanList=new ArrayList<>();
    String sql="Select loan_id, book_id, loan_date from loan where student_id=? and return_date is Null";
    PreparedStatement stmt=conn.prepareStatement(sql);
    stmt.setInt(1, student_id);
    ResultSet rs=stmt.executeQuery();
    while(rs.next()){
      int loan_id=rs.getInt(1);
      int book_id=rs.getInt(2);
      String loan_date=rs.getString(3);
      loanList.add(new LoanLL.Loan(loan_id,student_id, loan_date, book_id));
    }
    stmt.close();
    return loanList;
  }
  public boolean isbookBorrowedBy(int student_id,int book_id,Connection conn) throws SQLException{
    String sql="Select count(*) from loan where student_id=? and book_id=?";
    PreparedStatement stmt=conn.prepareStatement(sql);
    stmt.setInt(1, student_id);
    stmt.setInt(2, book_id);
    ResultSet rs=stmt.executeQuery();
    if(rs.next()){
      int check=rs.getInt(1);
      stmt.close();
      return check>=1;
    }
    return false;
  }
 public boolean CategoryContainBooksOrnot(int category_id,Connection conn) throws SQLException{
  String sql="Select Count(*) from book where category_id=?";
  PreparedStatement stmt=conn.prepareStatement(sql);
  stmt.setInt(1, category_id);
  ResultSet rs=stmt.executeQuery();
  if(rs.next()){
    int count=rs.getInt(1);
    stmt.close();
    return count>=1;
  }
  return false;
 }
 // Book exists or not
 public boolean BookExitedOrNot(String Title, Connection conn) throws SQLException{
  String sql="Select count(*) from book where title=?";
  PreparedStatement stmt=conn.prepareStatement(sql);
  stmt.setString(1, Title);
  ResultSet rs=stmt.executeQuery();
  if(rs.next()){
    int count=rs.getInt(1);
    stmt.close();
    return count>=1;
  }
  else{
    return false;
  }
 }
 //is in loan or not 
 public boolean BookinLoanorNot(int book_id,Connection conn) throws SQLException{
  String sql="Select COUNT(*) from loan where book_id=? and return_date is NOT NULL";
  PreparedStatement stmt=conn.prepareStatement(sql);
  stmt.setInt(1, book_id);
  ResultSet rs=stmt.executeQuery();
  if(rs.next()){
    int count=rs.getInt(1);
    stmt.close();
    return count==0;
  }
  else return false;
 }
}
