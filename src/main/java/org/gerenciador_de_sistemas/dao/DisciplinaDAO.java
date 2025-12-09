package org.gerenciador_de_sistemas.dao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.gerenciador_de_sistemas.model.Disciplina;
import org.gerenciador_de_sistemas.utils.JPAUtil;

import java.util.List;

public class DisciplinaDAO {

    public void salvarOuAtualizar(Disciplina disciplina) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();

            if (disciplina.getId() == null) {
                em.persist(disciplina);   // INSERT
            } else {
                em.merge(disciplina);     // UPDATE
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void excluir(Disciplina disciplina) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();

            Disciplina d = em.find(Disciplina.class, disciplina.getId());
            if (d != null) {
                em.remove(d);
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public List<Disciplina> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();
        List<Disciplina> lista = null;

        try {
            TypedQuery<Disciplina> query =
                    em.createQuery("SELECT d FROM Disciplina d ORDER BY d.id", Disciplina.class);
            lista = query.getResultList();
        } finally {
            em.close();
        }

        return lista;
    }
}
