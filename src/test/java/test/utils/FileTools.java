package test.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Convenience methods for file & I/O operations
 */
public class FileTools {


    public static String openClasspathFile(String filename) {

        Resource resource = new ClassPathResource(filename);
        String content = "";

        try{
            InputStream is = resource.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            while (true) {
                String line = reader.readLine();
                if (line == null)
                    break;
                content += line + '\n';
            }
            reader.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return content;
    }



}
