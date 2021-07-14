/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.correntista.dao;


import br.com.correntista.entidade.Usuario;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author fabio
 */
public interface UsuarioDao extends BaseDao<Usuario,Long> {
    List<Usuario> pesquisarPorNome(String nome, Session sessao) throws HibernateException;
}
