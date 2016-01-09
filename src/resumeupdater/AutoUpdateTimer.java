package resumeupdater;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
   Данный клас отвечает за автоматическое обновление всех резюме по времени
   Используется http://crunchify.com/how-to-run-multiple-threads-concurrently-in-java-executorservice-approach/

   This class is responsible for automatically updating summary every n time period
   Used http://crunchify.com/how-to-run-multiple-threads-concurrently-in-java-executorservice-approach/
*/

public class AutoUpdateTimer {
    
 private Timer timer = new Timer();
    
 public void startTask() {
        timer.schedule(new PeriodicTask(), 0);
 }
 
 public void stopTask() {
        timer.cancel();
 }
    
 private class PeriodicTask extends TimerTask {
        @Override
        public void run() {
            
                int MYTHREADS = 30;  
                ExecutorService executor = Executors.newFixedThreadPool(MYTHREADS);  
    
                for(String resumeID: ResumeList.resumes){
		
      			Runnable worker = new ResumeUpdater(resumeID);
			executor.execute(worker);
                }
                executor.shutdown();
                
                /*
                   Повторить каждые 3600 секунд (1 час
                   
                   Repeat every 3600 seconds (1 hour)
                */

                timer.schedule(new PeriodicTask(), 3600 * 1000);
        }
        
    }
    

    
    
}
