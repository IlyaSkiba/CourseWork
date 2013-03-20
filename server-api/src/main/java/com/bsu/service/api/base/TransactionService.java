package com.bsu.service.api.base;

import java.util.concurrent.Callable;

/**
 * @author HomeUser
 *         Date: 21.3.13
 *         Time: 0.11
 */
public interface TransactionService {
    <T> T callInTransaction(Callable<T> callable);
}
