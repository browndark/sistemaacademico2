package org.gerenciador_de_sistemas.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {

    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("sysimcPU"); // nome da persistence-unit

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}

