package com.apiwiz.apiwiz.Model;

import com.apiwiz.apiwiz.Enums.Notify_Frequency;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Builder
@Document
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Subscribe {

    @Id
    private String id;
    private String notifyTime;
    private String stockSymbol;
    @Enumerated(EnumType.STRING)
    private Notify_Frequency notifyFrequency;
    private String date;

    private String email;
}
