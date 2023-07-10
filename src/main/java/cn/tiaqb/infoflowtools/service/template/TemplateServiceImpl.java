package cn.tiaqb.infoflowtools.service.template;

import cn.tiaqb.infoflowtools.enums.MessageTemplate;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author tianqingbo_dxm
 * @date 2023/7/6 5:19 PM
 * @since 1.0
 */
@Service
@Slf4j
public class TemplateServiceImpl implements TemplateService {

    @Override
    public String queryTemplateById(String id) {
        MessageTemplate template = MessageTemplate.getById(id);
        if (template == null) {
            return null;
        }
        return template.getContent();
    }

    /**
     * temp method.
     * delete the method in the future, because the method is stupid.
     * I am a rookie
     * @return template map
     */
    @Deprecated
    private JSONObject readTemplateData() {
        InputStream is = null;
        BufferedReader reader = null;
        try {
            String file = "remind_template.json";
            is = this.getClass().getClassLoader().getResourceAsStream(file);
            if (is == null) {
                log.error("occur an error, get resource as stream is null, file = {}", file);
                return null;
            }
            StringBuilder contents = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(is));
            String text;
            while ((text = reader.readLine()) != null) {
                contents.append(text);
            }
            return JSONObject.parseObject(contents.toString());
        } catch (Exception ex) {
            log.error("occur an error for ");
        } finally {
            // final close stream
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    log.error("buffer reader close error");
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    log.error("input stream close error");
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        TemplateServiceImpl main = new TemplateServiceImpl();
        JSONObject jsonObject = main.readTemplateData();
        System.out.println(jsonObject);
    }

}
