package cn.tiaqb.infoflowtools.controller.index;

import cn.tiaqb.infoflowtools.common.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tianqingbo_dxm
 * @date 2023/6/21 5:01 PM
 * @since 1.0
 */
@RestController
@RequestMapping("/infoflow/index")
public class IndexController {

    @GetMapping
    public Response<String> index() {
        return Response.ok("welcome to infoflow server");
    }

}
