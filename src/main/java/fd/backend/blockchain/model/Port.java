package fd.backend.blockchain.model;

import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "port")
@Table(name = "port", schema = "public")
@ToString
public class Port {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "title", length = 255)
    private String title;

    @Column(name = "locode", unique = true, length = 15)
    private String locode;

    @Column(name = "country")
    private String country;

    @Column(name = "location")
    private String location;

}



