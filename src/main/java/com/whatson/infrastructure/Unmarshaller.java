package com.whatson.infrastructure;

/**
 * Created by gawain on 22/10/2016.
 */
public interface Unmarshaller<T> {

    public T unmarshall(String xmlfile);
}
