package fd.backend.blockchain.model.consignment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ConsignmentDto {

    String id;

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
