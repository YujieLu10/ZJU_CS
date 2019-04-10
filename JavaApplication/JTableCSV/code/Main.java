package com.cube;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.AbstractTableModel;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import com.cube.CSVUtils;

public class Main
{
    static ArrayList<ArrayList> arraylist = new ArrayList<ArrayList>();
    int cnumber;
    JFrame jFrame = new JFrame();
    MyTable mytable = new MyTable();
    JTextField jTextField = new JTextField();

    private JButton jbtopen = new JButton("OpenCsv");
    private JButton jbtsave = new JButton("SaveCsv");
    private JPanel jPanel = new JPanel();
    public Object[] options = {"OK"};
    public ArrayList<String> list = new ArrayList<String>();
    public String file = "";
    static JTable jTable = null;
    static JScrollPane jScrollPane = new JScrollPane(jTable);
    public JMenuBar menuBar = new JMenuBar();
    public JMenu fileMenu = new JMenu("Menu");
    JMenuItem openFile = new JMenuItem("Open");
    JMenuItem saveFile = new JMenuItem("Save");
    public Main(){

        menuBar.add(fileMenu);

        fileMenu.add(openFile);

        fileMenu.add(saveFile);
        /**
         * open file
         */
        openFile.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JFileChooser filechooser = new JFileChooser();
                        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                                "CSV Files", "csv","CSV","cad","jpg", "gif", "png");
                        filechooser.setFileFilter(filter);
                        int returnVal = filechooser.showOpenDialog(null);

                        if (returnVal == JFileChooser.APPROVE_OPTION) {
                            file = filechooser.getSelectedFile().getAbsolutePath();
                        }
                        //importCsv();
                        try{
                            //File csv =new File("/Users/c-ten/Desktop/JAD3/test.csv");
                            arraylist = new ArrayList<ArrayList>();
                            //File csv = new File(jTextField.getText());
                            File csv = new File(file);
                            BufferedReader br = new BufferedReader(new FileReader(csv));
                            String line = "";
                            while((line=br.readLine()) != null)
                            {
                                //System.out.println(line);
                                line = line.replace(";",",");
                                list = new ArrayList<String>();
                                StringTokenizer st = new StringTokenizer(line, ",");
                                while(st.hasMoreTokens()){
                                    list.add(st.nextToken());

                                }
                                arraylist.add(list);
                                cnumber = list.size();
                            }
                            br.close();
                        }
                        catch(IOException e1){
                            e1.printStackTrace();
                        }
                        //mt = new MyTable();
                        jFrame.getContentPane().add(menuBar, BorderLayout.NORTH);    //容器上方放置菜单栏
//                        jPanel.add(jbtopen, BorderLayout.WEST);
//                        jPanel.add(jbtsave, BorderLayout.EAST);
                        if(jTable == null) {
                            jTable = new JTable(mytable);
                            jScrollPane = new JScrollPane(jTable);
                        }
                        else
                            jTable.repaint();
                        jTable.setPreferredScrollableViewportSize(new Dimension(550, 200));
                        //jTable.setVisible(true);
                        //JScrollPane jScrollPane = new JScrollPane(jTable);
                        //jFrame.getContentPane().add(jTextField, BorderLayout.CENTER);
                        //jFrame.getContentPane().add(jPanel, BorderLayout.SOUTH);
                        jFrame.getContentPane().add(jScrollPane, BorderLayout.CENTER);
                        jFrame.setTitle("JAD3");
                        jFrame.pack();
                        jFrame.setVisible(true);

                    }
                }
        );
        /**
         * save file
         */
        saveFile.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JOptionPane.showOptionDialog(null, "The CSV Saved Successfully.\n(It only save the change after you get out the edit mode of the table)","Message",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                                null,options,options[0]);

                    }
                }
        );

        jFrame.getContentPane().add(menuBar, BorderLayout.NORTH);    //容器上方放置菜单栏

//        jPanel.add(jbtopen, BorderLayout.WEST);
//        jPanel.add(jbtsave, BorderLayout.EAST);
        //t=new JTable(mt);
        //t.setPreferredScrollableViewportSize(new Dimension(550, 200));
        JScrollPane jScrollPane = new JScrollPane(jTable);
        //jFrame.getContentPane().add(jTextField, BorderLayout.CENTER);
        //jFrame.getContentPane().add(jPanel, BorderLayout.SOUTH);
        jFrame.getContentPane().add(jScrollPane, BorderLayout.CENTER);
        jFrame.setTitle("JAD3");
        jFrame.pack();
        jFrame.setVisible(true);

        ActionListener listener = new MyListener();
        jbtopen.addActionListener(listener);

        ActionListener savelistener = new saveListener();
        jbtsave.addActionListener(savelistener);

        jFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        mytable.addTableModelListener( new TableModelListener(){
            public void tableChanged(TableModelEvent e)
            {
                int row = e.getFirstRow();
                int col = e.getColumn();

                mytable.mysetValueAt(mytable.getValueAt(row,col),row,col);
                jTable.repaint();
                try{
                    int j,k;
                    BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                    //System.out.println(jTextField.getText());
                    for(j = 0; j < arraylist.size(); j++){
                        String s = new String();
                        s = (String) arraylist.get(j).get(0);
                        for(k = 1; k < arraylist.get(j).size(); k++){
                            s = s + "," + arraylist.get(j).get(k);
                        }
                        bw.write(s.toString(),0,s.toString().length());
                        bw.newLine();
                    }
                    bw.close();
                }catch(IOException p){
                    p.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        new Main();


    }

    class MyTable extends AbstractTableModel{
        public int getColumnCount(){
            return cnumber;
        }
        public int getRowCount()
        {
            return arraylist.size();
        }
        public Object getValueAt(int row, int col)  //necessary
        {
            return arraylist.get(row).get(col);
        }
        public boolean isCellEditable(int rowIndex, int columnIndex)
        {
            return true;
        }
        public void setValueAt(Object value, int row, int col)
        {
            arraylist.get(row).set(col,(String) value);
            fireTableCellUpdated(row, col);
        }
        public void mysetValueAt(Object value, int row, int col)
        {
            arraylist.get(row).set(col,(String) value);
        }
    }

    class MyListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            //importCsv();
            try{
                //File csv =new File("/Users/c-ten/Desktop/JAD3/test.csv");
                arraylist = new ArrayList<ArrayList>();
                File csv = new File(jTextField.getText());
                BufferedReader br = new BufferedReader(new FileReader(csv));
                String line = "";
                while((line=br.readLine()) != null)
                {
                    //System.out.println(line);
                    line = line.replace(";",",");
                    list = new ArrayList<String>();
                    StringTokenizer st = new StringTokenizer(line, ",");
                    while(st.hasMoreTokens()){
                        list.add(st.nextToken());

                    }
                    arraylist.add(list);
                    cnumber = list.size();
                }
                br.close();
            }
            catch(IOException e){
                e.printStackTrace();
            }
            JMenuBar menuBar = new JMenuBar();
            JMenu fileMenu = new JMenu("Menu");
            menuBar.add(fileMenu);
            JMenuItem openFile = new JMenuItem("Open");
            fileMenu.add(openFile);
            JMenuItem saveFile = new JMenuItem("Save");
            fileMenu.add(saveFile);
            /**
             * open file
             */
            openFile.addActionListener(
                    new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JFileChooser filechooser = new JFileChooser();
                            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                                    "CSV Files", "csv","CSV","cad","jpg", "gif", "png");
                            filechooser.setFileFilter(filter);
                            int returnVal = filechooser.showOpenDialog(null);

                            if (returnVal == JFileChooser.APPROVE_OPTION) {
                                file = filechooser.getSelectedFile().getAbsolutePath();
                            }
                            //importCsv();
                            try{
                                //File csv =new File("/Users/c-ten/Desktop/JAD3/test.csv");
                                arraylist = new ArrayList<ArrayList>();
                                //File csv = new File(jTextField.getText());
                                File csv = new File(file);
                                BufferedReader br = new BufferedReader(new FileReader(csv));
                                String line = "";
                                while((line=br.readLine()) != null)
                                {
                                    //System.out.println(line);
                                    line = line.replace(";",",");
                                    list = new ArrayList<String>();
                                    StringTokenizer st = new StringTokenizer(line, ",");
                                    while(st.hasMoreTokens()){
                                        list.add(st.nextToken());

                                    }
                                    arraylist.add(list);
                                    cnumber = list.size();
                                }
                                br.close();
                            }
                            catch(IOException e1){
                                e1.printStackTrace();
                            }
                            //mt = new MyTable();
                            jFrame.getContentPane().add(menuBar, BorderLayout.NORTH);    //容器上方放置菜单栏
//                            jPanel.add(jbtopen, BorderLayout.WEST);
//                            jPanel.add(jbtsave, BorderLayout.EAST);
                            if(jTable == null)
                                jTable = new JTable(mytable);
                            else
                                jTable.repaint();
                            jTable.setPreferredScrollableViewportSize(new Dimension(550, 200));
                            JScrollPane jScrollPane = new JScrollPane(jTable);
                            //jFrame.getContentPane().add(jTextField, BorderLayout.CENTER);
                            //jFrame.getContentPane().add(jPanel, BorderLayout.SOUTH);
                            jFrame.getContentPane().add(jScrollPane, BorderLayout.CENTER);
                            jFrame.setTitle("JAD3");
                            jFrame.pack();
                            jFrame.setVisible(true);

                        }
                    }
            );
            /**
             * save file
             */
            saveFile.addActionListener(
                    new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JOptionPane.showOptionDialog(null, "The CSV Saved Successfully.\n(It only save the change after you get out the edit mode of the table)","Message",
                                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                                    null,options,options[0]);

                        }
                    }
            );

            jFrame.getContentPane().add(menuBar, BorderLayout.NORTH);    //容器上方放置菜单栏
            //mt = new MyTable();
//            jPanel.add(jbtopen, BorderLayout.WEST);
//            jPanel.add(jbtsave, BorderLayout.EAST);
            if(jTable == null)
                jTable = new JTable(mytable);
            else
                jTable.repaint();
            jTable.setPreferredScrollableViewportSize(new Dimension(550, 200));
            JScrollPane jScrollPane = new JScrollPane(jTable);
            //jFrame.getContentPane().add(jTextField, BorderLayout.CENTER);
            //jFrame.getContentPane().add(jPanel, BorderLayout.SOUTH);
            jFrame.getContentPane().add(jScrollPane, BorderLayout.CENTER);
            jFrame.setTitle("JAD3");
            jFrame.pack();
            jFrame.setVisible(true);
        }
    }

    class saveListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
//            importCsv();
//            exportCsv();
            JOptionPane.showOptionDialog(null, "The CSV Saved Successfully.\n(It only save the change after you get out the edit mode of the table)","Message",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null,options,options[0]);
        }
    }

    public void exportCsv() {
        List<String> dataList=new ArrayList<String>();
        boolean isSuccess=CSVUtils.exportCsv(new File(jTextField.getText()), dataList);
        //System.out.println(isSuccess);
    }

    public void importCsv()  {
        List<String> dataList=CSVUtils.importCsv(new File(jTextField.getText()));
        if(dataList!=null && !dataList.isEmpty()){
            for(String data : dataList){
                //System.out.println(data);
            }
        }
    }

}





