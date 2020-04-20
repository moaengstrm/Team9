/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testprojekt;

import java.util.Date;

/**
 *
 * @author isakj
 */
public class BlogPost {
    
    private String id;
    private String author;
    private Date date;
    private String title;
    private String text;
    private String category;
    
    public BlogPost(String id, String author, Date date, String title, String text, String category) {
        this.id = id;
        this.author = author;
        this.date = date; 
        this.title = title;
        this.date = date;
        this.text = text;
        this.category = category;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public String getID() {
        return id;
    }
    
    public Date getDate() {
        return date;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getText() {
        return text;
    }
    
    public String getCategory() {
        return category;
    }
    
}
