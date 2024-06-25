package quixotic.projects.cryptomanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EtherPriceDTO {
    private String ethbtc;
    private String ethbtc_timestamp;
    private String ethusd;
    private String ethusd_timestamp;
}
