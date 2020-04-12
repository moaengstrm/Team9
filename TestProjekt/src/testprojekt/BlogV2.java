/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testprojekt;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;



/**
 *
 * @author isakj
 */
public class BlogV2 extends javax.swing.JFrame {
    
    Profile profile;
    ArrayList<HashMap<String, String>> posts;
    ArrayList<BlogPost> blogPosts;
    
    int displayPage;

    MouseAdapter clickListenerNext = (new MouseAdapter() {  
        public void mouseClicked(MouseEvent c)  
        {  
           displayPage++;
           displayPosts(displayPage);
        }  
    });
    
        MouseAdapter clickListenerPrevious = (new MouseAdapter() {  
        public void mouseClicked(MouseEvent c)  
        {  
            displayPage--;
            displayPosts(displayPage);
        }  
    });
        
    MouseAdapter hoverListener = (new MouseAdapter() {
         public void mouseEntered(MouseEvent en) {
            setCursor(new Cursor(Cursor.HAND_CURSOR));
         }
         public void mouseExited(MouseEvent ex) {
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
         }
    });
    
    public BlogV2() {
        initComponents();
        setLocationRelativeTo(this);
        pnlNewPostTab.setVisible(false);
        txtText.getDocument().addDocumentListener(new DocumentListener() {
            
            @Override
            public void removeUpdate(DocumentEvent remove) {
                updateLetterCount();
            }

            @Override
            public void insertUpdate(DocumentEvent insert) {
                updateLetterCount();
            }

            @Override
            public void changedUpdate(DocumentEvent changed) {
                updateLetterCount();
            }
        });
        
        lblNext.addMouseListener(clickListenerNext);///////////////////////////////////////////////
        lblNext.addMouseListener(hoverListener);//////////////////////////////////////////////
        lblPrevious.addMouseListener(clickListenerPrevious);
        lblPrevious.addMouseListener(hoverListener);
        
        blogPosts = new ArrayList();
        displayPage = 0;
        createSamplePosts();
        displayPosts(displayPage);
    }
    
    public void createSamplePosts() {
        blogPosts.add(new BlogPost("Tanja", "Förtydligande Grupprapport", "Viktigt! \n \nGruppindelningen för grupprapporten finns under teamsammanställningen. \n \nDetta innebär att Grupp A i varje team skriver en egen rapport där de besvarar samtliga 3 uppgifter/frågor, och Grupp B i varje team skriver en egen rapport där de besvarar samtliga 3 uppgifter/frågor. \n \nNär ni lämnar in rapporten märk denna med Grupp nr och A/B (Ex. \"Grupp1A\")"));
        blogPosts.add(new BlogPost("Tanja", "Teamloggar", "Hej alla Team! \\n \\nNu har jag gjort uppdateringar på samtliga teamloggar, kika in på er teamlogg och följ anvisningar där (dvs. svara på mitt meddelande). Om teamloggen inte fungerar ber jag teamets Scrum Master att höra av sig till mig så att jag kan lösa det för er. \\n \\n//Tanja"));
        blogPosts.add(new BlogPost("Mathias", "Bokning av möten", "Hej \n \nGrupp 4, 5, 6 och 7 hittar en länk för att boka tid för customer meeting och sprintplaneringsmöte 1 under respektive teams loggar. \n\nVänligen \nMathias"));
        blogPosts.add(new BlogPost("Andreas", "Test-test", "God afton"));
        blogPosts.add(new BlogPost("Mathias", "HEJ", "Fest hos mig ikväll"));
        blogPosts.add(new BlogPost("Admin", "titel", "Text text text text text text text text text text text text text text text text text text text text text text text text text "));
    }
    
    public void addPost(BlogPost post) {
        blogPosts.add(0, post);
    }
    
    public void displayPost(int templateNumber, int page) {
        BlogPostTemplate[] templates = {post1, post2, post3, post4, post5};
        BlogPostTemplate template = templates[templateNumber];
        int post = page == 0 ? templateNumber : page + 4 + templateNumber;
        if (templateNumber % 2 == 1) {
            template.getTextArea().setBackground(new java.awt.Color(246, 246, 246));
        }
        if (post < blogPosts.size()) {
            template.setVisible(true);
            template.setAuthor(blogPosts.get(post).getAuthor());
            template.setText(blogPosts.get(post).getText());
            template.setTitle(blogPosts.get(post).getTitle());
        }
        else {
            template.setVisible(false);
        }
    }
    

    public void displayPosts(int page) {
        for (int i = 0; i < 5; i++) {
            displayPost(i, page);
        }
        displayPageInfo(page);
    }
    
    public void displayPageInfo(int page) {
        int showing = 0;
        int size = blogPosts.size();
        if(size <= 4) {
            showing = size;
        } else if (page == 0) {
            showing = 5;
        } else {
            showing = size - page * 5;
        }
        
        if(page == 0) {
            lblPrevious.setVisible(false);
        } else {
            lblPrevious.setVisible(true);    
        }
        
        lblShowingAmount.setText(Integer.toString(showing));
        lblTotalAmount.setText(size + " inlägg");
        
    }
    
    
    
    ////////////// New Post Tab /////////////////////
    
    private int countLetters(String text) {
        text = text.replaceAll(" ", "");
        return text.length();
    }
    
    private void updateLetterCount() {
        String text = txtText.getText();
        int count = countLetters(text);
        String LetterCount = Integer.toString(count);
        lblLetterCount.setText(LetterCount);
        if(count <= 2500) {
            updateLetterCountColor(false);
        } else {
            updateLetterCountColor(true);
        }
    }
    
    private void updateLetterCountColor (boolean tooManyLetters) {
        if(tooManyLetters) {
            lblMaxLetters.setForeground(Color.red);
            lblLetterCount.setForeground(Color.red);
        } else {
            lblMaxLetters.setForeground(Color.black);
            lblLetterCount.setForeground(Color.black);
        }
        updatePostButton(tooManyLetters);
    }
    
    private void updatePostButton(boolean tooManyLetters) {
        if(tooManyLetters) {
            btnPost.setEnabled(false);
        } else {
            btnPost.setEnabled(true);
        }
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlBlogTab = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        scrollPanel = new javax.swing.JScrollPane();
        pnlBlogPosts = new javax.swing.JPanel();
        post1 = new testprojekt.BlogPostTemplate();
        post2 = new testprojekt.BlogPostTemplate();
        post3 = new testprojekt.BlogPostTemplate();
        post4 = new testprojekt.BlogPostTemplate();
        post5 = new testprojekt.BlogPostTemplate();
        lblNext = new javax.swing.JLabel();
        lblPrevious = new javax.swing.JLabel();
        lblShowing = new javax.swing.JLabel();
        lblShowingAmount = new javax.swing.JLabel();
        lblSlash = new javax.swing.JLabel();
        lblTotalAmount = new javax.swing.JLabel();
        btnNewPost = new javax.swing.JButton();
        lblFilter = new javax.swing.JLabel();
        cbCategory = new javax.swing.JComboBox<>();
        btnShow = new javax.swing.JToggleButton();
        pnlNewPostTab = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        txtTitle = new javax.swing.JTextField();
        scrollPane = new javax.swing.JScrollPane();
        txtText = new javax.swing.JTextArea();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        btnPost = new javax.swing.JButton();
        lblLetterCount = new javax.swing.JLabel();
        lblMaxLetters = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        scrollPanel.setForeground(new java.awt.Color(255, 255, 255));
        scrollPanel.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        pnlBlogPosts.setEnabled(false);

        post2.setBackground(new java.awt.Color(246, 246, 246));

        post4.setBackground(new java.awt.Color(246, 246, 246));

        lblNext.setText("<HTML><U>Nästa&gt;&gt;</U></HTML>");

        lblPrevious.setText("<HTML><U>&lt;&lt;Förgående</U></HTML>");
        lblPrevious.setToolTipText("");

        lblShowing.setText("Visar  ");

        lblShowingAmount.setText("0");

        lblSlash.setText("/");

        lblTotalAmount.setText("5 inlägg");

        javax.swing.GroupLayout pnlBlogPostsLayout = new javax.swing.GroupLayout(pnlBlogPosts);
        pnlBlogPosts.setLayout(pnlBlogPostsLayout);
        pnlBlogPostsLayout.setHorizontalGroup(
            pnlBlogPostsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(post2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(post3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(post4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(post5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(post1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlBlogPostsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlBlogPostsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlBlogPostsLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblShowing)
                        .addGap(0, 0, 0)
                        .addComponent(lblShowingAmount))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlBlogPostsLayout.createSequentialGroup()
                        .addComponent(lblPrevious, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGroup(pnlBlogPostsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlBlogPostsLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblNext, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(pnlBlogPostsLayout.createSequentialGroup()
                        .addGap(0, 0, 0)
                        .addComponent(lblSlash)
                        .addGap(0, 0, 0)
                        .addComponent(lblTotalAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        pnlBlogPostsLayout.setVerticalGroup(
            pnlBlogPostsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBlogPostsLayout.createSequentialGroup()
                .addComponent(post1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(post2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(post3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(post4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(post5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addGroup(pnlBlogPostsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblShowing)
                    .addComponent(lblShowingAmount)
                    .addComponent(lblTotalAmount)
                    .addComponent(lblSlash))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlBlogPostsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNext, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPrevious, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        scrollPanel.setViewportView(pnlBlogPosts);

        btnNewPost.setText("Skapa inlägg");
        btnNewPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewPostActionPerformed(evt);
            }
        });

        lblFilter.setText("Filter");

        cbCategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Kategori --", "Jobb", "Fritid" }));

        btnShow.setText("Visa");
        btnShow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblFilter)
                        .addGap(6, 6, 6)
                        .addComponent(cbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)
                        .addComponent(btnShow))
                    .addComponent(btnNewPost, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addComponent(scrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 624, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFilter)
                    .addComponent(cbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnShow))
                .addGap(18, 18, 18)
                .addComponent(btnNewPost, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(scrollPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnlBlogTabLayout = new javax.swing.GroupLayout(pnlBlogTab);
        pnlBlogTab.setLayout(pnlBlogTabLayout);
        pnlBlogTabLayout.setHorizontalGroup(
            pnlBlogTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlBlogTabLayout.setVerticalGroup(
            pnlBlogTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pnlNewPostTab.setPreferredSize(new java.awt.Dimension(853, 447));

        lblTitle.setText("Titel");

        txtTitle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTitleActionPerformed(evt);
            }
        });

        txtText.setColumns(20);
        txtText.setLineWrap(true);
        txtText.setRows(5);
        txtText.setWrapStyleWord(true);
        txtText.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtTextPropertyChange(evt);
            }
        });
        scrollPane.setViewportView(txtText);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Kategori --", "Jobb", "Fritid" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jButton1.setText("Ny");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnPost.setText("Posta");
        btnPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPostActionPerformed(evt);
            }
        });

        lblLetterCount.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblLetterCount.setText("0");

        lblMaxLetters.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblMaxLetters.setText("/2500");

        jButton2.setText("Tillbaka");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlNewPostTabLayout = new javax.swing.GroupLayout(pnlNewPostTab);
        pnlNewPostTab.setLayout(pnlNewPostTabLayout);
        pnlNewPostTabLayout.setHorizontalGroup(
            pnlNewPostTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNewPostTabLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlNewPostTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollPane)
                    .addGroup(pnlNewPostTabLayout.createSequentialGroup()
                        .addComponent(lblTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTitle))
                    .addGroup(pnlNewPostTabLayout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(291, 291, 291)
                        .addComponent(btnPost, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 400, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlNewPostTabLayout.createSequentialGroup()
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblLetterCount, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(lblMaxLetters)))
                .addContainerGap())
        );
        pnlNewPostTabLayout.setVerticalGroup(
            pnlNewPostTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNewPostTabLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlNewPostTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitle)
                    .addComponent(txtTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlNewPostTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMaxLetters)
                    .addComponent(lblLetterCount)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(36, 36, 36)
                .addGroup(pnlNewPostTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnPost, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlNewPostTab, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 877, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pnlBlogTab, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlNewPostTab, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pnlBlogTab, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNewPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewPostActionPerformed
        // TODO add your handling code here:
        pnlNewPostTab.setVisible(true);
        pnlBlogTab.setVisible(false);
    }//GEN-LAST:event_btnNewPostActionPerformed

    private void btnShowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnShowActionPerformed

    private void txtTitleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTitleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTitleActionPerformed

    private void txtTextPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtTextPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTextPropertyChange

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPostActionPerformed
        // TODO add your handling code here:
        String author = "Admin";
        String title = txtTitle.getText();
        String text = txtText.getText();
        BlogPost post = new BlogPost(author, title, text);
        addPost(post);
        displayPage = 0;
        displayPosts(displayPage);
        pnlNewPostTab.setVisible(false);
        pnlBlogTab.setVisible(true);
    }//GEN-LAST:event_btnPostActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        pnlBlogTab.setVisible(true);
        pnlNewPostTab.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNewPost;
    private javax.swing.JButton btnPost;
    private javax.swing.JToggleButton btnShow;
    private javax.swing.JComboBox<String> cbCategory;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblFilter;
    private javax.swing.JLabel lblLetterCount;
    private javax.swing.JLabel lblMaxLetters;
    private javax.swing.JLabel lblNext;
    private javax.swing.JLabel lblPrevious;
    private javax.swing.JLabel lblShowing;
    private javax.swing.JLabel lblShowingAmount;
    private javax.swing.JLabel lblSlash;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTotalAmount;
    private javax.swing.JPanel pnlBlogPosts;
    private javax.swing.JPanel pnlBlogTab;
    private javax.swing.JPanel pnlNewPostTab;
    private testprojekt.BlogPostTemplate post1;
    private testprojekt.BlogPostTemplate post2;
    private testprojekt.BlogPostTemplate post3;
    private testprojekt.BlogPostTemplate post4;
    private testprojekt.BlogPostTemplate post5;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JScrollPane scrollPanel;
    private javax.swing.JTextArea txtText;
    private javax.swing.JTextField txtTitle;
    // End of variables declaration//GEN-END:variables

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BlogV2().setVisible(true);
            }
        });
    }
    
}
