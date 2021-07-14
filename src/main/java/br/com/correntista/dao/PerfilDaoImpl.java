/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.correntista.dao;

import br.com.correntista.entidade.Perfil;
import java.io.Serializable;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author fabio
 */
public  class PerfilDaoImpl extends BaseDaoImpl<Perfil, Long> implements PerfilDao, Serializable {
 

    @Override
    public Perfil pesquisarPorId(Long id, Session sessao) throws HibernateException {
       return (Perfil) sessao.get(Perfil.class, id);
    }

    @Override
    public List<Perfil> pesquisarPorNome(String nome, Session sessao) throws HibernateException {
       Query consulta = sessao.createQuery("from Perfil where nome = :nome");
        consulta.setParameter("nome", "%" + nome + "%");
        return consulta.list();

    }

    @Override
    public List<Perfil> pesquisarTodos(Session sessao) throws HibernateException {
        return sessao.createQuery("from Perfil").list();
    }
    

    
    
}
