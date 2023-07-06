package cn.tiaqb.infoflowtools.service.template;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
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
        JSONObject data = readTemplateData();
        if (data == null) {
            return null;
        }
        return data.getString(id);
    }

    private JSONObject readTemplateData() {
        try {
            String file = "remind_template.json";
            InputStream is = this.getClass().getClassLoader().getResourceAsStream(file);
            if (is == null) {
                log.error("occur an error, get resource as stream is null, file = {}", file);
                return null;
            }
            StringBuilder contents = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String text;
            while ((text = reader.readLine()) != null) {
                contents.append(text);
            }
            return JSONObject.parseObject(contents.toString());
        } catch (Exception ex) {
            log.error("occur an error for ");
        }
        return null;
    }

    public static void main(String[] args) {
        TemplateServiceImpl main = new TemplateServiceImpl();
        JSONObject jsonObject = main.readTemplateData();
        System.out.println(jsonObject);
    }

}
