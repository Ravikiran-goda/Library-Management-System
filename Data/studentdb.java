package Data;

import java.sql.*;

import LL.MemberShipll;

public class studentdb {
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
  public void updateBookCounttoDb(int count,int student_id,int num, Connection conn) throws SQLException{
    String sql="UPDATE STUDENT SET count=? WHERE student_id=?";
    PreparedStatement stmt= conn.prepareStatement(sql);
    stmt.setInt(1, count+num);
    stmt.setInt(2, student_id);
    stmt.executeUpdate();
  }
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
}
