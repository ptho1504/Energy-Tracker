package com.ptho1504.ingestion_service.service;

import com.ptho1504.ingestion_service.dto.EnergyUsageDto;
import com.ptho1504.kafka.event.EnergyUsageEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class IngestionService {

    private final KafkaTemplate<String, EnergyUsageEvent> kafkaTemplate;

    public void ingestEnergyUsage(EnergyUsageDto usageDto) {
        // Convert DTO to Event
        EnergyUsageEvent event = EnergyUsageEvent.builder()
                .deviceId(usageDto.deviceId())
                .energyConsumed(usageDto.energyConsumed())
                .timestamp(usageDto.timestamp())
                .build();

        kafkaTemplate.send("energy-usage", event);
        log.info("Ingested EnergyUsage Event: {}", event);
    }
}
