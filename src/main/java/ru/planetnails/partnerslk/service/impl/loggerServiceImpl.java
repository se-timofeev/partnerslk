package ru.planetnails.partnerslk.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.planetnails.partnerslk.logger.Logger;
import ru.planetnails.partnerslk.repository.LoggingRepository;
import ru.planetnails.partnerslk.service.LoggerService;
@Service
@Slf4j
public class loggerServiceImpl implements LoggerService {
    private final LoggingRepository loggingRepository;

    @Autowired
    public loggerServiceImpl(LoggingRepository loggingRepository) {
        this.loggingRepository = loggingRepository;
    }

    @Override
    public void save(Logger logger) {
       loggingRepository.save(logger);
    }
}
