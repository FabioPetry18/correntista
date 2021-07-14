/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.correntista.dao;

import br.com.correntista.entidade.PessoaFisica;
import java.io.Serializable;
import java.util.List;
import org.hibernate.*;

/**
 *
 * @author Silvio
 */
public class PessoaFisicaDaoImpl extends BaseDaoImpl<PessoaFisica, Long>
                                               implements PessoaFisicaDao, Serializable{

    @Override
    public PessoaFisica pesquisarPorId(Long id, Session sessao) throws HibernateException {
        return (PessoaFisica) sessao.get(PessoaFisica.class, id);
    }

    @Override
    public List<PessoaFisica> pesquisarPorNome(String nome, Session sessao) throws HibernateException {
        Query consulta = sessao.createQuery("from PessoaFisica pf left join fetch pf.cartaos"
                + " where pf.nome like :nome");
        consulta.setParameter("nome", "%" + nome + "%");
        return consulta.list();
    }

    @Override
    public PessoaFisica pesquisarPorCpf(String cpf, Session sessao) throws HibernateException {
        Query consulta = sessao.createQuery("from PessoaFisica pf left join fetch pf.cartaos"
                + " where cpf = :cpf");
        consulta.setParameter("cpf",  cpf);
        return (PessoaFisica) consulta.uniqueResult();
                
    }

    @Override
    public List<PessoaFisica> pesquisarTodo(Session sessao) throws HibernateException {
        return sessao.createQuery("from PessoaFisica").list();
    }
}
