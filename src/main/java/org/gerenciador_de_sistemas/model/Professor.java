package org.gerenciador_de_sistemas.model;
import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "professor")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Professor {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false, length = 120)
        private String nome;

        @Column(nullable = false, unique = true, length = 150)
        private String email;

        @Column(nullable = false, length = 120)
        private String formacao;

        // N:N com Disciplina (lado inverso)
        @ManyToMany(mappedBy = "professores")
        @ToString.Exclude
        private Set<Disciplina> disciplinas = new HashSet<>();
}


