/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.correntista.controle;

import br.com.correntista.dao.HibernateUtil;
import br.com.correntista.dao.PessoaJuridicaDao;
import br.com.correntista.dao.PessoaJuridicaDaoImpl;
import br.com.correntista.dao.ProfissaoDao;
import br.com.correntista.dao.ProfissaoDaoImpl;
import br.com.correntista.entidade.Cliente;
import br.com.correntista.entidade.Endereco;
import br.com.correntista.entidade.PessoaFisica;
import br.com.correntista.entidade.PessoaJuridica;
import br.com.correntista.entidade.Profissao;
import br.com.correntista.entidade.Telefone;
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
@ManagedBean(name = "pJuridicaC")
@ViewScoped
public class PessoaJuridicaControle {
    
    private PessoaJuridica pessoaJuridica;
    private Endereco endereco;
    private Telefone telefone;
    private Cliente cliente;
    private PessoaJuridicaDao pessoaJuridicaDao;
    private List<PessoaJuridica> pessoaJuridicas;
    private List<Telefone> telefones;
    private DataModel<PessoaJuridica> modelJuridicas;
    private int aba;
    private Session sessao;
    
    
    public PessoaJuridicaControle() {
           pessoaJuridicaDao = new PessoaJuridicaDaoImpl();
    }

    
    
    public String salvar() {
    
            Session  Sessao = HibernateUtil.abrirSessao();
            FacesContext contexto = FacesContext.getCurrentInstance(); //perguntar
            FacesMessage message = null; 
        try {
              endereco.setCliente(cliente);
              pessoaJuridica.setEndereco(endereco);
              pessoaJuridica.setTelefones(telefones);
              pessoaJuridicaDao.salvarOuAlterar(pessoaJuridica, Sessao);
              message = new FacesMessage("salvo com sucesso!","");
              contexto.addMessage(null, message);
              
        } catch (HibernateException e) {
            message = new FacesMessage("erro ao salvar!","");
            contexto.addMessage(null, message);
        }finally{
             Sessao.close();
             contexto.addMessage(null, message);
        }
        return "mostra";
    }
    
         public void pesquisarPorNome() {
        sessao = HibernateUtil.abrirSessao();
        try {
            pessoaJuridicas = pessoaJuridicaDao.pesquisarPorNome(pessoaJuridica.getNome(), sessao);
            modelJuridicas = new ListDataModel<>(pessoaJuridicas);
            pessoaJuridica.setNome(null);
        } catch (HibernateException e) {
            System.out.println("Erro ao pesquisar " + e.getMessage());
        } finally {
            sessao.close();
        }

    }
        public void adicionarTelefoneLista(){
            if(telefones == null){
                telefones = new ArrayList<>();
            }
            telefones.add(telefone);
            telefone.setPessoaJuridica(pessoaJuridica);
            telefone = new Telefone();
        }
         public void buscarCep(){
             WebServiceEndereco webService = new WebServiceEndereco();
             endereco = webService.pesquisarCep(endereco.getCep());
             if(endereco == null){
                 endereco = new Endereco();
                  FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Não existe nenhum Cep com esse valor!", null));
             }
         }
    
    
    public void onTabChange(TabChangeEvent event) {
       if(event.getTab().getTitle().equals("Novo")){
           
       }
    }

    public void onTabClose(TabCloseEvent event) {
        
    }
    
    public void excluir(){
        pessoaJuridica = modelJuridicas.getRowData();
           sessao = HibernateUtil.abrirSessao();
            pessoaJuridica = null;
        try {
         pessoaJuridicaDao.excluir(pessoaJuridica, sessao);
            pessoaJuridica = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Excluido com sucesso!", null));
            modelJuridicas = null;
        } catch (HibernateException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Erro ao Excluir", ""));

        }
    }
      public PessoaJuridica getPessoaJuridica() {
        if(pessoaJuridica == null) {
            pessoaJuridica = new PessoaJuridica();  
        }
        return pessoaJuridica;
    }

    public void setPessoaJuridica(PessoaJuridica pessoaJuridica) {
        this.pessoaJuridica = pessoaJuridica;
    }

    public List<PessoaJuridica> GetpessoaJuridicas() {
        return pessoaJuridicas;
    }

    public DataModel<PessoaJuridica> getModelJuridicas() {
        return modelJuridicas;
    }

   

    public int getAba() {
        return aba;
    }

    public Endereco getEndereco() {
        if(endereco == null){
            endereco = new Endereco();
            
        }
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Telefone getTelefone() {
         if(telefone == null){
            telefone = new Telefone();
        }
        return telefone;
    }

    public void setTelefone(Telefone telefone) {
        this.telefone = telefone;
    }

    public List<Telefone> getTelefones() {
        return telefones;
    }
    
    
    }
    

