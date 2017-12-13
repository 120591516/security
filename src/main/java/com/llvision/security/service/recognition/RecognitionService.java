package com.llvision.security.service.recognition;

import com.llvision.security.domain.User;
import com.llvision.security.service.dto.RecognitionRecordDTO;
import com.llvision.security.service.dto.RecognitionRecordOutputDTO;
import com.llvision.security.service.dto.WorkRecordOutputDTO;
import com.llvision.security.service.storage.StorageException;

/**
 * Created by llvision on 17/5/3.
 */
public interface RecognitionService {

    RecognitionRecordOutputDTO insert(RecognitionRecordDTO recognitionRecordDTO, User currentUser) throws StorageException, RecognitionException;

    WorkRecordOutputDTO fillWorkRecord(WorkRecordOutputDTO workRecordOutputDTO);
}
