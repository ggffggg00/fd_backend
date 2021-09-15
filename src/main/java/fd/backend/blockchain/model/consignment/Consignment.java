package fd.backend.blockchain.model.consignment;

import fd.backend.blockchain.model.Port;
import fd.backend.blockchain.model.company.Company;
import fd.backend.blockchain.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "consignment", schema = "public")
@Entity
public class Consignment {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Company sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private Company receiver;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "departure_port_id")
    private Port departurePort;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "port_id")
    private Port port;

    @Column(name = "cargo_data")
    private String cargoData;


}
