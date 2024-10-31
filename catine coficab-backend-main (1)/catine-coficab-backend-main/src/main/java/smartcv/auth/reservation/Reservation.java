package smartcv.auth.reservation;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import smartcv.auth.menu.Menu;
import smartcv.auth.model.User;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Setter
@Getter
@Table(name = "reservation")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    private Date reservationDate;
    private Date cancellationDeadline;

    private boolean isCancelled;

    // New fields for selected menu items
    private String entree;      // Field for the selected entree
    private String suite;       // Field for the selected main course (plat principal)
    private String garniture;   // Field for the selected garnish
    private String dessert;      // Field for the selected dessert
    private String sandwich;     // Field for the selected sandwich

    public Reservation(User user, Menu menu, Date reservationDate, Date cancellationDeadline,
                       String entree, String suite, String garniture, String dessert, String sandwich) {
        this.user = user;
        this.menu = menu;
        this.reservationDate = reservationDate;
        this.cancellationDeadline = cancellationDeadline;
        this.isCancelled = false;
        this.entree = entree;
        this.suite = suite;
        this.garniture = garniture;
        this.dessert = dessert;
        this.sandwich = sandwich;
    }
}
