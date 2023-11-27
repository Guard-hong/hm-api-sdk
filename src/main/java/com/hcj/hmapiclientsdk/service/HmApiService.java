package com.hcj.hmapiclientsdk.service;

import com.hcj.hmapiclientsdk.client.HmApiClient;
import com.hcj.hmapiclientsdk.model.request.UnifyRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/**
 * @Author:HCJ
 * @DateTime:2023/10/2
 * @Description:
 **/
@Slf4j
public class HmApiService extends BaseService {

    /**
     * 统一请求
     * @param hmApiClient
     * @param unifyRequest
     * @return
     */
    public String doUnifyRequest(HmApiClient hmApiClient, UnifyRequest unifyRequest){
        return request(hmApiClient,unifyRequest);
    }
}
