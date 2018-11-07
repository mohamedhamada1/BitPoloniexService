package sa.waqood.networkmodule;

import com.rx2androidnetworking.Rx2ANRequest;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mohamed.Shaaban on 2/25/2018.
 */

public class RXUploadRequestCreator {
    Rx2ANRequest.MultiPartBuilder uploadRequestBuilder;
    private int requestID;

    protected RXUploadRequestCreator(String requestUrl) {
        uploadRequestBuilder = Rx2AndroidNetworking.upload(requestUrl);
    }

    protected RXUploadRequestCreator(String requestUrl, HashMap<String, String> header) {
        uploadRequestBuilder = Rx2AndroidNetworking.upload(requestUrl);
        uploadRequestBuilder.addHeaders(header);
    }

    protected RXUploadRequestCreator(String requestUrl, HashMap<String, String> header, String contentType) {
        uploadRequestBuilder = Rx2AndroidNetworking.upload(requestUrl);
        uploadRequestBuilder.addHeaders(header);
        uploadRequestBuilder.setContentType(contentType);
    }

    public RXUploadRequestCreator addHeader(Map<String, String> header) {
        uploadRequestBuilder.addHeaders(header);
        return this;

    }

    public RXUploadRequestCreator addHeader(String key, String value) {
        uploadRequestBuilder.addHeaders(key, value);
        return this;

    }

    public RXUploadRequestCreator addApplicationJsonBody(Object object) {
        uploadRequestBuilder.addMultipartParameter(object);
        return this;

    }

    public RXUploadRequestCreator addJSONObjectBody(JSONObject jsonObject) {
        uploadRequestBuilder.addMultipartParameter(jsonObject);
        return this;

    }

    public RXUploadRequestCreator addBodyParameter(Object object) {
        uploadRequestBuilder.addMultipartParameter(object);

        return this;

    }

    public RXUploadRequestCreator addPathParameter(String key, String value) {
        uploadRequestBuilder.addMultipartParameter(key, value);
        return this;

    }

    public RXUploadRequestCreator addPathParameter(Object object) {
        uploadRequestBuilder.addPathParameter(object);
        return this;

    }

    public RXUploadRequestCreator addPathParameter(Map<String, String> pathBody) {
        uploadRequestBuilder.addPathParameter(pathBody);
        return this;

    }

    public RXUploadRequestCreator addQueryParameter(String key, String value) {
        uploadRequestBuilder.addQueryParameter(key, value);
        return this;

    }

    public RXUploadRequestCreator addQueryParameter(Object object) {
        uploadRequestBuilder.addQueryParameter(object);
        return this;

    }

    public RXUploadRequestCreator addQueryParameter(Map<String, String> pathBody) {
        uploadRequestBuilder.addQueryParameter(pathBody);
        return this;

    }

    public RXUploadRequestCreator setRequestTag(int requestID) {
        this.requestID = requestID;
        return this;
    }

    public RXUploadRequestCreator setContentType(String contentType) {
        uploadRequestBuilder.setContentType(contentType);
        return this;
    }

    public RXUploadRequestCreator addUrlEncodeFormBodyParameter(String key, String value) {
        uploadRequestBuilder.addMultipartParameter(key, value);
        return this;
    }

    public RXUploadRequestCreator addUrlEncodeFormBodyParameter(Object object) {
        uploadRequestBuilder.addMultipartParameter(object);
        return this;
    }

    public RXUploadRequestCreator addUrlEncodeFormBodyParameter(HashMap<String, String> map) {
        uploadRequestBuilder.addMultipartParameter(map);
        return this;
    }

    public RXUploadRequestCreator addMultipartFile(String key, File file) {
        uploadRequestBuilder.addMultipartFile(key, file);
        return this;
    }

    public RequestParser build() {
        return RequestParser.build(uploadRequestBuilder.build());
    }
}
