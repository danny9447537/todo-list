package entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "tasks")
@NamedQuery(name = "TasksEntity.findAll", query = "SELECT t FROM TasksEntity t")
public class TasksEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "description")
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TasksEntity tasks = (TasksEntity) o;
        return id == tasks.id && Objects.equals(description, tasks.description);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + Objects.hashCode(description);
        return result;
    }
}
