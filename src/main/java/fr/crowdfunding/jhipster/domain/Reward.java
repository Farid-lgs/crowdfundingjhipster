package fr.crowdfunding.jhipster.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Reward.
 */
@Entity
@Table(name = "reward")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Reward implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "minimum_value", nullable = false)
    private Double minimumValue;

    @Column(name = "deliver_at")
    private Instant deliverAt;

    @Column(name = "expires_at")
    private Instant expiresAt;

    @Column(name = "maximum_contributions")
    private Integer maximumContributions;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "projectImages",
            "contributions",
            "projectAccounts",
            "projectPosts",
            "rewards",
            "balanceTransfer",
            "community",
            "userInfos",
            "category",
        },
        allowSetters = true
    )
    private Project project;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Reward id(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public Reward title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public Reward description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getMinimumValue() {
        return this.minimumValue;
    }

    public Reward minimumValue(Double minimumValue) {
        this.minimumValue = minimumValue;
        return this;
    }

    public void setMinimumValue(Double minimumValue) {
        this.minimumValue = minimumValue;
    }

    public Instant getDeliverAt() {
        return this.deliverAt;
    }

    public Reward deliverAt(Instant deliverAt) {
        this.deliverAt = deliverAt;
        return this;
    }

    public void setDeliverAt(Instant deliverAt) {
        this.deliverAt = deliverAt;
    }

    public Instant getExpiresAt() {
        return this.expiresAt;
    }

    public Reward expiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
        return this;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Integer getMaximumContributions() {
        return this.maximumContributions;
    }

    public Reward maximumContributions(Integer maximumContributions) {
        this.maximumContributions = maximumContributions;
        return this;
    }

    public void setMaximumContributions(Integer maximumContributions) {
        this.maximumContributions = maximumContributions;
    }

    public byte[] getImage() {
        return this.image;
    }

    public Reward image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return this.imageContentType;
    }

    public Reward imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Project getProject() {
        return this.project;
    }

    public Reward project(Project project) {
        this.setProject(project);
        return this;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Reward)) {
            return false;
        }
        return id != null && id.equals(((Reward) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Reward{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", minimumValue=" + getMinimumValue() +
            ", deliverAt='" + getDeliverAt() + "'" +
            ", expiresAt='" + getExpiresAt() + "'" +
            ", maximumContributions=" + getMaximumContributions() +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            "}";
    }
}
