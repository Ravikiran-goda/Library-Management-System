package Data;
import java.sql.*;
import java.util.ArrayList;

import LL.categoryLl;

public class categorydb {
   public void insertCategoryToDB(String name, Connection conn) throws SQLException {
    String sql = "INSERT INTO Category (name) VALUES (?)";
    PreparedStatement stmt = conn.prepareStatement(sql);
    stmt.setString(1, name);
    stmt.executeUpdate();
    stmt.close();
  }
  public void deleteCategoryToDB(int id, Connection conn) throws SQLException {
    String sql = "DELETE FROM category WHERE id=?";
    PreparedStatement stmt = conn.prepareStatement(sql);
    stmt.setInt(1, id);
    stmt.executeUpdate();
    stmt.close();
  }
  public ArrayList<categoryLl.Category> getCategory(Connection conn) throws SQLException{
    ArrayList<categoryLl.Category> categoryList=new ArrayList<>();
    String sql="SELECT * FROM category";
    PreparedStatement stmt=conn.prepareStatement(sql);
    ResultSet rs = stmt.executeQuery();
    int id;
    String name;
    while (rs.next()) {
      id = rs.getInt("id");
      name = rs.getString("name");
      categoryList.add(new categoryLl.Category(id, name));
  }
  rs.close();
  stmt.close();
  return categoryList;
  }
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
}
