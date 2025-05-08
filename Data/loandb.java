package Data;

import java.sql.*;
import java.util.ArrayList;

import LL.LoanLL;

public class loandb {
  public void insertLoanToDB(int student_id,int book_id,String date, Connection conn) throws SQLException {
    String sql = "INSERT INTO Loan  (student_id,book_id,loan_date) VALUES (?,?,?)";
    PreparedStatement stmt = conn.prepareStatement(sql);
    stmt.setInt(1, student_id);
    stmt.setInt(2, book_id);
    stmt.setString(3, date);
    stmt.executeUpdate();
    stmt.close();
  }
   public void SetReturnDate(int loan_id,String date,Connection conn)throws SQLException{
    String sql="Update loan set return_date=? where loan_id=?";
    PreparedStatement stmt=conn.prepareStatement(sql);
    stmt.setString(1, date);
    stmt.setInt(2, loan_id);
    stmt.executeUpdate();
    stmt.close();
  }
 public ArrayList<LoanLL.Loan> getActiveLoans(int student_id,Connection conn) throws SQLException{
    ArrayList<LoanLL.Loan> loanList=new ArrayList<>();
    String sql="Select l.loan_id, l.book_id, l.loan_date,b.title from loan  l JOIN book b ON b.book_id=l.book_id where student_id=? and return_date is Null";
    PreparedStatement stmt=conn.prepareStatement(sql);
    stmt.setInt(1, student_id);
    ResultSet rs=stmt.executeQuery();
    int loan_id,book_id;
    String loan_date;
    String title;
    while(rs.next()){
      loan_id=rs.getInt(1);
      book_id=rs.getInt(2);
      loan_date=rs.getString(3);
      title=rs.getString(4);
      loanList.add(new LoanLL.Loan(loan_id,student_id, loan_date, book_id,title));
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
