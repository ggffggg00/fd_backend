package fd.backend.blockchain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "consignment", schema = "public")
@Entity
public class Consignment {
    //User object
    //User (id, hashPass, companyId?)

    Long id;

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }
    // Id (int?)
    // Дата создания (@TimeStamp?)
    // отправитель (User)
    // получатель (User)
    // кто перевозит (String)
    // порт отправления (Port.field)
    // порт назначения (Port.field)
    // Данные о грузе String

    /**
     * Admin (создать, зарегать - груз)
     * Порты зареганые
     *
     */
}
