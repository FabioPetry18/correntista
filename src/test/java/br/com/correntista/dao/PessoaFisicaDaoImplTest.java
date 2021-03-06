/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.correntista.dao;

import br.com.correntista.entidade.Endereco;
import br.com.correntista.entidade.PessoaFisica;
import br.com.correntista.entidade.Profissao;
import static br.com.correntista.util.UtilGerador.*;
import br.com.correntista.webservice.WebServiceEndereco;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Silvio
 */
public class PessoaFisicaDaoImplTest {

    private PessoaFisica fisica;
    private PessoaFisicaDao fisicaDao;
    private Session sessao;

    public PessoaFisicaDaoImplTest() {
        fisicaDao = new PessoaFisicaDaoImpl();
    }

   // @Test
    public void testSalvar() {
        System.out.println("salvar");
        
        ProfissaoDaoImplTest profissaoDao = new ProfissaoDaoImplTest();
        Profissao profissao = profissaoDao.buscarProfissaoBD();
        
       fisica = new PessoaFisica(null, gerarNome(), gerarEmail(), gerarCpf(), gerarNumero(6));
       
        //trabalhar com webservice
        WebServiceEndereco webService = new WebServiceEndereco();
        Endereco endereco = webService.pesquisarCep("01001000");
        endereco.setNumero(gerarNumero(3));
         endereco.setComplemento(gerarCaracter(7));
        
        fisica.setEndereco(endereco);  
        endereco.setCliente(fisica);//devido a composição - está salvando ao mesmo tempo
        fisica.setProfissao(profissao);
        sessao = HibernateUtil.abrirSessao();
        
        fisicaDao.salvarOuAlterar(fisica, sessao);
        sessao.close();
        assertNotNull(fisica.getId());
        assertNotNull(fisica.getEndereco().getId());
    }

  //  @Test
    public void testAlterar() {
        System.out.println("alterar");
        buscaPessoaFisicaBD();
        fisica.setNome(gerarNome());
        sessao = HibernateUtil.abrirSessao();
        fisicaDao.salvarOuAlterar(fisica, sessao);
        sessao.close();

        sessao = HibernateUtil.abrirSessao();
        PessoaFisica fisicaAlt = fisicaDao.pesquisarPorId(fisica.getId(), sessao);
        sessao.close();
        assertEquals(fisica.getNome(), fisicaAlt.getNome());
    }

  //  @Test
    public void testExcluir() { // lembrando que o excluir deverá só ser testado antes de ter pedido.
        System.out.println("excluir");
        buscaPessoaFisicaBD();
        sessao = HibernateUtil.abrirSessao();
        fisicaDao.excluir(fisica, sessao);

        PessoaFisica fisicaExc = fisicaDao.pesquisarPorId(fisica.getId(), sessao);
        sessao.close();

        assertNull(fisicaExc);
    }

  //  @Test
    public void testPesquisarPorNome() {
        System.out.println("pesquisarPorNome");
        buscaPessoaFisicaBD();
        sessao = HibernateUtil.abrirSessao();
        List<PessoaFisica> pessoas = fisicaDao.pesquisarPorNome(fisica.getNome().substring(0, 4), sessao);
        sessao.close();
        assertTrue(!pessoas.isEmpty());// será acertado se a lista não vier vazia
    }

  // @Test
    public void testPesquisarPorCpf() {
        System.out.println("pesquisarPorCpf");
        buscaPessoaFisicaBD();//esse metodo vai pesquisar uma pessoa fisica com spf do bd
        sessao = HibernateUtil.abrirSessao();
        PessoaFisica fisicaCPF = fisicaDao.pesquisarPorCpf(fisica.getCpf(), sessao);// tem que trazer
        sessao.close();
        assertNotNull(fisicaCPF);

    }

    public PessoaFisica buscaPessoaFisicaBD() {
        sessao = HibernateUtil.abrirSessao();
        Query consulta = sessao.createQuery("from PessoaFisica pf left join fetch pf.cartaos");
        List<PessoaFisica> fisicas = consulta.list();
        sessao.close();
        if (fisicas.isEmpty()) {
            testSalvar();
        } else {
            fisica = fisicas.get(0);
        }
        return fisica;
    }

}
