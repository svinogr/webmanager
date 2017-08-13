package ap.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "mail")
public class Mail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull(message = "mail is absent")
    @Email()
    @Column(name = "mail")
    private String mail;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "parentid", nullable = false)
    private User parentId;

    @Transient
    private String error;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Mail() {
    }

    public Mail(String number) {
        this.mail = mail;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public User getParentId() {
        return parentId;
    }

    public void setParentId(User parentId) {
        this.parentId = parentId;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "Mail{" +
                "id=" + id +
                ", number=" + mail +
                ", parentId=" + parentId.getId() +
                ", error='" + error + '\'' +
                '}';
    }
}
