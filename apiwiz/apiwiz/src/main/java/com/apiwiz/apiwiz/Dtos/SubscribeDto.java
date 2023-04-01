package com.apiwiz.apiwiz.Dtos;

import com.apiwiz.apiwiz.Enums.Notify_Frequency;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class SubscribeDto {
    @Id
    private String id;

    private String stockSymbol;
    @Enumerated(EnumType.STRING)
    private Notify_Frequency notifyFrequency;

    private String notifyTime;

}
