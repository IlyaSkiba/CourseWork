package com.bsu.server.global.service.base;

import com.bsu.service.api.base.TransactionService;
import com.google.common.base.Throwables;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.Callable;

/**
 * @author HomeUser
 *         Date: 21.3.13
 *         Time: 0.26
 */
@Service
@Transactional(readOnly = true)
public class TransactionServiceImpl implements TransactionService {
    @Override
    public <T> T callInTransaction(Callable<T> callable) {
        try {
            return callable.call();
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }
}
