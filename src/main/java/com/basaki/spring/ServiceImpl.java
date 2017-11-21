package com.basaki.spring;

import org.springframework.cache.annotation.Cacheable;

public class ServiceImpl implements Service {

    @Cacheable(cacheNames = "int-strings")
    public String getString(int i) {
        return String.valueOf(i);
    }
}
