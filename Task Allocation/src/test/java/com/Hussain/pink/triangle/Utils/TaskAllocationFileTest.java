package com.Hussain.pink.triangle.Utils;

import org.junit.Test;

import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

public class TaskAllocationFileTest {
    private Object[] data = {"0","Hussain","t1","012",false};

    @Test
    public void testParseTaskAllocationFile() throws Exception{
        URL url = getClass().getClassLoader().getResource("testAllocation.ta");
        ArrayList<Object []> data = TaskAllocationFile.parseTaskAllocationFile(url.toURI().getPath());
        for (int i = 0; i < data.size(); i++) {
            Object[] row = data.get(i);
            assertArrayEquals(this.data,row);
        }
    }


    @Test
    public void testSaveTaskAllocationFile() throws  Exception{
        DefaultTableModel model = new DefaultTableModel(null, new String [] {"ID","Name","Task","TaskID","Assign"});
        model.insertRow(0,new Object[]{"0","Hussain","t1","012",false});

        URL url = getClass().getClassLoader().getResource("testAllocation.ta");
        File file = new File(url.toURI());

        assertTrue(TaskAllocationFile.saveTaskAllocationFile(file.getParent() + File.pathSeparator + "test.ta", model));

        ArrayList<Object []> data = TaskAllocationFile.parseTaskAllocationFile(file.getParent() + File.pathSeparator + "test.ta");
        for (int i = 0; i < data.size(); i++) {
            Object [] d = data.get(i);
            assertArrayEquals(this.data,d);
        }
    }
}