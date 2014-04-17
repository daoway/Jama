package com.blogspot.ostas.monitoring.test.service.impl;

import com.blogspot.ostas.monitoring.annotation.MonitoringTarget;
import com.blogspot.ostas.monitoring.test.service.FibonacciService;

public class FibonacciServiceImpl implements FibonacciService {
    @MonitoringTarget(internalName = "fibonacci")
    public int fibonacci(int n)  {
        if(n == 0)
            return 0;
        else if(n == 1)
            return 1;
        else
            return fibonacci(n - 1) + fibonacci(n - 2);
    }
}
