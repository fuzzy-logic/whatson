package com.whatson.infrastructure;

import com.whatson.domain.EventSearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Created by gawain on 22/10/2016.
 */
public class EventUnmarshaller<T> implements Unmarshaller<T> {

    @Override
    public T unmarshall(String xml) {
        try {
            InputStream is = new ByteArrayInputStream(xml.getBytes("UTF-8"));
            return convertFromXMLToObject(is);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public T unmarshall(InputStream xml) {
        return convertFromXMLToObject(xml);
    }


    @Autowired
    private Jaxb2Marshaller marshaller;

    public T convertFromXMLToObject(InputStream is) throws RuntimeException {


        try {
            T results = (T) marshaller.unmarshal(new StreamSource(is));
            return results;
        } catch(Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e2) {
                    throw new RuntimeException(e2);
                }
            }
        }
    }
}
