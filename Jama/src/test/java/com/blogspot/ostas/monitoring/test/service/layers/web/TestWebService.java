package com.blogspot.ostas.monitoring.test.service.layers.web;

import com.blogspot.ostas.monitoring.annotation.MonitoringTarget;
import com.blogspot.ostas.monitoring.test.service.layers.service.TestServiceLayer;

public class TestWebService {
    private TestServiceLayer testServiceLayer;

    public void setTestServiceLayer(TestServiceLayer testServiceLayer) {
        this.testServiceLayer = testServiceLayer;
    }

    @MonitoringTarget(internalName = "webLayer1")
    public void testWebServiceMethod1(String param1) throws InterruptedException {
        testServiceLayer.testServiceLayerMethod1(param1);
        Thread.sleep(300);
    }
}
