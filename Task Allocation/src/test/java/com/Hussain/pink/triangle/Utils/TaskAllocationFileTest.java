package com.Hussain.pink.triangle.Utils;

import org.junit.Test;

import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

public class TaskAllocationFileTest {
    private String[] data = {"0","Hussain","t1"};

    @Test
    public void testParseTaskAllocationFile() throws Exception{
        URL url = getClass().getClassLoader().getResource("testAllocation.ta");
        ArrayList<String []> data = TaskAllocationFile.parseTaskAllocationFile(url.toURI().getPath());
        for (int i = 0; i < data.size(); i++) {
            String[] row = data.get(i);
            assertArrayEquals(this.data,row);
        }
    }


    @Test
    public void testSaveTaskAllocationFile() throws  Exception{
        DefaultTableModel model = new DefaultTableModel(null, new String [] {"ID","Name","Task"});
        model.insertRow(0,new String[]{"0","Hussain","t1"});

        URL url = getClass().getClassLoader().getResource("testAllocation.ta");
        File file = new File(url.toURI());

        assertTrue(TaskAllocationFile.saveTaskAllocationFile(file.getParent() + File.separator + "test.ta", model));

        ArrayList<String []> data = TaskAllocationFile.parseTaskAllocationFile(file.getParent() + File.separator + "test.ta");
        for (int i = 0; i < data.size(); i++) {
            String [] d = data.get(i);
            assertArrayEquals(this.data,d);
        }
    }
}