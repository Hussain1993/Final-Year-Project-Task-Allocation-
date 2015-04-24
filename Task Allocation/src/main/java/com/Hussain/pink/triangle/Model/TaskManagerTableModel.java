package com.Hussain.pink.triangle.Model;

import javax.swing.table.DefaultTableModel;
import java.sql.Date;

/**
 * Created by Hussain on 24/04/2015.
 */
public class TaskManagerTableModel extends DefaultTableModel {
    private static final int COMPLETED_COLUMN_INDEX = 5;

    public TaskManagerTableModel(){
        super(null, new Object[]{"ID","Task Name","Project ID","Date From","Date To", "Completed"});
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch(columnIndex)
        {
            case 0: return String.class;
            case 1: return String.class;
            case 2: return String.class;
            case 3: return Date.class;
            case 4: return Date.class;
            case 5: return Boolean.class;
            default: return String.class;
        }
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return column == COMPLETED_COLUMN_INDEX;
    }
}
