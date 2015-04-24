package com.Hussain.pink.triangle.Model;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableModel;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by Hussain on 24/04/2015.
 */
public class TaskManagerTable extends JTable {
    private LinkedHashSet<Integer> rowsEdited;

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

    public Set<Integer> getRowsEdited(){
        return this.rowsEdited;
    }

    public void clearRowsEdited(){
        this.rowsEdited.clear();
    }
}
