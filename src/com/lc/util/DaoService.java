package com.lc.util;

import java.util.List;

public interface DaoService <E> {
    List <E> showAll();
    int addData(E object);
    int updateData(E object);
}
