package game;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.util.*;
import java.lang.*;
public class Bombs extends JPanel {
    public static class Game extends JPanel
    {
        private JFrame frame;
        private JPanel A, B, C, bup, bdown;
        private JButton b1, b2, c1, c2, c3;
        private JTextArea terminal = new JTextArea(5,18);
        private JScrollPane scrollPane = new JScrollPane(terminal,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        private int safe, limit, mi, btncounter, bumb;
        private int  points = 0;
        private boolean life = false; private boolean levset = false;
        private ArrayList<JPanel> plist = new ArrayList<JPanel>();
        private ArrayList<Boolean> blist = new ArrayList<Boolean>();
        private enum Level {
            //EASY("5"), INTERMED("7"), HARD("9");
            }
            private static Game singleton = new Game(); //Prevents multiple instances creation
            private Game()
            {
                frame = new JFrame();
                frame.setTitle("Evade The Bomb");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                terminal.setEditable(false);

                Button b1 = new Button("Play Game");
                b1.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            if(levset == false){
                                terminal.append("Select Difficulty!\n");
                                terminal.setCaretPosition(terminal.getDocument().getLength());
                            }
                            else if(levset == true){
                                life = true; points = 0; safe =0; btncounter = 0; blist.clear();//Everytime reset the coordinate of mine
                                setGame();
                            }
                        }
                    });
                b1.setSize(50, 20);
                Button b2 = new Button("Exit");
                b2.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            //quits program
                            System.exit(0);
                        }
                    });
                Button c1 = new Button("Easy");
                c1.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            //sets level into easy
                            terminal.append("Current Level is EASY\n");
                            levset = true;
                            limit = 5;
                        }
                    });
                Button c2 = new Button("Intermediate");
                c2.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            //sets level into intermediate
                            terminal.append("Current Level is INTERMEDIATE\n");
                            levset = true;
                            limit = 7;
                        }
                    });
                Button c3 = new Button("Difficult");
                c3.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            //sets level into difficult
                            terminal.append("Current Level is HARD\n");
                            levset = true;
                            limit = 9;
                        }
                    });

                A = new JPanel();
                A.setLayout(new GridLayout(5, 2, 1, 1));
                A.setPreferredSize(new Dimension(250, 400));
                A.setBackground(Color.WHITE);
                for(int j = 0; j< 2; j++){
                    for(int i=0; i<5; i++){
                        JPanel panel = new JPanel();
                        panel.setBackground(Color.RED);
                        panel.setPreferredSize(new Dimension(45, 190));
                        panel.addMouseListener(new MouseAdapter(){
                                public void mousePressed(MouseEvent e){
                                    if(life == true && safe < limit){
                                        panel.setBackground(Color.YELLOW);
                                        points += 5; safe++; btncounter++;
                                        //System.out.printf("You've gotten %d points!\n",points);
                                        terminal.append("You've gotten "+points+" points!\n");
                                        if(safe == limit){
                                            terminal.append("ALL CLEAR!\n");
                                            panel.setBackground(Color.YELLOW);
                                            life = false;
                                        }
                                    }
                                    else{
                                        panel.setBackground(Color.RED);
                                        terminal.append("Press Start Button!\n");
                                    }
                                    if(blist.get(plist.indexOf(panel)) == true && life == true)
                                    {
                                        panel.setBackground(Color.BLACK);
                                        life = false;
                                        terminal.append("Game Over!\n");
                                    }
                                }
                            });
                        A.add(panel, j,i);
                        plist.add(panel);
                    }
                }

                B = new JPanel();
                B.setLayout(null);//new BorderLayout());
                B.setPreferredSize(new Dimension(250, 400));
                B.setBackground(Color.BLUE);
                
                b1.setBounds(90, 15, 80, 20);
                b2.setBounds(110, 50, 40, 20);
                //terminal.setBounds(10, 180, 230, 200);
                scrollPane.setBounds(5, 175, 240, 200);
                
                B.add(b1);
                B.add(b2);
                B.add(scrollPane);

                C = new JPanel();
                C.setLayout(null);
                C.setPreferredSize(new Dimension(250, 400));
                C.setBackground(Color.GREEN);
                c1.setBounds(98, 15, 50, 20);
                c2.setBounds(82, 50, 80, 20);
                c3.setBounds(96, 86, 55, 20);
                C.add(c1);
                C.add(c2);
                C.add(c3);

                frame.setLayout(new BorderLayout());
                frame.add(A, BorderLayout.WEST);
                frame.add(B, BorderLayout.CENTER);
                frame.add(C, BorderLayout.EAST);
                frame.pack();
                frame.setLocationByPlatform(true);
                frame.setVisible(true);
            }

            public synchronized void setGame(){
                if(limit < 5){
                    //No start with no level
                    terminal.append("Select Difficulty!\n");
                    terminal.setCaretPosition(terminal.getDocument().getLength());
                }
                else{
                    for(int c = 0; c <10; c++){
                        JPanel panel = plist.get(c);//Converts each index number to panel with corresponding
                        panel.setBackground(Color.RED);
                    }
                    Random bumbno = new Random(); 
                    bumb = bumbno.nextInt(10);
                    for(int a = 0; a <10; a++){
                        if(a == bumb){
                            blist.add(true);
                        }
                        else{
                            blist.add(false);
                        }
                    }
                }
            }

            public static Game getInstance(){
                return singleton;
            }

        }

        public static void main(String args[]) throws Exception {
            //In order to prevent multiple windows poping up when play button was pressed, Singleton designing was used
            Game singleton = Game.getInstance();
        }
    }