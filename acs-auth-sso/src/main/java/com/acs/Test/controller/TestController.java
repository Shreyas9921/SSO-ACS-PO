package com.acs.Test.controller;

import com.acs.Test.dto.TestLombok;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.acs.common.annotation.Authenticated;
import com.acs.common.dto.UsersAuthDto;
import com.acs.common.enums.DeviceType;
import com.acs.common.utils.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController

public class TestController {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @GetMapping("/test")
    public ResponseEntity<String> testSso(
        @Authenticated(required = true) UsersAuthDto user,
        @RequestHeader(name = Constant.AUTH_TOKEN) String authToken,
        @RequestHeader(name = Constant.DEVICE_TYPE) DeviceType deviceType,
        @RequestHeader(name = Constant.APP_VERSION) String appVersion) {
        String userInfo = String.format(
            "SSO Token received - Authenticated as: %s, AuthToken: %s, DeviceType: %s, AppVersion: %s",
            user.toString(), authToken, deviceType, appVersion
        );

        // Log the user info
        logger.info(userInfo);

        return ResponseEntity.ok(userInfo);
    }

//    TestLombok obj = new TestLombok();
//    obj.setName("Hello");
//    System.out.println(obj.getName());

}
