package LL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class LoanLL  {
    public void displayLoans(ArrayList<Loan> loanList){
        System.out.println("Active Loans");
        for(Loan l: loanList){
            System.out.println(l);
        }
    }
    public static class Loan{
        
        private int loanId;
        private int student_id;
        private String LoanDate;
        private String returnDate;
        private int BookId;
        private String title;
        
        public Loan(int loan_id,int student_id, String loanDate, int bookId) {
            loanId=loan_id;
            this.student_id = student_id;
            LoanDate = loanDate;
            BookId = bookId;
        }
        
        public Loan(int loanId, int student_id, String loanDate, int bookId, String title) {
            this.loanId = loanId;
            this.student_id = student_id;
            LoanDate = loanDate;
            BookId = bookId;
            this.title = title;
        }

        public void setReturnDate() {
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd:mm:yyyy   HH:MM:SS");
            String DateTime = currentDateTime.format(formatter);
            this.returnDate =DateTime;
        }
        
       
        public int getLoanId() {
            return loanId;
        }
        public int getBookId() {
            return BookId;
        }
        
        public String getLoanDate() {
            return LoanDate;
        }
        public int getStudent_id() {
            return student_id;
        }
        
        public String getReturnDate() {
            return returnDate;
        }
        
        @Override
        public String toString() {
            return " loanId=" + loanId + ", student_id=" + student_id + ", LoanDate=" + LoanDate + ", returnDate="
                    + returnDate + ", BookId=" + BookId+ ", Title=" + title;
        }
        
    }
    }

