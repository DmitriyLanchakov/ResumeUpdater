package resumeupdater;

/**
 * 
 * @author SteshenkoMA
 */

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

//Данный класс включает/выключает таймер автообновления в зависимости от значения переменной
//autoupdate в GUI

public class Watcher extends JFrame implements Observer {
    
   AutoUpdateTimer autoupdate;
   
    @Override
    public void update(Observable o, Object arg) {
        if (arg.equals(true)) {
            
        autoupdate = new AutoUpdateTimer();
        autoupdate.startTask();
       
               }
        else{
            try{
                
            autoupdate.stopTask();
            
            }
        catch (Exception e){
             e.printStackTrace();
        };
        
        }
      // System.out.println(arg);
    }
}