package com.Hussain.pink.triangle.Utils;
import org.junit.Test;

import java.net.URL;
import java.util.ArrayList;

public class TaskAllocationFileTest {

    @Test
    public void testParseTaskAllocationFile() throws Exception{
        URL url = getClass().getClassLoader().getResource("testAllocation.ta");
        ArrayList<String []> data = TaskAllocationFile.parseTaskAllocationFile(url.toURI().getPath());
        for (int i = 0; i < data.size(); i++) {
            String[] row = data.get(i);
            for (int j = 0; j < row.length; j++) {
                System.out.println(row[j]);
            }
            System.out.println();
        }
    }
}