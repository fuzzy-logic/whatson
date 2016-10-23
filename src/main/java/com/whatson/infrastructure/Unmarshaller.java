package com.whatson.infrastructure;

import java.io.InputStream;

/**
 * Created by gawain on 22/10/2016.
 */
public interface Unmarshaller<T> {

    public T unmarshall(String xml);

    public T unmarshall(InputStream xml);
}
