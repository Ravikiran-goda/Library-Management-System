package LL;

import java.util.ArrayList;

public class categoryLl {
 
  public void display(ArrayList<Category> clist){
      for(Category cat: clist){
        System.out.println(cat);
      }
  }

  public static class Category {
    
    private int id;
    private String Name;
    public Category( int id,String name) {
        this.id = id;
        Name = name;
    }
    public int id() {
        return id;
    }
    public String Name() {
        return Name;
    }
    @Override
    public String toString() {
        return "    " + id + "             " + Name ;
    }
    
  }
}