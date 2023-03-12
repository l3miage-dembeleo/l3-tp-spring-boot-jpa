package fr.uga.l3miage.library.data.domain;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

// Attention le mot 'user' est reservé
@Entity
@Table(name = "`user`")
@NamedQueries({
        @NamedQuery(name = "findAllOlderThan", query = "SELECT u FROM User u WHERE FUNCTION('YEAR', CURRENT_DATE) - FUNCTION('YEAR', u.birth) > :age")
})
public class User extends Person {
    @Temporal(TemporalType.DATE)
    private Date registered;
    private float lateRatio;

    public Date getRegistered() {
        return registered;
    }

    public void setRegistered(Date registered) {
        this.registered = registered;
    }

    public float getLateRatio() {
        return lateRatio;
    }

    public void setLateRatio(float lateRatio) {
        this.lateRatio = lateRatio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        if (!super.equals(o)) return false;
        return Float.compare(user.lateRatio, lateRatio) == 0 && Objects.equals(registered, user.registered);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), registered, lateRatio);
    }
}

