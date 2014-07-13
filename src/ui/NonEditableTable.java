package ui;

import javax.swing.JTable;

/**
 *
 * @author AMILA
 */

public class NonEditableTable extends JTable{

    NonEditableTable(Object[][] rowData, String[] columnNames) 
    {
        super(rowData, columnNames);
    }
    @Override
    public boolean isCellEditable(int row, int column) 
    {
       return false;
    }
}
