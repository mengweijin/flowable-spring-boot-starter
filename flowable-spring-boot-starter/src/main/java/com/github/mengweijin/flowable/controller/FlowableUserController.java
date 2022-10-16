package com.github.mengweijin.flowable.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.github.mengweijin.flowable.domain.UserQueryParams;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author mengweijin
 * @date 2022/10/16
 */
@Controller
public class FlowableUserController extends AbstractFlowableController {

    @GetMapping(value = "/user/list")
    public String getUserList(UserQueryParams params, HttpServletRequest request) {
        Map<String, Object> map = BeanUtil.beanToMap(params, false, true);
        Map<String, String> allRequestParams = new LinkedHashMap<>(map.size());
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            allRequestParams.put(entry.getKey(), StrUtil.toStringOrNull(entry.getValue()));
        }
        return null;
    }
}