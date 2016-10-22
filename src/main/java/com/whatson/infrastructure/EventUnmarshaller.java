package com.whatson.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import javax.xml.transform.stream.StreamSource;
import java.io.FileInputStream;
import java.util.List;

/**
 * Created by gawain on 22/10/2016.
 */
public class EventUnmarshaller implements Unmarshaller<EventSearchResult> {

    @Override
    public EventSearchResult unmarshall(String xmlfile) {
        return convertFromXMLToObject(xmlfile);
    }

    @Autowired
    private Jaxb2Marshaller marshaller;

    public EventSearchResult convertFromXMLToObject(String xmlfile) throws RuntimeException {

        FileInputStream is = null;
        try {
            is = new FileInputStream(xmlfile);
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
