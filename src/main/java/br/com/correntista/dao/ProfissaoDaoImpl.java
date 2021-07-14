/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.correntista.dao;

import br.com.correntista.entidade.Profissao;
import java.io.Serializable;
import java.util.List;
import org.hibernate.*;

/**
 *
 * @author Silvio
 */
public class ProfissaoDaoImpl extends BaseDaoImpl<Profissao, Long>
                                               implements ProfissaoDao, Serializable{

    @Override
    public Profissao pesquisarPorId(Long id, Session sessao) throws HibernateException {
        return (Profissao) sessao.get(Profissao.class, id);
    }

    @Override
    public List<Profissao> pesquisarPorNome(String nome, Session sessao) throws HibernateException {
        Query consulta = sessao.createQuery("from Profissao where nome like :nome");
        consulta.setParameter("nome", "%" + nome + "%");
        return consulta.list();
    }

    @Override
    public List<Profissao> pesquisarTodo(Session sessao) throws HibernateException {
       return sessao.createQuery("from Profissao order by nome").list();
    }
    
}
