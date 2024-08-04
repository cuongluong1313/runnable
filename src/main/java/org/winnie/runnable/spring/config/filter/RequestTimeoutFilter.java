package org.winnie.runnable.spring.config.filter;

import jakarta.servlet.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Order(1)
@Component
public class RequestTimeoutFilter implements Filter {

    private static final ScheduledExecutorService timeoutPool = Executors.newScheduledThreadPool(10);

    /**
     * The above Servlet Filter will not work if we use Async Servlet Filters.
     * When using Async Servlet Filter there is typically more than 1 thread that handles a request hence the above approach will not work.
     * Having said that if you use Async Servlet Filter there already is a way to apply a timeout that is defined by the API
     *
     * @param servletRequest  The request to process
     * @param servletResponse The response associated with the request
     * @param filterChain     Provides access to the next filter in the chain for this filter to pass the request and response
     *                        to for further processing
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws ServletException, IOException {
        System.out.println("RequestTimeoutFilter");
        AtomicBoolean completed = new AtomicBoolean(false);
        Thread requestHandlingThread = Thread.currentThread();
        ScheduledFuture<?> timeout = timeoutPool.schedule(() -> {
            if (completed.compareAndSet(false, true)) {
                requestHandlingThread.interrupt();
            }
        }, 5, TimeUnit.SECONDS);

        try {
            filterChain.doFilter(servletRequest, servletResponse);
            timeout.cancel(false);
        } finally {
            completed.set(true);
        }
    }

}
