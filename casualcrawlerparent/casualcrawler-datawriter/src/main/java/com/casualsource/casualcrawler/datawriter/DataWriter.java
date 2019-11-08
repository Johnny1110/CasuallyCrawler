package com.casualsource.casualcrawler.datawriter;

import java.util.List;

public interface DataWriter<T> {
    void createTable(List<MetaData> metaDataList);
}
