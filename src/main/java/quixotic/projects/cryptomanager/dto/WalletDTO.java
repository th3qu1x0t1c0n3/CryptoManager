package quixotic.projects.cryptomanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import quixotic.projects.cryptomanager.model.enums.Network;
import quixotic.projects.cryptomanager.model.Wallet;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WalletDTO {
    private Long id;
    private String name;
    private String address;
    private Network network;
    private String notes;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;

    public WalletDTO(Wallet wallet) {
        this.id = wallet.getId();
        this.name = wallet.getName();
        this.address = wallet.getAddress();
        this.network = wallet.getNetwork();
        this.notes = wallet.getNotes();
        this.updatedAt = wallet.getUpdatedAt();
        this.createdAt = wallet.getCreatedAt();
    }

    public Wallet toEntity() {
        return Wallet.builder()
                .id(this.id)
                .name(this.name)
                .address(this.address)
                .network(this.network)
                .notes(this.notes)
                .updatedAt(this.updatedAt)
                .createdAt(this.createdAt)
                .build();
    }

}
