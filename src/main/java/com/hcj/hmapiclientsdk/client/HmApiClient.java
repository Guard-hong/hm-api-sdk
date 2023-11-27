package com.hcj.hmapiclientsdk.client;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author:HCJ
 * @DateTime:2023/11/27
 * @Description:
 **/
@AllArgsConstructor
@Data
public class HmApiClient {
    /**
     * accessKey
     */
    private String accessKey;

    /**
     * secretKey
     */
    private String secretKey;
}
