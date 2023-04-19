package com.mega.hephaestus.pms.data.runtime.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mega.hephaestus.pms.data.model.entity.StorageStatus;
import com.mega.hephaestus.pms.data.runtime.mapper.StorageStatusMapper;
import com.mega.hephaestus.pms.data.runtime.service.IStorageStatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class StorageStatusServiceImpl extends
        ServiceImpl<StorageStatusMapper, StorageStatus> implements IStorageStatusService {


}
