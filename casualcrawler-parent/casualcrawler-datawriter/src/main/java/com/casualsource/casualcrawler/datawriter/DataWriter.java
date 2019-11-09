package com.casualsource.casualcrawler.datawriter;

import java.util.List;

public interface DataWriter<T> {
    void createStorage(Class<? extends Object> clazz);

    int[] writeData(List<T> entityList);
}
