package LL;
import java.io.Serializable;
import java.util.ArrayList;

public class AuthorLL{
  public void display(ArrayList<Author> authorlist){
      for(Author a: authorlist){
        System.out.println(a);
      }
  }

  public static class  Author {
    int author_id;
    String name;
    String Birth_date;
    String Nationallity;
    public Author(int id,String name, String birth_date, String nationallity) {
        this.author_id=id;
        this.name = name;
        Birth_date = birth_date;
        Nationallity = nationallity;
    }
 
    public int author_id() {
        return author_id;
    }
    public String name() {
        return name;
    }
    public String Birth_date() {
        return Birth_date;
    }
    public String Nationallity() {
        return Nationallity;
    }
    @Override
    public String toString() {
        return " author_id=" + author_id + ", name=" + name + ", Birth_date=" + Birth_date + ", Nationallity="
                + Nationallity;
    }
    
  }
}
