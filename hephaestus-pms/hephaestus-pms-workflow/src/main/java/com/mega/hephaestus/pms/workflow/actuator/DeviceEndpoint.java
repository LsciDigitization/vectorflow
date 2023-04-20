package com.mega.hephaestus.pms.workflow.actuator;

import com.mega.component.nuc.device.DeviceType;
import com.mega.hephaestus.pms.nuc.manager.AbstractDeviceManager;
import com.mega.hephaestus.pms.nuc.manager.DeviceManagerFactory;
import com.mega.hephaestus.pms.workflow.device.devicebus.DeviceBusPool;
import com.mega.hephaestus.pms.workflow.device.deviceclient.DeviceClientPool;
import com.mega.hephaestus.pms.workflow.device.devicedaemon.DeviceDaemonPool;
import com.mega.hephaestus.pms.workflow.device.devicetask.DeviceTaskEntity;
import com.mega.hephaestus.pms.workflow.device.devicetask.DeviceTaskPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.web.Link;
import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.*;

/**
 * 请求示例：
 * <a href="http://localhost:8080/actuator/device">http://localhost:8080/actuator/device</a>
 */
@Component
@RestControllerEndpoint(id = "device")
public class DeviceEndpoint {

    @Autowired
    private DeviceManagerFactory deviceManagerFactory;

    @Autowired
    private DeviceTaskPool deviceTaskPool;
    @Autowired
    private DeviceDaemonPool deviceDaemonPool;
    @Autowired
    private DeviceClientPool deviceClientPool;
    @Autowired
    private DeviceBusPool deviceBusPool;

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
        links.put("deviceManagerPool", createLink(normalizedUrl, "/deviceManagerPool"));
        links.put("deviceClientPool", createLink(normalizedUrl, "/deviceClientPool"));
        links.put("deviceTaskPool", createLink(normalizedUrl, "/deviceTaskPool"));
        links.put("deviceDaemonPool", createLink(normalizedUrl, "/deviceDaemonPool"));
        links.put("deviceBusPool", createLink(normalizedUrl, "/deviceBusPool"));

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


    @GetMapping("/deviceManagerPool")
    public Map<String, Object> deviceManagerPool() {
        Map<DeviceType, AbstractDeviceManager<?, ?>> deviceManagerMap = deviceManagerFactory.getDevices();

        Map<String, Object> deviceMap = new HashMap<>();
        deviceManagerMap.forEach((k, v) -> {
            deviceMap.put(k.toString(), v.toMap());
        });

        return deviceMap;
    }

    @GetMapping("/deviceClientPool")
    public Map<String, Object> deviceClientPool() {
        Map<String, Object> deviceMap = new HashMap<>();
        deviceClientPool.all().forEach((k, v) -> {
            try {
                deviceMap.put(k, v.toMap());
            } catch (Exception e) {
                //
                e.printStackTrace();
            }
        });
        return deviceMap;
    }

    @GetMapping("/deviceTaskPool")
    public Map<String, Map<String, DeviceTaskEntity>> deviceTaskPool() {
        Map<String, Map<String, DeviceTaskEntity>> deviceMap = new HashMap<>();
        deviceTaskPool.all().forEach((k, v) -> {
            deviceMap.put(k, v.toMap());
        });
        return deviceMap;
    }

    @GetMapping("/deviceDaemonPool")
    public Map<String, Map<String, Object>> deviceDaemonPool() {
        Map<String, Map<String, Object>> deviceMap = new HashMap<>();
        deviceDaemonPool.all().forEach((k, v) -> {
            deviceMap.put(k, threadToMap(v));
        });
        return deviceMap;
    }

    @GetMapping("/deviceBusPool")
    public Map<String, Map<String, Object>> deviceBusPool() {
        Map<String, Map<String, Object>> deviceMap = new HashMap<>();
        deviceBusPool.all().forEach((k, v) -> {
            deviceMap.put(k, threadToMap(v));
        });
        return deviceMap;
    }


    private Map<String, Object> threadToMap(Thread thread) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", thread.getId());
        map.put("name", thread.getName());
        map.put("state", thread.getState());
        map.put("parent", thread.getThreadGroup());
        map.put("priority", thread.getPriority());
        map.put("alive", thread.isAlive());
        map.put("daemon", thread.isDaemon());
        map.put("interrupt", thread.isInterrupted());
        return map;
    }

}
