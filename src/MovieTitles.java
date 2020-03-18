/**
* Nour Sleiman
* Due: 3-19-20
*/

import java.util.*;
import java.io.*;

public class MovieTitles {      
  public static void main(String[] args) throws Exception{
    String[] files = new String[] {"..data/movies.csv"};
    String[][] movies = new String[9743][2];
    for(int f = 0; f < files.length; f++) {
      read(files[f], movies);                  
    }
    MovieBST MovieTree= new MovieBST();
    Movie root = new Movie(movies[0][0], movies[0][1]);
    for(int i = 0; i < movies.length-1; i++){
      MovieTree.insert(root, movies[i][0], movies[i][1]);
    }
    PrintWriter writer = new PrintWriter("../data/output/movieSubSets.txt");
    writer.println("set #1");
    ArrayList<String> set = new ArrayList<>();
    MovieTree.subSet(root, "Balto", "Catwalk", set);
    for(String s : set) {
      writer.println(s);
    }
    
    set.clear();
    writer.println("\n\nset #2");
    MovieTree.subSet(root, "Safe", "Unforgettable", set);
    for(String s : set){
      writer.println(s);
    }
    writer.close();
  }
  
  //movie titles and years are read in separately into a 2d array
  public static void read(String file, String[][] arr) throws Exception {
    BufferedReader br = new BufferedReader(new FileReader(file));
    String description = br.readLine();
    
    String data = br.readLine();
    //splits around commas. 
    // ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)" is a short-hand for ignoring commas within quotations.
    String[] line = data.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
    String whole = line[1].trim();
    int location = whole.lastIndexOf(")");
    String title = whole.substring(0, location - 5);
    String year = whole.substring(location - 4, location);
    arr[0][0] = title; 
    arr[0][1] = year;
    
    int count = 1;
    for(int i = 2; i < arr.length; i++){
      data = br.readLine();
      line = data.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
      whole = line[1].trim();
      if(whole.lastIndexOf(")") > 0) {
        location = whole.lastIndexOf(")");
        title = whole.substring(0, location - 5);
        year = whole.substring(location - 4, location);
        arr[count][0] = title;
        arr[count][1] = year;
        //Not all movies have the year attached to them in the file.
      } else {
        arr[count][0] = line[1].trim();
        arr[count][1] = "N/A";
      }
      count++;
    } 
    br.close();
  }
}

//Movie represents a node
class Movie {
  private String title;
  private String releaseYear;
  private Movie left;
  private Movie right;
  
  public Movie(String title, String year) {
    this.title = title;
    releaseYear = year;
    left = null;
    right = null;
  }
  
  public void setRight(Movie RNode) {
    right = RNode;
  }
  
  public void setLeft(Movie LNode) {
    left = LNode;
  }
  
  public Movie getRight() {
    return right;
  }
  
  public Movie getLeft() {
    return left;
  }
  
  public void setInfo(String t, String y) {
    title = t;
    releaseYear = y;
  }
  
  public String getTitle() {
    return title;
  }
  
  public String getReleaseYear(){
    return releaseYear;
  }
}

//Binary Search Tree
class MovieBST {
  public Movie root;
  
  public MovieBST() {
    root = null;
  }
  
  //if the title comes before the root's title, it is inserted in the left side
  //if the title comes after the root's title, it is inserted in the right side
  public Movie insert(Movie root, String title, String year){
    if(root == null) {
      root = new Movie(title, year);
      return root;
    } else if(root.getTitle().compareTo(title) > 0) {
      root.setLeft(insert(root.getLeft(), title, year));
    } else if (root.getTitle().compareTo(title) < 0) {
      root.setRight(insert(root.getRight(), title, year));
    }
  
    return root;
  }
  
  //collects all the movies between the first movie specified and last movie specified
  public void subSet(Movie root, String start, String end, ArrayList<String> list) {
    if(root == null) {
      return;
    }
    if(start.compareToIgnoreCase(root.getTitle()) < 0) {
      subSet(root.getLeft(), start, end, list);
    }
    if((start.compareToIgnoreCase(root.getTitle()) <= 0) && (end.compareToIgnoreCase(root.getTitle()) >= 0)) {
     list.add(root.getTitle() + " " + root.getReleaseYear()); 
    }
    if(end.compareToIgnoreCase(root.getTitle()) > 0) {
      subSet(root.getRight(), start, end, list);
    }
  }
} 
