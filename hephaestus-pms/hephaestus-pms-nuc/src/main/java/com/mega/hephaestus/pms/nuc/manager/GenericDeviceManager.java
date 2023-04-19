package com.mega.hephaestus.pms.nuc.manager;


import com.mega.hephaestus.pms.nuc.client.GenericDeviceClient;
import com.mega.hephaestus.pms.nuc.config.DeviceConfiguration;
import com.mega.component.nuc.device.GenericDevice;
import com.mega.component.nuc.message.Response;

public class GenericDeviceManager extends AbstractDeviceManager<GenericDevice, GenericDeviceClient> {

    public GenericDeviceManager(GenericDevice device, GenericDeviceClient client, DeviceConfiguration configuration) {
        super(device, client, configuration);
    }

    public GenericDeviceManager(GenericDevice device, GenericDeviceClient client) {
        super(device, client, null);
    }

    public static GenericDeviceManager of(GenericDevice device, GenericDeviceClient client) {
        return new GenericDeviceManager(device, client, null);
    }

    public static GenericDeviceManager of(GenericDevice device, GenericDeviceClient client, DeviceConfiguration configuration) {
        return new GenericDeviceManager(device, client, configuration);
    }

    public static GenericDeviceManager of(GenericDeviceClient client, DeviceConfiguration configuration) {
        GenericDevice device = GenericDevice.of(configuration.getDeviceType(), configuration.getDeviceId(), configuration.getDeviceKey());
        return new GenericDeviceManager(device, client, configuration);
    }


    // IsSimulation
    public Response getIsSimulation() {
        return getParameters(GenericDevice.GenericParameter.IsSimulation.parameter());
    }

    public Response setIsSimulation(boolean is) {
        return setParameters(GenericDevice.GenericParameter.IsSimulation.parameter(), is);
    }

    // PMSAddress
    public Response getPMSAddress() {
        return getParameters(GenericDevice.GenericParameter.PMSAddress.parameter());
    }

    public Response setPMSAddress(String callbackUrl) {
        return setParameters(GenericDevice.GenericParameter.PMSAddress.parameter(), callbackUrl);
    }


    // Language
    public Response getLanguage() {
        return getParameters(GenericDevice.GenericParameter.Language.parameter());
    }

    public Response setLanguage(String lang) {
        return setParameters(GenericDevice.GenericParameter.Language.parameter(), lang);
    }

    // NUCVisibility
    public Response getNUCVisibility() {
        return getParameters(GenericDevice.GenericParameter.NUCVisibility.parameter());
    }

    public Response setNUCVisibility(String visibility) {
        return setParameters(GenericDevice.GenericParameter.NUCVisibility.parameter(), visibility);
    }

    // HasBalloonTip
    public Response getHasBalloonTip() {
        return getParameters(GenericDevice.GenericParameter.HasBalloonTip.parameter());
    }

    public Response setHasBalloonTip(boolean is) {
        return setParameters(GenericDevice.GenericParameter.HasBalloonTip.parameter(), is);
    }

}
