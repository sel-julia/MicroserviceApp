package org.resource.service;

import org.apache.tika.exception.TikaException;
import org.resource.entity.Resource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.List;

public interface ResourceService {

    Long save(byte[] bytes) throws TikaException, IOException, SAXException;
    Resource get(Long id);
    List<Long> delete(String ids);
    void deleteById(Long id);

}
