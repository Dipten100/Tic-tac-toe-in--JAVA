package com.MyGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class MyGame extends JFrame implements ActionListener {

    JLabel heading,clockLabel;
    Font font=new Font("",Font.BOLD,40);
    JPanel mainPanel;
    JButton[] btns=new JButton[9];
    int[] gameChance={2,2,2,2,2,2,2,2,2};
    int activePlayer=1;

    int wps[][]={
            {0,1,2},
            {3,4,5},
            {6,7,8},
            {0,3,6},
            {1,4,7},
            {2,5,8},
            {0,4,8},
            {2,4,6}
    };
    int Winner=2;
    MyGame(){
        setTitle("Tic Tac Toe");
        setSize(850,850);
        ImageIcon icon=new ImageIcon("src/img/icon.png");
        setIconImage(icon.getImage());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createGUI();
        setVisible(true);
    }
    private void createGUI(){
        this.setLayout(new BorderLayout());
//        North heading....
        heading=new JLabel("Tic Tac Toe");
        heading.setFont(font);
        heading.setHorizontalAlignment(SwingConstants.CENTER);

        this.add(heading,BorderLayout.NORTH);

//        South Clock...
        clockLabel=new JLabel("Clock");

        clockLabel.setFont(font);
        clockLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(clockLabel,BorderLayout.SOUTH);

        Thread t= new Thread(() -> {
            try{
                while (true){
                    String dateTime= new Date().toString();
                    clockLabel.setText(dateTime);

                    Thread.sleep(1000);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        });

        t.start();

//       Panel.....

        mainPanel=new JPanel();
        mainPanel.setLayout(new GridLayout(3,3));

        for (int i=1;i<=9;i++){
            JButton btn=new JButton();
            btn.setFont(font);
            mainPanel.add(btn);
            btns[i-1]=btn;
            btn.addActionListener(this);
            btn.setName(String.valueOf(i-1));
        }

        this.add(mainPanel,BorderLayout.CENTER);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton currentButton=(JButton) e.getSource();
        String nameStr=currentButton.getName();

        int name=Integer.parseInt(nameStr.trim());
        if (gameChance[name]==2){
            if (activePlayer==1){
                currentButton.setIcon(new ImageIcon("src/img/X.png"));
                gameChance[name]=activePlayer;
                activePlayer=0;
            }else{
                currentButton.setIcon(new ImageIcon("src/img/zero.png"));
                gameChance[name]=activePlayer;
                activePlayer=1;
            }
        }else {
            JOptionPane.showMessageDialog(this,"Position already occupied...");
        }

//        Find Winner

        for (int[] temp:wps) {
            if (gameChance[temp[0]]==gameChance[temp[1]] && gameChance[temp[0]]==gameChance[temp[2]] && gameChance[temp[0]]!=2){
                Winner=gameChance[temp[0]];
                JOptionPane.showMessageDialog(null,"Player "+Winner+" has won the game...");
                int i=JOptionPane.showConfirmDialog(this,"do you want to play more ??");
                if (i==0){
                    this.setVisible(false);
                    new MyGame();
                } else {
                    System.exit(2332);
                }
                break;
            }
        }

//        Draw Logic

        int f=0;
        for (int i=0;i<9;i++){
            if(gameChance[i]==2){
                f++;
            }
        }
        if (f==0){
            JOptionPane.showMessageDialog(null,"Draw....");
            int i=JOptionPane.showConfirmDialog(this,"do you want to play more ??");
            if (i==0){
                this.setVisible(false);
                new MyGame();
            } else {
                System.exit(2332);
            }
        }
    }
}
