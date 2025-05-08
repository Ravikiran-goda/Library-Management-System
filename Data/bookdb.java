package Data;

import java.sql.*;
import java.util.ArrayList;

import LL.BookLL;

public class bookdb {
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
  public void deleteBookfromDb(int book_id,Connection conn) throws SQLException{
    String sql="DELETE FROM Book where book_id=?";
    PreparedStatement stmt= conn.prepareStatement(sql);
    stmt.setInt(1, book_id);
    stmt.executeUpdate();
    stmt.close();
  }
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
  public void updateBookCopies(int book_id, int availCopies, Connection conn)throws SQLException{
    String sql ="UPDATE BOOK SET avl_copies=? WHERE book_id=?";
    PreparedStatement stmt = conn.prepareStatement(sql);
    stmt.setInt(1,availCopies);
    stmt.setInt(2,book_id);
    stmt.executeUpdate();
    stmt.close();
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
}
