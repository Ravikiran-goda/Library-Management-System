package Temporary;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

import LL.BookLL;
import LL.categoryLl;
import LibraryFunctions.Library;
import LL.*;


public class main_method2 {
  public static BookLL readDataFromFile(File file,BookLL list) throws FileNotFoundException{
    Scanner sr=new Scanner(file);
    String line;
    String[] data;
    int Book_id;
    int category_id;
    String Title;
    String publication_year;
    int author_id;
    int aval_copies;
    while(sr.hasNextLine()){
      line=sr.nextLine();
      data = line.split(",");
      category_id=Integer.parseInt(data[1].trim());
      Title=data[3].trim();
      publication_year=data[4].trim();
      author_id=Integer.parseInt(data[2].trim());
      aval_copies=Integer.parseInt(data[5].trim());
      list.Setid();
      list.insertLast(new BookLL.Book( category_id, Title, publication_year, author_id, aval_copies));
      
    }
    return list;
  
  }
  public static void main(String[] args) throws IOException,FileNotFoundException {
    Scanner sc=new Scanner(System.in);
    BookLL list=new BookLL();
    AuthorLL alist=new AuthorLL();
    categoryLl clist=new categoryLl();
    int choice ;
    File authdata=new File("D:\\library_mgmt\\library_mgmt\\library_mgmt\\auth.dat");
    File categorydata=new File("D:\\library_mgmt\\library_mgmt\\library_mgmt\\category.dat");
    File Booksdat=new File
    ("D:\\library_mgmt\\library_mgmt\\library_mgmt\\Books.dat");
    File librarFile=new File("D:\\library_mgmt\\library_mgmt\\library_mgmt\\library.dat");
    File file= new File("D:\\library_mgmt\\library_mgmt\\library_mgmt\\Books.txt");
    

    // try(FileInputStream fin= new FileInputStream(librarFile)){
    //         ObjectInputStream ois= new ObjectInputStream(fin);
    //         clist=(categoryLl)ois.readObject();
    //         list=( BookLL)ois.readObject();
    //         alist=(AuthorLL)ois.readObject();
            
    //         ois.close();
    //     }
    //     catch (IOException | ClassNotFoundException e) {
    //         e.printStackTrace();
    //     }
    try(FileInputStream fin= new FileInputStream(Booksdat)){
      ObjectInputStream ois= new ObjectInputStream(fin);
      
      list=( BookLL)ois.readObject();
      
      ois.close();
  }
  catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
  }
  try(FileInputStream fin= new FileInputStream(categorydata)){
    ObjectInputStream ois= new ObjectInputStream(fin);
    clist=(categoryLl)ois.readObject();
    
    ois.close();
}
catch (IOException | ClassNotFoundException e) {
    e.printStackTrace();
}
try(FileInputStream fin= new FileInputStream(authdata)){
  ObjectInputStream ois= new ObjectInputStream(fin);
  alist=(AuthorLL)ois.readObject();
  
  ois.close();
}
catch (IOException | ClassNotFoundException e) {
  e.printStackTrace();
}
    // list=readDataFromFile(file, list);
    do{
      choice=sc.nextInt();
      sc.nextLine();
      
      int cat_id;
      String Title;
      String pub_year;
      int author_id;
      int aval_cop;
      switch(choice){
        case 1:
          cat_id=sc.nextInt();
          sc.nextLine();
          Title=sc.nextLine();
          pub_year=sc.nextLine();
          author_id=sc.nextInt();
          sc.nextLine();
          aval_cop=sc.nextInt();
          sc.nextLine();
          list.Setid();
          list.insertLast(new BookLL.Book(cat_id,Title,pub_year,author_id,aval_cop));
          break;
        case 2:
          list.display();
          System.out.println();
          alist.display();
          clist.display();
          break;
        case 3:
          list.findById(sc.nextInt());
          sc.nextLine();
          break;
        case 4:
          list.findByName(sc.nextLine());
          break;
        case 5:
          int id = sc.nextInt();
          sc.nextLine();
          // System.out.println(list.deleteById(id));
          break;
        default:
          if (!Booksdat.exists()){
            Booksdat.createNewFile();
           }
          ObjectOutputStream oos= new ObjectOutputStream(new FileOutputStream(librarFile));
          oos.writeObject(clist);
          oos.writeObject(list);
          oos.writeObject(alist);
          oos.close();
          System.out.println("exit");
          break;
      }
    }while(choice<6);
  }
}
