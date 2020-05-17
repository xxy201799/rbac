package com.xxy.rbac_cloud_monitor;

import com.xxy.rbac_cloud_monitor.client.HttpClientUtils;
import com.xxy.rbac_cloud_monitor.client.HttpRequestMethodEnum;
import lombok.AllArgsConstructor;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RbacMonitorApplication.class)
@AllArgsConstructor
public class ClientTest {


    private final String UTF8 = "utf-8";

    private final CloseableHttpClient httpClient;

    private final RequestConfig requestConfig;

    /**
     * HttpClient基本使用测试
     */
    @Test
    public void test01() {
        String url = "http://localhost:8080/index";

        //创建http GET请求
        HttpRequestBase request = new HttpGet(url);
        //向请求添加config
        request.setConfig(requestConfig);

        CloseableHttpResponse response = null;
        try {
            //使用HttpClient发起请求得到响应
            response = httpClient.execute(request);
            //获得响应实体
            HttpEntity entity = response.getEntity();
            Header[] headers = response.getAllHeaders();
            //将响应实体转换为字符串
            String responseContext = EntityUtils.toString(entity, UTF8);

            for (Header header : headers) {
                System.out.println("name:  " + header.getName() + "  value:  " + header.getValue());
            }
            System.out.println(responseContext);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * HttpClientUtils测试
     */
    @Test
    public void test02() {
        String url = "http://localhost:8080/index";

        String result = HttpClientUtils.sendHttp(HttpRequestMethodEnum.HttpGet, url, null, null);

        System.out.println(result);
    }
}