package resumeupdater;

/**
 * 
 * @author SteshenkoMA
 */

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;

//Данный рендерер позволяет отображать в ячейке полностью весь текст

public class MyCellRenderer extends JTextArea implements TableCellRenderer {
     public MyCellRenderer() {
       setLineWrap(true);
     //  setWrapStyleWord(true);
    }

   public Component getTableCellRendererComponent(JTable table, Object
           value, boolean isSelected, boolean hasFocus, int row, int column) {
       setText((String) value);
       setSize(table.getColumnModel().getColumn(column).getWidth(),
               getPreferredSize().height);
       if (table.getRowHeight(row) != getPreferredSize().height) {
               table.setRowHeight(row, getPreferredSize().height);
       }
       return this;
   }
} 
