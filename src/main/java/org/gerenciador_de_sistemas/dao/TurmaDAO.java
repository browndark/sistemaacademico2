package org.gerenciador_de_sistemas.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.gerenciador_de_sistemas.model.Turma;
import org.gerenciador_de_sistemas.utils.JPAUtil;

import java.util.List;

public class TurmaDAO {

    public void salvarOuAtualizar(Turma turma) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            if (turma.getId() == null) {
                em.persist(turma);  // INSERT
            } else {
                em.merge(turma);    // UPDATE
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void excluir(Turma turma) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            Turma t = em.find(Turma.class, turma.getId());
            if (t != null) {
                em.remove(t);
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public List<Turma> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Turma> q =
                    em.createQuery("SELECT t FROM Turma t ORDER BY t.id", Turma.class);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
}
