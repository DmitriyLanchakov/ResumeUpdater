package resumeupdater;

/**
 * 
 * @author SteshenkoMA
 */

//Идея взята здесь: //http://habrahabr.ru/sandbox/72648/

public class Main {
    public static void main(String[] args) throws Exception {
             
        
        GUI f = new GUI();
        
        //guiWatcher будет следить за изменениями в переменной autoupdate в классе GUI
        
        Watcher guiWatcher = new Watcher();
        f.addObserver(guiWatcher);
        
         
   
}
}