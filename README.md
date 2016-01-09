# ResumeUpdater

Данная программа позволяет автоматически обновлять резюме на сайте hh.ru  
Сама идея взята здесь:   
http://habrahabr.ru/sandbox/72648/

Подробное описание:

ResumeUpdater обновляет резюме на сайте hh.ru. Для этого посылается POST запрос, настройки которого берутся из кэша браузера. Эти данные считываются из файла с форматом .properties, и отображаются в виде таблицы. При изменении значений таблицы, также перезаписывается этот файл. Более того при внесении изменений в данный файл, таблица программы будет создана уже с новой информацией. Программа умеет обновлять несколько резюме одновременно. Для этого используются ExecutorService, Executors которые запускают несколько потоков на выполнение. В программе можно установить автоматическое обновление при следующем запуске. Запуск таймера и его отключение зависит от вкл/выкл соответствующего флажка. Для отслеживания изменения значений этого флажка используется паттерен Observer.

Для обновления резюме необходимо:    
1) добавить id Вашего резюме  
2) узнать данные POST запроса (более подробно по ссылке выше)  
Пример для Mozilla Firefox:  

![requestsetting](https://cloud.githubusercontent.com/assets/13558216/11936990/f69993c2-a82a-11e5-8b2a-ce79ca8b3cf4.PNG)

3) добавить их в таблицу, либо в файл PostRequest.properties

![resumeupdater](https://cloud.githubusercontent.com/assets/13558216/11936992/f9d3ee02-a82a-11e5-8277-e21fd92609cd.PNG)



This program allows you to automatically update resume on the website hh.ru  
The idea is taken from here (Russian only):  
http://habrahabr.ru/sandbox/72648/  

Detailed description:

ResumeUpdater updates the resume on the website hh.ru. For this POST request is sent, it settings are taken from the browser cache. This data is read from the file with the  .properties format and appear in the form of a table. If you change the values in the table, it will overwrite this file. Moreover when you make changes to the file, program table will be created with new information. The program can update multiple resumes simultaneously. For this purpose is uses, ExecutorService, Executors that launch multiple threads for execution. The program can be set to automatically update when you next start it. Starting the timer on and off depends on the on/off checkbox. To track value changes of this option patteren Observer is used.

To update summary you should (Example for Mozilla Firefox is shown above):  
1) add id of your resume  
2) learn data POST request (more at the link above)  
3) add them to a table, or in PostRequest.properties file  

