package net.canway.cloud.cloud_client2.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class MyZuulFilter extends ZuulFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyZuulFilter.class);

    @Override
    //判断此过滤器是否执行，true为执行
    public boolean shouldFilter() {
        return true;
    }

    @Override
    //过滤器的类型，即在哪个阶段执行这个过滤器
    public String filterType() {
        //访问前过滤
        return "pre";
    }

    @Override
    //过滤器执行的顺序，数字越大越靠后
    public int filterOrder() {
        return 1;
    }

    @Override
    //过滤器里面实现的具体逻辑
    public Object run() throws ZuulException {
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        String host = request.getRemoteHost();

        MyZuulFilter.LOGGER.info("请求的host:{}", host);
        return null;
    }

    
}
