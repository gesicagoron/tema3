package Presentation;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Field;
import java.util.List;

public class Table {

    public static <T> JTable createTable(List<T> list, Class<T> type) {
        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);

        // add columns (fields of the class)
        for (Field field : type.getDeclaredFields()) {
            model.addColumn(field.getName());
        }

        // add rows (objects in the list)
        for (T obj : list) {
            Object[] row = new Object[type.getDeclaredFields().length];
            int i = 0;
            for (Field field : type.getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    row[i++] = field.get(obj);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            model.addRow(row);
        }

        return table;
    }
}

