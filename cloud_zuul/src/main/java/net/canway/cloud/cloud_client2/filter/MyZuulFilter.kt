package net.canway.cloud.cloud_client2.filter

import com.netflix.zuul.ZuulFilter
import com.netflix.zuul.context.RequestContext
import com.netflix.zuul.exception.ZuulException
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.servlet.http.HttpServletRequest

class MyZuulFilter : ZuulFilter() {

    override//判断此过滤器是否执行，true为执行
    fun shouldFilter(): Boolean {
        return true
    }

    override//过滤器的类型，即在哪个阶段执行这个过滤器
    fun filterType(): String {
        //访问前过滤
        return "pre"
    }

    override//过滤器执行的顺序，数字越大越靠后
    fun filterOrder(): Int {
        return 1
    }

    @Throws(ZuulException::class)
    override//过滤器里面实现的具体逻辑
    fun run(): Any? {
        val request = RequestContext.getCurrentContext().request
        val host = request.remoteHost

        MyZuulFilter.LOGGER.info("请求的host:{}", host)
        return null
    }

    companion object {

        private val LOGGER = LoggerFactory.getLogger(MyZuulFilter::class.java!!)
    }


}
