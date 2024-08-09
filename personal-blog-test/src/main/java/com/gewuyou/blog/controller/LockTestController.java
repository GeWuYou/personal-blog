package com.gewuyou.blog.controller;

import com.gewuyou.blog.common.constant.InterfacePermissionConstant;
import com.gewuyou.blog.service.LockTestService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author gewuyou
 * @since 2024-08-08 23:03:05
 */
@RestController
@RequestMapping(InterfacePermissionConstant.TEST_BASE_URL + "/lock")
public class LockTestController {
    @Resource
    private LockTestService lockTestService;

    @GetMapping("/read")
    public List<String> testReadLock() throws InterruptedException {
        return lockTestService.readResource();
    }

    @PostMapping("/write")
    public void testWriteLock(@RequestParam String value) throws InterruptedException {
        lockTestService.writeResource(value);
    }
}
