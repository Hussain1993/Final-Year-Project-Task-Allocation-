package com.Hussain.pink.triangle.Model;

import javax.swing.table.DefaultTableModel;

/**
 * This is the table model for the Allocation table that will be
 * presented to the user, this class extends the DefaultTableModel
 *
 * Created by Hussain on 13/11/2014.
 */
public class AllocationTableModel extends DefaultTableModel {
    private static final int ASSIGN_TASK_COLUMN_INDEX = 4;//This is the only column that the user will be able change

    public AllocationTableModel(){
        //Populate an empty table but has 5 columns and no rows
        super(null,new Object [] {"ID","Employee Name","Allocated Task","Task ID","Assign"});
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
        return column == ASSIGN_TASK_COLUMN_INDEX;
    }
}
