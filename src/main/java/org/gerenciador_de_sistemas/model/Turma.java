package org.gerenciador_de_sistemas.model;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "turma")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Turma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Ex: "2025/1", "2025/2"
    @Column(nullable = false, length = 20)
    private String semestre;

    @Column(nullable = false, length = 50)
    private String horario;

    // N:1 Disciplina
    @ManyToOne
    @JoinColumn(name = "disciplina_id", nullable = false)
    private Disciplina disciplina;

    // N:1 Professor
    @ManyToOne
    @JoinColumn(name = "professor_id", nullable = false)
    private Professor professor;
}
