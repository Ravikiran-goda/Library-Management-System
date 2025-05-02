package Data;
import java.io.*;

import LL.*;

public class ReadWrite {
   /**
     * Writes multiple linked list objects to a file using serialization.
     * This includes category, book, author, membership, and loan linked lists.
     
     * @param file The file where the objects will be written
     * @param category_list The linked list of categories
     * @param obj2 The linked list of books
     * @param obj3 The linked list of authors
     * @param obj4 The linked list of memberships
     * @param obj5 The linked list of loans
     * @throws FileNotFoundException if the file cannot be found
     * @throws IOException if an I/O error occurs during writing
     */

    public void writeobjectsintofile(File file,categoryLl category_list,BookLL obj2,AuthorLL obj3,MemberShipll obj4,LoanLL obj5 ) throws FileNotFoundException, IOException{
      if(!file.exists()){
        file.createNewFile();
      }
      ObjectOutputStream oos= new ObjectOutputStream(new FileOutputStream(file));
      oos.writeObject(category_list);
      oos.writeObject(obj2);
      oos.writeObject(obj3);
      oos.writeObject(obj4);
      oos.writeObject(obj5);
      oos.close();
    }
    public void readObjectsFromFile(File file, categoryLl category_list, BookLL books_list, AuthorLL author_list, MemberShipll student_list, LoanLL loan_list){
      try(FileInputStream fin= new FileInputStream(file)){
        ObjectInputStream ois= new ObjectInputStream(fin);
        category_list=(categoryLl)ois.readObject();
        books_list=( BookLL)ois.readObject();
        author_list=(AuthorLL)ois.readObject();
        student_list=(MemberShipll)ois.readObject();
        loan_list=(LoanLL)ois.readObject();
        ois.close();
    }
    catch (IOException | ClassNotFoundException e) {
        e.printStackTrace();
    }
    }
}
