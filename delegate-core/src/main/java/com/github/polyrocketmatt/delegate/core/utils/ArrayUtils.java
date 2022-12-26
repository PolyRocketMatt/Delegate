package com.github.polyrocketmatt.delegate.core.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ArrayUtils {

    @SuppressWarnings("unchecked")
    public static <S, T extends S, K extends S> S[] combine(List<T> pTypeList, List<K> sTypeList) {
        ArrayList<S> combinedList = new ArrayList<>();

        combinedList.addAll(pTypeList);
        combinedList.addAll(sTypeList);

        return (S[]) combinedList.toArray(Object[]::new);
    }

}
