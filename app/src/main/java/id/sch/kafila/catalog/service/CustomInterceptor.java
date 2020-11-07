package id.sch.kafila.catalog.service;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;

class CustomInterceptor implements ClientHttpRequestInterceptor { 
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException
    {
        logRequest(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        logResponse(response);

        //Add optional additional headers
        response.getHeaders().add("headerName", "VALUE");

        return response;
    }

    private void logRequest(HttpRequest request, byte[] body) throws IOException
    {
        if (true);//log.isDebugEnabled())
        {
            System.out.println("===========================request begin================================================");
            System.out.println("URI         :  "+ request.getURI());
            System.out.println("Method      :  "+ request.getMethod());
            System.out.println("Headers     :  "+ request.getHeaders());
            System.out.println("Request body:  "+ new String(body, "UTF-8"));
            System.out.println("==========================request end================================================");
        }
    }

    private void logResponse(ClientHttpResponse response) throws IOException
    {
        if (true)//log.isDebugEnabled())
        {
            System.out.println("============================response begin==========================================");
            System.out.println("Status code  : "+ response.getStatusCode());
            System.out.println("Status text  :  "+ response.getStatusText());
            System.out.println("Headers      :  "+ response.getHeaders());
            String responseString = StreamUtils.copyToString(response.getBody(), Charset.defaultCharset());
            System.out.println("Response body:  "+ responseString.replace("{","\n{"));
            System.out.println("=======================response end=================================================");
        }
    }
}
