package Temporary;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

import LL.*;

public class main_method {
  public static AuthorLL readFileintoLlist(File file, AuthorLL list) throws FileNotFoundException{
    Scanner scanner = new Scanner(file);

    while (scanner.hasNextLine()) {
        try {
            
            String name = scanner.nextLine().trim();
            String birthDate = scanner.nextLine().trim();
            String nationality = scanner.nextLine().trim();
            list.Setid();
            AuthorLL.Author author = new AuthorLL.Author( name, birthDate, nationality);
            list.insertLast(author); // Add author to the linked list
        } catch (Exception e) {
            System.out.println("Error reading one author block: " + e.getMessage());
            break; // Stop reading if unexpected format
        }
    }
    return list;

 
}
  public static void main(String[] args) throws IOException {
    File File= new File("D:\\library_mgmt\\library_mgmt\\library_mgmt\\authorFile.txt");
    File authdat=new File ("D:\\library_mgmt\\library_mgmt\\library_mgmt\\auth.dat");
    if(!File.exists()){
      File.createNewFile();
    }
    AuthorLL list=new AuthorLL();
    list=readFileintoLlist(File, list);
    Scanner sc=new Scanner(System.in);
    int choice ;
    do{
      choice=sc.nextInt();
      sc.nextLine();
      int id;
      String Name;
      String Birth_date;
      String nationality;
      switch(choice){
        case 1:
          Name=sc.nextLine();
          Birth_date=sc.nextLine();
          nationality=sc.next();
          list.Setid();
          list.insertLast(new AuthorLL.Author(Name,Birth_date,nationality));
          break;
        case 2:
          list.display();
          break;
        case 3:
          list.findById(sc.nextInt());
          sc.nextLine();
          break;
        case 4:
          Name = sc.nextLine();
          list.findByName(Name);
          break;
        case 5:
          id = sc.nextInt();
          sc.nextLine();
          list.deleteById(id);
          break;
        default:
          if (!authdat.exists()){
            authdat.createNewFile();
           }
          ObjectOutputStream oos= new ObjectOutputStream(new FileOutputStream(authdat));
          oos.writeObject(list);
          oos.close();
          System.out.println("exit");
          break;
      }
    }while(choice<6);
  }
}
