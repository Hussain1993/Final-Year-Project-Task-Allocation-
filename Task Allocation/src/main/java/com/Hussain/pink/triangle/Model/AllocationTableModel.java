package com.Hussain.pink.triangle.Model;

import javax.swing.table.DefaultTableModel;

/**
 * Created by Hussain on 13/11/2014.
 */
public class AllocationTableModel extends DefaultTableModel {
    private static final int ASSIGN_TASK_COLUMN_INDEX = 4;

    public AllocationTableModel(Object[][] data, Object[] columnNames) {
        super(data, columnNames);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return String.class;
            case 1:
                return String.class;
            case 2:
                return String.class;
            case 3:
                return String.class;
            default:
                return Boolean.class;
        }
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        if(column == ASSIGN_TASK_COLUMN_INDEX)
        {
            return true;
        }
        return false;
    }
}
