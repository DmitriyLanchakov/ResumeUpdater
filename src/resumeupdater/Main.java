package resumeupdater;

/*
   Стешенко Максим (Steshenko Maxim)
   https://github.com/SteshenkoMA
   bigmax-91@mail.ru
*/

/*
   Идея взята здесь: http://habrahabr.ru/sandbox/72648/

   Idea is taken from here (Russian only): http://habrahabr.ru/sandbox/72648/
*/

public class Main {
    public static void main(String[] args) throws Exception {
             
        
        GUI f = new GUI();
        
        /*
           guiWatcher будет следить за изменениями в переменной autoupdate в классе GUI
           
           guiWatcher will monitor the changes in the autoupdate variable in the GUI class
        */
        
        Watcher guiWatcher = new Watcher();
        f.addObserver(guiWatcher);
        
         
   
}
}