package com.wfc.common.http;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hui.guo
 * @since 2023/5/5 12:04 上午
 */
@Slf4j
public abstract class AbsHttpReq implements HttpReq, HttpGetReq, HttpPostReq, HttpReqExec {

    protected String url;
    protected HttpReqMethod reqMethod;
    protected Map<String, String> headerMap = new HashMap<>();
    protected String referer;
    protected String userAgent;

    @Override
    public HttpPostReq userAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
    }

    @Override
    public HttpPostReq referer(String referer) {
        this.referer = referer;
        return this;
    }

    @Override
    public HttpPostReq header(String key, String value) {
        this.headerMap.put(key, value);
        return this;
    }

    @Override
    public HttpPostReq header(Map<String, String> headerMap) {
        this.headerMap.putAll(headerMap);
        return this;
    }

    @Override
    public HttpPostReq body(String body) {
        return this;
    }

    @Override
    public HttpGetReq get(String url) {
        Preconditions.checkArgument(StringUtils.isNotBlank(url));

        this.url = url;
        this.reqMethod = HttpReqMethod.GET;
        return this;
    }

    @Override
    public HttpPostReq post(String url) {
        Preconditions.checkArgument(StringUtils.isNotBlank(url));

        this.url = url;
        this.reqMethod = HttpReqMethod.POST;
        return this;
    }

    @Override
    public HttpPostReq param(String key, String value) {
        if (StringUtils.isBlank(key)) {
            return this;
        }

        StringBuilder urlSb = new StringBuilder(url);
        if (!StringUtils.contains(url, "?")) {
            urlSb.append("?");
        }

        if (!StringUtils.endsWithAny(url, "?", "&")) {
            urlSb.append("&");
        }
        urlSb.append(key).append("=").append(this.escapeString(value));

        this.url = urlSb.toString();

        return this;
    }

    @Override
    public HttpPostReq param(Map<String, String> paramMap) {
        if (MapUtils.isEmpty(paramMap)) {
            return this;
        }
        paramMap.forEach(this::param);
        return this;
    }

    @Override
    public String execute() {
        return this.execute(String::toString);
    }

    @Override
    public void asyncExecute() {
        this.asyncExecute(String::toString);
    }

    protected String escapeString(String str) {
        if (StringUtils.isBlank(str)) {
            return "";
        }
        try {
            return URLEncoder.encode(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
            log.warn("URLEncoder出现异常, str: {}", str, e);
            return str;
        }
    }

}
