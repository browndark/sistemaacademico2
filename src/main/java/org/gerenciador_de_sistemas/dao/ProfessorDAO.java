package org.gerenciador_de_sistemas.dao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.gerenciador_de_sistemas.model.Professor;
import org.gerenciador_de_sistemas.utils.JPAUtil;

import java.util.List;

public class ProfessorDAO {

    public void salvarOuAtualizar(Professor professor) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            if (professor.getId() == null) {
                em.persist(professor);
            } else {
                em.merge(professor);
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void excluir(Professor professor) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            Professor p = em.find(Professor.class, professor.getId());
            if (p != null) em.remove(p);

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public List<Professor> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Professor> q =
                    em.createQuery("SELECT p FROM Professor p ORDER BY p.id", Professor.class);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
}
