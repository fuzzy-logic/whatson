package com.whatson.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * Created by gawain on 22/10/2016.
 */
public class EventUnmarshaller implements Unmarshaller<EventSearchResult> {

    @Override
    public EventSearchResult unmarshall(String xml) {
        try {
            InputStream is = new ByteArrayInputStream(xml.getBytes("UTF-8"));
            return convertFromXMLToObject(is);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public EventSearchResult unmarshall(InputStream xml) {
        return convertFromXMLToObject(xml);
    }


    @Autowired
    private Jaxb2Marshaller marshaller;

    public EventSearchResult convertFromXMLToObject(InputStream is) throws RuntimeException {


        try {
            EventSearchResult results = (EventSearchResult) marshaller.unmarshal(new StreamSource(is));
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
