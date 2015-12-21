package resumeupdater;

/**
 *
 * @author BigMax
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

// Данный класс создает файл ResumeList.txt (в нем хранятся id резюме),
// отвечает за чтение и запись данного файла

public class ResumeList {
    
   public static ArrayList <String> resumes = new ArrayList<String>();
  
   // Данный метод считывает ArrayList из ResumeList.txt и присваивает его данные переменной resumes

   public static void read () {

            try {
                 
            File file = new File("ResumeList.txt");

            // Если файл не создан, то создаем его

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

   // Данный метод записывает переменную resumes в ResumeList.txt
   
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

