package com.cube;
import java.io.*;

public class TechServer{
    static String question[] = new String[1000];
    static String answer[] = new String[1000];
    static String [] keyWords = new String[200];

    static FileWriter fileWriter;
    static BufferedWriter bufferWriter;

    static FileReader fileReader;
    static BufferedReader bufferReader;

    public static void initTechSupport(){
        try{
            /**
             * help
             * contact
             * error
             * warning
             * crash
             * network
             * product
             * detail
             */
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
            fileWriter = new FileWriter("./techsupport.txt");
            bufferWriter = new BufferedWriter(fileWriter);
            //bufferWriter.write("Hello This is TechSupport of Dodgy Company !\n");
            //help
            bufferWriter.write("This support would give you help ~\n");
            bufferWriter.write("Get help by this techsupport or contact us by 8888-6666~\n");
            bufferWriter.write("You could also get help by visiting out website www.dodgycompany.com\n");
            //contact
            bufferWriter.write("Get help by this techsupport or contact us by 8888-6666~\n");
            bufferWriter.write("You could also get help by visiting out website www.dodgycompany.com\n");
            bufferWriter.write("contact us by website or phone\n");
            //error
            bufferWriter.write("error could be cause by so many reasons 1. check your syntax 2. check the logout 3. If you still cannot handle the error , please contact us by 8888-6666 we would by no means help you!\n");
            bufferWriter.write("Please contact us by 8888-6666 we would by no means help you!\n");
            bufferWriter.write("You could set up a question at www.dodgyHelp.com\n");
            //warning
            bufferWriter.write("warning could be cause by so many reasons 1. check your syntax 2. check the logout 3. If you still cannot handle the error , please contact us by 8888-6666 we would by no means help you!\n");
            bufferWriter.write("Please contact us by 8888-6666 we would by no means help you!\n");
            bufferWriter.write("You could set up a question at www.dodgyHelp.com\n");
            //crash
            bufferWriter.write("crash could be cause by so many reasons. We suggest you visit www.dodgyHelp.com first.\n");
            bufferWriter.write("Please visit www.dodgyHelp.com to get the solution.");
            bufferWriter.write("www.dodgyHelp.com may contains the solution you need for the crash problem\n");
            //network
            bufferWriter.write("If you have network problem. Please visit www.dodgyHelp.com. Check your network first)\n");
            bufferWriter.write("1. check network first 2. check network signal 3. find similar problem at www.dodgyhelp.com\n");
            bufferWriter.write("Here are som suggestions for network problem 1. check network first 2. check network signal 3. find similar problem at www.dodgyhelp.com\n");
            //product
            bufferWriter.write("welcome to the offline store dodgy Store\n");
            bufferWriter.write("online product please visit dodgyProduct.com\n");
            bufferWriter.write("products : 1. software 2. interesting things 3. visit our website for more details\n");
            //detail
            bufferWriter.write("If you want more detail, visit our website www.dodgycompany.com\n");
            bufferWriter.write("www.dodgycompany.com contains more details\n");
            bufferWriter.write("details could be found at www.dodgycompany.com\n");

            bufferWriter.write("此系统暂未开通中文技术支持， 您可以联系 8888-6666 获得相应帮助\n");
            bufferWriter.write("需要帮助的话，可以联系 8888-6666\n");
            bufferWriter.write("帮助 ？ 看起来你需要中方技术人员的帮助~请联系8888-6666\n");

            bufferWriter.write("中文 ？ 看起来你需要中方技术人员的帮助~请联系8888-6666\n");
            bufferWriter.write("此系统暂未开通中文技术支持， 您可以联系 8888-6666\n");
            bufferWriter.write("中文技术支持，请联系8888-6666\n");
            bufferWriter.write("此系统暂未开通中文技术支持， 您可以联系 8888-6666\n");
            bufferWriter.write("中文技术支持，请联系8888-6666\n");

        }catch(Exception e){}
        finally{
            try{
                bufferWriter.flush();
                fileWriter.close();
                bufferWriter.close();
            }catch(Exception e){System.out.println(e.toString());}
        }
    }
    public static void getTechSupport(){
        try{
            File f = new File("./techsupport.txt");
            if(f.exists()==false){
                initTechSupport();
            }
            fileReader = new FileReader("./techsupport.txt");
            bufferReader = new BufferedReader(fileReader);
            String s = "";
            int i = 0;
            while((s = bufferReader.readLine()) != null){
                answer[i] = s;
                i++;
            }
        }catch(Exception e){System.err.println(e.toString());}

    }
}