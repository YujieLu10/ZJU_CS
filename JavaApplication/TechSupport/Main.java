package com.cube;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Random;

public class Main extends JFrame implements ActionListener{
    JButton btnSend,btnExit;
    JTextField textField;
    JTextArea textArea;
    JLabel label1,label2;
    JPanel panel1;
    static String [] keyWords = new String[20];
    public static void main(String[] args){
        keyWords[0] = "help";
        keyWords[1] = "contact";
        keyWords[2] = "error";
        keyWords[3] = "warning";
        keyWords[4] = "crash";
        keyWords[5] = "network";
        keyWords[6] = "product";
        keyWords[7] = "detail";
        keyWords[8] = "帮助";
        keyWords[9] = "中文";
        TechServer.getTechSupport();
        new Main();
    }
    public Main(){
        btnSend = new JButton("Send");
        btnExit = new JButton("Exit");
        textField = new JTextField(10);
        textArea = new JTextArea();
        label1 = new JLabel("This is Dodgy Company TechSupport");
        label2 = new JLabel("Please input words and press send :");

        panel1 = new JPanel();
        panel1.add(label2);panel1.add(textField);panel1.add(btnSend);panel1.add(btnExit);

        btnSend.addActionListener(this);
        btnSend.setActionCommand("go");

        btnExit.addActionListener(this);
        btnExit.setActionCommand("exit");

        textArea.setText("Hello This is TechSupport of Dodgy Company !\n"+"\nKeywords :\n             * help\n" +
                "             * contact\n" +
                "             * error\n" +
                "             * warning\n" +
                "             * crash\n" +
                "             * network\n" +
                "             * product\n" +
                "             * detail\n" +
                "             * 中文\n" +
                "             * 帮助\n");

        this.add(label1,BorderLayout.NORTH);
        this.add(textArea);
        this.add(panel1,BorderLayout.SOUTH);
        this.setTitle("Dodgy Company TechSupport");
        this.setSize(600,300);
        this.setLocation(200,200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("go")){
            String text = textField.getText();
            String texttest = getResponse();
            textArea.setText("Question ：\n" + text + "\n\nAnswer ：\n"+texttest);
            textField.setText("");
        }else if(e.getActionCommand().equals("exit")){
            System.exit(0);
        }
    }
    public String getResponse(){
        try{
            for(int i = 0; i < keyWords.length; i++){
                String text = textField.getText();
                if(text.indexOf(keyWords[i])!=-1){
                    Random randomAnswer = new Random();
                    //String clientQuestion = TechServer.question[ i * 3 + randomAnswer.nextInt(3)];
                    String supportAnswer = TechServer.answer[i * 3 + randomAnswer.nextInt(3)];
                    return supportAnswer;
                }
            }

        }catch(Exception ergg){
        }
        return "Your question is hard to answer.\nYou could contact us by 8888-6666.";
    }

}
