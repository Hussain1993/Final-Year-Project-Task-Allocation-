package com.Hussain.pink.triangle.Model;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableModel;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * This is the table for the task manager,
 * this class extends JTable because we would
 * like to record when the user
 * has changed a value in the table
 *
 * Created by Hussain on 24/04/2015.
 */
public class TaskManagerTable extends JTable {
    private LinkedHashSet<Integer> rowsEdited;//Set containing the rows that have been edited

    public TaskManagerTable(TableModel tableModel){
        super(tableModel);
        rowsEdited = new LinkedHashSet<>();
    }

    @Override
    public void editingStopped(ChangeEvent e) {
        TableCellEditor editor = getCellEditor();
        if (editor != null) {
            Object value = editor.getCellEditorValue();
            setValueAt(value, editingRow, editingColumn);
            if(rowsEdited.contains(editingRow))
            {
                rowsEdited.remove(editingRow);
            }
            else
            {
                rowsEdited.add(editingRow);
            }
            removeEditor();
        }
    }

    /**
     *
     * @return Returns the set of rows that have been changed
     */
    public Set<Integer> getRowsEdited(){
        return this.rowsEdited;
    }

    /**
     * Clear this set once the batch has been executed
     */
    public void clearRowsEdited(){
        this.rowsEdited.clear();
    }
}
