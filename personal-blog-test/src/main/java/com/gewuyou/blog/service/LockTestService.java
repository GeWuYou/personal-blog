package com.gewuyou.blog.service;

import com.gewuyou.blog.common.annotation.ReadLock;
import com.gewuyou.blog.common.annotation.WriteLock;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LockTestService {

    private final List<String> sharedResource = new ArrayList<>();

    @ReadLock("myLock")
    public List<String> readResource() throws InterruptedException {
        System.out.println("Reading resource: " + sharedResource);
        return new ArrayList<>(sharedResource);
    }

    @WriteLock("myLock")
    public void writeResource(String value) throws InterruptedException {
        System.out.println("Writing resource: " + value);
        Thread.sleep(5000);
        sharedResource.add(value);
    }
}
