package dev.danilosales.bank.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {

    @JsonProperty("account_id")
    private Long id;

    @NotBlank
    @Size(max = 255)
    @JsonProperty("document_number")
    private String documentNumber;

    @NotBlank
    @Size(max = 255)
    private String name;
}
