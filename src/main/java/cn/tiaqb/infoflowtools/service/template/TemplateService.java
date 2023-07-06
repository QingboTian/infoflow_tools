package cn.tiaqb.infoflowtools.service.template;

/**
 * @author tianqingbo_dxm
 * @date 2023/7/6 5:19 PM
 * @since 1.0
 */
public interface TemplateService {

    /**
     * 指定指定id查询模板
     * @param id 模板id
     * @return 模版
     */
    String queryTemplateById(String id);

}
