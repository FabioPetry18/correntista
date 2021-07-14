/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.correntista.dao;

import br.com.correntista.entidade.PessoaJuridica;
import br.com.correntista.entidade.Telefone;
import java.util.List;
import org.hibernate.*;

/**
 *
 * @author Silvio
 */
public interface PessoaJuridicaDao extends BaseDao<PessoaJuridica, Long>{
    
    List<PessoaJuridica> pesquisarPorNome(String nome, Session sessao) throws HibernateException;
    
    PessoaJuridica pesquisarPorCnpj(String cnpj, Session sessao) throws HibernateException;
    
    Telefone pesquisarPorContato(String numero, Session sessao)throws HibernateException;
}
