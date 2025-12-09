package org.gerenciador_de_sistemas.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.gerenciador_de_sistemas.model.Curso;
import org.gerenciador_de_sistemas.utils.JPAUtil;


import java.util.List;

public class CursoDAO {

    public void salvarOuAtualizar(Curso curso) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();

            if (curso.getId() == null) {
                em.persist(curso);       // INSERT
            } else {
                em.merge(curso);         // UPDATE
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void excluir(Curso curso) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();

            Curso c = em.find(Curso.class, curso.getId());
            if (c != null) {
                em.remove(c);
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public List<Curso> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();
        List<Curso> lista = null;

        try {
            TypedQuery<Curso> query =
                    em.createQuery("SELECT c FROM Curso c ORDER BY c.id", Curso.class);
            lista = query.getResultList();
        } finally {
            em.close();
        }

        return lista;
    }
}
