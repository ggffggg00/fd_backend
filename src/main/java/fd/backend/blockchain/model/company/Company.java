package fd.backend.blockchain.model.company;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@Table(schema = "public")
@Entity(name = "company")
@RequiredArgsConstructor
@Accessors(chain = true)
@Builder
@ToString
public class Company {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "tax_id", nullable = false)
    private String taxIdentifier;

    @Column(name = "ogrn", nullable = false)
    private String ogrn;

    @Column(name = "legal_address", nullable = false)
    private String legalAddress;

    @Column(name = "phone", nullable = false)
    private String contactPhoneNumber;

    @JsonIgnore
    @Column(name = "aes_key", nullable = true)
    private String aesKey;


}