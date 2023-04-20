package com.mega.hephaestus.pms.workflow.config;

import lombok.extern.slf4j.Slf4j;

@Deprecated(since = "20220420")
//@Configuration(proxyBeanMethods = true)
@Slf4j
public class CustomDeviceManagerConfiguration {

//    @Resource
//    private IRedisLockService redisLockService;
//    @Resource
//    private DeviceManagerFactory deviceManagerFactory;
//    @Resource
//    private DeviceConfigurationManager deviceConfigurationManager;
//
//    @Autowired
//    private ApplicationContext applicationContext;


//    @Bean
//    public CentrifugeManager centrifugeManager() {
//        DeviceConfiguration deviceConfiguration = deviceConfigurationManager.getDevice(DeviceType.Centrifuge).orElseGet(() -> {
//            DeviceConfiguration emptyDevice = new DeviceConfiguration();
//            emptyDevice.setDeviceId("0");
//            emptyDevice.setDeviceType(DeviceType.Centrifuge);
//            emptyDevice.setDeviceKey(DeviceType.Centrifuge + "-" + 0);
//            return emptyDevice;
//        });
//
//        FeignClientBuilder feignClientBuilder = new FeignClientBuilder(applicationContext);
//        FeignClientBuilder.Builder<CentrifugeClient> clientBuilder = feignClientBuilder.forType(CentrifugeClient.class, DeviceType.Centrifuge.getCode());
//        clientBuilder.url(deviceConfiguration.getUrl());
//
//        CentrifugeManager of = CentrifugeManager.of(clientBuilder.build(), deviceConfiguration, redisLockService);
//        deviceManagerFactory.putDeviceManager(DeviceType.Centrifuge, of);
//        return of;
//    }

//    @Bean
//    public CytometerManager cytometerManager() {
//        DeviceConfiguration deviceConfiguration = deviceConfigurationManager.getDevice(DeviceType.Cytometer).orElseGet(() -> {
//            DeviceConfiguration emptyDevice = new DeviceConfiguration();
//            emptyDevice.setDeviceId("0");
//            emptyDevice.setDeviceType(DeviceType.Cytometer);
//            emptyDevice.setDeviceKey(DeviceType.Cytometer + "-" + 0);
//            return emptyDevice;
//        });
//
//        FeignClientBuilder feignClientBuilder = new FeignClientBuilder(applicationContext);
//        FeignClientBuilder.Builder<CytometerClient> clientBuilder = feignClientBuilder.forType(CytometerClient.class, DeviceType.Cytometer.getCode());
//        clientBuilder.url(deviceConfiguration.getUrl());
//
//        CytometerManager of = CytometerManager.of(clientBuilder.build(), deviceConfiguration, redisLockService);
//        deviceManagerFactory.putDeviceManager(DeviceType.Cytometer, of);
//        return of;
//    }

//    @Bean
//    public HCSManager hcsManager() {
//        DeviceConfiguration deviceConfiguration = deviceConfigurationManager.getDevice(DeviceType.HCS).orElseGet(() -> {
//            DeviceConfiguration emptyDevice = new DeviceConfiguration();
//            emptyDevice.setDeviceId("0");
//            emptyDevice.setDeviceType(DeviceType.HCS);
//            emptyDevice.setDeviceKey(DeviceType.HCS + "-" + 0);
//            return emptyDevice;
//        });
//
//        FeignClientBuilder feignClientBuilder = new FeignClientBuilder(applicationContext);
//        FeignClientBuilder.Builder<HCSClient> clientBuilder = feignClientBuilder.forType(HCSClient.class, DeviceType.HCS.getCode());
//        clientBuilder.url(deviceConfiguration.getUrl());
//
//        HCSManager of = HCSManager.of(clientBuilder.build(), deviceConfiguration, redisLockService);
//        deviceManagerFactory.putDeviceManager(DeviceType.HCS, of);
//        return of;
//    }

//    @Bean
//    public IncubatorManager incubatorManager() {
//        DeviceConfiguration deviceConfiguration = deviceConfigurationManager.getDevice(DeviceType.Incubator).orElseGet(() -> {
//            DeviceConfiguration emptyDevice = new DeviceConfiguration();
//            emptyDevice.setDeviceId("0");
//            emptyDevice.setDeviceType(DeviceType.Incubator);
//            emptyDevice.setDeviceKey(DeviceType.Incubator + "-" + 0);
//            return emptyDevice;
//        });
//
//        FeignClientBuilder feignClientBuilder = new FeignClientBuilder(applicationContext);
//        FeignClientBuilder.Builder<IncubatorClient> clientBuilder = feignClientBuilder.forType(IncubatorClient.class, DeviceType.Incubator.getCode());
//        clientBuilder.url(deviceConfiguration.getUrl());
//
//        IncubatorManager of = IncubatorManager.of(clientBuilder.build(), deviceConfiguration, redisLockService);
//        deviceManagerFactory.putDeviceManager(DeviceType.Incubator, of);
//        return of;
//    }

//    @Bean
//    public LabwareHandlerMZ04Manager labwareHandlerMZ04Manager() {
//        DeviceConfiguration deviceConfiguration = deviceConfigurationManager.getDevice(DeviceType.LabwareHandlerMZ04).orElseGet(() -> {
//            DeviceConfiguration emptyDevice = new DeviceConfiguration();
//            emptyDevice.setDeviceId("0");
//            emptyDevice.setDeviceType(DeviceType.LabwareHandlerMZ04);
//            emptyDevice.setDeviceKey(DeviceType.LabwareHandlerMZ04 + "-" + 0);
//            return emptyDevice;
//        });
//
//        FeignClientBuilder feignClientBuilder = new FeignClientBuilder(applicationContext);
//        FeignClientBuilder.Builder<LabwareHandlerMZ04Client> clientBuilder = feignClientBuilder.forType(LabwareHandlerMZ04Client.class, DeviceType.LabwareHandlerMZ04.getCode());
//        clientBuilder.url(deviceConfiguration.getUrl());
//
//        LabwareHandlerMZ04Manager of = LabwareHandlerMZ04Manager.of(clientBuilder.build(), deviceConfiguration, redisLockService);
//        deviceManagerFactory.putDeviceManager(DeviceType.LabwareHandlerMZ04, of);
//        return of;
//    }

//    @Bean
//    public LabwareHandlerPF400Manager labwareHandlerPF400Manager() {
//        DeviceConfiguration deviceConfiguration = deviceConfigurationManager.getDevice(DeviceType.LabwareHandlerPF400).orElseGet(() -> {
//            DeviceConfiguration emptyDevice = new DeviceConfiguration();
//            emptyDevice.setDeviceId("0");
//            emptyDevice.setDeviceType(DeviceType.LabwareHandlerPF400);
//            emptyDevice.setDeviceKey(DeviceType.LabwareHandlerPF400 + "-" + 0);
//            return emptyDevice;
//        });
//
//        FeignClientBuilder feignClientBuilder = new FeignClientBuilder(applicationContext);
//        FeignClientBuilder.Builder<LabwareHandlerPF400Client> clientBuilder = feignClientBuilder.forType(LabwareHandlerPF400Client.class, DeviceType.LabwareHandlerPF400.getCode());
//        clientBuilder.url(deviceConfiguration.getUrl());
//
//        LabwareHandlerPF400Manager of = LabwareHandlerPF400Manager.of(clientBuilder.build(), deviceConfiguration, redisLockService);
//        deviceManagerFactory.putDeviceManager(DeviceType.LabwareHandlerPF400, of);
//        return of;
//    }

//    @Bean
//    public MultidropManager multidropManager() {
//        DeviceConfiguration deviceConfiguration = deviceConfigurationManager.getDevice(DeviceType.Multidrop).orElseGet(() -> {
//            DeviceConfiguration emptyDevice = new DeviceConfiguration();
//            emptyDevice.setDeviceId("0");
//            emptyDevice.setDeviceType(DeviceType.Multidrop);
//            emptyDevice.setDeviceKey(DeviceType.Multidrop + "-" + 0);
//            return emptyDevice;
//        });
//
//        FeignClientBuilder feignClientBuilder = new FeignClientBuilder(applicationContext);
//        FeignClientBuilder.Builder<MultidropClient> clientBuilder = feignClientBuilder.forType(MultidropClient.class, DeviceType.Multidrop.getCode());
//        clientBuilder.url(deviceConfiguration.getUrl());
//
//        MultidropManager of = MultidropManager.of(clientBuilder.build(), deviceConfiguration, redisLockService);
//        deviceManagerFactory.putDeviceManager(DeviceType.Multidrop, of);
//        return of;
//    }

//    @Bean
//    public ReaderManager readerManager() {
//        DeviceConfiguration deviceConfiguration = deviceConfigurationManager.getDevice(DeviceType.Reader).orElseGet(() -> {
//            DeviceConfiguration emptyDevice = new DeviceConfiguration();
//            emptyDevice.setDeviceId("0");
//            emptyDevice.setDeviceType(DeviceType.Reader);
//            emptyDevice.setDeviceKey(DeviceType.Reader + "-" + 0);
//            return emptyDevice;
//        });
//
//        FeignClientBuilder feignClientBuilder = new FeignClientBuilder(applicationContext);
//        FeignClientBuilder.Builder<ReaderClient> clientBuilder = feignClientBuilder.forType(ReaderClient.class, DeviceType.Reader.getCode());
//        clientBuilder.url(deviceConfiguration.getUrl());
//
//        ReaderManager of = ReaderManager.of(clientBuilder.build(), deviceConfiguration, redisLockService);
//        deviceManagerFactory.putDeviceManager(DeviceType.Reader, of);
//        return of;
//    }

//    @Bean
//    public WasherManager washerManager() {
//        DeviceConfiguration deviceConfiguration = deviceConfigurationManager.getDevice(DeviceType.Washer).orElseGet(() -> {
//            DeviceConfiguration emptyDevice = new DeviceConfiguration();
//            emptyDevice.setDeviceId("0");
//            emptyDevice.setDeviceType(DeviceType.Washer);
//            emptyDevice.setDeviceKey(DeviceType.Washer + "-" + 0);
//            return emptyDevice;
//        });
//
//        FeignClientBuilder feignClientBuilder = new FeignClientBuilder(applicationContext);
//        FeignClientBuilder.Builder<WasherClient> clientBuilder = feignClientBuilder.forType(WasherClient.class, DeviceType.Washer.getCode());
//        clientBuilder.url(deviceConfiguration.getUrl());
//
//        WasherManager of = WasherManager.of(clientBuilder.build(), deviceConfiguration, redisLockService);
//        deviceManagerFactory.putDeviceManager(DeviceType.Washer, of);
//        return of;
//    }

//    @Bean
//    public WorkStationManager workStationManager() {
//        DeviceConfiguration deviceConfiguration = deviceConfigurationManager.getDevice(DeviceType.Workstation).orElseGet(() -> {
//            DeviceConfiguration emptyDevice = new DeviceConfiguration();
//            emptyDevice.setDeviceId("0");
//            emptyDevice.setDeviceType(DeviceType.Workstation);
//            emptyDevice.setDeviceKey(DeviceType.Workstation + "-" + 0);
//            return emptyDevice;
//        });
//
//        FeignClientBuilder feignClientBuilder = new FeignClientBuilder(applicationContext);
//        FeignClientBuilder.Builder<WorkstationClient> clientBuilder = feignClientBuilder.forType(WorkstationClient.class, DeviceType.Workstation.getCode());
//        clientBuilder.url(deviceConfiguration.getUrl());
//
//        WorkStationManager of = WorkStationManager.of(clientBuilder.build(), deviceConfiguration, redisLockService);
//        deviceManagerFactory.putDeviceManager(DeviceType.Workstation, of);
//        return of;
//    }


}
