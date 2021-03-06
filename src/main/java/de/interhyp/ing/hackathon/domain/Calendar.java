package de.interhyp.ing.hackathon.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Calendar.
 */
@Entity
@Table(name = "calendar")
public class Calendar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "jhi_start")
    private ZonedDateTime start;

    @Column(name = "jhi_end")
    private ZonedDateTime end;

    @Column(name = "title")
    private String title;

    @Column(name = "backgroundcolor")
    private String backgroundcolor;

    @Column(name = "foregroundcolor")
    private String foregroundcolor;

    @OneToOne
    @JoinColumn(unique = true)
    private Location location;

    @OneToOne
    @JoinColumn(unique = true)
    private Transaction transaction;

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getStart() {
        return start;
    }

    public Calendar start(ZonedDateTime start) {
        this.start = start;
        return this;
    }

    public void setStart(ZonedDateTime start) {
        this.start = start;
    }

    public ZonedDateTime getEnd() {
        return end;
    }

    public Calendar end(ZonedDateTime end) {
        this.end = end;
        return this;
    }

    public void setEnd(ZonedDateTime end) {
        this.end = end;
    }

    public String getTitle() {
        return title;
    }

    public Calendar title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackgroundColor() {
        return backgroundcolor;
    }

    public Calendar backgroundcolor(String backgroundcolor) {
        this.backgroundcolor = backgroundcolor;
        return this;
    }

    public void setBackgroundColor(String backgroundcolor) {
        this.backgroundcolor = backgroundcolor;
    }

    public String getForegroundColor() {
        return foregroundcolor;
    }

    public Calendar foregroundcolor(String foregroundcolor) {
        this.foregroundcolor = foregroundcolor;
        return this;
    }

    public void setForegroundColor(String foregroundcolor) {
        this.foregroundcolor = foregroundcolor;
    }
    public Location getLocation() {
        return location;
    }

    public Calendar location(Location location) {
        this.location = location;
        return this;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Calendar calendar = (Calendar) o;
        if (calendar.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), calendar.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Calendar{" +
            "id=" + getId() +
            ", start='" + getStart() + "'" +
            ", end='" + getEnd() + "'" +
            ", title='" + getTitle() + "'" +
            ", backgroundcolor='" + getBackgroundColor() + "'" +
            ", foregroundcolor='" + getForegroundColor() + "'" +
            "}";
    }
}
