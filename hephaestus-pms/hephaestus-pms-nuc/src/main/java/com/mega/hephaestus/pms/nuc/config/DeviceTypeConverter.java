package com.mega.hephaestus.pms.nuc.config;

import com.mega.component.nuc.device.DeviceType;
import com.mega.component.nuc.device.GenericDeviceType;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;

import java.util.Collections;
import java.util.Set;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/3/29 20:54
 */
public class DeviceTypeConverter implements GenericConverter {
    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return Collections.singleton(new ConvertiblePair(String.class, DeviceType.class));
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (source instanceof String) {
            String code = (String) source;
            return new GenericDeviceType(code, code);
        }
        return null;
    }
}
