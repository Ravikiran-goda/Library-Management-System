package Temporary;
import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Locale.Category;

import LL.categoryLl;
public class main_method3{
  public static categoryLl readdetailsfromfile(File file, categoryLl obj) throws FileNotFoundException{
    Scanner sc=new Scanner(file);
    int cat_id;
    String name;
    obj.Setid();
    while(sc.hasNextLine()){
      String s=sc.nextLine().trim();
      obj.insertLast(new categoryLl.Category(s));
    }
    return obj;
  }
  public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
    Scanner sc=new Scanner(System.in);
    categoryLl obj=new categoryLl();
    int choice ;
    File categorydata=new File("D:\\library_mgmt\\library_mgmt\\library_mgmt\\category.dat");
    File catdata= new File("D:\\library_mgmt\\library_mgmt\\library_mgmt\\category.dat");
    File file = new File("D:\\library_mgmt\\library_mgmt\\library_mgmt\\category.txt");
    readdetailsfromfile(file, obj);
  //   try(FileInputStream fin= new FileInputStream(categorydata)){
  //     ObjectInputStream ois= new ObjectInputStream(fin);
  //     obj=(categoryLl)ois.readObject();
      
  //     ois.close();
  // }
  // catch (IOException | ClassNotFoundException e) {
  //     e.printStackTrace();
  // }
    do{
      choice=sc.nextInt();
      sc.nextLine();
      String name;
      int cat_id;
      switch(choice){
        case 1:
          name=sc.nextLine();
          obj.insertLast(new categoryLl.Category(name));
          break;
        case 2:
          obj.display();
          break;
        case 3:
          obj.findById(sc.nextInt());
          sc.nextLine();
          break;
        case 4:
          obj.findByName(sc.nextLine());
          break;
        case 5:
          cat_id=sc.nextInt();
          sc.nextLine();
          System.out.println(obj.deleteById(cat_id));
          break;
        default:
          if(!catdata.exists()){
            catdata.createNewFile();
          }
          ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(catdata));
          oos.writeObject(obj);
          oos.flush();
          System.out.println("exit");
          break;
      }
    }while(choice<6);
  }
}
