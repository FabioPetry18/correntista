/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.correntista.dao;


import br.com.correntista.entidade.Usuario;
import java.io.Serializable;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author fabio
 */
public  class UsuarioDaoImpl extends BaseDaoImpl<Usuario, Long> implements UsuarioDao, Serializable {
 

    @Override
    public Usuario pesquisarPorId(Long id, Session sessao) throws HibernateException {
       return (Usuario) sessao.get(Usuario.class, id);
    }

    @Override
    public List<Usuario> pesquisarPorNome(String nome, Session sessao) throws HibernateException {
       Query consulta = sessao.createQuery("from Usuario where nome = :nome");
        consulta.setParameter("nome", "%" + nome + "%");
        return consulta.list();

    }

    
    
}
