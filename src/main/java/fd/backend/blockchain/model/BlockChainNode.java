package fd.backend.blockchain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "bcNode")
@Table(name = "blockchain_node", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "uc_blockchainnode_id", columnNames = {"id"})
})
@ToString(onlyExplicitlyIncluded = true)
public class BlockChainNode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "host", nullable = false)
    private String host;

    @JsonProperty("node_name")
    @Column(name = "node_name", nullable = false)
    private String nodeName;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Date creationDate;

}
