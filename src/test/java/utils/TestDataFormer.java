package utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class TestDataFormer {

    private final List<Item> list = new ArrayList<>();

    public TestDataFormer append(Object right, Object wrong){
        list.add(new Item(right, wrong));
        return this;
    }

    public Object[][] form(){
        int size = list.size();
        int columnSize = 1 << size;
        Object[][] objects = new Object[columnSize][size + 1];
        for (int i = 0; i < columnSize; i++) {
            for (int j = 0; j < size; j++) {
                objects[i][j] = i % (2 << j) == 0 ? list.get(j).getRight() : list.get(j).getWrong();
            }
            objects[i][size] = i == 0;
        }

        return objects;
    }

    @AllArgsConstructor
    @Getter
    private static class Item{
        private final Object right;
        private final Object wrong;
    }
}
