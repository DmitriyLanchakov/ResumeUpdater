package resumeupdater;

/**
 * 
 * @author SteshenkoMA
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Properties;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JCheckBox;

/*
   Данный класс отвечает за создание графического интерфейса программы

   This class is responsible for creating the graphical interface
*/
 
public class GUI extends JFrame {
    
    JCheckBox autoUpdateBox;
    JList list;
        
    public GUI() {
        
        super("ResumeUpdater");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        JPanel resumePanel = makeResumePanel();
        JPanel settingsPanel = makeSettingsPanel();
        JPanel updateMenuPanel = makeUpdateMenuPanel(); 
       
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(5, 5));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        mainPanel.add(resumePanel, BorderLayout.NORTH);
        mainPanel.add(settingsPanel, BorderLayout.CENTER);
        mainPanel.add(updateMenuPanel,BorderLayout.SOUTH);
        getContentPane().add(mainPanel);
        setPreferredSize(new Dimension(300, 500));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    /*
       Метод создающий resumePanel со списком резюме и кнопками добавления/удаления
       
       Method creates resumePanel with the resumes list and add/remove buttons
    */
 
    public JPanel makeResumePanel(){
        
     JPanel resumePanel = new JPanel();
     resumePanel.setLayout(new BorderLayout(5,5));

     JTextField resumeID = new JTextField();
     resumePanel.add(resumeID, BorderLayout.NORTH);
        
     DefaultListModel listModel = new DefaultListModel();
     
     /*
        Считываем список резюме из файла
        
        Read the list from summary file
     */
     
     ResumeList.read();
                
     for(int i=0; i<ResumeList.resumes.size(); i++) {
     listModel.addElement(ResumeList.resumes.get(i));
     }
        
     list = new JList(listModel);
     list.setFocusable(false);
     list.setVisibleRowCount(5);
     resumePanel.add(new JScrollPane(list), BorderLayout.CENTER);
 
     JPanel buttonsPanel = new JPanel();
     buttonsPanel.setLayout(new GridLayout(1, 2, 5, 0));
     resumePanel.add(buttonsPanel, BorderLayout.SOUTH);
 
     JButton addButton = new JButton("Добавить (Add)");
     addButton.setFocusable(false);
     addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                ResumeList.resumes.add(resumeID.getText());
                ResumeList.write();
                
                String element = resumeID.getText();
                listModel.addElement(element);
            
            }
        });
     buttonsPanel.add(addButton);
 
     JButton removeButton = new JButton("Удалить (Del)");
     removeButton.setFocusable(false);
     removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ResumeList.resumes.remove(list.getSelectedIndex());
                ResumeList.write();
                listModel.remove(list.getSelectedIndex());
            }
        });
     buttonsPanel.add(removeButton);
 
     list.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (list.getSelectedIndex() >= 0) {
                    removeButton.setEnabled(true);
                } else {
                    removeButton.setEnabled(false);
                }
            }
        });
 
     return resumePanel;
     
    } 
    
    /*
       Метод содающий settingsPanel с таблицей данных POST завпроса
       Method that creates settingsPanel the table data of the POST request
    */
    public JPanel makeSettingsPanel(){
    
     JPanel settingsPanel = new JPanel();
     settingsPanel.setLayout(new BorderLayout());
     
     DefaultTableModel model = new DefaultTableModel();
     
     model.addColumn("Field");
     model.addColumn("Data");
 
     /*
        Считываем данные POST запроса из файла
        
        Read POST request data from the file
     */
      
     Properties  RequestConfig = new Properties();
        try {
        RequestConfig.load(new FileInputStream("PostRequest.properties"));
        } catch (IOException e) {
        }
     
     /*
        Записыdаем полученные данные в HashMap hm   
        
        Write data in HashMap hm   
     */
        
     Map<String, String> hm = new HashMap<String, String>();
           for(String key : RequestConfig.stringPropertyNames()) {
           String value = RequestConfig.getProperty(key);
           hm.put(key,value);
     }
     
     /* 
        Добавляем данные hm в модель
            
        Add the data hm to the model
     */
           
     for (Map.Entry<String,String> entry : hm.entrySet()) {
        model.addRow(new String[] { entry.getKey(), entry.getValue() });
     }
    
    /* 
       Создаем таблицу
     
       Create a table
    */
     
        JTable table = new JTable(model)
    {
            public boolean getScrollableTracksViewportWidth()
            {
                return getPreferredSize().width < getParent().getWidth();
            }
        };
        table.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
        
        /*
           Используем рендерер MyCellRenderer и TableColumnAdjuster
        
           Use MyCellRenderer renderer and TableColumnAdjuster
        */
        
        table.getColumnModel().getColumn(1).setCellRenderer(new MyCellRenderer());
        TableColumnAdjuster tca = new TableColumnAdjuster(table);
        tca.adjustColumns();
        
        /*
           Данный Listener следит за изменениями вносимыми в таблицу и автоматически 
           перезаписывает файл PostRequest.properties новыми 
           
           This Listener watches for changes you make to a table and automatically 
           overwrites the file PostRequest.properties with new once
        */
        
        table.getModel().addTableModelListener(new TableModelListener() {
        public void tableChanged(TableModelEvent e) {
                     
        /*
           Считываем измененные данные из таблицы и добавляем их в HashMap changedData
            
           Read the modified data from table and add it to a changedData HashMap  
        */
            
        Map<String, String> changedData = new HashMap<String, String>();
        for(int i=0; i< table.getRowCount();i++){
        String key = (String)table.getModel().getValueAt(i, 0);
        String value = (String)table.getModel().getValueAt(i, 1);
        changedData.put(key,value);
        }
              
        // Заполняем переменную ChangedRequestConfig данными из changedData
        
        Properties  ChangedRequestConfig = new Properties();
        try {
        for (Map.Entry<String,String> entry : changedData.entrySet()) {
        ChangedRequestConfig.setProperty(entry.getKey(), entry.getValue());
        }
        
        //Перезаписываем файл PostRequest.properties
        
        File f = new File("PostRequest.properties");
        OutputStream out = new FileOutputStream( f );
        ChangedRequestConfig.store(out,"");
        }
        catch (Exception ex ) {
        ex.printStackTrace();
        }
           }
               
        });
                
        JScrollPane scrollPane = new JScrollPane( table );
        getContentPane().add( scrollPane );
        settingsPanel.add(scrollPane,BorderLayout.CENTER);
        
        return settingsPanel;
       }
    
    //Метод создающий updateMenuPanel c кнопками обновления и autoupdate Combobox
    
    public JPanel makeUpdateMenuPanel(){
    
        JPanel updateMenuPanel = new JPanel();
        updateMenuPanel.setLayout(new GridBagLayout());
       
        JButton updateSelectedButton = new JButton("Обновить (Refresh)");   
        updateSelectedButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
               String resumeID = (String)list.getSelectedValue();
               Runnable worker = new ResumeUpdater(resumeID);
               Thread t = new Thread(worker);
               t.start();
            }
        });
        
        JButton updateAllButton = new JButton("Обновить все (Refresh All)");
        updateAllButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                int MYTHREADS = 30;  
                ExecutorService executor = Executors.newFixedThreadPool(MYTHREADS);  
    
                for(String resumeID: ResumeList.resumes){
		
      			Runnable worker = new ResumeUpdater(resumeID);
			executor.execute(worker);
                }
                executor.shutdown();
            }
        });
    
        //Считываем данные из config.properties. В нем содержатся настройки autoupdate Combobox
        
        Properties  config = new Properties();
        try {
        config.load(new FileInputStream("config.properties"));
        } catch (IOException e) {
        }
        
        //Присваеваем переменной flag значение true/false из переменной config
        
        Boolean flag = null;
        for(String key : config.stringPropertyNames()) {
        String value = config.getProperty(key);
        flag = Boolean.valueOf(value);        
        }
                
        autoUpdateBox = new JCheckBox("Автообновление (Autoupdate)");
        
        //Данный Listener перезаписывает файл config.properties новым значением переменной autoudate (true/false)
        //А также оповещает myObservable о каждом ее изменении
        
        autoUpdateBox.addItemListener(new ItemListener() {
         public void itemStateChanged(ItemEvent e) {
             if(e.getStateChange() == ItemEvent.SELECTED) {//автообновление было включено
        try {
        config.setProperty("autoUpdate", "true");
        File f = new File("config.properties");
        OutputStream out = new FileOutputStream( f );
        config.store(out,"");
        
        myObservable.notifyObservers(autoUpdateBox.isSelected());
        }
             
        catch (Exception ex ) {
        ex.printStackTrace();
        }
        } else {//автообновление было выключено
             try {
        config.setProperty("autoUpdate", "false");
        File f = new File("config.properties");
        OutputStream out = new FileOutputStream( f );
        config.store(out,"");
        
        myObservable.notifyObservers(autoUpdateBox.isSelected());
        }
        catch (Exception ex ) {
        ex.printStackTrace();
        }
        };
      
    }
        });
        
        //Выставляем флажок автообноления в зависимости отзначения flag
        
        autoUpdateBox.setSelected(flag); 
              
        GridBagConstraints c;  
        c = new GridBagConstraints();
        c.insets = new Insets(0, 0, 0, 0);
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.1;
        updateMenuPanel.add(autoUpdateBox, c);
       
        c = new GridBagConstraints();
        c.insets = new Insets(0, 0, 0, 5);
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0.1;
        updateMenuPanel.add(updateSelectedButton, c);
        
        c = new GridBagConstraints();
        c.insets = new Insets(0, 0, 0, 0);
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 1;
        c.gridy = 1;
        c.weightx = 0.1;
        updateMenuPanel.add(updateAllButton, c);
        
    return updateMenuPanel;}
    
    //Далее используется паттерн Наблюдатель. Подробнее см. класс Watcher и Main
    
    Observable myObservable = new Observable()
    {
        public void notifyObservers(Object arg) {
            setChanged();
            super.notifyObservers(arg);
        }
    };
   
    public  void addObserver(Observer o)
    {
           myObservable.addObserver(o);
           myObservable.notifyObservers(autoUpdateBox.isSelected());
    }
   
    }
    
 
      
   

