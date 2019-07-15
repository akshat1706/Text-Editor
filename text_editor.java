
// Code for text editor

import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.text.*;

public class TextEditor extends JFrame implements ActionListener 
{
    public static void main(String args[])  // Main class
    {
        TextEditor e = new TextEditor();
    }
    
    // Text area creation

    JTextPane t;

    // Frame creation
    
    JFrame f;
    JFrame newFrame = new JFrame();
    JFrame editor_frame = new JFrame();
    JButton case_lower = new JButton("LOWERCASE");
    JButton case_upper = new JButton("UPPERCASE");
    Choice ftype = new Choice();
    Choice fsize = new Choice();
    JFrame myframe = new JFrame();


    TextEditor() // Constructor
    {
    	myframe.setVisible(false);
        JPanel mainPanel = new JPanel(new GridLayout(6, 1));
        
        JLabel fi = new JLabel("WHAT DO YOU WANT TO FIND ?"); // Find option
        mainPanel.add(fi);
        
        JTextField f1 = new JTextField();
        mainPanel.add(f1);
        
        JLabel rw = new JLabel("REPLACE WITH"); // Replace option
        mainPanel.add(rw);
        
        JTextField f2 = new JTextField();
        mainPanel.add(f2);
        
        JButton rbt = new JButton("REPLACE"); // Replace option
        mainPanel.add(rbt);
        
        JButton rabt = new JButton("REPLACE ALL"); // Replace all option
        mainPanel.add(rabt);
        
        myframe.add(mainPanel); // Added frame to panel
        myframe.setSize(200, 400);

        rbt.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent) 
            {
                StringBuffer s = new StringBuffer(t.getText()); // Code for replace option
                int start = t.getText().indexOf(f1.getText(), 0);
                int end = start + f1.getText().length();
                s.replace(start, end, f2.getText());
                t.setText(s.toString());
            }
        });
        
        rabt.addActionListener(new ActionListener() 
        {    
            @Override
            public void actionPerformed(ActionEvent actionEvent) 
            {
                String s = new String(t.getText()); // Code for replace all option
                String temp =  s.replaceAll(f1.getText(), f2.getText());
                t.setText(temp);
            }
        });

        JButton word_character_count; // To count number of words
        
        GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fontnames = e.getAvailableFontFamilyNames();
        
        for(int i = 0; i < fontnames.length; i ++) // Drop down menu for fonts
            ftype.add(fontnames[i]);

        for(int i = 8; i < 64; i = i + 2) // Drop down menu for font sizes
        {
            fsize.add("" + i);
        }

        word_character_count = new JButton("COUNT WORDS AND CHARACTERS");
        word_character_count.addActionListener(this);
        
        editor_frame.setSize(800, 500);
        editor_frame.setVisible(false);
        Draw ob = new Draw();
        editor_frame.add(ob);

        JPanel findpanel = new JPanel(new GridLayout(3, 1)); // Find panel created
        
        JLabel findlabel = new JLabel("PLEASE ENTER THE TEXT YOU WANT TO FIND");
        findpanel.add(findlabel);
        
        JTextField findfield = new JTextField();
        findpanel.add(findfield);
        
        JButton findbt = new JButton("Find");
        findpanel.add(findbt);
        
        newFrame.add(findpanel); // Added find panel to frame
        newFrame.setVisible(false);
        newFrame.setSize(500,200);
        
        f = new JFrame("TEXT EDITOR"); // Frame created
        JMenuItem rep = new JMenuItem("Replace"); // Replace menu

        t = new JTextPane(); // Text area

        JMenuBar mb = new JMenuBar(); // Menubar created

        JMenu m1 = new JMenu("FILE");
        
        JButton graphics = new JButton("DRAW SHAPES");
        graphics.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent) 
            {
                editor_frame.setVisible(true);
            }
        });
        
        findbt.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent) 
            {
                if(t.getText().length() == 0)
                    JOptionPane.showMessageDialog(null, "Not found!"); // If text is not found

                else 
                {
                    String text = findfield.getText();
                    String tmp = t.getText();
                    
                    int count = 0;
                    int start = 0;
                    int again = 0;
                    
                    Highlighter.HighlightPainter ob = new DefaultHighlighter.DefaultHighlightPainter(Color.CYAN);
                    Highlighter high = t.getHighlighter();
                    
                    do 
                    {
                        start = tmp.indexOf(text, again);
                        int end = start + text.length();
                        if (start != -1) 
                        {
                            try 
                            {
                                count++;
                                high.addHighlight(start, end, ob);
                            } 
                            catch (BadLocationException e) 
                            {
                                e.printStackTrace();
                            }
                            again = end;
                        }
                        
                        again = again + 1;
                    }while (start!=-1);
                    
                    if(count==0)
                        JOptionPane.showMessageDialog(null,"NOT FOUND!");
                }
            }
        });

        // Creating menu items for first menu
        JMenuItem mi1 = new JMenuItem("New");
        JMenuItem mi2 = new JMenuItem("Open");
        JMenuItem mi3 = new JMenuItem("Save");
        JMenuItem mi9 = new JMenuItem("Print");

        //Adding action listener
        mi1.addActionListener(this);
        mi2.addActionListener(this);
        mi3.addActionListener(this);
        mi9.addActionListener(this);
        
        case_lower.addActionListener(this);
        case_upper.addActionListener(this);

        m1.add(mi1);
        m1.add(mi2);
        m1.add(mi3);
        m1.add(mi9);
        
        JMenu m2 = new JMenu("EDIT");

        //Creating menu items for second menu
        JMenuItem mi4 = new JMenuItem("Cut");
        JMenuItem mi5 = new JMenuItem("Copy");
        JMenuItem mi6 = new JMenuItem("Paste");
        JMenuItem mi7 = new JMenuItem("Find");

        //Adding action listener
        mi4.addActionListener(this);
        mi5.addActionListener(this);
        mi6.addActionListener(this);
        mi7.addActionListener(this);
        rep.addActionListener(this);

        m2.add(mi4);
        m2.add(mi5);
        m2.add(mi6);
        m2.add(mi7);
        m2.add(rep);

        mb.add(m1);
        mb.add(m2);
        mb.add(case_lower);
        mb.add(case_upper);
        mb.add(graphics);
        mb.add(word_character_count);
        
        mb.add(ftype);
        mb.add(fsize);

        ftype.addItemListener(new ItemListener() 
        {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) 
            {
                String fn = itemEvent.getItem().toString();
                StyledDocument doc = (StyledDocument) t.getDocument();
                int selectionEnd = t.getSelectionEnd();
                int selectionStart = t.getSelectionStart();
                
                if (selectionStart == selectionEnd) 
                    return;

                Element element = doc.getCharacterElement(selectionStart);
                AttributeSet as = element.getAttributes();
                MutableAttributeSet asNew = new SimpleAttributeSet(as.copyAttributes());
                StyleConstants.setFontFamily(asNew, itemEvent.getItem().toString());
                doc.setCharacterAttributes(selectionStart, t.getSelectedText().length(), asNew, true);
                String text = (StyleConstants.isBold(as) ? "Cancel Bold" : "Bold");
            }
        });

        fsize.addItemListener(new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) 
            {
                String fn = itemEvent.getItem().toString();
                StyledDocument doc = (StyledDocument) t.getDocument();
                int selectionEnd = t.getSelectionEnd();
                int selectionStart = t.getSelectionStart();
                
                if (selectionStart == selectionEnd) 
                    return;

                Element element = doc.getCharacterElement(selectionStart);
                AttributeSet as = element.getAttributes();
                MutableAttributeSet asNew = new SimpleAttributeSet(as.copyAttributes());
                StyleConstants.setFontSize(asNew, Integer.parseInt(itemEvent.getItem().toString()));
                doc.setCharacterAttributes(selectionStart, t.getSelectedText().length(), asNew, true);
                String text = (StyleConstants.isBold(as) ? "Cancel Bold" : "Bold");
            }
        });

        f.setJMenuBar(mb);
        f.add(t);
        f.setSize(500, 500);
        f.show();
    }

    // When a button is pressed
    public void actionPerformed(ActionEvent e)
    {
        String s = e.getActionCommand();

        if(s.equals("cut")) 
            t.cut();
        
        else if(s.equals("copy")) 
            t.copy();
        
        else if(s.equals("Paste")) 
            t.paste(); 
        
        else if(s.equals("Find"))
            newFrame.setVisible(true);
        
        else if (s.equals("Save")) 
        {
            JFileChooser j = new JFileChooser("f:"); // Create an object of JFileChooser

            int r = j.showSaveDialog(null); // Show dialog box

            if (r == JFileChooser.APPROVE_OPTION) 
            {
                File fi = new File(j.getSelectedFile().getAbsolutePath());

                try 
                {
                    FileWriter wr = new FileWriter(fi, false); // Create a filewriter

                    BufferedWriter w = new BufferedWriter(wr); // Create a buffered writer

                    w.write(t.getText());
                    w.flush();
                    w.close();
                }
                catch (Exception evt) 
                {
                    JOptionPane.showMessageDialog(f, evt.getMessage());
                }
            }
            // If user cancels the operation
            else
                JOptionPane.showMessageDialog(f, "OPERATION WAS ABORTED BY THE USER!");
        }
        
        else if (s.equals("Print")) 
        {
            try 
            {
            	t.print(); // Print the file
            }
            catch (Exception evt) 
            {
                JOptionPane.showMessageDialog(f, evt.getMessage());
            }
        }
        
        else if (s.equals("Open")) 
        {
            JFileChooser j = new JFileChooser("f:");
            int r = j.showOpenDialog(null);
            
            if (r == JFileChooser.APPROVE_OPTION) 
            {
                File fi = new File(j.getSelectedFile().getAbsolutePath());
                try
                {
                    String s1 = "", sl = "";
                    FileReader fr = new FileReader(fi);
                    BufferedReader br = new BufferedReader(fr);
                    sl = br.readLine();
                    while ((s1 = br.readLine()) != null) 
                        sl = sl + "\n" + s1;
                    
                    t.setText(sl);
                }
                catch (Exception evt) 
                {
                    JOptionPane.showMessageDialog(f, evt.getMessage());
                }
            }
            // If operation is cancelled by the user
            else
                JOptionPane.showMessageDialog(f, "OPERATION WAS ABORTED BY THE USER!");
        }
        
        else if (s.equals("New")) 
            t.setText("");
        
        
        else if (s.equals("close")) 
            f.setVisible(false);
        
        else if(s.equals("COUNT WORDS AND CHARACTERS")) // Counting words and characters
        {
            String temp = t.getSelectedText();
            int w1 = temp.length();
            String[] w2 = temp.split(" ");
            int w3 = w2.length;
            JOptionPane.showMessageDialog(null, "NUMBER OF WORDS: " + w3 + ", NUMBER OF CHARACTERS: " + w1);
        }

        // Lower case
        else if(s.equals("LOWERCASE"))
        {
            String temp = t.getSelectedText();
            t.replaceSelection(temp.toLowerCase());
        }
        // For upper case
        else if(s.equals("UPPERCASE"))
        {
            String temp = t.getSelectedText();
            t.replaceSelection(temp.toUpperCase());
        }
        // For replace
        else if(s.equals("Replace"))
            myframe.setVisible(true);
    }
}

class Draw extends JPanel implements MouseListener,MouseMotionListener 
{
    JRadioButton bt5, bt2, bt3, bt4, bt1;
    int x;
    int xo1,xo2,y1,y2;

    public Draw() 
    {
    	this.setPreferredSize(new Dimension(1000, 1000));
    	this.setBackground(Color.WHITE);
       
    	// Setting buttons for shapes
        bt1 = new JRadioButton("Circle");
        bt2 = new JRadioButton("Oval");
        bt3 = new JRadioButton("Round Rectangle");
        bt4 = new JRadioButton("Square");
        bt5 = new JRadioButton("Recangle");
        
        addMouseListener(this);
        addMouseMotionListener(this);

        this.add(bt1);
        this.add(bt2);
        this.add(bt3);
        this.add(bt4);
        this.add(bt5);
        
        bt5.setSelected(false);
        bt2.setSelected(false);
        bt3.setSelected(false);
        bt4.setSelected(false);
        bt1.setSelected(false);
        
        bt5.addActionListener(new ActionListener() // Rectangle
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                bt1.setSelected(false);
                bt2.setSelected(false);
                bt3.setSelected(false);
                bt4.setSelected(false);
                x = 1;
                drawing();
            }
        });
        
        bt2.addActionListener(new ActionListener() // Oval
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                bt5.setSelected(false);
                bt3.setSelected(false);
                bt4.setSelected(false);
                bt1.setSelected(false);
                x = 2;
                drawing();
            }
        });
        
        bt3.addActionListener(new ActionListener() // Rounded rectangle
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                bt2.setSelected(false);
                bt5.setSelected(false);
                bt4.setSelected(false);
                bt1.setSelected(false);
                x = 3;
                drawing();
            }
        });

        bt4.addActionListener((new ActionListener() // Square
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent) 
            {
                bt2.setSelected(false);
                bt3.setSelected(false);
                bt5.setSelected(false);
                bt1.setSelected(false);
                x = 4;
                drawing();
            }
        }));
        
        bt1.addActionListener(new ActionListener() // Circle
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent) 
            {
                bt5.setSelected(false);
                bt2.setSelected(false);
                bt2.setSelected(false);
                bt4.setSelected(false);
                x = 5;
                drawing();
            }
        });
    }

    public void drawing() 
    {
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        if (x == 1) 
        {
            g.setColor(Color.red);
            g.fillRect(xo1, y1, xo2 - xo1, y2 - y1);
        }
        
        if (x == 2) 
        {
            g.setColor(Color.red);
            g.fillOval(xo1, y1, xo2 - xo1, y2 - y1);
        }
        
        if (x == 3) 
        {
            g.setColor(Color.red);
            g.fillRoundRect(xo1, y1, xo2 - xo1, y2 - y1, 50, 50);
        }
        
        if(x==4)
        {   g.setColor(Color.red);
            g.fillRect(xo1, y1, xo2 - xo1, xo2 - xo1);
        }
        
        if(x == 5)
        {
            g.setColor(Color.red);
            g.fillOval(xo1, y1, xo2 - xo1, xo2 - xo1);
        }
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) 
    {
        xo1 = mouseEvent.getX();
        y1 = mouseEvent.getY();
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) 
    {
        xo1 = mouseEvent.getX();
        y1 = mouseEvent.getY();
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent)
    {
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) 
    {
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) 
    {
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) 
    {
        xo2 = mouseEvent.getX();
        y2 = mouseEvent.getY();
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) 
    {
    }
}
