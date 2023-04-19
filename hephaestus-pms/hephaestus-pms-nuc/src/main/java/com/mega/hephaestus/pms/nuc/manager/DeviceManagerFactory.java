package com.mega.hephaestus.pms.nuc.manager;


import com.mega.component.nuc.device.AbstractDevice;
import com.mega.component.nuc.device.DeviceClient;
import com.mega.component.nuc.device.DeviceType;
import com.mega.component.nuc.device.DeviceTypeEnum;
import com.mega.component.nuc.exceptions.DeviceException;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DeviceManagerFactory {

    @Deprecated(since = "20221115")
    private final Map<DeviceType, AbstractDeviceManager<?, ?>> devices = new ConcurrentHashMap<>();

    @Deprecated(since = "20221115")
    public void putDeviceManager(DeviceType deviceType, AbstractDeviceManager<?, ?> deviceManager) {
        devices.put(deviceType, deviceManager);
    }

    @Deprecated(since = "20221115")
    public Map<DeviceType, AbstractDeviceManager<?, ?>> getDevices() {
        return devices;
    }

    public AbstractDeviceManager<?, ?> getDeviceManager(DeviceType deviceType) {
        if (devices.containsKey(deviceType)) {
            return devices.get(deviceType);
        }
        throw new DeviceException(String.format("No found device manager %s.", deviceType), deviceType);
    }


//    public CentrifugeManager getCentrifugeManager(DeviceType deviceType) {
//        if (deviceType == DeviceType.Centrifuge) {
//            return (CentrifugeManager) getDeviceManager(deviceType);
//        }
//        throw new DeviceException("Device type not match.");
//    }

//    public CytometerManager getCytometerManager(DeviceType deviceType) {
//        if (deviceType == DeviceType.Cytometer) {
//            return (CytometerManager) getDeviceManager(deviceType);
//        }
//        throw new DeviceException("Device type not match.");
//    }

//    public HCSManager getHCSManager(DeviceType deviceType) {
//        if (deviceType == DeviceType.HCS) {
//            return (HCSManager) getDeviceManager(deviceType);
//        }
//        throw new DeviceException("HCS Device type not match.");
//    }

//    public IncubatorManager getIncubatorManager(DeviceType deviceType) {
//        if (deviceType == DeviceType.Incubator) {
//            return (IncubatorManager) getDeviceManager(deviceType);
//        }
//        throw new DeviceException("Incubator Device type not match.");
//    }

//    public LabwareHandlerMZ04Manager getLabwareHandlerMZ04Manager(DeviceType deviceType) {
//        if (deviceType == DeviceType.LabwareHandlerMZ04) {
//            return (LabwareHandlerMZ04Manager) getDeviceManager(deviceType);
//        }
//        throw new DeviceException("LabwareHandlerMZ04 Device type not match.");
//    }

//    public LabwareHandlerPF400Manager getLabwareHandlerPF400Manager(DeviceType deviceType) {
//        if (deviceType == DeviceType.LabwareHandlerPF400) {
//            return (LabwareHandlerPF400Manager) getDeviceManager(deviceType);
//        }
//        throw new DeviceException("LabwareHandlerPF400 Device type not match.");
//    }

//    public MultidropManager getMultidropManager(DeviceType deviceType) {
//        if (deviceType == DeviceType.Multidrop) {
//            return (MultidropManager) getDeviceManager(deviceType);
//        }
//        throw new DeviceException("Multidrop Device type not match.");
//    }

//    public ReaderManager getReaderManager(DeviceType deviceType) {
//        if (deviceType == DeviceType.Reader) {
//            return (ReaderManager) getDeviceManager(deviceType);
//        }
//        throw new DeviceException("Reader Device type not match.");
//    }

//    public WasherManager getWasherManager(DeviceType deviceType) {
//        if (deviceType == DeviceType.Washer) {
//            return (WasherManager) getDeviceManager(deviceType);
//        }
//        throw new DeviceException("Washer Device type not match.");
//    }

//    public WorkStationManager getWorkStationManager(DeviceType deviceType) {
//        if (deviceType == DeviceType.Workstation) {
//            return (WorkStationManager) getDeviceManager(deviceType);
//        }
//        throw new DeviceException("Workstation Device type not match.");
//    }

//    @SuppressWarnings("unchecked")
//    public <T> T getDeviceManagerMatch(DeviceType deviceType) {
//        switch (deviceType) {
//            case Centrifuge:
//                return (T) getCentrifugeManager(deviceType);
//            case Cytometer:
//                return (T) getCytometerManager(deviceType);
//            case HCS:
//                return (T) getHCSManager(deviceType);
//            case Incubator:
//                return (T) getIncubatorManager(deviceType);
//            case LabwareHandlerMZ04:
//                return (T) getLabwareHandlerMZ04Manager(deviceType);
//            case LabwareHandlerPF400:
//                return (T) getLabwareHandlerPF400Manager(deviceType);
//            case Multidrop:
//                return (T) getMultidropManager(deviceType);
//            case Reader:
//                return (T) getReaderManager(deviceType);
//            case Washer:
//                return (T) getWasherManager(deviceType);
//            case Workstation:
//                return (T) getWorkStationManager(deviceType);
//            default:
//                throw new DeviceException("Device type not match.");
//        }
//    }


    /**
     * 根据设备TYPE字符串获取设备实例
     * @param deviceTypeString 设备TYPE
     * @return 设备实例
     */
    public AbstractDevice getDevice(String deviceTypeString) {
        DeviceType deviceType = DeviceTypeEnum.toEnum(deviceTypeString);
        AbstractDeviceManager<?, ?> deviceManager = getDeviceManager(deviceType);
        return deviceManager.getDevice();
    }

    /**
     * 根据设备TYPE字符串获取设备请求Client对象
     * @param deviceTypeString 设备TYPE
     * @return 设备请求Client对象
     */
    public DeviceClient getDeviceClient(String deviceTypeString) {
        DeviceType deviceType = DeviceTypeEnum.toEnum(deviceTypeString);
        AbstractDeviceManager<?, ?> deviceManager = getDeviceManager(deviceType);
        return deviceManager.getClient();
    }

}
