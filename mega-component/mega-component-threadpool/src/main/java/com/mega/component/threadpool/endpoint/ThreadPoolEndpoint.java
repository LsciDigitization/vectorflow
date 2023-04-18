package com.mega.component.threadpool.endpoint;

import com.mega.component.threadpool.ThreadPoolMonitor;
import com.mega.component.threadpool.entity.ThreadPoolDetailInfo;
import com.mega.component.threadpool.queue.ResizeableBlockingQueue;
import com.mega.component.threadpool.entity.ThreadPoolInfo;
import com.mega.component.threadpool.ThreadPoolManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.web.Link;
import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.net.URI;
import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The type Thread pool endpoint.
 *
 * @author newrank
 */
@RestControllerEndpoint(id = "threadpool")
@Component
public class ThreadPoolEndpoint {
    @Autowired
    private ThreadPoolManager threadPoolUtil;

    private static final ReentrantLock LOCK = new ReentrantLock();

    private static final String RESIZEABLE_BLOCKING_QUEUE = "ResizeableBlockingQueue";

    /**
     * 请求示例：
     * GET <a href="http://localhost:8080/actuator/device">http://localhost:8080/actuator/device</a>
     *
     * @return _links
     */
    @GetMapping("/")
    public Map<String, Map<String, Link>> device(HttpServletRequest request) {
        String hostUri = getHostUri(request);
        String requestUri = UriComponentsBuilder.fromUri(URI.create(request.getRequestURI())).replaceQuery(null)
                .toUriString();
        String normalizedUrl = hostUri + normalizeRequestUrl(requestUri);
        Map<String, Link> links = new LinkedHashMap<>();
        links.put("self", new Link(normalizedUrl));
        links.put("getThreadPools", createLink(normalizedUrl, "/getThreadPools"));
        links.put("getThreadPoolFixInfo", createLink(normalizedUrl, "/getThreadPoolFixInfo/?threadPoolName={threadPoolName}"));
        links.put("setThreadPoolFixInfo", createLink(normalizedUrl, "/setThreadPoolFixInfo"));
        links.put("getThreadPoolListInfo", createLink(normalizedUrl, "/getThreadPoolListInfo"));

        return Collections.singletonMap("_links", links);
    }

    private String getHostUri(HttpServletRequest request) {
        String scheme = request.getScheme();
        String remoteHost = request.getRemoteHost();
        int serverPort = request.getServerPort();
        String host;
        if (scheme.equals("https")) {
            if (serverPort == 443) {
                host = scheme + "://" + remoteHost;
            } else {
                host = scheme + "://" + remoteHost + ":" + serverPort;
            }
        } else {
            if (serverPort == 80) {
                host = scheme + "://" + remoteHost;
            } else {
                host = scheme + "://" + remoteHost + ":" + serverPort;
            }
        }
        return host;
    }

    private String normalizeRequestUrl(String requestUrl) {
        if (requestUrl.endsWith("/")) {
            return requestUrl.substring(0, requestUrl.length() - 1);
        }
        return requestUrl;
    }

    private Link createLink(String requestUrl, String path) {
        return new Link(requestUrl + (path.startsWith("/") ? path : "/" + path));
    }

    /**
     * getThreadPools
     * 获取当前所有线程池的线程名称
     */
    @GetMapping("getThreadPools")
    private List<String> getThreadPools() {
        List<String> threadPools = new ArrayList<>();
        if (!threadPoolUtil.getThreadPoolExecutorHashMap().isEmpty()) {
            for (Map.Entry<String, ThreadPoolMonitor> entry : threadPoolUtil.getThreadPoolExecutorHashMap().entrySet()) {
                threadPools.add(entry.getKey());
            }
        }
        return threadPools;
    }

    /**
     * 获取线程池可变参数信息
     *
     * @param threadPoolName 线程池名称
     * @return ThreadPoolInfo 线程池信息
     */
    @GetMapping("getThreadPoolFixInfo")
    private ThreadPoolInfo getThreadPoolInfo(@RequestParam String threadPoolName) {
        if (threadPoolUtil.getThreadPoolExecutorHashMap().containsKey(threadPoolName)) {
            ThreadPoolMonitor threadPoolExecutor = threadPoolUtil.getThreadPoolExecutorHashMap().get(threadPoolName);
            int queueCapacity = 0;
            if (RESIZEABLE_BLOCKING_QUEUE.equals(threadPoolExecutor.getQueue().getClass().getSimpleName())) {
                ResizeableBlockingQueue<Runnable> queue = (ResizeableBlockingQueue<Runnable>) threadPoolExecutor.getQueue();
                queueCapacity = queue.getCapacity();
            }
            return new ThreadPoolInfo(
                    threadPoolName,
                    threadPoolExecutor.getCorePoolSize(),
                    threadPoolExecutor.getMaximumPoolSize(),
                    threadPoolExecutor.getQueue().getClass().getSimpleName(),
                    queueCapacity);
        }
        return null;
    }


    /**
     * 修改线程池配置
     *
     * @param threadPoolInfo 线程池信息
     * @return Boolean
     */
    @PostMapping("setThreadPoolFixInfo")
    private Boolean setThreadPoolInfo(@RequestBody ThreadPoolInfo threadPoolInfo) {
        if (threadPoolUtil.getThreadPoolExecutorHashMap().containsKey(threadPoolInfo.getThreadPoolName())) {
            LOCK.lock();
            try {
                ThreadPoolMonitor threadPoolExecutor = threadPoolUtil.getThreadPoolExecutorHashMap().get(threadPoolInfo.getThreadPoolName());
                threadPoolExecutor.setMaximumPoolSize(threadPoolInfo.getMaximumPoolSize());
                threadPoolExecutor.setCorePoolSize(threadPoolInfo.getCorePoolSize());
                if (RESIZEABLE_BLOCKING_QUEUE.equals(threadPoolExecutor.getQueue().getClass().getSimpleName())) {
                    ResizeableBlockingQueue<Runnable> queue = (ResizeableBlockingQueue<Runnable>) threadPoolExecutor.getQueue();
                    queue.setCapacity(threadPoolInfo.getQueueCapacity());
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            } finally {
                LOCK.unlock();
            }
        }
        return false;
    }

    /**
     * 获取线程池监控信息
     *
     * @return ThreadPoolDetailInfo list
     */
    @GetMapping("getThreadPoolListInfo")
    private List<ThreadPoolDetailInfo> getThreadPoolListInfo() {
        List<ThreadPoolDetailInfo> detailInfoList = new ArrayList<>();
        if (!threadPoolUtil.getThreadPoolExecutorHashMap().isEmpty()) {
            for (Map.Entry<String, ThreadPoolMonitor> entry : threadPoolUtil.getThreadPoolExecutorHashMap().entrySet()) {
                ThreadPoolDetailInfo threadPoolDetailInfo = threadPoolInfo(entry.getValue(), entry.getKey());
                detailInfoList.add(threadPoolDetailInfo);
            }
        }
        return detailInfoList;
    }

    /**
     * 组装线程池详情
     *
     * @param threadPool 线程池
     * @param threadPoolName 线程池名称
     * @return ThreadPoolDetailInfo 张程池祥细信息
     */
    private ThreadPoolDetailInfo threadPoolInfo(ThreadPoolMonitor threadPool, String threadPoolName) {
        BigDecimal activeCount = new BigDecimal(threadPool.getActiveCount());
        BigDecimal maximumPoolSize = new BigDecimal(threadPool.getMaximumPoolSize());
        BigDecimal result = activeCount.divide(maximumPoolSize, 2, BigDecimal.ROUND_HALF_UP);
        NumberFormat numberFormat = NumberFormat.getPercentInstance();
        numberFormat.setMaximumFractionDigits(2);
        int queueCapacity = 0;
        if (RESIZEABLE_BLOCKING_QUEUE.equals(threadPool.getQueue().getClass().getSimpleName())) {
            ResizeableBlockingQueue<Runnable> queue = (ResizeableBlockingQueue<Runnable>) threadPool.getQueue();
            queueCapacity = queue.getCapacity();
        }
        return new ThreadPoolDetailInfo(
                threadPoolName,
                threadPool.getPoolSize(),
                threadPool.getCorePoolSize(),
                threadPool.getLargestPoolSize(),
                threadPool.getMaximumPoolSize(),
                threadPool.getCompletedTaskCount(),
                threadPool.getActiveCount(),
                threadPool.getTaskCount(),
                threadPool.getKeepAliveTime(TimeUnit.MILLISECONDS),
                numberFormat.format(result.doubleValue()),
                queueCapacity,
                threadPool.getQueue().size(),
                threadPool.getTaskCount() == 0 ? 0 : threadPool.getTotalDiff() / threadPool.getTaskCount());
    }


}
