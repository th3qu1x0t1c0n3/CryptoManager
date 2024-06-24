package quixotic.projects.cryptomanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import quixotic.projects.cryptomanager.model.Network;
import quixotic.projects.cryptomanager.model.Wallet;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WalletDTO {
    private Long id;
    private String name;
    private String address;
    private Network network;

    public WalletDTO(Wallet wallet) {
        this.id = wallet.getId();
        this.name = wallet.getName();
        this.address = wallet.getAddress();
        this.network = wallet.getNetwork();
    }

    public Wallet toEntity() {
        return Wallet.builder()
                .id(this.id)
                .name(this.name)
                .address(this.address)
                .network(this.network)
                .build();
    }

}
