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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import oru.inf.InfDB;
import oru.inf.InfException;



/**
 *
 * @author isakj
 */
public class BlogV2 extends javax.swing.JFrame {
    
    InfDB idb;
    Profile profile;
    Validator validator;
    SimpleDateFormat dateFormat;
    NewCategoryWindow newCategory; 
    
    ArrayList<BlogPost> blogPosts;
    
    String userID;
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
    
    public BlogV2(String userID) {
        initComponents();
        this.userID = userID;
        idb = TestProjekt.getDB();
        validator = new Validator();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        newCategory = new NewCategoryWindow();
        setCategoryCbs(); 
        
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
        setPosts();
        displayPage = 0;

        displayPosts(displayPage);
    }
    
    public void addPost(BlogPost post) {
        blogPosts.add(0, post);
    }
    
    public void displayPost(int templateNumber, int page) {
        BlogPostTemplate[] templates = {post1, post2, post3, post4, post5};
        BlogPostTemplate template = templates[templateNumber];
        //int post = page == 0 ? templateNumber : page + 4 + templateNumber;
        int post = page * 5 + templateNumber;
        if (templateNumber % 2 == 1) {
            template.getTextArea().setBackground(new java.awt.Color(246, 246, 246));
            template.getTitleField().setBackground(new java.awt.Color(246, 246, 246));
        }
        if (post < blogPosts.size()) {
            template.setVisible(true);
            template.setAuthor(blogPosts.get(post).getAuthor());
            template.setText(blogPosts.get(post).getText());
            template.setTitle(blogPosts.get(post).getTitle());
            template.setId(blogPosts.get(post).getID());
            template.setCategory("#"+findCategoryName(blogPosts.get(post).getCategory()));
            String date = formatDate(blogPosts.get(post).getDate());
            template.setDate(date);
            
            if(findUserName(userID).equals(blogPosts.get(post).getAuthor())) {
                templates[templateNumber].getEditButton().setVisible(true);
            } else {
                templates[templateNumber].getEditButton().setVisible(false);
            }
       }
        else {
            template.setVisible(false);
        }
    }

    public void displayPosts(int page) {
        for (int i = 0; i < 5; i++) {
            displayPost(i, page);
        }
        displayPageInfo();
    }
    
    public void displayPageInfo() {
        int showing = 0;
        int size = blogPosts.size();
        if(size <= 4) {
            showing = size;
        } else if (displayPage == 0) {
            showing = 5;
        } else {
            System.out.println(size);
            showing = size - (displayPage * 5) > 5 ? 5 : size - ((displayPage) * 5);
        }
        
        if(displayPage == 0) {
            lblPrevious.setVisible(false);
        } else {
            lblPrevious.setVisible(true);    
        }
//        if ((displayPage + 1) * 5 >= size) {
//            lblNext.setVisible(false);
//        }   
        
        lblShowingAmount.setText(Integer.toString(showing));
        lblTotalAmount.setText(size + " inl�gg");
        
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
    
    public String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }
    
    public void setPosts() {
        try {
            try {
                String cbValue = cbShowFormal.getSelectedItem().toString();
                String table = (cbValue.equals("Formella inl�gg")) ? "Formell" : "Informell";
                String searchString = txtSearch.getText();
                String where = ", " + table + " WHERE Inlagg.InlaggsID = " + table + ".InlaggsID" +
                               " AND Text like '%" + searchString + "%'";
                String query = "Select * FROM Inlagg" + where;
                                System.out.println(query);
                ArrayList<HashMap<String, String>> posts = idb.fetchRows(query);
                blogPosts.clear();
                if (!posts.isEmpty()) {
                    for (HashMap<String, String> post : posts) {
                        String rubrik = post.get("RUBRIK");
                        String text = post.get("TEXT");
                        String inlaggsid = post.get("INLAGGSID");
                        String kategori = post.get("KAID");
                        String id = post.get("ANVANDAR_ID");
                        Date datum = new Date();
                        BlogPost blogPost = new BlogPost(inlaggsid, findUserName(id), datum, rubrik, text, kategori);
                        addPost(blogPost);
                    }
                }
            } catch(NullPointerException npe) {

            }
        } catch (InfException ie) {
            System.out.println(ie.getMessage());
        }
    }
    
    public void filterPosts() {
        
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
        cbShowFormal = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        btnSokProfil = new javax.swing.JButton();
        txtSok = new javax.swing.JTextField();
        pnlNewPostTab = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        txtTitle = new javax.swing.JTextField();
        scrollPane = new javax.swing.JScrollPane();
        txtText = new javax.swing.JTextArea();
        cbCategoryNewPost = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        btnPost = new javax.swing.JButton();
        lblLetterCount = new javax.swing.JLabel();
        lblMaxLetters = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        cbFormal = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        scrollPanel.setForeground(new java.awt.Color(255, 255, 255));
        scrollPanel.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        pnlBlogPosts.setEnabled(false);

        post2.setBackground(new java.awt.Color(246, 246, 246));

        post4.setBackground(new java.awt.Color(246, 246, 246));

        lblNext.setText("<HTML><U>N�sta&gt;&gt;</U></HTML>");

        lblPrevious.setText("<HTML><U>&lt;&lt;F�rg�ende</U></HTML>");
        lblPrevious.setToolTipText("");

        lblShowing.setText("Visar  ");

        lblShowingAmount.setText("0");

        lblSlash.setText("/");

        lblTotalAmount.setText("5 inl�gg");

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
                .addGroup(pnlBlogPostsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlBlogPostsLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblShowing)
                        .addGap(0, 0, 0)
                        .addComponent(lblShowingAmount))
                    .addGroup(pnlBlogPostsLayout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(lblPrevious, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(pnlBlogPostsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlBlogPostsLayout.createSequentialGroup()
                        .addComponent(lblSlash)
                        .addGap(0, 0, 0)
                        .addComponent(lblTotalAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBlogPostsLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblNext, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(67, 67, 67))))
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

        btnNewPost.setText("Skapa inl�gg");
        btnNewPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewPostActionPerformed(evt);
            }
        });

        lblFilter.setText("Filter");

        cbCategory.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                cbCategoryPopupMenuWillBecomeVisible(evt);
            }
        });
        cbCategory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbCategoryMouseClicked(evt);
            }
        });
        cbCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbCategoryActionPerformed(evt);
            }
        });

        btnShow.setText("Visa");
        btnShow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowActionPerformed(evt);
            }
        });

        cbShowFormal.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Formella inl�gg", "Informella inl�gg" }));
        cbShowFormal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbShowFormalActionPerformed(evt);
            }
        });

        jLabel1.setText("Fritext");

        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });

        jButton3.setText("Tillbaka");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        btnSokProfil.setText("S�k profil");
        btnSokProfil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSokProfilActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnNewPost, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblFilter)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbCategory, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cbShowFormal, 0, 170, Short.MAX_VALUE)
                                    .addComponent(txtSearch))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnShow, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txtSok)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSokProfil)
                        .addGap(55, 55, 55)))
                .addComponent(scrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 630, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblFilter)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnShow))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbShowFormal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSokProfil)
                    .addComponent(txtSok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 130, Short.MAX_VALUE)
                .addComponent(btnNewPost, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67)
                .addComponent(jButton3)
                .addGap(21, 21, 21))
            .addComponent(scrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
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

        cbCategoryNewPost.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                cbCategoryNewPostPopupMenuWillBecomeVisible(evt);
            }
        });
        cbCategoryNewPost.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbCategoryNewPostMouseClicked(evt);
            }
        });
        cbCategoryNewPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbCategoryNewPostActionPerformed(evt);
            }
        });

        jButton1.setText("Ny kategori");
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

        cbFormal.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Formellt inl�gg", "Informellt inl�gg" }));

        javax.swing.GroupLayout pnlNewPostTabLayout = new javax.swing.GroupLayout(pnlNewPostTab);
        pnlNewPostTab.setLayout(pnlNewPostTabLayout);
        pnlNewPostTabLayout.setHorizontalGroup(
            pnlNewPostTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNewPostTabLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlNewPostTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlNewPostTabLayout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(291, 291, 291)
                        .addComponent(btnPost, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 376, Short.MAX_VALUE))
                    .addComponent(scrollPane)
                    .addGroup(pnlNewPostTabLayout.createSequentialGroup()
                        .addComponent(lblTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTitle))
                    .addGroup(pnlNewPostTabLayout.createSequentialGroup()
                        .addComponent(cbFormal, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(cbCategoryNewPost, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
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
                    .addComponent(cbCategoryNewPost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(cbFormal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
            .addComponent(pnlNewPostTab, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 883, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pnlBlogTab, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlNewPostTab, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 491, Short.MAX_VALUE)
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
        setPosts();
        displayPosts(0);
    }//GEN-LAST:event_btnShowActionPerformed

    private void txtTitleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTitleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTitleActionPerformed

    private void txtTextPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtTextPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTextPropertyChange

    private void cbCategoryNewPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCategoryNewPostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbCategoryNewPostActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        newCategory.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPostActionPerformed
        // TODO add your handling code here:
        try {
            String author = idb.fetchSingle("select Anvandar_namn from anvandare where anvandar_id = " + userID);
            String title = txtTitle.getText();
            String text = txtText.getText();
            String category = cbCategoryNewPost.getSelectedItem().toString();
            
            if (!category.equals("-- Kategori --")) {
                category = findCategoryID(category);
            }

            if (validator.validateNewPost(title, text, category)) {
                
                int id;
                if (idb.fetchSingle("SELECT count (*) FROM Inlagg").equals("0")) {
                    id = 1;
                } else {
                    String maxInlaggsID = idb.fetchSingle("SELECT max (inlaggsid) FROM Inlagg");

                    int maxID = Integer.parseInt(maxInlaggsID);
                    id = maxID + 1;
                }
                
                String postID = Integer.toString(id);
                
                System.out.println("test");
                String query = "INSERT into Inlagg values('" + title + "', '" + text + "', " + id + ", " + userID + ", " + category + ");";
                idb.insert(query);
                if (cbFormal.getSelectedItem().equals("Formellt inl�gg")) {
                    query = "INSERT INTO Formell values(" + id + ")";
                    idb.insert(query);
                } else {
                 query = "INSERT INTO Informell values(" + id + ")";
                    idb.insert(query);
                }

                BlogPost post = new BlogPost(postID, author, new Date(), title, text, category);
                addPost(post);
                displayPage = 0;
                displayPosts(displayPage);
                pnlNewPostTab.setVisible(false);
                pnlBlogTab.setVisible(true);
            }
        } catch (InfException ie) {
            System.out.println(ie.getMessage() + "test");
        } 
    }//GEN-LAST:event_btnPostActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        pnlBlogTab.setVisible(true);
        pnlNewPostTab.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void cbCategoryNewPostMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbCategoryNewPostMouseClicked
       // setCategoryCbs();
    }//GEN-LAST:event_cbCategoryNewPostMouseClicked

    private void cbCategoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbCategoryMouseClicked
       // setCategoryCbs();
    }//GEN-LAST:event_cbCategoryMouseClicked

    private void cbCategoryPopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbCategoryPopupMenuWillBecomeVisible
        try {
            if(idb.fetchSingle("SELECT count (*) FROM KATEGORI").equals("0")){
                JOptionPane.showMessageDialog(null, "Det finns ingen kategori tillagd");
            }
            setCategoryCbs();
        } catch (InfException ex) {
            Logger.getLogger(BlogV2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_cbCategoryPopupMenuWillBecomeVisible

    private void cbCategoryNewPostPopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbCategoryNewPostPopupMenuWillBecomeVisible
        try {
            if(idb.fetchSingle("SELECT count (*) FROM KATEGORI").equals("0")){
                JOptionPane.showMessageDialog(null, "Det finns ingen kategori tillagd");
            }
            setCategoryCbs();
        } catch (InfException ex) {
            Logger.getLogger(BlogV2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_cbCategoryNewPostPopupMenuWillBecomeVisible

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed

    private void cbCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCategoryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbCategoryActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        setVisible(false);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btnSokProfilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSokProfilActionPerformed
        // TODO add your handling code here:
      
     try{
        String anvandaresok = this.txtSok.getText();
        String an = this.idb.fetchSingle("select ANVANDAR_ID from ANVANDARE where NAMN = '" + anvandaresok + "'");
        String na = this.idb.fetchSingle("select NAMN from ANVANDARE where ANVANDAR_ID = '" + an + "'");
        String em = this.idb.fetchSingle("select EPOST from ANVANDARE where ANVANDAR_ID= '" + an + "'");
        String ph = this.idb.fetchSingle("select TELE from ANVANDARE where ANVANDAR_ID = '" + an + "'");
        
            if(anvandaresok.equals(na)) {
              this.setVisible(false);
            new Profile(idb,na,ph,em).setVisible(true);
            
           }
     else {
                 
                 JOptionPane.showMessageDialog(null, "Kunde inte hitta anvandaren");
             }
        }
        catch(InfException e)
        {
        JOptionPane.showMessageDialog(null, "Ett fel uppstod.");
                System.out.println("Internt felmeddelande:" + e.getMessage());
        }
    }//GEN-LAST:event_btnSokProfilActionPerformed

    private void cbShowFormalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbShowFormalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbShowFormalActionPerformed

    public String findCategoryID(String category) {    
        String result = "";
        try {
            String query = "SELECT KAID FROM Kategori WHERE Namn = '" + category + "'";
            result = idb.fetchSingle(query);
        } catch(InfException ie) {
                
        }
        return result;
    }
    
    public String findCategoryName(String id) {
        String query = "Select Namn from Kategori where KAID = " + id;
        String result = "";
        try {
            result = idb.fetchSingle(query);
        } catch(InfException ie) {
            System.out.println(ie.getMessage());
        }
        return result;
    }
    
    public String findUserName(String id) {    
        String result = "";
        try {
            String query = "SELECT Anvandar_namn FROM Anvandare WHERE Anvandar_ID = '" + id + "'";
            result = idb.fetchSingle(query);
        } catch(InfException ie) {
                
        }
        return result;
    }
   
   public void setCategoryCbs(){ 
        try {
            
            if(idb.fetchSingle("SELECT count (*) FROM KATEGORI").equals("0")){
            
            }
            else{
            ArrayList<String> kategorier = idb.fetchColumn("SELECT namn FROM KATEGORI");
            DefaultComboBoxModel kategori = new DefaultComboBoxModel();
            kategori.addElement("-- Kategori --");
            for(String listaKategorier : kategorier){
                kategori.addElement(listaKategorier);
            
           }
            cbCategory.setModel(kategori);
            cbCategoryNewPost.setModel(kategori);
            }
        } catch (InfException ex) {
            Logger.getLogger(BlogV2.class.getName()).log(Level.SEVERE, null, ex);
        }
   }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNewPost;
    private javax.swing.JButton btnPost;
    private javax.swing.JToggleButton btnShow;
    private javax.swing.JButton btnSokProfil;
    private javax.swing.JComboBox<String> cbCategory;
    private javax.swing.JComboBox<String> cbCategoryNewPost;
    private javax.swing.JComboBox<String> cbFormal;
    private javax.swing.JComboBox<String> cbShowFormal;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtSok;
    private javax.swing.JTextArea txtText;
    private javax.swing.JTextField txtTitle;
    // End of variables declaration//GEN-END:variables

    
}
