/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.correntista.controle;

import br.com.correntista.dao.HibernateUtil;
import br.com.correntista.dao.ProfissaoDao;
import br.com.correntista.dao.ProfissaoDaoImpl;
import br.com.correntista.entidade.Profissao;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.view.ViewScoped;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author fabio
 */
@ManagedBean(name = "profissaoC")
@ViewScoped 
public class ProfissaoControle {

    private Profissao profissao;
    private ProfissaoDao profissaoDao;
    private Session sessao;
    private List<Profissao> profissoes;
    private DataModel<Profissao> modelProfissoes;
    private int aba;

    public ProfissaoControle() {

        profissaoDao = new ProfissaoDaoImpl();
    }

    public void salvar() {
        sessao = HibernateUtil.abrirSessao();
        try {
            profissaoDao.salvarOuAlterar(profissao, sessao);
            profissao = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Salvo com sucesso!", null));
             modelProfissoes = null;
        } catch (HibernateException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Informação", ""));

        } finally {
            sessao.close();
        }
    }

    public void pesquisarPorNome() {
        sessao = HibernateUtil.abrirSessao();
        try {
            profissoes = profissaoDao.pesquisarPorNome(profissao.getNome(), sessao);
            modelProfissoes = new ListDataModel<>(profissoes);
            profissao.setNome(null);
        } catch (HibernateException e) {
            System.out.println("Erro ao pesquisar " + e.getMessage());
        } finally {
            sessao.close();
        }

    }
    public void excluir(){
        profissao = modelProfissoes.getRowData();
           sessao = HibernateUtil.abrirSessao();
            profissao = null;
        try {
         profissaoDao.excluir(profissao, sessao);
            profissao = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Excluido com sucesso!", null));
            modelProfissoes = null;
        } catch (HibernateException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Erro ao Excluir", ""));

        }
    }
    
    public void prepararAlterar(){
        profissao = modelProfissoes.getRowData();
        aba = 1;
    }
    
    public Profissao getProfissao() {
        if (profissao == null) {
            profissao = new Profissao();
        }
        return profissao;
    }

    public void setProfissao(Profissao profissao) {
        this.profissao = profissao;
    }

   
    public DataModel<Profissao> getModelProfissoes() {
        return modelProfissoes;
    }

    public int getAba() {
        return aba;
    }
    
}
