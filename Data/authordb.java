package Data;

import java.sql.*;
import java.util.ArrayList;

import LL.AuthorLL;

public class authordb {
  public void insertAuthorToDB(String name,String dob,String Nat,Connection conn) throws SQLException {
    String sql = "INSERT INTO Author ( name, birth_date, nationality) VALUES ( ?, ?, ?)";
    PreparedStatement stmt = conn.prepareStatement(sql);
    stmt.setString(1, name);
    stmt.setString(2, dob);
    stmt.setString(3, Nat);
    stmt.executeUpdate();
    stmt.close();
    
  }
  public void deleteAuthorToDB(int id, Connection conn) throws SQLException {
    String sql = "Delete FROM Author WHERE author_id=?";
    PreparedStatement stmt = conn.prepareStatement(sql);
    stmt.setInt(1, id);
    stmt.executeUpdate(); 
    stmt.close();
  }
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

}
