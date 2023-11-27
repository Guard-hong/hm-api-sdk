package com.hcj.hmapiclientsdk.service;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.hcj.hmapiclientsdk.client.HmApiClient;
import com.hcj.hmapiclientsdk.common.ErrorCode;
import com.hcj.hmapiclientsdk.exception.BusinessException;
import com.hcj.hmapiclientsdk.model.enums.MethodEnum;
import com.hcj.hmapiclientsdk.model.hmapiclient.Identification;
import com.hcj.hmapiclientsdk.model.request.UnifyRequest;
import com.hcj.hmapiclientsdk.utils.SignUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author:HCJ
 * @DateTime:2023/10/2
 * @Description:
 **/
@Slf4j
public abstract class BaseService {

    private HmApiClient hmApiClient;

    /**
     * 封装请求头
     * @param accessKey
     * @param secretKey
     * @param body
     * @return
     */
    private Map<String, String> getHeaders(String accessKey, String secretKey, String body){
        HashMap<String, String> headers = new HashMap<>();
        headers.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        String encodedBody = SecureUtil.md5(body);
        headers.put("body",encodedBody);
        headers.put("accessKey",accessKey);
        headers.put("sign", SignUtils.genSign(encodedBody,secretKey));
        // todo 增加请求头，增强完整性和安全性
        return headers;
    }

    /**
     * 发送请求
     * @param unifyRequest
     * @return
     */
    private HttpResponse doRequest(UnifyRequest unifyRequest){
        String method = unifyRequest.getMethod().trim().toUpperCase();
        String path = unifyRequest.getPath().trim(); // path: /api/xx/*
        Map<String, Object> requestParams = unifyRequest.getRequestParams();
        if(! MethodEnum.getValues().contains(method)){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"请求方法不存在");
        }
        if(StringUtils.isBlank(path)){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"请求路径不存在");
        }
        HttpRequest httpRequest;
        switch (method){
            case "GET" :{
                // 拼接get参数 /api/xx?username=cc&password=xx
                path = splicingGetRequest(path,requestParams);
                httpRequest = HttpRequest.get(path);
                break;
            }
            case "POST" :{
                httpRequest = HttpRequest.post(path).body(JSONUtil.toJsonStr(unifyRequest.getRequestParams()));
                break;
            }
            default :{
                throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"请求方法不存在");
            }
        }
        log.info("{}请求路径是： {}",method,path);
        return httpRequest
                .addHeaders(getHeaders(hmApiClient.getAccessKey(),hmApiClient.getSecretKey(),JSONUtil.toJsonStr(unifyRequest)))
                .execute();
    }


    /**
     * 响应
     * @param unifyRequest
     * @return
     */
    private String doResponse(UnifyRequest unifyRequest){
        HttpResponse httpResponse = doRequest(unifyRequest);
        String res = httpResponse.body();
        return res;
    }

    /**
     * 发送请求
     * @param unifyRequest
     * @param hmApiClient
     * @return
     */
    public String request(HmApiClient hmApiClient,UnifyRequest unifyRequest){
        checkConfig(hmApiClient);
        return doResponse(unifyRequest);
    }

    /**
     * 判断ak和sk
     * @param hmApiClient
     */
    private void checkConfig(HmApiClient hmApiClient){
        if (hmApiClient == null || StringUtils.isAnyBlank(hmApiClient.getAccessKey(), hmApiClient.getSecretKey())) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "请先配置密钥AccessKey/SecretKey");
        }
        this.hmApiClient = hmApiClient;
    }

    /**
     * 给get请求添加请求参数
     * @param path
     * @param requestParams
     * @return
     */
    private String splicingGetRequest(String path,Map<String,Object> requestParams){
        StringBuilder sb = new StringBuilder(path);
        if(requestParams!=null && !requestParams.isEmpty()){
            sb.append("?");
            for (Map.Entry<String, Object> entry : requestParams.entrySet()) {
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
            sb.deleteCharAt(sb.length()-1);
        }
        return sb.toString();
    }

}
