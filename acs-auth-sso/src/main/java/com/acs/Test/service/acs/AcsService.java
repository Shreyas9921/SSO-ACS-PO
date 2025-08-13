package com.acs.Test.service.acs;

import com.acs.Test.dto.acs.AcsCreateVendorRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AcsService {

    private final RestTemplate restTemplate;

    @Value("${acs.vendor.create-url}")
    private String acsVendorUrl;

    @Value("${acs.vendor.update-url}")
    private String acsVendorUpdateUrl;

    @Value("${acs.device-type}")
    private String deviceType;

    @Value("${acs.version}")
    private String version;

    public boolean createVendor(AcsCreateVendorRequest request, String accountId, String apiKey, String cookie) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("AccountId", accountId);
            headers.set("ApiKey", apiKey);
            headers.set("DEVICE-TYPE", deviceType);
            headers.set("VER", version);
//      headers.set("cookie", cookie);
            headers.add(HttpHeaders.COOKIE, cookie.trim());

            // ❗ ACS expects a list of vendors, not a single vendor
            List<AcsCreateVendorRequest> requestList = List.of(request);

            HttpEntity<List<AcsCreateVendorRequest>> entity = new HttpEntity<>(requestList, headers);

//      log.info("Sending ACS Request Body: {}", new ObjectMapper().writeValueAsString(request));
//      log.info("ACS Request Headers: AccountId={}, ApiKey={}..., DEVICE-TYPE={}, VER={}",
//        accountId,
//        apiKey != null ? apiKey.substring(0, 4) + "****" : "null",
//        deviceType,
//        version
//      );

            ResponseEntity<String> response = restTemplate.postForEntity(acsVendorUrl, entity, String.class);

            // log.info("ACS Supplier create response = {}", response);
            log.info("ACS Supplier create response: statusCode={}, body={}",
                    response.getStatusCodeValue(),
                    response.getBody());
            return response.getStatusCode() == HttpStatus.OK || response.getStatusCode() == HttpStatus.CREATED;

        } catch (Exception e) {
            log.error("Error creating vendor in ACS: {}", e.getMessage(), e);
            return false;
        }
    }

    /*public boolean updateVendor(AcsUpdateVendorRequest request, String accountId, String apiKey, String cookie) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("AccountId", accountId);
            headers.set("ApiKey", apiKey);
            headers.set("DEVICE-TYPE", deviceType);
            headers.set("VER", version);
            headers.add(HttpHeaders.COOKIE, cookie.trim());

            List<AcsUpdateVendorRequest> requestList = List.of(request);
            HttpEntity<List<AcsUpdateVendorRequest>> entity = new HttpEntity<>(requestList, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(acsVendorUpdateUrl, entity, String.class);

            // log.info("ACS Supplier update response = {}", response);
            log.info("ACS Supplier update response: statusCode={}, body={}",
                    response.getStatusCodeValue(),
                    response.getBody());
            return response.getStatusCode() == HttpStatus.OK || response.getStatusCode() == HttpStatus.CREATED;

        } catch (Exception e) {
            log.error("Error updating vendor in ACS: {}", e.getMessage(), e);
            return false;
        }
    }*/

    /*public AcsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public AcsService(RestTemplate restTemplate, String acsVendorUrl, String acsVendorUpdateUrl, String deviceType, String version) {
        this.restTemplate = restTemplate;
        this.acsVendorUrl = acsVendorUrl;
        this.acsVendorUpdateUrl = acsVendorUpdateUrl;
        this.deviceType = deviceType;
        this.version = version;
    }*/

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public String getAcsVendorUrl() {
        return acsVendorUrl;
    }

    public void setAcsVendorUrl(String acsVendorUrl) {
        this.acsVendorUrl = acsVendorUrl;
    }

    public String getAcsVendorUpdateUrl() {
        return acsVendorUpdateUrl;
    }

    public void setAcsVendorUpdateUrl(String acsVendorUpdateUrl) {
        this.acsVendorUpdateUrl = acsVendorUpdateUrl;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}

