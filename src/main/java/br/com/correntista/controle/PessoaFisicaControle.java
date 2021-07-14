/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.correntista.controle;

import br.com.correntista.dao.HibernateUtil;
import br.com.correntista.dao.PessoaFisicaDao;
import br.com.correntista.dao.PessoaFisicaDaoImpl;
import br.com.correntista.dao.ProfissaoDao;
import br.com.correntista.dao.ProfissaoDaoImpl;
import br.com.correntista.entidade.Endereco;
import br.com.correntista.entidade.PessoaFisica;
import br.com.correntista.entidade.Profissao;
import br.com.correntista.webservice.WebServiceEndereco;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TabCloseEvent;

/**
 *
 * @author fabio
 */
@ManagedBean(name = "pfisicaC")
@ViewScoped
public class PessoaFisicaControle {

    private PessoaFisica pessoaFisica;
    private Endereco endereco;
    private PessoaFisicaDao pessoaFisicadao;
    private Session sessao;
    private List<PessoaFisica> pessoaFisicas;
    private List<SelectItem> comboProfissoes;
    private DataModel<PessoaFisica> modelfisicas;
    private int aba;
    private Profissao profissao;

    public PessoaFisicaControle() {
        PessoaFisicaDao pessoaFisicaDao = new PessoaFisicaDaoImpl();
    }

    public String salvar() {
        pessoaFisicadao = new PessoaFisicaDaoImpl();
        Session Sessao = HibernateUtil.abrirSessao();
        FacesContext contexto = FacesContext.getCurrentInstance(); //perguntar
        FacesMessage message = null;
        try {
            pessoaFisica.setProfissao(profissao);
            pessoaFisicadao.salvarOuAlterar(pessoaFisica, Sessao);
            message = new FacesMessage("salvo com sucesso!", "");
            contexto.addMessage(null, message);
            modelfisicas = null;

        } catch (HibernateException e) {
            message = new FacesMessage("erro ao salvar!", "");
            contexto.addMessage(null, message);
        } finally {
            Sessao.close();
            contexto.addMessage(null, message);
        }
        return "mostra";
    }

    public void pesquisarPorNome() {
        sessao = HibernateUtil.abrirSessao();
        try {
            pessoaFisicas = pessoaFisicadao.pesquisarPorNome(pessoaFisica.getNome(), sessao);
            modelfisicas = new ListDataModel<>(pessoaFisicas);
            pessoaFisica.setNome(null);
        } catch (HibernateException e) {
            System.out.println("Erro ao pesquisar " + e.getMessage());
        } finally {
            sessao.close();
        }

    }

    public void buscarCep() {
        WebServiceEndereco webService = new WebServiceEndereco();
        endereco = webService.pesquisarCep(endereco.getCep());
        if (endereco == null) {
            endereco = new Endereco();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Não existe nenhum Cep com esse valor!", null));
        }
    }

    public void carregarComboProfissao() {
        sessao = HibernateUtil.abrirSessao();
        ProfissaoDao profissaoDao = new ProfissaoDaoImpl();
        try {
            List<Profissao> profissoes = profissaoDao.pesquisarTodo(sessao);
            comboProfissoes = new ArrayList<>();
            for (Profissao perfi : profissoes) {
                comboProfissoes.add(new SelectItem(perfi.getId(), perfi.getNome()));
            }

        } catch (HibernateException e) {
            System.out.println("Erro ao carregar combobox perfil " + e.getMessage());
        } finally {
            sessao.close();
        }
    }

    public void onTabChange(TabChangeEvent event) {
        if (event.getTab().getTitle().equals("Novo")) {
            carregarComboProfissao();
        }
    }

    public void onTabClose(TabCloseEvent event) {

    }

    public void excluir() {
        pessoaFisica = modelfisicas.getRowData();
        sessao = HibernateUtil.abrirSessao();
        pessoaFisica = null;
        try {
            pessoaFisicadao.excluir(pessoaFisica, sessao);
            pessoaFisica = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Excluido com sucesso!", null));
            modelfisicas = null;
        } catch (HibernateException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Erro ao Excluir", ""));

        }
    }

    public void prepararAlterar() {
        pessoaFisica = modelfisicas.getRowData();
        endereco = pessoaFisica.getEndereco();
        profissao = pessoaFisica.getProfissao();
        aba = 1;
    }

    public PessoaFisica getPessoaFisica() {
        if (pessoaFisica == null) {
            pessoaFisica = new PessoaFisica();
        }
        return pessoaFisica;
    }

    public void setPessoaFisica(PessoaFisica pessoaFisica) {
        this.pessoaFisica = pessoaFisica;
    }

    public List<PessoaFisica> GetpessoaFisicas() {
        return pessoaFisicas;
    }

    public DataModel<PessoaFisica> getModelfisicas() {
        return modelfisicas;
    }

    public List<SelectItem> getComboProfissoes() {
        return comboProfissoes;
    }

    public Profissao getProfissao() {
        if(profissao == null){
            profissao = new Profissao();
        }
        return profissao;
    }

    public void setProfissao(Profissao profissao) {
        this.profissao = profissao;
    }

    public int getAba() {
        return aba;
    }

    public Endereco getEndereco() {
        if (endereco == null) {
            endereco = new Endereco();

        }
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

}
