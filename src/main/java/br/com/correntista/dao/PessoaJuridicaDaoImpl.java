/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.correntista.dao;


import br.com.correntista.entidade.PessoaJuridica;
import br.com.correntista.entidade.Telefone;
import java.io.Serializable;
import java.util.List;
import org.hibernate.*;

/**
 *
 * @author Fabio
 */
public class PessoaJuridicaDaoImpl extends BaseDaoImpl<PessoaJuridica, Long>
                                               implements PessoaJuridicaDao, Serializable{

    @Override
    public PessoaJuridica pesquisarPorId(Long id, Session sessao) throws HibernateException {
        return (PessoaJuridica) sessao.get(PessoaJuridica.class, id);
    }

    @Override
    public List<PessoaJuridica> pesquisarPorNome(String nome, Session sessao) throws HibernateException {
        Query consulta = sessao.createQuery("from PessoaJuridica pj left join fetch pj.cartaos "
                + "where nome like :nome");
        consulta.setParameter("nome", "%" + nome + "%");
        return consulta.list();
    }

    @Override
    public PessoaJuridica pesquisarPorCnpj(String cnpj, Session sessao) throws HibernateException {
        Query consulta = sessao.createQuery("from PessoaJuridica pj "
                + " where cnpj = :cnpj");
        consulta.setParameter("cnpj",  cnpj);
        return (PessoaJuridica) consulta.uniqueResult();
                
    }

    @Override
    public Telefone pesquisarPorContato(String numero, Session sessao) throws HibernateException {
        Query consulta = sessao.createQuery("from PessoaJuridica pj left join fetch pj.telefones "
                + "where contato like :contato");
        return (Telefone) consulta.uniqueResult();
    }
}
