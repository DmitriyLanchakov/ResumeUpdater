package resumeupdater;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/* 
   Данный класс создает файл ResumeList.txt (в нем хранятся id резюме),
   отвечает за чтение и запись данного файла

   This class creates the file ResumeList.txt (this stores id summary),
   responsible for reading and writing this file
*/

public class ResumeList {
    
   public static ArrayList <String> resumes = new ArrayList<String>();
  
   /*
      Данный метод считывает ArrayList из ResumeList.txt и присваивает его данные переменной resumes
     
      This method reads the ArrayList from ResumeList.txt and assigns it data to the resumes variable  
   */
   public static void read () {

            try {
                 
            File file = new File("ResumeList.txt");

            /*
               Если файл не создан, то создаем его
            
               If the file is not created, then create it
            */
            
            if (!file.exists()) {
               file.createNewFile();
            }

            FileInputStream fis = new FileInputStream(file.getAbsoluteFile());
            ObjectInputStream ois = new ObjectInputStream(fis);
            
            try {

            ArrayList<String> a   = (ArrayList<String>) ois.readObject();  
             
            resumes = a;
           
            } catch (ClassNotFoundException ex) {
                    
            }
            ois.close();
                 

           } catch (IOException e) {
            e.printStackTrace();
          }
   
 }

   /*
      Данный метод записывает переменную resumes в ResumeList.txt
   
      This method writes a resumes variable in ResumeList.txt
   */
   
   public static void write () {

            try {
                 
                 File file = new File("ResumeList.txt");

            if (!file.exists()) {
               file.createNewFile();
            }

            FileOutputStream  fos = new FileOutputStream(file.getAbsoluteFile());
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(resumes);
            oos.close();
                 

        } catch (IOException e) {
            e.printStackTrace();
        }


    } 

}

